package github.sjroom.common.util;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import lombok.experimental.UtilityClass;

/**
 * 业务id 生成工具
 *
 * @author manson.zhou
 */
@UtilityClass
public class UtilId {

    /**
     * 生成业务id
     *
     * @return 业务id
     */
    public static Long getBId() {
        return IdWorker.getId();
    }

    /**
     * 生成业务id
     *
     * @return 业务id
     */
    public static String getBIdStr() {
        return String.valueOf(IdWorker.getId());
    }
}
