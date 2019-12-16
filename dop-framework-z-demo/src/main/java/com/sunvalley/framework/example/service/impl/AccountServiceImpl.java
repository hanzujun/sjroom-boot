package com.sunvalley.framework.example.service.impl;

import com.sunvalley.framework.example.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import com.sunvalley.framework.example.bean.entity.Account;
import com.sunvalley.framework.example.dao.IAccountDao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import com.sunvalley.framework.mybatis.service.impl.BaseServiceImpl;

/**
 * <B>说明：服务实现</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0
 * @since 2019-12-16 14:14
 */
@Slf4j
@Service
@Validated
public class AccountServiceImpl extends BaseServiceImpl<IAccountDao, Account> implements IAccountService {
    @Autowired
    private IAccountDao accountDao;

}

