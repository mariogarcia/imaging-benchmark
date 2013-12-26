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
package benchmark.gpars

import javax.imageio.ImageIO
import java.nio.file.Paths
import org.imgscalr.Scalr
import groovyx.gpars.actor.DefaultActor

/**
 * This actor crops every image that is sent to him. It crops the
 * image but it doesnt persist it, so at the moment is useless
 */
class ImageListProcessorActor extends DefaultActor {

    ImageAgent agent

    ImageListProcessorActor(ImageAgent agent) {
        this.agent = agent
    }

    void act() {
        loop {
            react { imageToProcess ->
                if (imageToProcess == 'stop') {
                    stop()
                } else {
                    Scalr.crop(
                        ImageIO.read(
                            Paths.get(imageToProcess).toFile()
                        ),
                        0,
                        5,
                        10,
                        20
                    )
                    agent.imageAdded()
                }
            }
        }
    }

}


