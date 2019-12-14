package com.sunvalley.framework.base.env;

import com.sunvalley.framework.core.utils.CharPool;
import com.sunvalley.framework.core.utils.INetUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 服务 ip 信息，启动之后几乎不会再变动
 *
 * @author L.cm
 */
@Slf4j
@Getter
public class ServerInfo implements SmartInitializingSingleton {

    @Setter
    private ServerProperties serverProperties;

    private String hostName;
    private String ip;
    private String ipSuffix;
    private Integer port;
    private String ipWithPort;

    @Override
    public void afterSingletonsInstantiated() {
        this.hostName = INetUtil.getHostName();
        this.ip = INetUtil.getHostIp();
        this.ipSuffix = getIpSuffix(this.ip);
        this.port = serverProperties.getPort();
        this.ipWithPort = String.format("%s:%d", this.ip, this.port);
    }

    private static String getIpSuffix(String ip) {
        String ipSuffix = ip.substring(ip.lastIndexOf(CharPool.DOT) + 1);
        if (ipSuffix.length() > 2) {
            return ipSuffix;
        }
        // ip 后缀小于 3 位的补零
        return String.format("%03d", Integer.valueOf(ipSuffix));
    }

}
