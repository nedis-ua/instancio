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
package org.instancio.generator.specs;

import org.instancio.generator.AsStringGeneratorSpec;
import org.instancio.generator.Generator;

import java.net.URI;

/**
 * Generator spec for {@link URI} values that supports {@link AsGeneratorSpec}.
 *
 * @since 2.6.0
 */
public interface URIAsGeneratorSpec
        extends URIGeneratorSpec, AsGeneratorSpec<URI>, AsStringGeneratorSpec<URI> {

    @Override
    URIAsGeneratorSpec scheme(String... schemes);

    @Override
    URIAsGeneratorSpec userInfo(String userInfo);

    @Override
    URIAsGeneratorSpec host(Generator<String> hostGenerator);

    @Override
    URIAsGeneratorSpec port(int port);

    @Override
    URIAsGeneratorSpec randomPort();

    @Override
    URIAsGeneratorSpec path(Generator<String> pathGenerator);

    @Override
    URIAsGeneratorSpec query(Generator<String> queryGenerator);

    @Override
    URIAsGeneratorSpec fragment(Generator<String> fragmentGenerator);
}
