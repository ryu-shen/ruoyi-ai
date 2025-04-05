package org.dromara.langchain.common;

import org.dromara.langchain.common.config.AuthProps;
import org.dromara.langchain.common.config.ChatProps;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ AuthProps.class, ChatProps.class, })
public class CommonAutoConfiguration {
}
