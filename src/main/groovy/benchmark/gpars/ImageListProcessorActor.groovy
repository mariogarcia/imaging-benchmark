package benchmark.gpars

import javax.imageio.ImageIO
import java.nio.file.Paths
import org.imgscalr.Scalr
import groovyx.gpars.actor.DefaultActor

/**
 *
 */
class ImageListProcessorActor extends DefaultActor {

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
                }
            }
        }
    }

}


