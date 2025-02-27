// Copyright (c) 2021 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
//
// WSO2 Inc. licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except
// in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

import ballerina/io;
import ballerinax/mssql;
import ballerina/sql;

// The username , password and name of the MSSQL database
configurable string dbUsername = "sa";
configurable string dbPassword = "Test123#";
configurable string dbName = "EXAMPLE_DB";

// Defines a record to load the query result schema as shown below in the
// 'getDataWithTypedQuery' function. In this example, all columns of the
// customer table will be loaded. Therefore, the `Customer` record will be
// created with all the columns. The column name of the result and the
// defined field name of the record will be matched case insensitively.
type Customer record {
    int customerId;
    string lastName;
    string firstName;
    int registrationId;
    float creditLimit;
    string country;
};

public function main() returns error? {
    // Runs the prerequisite setup for the example.
    check beforeExample();

    // Initializes the MSSQL client.
    mssql:Client dbClient = check new (user = dbUsername, password = dbPassword, database = dbName);

    // The records to be inserted.
    var insertRecords = [
        {firstName: "Peter", lastName: "Stuart", registrationID: 1,
                                    creditLimit: 5000.75, country: "USA"},
        {firstName: "Stephanie", lastName: "Mike", registrationID: 2,
                                    creditLimit: 8000.00, country: "USA"},
        {firstName: "Bill", lastName: "John", registrationID: 3,
                                    creditLimit: 3000.25, country: "USA"}
    ];

    // Creates a batch parameterized query.
    sql:ParameterizedQuery[] insertQueries =
        from var data in insertRecords
            select  `INSERT INTO Customers
                (firstName, lastName, registrationID, creditLimit, country)
                VALUES (${data.firstName}, ${data.lastName},
                ${data.registrationID}, ${data.creditLimit}, ${data.country})`;

    // Inserts the records with the auto-generated ID.
    sql:ExecutionResult[] result =
                            check dbClient->batchExecute(insertQueries);

    // Checks the data after the batch execution.
    stream<record{}, error?> resultStream =
        dbClient->query("SELECT * FROM Customers");

    io:println("Data in Customers table:");
    error? e = resultStream.forEach(function(record {} result) {
         io:println(result.toString());
    });

    // Closes the MSSQL client.
    check dbClient.close();
}

// Initializes the database as a prerequisite to the example.
function beforeExample() returns sql:Error? {
    //Initializes the database.
    mssql:Client mssqlClient = check new(user = dbUsername, password = dbPassword);
    _ = check mssqlClient->execute(`DROP DATABASE IF EXISTS EXAMPLE_DB`);
    _ = check mssqlClient->execute(`CREATE DATABASE EXAMPLE_DB`);
    _ = check mssqlClient.close();

    // Initializes the MSSQL client.
    mssql:Client dbClient = check new (user = dbUsername, password = dbPassword, database = dbName);

    // Creates a table in the database.
    sql:ExecutionResult result = check dbClient->execute(`DROP TABLE IF EXISTS Customers`);
    result = check dbClient->execute(`
        CREATE TABLE Customers (
            customerId INT NOT NULL IDENTITY PRIMARY KEY,
            firstName VARCHAR(300),
            lastName  VARCHAR(300),
            registrationID INT,
            creditLimit FLOAT,
            country  VARCHAR(300)
        );
    `);

    // Closes the MSSQL client.
    check dbClient.close();
}
