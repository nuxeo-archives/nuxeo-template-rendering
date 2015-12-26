/*
 * (C) Copyright 2012 Nuxeo SA (http://nuxeo.com/) and others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Contributors:
 *     Thierry Delprat
 */
package org.nuxeo.template.context;

import org.nuxeo.ecm.core.api.DocumentModel;
import org.nuxeo.ecm.core.api.model.Property;
import org.nuxeo.ecm.platform.rendering.fm.adapters.SchemaTemplate;

public class SimpleSchemaWrapper {

    private final DocumentModel doc;

    private final String schemaName;

    public SimpleSchemaWrapper(SchemaTemplate.DocumentSchema schema) {
        this.doc = schema.doc;
        this.schemaName = schema.schemaName;
    }

    public Object get(String name) {
        if (doc.isPrefetched(schemaName, name)) {
            // simple value already available, don't load DocumentPart
            return doc.getProperty(schemaName, name);
        } else {
            // use normal Property lookup in Part
            return wrap(doc.getPart(schemaName).get(name));
        }
    }

    protected Object wrap(Property prop) {
        if (prop == null || prop.getValue() == null) {
            return null;
        }
        return prop.getValue();
    }

}
