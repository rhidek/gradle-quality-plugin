package com.reachlocal

class QualityExtension {
    CheckStyleConfig checkStyleConfig = new CheckStyleConfig()
    PmdConfig pmdConfig = new PmdConfig()

    void checkStyle(Closure closure){
        closure.delegate = checkStyleConfig
        closure()
    }

    void pmd(Closure closure){
        closure.delegate = pmdConfig
        closure()
    }
}
