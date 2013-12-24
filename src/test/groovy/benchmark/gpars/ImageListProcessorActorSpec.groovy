package benchmark.gpars

import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.Files
import spock.lang.Specification
import groovyx.gpars.actor.Actors

/**
 * ImageListProcessorActorSpec.groovy
 *
 */
@SuppressWarnings('VariableName')
class ImageListProcessorActorSpec extends Specification {

    static final Path SAMPLE_FILE = Paths.get('src/test/resources/image-to-scale.jpg')

    def 'Executing several long processes asynchronously'() {
        given: 'A list of images'
            final listOfImages = generateSampleImages()
        when: 'Processing all of them'
            final processor = new ImageListProcessorActor()
            def feeder = Actors.actor {
                        processor << listOfImages.first().toFile().absolutePath
                    println 'stopping'
                    stop()
            }

            [processor, feeder]*.join()
        then: 'We should get all the results eventually'
            true
    }

    def generateSampleImages() {

        def collector = { Long seed, Integer index ->
            Files.copy(
                SAMPLE_FILE,
                Paths.get(System.getProperty('java.io.tmpdir')).
                    resolve("${seed}_${index}")
            )
        }.curry(new Date().time)

        return (1..100).collect(collector)

    }
}


