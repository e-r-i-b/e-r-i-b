package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author osminin
 * @ created 31.07.13
 * @ $Author$
 * @ $Revision$
 *
 * ������ �� �������������� ������������ ����� ����
 */
public class FinishCreateMobileSessionRequestData extends RequestDataBase
{
	private static final String REQUEST_DATA_NAME = "finishCreateMobileSessionRq";

	private String ouid;

	/**
	 * �����������
	 * @param ouid ������������� ��������
	 */
	public FinishCreateMobileSessionRequestData(String ouid)
	{
		this.ouid = ouid;
	}

	public String getName()
	{
		return REQUEST_DATA_NAME;
	}

	public Document getBody()
	{
		Document request = createRequest();
		XmlHelper.appendSimpleElement(request.getDocumentElement(), RequestConstants.OUID_PARAM_NAME, ouid);

		return request;
	}
}
