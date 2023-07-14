package com.rssl.phizic.test.wsgateclient.esberibback;

import com.rssl.phizic.utils.RandomGUID;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.GregorianCalendar;
import javax.xml.parsers.DocumentBuilder;

/**
 * @author lukina
 * @ created 22.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class UpdateSecurityDictionaryHelper
{
	public static Document getSecDicInfoRequest(String issuer, String securityName, String securityNumber, String securityType,String securityNominal,String securityNominalCur,String  insideCode, String isDelete)
	{
		DocumentBuilder builder = XmlHelper.getDocumentBuilder();
		Document document = builder.newDocument();
		Element root = document.createElement("SecDicInfoRq");
		document.appendChild(root);
		XmlHelper.appendSimpleElement(root,"RqUID",new RandomGUID().getStringValue());
		XmlHelper.appendSimpleElement(root,"RqTm", XMLDatatypeHelper.formatDateTimeWithoutTimeZone(new GregorianCalendar()));
		XmlHelper.appendSimpleElement(root,"OperUID",generateOUUID());
		XmlHelper.appendSimpleElement(root,"SPName","BP_ERIB");

		Element allowedOperations = XmlHelper.appendSimpleElement(root, "DictRec");
		XmlHelper.appendSimpleElement(allowedOperations,"Issuer",issuer);
		XmlHelper.appendSimpleElement(allowedOperations,"SecurityName",securityName);
		XmlHelper.appendSimpleElement(allowedOperations,"SecurityNumber",securityNumber);
		XmlHelper.appendSimpleElement(allowedOperations,"SecurityType",securityType);
		XmlHelper.appendSimpleElement(allowedOperations,"SecurityNominal",securityNominal);
		XmlHelper.appendSimpleElement(allowedOperations,"SecurityNominalCur",securityNominalCur);
		XmlHelper.appendSimpleElement(allowedOperations,"InsideCode",insideCode);
		XmlHelper.appendSimpleElement(allowedOperations,"IsDelete",isDelete);

		return document;
	}


	private static String generateOUUID()
	{
		String uuid = new RandomGUID().getStringValue();
		return "AA" + uuid.substring(2);
	}
}
