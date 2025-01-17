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
import org.instancio.exception.InstancioApiException;
import org.instancio.generator.GeneratorContext;
import org.instancio.settings.Settings;
import org.instancio.support.DefaultRandom;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CalendarGeneratorTest {

    private static final Settings settings = Settings.create();
    private static final Random random = new DefaultRandom();
    private static final GeneratorContext context = new GeneratorContext(settings, random);
    private static final ZonedDateTime START = ZonedDateTime.of(
            LocalDateTime.of(1970, 1, 1, 0, 0, 1, 999999999),
            OffsetDateTime.now().getOffset());

    private final CalendarGenerator generator = new CalendarGenerator(context);

    @Test
    void apiMethod() {
        assertThat(generator.apiMethod()).isEqualTo("calendar()");
    }

    @Test
    void smallestAllowedRange() {
        final GregorianCalendar calendar = GregorianCalendar.from(START);
        generator.range(calendar, calendar);
        assertThat(generator.generate(random).getTimeInMillis()).isEqualTo(calendar.getTimeInMillis());
    }

    @Test
    void past() {
        generator.past();
        assertThat(generator.generate(random).toInstant()).isBefore(Instant.now());
    }

    @Test
    void future() {
        generator.future();
        assertThat(generator.generate(random).toInstant()).isAfter(Instant.now());
    }

    @Test
    void validateRange() {
        final Calendar min = GregorianCalendar.from(START);
        final Calendar max = GregorianCalendar.from(START.minusNanos(1000000));

        assertThatThrownBy(() -> generator.range(min, max))
                .isExactlyInstanceOf(InstancioApiException.class)
                .hasMessageContaining("Start must not exceed end");
    }

    @Test
    void range() {
        final Calendar min = GregorianCalendar.from(START);
        final Calendar max = GregorianCalendar.from(START.plusSeconds(10));
        generator.range(min, max);
        assertThat(generator.generate(random)).isBetween(min, max);
    }

    @Test
    void nullable() {
        generator.nullable();
        assertThat(Stream.generate(() -> generator.generate(random)).limit(500))
                .containsNull();
    }
}
