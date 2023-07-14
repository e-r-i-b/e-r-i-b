package com.rssl.phizic.business.documents.templates.service;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeCodeAdapter;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.clients.ClientHelper;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.StringHelper;

/**
 * Класс для генерации ключа кеша на основе ФИО, ДУЛ, ДР, ТБ клиента
 *
 * @author khudyakov
 * @ created 05.03.14
 * @ $Author$
 * @ $Revision$
 */
public class ClientCacheKeyGenerator
{
	private static final String DATE_FORMAT                 = "%1$td.%1$tm.%1$tY";
	private static final String DELIMITER                   = "^";


	/**
	 * Сформировать ключ по списку клиентов
	 * @param client клиент
	 * @return ключ
	 * @throws BusinessException
	 */
	public static String getKey(Client client) throws BusinessException
	{
		return client.getSurName() + DELIMITER + client.getFirstName() + DELIMITER + client.getPatrName() + DELIMITER +
				getClientDocumentKey(client) + DELIMITER +  String.format(DATE_FORMAT, client.getBirthDay()) + DELIMITER + getClientOfficeKey(client);
	}

	private static String getClientOfficeKey(Client client)
	{
		return new SBRFOfficeCodeAdapter(client.getOffice().getCode()).getRegion();
	}

	private static String getClientDocumentKey(Client client) throws BusinessException
	{
		try
		{
			ClientDocument document = ClientHelper.getClientWayDocument(client);
			return StringHelper.getEmptyIfNull(document.getDocNumber()) + StringHelper.getEmptyIfNull(document.getDocSeries());
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
	}
}
