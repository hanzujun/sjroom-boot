package com.sunvalley.framework.base.code;

import com.sunvalley.framework.base.env.DopProperties;
import com.sunvalley.framework.base.env.ServerInfo;
import com.sunvalley.framework.core.utils.StringUtil;
import org.springframework.context.i18n.LocaleContextHolder;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Optional;

/**
 * @author Katrel.Zhou
 * @date 2019/6/10 18:33
 */
public class SimpleCodeTranslator extends CodeTranslator {

    public SimpleCodeTranslator(ServerInfo serverInfo, DopProperties properties) {
        super(serverInfo, properties);
    }

    @Override
    public String doTranslation(String code, String message, Object[] args) {
                return Optional.ofNullable(message).map(m -> {
            Locale locale = LocaleContextHolder.getLocale();
            MessageFormat format = new MessageFormat(m, locale);
            return format.format(args);
        }).orElse(null);
    }


}
