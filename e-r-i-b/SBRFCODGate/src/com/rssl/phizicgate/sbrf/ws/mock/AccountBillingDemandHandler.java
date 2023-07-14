package com.rssl.phizicgate.sbrf.ws.mock;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.impl.DefaultErrorMessageHandler;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.Constants;

import java.util.Date;
import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import javax.xml.transform.TransformerException;

/**
 * @author Roshka
 * @ created 19.09.2006
 * @ $Author$
 * @ $Revision$
 */

public class AccountBillingDemandHandler extends MockHandlerSupport
{
	private static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	protected Document makeMockRequest(Document message, String parentMessageId) throws Exception
	{
		Calendar operday = getOperday();

		Element root = message.getDocumentElement();
		Calendar startDate = readNodeDate(root, "/billingDemand_q/startDate");
		if (startDate.after(operday))
			return makeClientError(formatInvalidStartDateMessage(operday), parentMessageId);

		Element requestAccount = XmlHelper.selectSingleNode(message.getDocumentElement(), "/billingDemand_q/account");
		Element copying = XmlHelper.selectSingleNode(message.getDocumentElement(), "/billingDemand_q/copying");
		Document response = null;
		if(copying!=null && "true".equals(copying.getTextContent()))
		{
			response = XmlHelper.loadDocumentFromResource("com/rssl/phizicgate/sbrf/ws/mock/xml/billingc_a.xml");
		}
		else
		{
			response = XmlHelper.loadDocumentFromResource("com/rssl/phizicgate/sbrf/ws/mock/xml/billing_a.xml");
		}
		Element responseAccount = XmlHelper.selectSingleNode(response.getDocumentElement(), "/message/billing_a/account");
		responseAccount.setTextContent(requestAccount.getTextContent());

		String currencyDigitalCode = "";
		try
		{
			currencyDigitalCode = requestAccount.getTextContent().substring(5, 8);
		}
		catch (Exception e)
		{
			log.error("Ќевозможно получить код валюты дл€ счета " + requestAccount.getTextContent(), e);
		}

		String currCode;
		//ƒл€ заглушки должно хватить трех вариантов
		if (currencyDigitalCode.equals("978"))
		{
			currCode = "EUR";
		}
		else if (currencyDigitalCode.equals("840"))
		{
			currCode = "USD";
		}
		else
		{
			currCode = "RUR";
		}

		Element currencyCode = XmlHelper.selectSingleNode(response.getDocumentElement(), "/message/billing_a/currencyCode");
		currencyCode.setTextContent(currCode);

		return response;
	}

	private Calendar getOperday()
	{
		Calendar operday = Calendar.getInstance();
		// ≈сли текущее врем€ меньше 13 дн€, берЄм вчерашний опердень
        if (operday.get(Calendar.HOUR_OF_DAY)<13)
        operday.add(Calendar.DATE, -1);
		operday.set(Calendar.HOUR_OF_DAY, 0);
		operday.set(Calendar.MINUTE, 0);
		operday.set(Calendar.SECOND, 0);
		operday.set(Calendar.MILLISECOND, 0);
		return operday;
	}

	private Document makeClientError(String errorMessage, String parentMessageId) throws GateException
	{
		ErrorAHandler handler = new ErrorAHandler(
				DefaultErrorMessageHandler.ERROR_CLIENT_CODE,
				errorMessage
		);

		// ErrorAHandler не использует параметры
		return handler.makeRequest(null, null, parentMessageId);
	}

	private String formatInvalidStartDateMessage(Calendar operday)
	{
		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		return "ƒата начала периода выписки должна быть меньше либо равна "
				+ dateFormat.format(operday.getTime())
				+ " .";
	}

	private Calendar readNodeDate(Element root, String dateElementName) throws TransformerException
	{
		Element node = XmlHelper.selectSingleNode(root, dateElementName);
		if (node != null) {
			try {
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = dateFormat.parse(XmlHelper.getElementText(node));
				return DateHelper.toCalendar(date);
			} catch (ParseException ignored) {
				return null;
			}
		}
		return null;
	}
}
