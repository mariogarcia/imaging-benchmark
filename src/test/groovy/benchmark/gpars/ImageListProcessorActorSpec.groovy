package benchmark.gpars

import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.Files
import spock.lang.Specification

/**
 *
 */
@SuppressWarnings('VariableName')
class ImageListProcessorActorSpec extends Specification {

    static final Path SAMPLE_FILE = Paths.get('src/test/resources/image-to-scale.jpg')

    def 'Executing several long processes asynchronously'() {
        given: 'A list of images'
            def listOfImages = generateSampleImages()
            def agent = new ImageAgent()
            def processor = new ImageListProcessorActor(agent).start()
        when: 'Processing all of them'
            listOfImages.each { image ->
                processor << image.toFile().absolutePath
            }
            processor.send 'stop'
            processor.join()
        then: 'The actor should have processed a hundred images'
            agent.val == 100
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


