package com.rssl.phizicgate.cpfl.mock;

import com.rssl.phizgate.common.messaging.mock.FilenameRequestHandler;
import com.rssl.phizgate.common.messaging.mock.MockRequestHandler;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;
import javax.xml.transform.TransformerException;

/**
 * @author krenev
 * @ created 16.02.2011
 * @ $Author$
 * @ $Revision$
 * ��������-���������� �������� � ����
 */
public class MockRequestProcessor
{
	private static long counter = 0;

	private static final Map<String, MockRequestHandler> succsessHandlers = new HashMap<String, MockRequestHandler>();
	private static final MockRequestHandler errorHandler;

	static
	{
		try
		{
			succsessHandlers.put("recipientListDemand_q", new RecipientListDemandHandler());
			succsessHandlers.put("preparePaymentDemand_q", new PreparePaymentDemandHandler());
			succsessHandlers.put("typePaymentList_q", new TypePaymentListHandler());
			errorHandler = new FilenameRequestHandler("com/rssl/phizicgate/cpfl/messaging/examples/xml/error_a.xml");
		}
		catch (GateException e)
		{
			throw new RuntimeException("������ ��� ������������� �������� ����", e);
		}
	}

	/**
	 * ���������� ���������
	 * @param message ���������
	 * @return �����.
	 */
	public static String processMessage(String message) throws GateException
	{
		//�������� DOM-������������� �������.
		Document request;
		try
		{
			request = XmlHelper.parse(message);
		}
		catch (Exception e)
		{
			throw new GateException("������ �������� �������", e);
		}

		String requestType = getRequestType(request);//�������� ��� �������
		MockRequestHandler requestHandler = getRequestHandler(requestType); //�������� ���������� �������

		//������������ ������
		try
		{
			return XmlHelper.convertDomToText(requestHandler.proccessRequest(request));
		}
		catch (TransformerException e)
		{
			throw new GateException("������ ��� ����������� ������ � ������", e);
		}
	}

	/**
	 * �������� ��� �������
	 * ������ ����� ���:
	 * <message>
	 *    <messageId>...</messageId>
	 *    <messageDate>...</messageDate>
	 *    <fromAbonent>...</fromAbonent>
	 *    <���_�������>...</���_�������>
	 * </message>
	 * @param request ������
	 * @return ��� �������(4 ������� � ���������)
	 * @throws GateException
	 */
	private static String getRequestType(Document request) throws GateException
	{
		try
		{
			NodeList childNodes = XmlHelper.selectNodeList(request.getDocumentElement(), "/message/*");
			if (childNodes.getLength() < 4)
			{
				throw new GateException("������������ ������ ������� �������");
			}
			return childNodes.item(3).getNodeName();
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}

	private static MockRequestHandler getRequestHandler(String requestType) throws GateException
	{
		MockRequestHandler requestHandler = succsessHandlers.get(requestType);
		if (requestHandler == null)
		{
			throw new GateException("���������������� ��� ������� " + requestType);
		}
		//������ ��������� ��������� �����.
		counter++;
		if (counter % 10 == 5)
		{
			//�� ������ 10 ������ ���������� �����-������.
			return errorHandler;
		}
		return requestHandler;
	}
}
