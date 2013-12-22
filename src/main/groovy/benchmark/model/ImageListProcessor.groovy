package benchmark.model

import java.nio.file.Path
/**
 *
 */
interface ImageListProcessor {

    abstract Integer process(List<Path> imagePathList)

}


