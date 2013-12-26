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

import groovyx.gpars.GParsPool
import org.imgscalr.Scalr
import java.nio.file.Path
import javax.imageio.ImageIO
import benchmark.model.ImageListProcessor

/**
 * Using asynchronous invocation of the cropping action.
 */
class ImageListProcessorAsync implements ImageListProcessor {

    Integer process(List<Path> images) {
        GParsPool.withPool {

            def futureImages = images.collect { img->
                def async = { image -> Scalr.crop(ImageIO.read(image.toFile()), 0, 5, 10, 20) }.async()
                def future = async(img)

                return future
            }

            return futureImages*.get().size()

        }
    }

}


