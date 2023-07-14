package com.rssl.phizic.gate.clients;

import com.rssl.phizic.common.types.client.ClientDocumentType;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Хелпер для работы с клиентами
 *
 * @author khudyakov
 * @ created 05.03.14
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
	 * @throws GateException
	 */
	public static ClientDocument getClientDocumentByType(Client client, ClientDocumentType documentType) throws GateException
	{
		return getClientDocumentByType(client.getDocuments(), documentType);
	}

	/**
	 * Найти ДУЛ клиента по типу документа
	 * @param documents список документов клиента
	 * @param documentType тип документа
	 * @return документ
	 * @throws GateException
	 */
	public static ClientDocument getClientDocumentByType(List<? extends ClientDocument> documents, ClientDocumentType documentType) throws GateException
	{
		if (CollectionUtils.isEmpty(documents))
		{
			throw new GateException("У клинта отсутствуют ДУЛ");
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
	 * @throws GateException
	 */
	public static ClientDocument getClientWayDocument(Client client) throws GateException
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
	 * @throws GateException
	 */
	public static ClientDocument getClientWayDocument(List<? extends ClientDocument> documents) throws GateException
	{
		ClientDocument document = getClientDocumentByType(documents, ClientDocumentType.PASSPORT_WAY);
		if (document == null)
		{
			throw new GateException("У клиента отсутствует паспорт way");
		}
		return document;
	}

	/**
	 * Получить список уникальных профилей клиента
	 * @param guids профили клиента
	 * @return список профилей
	 */
	public static List<? extends GUID> getUniqueClientHistory(List<? extends GUID> guids)
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

	/**
	 * @param client1 клиент 1
	 * @param client2 клиент 1
	 * @return true если информация совпала
	 */
	public static boolean isSameClient(Client client1,Client client2)
	{
		if (!isSameFIOAndBirthday(client1.getFirstName(), client1.getSurName(), client1.getPatrName(), client1.getBirthDay(),
				client2.getFirstName(), client2.getSurName(), client2.getPatrName(), client2.getBirthDay()))
			return false;

		List<? extends ClientDocument>  clientDocuments1 = client1.getDocuments();
		List<? extends ClientDocument>  clientDocuments2 = client2.getDocuments();

		for(ClientDocument doc1:clientDocuments1)
		{
			String seriesAndNumber1 = (StringHelper.getEmptyIfNull(doc1.getDocSeries()) + StringHelper.getEmptyIfNull(doc1.getDocNumber())).replaceAll(" ", "");
			for(ClientDocument doc2:clientDocuments2)
			{
				String  seriesAndNumber2 = (StringHelper.getEmptyIfNull(doc2.getDocSeries()) + StringHelper.getEmptyIfNull(doc2.getDocNumber())).replaceAll(" ", "");
				if (seriesAndNumber1.equalsIgnoreCase(seriesAndNumber2))
					return true;
			}
		}
		return false;
	}

	public static boolean isSameFIOAndBirthday(String f1, String s1, String p1, Calendar b1, String f2, String s2, String p2, Calendar b2)
	{
		String clientFIO1 = StringHelper.getEmptyIfNull(f1) + StringHelper.getEmptyIfNull(s1) + StringHelper.getEmptyIfNull(p1);
		String clientFIO2 = StringHelper.getEmptyIfNull(f2) + StringHelper.getEmptyIfNull(s2) + StringHelper.getEmptyIfNull(p2);

		if (!StringHelper.equalsAsPersonName(clientFIO1, clientFIO2))
			return false;

		if (!DateUtils.isSameDay(b1, b2))
			return false;

		return true;
	}
}
