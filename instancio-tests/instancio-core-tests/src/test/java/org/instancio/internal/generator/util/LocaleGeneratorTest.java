/*
 * Copyright 2022-2023 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.instancio.internal.generator.util;

import org.instancio.Random;
import org.instancio.generator.GeneratorContext;
import org.instancio.settings.Settings;
import org.instancio.support.DefaultRandom;
import org.instancio.test.support.tags.NonDeterministicTag;
import org.junit.jupiter.api.Test;

import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.data.Percentage.withPercentage;

@NonDeterministicTag
class LocaleGeneratorTest {

    private static final int SAMPLE_SIZE = 7000;
    private static final Random random = new DefaultRandom();
    private static final GeneratorContext context = new GeneratorContext(Settings.defaults(), random);
    private final LocaleGenerator generator = new LocaleGenerator(context);

    @Test
    void locale() {
        final Set<Locale> results = IntStream.range(0, SAMPLE_SIZE)
                .boxed()
                .map(it -> generator.generate(random))
                .collect(Collectors.toSet());

        assertThat(results.size())
                .isCloseTo(Locale.getAvailableLocales().length, withPercentage(5));
    }
}
