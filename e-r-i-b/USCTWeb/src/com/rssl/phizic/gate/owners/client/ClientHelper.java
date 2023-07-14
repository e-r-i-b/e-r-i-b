package com.rssl.phizic.gate.owners.client;

import com.rssl.phizgate.common.services.types.ClientDocumentImpl;
import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.GUID;
import com.rssl.phizic.gate.clients.GUIDComparator;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author khudyakov
 * @ created 23.04.14
 * @ $Author$
 * @ $Revision$
 */
public class ClientHelper
{
	/**
	 * Найти ДУЛ клиента по типу документа
	 * @param client клиент
	 * @param documentType тип документа
	 * @return документ
	 * @throws Exception
	 */
	public static ClientDocument getClientDocumentByType(Client client, ClientDocumentType documentType) throws Exception
	{
		return getClientDocumentByType(client.getDocuments(), documentType);
	}

	/**
	 * Найти ДУЛ клиента по типу документа
	 * @param documents список документов клиента
	 * @param documentType тип документа
	 * @return документ
	 * @throws Exception
	 */
	public static ClientDocument getClientDocumentByType(List<? extends ClientDocument> documents, ClientDocumentType documentType) throws Exception
	{
		if (CollectionUtils.isEmpty(documents))
		{
			throw new Exception("У клинта отсутствуют ДУЛ");
		}

		for (ClientDocument document : documents)
		{
			if (documentType == document.getDocumentType())
			{
				return document;
			}
		}
		return null;
	}

	/**
	 * Найти Way ДУЛ клиента.
	 *
	 * Ограничение: паспорт way, может отсутствовать при прямом получении клиента из внешней системы
	 *
	 * @param client клиент
	 * @return документ
	 * @throws Exception
	 */
	public static ClientDocument getClientWayDocument(Client client) throws Exception
	{
		return getClientWayDocument(client.getDocuments());
	}

	/**
	 * Найти Way ДУЛ клиента.
	 *
	 * Ограничение: паспорт way, может отсутствовать при прямом получении клиента из внешней системы
	 *
	 * @param documents документы клиента
	 * @return документ
	 * @throws Exception
	 */
	public static ClientDocument getClientWayDocument(List<? extends ClientDocument> documents) throws Exception
	{
		ClientDocument document = getClientDocumentByType(documents, ClientDocumentType.PASSPORT_WAY);
		if (document == null)
		{
			throw new Exception("У клиента отсутствует паспорт way");
		}
		return document;
	}

	/**
	 * Создать паспорт way
	 * @param passport серия и номер документа
	 * @return паспорт way
	 */
	public static ClientDocument getWayDocument(String passport)
	{
		ClientDocumentImpl document = new ClientDocumentImpl();
		document.setDocSeries(passport);
		document.setDocumentType(ClientDocumentType.PASSPORT_WAY);

		return document;
	}

	/**
	 * Получить список уникальных профилей клиента
	 * @param guids профили клиента
	 * @return список профилей
	 */
	public static List<GUID> getUniqueClientHistory(List<GUID> guids)
	{
		if (CollectionUtils.isEmpty(guids))
		{
			return Collections.emptyList();
		}

		List<GUID> result = new ArrayList<GUID>();
		for (GUID guid : guids)
		{
			if (isAdded(guid, result))
			{
				continue;
			}
			result.add(guid);
		}
		return result;
	}

	private static boolean isAdded(GUID guid, List<GUID> guids)
	{
		GUIDComparator comparator = new GUIDComparator();
		for (GUID unique : guids)
		{
			if (comparator.compare(guid, unique) != 0)
			{
				continue;
			}
			return true;
		}
		return false;
	}
}
