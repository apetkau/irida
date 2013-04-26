/*
 * Copyright 2013 Franklin Bristow <franklin.bristow@phac-aspc.gc.ca>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ca.corefacility.bioinformatics.irida.web.controller.test.unit.support;

import ca.corefacility.bioinformatics.irida.web.assembler.resource.Resource;

/**
 *
 * @author Franklin Bristow <franklin.bristow@phac-aspc.gc.ca>
 */
public class IdentifiableTestResource extends Resource<IdentifiableTestEntity> {

    public IdentifiableTestResource() {
        super(new IdentifiableTestEntity());
    }

    public IdentifiableTestResource(IdentifiableTestEntity e) {
        super(e);
    }

    public String getNonNull() {
        return resource.getNonNull();
    }

    public void setNonNull(String nonNull) {
        this.resource.setNonNull(nonNull);
    }
}
