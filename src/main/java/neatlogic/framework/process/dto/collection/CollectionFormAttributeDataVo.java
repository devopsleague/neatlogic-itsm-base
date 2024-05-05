/*
 * Copyright (C) 2024  深圳极向量科技有限公司 All Rights Reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package neatlogic.framework.process.dto.collection;

import neatlogic.framework.form.dto.AttributeDataVo;

public class CollectionFormAttributeDataVo extends AttributeDataVo {
    private Long collectionId;

    public CollectionFormAttributeDataVo() {
    }

    public CollectionFormAttributeDataVo(Long collectionId, AttributeDataVo attributeDataVo) {
        this.collectionId = collectionId;
        this.setId(attributeDataVo.getId());
        this.setFormUuid(attributeDataVo.getFormUuid());
        this.setAttributeUuid(attributeDataVo.getAttributeUuid());
        this.setAttributeLabel(attributeDataVo.getAttributeLabel());
        this.setHandler(attributeDataVo.getHandler());
        this.setData(attributeDataVo.getData());
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }

}
