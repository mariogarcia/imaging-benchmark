package benchmark.gpars.async

import groovyx.gpars.GParsPool
import java.awt.image.BufferedImage
import benchmark.model.ImageListProcessor

/**
 *
 */
class ImageListProcessAsync implements ImageListProcessor {

    @SuppressWarnings('UnusedMethodParameter')
    void process(List<BufferedImage> images) {
        GParsPool.withPool() {


        }
    }

}


