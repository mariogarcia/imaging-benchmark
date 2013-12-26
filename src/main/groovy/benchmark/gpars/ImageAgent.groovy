package benchmark.gpars

import groovyx.gpars.agent.Agent

/**
 * This agent only counts how many images have been
 * processed by the actor
 */
class ImageAgent extends Agent<Integer> {

    ImageAgent() {
        super(0)
    }

    def imageAdded() {
        data += 1
    }

}


