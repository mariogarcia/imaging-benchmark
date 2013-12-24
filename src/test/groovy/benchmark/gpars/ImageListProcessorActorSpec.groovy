package benchmark.gpars

import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.Files
import spock.lang.Specification

/**
 * ImageListProcessorActorSpec.groovy
 *
 */
@SuppressWarnings('VariableName')
class ImageListProcessorActorSpec extends Specification {

    static final Path SAMPLE_FILE = Paths.get('src/test/resources/image-to-scale.jpg')

    def 'Executing several long processes asynchronously'() {
        given: 'A list of images'
            def listOfImages = generateSampleImages()
            def processor = new ImageListProcessorActor().start()
        when: 'Processing all of them'
            listOfImages.each { image ->
                processor << image.toFile().absolutePath
            }
            processor.send 'stop'
            processor.join()
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


