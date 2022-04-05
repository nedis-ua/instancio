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
package org.instancio.nodes;

import org.instancio.internal.nodes.CollectionNode;
import org.instancio.internal.nodes.Node;
import org.instancio.testsupport.templates.NodeTestTemplate;

import java.time.LocalDateTime;
import java.util.List;

import static org.instancio.testsupport.asserts.NodeAssert.assertNode;

public class ListLocalDateTimeNodeTest extends NodeTestTemplate<List<LocalDateTime>> {

    @Override
    protected void verify(final Node rootNode) {
        final CollectionNode listNode = assertNode(rootNode)
                .hasTargetClass(List.class)
                .hasTypeMappedTo(List.class, "E", LocalDateTime.class)
                .hasNoChildren()
                .getAs(CollectionNode.class);

        assertNode(listNode.getElementNode())
                .hasParent(listNode)
                .hasNullField()
                .hasTargetClass(LocalDateTime.class)
                .hasGenericType(null)
                .hasEmptyTypeMap();
    }
}