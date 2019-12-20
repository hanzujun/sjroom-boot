package github.sjroom.example.dao;

import github.sjroom.mybatis.mapper.IMapper;
import github.sjroom.example.bean.entity.Account;
import org.apache.ibatis.annotations.Mapper;

/**
 * <B>说明：</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0.
 * @date 2019-12-16 14:14
 */
@Mapper
public interface IAccountDao extends IMapper<Account> {
}
