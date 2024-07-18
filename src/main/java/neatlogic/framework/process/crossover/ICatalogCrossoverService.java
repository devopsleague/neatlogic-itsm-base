package neatlogic.framework.process.crossover;

import neatlogic.framework.crossover.ICrossoverService;
import neatlogic.framework.process.constvalue.CatalogChannelAuthorityAction;

/**
 * @author longrf
 * @date 2022/1/14 4:27 下午
 */
public interface ICatalogCrossoverService extends ICrossoverService {
    /**
     * @param channelUuid
     * @param userUuid    用户uuid
     * @param action      授权类型
     * @return boolean
     * @Time:2020年7月7日
     * @Description: 判断当前用户是否有channelUuid服务的action权限，根据服务是否激活，服务是否授权，服务的所有上级目录是否都授权来判断
     */
    public boolean channelIsAuthority(String channelUuid, String userUuid, CatalogChannelAuthorityAction action);
}
