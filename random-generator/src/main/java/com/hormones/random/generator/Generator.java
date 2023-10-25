package com.hormones.random.generator;

import com.hormones.random.enums.Type;

public interface Generator {

    void generate();

    Type type();
}
