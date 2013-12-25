package benchmark.gpars

import javax.imageio.ImageIO
import java.nio.file.Paths
import org.imgscalr.Scalr
import groovyx.gpars.actor.DefaultActor
import groovyx.gpars.agent.Agent

/**
 *
 */
class ImageListProcessorActor extends DefaultActor {

    Agent agent

    ImageListProcessorActor(Agent agent) {
        this.agent = agent
    }

    void act() {
        loop {
            react { imageToProcess ->
                if (imageToProcess == 'stop') {
                    stop()
                } else {
                    agent.send { increment() }
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


