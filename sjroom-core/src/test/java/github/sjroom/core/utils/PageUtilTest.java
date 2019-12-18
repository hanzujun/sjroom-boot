package github.sjroom.core.utils;

import github.sjroom.core.page.PageUtil;
import org.junit.Assert;
import org.junit.Test;


public class PageUtilTest {

    /**
     * sql 注入测试
     *
     * http://securityidiots.com/Web-Pentest/SQL-Injection/group-by-and-order-by-sql-injection.html
     */
    @Test
    public void testFilter() {
        String sql = PageUtil.filter("select 1;id`,extractvalue(0x0a,concat(0x0a,(select database())))#");
        System.out.println(sql);
        Assert.assertFalse(sql.contains(","));
    }
}
