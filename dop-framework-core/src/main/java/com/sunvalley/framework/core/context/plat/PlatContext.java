package com.sunvalley.framework.core.context.plat;

import com.sunvalley.framework.core.code.LanguageEnum;
import lombok.Data;

/**
 * 访问请求的上下文
 *
 * @Auther: smj
 * @Date: 2018/10/15 08:57
 * @Version 1.8
 * @Description:
 */
@Data
public class PlatContext {

    private String locale;
    private LanguageEnum language;

    private String authToken;
    private Long accountId;
    private String clientIp;

	private String clientUa;

//    private String clientApp;
//    private String clientPlat;
//    private String clientSerial;

    private Long tenantId;

}
