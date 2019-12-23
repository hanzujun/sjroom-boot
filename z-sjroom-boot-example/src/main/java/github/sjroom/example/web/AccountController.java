package github.sjroom.example.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import github.sjroom.common.code.ApiCoreCode;
import github.sjroom.common.util.AssertUtil;
import github.sjroom.common.util.BeanUtil;
import github.sjroom.common.R;
import github.sjroom.example.bean.entity.Account;
import github.sjroom.example.bean.vo.*;
import github.sjroom.example.service.IAccountService;
import github.sjroom.example.service.IAccountServiceComp;
import github.sjroom.mybatis.annotation.FillField;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <B>说明： 控制器</B><BR>
 *
 * @author manson.zhou
 * @version 1.0.0
 * @since 2019-12-16 14:14
 */
@Slf4j
@Validated
@RestController
@RequestMapping("api/account")
@Api(" 控制器")
public class AccountController {
    @Autowired
    private IAccountService accountService;
    @Autowired
    private IAccountServiceComp accountServiceComp;

    @ApiOperation(value = "查看详情", notes = "传入id")
    @GetMapping("find")
    public AccountRespVo find(@RequestBody @Validated Integer id) {
        return new AccountRespVo();
    }

    @ApiOperation("查看分页")
    @PostMapping("list")
    @FillField
    public List<AccountRespVo> page(@RequestBody AccountPageReqVo reqVo) {
        return BeanUtil.copy(accountService.list(), AccountRespVo.class);
    }

    @ApiOperation("创建")
    @PostMapping("add")
    public Long add(@RequestBody @Validated AccountReqVo accountReqVo) {
        Account account = BeanUtil.copy(accountReqVo, Account.class);
        accountService.save(account);
        return account.getAccountId();
    }

    @ApiOperation("更新")
    @PostMapping("modify")
    public void modify(@RequestBody @Validated AccountReqVo accountReqVo) {

        List<Account> accounts = accountService.list();
        Set<Long> accountIds = accounts.stream().map(Account::getAccountId).collect(Collectors.toSet());
        Account account = new Account();
        account.setStatus(0);
        account.setCreatedBy(1l);
        accountService.updateBatchByBIds(account, accountIds);
    }


}
