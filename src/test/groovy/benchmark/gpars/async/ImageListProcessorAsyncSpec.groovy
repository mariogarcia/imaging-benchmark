package benchmark.gpars.async

import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.Files
import spock.lang.Specification

/**
 *
 */
class ImageListProcessorAsyncSpec extends Specification {

    static final Path SAMPLE_FILE = Paths.get('src/test/resources/image-to-scale.jpg')

    def 'Executing several long processes asynchronously'() {
        given: 'A list of images'
            def listOfImages = generateSampleImages()
        when: 'Processing all of them'
            def successfullyProcessedImages =
                new ImageListProcessorAsync().process(listOfImages)
        then: 'We should get all the results eventually'
            successfullyProcessedImages == listOfImages.size()
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


