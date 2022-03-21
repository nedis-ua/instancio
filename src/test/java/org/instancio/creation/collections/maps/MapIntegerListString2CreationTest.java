package org.instancio.creation.collections.maps;

import org.instancio.testsupport.Constants;
import org.instancio.testsupport.tags.GenericsTag;
import org.instancio.testsupport.templates.CreationTestTemplate;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@GenericsTag
public class MapIntegerListString2CreationTest extends CreationTestTemplate<Map<Integer, List<String>>> {

    @Override
    protected void verify(Map<Integer, List<String>> result) {
        assertThat(result).hasSize(Constants.MAP_SIZE);

        assertThat(result.entrySet()).allSatisfy(entry -> {
            assertThat(entry.getKey()).isInstanceOf(Integer.class);
            assertThat(entry.getValue())
                    .hasSize(Constants.COLLECTION_SIZE)
                    .hasOnlyElementsOfType(String.class);
        });
    }

}