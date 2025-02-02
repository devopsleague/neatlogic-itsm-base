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
import neatlogic.framework.process.dto.ChannelTypeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IChannelTypeCrossoverMapper extends ICrossoverService {

    ChannelTypeVo getChannelTypeByUuid(String uuid);

    ChannelTypeVo getChannelTypeByName(String name);

    List<Long> getAuthorizedChannelTypeRelationIdListBySourceChannelUuid(
            @Param("source") String source,
            @Param("userUuid") String userUuid,
            @Param("teamUuidList") List<String> teamUuidList,
            @Param("roleUuidList") List<String> roleUuidList,
            @Param("processUserTypeList") List<String> processUserTypeList
    );

    List<String> getChannelUuidListByParentUuidListAndChannelTypeUuidList(
            @Param("parentUuidList") List<String> parentUuidList,
            @Param("channelTypeUuidList") List<String> channelTypeUuidList
    );

    int checkChannelTypeRelationIsExists(Long id);

    List<String> getChannelTypeRelationTargetListByChannelTypeRelationId(Long channelTypeRelationId);
}
