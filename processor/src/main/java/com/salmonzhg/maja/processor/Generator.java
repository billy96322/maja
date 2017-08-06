package com.salmonzhg.maja.processor;

import javax.annotation.processing.ProcessingEnvironment;
import java.io.IOException;

/**
 * author: Salmon
 * date: 2017-08-02 17:37
 * github: https://github.com/billy96322
 * email: salmonzhg@foxmail.com
 */
public interface Generator {

    void generate(ProcessingEnvironment processingEnvironment) throws IOException;
}
