package org.dromara.langchain.platform.service;

import java.util.List;

import org.dromara.langchain.platform.domain.AgiDocs;
import org.dromara.langchain.platform.domain.AgiDocsSlice;
import org.dromara.langchain.platform.domain.AgiKnowledge;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 
 * 
 */
public interface AgiKnowledgeService extends IService<AgiKnowledge> {

	void addDocs(AgiDocs data);

	void updateDocs(AgiDocs data);

	void addDocsSlice(AgiDocsSlice data);

	void updateDocsSlice(AgiDocsSlice data);

	List<String> listSliceVectorIdsOfDoc(String docsId);

	List<AgiDocs> getDocsByKb(String knowledgeId);

	void removeKnowledge(String knowledgeId);

	void removeSlicesOfDoc(String docsId);
}
