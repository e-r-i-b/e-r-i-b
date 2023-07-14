package com.rssl.phizicgate.way4u.messaging;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.gate.mobilebank.UserInfo;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MapUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.way4u.UserInfoImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.SimpleDateFormat;
import java.util.*;
import javax.xml.transform.TransformerException;

/**
 * @author krenev
 * @ created 10.10.2013
 * @ $Author$
 * @ $Revision$
 */

class UserInfoBuilder
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_GATE);
	private static final Set<String> DATA_NOT_FOUND_RET_CODES =
			new HashSet<String>(Arrays.asList(new String[]{"14", "15", "25", "56", "78", "1900", "2200", "2400", "2620"}));
	private static final String SUCCESS_RET_CODE = "0";
	private static final String DATE_FORMAT = "yyyy-MM-dd";

	private static final String DATA_RS_XPATH = "/UFXMsg/MsgData/Information/DataRs";
	private static final String CONTRACT_XPATH = "/DataRs/ContractRs/Contract";

	private static final String CLIENT_INFO_PATH = "ContractIDT/Client/ClientInfo";
	private static final String CONTRACT_NUMBER_PATH = "ContractIDT/ContractNumber";
	private static final String EXTRA_RS_PATH = "AddContractInfo/ExtraRs";

	private static final String FIRST_NAME_TAG = "FirstName";
	private static final String MIDDLE_NAME_TAG = "MiddleName";
	private static final String LAST_NAME_TAG = "LastName";
	private static final String REG_NUMBER_TAG = "RegNumber";
	private static final String BIRTH_DATE_TAG = "BirthDate";


	static UserInfo buildForUserId(Way4uResponse way4uResponse, String userId) throws SystemException
	{
		Document root = getDataRs(way4uResponse);
		if (root == null)
		{
			return null;
		}
		try
		{
			ArrayList<String> cards = new ArrayList<String>();
			NodeList contracts = XmlHelper.selectNodeList(root.getDocumentElement(), CONTRACT_XPATH);
			UserInfoImpl userInfo = null;
			for (int i = 0; i < contracts.getLength(); i++)
			{
				Element contract = (Element) contracts.item(i);
				String contractNumber = getContractNumber(contract);
				cards.add(contractNumber);

				String authIdt = getAuthIdt(contract);
				if (userId.equals(authIdt))
				{
					userInfo = getUserInfo(contract);
				}
			}
			if (userInfo == null)
			{
				return null;
			}
			if (StringHelper.isEmpty(userInfo.getSurname()))
			{
				return null;
			}
			userInfo.setCards(cards);
			return userInfo;
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	static UserInfo buildForCard(Way4uResponse way4uResponse, final String cardNumber) throws SystemException
	{
		Document root = getDataRs(way4uResponse);
		if (root == null)
		{
			return null;
		}
		try
		{
			ArrayList<String> cards = new ArrayList<String>();
			NodeList contracts = XmlHelper.selectNodeList(root.getDocumentElement(), CONTRACT_XPATH);
			UserInfoImpl userInfo = null;
			for (int i = 0; i < contracts.getLength(); i++)
			{
				Element contract = (Element) contracts.item(i);
				String contractNumber = getContractNumber(contract);
				cards.add(contractNumber);

				if (cardNumber.equals(contractNumber))
				{
					userInfo = getUserInfo(contract);
				}
			}
			if (userInfo == null)
			{
				return null;
			}
			if (StringHelper.isEmpty(userInfo.getSurname()))
			{
				return null;
			}
			userInfo.setCards(cards);
			return userInfo;
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	private static Document getDataRs(Way4uResponse response) throws SystemException
	{
		if (response == null)
		{
			return null;
		}
		String respCode = response.getRespCode();
		String respTest = response.getRespText();
		if (DATA_NOT_FOUND_RET_CODES.contains(respCode))
		{
			log.warn(String.format("ѕолучен код отказа %s(%s), который приравниваетс€ к ответу 'ƒанные не найдены'.", respCode, respTest));
			return null;
		}
		if (!SUCCESS_RET_CODE.equals(respCode))
		{
			throw new SystemException(String.format("ѕолучен код отказа %s(%s) от way4u.", respCode, respTest));
		}
		try
		{
			return XmlHelper.extractPart(response.asDOM(), DATA_RS_XPATH);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	private static UserInfoImpl getUserInfo(Element contract) throws Exception
	{
		UserInfoImpl result = new UserInfoImpl();

		Element clientInfo = XmlHelper.selectSingleNode(contract, CLIENT_INFO_PATH);

		result.setFirstname(XmlHelper.getSimpleElementValue(clientInfo, FIRST_NAME_TAG));
		result.setPatrname(XmlHelper.getSimpleElementValue(clientInfo, MIDDLE_NAME_TAG));
		result.setSurname(XmlHelper.getSimpleElementValue(clientInfo, LAST_NAME_TAG));
		result.setPassport(XmlHelper.getSimpleElementValue(clientInfo, REG_NUMBER_TAG));
		result.setBirthdate(DateHelper.toCalendar(new SimpleDateFormat(DATE_FORMAT).parse(XmlHelper.getSimpleElementValue(clientInfo, BIRTH_DATE_TAG))));

		result.setCardNumber(getContractNumber(contract));

		//<ExtraRs>ADD_CARD=..;CB_CODE=Е;AUTH_IDT=Е;ContractStatusID=Е</ExtraRs>
		Map<String, String> extraRs = getExtraRS(contract);
		result.setCbCode(extraRs.get("CB_CODE"));
		result.setUserId(extraRs.get("AUTH_IDT"));
		result.setActiveCard(isActiveCard(extraRs.get("ContractStatusID")));
		result.setMainCard(isMainCard(extraRs.get("ADD_CARD")));
		return result;
	}

	private static String getContractNumber(Element contract) throws TransformerException
	{
		return XmlHelper.getElementValueByPath(contract, CONTRACT_NUMBER_PATH);
	}

	//<ExtraRs>ADD_CARD=..;CB_CODE=Е;AUTH_IDT=Е;ContractStatusID=Е</ExtraRs>
	private static Map<String, String> getExtraRS(Element contract) throws TransformerException
	{
		return MapUtil.parse(XmlHelper.getElementValueByPath(contract, EXTRA_RS_PATH), "=", ";");
	}

	private static String getAuthIdt(Element contract) throws TransformerException
	{
		Map<String, String> extraRs = getExtraRS(contract); //<ExtraRs>ADD_CARD=..;CB_CODE=Е;AUTH_IDT=Е;ContractStatusID=Е</ExtraRs>
		if (extraRs == null)
		{
			return null;
		}
		return extraRs.get("AUTH_IDT");
	}

	/**
	 *
	 * contractStatusID - текущий статус карты (ID), дл€ активной карты будет возвращатьс€: 14 или 239.
	 *
	 */
	private static boolean isActiveCard(String contractStatusID)
	{
		return "14".equals(contractStatusID) || "239".equals(contractStatusID);
	}

	/**
	 * ADD_CARD=0 Ц  арта основна€
	 * ADD_CARD=1 Ц  арта дополнительна€
	 */
	private static boolean isMainCard(String addCard)
	{
		if ("0".equals(addCard))
		{
			return true;
		}
		if ("1".equals(addCard))
		{
			return false;
		}
		throw new IllegalArgumentException("Ќедопустимое значение ADD_CARD = " + addCard);
	}
}
