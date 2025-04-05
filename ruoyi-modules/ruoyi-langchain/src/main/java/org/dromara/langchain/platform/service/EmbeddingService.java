
package org.dromara.langchain.platform.service;

import java.util.List;
import java.util.Map;

import org.dromara.langchain.platform.domain.AgiDocs;

public interface EmbeddingService {

	void clearDocSlices(String docsId);

	void embedDocsSlice(AgiDocs data, String url);

	List<Map<String, Object>> search(AgiDocs data);
}
