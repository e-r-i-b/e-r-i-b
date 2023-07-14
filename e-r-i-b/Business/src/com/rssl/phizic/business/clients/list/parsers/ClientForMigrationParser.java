package com.rssl.phizic.business.clients.list.parsers;

import com.rssl.phizic.business.clients.list.ClientForMigration;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 29.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Парсер списка клиентов для миграции
 */

public class ClientForMigrationParser extends ParserBase
{
	private static final String FOREACH_XPATH        = "clients/clientInfo";
	private static final String EXTERNAL_ID_TAG_NAME = "externalId";
	private static final String FIRSTNAME_TAG_NAME   = "firstname";
	private static final String SURNAME_TAG_NAME     = "surname";
	private static final String PATRONYMIC_TAG_NAME  = "patrname";
	private static final String BIRTHDAY_TAG_NAME    = "birthdate";
	private static final String PASSPORT_TAG_NAME    = "passport";
	private static final String TB_TAG_NAME          = "tb";
	private static final String CREATION_TYPE_TAG    = "creationType";
	private static final String AGREEMENT_NUMBER_TAG = "agreementNumber";

	private final List<ClientForMigration> clients = new ArrayList<ClientForMigration>();
	private final Document response;

	/**
	 * конструктор
	 * @param response xml представление списка клиентов
	 */
	public ClientForMigrationParser(Document response)
	{
		this.response = response;
	}

	/**
	 * распарсить xml'ку
	 * @throws Exception
	 */
	public void parse() throws Exception
	{
		XmlHelper.foreach(response.getDocumentElement(), FOREACH_XPATH, this);
	}

	/**
	 * @return список клиентов
	 */
	public List<ClientForMigration> getClients()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return clients;
	}

	public void execute(Element element) throws Exception
	{
		ClientForMigration client = new ClientForMigration();
		client.setId(getLongValue(element, EXTERNAL_ID_TAG_NAME));
		client.setFirstname(getStringValue(element, FIRSTNAME_TAG_NAME));
		client.setSurname(getStringValue(element, SURNAME_TAG_NAME));
		client.setPatronymic(getStringValue(element, PATRONYMIC_TAG_NAME));
		client.setBirthday(getCalendarValue(element, BIRTHDAY_TAG_NAME));
		client.setDocument(getStringValue(element, PASSPORT_TAG_NAME));
		client.setDepartment(getStringValue(element, TB_TAG_NAME));
		client.setAgreementType(getEnumTypeValue(element, CREATION_TYPE_TAG, CreationType.class));
		client.setAgreementNumber(getStringValue(element, AGREEMENT_NUMBER_TAG));
		clients.add(client);
	}
}
