/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package benchmark

import groovy.util.logging.Log

/**
 * This closure checks Im4java is not available. This closure
 * will be used by Spock's @IgnoreIf annotation to ignore
 * those tests having to do with this library.
 *
 * @author Mario Garcia
 */
@Log
class Im4JavaNotAvailable extends Closure<Boolean> {

    static final Integer ABNORMAL_TERMINATION = 1

    Im4JavaNotAvailable(Object owner, Object delegate) {
        super(owner, delegate)
    }

    Boolean doCall() {

        def result = ABNORMAL_TERMINATION

        try {

            def process = ["convert","--version"].execute()
            def out = new StringBuilder()
            def err = new StringBuilder()

            process.waitForProcessOutput(out, err)
            result = process.exitValue()

        } catch (e) {
            log.info(e.message)
        }

     /* If 0 indicates normal termination, in this case
        Im4java is not available if the method returns
        true */
        return result == 0 ? false : true

    }

}
