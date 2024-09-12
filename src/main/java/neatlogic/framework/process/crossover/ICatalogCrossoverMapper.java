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

package neatlogic.framework.process.crossover;

import neatlogic.framework.crossover.ICrossoverService;
import neatlogic.framework.process.dto.CatalogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ICatalogCrossoverMapper extends ICrossoverService {

    CatalogVo getCatalogByUuid(String uuid);

    List<CatalogVo> getCatalogByName(String name);

    List<String> getUpwardUuidListByLftRht(@Param("lft") Integer lft, @Param("rht") Integer rht);

    List<String> getCatalogUuidListByLftRht(@Param("lft") Integer lft, @Param("rht")Integer rht);
    /**
     *
     * @Time:2020年7月7日
     * @Description: 根据左右编码查出目录及所有上级目录
     * @param lft 左编码
     * @param rht 右编码
     * @return List<CatalogVo>
     */
    List<CatalogVo> getAncestorsAndSelfByLftRht(@Param("lft") Integer lft, @Param("rht") Integer rht);

}
