package com.rssl.phizic.test.wsgateclient.esberib;

import com.rssl.phizic.test.wsgateclient.esberib.generated.*;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.esberibgate.messaging.RequestHelperBase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.math.BigDecimal;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerException;

/**
 * @author osminin
 * @ created 13.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class UpdateSecurityDicClientTest extends EsbEribBackServiceClientBase
{
	public String createDefaultRequest()
	{
		try
		{
			DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
			Document request = documentBuilder.newDocument();

			Element root = request.createElement("SecDicInfoRq");

			XmlHelper.appendSimpleElement(root, "RqUID", RequestHelperBase.generateUUID());
            XmlHelper.appendSimpleElement(root, "RqTm", RequestHelperBase.generateRqTm());
			XmlHelper.appendSimpleElement(root, "OperUID", RequestHelperBase.generateOUUID());
			XmlHelper.appendSimpleElement(root, "SPName", SPName_Type.BP_DEPO.getValue());

			Element dictRec = XmlHelper.appendSimpleElement(root, "DictRec");
			XmlHelper.appendSimpleElement(dictRec, "Issuer", "Ёмитент");
			XmlHelper.appendSimpleElement(dictRec, "SecurityName", "Ќаименование ценной бумаги");
			XmlHelper.appendSimpleElement(dictRec, "SecurityNumber", "–егистрационный номер ценной бумаги");
			XmlHelper.appendSimpleElement(dictRec, "SecurityType", "“ип ценной бумаги");
			XmlHelper.appendSimpleElement(dictRec, "SecurityNominal", "ћинимальный номинал выпуска ценной бумаги");
			XmlHelper.appendSimpleElement(dictRec, "SecurityNominalCur", "¬алюта минимального номинала ценной бумаги");
			XmlHelper.appendSimpleElement(dictRec, "InsideCode", "ƒепозитарный  код выпуска ценной бумаги ");
			XmlHelper.appendSimpleElement(dictRec, "IsDelete", "ѕризнак была ли запись удален. True- если да, False Ц во всех остальных случа€х.");

			root.appendChild(dictRec);
			request.appendChild(root);

			return XmlHelper.convertDomToText(request);
		}
		catch(TransformerException e)
		{
			return e.getMessage();
		}
	}

	public String makeResponse(IFXRs_Type result)
	{
		try
		{
			SecDicInfoRs_Type secDicInfoRs_type = result.getSecDicInfoRs();
			DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
			Document secDicInfoRs = documentBuilder.newDocument();
			Element root = secDicInfoRs.createElement("SecDicInfoRs");
			secDicInfoRs.appendChild(root);

			XmlHelper.appendSimpleElement(root, "RqUID", secDicInfoRs_type.getRqUID());
			XmlHelper.appendSimpleElement(root, "RqTm", secDicInfoRs_type.getRqTm());
			XmlHelper.appendSimpleElement(root, "OperUID", secDicInfoRs_type.getOperUID());

			Status_Type status_type = secDicInfoRs_type.getStatus();
			Element status = XmlHelper.appendSimpleElement(root, "Status");
			Long statusCode = status_type.getStatusCode();
			XmlHelper.appendSimpleElement(status, "StatusCode", statusCode.toString());
			if (!statusCode.equals(0L))
				XmlHelper.appendSimpleElement(status, "StatusDesc", status_type.getStatusDesc());
			root.appendChild(status);

			return XmlHelper.convertDomToText(secDicInfoRs);
		}
		catch (TransformerException e)
		{
			return e.getMessage();
		}
	}

	public IFXRq_Type getParameters(String request) throws Exception
	{
		Document secDicInfoRq = XmlHelper.parse(request);
		Element root = secDicInfoRq.getDocumentElement();
		String rqUID = XmlHelper.getSimpleElementValue(root, "RqUID");
		String rqTm = XmlHelper.getSimpleElementValue(root, "RqTm");
		String operUID = XmlHelper.getSimpleElementValue(root, "OperUID");
		String spName = XmlHelper.getSimpleElementValue(root, "SPName");
		SPName_Type spName_type = new SPName_Type(spName);

		NodeList dictRecList = XmlHelper.selectNodeList(root, "DictRec");
		DictRec_Type[] dictRecArray = new DictRec_Type[dictRecList.getLength()];
		for (int i = 0; i < dictRecList.getLength(); i++)
		{
			Element dictRec = (Element) dictRecList.item(i);
			String issuer = XmlHelper.getSimpleElementValue(dictRec, "Issuer");
			String securityName = XmlHelper.getSimpleElementValue(dictRec, "SecurityName");
			String securityNumber = XmlHelper.getSimpleElementValue(dictRec, "SecurityNumber");
			String securityType = XmlHelper.getSimpleElementValue(dictRec, "SecurityType");

			String securityNominalVal = XmlHelper.getSimpleElementValue(dictRec, "SecurityNominal");
			BigDecimal securityNominal = new BigDecimal(securityNominalVal);
			String securityNominalCur = XmlHelper.getSimpleElementValue(dictRec, "SecurityNominalCur");

			String insideCode = XmlHelper.getSimpleElementValue(dictRec, "InsideCode");
			boolean isDelete = XmlHelper.getSimpleElementValue(dictRec, "IsDelete").equals("true");

			dictRecArray[i] = new DictRec_Type(issuer,securityName, securityNumber,securityType, securityNominal, securityNominalCur, insideCode, isDelete);
		}

		SecDicInfoRq_Type secDicInfoRq_type = new SecDicInfoRq_Type(rqUID, rqTm, operUID, spName_type, dictRecArray);
		IFXRq_Type ifxRq_type = new IFXRq_Type();
		ifxRq_type.setSecDicInfoRq(secDicInfoRq_type);

		return ifxRq_type;
	}
}
