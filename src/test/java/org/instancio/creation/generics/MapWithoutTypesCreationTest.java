/*
 * Copyright 2022 the original author or authors.
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
package org.instancio.creation.generics;

import org.instancio.pojo.generics.MapWithoutTypes;
import org.instancio.testsupport.Constants;
import org.instancio.testsupport.tags.GenericsTag;
import org.instancio.testsupport.templates.CreationTestTemplate;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@GenericsTag
public class MapWithoutTypesCreationTest extends CreationTestTemplate<MapWithoutTypes> {

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected void verify(MapWithoutTypes result) {
        final Map map = result.getMap();

        assertThat(map).hasSizeBetween(Constants.MIN_SIZE, Constants.MAX_SIZE);
        assertThat(map.keySet()).hasOnlyElementsOfType(Object.class);
        assertThat(map.values()).hasOnlyElementsOfType(Object.class);
    }

}
