package com.rssl.phizic.business.ermb.migration.mbk;

import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.utils.Priority;
import com.rssl.phizic.utils.PriorityComparator;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author Erkin
 * @ created 12.11.2014
 * @ $Author$
 * @ $Revision$
 */
class ClientPassportResolver
{
	/**
	 * Возвращает подходящий документ клиента: паспорт, ДУЛ либо любой другой
	 * @param client - клиент
	 * @return серия + номер  документа или null, если ничего не подошло
	 */
	String getClientPassport(Client client)
	{
		List<? extends ClientDocument> documents = client.getDocuments();
		if (CollectionUtils.isEmpty(documents))
			return null;

		// Ищем либо паспорт либо любой ДУЛ
		ClientDocument document = Collections.max(documents, new PriorityComparator<ClientDocument>()
		{
			@Override
			protected Priority getPriority(ClientDocument document)
			{
				Priority priority = new Priority();

				if (document.getDocumentType() == ClientDocumentType.REGULAR_PASSPORT_RF)
					priority.p1 = 1;

				if (document.isDocIdentify())
					priority.p2 = 1;

				return priority;
			}
		});

		return StringHelper.getEmptyIfNull(document.getDocSeries())	+
				StringHelper.getEmptyIfNull(document.getDocNumber());
	}
}
