package com.rssl.phizic.business.clients.list.parsers;

import com.rssl.phizic.business.clients.list.ClientBlock;
import com.rssl.phizic.business.clients.list.ClientInformation;
import com.rssl.phizic.business.clients.list.ClientNodeInfo;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.ermb.ErmbStatus;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 07.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Парсер списка клиентов
 */

public class ClientListParser extends ParserBase
{
	private static final String FOREACH_XPATH = "clients/clientInfo";
	private static final String EXTERNAL_ID_TAG_NAME = "externalId";
	private static final String FIRSTNAME_TAG_NAME = "firstname";
	private static final String SURNAME_TAG_NAME = "surname";
	private static final String PATRNAME_TAG_NAME = "patrname";
	private static final String BIRTHDATE_TAG_NAME = "birthdate";
	private static final String PASSPORT_TAG_NAME = "passport";
	private static final String TB_TAG_NAME = "tb";
	private static final String LOGIN_TAG_NAME = "login";
	private static final String USER_ID_TAG_NAME = "userId";
	private static final String LAST_LOGIN_DATE_TAG_NAME = "lastLoginDate";
	private static final String CREATION_TYPE_TAG = "creationType";
	private static final String AGREEMENT_NUMBER_TAG = "agreementNumber";
	private static final String NODES_TAG_NAME = "nodes/node";
	private static final String BLOCKS_TAG_NAME = "locks/lockInfo";
	private static final String ERMB_ACTIVE_PHONE = "mainPhone";
	private static final String ERMB_STATUS = "ermbStatus";

	private final List<ClientInformation> clients = new ArrayList<ClientInformation>();
	private final Document response;

	/**
	 * конструктор
	 * @param response xml'ка
	 */
	public ClientListParser(Document response)
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
	public List<ClientInformation> getClients()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return clients;
	}

	private List<ClientBlock> getClientBlocksTypeValue(Element parent) throws Exception
	{
		ClientBlockListParser action = new ClientBlockListParser();
		XmlHelper.foreach(parent, BLOCKS_TAG_NAME, action);
		return action.getBlocks();
	}

	private List<ClientNodeInfo> getClientNodesTypeValue(Element parent) throws Exception
	{
		ClientNodeInfoParser action = new ClientNodeInfoParser();
		XmlHelper.foreach(parent, NODES_TAG_NAME, action);
		return action.getNodes();
	}

	public void execute(Element element) throws Exception
	{
		ClientInformation clientInformation = new ClientInformation();
		clientInformation.setId(getLongValue(element, EXTERNAL_ID_TAG_NAME));
		clientInformation.setFirstname(getStringValue(element, FIRSTNAME_TAG_NAME));
		clientInformation.setSurname(getStringValue(element, SURNAME_TAG_NAME));
		clientInformation.setPatrname(getStringValue(element, PATRNAME_TAG_NAME));
		clientInformation.setBirthday(getCalendarValue(element, BIRTHDATE_TAG_NAME));
		clientInformation.setDocument(getStringValue(element, PASSPORT_TAG_NAME));
		clientInformation.setTb(getStringValue(element, TB_TAG_NAME));
		clientInformation.setCreationType(getEnumTypeValue(element, CREATION_TYPE_TAG, CreationType.class));
		clientInformation.setAgreementNumber(getStringValue(element, AGREEMENT_NUMBER_TAG));
		clientInformation.setLogin(getStringValue(element, LOGIN_TAG_NAME));
		clientInformation.setUserId(getStringValue(element, USER_ID_TAG_NAME));
		clientInformation.setLastLoginDate(getCalendarValue(element, LAST_LOGIN_DATE_TAG_NAME));
		clientInformation.setBlocks(getClientBlocksTypeValue(element));
		clientInformation.setNodes(getClientNodesTypeValue(element));
		clientInformation.setErmbActivePhone(getStringValue(element, ERMB_ACTIVE_PHONE));
		clientInformation.setErmbStatus(getEnumTypeValue(element, ERMB_STATUS, ErmbStatus.class));
		clients.add(clientInformation);
	}
}
