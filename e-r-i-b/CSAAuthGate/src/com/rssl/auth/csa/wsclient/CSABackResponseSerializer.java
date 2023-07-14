package com.rssl.auth.csa.wsclient;

import com.rssl.auth.csa.back.servises.UserLogonType;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.responses.ConfirmationInformation;
import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;
import com.rssl.auth.csa.wsclient.responses.SessionInfo;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.authgate.AuthParamsContainer;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.transform.TransformerException;

/**
 * Сериалайзер ответов из CSABack
 * @author niculichev
 * @ created 15.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class CSABackResponseSerializer
{
	private static final String SID_PATH = "/createMobileSessionRs/sessionInfo/SID";
	private static final String USER_ID_PATH = "/createMobileSessionRs/connectorInfo/userId";
	private static final String CARD_NUMBER_PATH = "/createMobileSessionRs/connectorInfo/cardNumber";
	private static final String CB_CODE_PATH = "/createMobileSessionRs/connectorInfo/cbCode";
	private static final String FIRSTNAME_PATH = "/createMobileSessionRs/userInfo/firstname";
	private static final String SURNAME_PATH = "/createMobileSessionRs/userInfo/surname";
	private static final String PATRNAME_PATH = "/createMobileSessionRs/userInfo/patrname";
	private static final String PASSPORT_PATH = "/createMobileSessionRs/userInfo/passport";
	private static final String BIRTH_DATE_PATH = "/createMobileSessionRs/userInfo/birthdate";

	/**
	 * Парсннг блока ConnectorInfo
	 * @param connectorInfoElement элемент с информацией о коннекторе
	 * @return  информация о коннекторе
	 */
	private static ConnectorInfo getConnectorInfo(Element connectorInfoElement) throws TransformerException
	{
		if(connectorInfoElement == null)
			return null;

		ConnectorInfo connectorInfo = new ConnectorInfo();
		connectorInfo.setId(Long.valueOf(XmlHelper.getSimpleElementValue(connectorInfoElement, CSAResponseConstants.ID_TAG)));
		connectorInfo.setPushSupported(Boolean.valueOf(XmlHelper.getSimpleElementValue(connectorInfoElement, CSAResponseConstants.PUSH_SUPPORTED_TAG)));
		connectorInfo.setDeviceState(XmlHelper.getSimpleElementValue(connectorInfoElement, CSAResponseConstants.DEVICE_STATE_TAG));
		connectorInfo.setDeviceInfo(XmlHelper.getSimpleElementValue(connectorInfoElement, CSAResponseConstants.DEVICE_INFO_TAG));
		connectorInfo.setDeviceID(XmlHelper.getSimpleElementValue(connectorInfoElement, CSAResponseConstants.DEVICE_ID_TAG));
		connectorInfo.setCbCode(XmlHelper.getSimpleElementValue(connectorInfoElement, CSAResponseConstants.CB_CODE_TAG));
		connectorInfo.setUserId(XmlHelper.getSimpleElementValue(connectorInfoElement, CSAResponseConstants.USER_ID_TAG));
		connectorInfo.setCardNumber(XmlHelper.getSimpleElementValue(connectorInfoElement, CSAResponseConstants.CARD_NUMBER_TAG));
		connectorInfo.setLogin(XmlHelper.getSimpleElementValue(connectorInfoElement, CSAResponseConstants.LOGIN_TAG));
		connectorInfo.setType(ConnectorInfo.Type.valueOf(
				XmlHelper.getSimpleElementValue(connectorInfoElement, CSAResponseConstants.TYPE_TAG)));
		connectorInfo.setGuid(XmlHelper.getSimpleElementValue(connectorInfoElement, CSAResponseConstants.GUID_TAG));
		
		String creationDateStr = XmlHelper.getSimpleElementValue(connectorInfoElement, CSAResponseConstants.CREATION_DATE_TAG);
		connectorInfo.setCreationDate(XMLDatatypeHelper.parseDateTime(creationDateStr));

		String passwordCreationDateStr = XmlHelper.getSimpleElementValue(connectorInfoElement, CSAResponseConstants.PASSWORD_CREATION_DATE_TAG);
		if(StringHelper.isNotEmpty(passwordCreationDateStr))
			connectorInfo.setPasswordCreationDate(XMLDatatypeHelper.parseDateTime(passwordCreationDateStr));
		String lastSessionDate = XmlHelper.getSimpleElementValue(connectorInfoElement, CSAResponseConstants.LAST_SESSION_DATE);
		if (StringHelper.isNotEmpty(lastSessionDate))
			connectorInfo.setLastSessionDate(XMLDatatypeHelper.parseDateTime(lastSessionDate));

		return connectorInfo;
	}

	/**
	 * Парсннг блоков ConnectorInfo
	 * @param document ответ
	 * @return список информаций о коннекторе
	 * @throws TransformerException
	 */
	public static List<ConnectorInfo> getConnectorInfos(Document document) throws TransformerException
	{
		final List<ConnectorInfo> connectorInfos = new ArrayList<ConnectorInfo>();
		try
		{
			XmlHelper.foreach(document.getDocumentElement(), CSAResponseConstants.CONNECTOR_INFO_TAG, new ForeachElementAction(){
				public void execute(Element element) throws Exception
				{
					connectorInfos.add(getConnectorInfo(element));
				}
			});
		}
		catch (Exception e)
		{
			throw new TransformerException(e);
		}

		return connectorInfos;
	}

	/**
	 * Парсинг истории UserInfo
	 * @param document ответ CSA
	 * @return список исторической информации о профиле пользователя
	 * @throws TransformerException
	 */
	public static List<UserInfo> getHistoryUserInfoList (Document document) throws TransformerException
	{
		final List<UserInfo> userInfos = new ArrayList<UserInfo>();

		Element element = document.getDocumentElement();
		Element historyTag = XmlHelper.selectSingleNode(element, CSAResponseConstants.HISTORY_TAG);
		try
		{
			XmlHelper.foreach(historyTag, CSAResponseConstants.HISTORY_ITEM_TAG, new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					UserInfo userInfo = new UserInfo();

					userInfo.setFirstname(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.FIRSTNAME_TAG));
					userInfo.setPatrname(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.PATRNAME_TAG));
					userInfo.setSurname(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.SURNAME_TAG));

					String birthdateStr = XmlHelper.getSimpleElementValue(element, CSAResponseConstants.BIRTHDATE_TAG);
					userInfo.setBirthdate(XMLDatatypeHelper.parseDateTime(birthdateStr));

					userInfo.setPassport(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.PASSPORT_TAG));
					userInfo.setCbCode(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.CB_CODE_TAG));
					userInfo.setTb(XmlHelper.getSimpleElementValue(element, CSAResponseConstants.TER_BANK_TAG));

					userInfos.add(userInfo);
				}
			});
		}
		catch (Exception e)
		{
			throw new TransformerException(e);
		}
		return userInfos;
	}

	/**
	 * Парсннг блока UserInfo
	 * @param document ответ CSABack
	 * @return информация о пользователе
	 */
	public static UserInfo getUserInfo(Document document) throws TransformerException
	{
		UserInfo userInfo = new UserInfo();
		Element userInfoElement = XmlHelper.selectSingleNode(document.getDocumentElement(), CSAResponseConstants.USER_INFO_TAG);
		if(userInfoElement == null)
			return null;

		userInfo.setFirstname(XmlHelper.getSimpleElementValue(userInfoElement, CSAResponseConstants.FIRSTNAME_TAG));
		userInfo.setPatrname(XmlHelper.getSimpleElementValue(userInfoElement, CSAResponseConstants.PATRNAME_TAG));
		userInfo.setSurname(XmlHelper.getSimpleElementValue(userInfoElement, CSAResponseConstants.SURNAME_TAG));

		String birthdateStr = XmlHelper.getSimpleElementValue(userInfoElement, CSAResponseConstants.BIRTHDATE_TAG);
		userInfo.setBirthdate(XMLDatatypeHelper.parseDateTime(birthdateStr));

		userInfo.setPassport(XmlHelper.getSimpleElementValue(userInfoElement, CSAResponseConstants.PASSPORT_TAG));
		userInfo.setCbCode(XmlHelper.getSimpleElementValue(userInfoElement, CSAResponseConstants.CB_CODE_TAG));
		userInfo.setTb(XmlHelper.getSimpleElementValue(userInfoElement, CSAResponseConstants.TER_BANK_TAG));

		return userInfo;
	}

	/**
	 * Парсннг блока NodeInfo
	 * @param document ответ CSABack
	 * @return уникальный идентификатор операции
	 */
	public static NodeInfo getNodeInfo(Document document) throws TransformerException
	{
		Element nodeInfoElement = XmlHelper.selectSingleNode(document.getDocumentElement(), CSAResponseConstants.NODE_INFO_TAG);
		if(nodeInfoElement == null)
			return null;

		NodeInfo nodeInfo = new NodeInfo();
		nodeInfo.setHost(XmlHelper.getSimpleElementValue(nodeInfoElement, CSAResponseConstants.HOST_TAG));

		return nodeInfo;
	}

	/**
	 * Парсннг блока OUID
	 * @param document ответ CSABack
	 * @return уникальный идентификатор операции
	 */
	public static String getOUID(Document document)
	{
		return XmlHelper.getSimpleElementValue(document.getDocumentElement(), CSAResponseConstants.OUID_TAG);
	}

	/**
	 * Парсннг блока profileId
	 * @param document ответ CSABack
	 * @return идентификатор
	 */
	public static Long getProfileId(Document document)
	{
		String res = XmlHelper.getSimpleElementValue(document.getDocumentElement(), CSAResponseConstants.PROFILE_ID_TAG);
		return StringHelper.isNotEmpty(res) ? Long.valueOf(res) : null;
	}

	/**
	 * Парсннг блока authToken
	 * @param document ответ CSABack
	 * @return токен аутентификации
	 */
	public static String getAuthToken(Document document)
	{
		return XmlHelper.getSimpleElementValue(document.getDocumentElement(), CSAResponseConstants.AUTH_TOKEN_TAG);
	}

	private static Long getNullSafeLong(String value)
	{
		if (StringHelper.isEmpty(value))
			return null;

		return Long.valueOf(value);
	}

	/**
	 * Парсннг блока ConfirmParameters
	 * @param document ответ CSABack
	 * @return параметры подтверждения
	 */
	public static Map<String, Object> getConfirmParameters(Document document) throws TransformerException
	{
		Map<String, Object> params = new HashMap<String, Object>();
		Element confirmParametersElement = XmlHelper.selectSingleNode(document.getDocumentElement(), "confirmParameters");

		params.put(CSAResponseConstants.TIMEOUT_CONFIRM_PARAM_NAME,          getNullSafeLong(XmlHelper.getSimpleElementValue(confirmParametersElement, CSAResponseConstants.TIMEOUT_TAG)));
		params.put(CSAResponseConstants.ATTEMPTS_CONFIRM_PARAM_NAME,         getNullSafeLong(XmlHelper.getSimpleElementValue(confirmParametersElement, CSAResponseConstants.ATTEMPTS_TAG)));
		params.put(CSAResponseConstants.PASSWORD_NUMBER_CONFIRM_PARAM_NAME,  XmlHelper.getSimpleElementValue(confirmParametersElement, CSAResponseConstants.PASSWORD_NUMBER_TAG));
		params.put(CSAResponseConstants.RECEIPT_NUMBER_CONFIRM_PARAM_NAME,   XmlHelper.getSimpleElementValue(confirmParametersElement, CSAResponseConstants.RECEIPT_NUMBER_TAG));
		params.put(CSAResponseConstants.PASSWORDS_LEFT_CONFIRM_PARAM_NAME,   getNullSafeLong(XmlHelper.getSimpleElementValue(confirmParametersElement, CSAResponseConstants.PASSWORDS_LEFT_TAG)));

		return params;
	}

	/**
	 * Парсннг блока SessionInfo
	 * @param document ответ CSABack
	 * @return Информация о сессии
	 */
	public static SessionInfo getSessionInfo(Document document) throws TransformerException
	{
		SessionInfo sessionInfo = new SessionInfo();
		Element sessionInfoElement = XmlHelper.selectSingleNode(document.getDocumentElement(), CSAResponseConstants.SESSION_INFO_TAG);
		// инфомации о сессии может и нет быть
		if(sessionInfoElement == null)
			return null;

		sessionInfo.setSID(XmlHelper.getSimpleElementValue(sessionInfoElement, CSAResponseConstants.SID_TAG));

		String creationDateStr = XmlHelper.getSimpleElementValue(sessionInfoElement, CSAResponseConstants.CREATION_DATE_TAG);
		sessionInfo.setCreationDate(XMLDatatypeHelper.parseDateTime(creationDateStr));

		String expireDateStr = XmlHelper.getSimpleElementValue(sessionInfoElement, CSAResponseConstants.EXPIRE_DATE_TAG);
		sessionInfo.setExpireDate(XMLDatatypeHelper.parseDateTime(expireDateStr));

		String prevSessionDateStr = XmlHelper.getSimpleElementValue(sessionInfoElement, CSAResponseConstants.PREV_SESSION_DATE_TAG);
		if(StringHelper.isNotEmpty(prevSessionDateStr))
			sessionInfo.setExpireDate(XMLDatatypeHelper.parseDateTime(prevSessionDateStr));

		String prevSIDStr = XmlHelper.getSimpleElementValue(sessionInfoElement, CSAResponseConstants.PREV_SID_TAG);
		if(StringHelper.isNotEmpty(prevSessionDateStr))
			sessionInfo.setExpireDate(XMLDatatypeHelper.parseDateTime(prevSIDStr));

		return sessionInfo;
	}


	/**
	 * Получить контейнер с параметрами аутентификации из ответа ЦСА, содержащего данные по клиенту
	 * @param response ответ ЦСА
	 * @return заполненный контейнер с данными по клиенту
	 */
	public static AuthParamsContainer getAuthContainer(Document response) throws BackException
	{
		Element responseElement = response.getDocumentElement();
		AuthParamsContainer container = new AuthParamsContainer();
		try
		{
			container.addParameter("SID", XmlHelper.getElementValueByPath(responseElement, SID_PATH));
			container.addParameter("UserId", XmlHelper.getElementValueByPath(responseElement, USER_ID_PATH));
			container.addParameter("CardNumber", XmlHelper.getElementValueByPath(responseElement, CARD_NUMBER_PATH));
			container.addParameter("CB_CODE", XmlHelper.getElementValueByPath(responseElement, CB_CODE_PATH));
			container.addParameter("FirstName", XmlHelper.getElementValueByPath(responseElement, FIRSTNAME_PATH));
			container.addParameter("LastName", XmlHelper.getElementValueByPath(responseElement, SURNAME_PATH));
			container.addParameter("MiddleName", XmlHelper.getElementValueByPath(responseElement, PATRNAME_PATH));
			container.addParameter("PassportNo", XmlHelper.getElementValueByPath(responseElement, PASSPORT_PATH));
			container.addParameter("BirthDate", XmlHelper.getElementValueByPath(responseElement, BIRTH_DATE_PATH));
		}
		catch (TransformerException e)
		{
			throw new BackException(e);
		}

		return container;
	}

	/**
	 * Получить контейнер с данными о способах подтверждения операций клиентом
	 * @param document ответ ЦСА
	 * @return заполненный контейнер о способах подтверждения операций клиентом
	 */
	public static ConfirmationInformation getConfirmationInformation(Document document) throws Exception
	{
		Element confirmationInfoElement = XmlHelper.selectSingleNode(document.getDocumentElement(), CSAResponseConstants.CONFIRMATION_INFO_TAG);
		if(confirmationInfoElement == null)
			return null;

		final ConfirmationInformation information = new ConfirmationInformation();

		information.setPreferredConfirmType(XmlHelper.getSimpleElementValue(confirmationInfoElement, CSAResponseConstants.PREFERRED_CONFIRM_TYPE_TAG));
		information.setPushAllowed(Boolean.valueOf(XmlHelper.getSimpleElementValue(confirmationInfoElement, CSAResponseConstants.PUSH_ALLOWED_TAG)));
		Element cardConfirmationSourceElement = XmlHelper.selectSingleNode(confirmationInfoElement, CSAResponseConstants.CARD_CONFIRMATION_SOURCE_TAG);
		XmlHelper.foreach(cardConfirmationSourceElement, CSAResponseConstants.CARD_NUMBER_TAG, new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				information.addCardConfirmationSource(XmlHelper.getElementText(element));
			}
		});

		return information;
	}

	/**
	 * Получить тип входящего клиента
	 * @param document ответ
	 * @return тип клиента
	 */
	public static UserLogonType getUserLogonType(Document document)
	{
		String res = XmlHelper.getSimpleElementValue(document.getDocumentElement(), CSAResponseConstants.USER_LOGON_TYPE);
		return StringHelper.isNotEmpty(res) ? UserLogonType.valueOf(res) : null;
	}

	public static Boolean getUserRegistered(Document document)
	{
		String profileId = XmlHelper.getSimpleElementValue(document.getDocumentElement(), CSAResponseConstants.PROFILE_ID_TAG);
		return profileId != null;
	}
}
