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

import io.ballerina.runtime.api.values.BArray;
import io.ballerina.runtime.api.values.BObject;
import io.ballerina.stdlib.mssql.parameterprocessor.MssqlStatementParameterProcessor;
import io.ballerina.stdlib.sql.parameterprocessor.DefaultResultParameterProcessor;

/**
 * This class holds the utility methods involved with executing the call statements.
 *
 * @since 0.1.0
 */
public class CallProcessorUtils { 
    private CallProcessorUtils() { 
    }
    
    public static Object nativeCall(BObject client, Object paramSQLString, BArray recordTypes) {
        return io.ballerina.stdlib.sql.nativeimpl.CallProcessor.nativeCall(client, paramSQLString,
            recordTypes, MssqlStatementParameterProcessor.getInstance(),
            DefaultResultParameterProcessor.getInstance());
    }
}
