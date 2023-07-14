package com.rssl.phizic.rapida;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.XmlHelper;

import javax.xml.parsers.DocumentBuilder;

/**
 * @author hudyakov
 * @ created 25.02.2009
 * @ $Author$
 * @ $Revision$
 */

// заглушка для использования в демонстрационных случаях
public class RapidaDemoOnlineService implements RapidaService
{
	private String [] request;

	public Document sendPayment(String requestStr) throws GateException
	{
		request = requestStr.split("&");

		DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
		Document document = documentBuilder.newDocument();

		Element root = document.createElement("Response");
		document.appendChild(root);
		XmlHelper.appendSimpleElement(root, "Result", "OK");
		XmlHelper.appendSimpleElement(root, "PaymNumb", "1234567890");
		XmlHelper.appendSimpleElement(root, "PaymExtId", getRequestData("PaymExtId"));
		XmlHelper.appendSimpleElement(root, "Description", "Платеж принят получателем. Номер регистрации  у получателя " + getRequestData("PaymSubjTp"));
		XmlHelper.appendSimpleElement(root, "Balance", getRequestData("Amount"));

		return document;

	}

	private String getRequestData(String str)
	{
		for (int i = 0; i < request.length; i++)
		{
			String [] param = request[i].split("=");
			if (param[0].equals(str))
				return param[1];
		}
		return null;
	}

}
