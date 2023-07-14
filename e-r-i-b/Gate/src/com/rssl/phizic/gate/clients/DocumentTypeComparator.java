package com.rssl.phizic.gate.clients;

import com.rssl.phizic.common.types.client.ClientDocumentType;

import java.util.Comparator;

/**
 * @author egorova
 * @ created 24.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class DocumentTypeComparator implements Comparator<ClientDocument>
{
	public int compare(ClientDocument document1, ClientDocument document2)
	{
		//первичная сортировка идет по паспорту
		if (document1.getDocumentType() == ClientDocumentType.REGULAR_PASSPORT_RF && document2.getDocumentType() == ClientDocumentType.REGULAR_PASSPORT_RF)
			return 0;
		if (document1.getDocumentType() == ClientDocumentType.REGULAR_PASSPORT_RF)
			return -1;
		if (document2.getDocumentType() == ClientDocumentType.REGULAR_PASSPORT_RF)
			return 1;

		//второй этап сортировки идет по паспорту Way
		//должен идти после всех идентифицирующих документов, но перед неидентифицирующими
		if (document1.getDocumentType() == ClientDocumentType.PASSPORT_WAY && document2.getDocumentType() == ClientDocumentType.PASSPORT_WAY)
			return 0;
		if (document1.getDocumentType() == ClientDocumentType.PASSPORT_WAY)
			return document2.isDocIdentify() ? 1 : -1;
		if (document2.getDocumentType() == ClientDocumentType.PASSPORT_WAY)
			return document1.isDocIdentify() ? -1 : 1;

		//третий этап сортировки, по тому что документ удостоверяет личность
		if (document1.isDocIdentify() && document2.isDocIdentify())
			return 0;
		if (document1.isDocIdentify())
			return -1;
		if (document2.isDocIdentify())
			return 1;

		//остальные считаем равными
		return 0;
	}
}