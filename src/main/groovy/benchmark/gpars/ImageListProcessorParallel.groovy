package benchmark.gpars

import groovyx.gpars.GParsPool
import org.imgscalr.Scalr
import java.nio.file.Path
import javax.imageio.ImageIO
import benchmark.model.ImageListProcessor

/**
 * Parallel execution of the cropping action
 */
class ImageListProcessorParallel implements ImageListProcessor {

    Integer process(List<Path> images) {
        GParsPool.withPool {
            images.collectParallel { img->
                Scalr.crop(ImageIO.read(img.toFile()), 0, 5, 10, 20)
            }.size()
        }
    }

}
