package com.rssl.phizic.test.wsgateclient.esberib;

import com.rssl.phizic.test.wsgateclient.esberib.generated.*;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.esberibgate.messaging.RequestHelperBase;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerException;

/**
 * @author osminin
 * @ created 13.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class UpdateDocStateClientTest extends EsbEribBackServiceClientBase
{
	/**
	 * @return запрос, отображаемый по умолчанию с частично заполненными данными
	 */
	public String createDefaultRequest()
	{
		try
		{
			DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
			Document request = documentBuilder.newDocument();

			Element root = request.createElement("DocStateUpdateRq");

			XmlHelper.appendSimpleElement(root, "RqUID", RequestHelperBase.generateUUID());
            XmlHelper.appendSimpleElement(root, "RqTm", RequestHelperBase.generateRqTm());
            XmlHelper.appendSimpleElement(root, "OperUID", RequestHelperBase.generateOUUID());
			XmlHelper.appendSimpleElement(root, "SPName", SPName_Type.AUTOPAY.getValue());

			Element document = XmlHelper.appendSimpleElement(root, "Document");
			XmlHelper.appendSimpleElement(document, "DocNumber", "Введите номер документа");

			Element bankInfo = XmlHelper.appendSimpleElement(document, "BankInfo");
			XmlHelper.appendSimpleElement(bankInfo, "RbTbBrchId", "Введите код территориального банка");

			Element status = XmlHelper.appendSimpleElement(document, "Status");
			XmlHelper.appendSimpleElement(status, "StatusCode", "Введите статусный код возврата");
			XmlHelper.appendSimpleElement(status, "StatusDesc", "Введите описание статуса (Тег указывается, если код статуса не 0)");

			document.appendChild(bankInfo);
			document.appendChild(status);
			root.appendChild(document);
			request.appendChild(root);

			return XmlHelper.convertDomToText(request);
		}
		catch(TransformerException e)
		{
			return e.getMessage();
		}
	}

	public IFXRq_Type getParameters(String request) throws Exception
	{
		Document docStateUpdateRq = XmlHelper.parse(request);
		Element root = docStateUpdateRq.getDocumentElement();
		String rqUID = XmlHelper.getSimpleElementValue(root, "RqUID");
		String rqTm = XmlHelper.getSimpleElementValue(root, "RqTm");
		String operUID = XmlHelper.getSimpleElementValue(root, "OperUID");
		String spName = XmlHelper.getSimpleElementValue(root, "SPName");
		SPName_Type spName_type = new SPName_Type(spName);

		NodeList documentNodeList = XmlHelper.selectNodeList(root, "Document");
		Document_Type[] documentTypeArray = new Document_Type[documentNodeList.getLength()];
		for (int i = 0; i < documentNodeList.getLength(); i++)
		{
			Element document = (Element) documentNodeList.item(i);
			String docNumber = XmlHelper.getSimpleElementValue(document, "DocNumber");

			Element bankInfo = XmlHelper.selectSingleNode(document, "BankInfo");
			String rbTbBrchId = XmlHelper.getSimpleElementValue(bankInfo, "RbTbBrchId");
			BankInfo_Type bankInfo_type = new BankInfo_Type("", "", "", rbTbBrchId, "");

			Element status = XmlHelper.selectSingleNode(document, "Status");
			Long statusCode = Long.decode(XmlHelper.getSimpleElementValue(status, "StatusCode"));
			String statusDesc = XmlHelper.getSimpleElementValue(status, "StatusDesc");
			Status_Type status_type = new Status_Type(statusCode, statusDesc, null, null);

			documentTypeArray[i] = new Document_Type(docNumber, bankInfo_type, status_type);
		}
		DocStateUpdateRq_Type docStateUpdateRq_type = new DocStateUpdateRq_Type(rqUID, rqTm, operUID, spName_type, documentTypeArray);

		IFXRq_Type ifxRq_type = new IFXRq_Type();
		ifxRq_type.setDocStateUpdateRq(docStateUpdateRq_type);
		return ifxRq_type;
	}

	public String makeResponse(IFXRs_Type result)
	{
		try
		{
			DocStateUpdateRs_Type docStateUpdateRs_type = result.getDocStateUpdateRs();
			DocumentBuilder documentBuilder = XmlHelper.getDocumentBuilder();
			Document docStateUpdateRs = documentBuilder.newDocument();
			Element root = docStateUpdateRs.createElement("DocStateUpdateRs");
			docStateUpdateRs.appendChild(root);

			XmlHelper.appendSimpleElement(root, "RqUID", docStateUpdateRs_type.getRqUID());
			XmlHelper.appendSimpleElement(root, "RqTm", docStateUpdateRs_type.getRqTm());
			XmlHelper.appendSimpleElement(root, "OperUID", docStateUpdateRs_type.getOperUID());

			Document_Type[] document_types = docStateUpdateRs_type.getDocument();
			for (int i = 0; i < document_types.length; i++)
			{
				Document_Type document_type = document_types[i];
				Element document = XmlHelper.appendSimpleElement(root, "Document");
				XmlHelper.appendSimpleElement(document, "DocNumber", document_type.getDocNumber());

				BankInfo_Type bankInfo_type = document_type.getBankInfo();
				Element bankInfo = XmlHelper.appendSimpleElement(document, "BankInfo");
				XmlHelper.appendSimpleElement(bankInfo, "RbTbBrchId", bankInfo_type.getRbTbBrchId());
				document.appendChild(bankInfo);

				Status_Type status_type = document_type.getStatus();
				Element status = XmlHelper.appendSimpleElement(document, "Status");
				Long statusCode = status_type.getStatusCode();
				XmlHelper.appendSimpleElement(status, "StatusCode", statusCode.toString());
				if (!statusCode.equals(0L))
					XmlHelper.appendSimpleElement(status, "StatusDesc", status_type.getStatusDesc());
				document.appendChild(status);

				root.appendChild(document);
			}

			return XmlHelper.convertDomToText(docStateUpdateRs);
		}
		catch (TransformerException e)
		{
			return e.getMessage();
		}
	}
}
