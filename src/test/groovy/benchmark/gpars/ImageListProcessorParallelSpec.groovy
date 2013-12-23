package benchmark.gpars

import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.Files
import spock.lang.Specification

/**
 *
 */
class ImageListProcessorParallelSpec extends Specification {

    static final Path SAMPLE_FILE = Paths.get('src/test/resources/image-to-scale.jpg')

    def 'Executing several long processes in parallel'() {
        given: 'A list of images'
            def listOfImages = generateSampleImages()
        when: 'Processing all of them'
            def processor = new ImageListProcessorParallel()
            def processedImages = processor.process(listOfImages)
        then: 'We should get all the results eventually'
            processedImages == listOfImages.size()
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


