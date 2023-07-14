package com.rssl.phizic.test.way4u;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.gate.mobilebank.UserInfo;
import com.rssl.phizgate.mobilebank.MobileBankConfig;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.source.JDBCActionExecutor;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MapUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.mobilebank.csa.GetClientByCardNumberJDBCAction;
import com.rssl.phizicgate.mobilebank.csa.GetClientByLoginJDBCAction;
import com.rssl.phizicgate.way4u.UserInfoImpl;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Map;

/**
 * @author krenev
 * @ created 01.10.2013
 * @ $Author$
 * @ $Revision$
 * Обработчик запросов - заглушка
 */

public class MessageProcessor
{
	private final JDBCActionExecutor executor2 = new JDBCActionExecutor("jdbc/MobileBank2", System.jdbc);
	private static final MessageProcessor instance = new MessageProcessor();

	/**
	 * @return инстанс обработчика запросов.
	 */
	public static MessageProcessor getInstance()
	{
		return instance;
	}

	/**
	 * обработать запрос и вернуть результат
	 * @param message запрос
	 * @return результат
	 */
	public String process(String message) throws Exception
	{
		Document document = XmlHelper.parse(message);

		UserInfo userInfo;

		Element root = document.getDocumentElement();
		String customCode = XmlHelper.getElementValueByPath(root, "/UFXMsg/MsgData/Information/ObjectFor/ClientIDT/CustomCode");
		if (StringHelper.isNotEmpty(customCode) && customCode.equals("MOBILEBANKING"))
		{
			userInfo = getErmbConnectResponseUserInfo(document);

			if (userInfo != null)
				return SuccessResponseBuilder.getInstance().buildErmbConnectedInfoResponse(document, userInfo);
		}

		userInfo = getUserInfo(document);

		if (userInfo != null)
		{
		    return SuccessResponseBuilder.getInstance().build(document, userInfo);
        }

		return FailureResponseBuilder.getInstance().build(document);
	}

	private UserInfo getUserInfo(Document document) throws Exception
	{
		Element root = document.getDocumentElement();
		String cardNumber = XmlHelper.getElementValueByPath(root, "/UFXMsg/MsgData/Information/ObjectFor/ClientIDT/RefContractNumber");
		if (!StringHelper.isEmpty(cardNumber))
		{

			return (ConfigFactory.getConfig(MobileBankConfig.class).isUseMobilebankAsApp()) ?
					GateSingleton.getFactory().service(MobileBankService.class).getClientByCardNumber(cardNumber) :
					executor2.execute(new GetClientByCardNumberJDBCAction(cardNumber));
		}
		String userId = XmlHelper.getElementValueByPath(root, "/UFXMsg/MsgData/Information/ObjectFor/ClientIDT/CustomIDT");
		if (!StringHelper.isEmpty(userId))
		{
			return (ConfigFactory.getConfig(MobileBankConfig.class).isUseMobilebankAsApp()) ?
					GateSingleton.getFactory().service(com.rssl.phizic.gate.mobilebank.MobileBankService.class).getClientByLogin(userId) :
					executor2.execute(new GetClientByLoginJDBCAction(userId));
		}
		return null;
	}

	private UserInfo getErmbConnectResponseUserInfo(Document document) throws Exception
	{
		Element root = document.getDocumentElement();
		Map<String, String> customIDT = MapUtil.parse(XmlHelper.getElementValueByPath(root, "/UFXMsg/MsgData/Information/ObjectFor/ClientIDT/CustomIDT"), "=", ";");

		UserInfoImpl userInfo = new UserInfoImpl();
		userInfo.setFirstname(customIDT.get("FNAME"));
		userInfo.setSurname(customIDT.get("LNAME"));
		userInfo.setPatrname(customIDT.get("MNAME"));
		userInfo.setBirthdate(DateHelper.parseCalendar(customIDT.get("BDATE"), "yyyy-MM-dd"));
		userInfo.setPassport(customIDT.get("DUL"));

		return userInfo;
	}
}
