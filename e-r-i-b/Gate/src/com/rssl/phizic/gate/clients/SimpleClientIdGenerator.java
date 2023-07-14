package com.rssl.phizic.gate.clients;

import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.utils.HashHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author khudyakov
 * @ created 29.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class SimpleClientIdGenerator
{
	private static final String SEPARATOR = "^";                                //разделитель

	/**
	 * Генерирует внешний идентификатор клиента по входящим данным
	 * формат: hash(surName+firstName+middleName)^birthDay^documentNumber^RbBrchId
	 *
	 * @param client клиент
	 * @return ключ
	 */
	public static String generateClientId(Client client)
	{
		return generateClientId(client.getSurName(), client.getFirstName(), client.getPatrName(), client.getBirthDay() == null ? null : String.format("%1$td.%1$tm.%1$tY", client.getBirthDay()), getClientDocument(client), getRbTbBrunch(client.getOffice()));
	}

	/**
	 * Генерирует внешний идентификатор клиента по входящим данным
	 * формат: hash(surName+firstName+middleName)^birthDay^documentNumber^RbBrchId
	 *
	 * @param surName фамилия
	 * @param firstName имя
	 * @param middleName отчество
	 * @param birthDay дата рождения
	 * @param documentNumber номер документа
	 * @param rbTbBrunch rbTbBrunch
	 * @return id
	 */
	public static String generateClientId(String surName, String firstName, String middleName, String birthDay, String documentNumber, String rbTbBrunch)
	{
		StringBuilder userId = new StringBuilder();
		if (StringHelper.isNotEmpty(surName) || StringHelper.isNotEmpty(firstName) || StringHelper.isNotEmpty(middleName))
		{
			//добавляем hash фио (из того, что есть)
			StringBuilder fio = new StringBuilder().append(StringHelper.getEmptyIfNull(surName)).append(StringHelper.getEmptyIfNull(firstName)).append(StringHelper.getEmptyIfNull(middleName));
			userId.append(HashHelper.hash(fio.toString()));
		}
		if (StringHelper.isNotEmpty(birthDay))
		{
			//добавляем дату рождения
			userId.append(birthDay).append(SEPARATOR);
		}
		if (StringHelper.isNotEmpty(documentNumber))
		{
			//добавляем номер документа
			userId.append(documentNumber).append(SEPARATOR);
		}
		if (StringHelper.isNotEmpty(rbTbBrunch))
		{
			//добавляем номер RbTbBrunch
			userId.append(rbTbBrunch);
		}
		return userId.toString();
	}

	private static String getRbTbBrunch(Office office)
	{
		if (office == null)
			return null;

		if (office.getCode() == null)
			return null;

		return StringHelper.appendLeadingZeros(office.getCode().getFields().get("region"), 2) + "000000"; 
	}

	private static String getClientDocument(Client client)
	{
		if (CollectionUtils.isNotEmpty(client.getDocuments()))
		{
			List<? extends ClientDocument> clientDocuments = new ArrayList<ClientDocument>(client.getDocuments());
			Collections.sort(clientDocuments, new DocumentTypeComparator());
			ClientDocument document = clientDocuments.get(0);
			return document.getDocSeries() + document.getDocNumber();
		}
		return null;
	}
}
