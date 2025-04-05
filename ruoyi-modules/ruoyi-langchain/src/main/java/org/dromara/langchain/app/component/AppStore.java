
package org.dromara.langchain.app.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dromara.langchain.app.domain.AgiApp;
import org.dromara.langchain.app.service.AgiAppService;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@AllArgsConstructor
public class AppStore {

	private static final Map<String, AgiApp> appMap = new HashMap<>();
	private final AgiAppService agiAppService;

	@PostConstruct
	public void init() {
		log.info("initialize app config list...");
		List<AgiApp> list = agiAppService.list();
		list.forEach(i -> appMap.put(i.getId(), i));
	}

	public AgiApp get(String appId) {
		return appMap.get(appId);
	}
}
