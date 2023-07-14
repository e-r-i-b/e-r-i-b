package com.rssl.phizicgate.cpfl.mock;

import com.rssl.phizgate.common.messaging.mock.MockRequestHandler;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author krenev
 * @ created 18.02.2011
 * @ $Author$
 * @ $Revision$
 * ������� ����� ������������ �������� ����.
 * ��������� ���������� �������� ��������:
 * 1) messageId
 * 2) messageDate
 * 3) fromAbonent
 */
public abstract class MockRequestHandlerBase implements MockRequestHandler
{
	public final Document proccessRequest(Document request) throws GateException
	{
		//�������� �����
		Document responce = doProcessRequest(request);
		try
		{
			Element requestRoot = request.getDocumentElement();
			//�������� ������������ messageId
			String originalMessageId = XmlHelper.getSimpleElementValue(requestRoot, "messageId");
			//�������� ������������ messageDate
			String originalMessageDate = XmlHelper.getSimpleElementValue(requestRoot, "messageDate");
			//�������� ������������ fromAbonent
			String originalFromAbonent = XmlHelper.getSimpleElementValue(requestRoot, "fromAbonent");

			Element responceRoot = responce.getDocumentElement();
			//��������� messageId, messageDate,fromAbonent ���������� �� ��������
			Element element = XmlHelper.selectSingleNode(responceRoot, "//messageId");
			element.setTextContent(originalMessageId);
			element = XmlHelper.selectSingleNode(responceRoot, "//messageDate");
			element.setTextContent(originalMessageDate);
			element = XmlHelper.selectSingleNode(responceRoot, "//fromAbonent");
			element.setTextContent(originalFromAbonent);
			return responce;
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	/**
	 * ���������� ������.
	 * @param request ������
	 * @return �����
	 * @throws GateException
	 */
	public abstract Document doProcessRequest(Document request) throws GateException;
}
