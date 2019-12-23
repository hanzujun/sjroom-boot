package github.sjroom.example.service;

import github.sjroom.example.bean.entity.Account;
import github.sjroom.mybatis.service.BaseService;

import java.util.HashSet;
import java.util.Map;

/**
 * <B>说明：服务</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0
 * @since 2019-12-16 14:14
 */
public interface IAccountService extends BaseService<Account> {

    Map<Integer, String> mapStatus();
}
