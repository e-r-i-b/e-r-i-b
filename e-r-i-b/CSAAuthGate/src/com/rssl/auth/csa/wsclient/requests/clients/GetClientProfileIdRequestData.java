package com.rssl.auth.csa.wsclient.requests.clients;

import com.rssl.auth.csa.wsclient.requests.UserInfoRequestDataBase;
import com.rssl.phizic.gate.csa.UserInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author komarov
 * @ created 14.07.15
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������� �� ��������e �������������� ������� �� ���+���+��+��
 */
public class GetClientProfileIdRequestData extends UserInfoRequestDataBase
{
	private static final String REQUEST_NAME = "getClientProfileIdRq";
	private UserInfo info;

	/**
	 * �����������
	 * @param info ���������� � �������
	 */
	public GetClientProfileIdRequestData(UserInfo info)
	{
		this.info = info;
	}

	public String getName()
	{
		return REQUEST_NAME;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();
		fillUserInfo(root, info);
		return request;
	}
}
