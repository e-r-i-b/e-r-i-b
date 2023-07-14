package com.rssl.phizic.ws.esberiblistener.depo;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringUtils;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerException;

/**
 * @author mihaylov
 * @ created 10.11.2010
 * @ $Author$
 * @ $Revision$
 */

public abstract class EsbEribDocumentUpdaterBase<T>
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String CHECK_DOCUMENT_ERROR = "�������� ������ ����������� ���������. ������ ���� DISPATCHED";
	private static final Long STATUS_CODE = 0L;
	private static final Long ERROR_STATUS_CODE = -1L;

	protected Document request;

	protected EsbEribDocumentUpdaterBase(Document request)
	{
		this.request = request;
	}

	public Document updateDocumentsState() throws TransformerException
	{
		Document response = createResponseDocument(request);
		Element documentElement = request.getDocumentElement();
		NodeList nodes = XmlHelper.selectNodeList(documentElement,"Document");
		for (int i=0; i<nodes.getLength(); i++)
		{
			Element element = (Element) nodes.item(i);
			String docNumber = XmlHelper.getSimpleElementValue(element, "DocNumber");
			Element bankInfo = XmlHelper.selectSingleNode(element,"BankInfo");
			//�� ����� ����� ��� ����, ���������� ��� �������
			String rbTbBrchId = XmlHelper.getSimpleElementValue(bankInfo, "RbTbBrchId");

			Element status = XmlHelper.selectSingleNode(element,"Status");
			String statusCode = XmlHelper.getSimpleElementValue(status, "StatusCode");
			boolean isDocumentExecute = "0".equals(statusCode);
			String reason = null;
			if(!isDocumentExecute)
			{
			   reason = XmlHelper.getSimpleElementValue(status, "StatusDesc");
			}
			String errorDesc = updateDocument(docNumber, reason, isDocumentExecute);

			appendDocumentBlock(response,docNumber,errorDesc, rbTbBrchId);
		}
		return response;
	}

	/**
	 * ����� ��������
	 * @param documentNumber - ����� ���������
	 * @return - ��������
	 */
	protected abstract T getDocument(String documentNumber) throws GateException, GateLogicException;

	/**
	 * �������� ������ ���������
	 * @param document - ��������
	 * @param command - ������ ���������
	 */
	protected abstract void updateDocument(T document, DocumentCommand command) throws GateException, GateLogicException;

	/**
	 * �������� ��������� �� ������ � ��������
	 * @param document ��������
	 * @return ������ � ������� � ������ ������ ��� null ���� �������� ���������
	 */
	protected abstract boolean checkDocument(T document);

	/**
	 * �������� ������ ��������� � �������
	 * @param docNumber - ����� ���������
	 * @param reason - ������� ������
	 * @param execute - ���� true - �� �������� ��������, ����� - �������
	 * @return null � ������ �������� ��������� ������� ���������, ����� ����� ������
	 */
	private String updateDocument(String docNumber, String reason, boolean execute)
	{

		try
		{
			T document = getDocument(docNumber);
			if(document == null)
				return "�� ������ �������� � ������� " + docNumber;

			if (!checkDocument(document))
				return CHECK_DOCUMENT_ERROR;

			DocumentCommand command;
			if(execute)
				command = new DocumentCommand(DocumentEvent.EXECUTE, Collections.EMPTY_MAP);
			else
			{
				Map<String, Object> additionalFields = new HashMap<String, Object>();
				additionalFields.put(DocumentCommand.ERROR_TEXT, reason);
				command = new DocumentCommand(DocumentEvent.REFUSE, additionalFields);
			}
			updateDocument(document, command);
		}
		catch (GateException e)
		{
			return e.getMessage();
		}
		catch (GateLogicException e)
		{
			return e.getMessage();
		}
		return null;
	}

	/**
	 * ������� �������� ���������
	 * @param request - ������
	 * @return response
	 */
	private static Document createResponseDocument(Document request)
	{
		Element requestRoot = request.getDocumentElement();

		DocumentBuilder builder = XmlHelper.getDocumentBuilder();
		Document response = builder.newDocument();
		Element root = response.createElement("DocStateUpdateRs");
		response.appendChild(root);
		XmlHelper.appendSimpleElement(root,"RqUID",XmlHelper.getSimpleElementValue(requestRoot, "RqUID"));
		XmlHelper.appendSimpleElement(root,"RqTm", XMLDatatypeHelper.formatDateTimeWithoutTimeZone(new GregorianCalendar()));
		XmlHelper.appendSimpleElement(root,"OperUID",XmlHelper.getSimpleElementValue(requestRoot, "OperUID"));
		return response;
	}

	private static void appendDocumentBlock(Document document, String docNumber, String statusDesc, String rbTbBrchId)
	{
		Element responseRoot = document.getDocumentElement();
		Element documentElement = XmlHelper.appendSimpleElement(responseRoot,"Document");
		XmlHelper.appendSimpleElement(documentElement,"DocNumber",docNumber);
		Element bankInfo = XmlHelper.appendSimpleElement(documentElement,"BankInfo");
		XmlHelper.appendSimpleElement(bankInfo,"RbTbBrchId", rbTbBrchId);

		Element status = XmlHelper.appendSimpleElement(documentElement,"Status");
		if(statusDesc == null)
			XmlHelper.appendSimpleElement(status,"StatusCode",STATUS_CODE.toString());
		else
		{
			XmlHelper.appendSimpleElement(status,"StatusCode",ERROR_STATUS_CODE.toString());
			XmlHelper.appendSimpleElement(status,"StatusDesc", StringUtils.safeTrunc(statusDesc,255));
		}
	}
}
