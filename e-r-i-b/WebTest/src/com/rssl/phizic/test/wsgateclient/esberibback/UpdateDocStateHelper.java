package com.rssl.phizic.test.wsgateclient.esberibback;

import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.GregorianCalendar;
import javax.xml.parsers.DocumentBuilder;

/**
 * @author mihaylov
 * @ created 07.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class UpdateDocStateHelper
{
	public static Document getUpdateDocStateRequest(String[] executeIds, String[] rejectIds)
	{
		DocumentBuilder builder = XmlHelper.getDocumentBuilder();
		Document document = builder.newDocument();
		Element root = document.createElement("DocStateUpdateRq");
		document.appendChild(root);
		XmlHelper.appendSimpleElement(root,"RqUID",new RandomGUID().getStringValue());
		XmlHelper.appendSimpleElement(root,"RqTm", XMLDatatypeHelper.formatDateTimeWithoutTimeZone(new GregorianCalendar()));
		XmlHelper.appendSimpleElement(root,"OperUID",generateOUUID());
		XmlHelper.appendSimpleElement(root,"SPName","BP_ERIB");
		//документы, статусы которых изменились
		for(String executeId : executeIds)
		{
			appendDocumentBlock(root,executeId,"0");
		}
		for(String rejectedId : rejectIds)
		{
			appendDocumentBlock(root,rejectedId,"-10");
		}
		
		return document;
	}

	private static void appendDocumentBlock(Element root, String docNumber, String statusCode)
	{				
		Element documentElement = XmlHelper.appendSimpleElement(root,"Document");
		XmlHelper.appendSimpleElement(documentElement,"DocNumber",docNumber);
		Element bankInfo = XmlHelper.appendSimpleElement(documentElement,"BankInfo");
		//TODO пока не понял зачем это поле при работе с документами, его не использую
		//TODO поэтому заполняю так
		XmlHelper.appendSimpleElement(bankInfo,"RbTbBrchId","12345678");
		Element status = XmlHelper.appendSimpleElement(documentElement,"Status");
		XmlHelper.appendSimpleElement(status,"StatusCode",statusCode);
		if(!"0".equals(statusCode))
			XmlHelper.appendSimpleElement(status,"StatusDesc","Тестовый отказ");
	}

	private static String generateOUUID()
	{
		String uuid = new RandomGUID().getStringValue();
		return "AA" + uuid.substring(2);
	}
}
