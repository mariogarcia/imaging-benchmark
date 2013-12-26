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


