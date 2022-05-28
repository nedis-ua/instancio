/*
 *  Copyright 2022 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.instancio.internal.nodes;

import org.instancio.internal.context.SubtypeSelectorMap;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.lang.reflect.TypeVariable;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;

public final class NodeContext {

    private final Map<TypeVariable<?>, Class<?>> rootTypeMap;
    private final SubtypeSelectorMap subtypeSelectorMap;

    public NodeContext(
            final Map<TypeVariable<?>, Class<?>> rootTypeMap,
            final SubtypeSelectorMap subtypeSelectorMap) {

        this.rootTypeMap = Collections.unmodifiableMap(rootTypeMap);
        this.subtypeSelectorMap = subtypeSelectorMap;
    }

    public Map<TypeVariable<?>, Class<?>> getRootTypeMap() {
        return rootTypeMap;
    }

    Optional<Class<?>> getUserSuppliedSubtype(final Class<?> targetClass, @Nullable final Field field) {
        return subtypeSelectorMap.getUserSuppliedSubtype(targetClass, field);
    }
}
