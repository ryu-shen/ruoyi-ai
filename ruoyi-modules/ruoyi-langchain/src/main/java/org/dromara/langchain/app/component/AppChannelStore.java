package org.dromara.langchain.app.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dromara.common.satoken.utils.AgiLoginHelperUtil;
import org.dromara.langchain.app.domain.AgiAppApi;
import org.dromara.langchain.app.domain.constant.AppConst;
import org.dromara.langchain.app.service.AgiAppApiService;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class AppChannelStore {

	private static final Map<String, AgiAppApi> API_MAP = new HashMap<>();

	private final AgiAppApiService appApiService;

	public static AgiAppApi getApiChannel() {
		String token = AgiLoginHelperUtil.getAuthorizationToken();
		return API_MAP.get(token);
	}

	@PostConstruct
	public void init() {
		log.info("initialize app channel config list...");
		List<AgiAppApi> apis = appApiService.list();
		apis.forEach(api -> API_MAP.put(api.getApiKey(), api));
	}

	public void isExpired(String channel) {
		String token = AgiLoginHelperUtil.getAuthorizationToken();

		if (AppConst.CHANNEL_API.equals(channel)) {
			AgiAppApi data = API_MAP.get(token);
			if (data == null) {
				throw new RuntimeException("The ApiKey is empty");
			}
		}
	}
}
