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
package org.example.spi;

import org.example.generator.CustomIntegerGenerator;
import org.instancio.Gen;
import org.instancio.Node;
import org.instancio.Random;
import org.instancio.generator.AfterGenerate;
import org.instancio.generator.Generator;
import org.instancio.generator.GeneratorSpec;
import org.instancio.generator.Hints;
import org.instancio.generators.Generators;
import org.instancio.internal.nodes.NodeImpl;
import org.instancio.spi.InstancioServiceProvider;
import org.instancio.test.support.pojo.person.Address;
import org.instancio.test.support.pojo.person.PersonName;
import org.instancio.test.support.pojo.person.Phone;
import org.instancio.test.support.pojo.person.PhoneWithType;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class CustomGeneratorProvider implements InstancioServiceProvider {

    public static final String STRING_GENERATOR_VALUE = "overridden string generator from SPI Generator!";
    public static final Pattern PATTERN_GENERATOR_VALUE = Pattern.compile("baz");

    private static final Map<Class<?>, GeneratorSpec<?>> GENERATOR_MAP = new HashMap<Class<?>, GeneratorSpec<?>>() {{
        put(String.class, new StringGeneratorFromSpi());
        put(Pattern.class, new PatternGeneratorFromSpi());
        put(int.class, new CustomIntegerGenerator());
        put(Integer.class, new CustomIntegerGenerator());
        put(Address.class, new CustomAddressGenerator());
        put(Phone.class, new CustomPhoneGenerator());

        // Error-handling: generator spec does not implement the Generator interface
        put(Float.class, new CustomFloatSpec());
    }};

    private final GeneratorProvider generatorProvider = new GeneratorProviderImpl();

    private boolean getGeneratorProviderInvoked;

    @Override
    public GeneratorProvider getGeneratorProvider() {
        if (getGeneratorProviderInvoked) {
            throw new AssertionError("getGeneratorProvider() should not be invoked more than once!");
        }

        getGeneratorProviderInvoked = true;
        return generatorProvider;
    }

    private static class GeneratorProviderImpl implements GeneratorProvider {
        @Override
        public GeneratorSpec<?> getGenerator(final Node node, final Generators generators) {
            if (node.getClass() != NodeImpl.class) {
                // Verify that InternalNode implementation is not exposed via the SPI
                throw new AssertionError("Unexpected Node implementation: " + node.getClass());
            }

            if (node.getField() != null) {
                // Set string length based on annotation attributes
                final PersonName name = node.getField().getAnnotation(PersonName.class);
                if (name != null) {
                    return generators.string()
                            .minLength(name.min())
                            .maxLength(name.max());
                }
            }

            // verify: subtype(all(Phone.class),  PhoneWithType.class)
            if (node.getTargetClass() == PhoneWithType.class) {
                return new PhoneWithTypeGenerator();
            }
            return GENERATOR_MAP.get(node.getTargetClass());
        }
    }

    private static class StringGeneratorFromSpi implements org.instancio.generator.Generator<String> {
        @Override
        public String generate(final Random random) {
            return STRING_GENERATOR_VALUE;
        }
    }

    private static class PatternGeneratorFromSpi implements Generator<Pattern> {
        @Override
        public Pattern generate(final Random random) {
            return PATTERN_GENERATOR_VALUE;
        }
    }

    public static class CustomAddressGenerator implements Generator<Address> {
        public static final String COUNTRY = "foo";

        @Override
        public Address generate(final Random random) {
            return Address.builder().country(COUNTRY).build();
        }

        @Override
        public Hints hints() {
            return Hints.afterGenerate(AfterGenerate.APPLY_SELECTORS);
        }
    }

    public static class CustomPhoneGenerator implements Generator<Phone> {
        public static final String NUMBER = Gen.string().digits().get();

        @Override
        public Phone generate(final Random random) {
            return Phone.builder().number(NUMBER).build();
        }
    }

    public static class PhoneWithTypeGenerator implements Generator<PhoneWithType> {
        public static final String NUMBER = Gen.string().digits().get();

        @Override
        public PhoneWithType generate(final Random random) {
            return PhoneWithType.builder().number(NUMBER).build();
        }
    }

    public static class CustomFloatSpec implements GeneratorSpec<Float> {}
}