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
package org.instancio.settings;

import javax.annotation.Nullable;
import java.util.Objects;

public final class Key implements SettingKey {

    private final String propertyKey;
    private final Class<?> type;
    private final Object defaultValue;
    private final RangeAdjuster rangeAdjuster;

    Key(final String propertyKey, final Class<?> type, final Object defaultValue, @Nullable final RangeAdjuster rangeAdjuster) {
        this.propertyKey = propertyKey;
        this.type = type;
        this.defaultValue = defaultValue;
        this.rangeAdjuster = rangeAdjuster;
    }

    public String propertyKey() {
        return propertyKey;
    }

    @SuppressWarnings("unchecked")
    public <T> Class<T> type() {
        return (Class<T>) type;
    }

    @SuppressWarnings("unchecked")
    public <T> T defaultValue() {
        return (T) defaultValue;
    }

    @Override
    public void autoAdjust(final Settings settings, final Object otherValue) {
        if (rangeAdjuster != null) {
            rangeAdjuster.adjustRange(settings, this, otherValue);
        }
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (!(o instanceof Key)) return false;
        return propertyKey.equals(((SettingKey) o).propertyKey());
    }

    @Override
    public int hashCode() {
        return Objects.hash(propertyKey);
    }

    @Override
    public int compareTo(final SettingKey o) {
        return propertyKey.compareTo(o.propertyKey());
    }

    @Override
    public String toString() {
        return propertyKey;
    }
}