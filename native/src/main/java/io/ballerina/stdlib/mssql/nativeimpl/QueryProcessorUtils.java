/*
 * Copyright (c) 2021, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.ballerina.stdlib.mssql.nativeimpl;

import io.ballerina.runtime.api.values.BObject;
import io.ballerina.runtime.api.values.BStream;
import io.ballerina.runtime.api.values.BTypedesc;
import io.ballerina.stdlib.mssql.parameterprocessor.MssqlStatementParameterProcessor;
import io.ballerina.stdlib.sql.parameterprocessor.DefaultResultParameterProcessor;

/**
 * This class provides the methods for query processing which executes sql queries.
 * 
 * @since 0.1.0
 */
public class QueryProcessorUtils {
    private QueryProcessorUtils() {
    }

    public static BStream nativeQuery(BObject client, Object paramSQLString,
                                      BTypedesc recordType) {
        MssqlStatementParameterProcessor statementParametersProcessor = MssqlStatementParameterProcessor.getInstance();
        DefaultResultParameterProcessor resultParametersProcessor = DefaultResultParameterProcessor.getInstance();
        return io.ballerina.stdlib.sql.nativeimpl.QueryProcessor.nativeQuery(client, paramSQLString, recordType,
                statementParametersProcessor, resultParametersProcessor);
    }

    public static Object nativeQueryRow(BObject client, Object paramSQLString, BTypedesc recordType) {
        MssqlStatementParameterProcessor statementParametersProcessor = MssqlStatementParameterProcessor
                .getInstance();
        DefaultResultParameterProcessor resultParametersProcessor = DefaultResultParameterProcessor.getInstance();
        return io.ballerina.stdlib.sql.nativeimpl.QueryProcessor.nativeQueryRow(client, paramSQLString, recordType,
                statementParametersProcessor, resultParametersProcessor);
    }

}
