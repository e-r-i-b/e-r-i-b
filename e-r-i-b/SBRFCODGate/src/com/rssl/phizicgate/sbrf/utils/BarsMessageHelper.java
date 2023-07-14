package com.rssl.phizicgate.sbrf.utils;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.messaging.GateMessage;
import com.rssl.phizic.gate.messaging.WebBankServiceFacade;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author osminin
 * @ created 16.03.2011
 * @ $Author$
 * @ $Revision$
 * Хелпер, для работы с сообщениями БАРСа
 */
public class BarsMessageHelper
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	/**
	 * Получить наименование получателя из БАРСа
	 * @param factory фабрика
	 * @param personalAcc счет получателя
	 * @param bic БИК получателя
	 * @return наименование получателя
	 */
	public String readRemoteClientName(GateFactory factory, String personalAcc, String bic)
	{
		try
		{
			WebBankServiceFacade serviceFacade = factory.service(WebBankServiceFacade.class);
			// формируем запрос
			GateMessage message = serviceFacade.createRequest("SB001");
			Element benifElement = XmlHelper.appendSimpleElement(message.getDocument().getDocumentElement(), "Benif");
			XmlHelper.appendSimpleElement(benifElement, "PersonalAcc", personalAcc);
			Element bankElement = XmlHelper.appendSimpleElement(benifElement, "Bank");
			XmlHelper.appendSimpleElement(bankElement, "BIC", bic);

			Document response = serviceFacade.sendOnlineMessage(message, null);
			//Наименование полчателя приходит в теге SSName 
			return XmlHelper.getSimpleElementValue(response.getDocumentElement(), "SSName");
		}
		catch (Exception e)
		{
			// Все возникшие ошибки складываем в лог
			log.error(e.getMessage(), e);
			return null;
		}
	}
}
