package github.sjroom.core.junit;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;

/**
 * @Author: manson.zhou
 * @Date: 2019/6/26 17:29
 * @Desc:
 *
 * <pre>
 * 1、取消了@Autowired的required启动阶段强检查，同时spring bean的依赖关系可以好人mockito结合，
 * 2、注意空指针，是test案例问题，还是代码问题、
 * 3、测试demo方案见@link(http://gitlab.srv.sunvalley/dop/bc/dop-demo-order/tree/master/dop-demo-order-app/src/test/java/com/sunvalley/junit)
 * </pre>
 */
public class CustomAutowiredBeanPostProcessor extends AutowiredAnnotationBeanPostProcessor {



    @Override
    protected boolean determineRequiredStatus(AnnotationAttributes ann) {
        return false;    // 其他
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
