package com.rssl.phizic.rsa.senders.builders.offline;

import com.rssl.phizic.rsa.senders.builders.RequestBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author tisov
 * @ created 09.07.15
 * @ $Author$
 * @ $Revision$
 * Базовый класс билдера оффлайн-запросов во фрод-мониторинг
 */
public abstract class FraudMonitoringOfflineRequestBuilderBase<RQ> implements RequestBuilder<RQ>
{
	protected Element rootNode;

	public void initialize(Document document)
	{
		this.rootNode = document.getDocumentElement();
	}

}
