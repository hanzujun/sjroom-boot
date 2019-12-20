package github.sjroom.common.context.plat;

import lombok.Data;

/**
 * 访问请求的上下文
 *
 * @Auther: manson.zhou
 * @Date: 2018/10/15 08:57
 * @Version 1.8
 * @Description:
 */
@Data
public class PlatContext {



    private String requestId;
    private String token;


    private Long accountId;
    private String clientIp;
	private String clientUa;

}
