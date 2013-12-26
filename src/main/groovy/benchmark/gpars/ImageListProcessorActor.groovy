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


