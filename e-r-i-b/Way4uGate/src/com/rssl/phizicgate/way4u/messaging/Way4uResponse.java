package com.rssl.phizicgate.way4u.messaging;

import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author krenev
 * @ created 09.10.2013
 * @ $Author$
 * @ $Revision$
 * ��������� ������������� ������ way4u
 */

public class Way4uResponse
{
	private static final String MSG_ID_XPATH = "/UFXMsg/MsgId";
	private static final String RESP_CODE_XPATH = "/UFXMsg/MsgData/Information/Status/RespCode";
	private static final String RESP_TEXT_XPATH = "/UFXMsg/MsgData/Information/Status/RespText";
	private final String bodyAsString;
	private final Document bodyAsDOM;
	private final String id;
	private final String respCode;
	private final String respText;

	/**
	 * ����������� ������
	 * @param body ���� ������.
	 */
	public Way4uResponse(String body) throws SystemException
	{
		this.bodyAsString = body;
		try
		{
			bodyAsDOM = XmlHelper.parse(body);
			Element root = bodyAsDOM.getDocumentElement();
			id = XmlHelper.getElementValueByPath(root, MSG_ID_XPATH);
			respCode = XmlHelper.getElementValueByPath(root, RESP_CODE_XPATH);
			respText = XmlHelper.getElementValueByPath(root, RESP_TEXT_XPATH);
		}
		catch (Exception e)
		{
			throw new SystemException(e);
		}
	}

	/**
	 * @return ������������� ������
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @return ���� ������ � ��������� �������������
	 */
	public String asString()
	{
		return bodyAsString;
	}

	/**
	 * @return ���� ������ � DOM �������������
	 */
	public Document asDOM()
	{
		return bodyAsDOM;
	}

	/**
	 * @return ��� ������.
	 */
	public String getRespCode()
	{
		return respCode;
	}

	/**
	 * @return �������� ���� ������.
	 */
	public String getRespText()
	{
		return respText;
	}
}
