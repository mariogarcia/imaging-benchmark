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

import groovyx.gbench.Benchmark
import spock.lang.Specification

import java.awt.Image
import java.awt.Toolkit

import java.awt.image.CropImageFilter
import java.awt.image.FilteredImageSource

import org.imgscalr.Scalr

/**
 * This specification crops an image using several imaging java libraries
 *
 * IMGS: imgscalr (https://github.com/thebuzzmedia/imgscalr)
 * JAVA: Regular JDK classes
 *
 * @author Mario Garcia
 *
 */
class CropSpec extends Specification {

    static final File SAMPLE_FILE = new File('src/test/resources/image-to-scale.jpg')
    static final Map CROP_DATA = [ x:80, y:90, height: 100, width: 200]

    @Benchmark
    void 'IMGS: Simple croping'() {
        given: 'An image'
            BufferedImage source = ImageIO.read(SAMPLE_FILE)
        when:'Scaling the image'
            BufferedImage result =
                Scalr.crop(source, CROP_DATA.x, CROP_DATA.y, CROP_DATA.width, CROP_DATA.height)
        then: 'The cropped image has the required dimensions'
            assertThat result.height, is(CROP_DATA.height)
            assertThat result.width, is(CROP_DATA.width)
    }

    @Benchmark
    void 'JAVA: Simple croping'() {
        given: 'An image'
            BufferedImage source = ImageIO.read(SAMPLE_FILE)
            CropImageFilter crop =
                new CropImageFilter(CROP_DATA.x, CROP_DATA.y, CROP_DATA.width, CROP_DATA.height)
            FilteredImageSource filteredImage = new FilteredImageSource(source.getSource(), crop)
        when:'Croping the image'
            Image result = Toolkit.defaultToolkit.createImage(filteredImage)
        then: 'The cropped image has the required dimensions'
            assertThat result.height, is(CROP_DATA.height)
            assertThat result.width, is(CROP_DATA.width)
    }
}
