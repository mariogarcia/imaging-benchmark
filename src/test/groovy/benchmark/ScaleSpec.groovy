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

import static org.junit.Assert.assertThat
import static org.hamcrest.CoreMatchers.is

import java.awt.image.BufferedImage
import javax.imageio.ImageIO

import spock.lang.Specification
import groovyx.gbench.Benchmark

import org.imgscalr.Scalr
import com.mortennobel.imagescaling.ResampleOp

/**
 * This specification scales an image using several imaging java libraries
 *
 * IMGS: imgscalr (https://github.com/thebuzzmedia/imgscalr)
 * JISC: java-image-scaling (http://code.google.com/p/java-image-scaling/)
 *
 * @author Mario Garcia
 *
 */
class ScaleSpec extends Specification {

    static final File SAMPLE_FILE = new File('src/test/resources/image-to-scale.jpg')
    static final Map SCALE_DATA = [ height: 150, width: 300 ]

    @Benchmark
    void 'IMGS: Simple scaling'() {
        given: 'An image'
            BufferedImage source = ImageIO.read(SAMPLE_FILE)
        when:'Scaling the image'
            BufferedImage thumbnail =
                Scalr.resize(source, Scalr.Method.SPEED, Scalr.Mode.FIT_EXACT, SCALE_DATA.width, SCALE_DATA.height)
        then: 'The transformed image has the required dimensions'
            assertThat thumbnail.height, is(SCALE_DATA.height)
    }

    @Benchmark
    void 'JISC: Simple scaling'() {
        given: 'An image'
            BufferedImage source = ImageIO.read(SAMPLE_FILE)
        when:'Scaling the image'
            ResampleOp operation = new ResampleOp(SCALE_DATA.width, SCALE_DATA.height)
            BufferedImage thumbnail = operation.filter(source, null)
        then: 'The transformed image has the required dimensions'
            assertThat thumbnail.height, is(SCALE_DATA.height)
    }

}
