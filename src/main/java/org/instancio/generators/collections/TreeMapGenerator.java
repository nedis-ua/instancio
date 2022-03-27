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
package org.instancio.generators.collections;

import org.instancio.internal.model.ModelContext;

import java.util.Map;
import java.util.TreeMap;

public class TreeMapGenerator<K, V> extends MapGenerator<K, V> {

    public TreeMapGenerator(final ModelContext<?> context) {
        super(context);
        type(TreeMap.class);
    }

    @Override
    @SuppressWarnings("SortedCollectionWithNonComparableKeys")
    public Map<K, V> generate() {
        return random().diceRoll(nullable) ? null : new TreeMap<>();
    }
}