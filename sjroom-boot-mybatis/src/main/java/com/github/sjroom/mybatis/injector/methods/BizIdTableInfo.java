package com.github.sjroom.mybatis.injector.methods;

import com.baomidou.mybatisplus.core.metadata.TableInfo;

/**
 * 业务 id 的表信息，更改 isLogicDelete 为 true
 *
 * @author dream.lu
 */
public class BizIdTableInfo extends TableInfo {

    @Override
    public boolean isLogicDelete() {
        return true;
    }
}
