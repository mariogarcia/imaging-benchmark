package benchmark.gpars

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
                println 'receiving ' + imageToProcess
                Scalr.crop(
                    ImageIO.read(
                        Paths.get(imageToProcess)
                    ),
                    0,
                    5,
                    10,
                    20)
            }
        }
    }

}


