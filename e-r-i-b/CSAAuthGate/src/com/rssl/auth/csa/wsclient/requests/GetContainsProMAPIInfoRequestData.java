package com.rssl.auth.csa.wsclient.requests;

import com.rssl.phizic.gate.csa.UserInfo;
import org.w3c.dom.Document;

/**
 * @author osminin
 * @ created 16.04.15
 * @ $Author$
 * @ $Revision$
 *
 * ������ �� ��������� ���������� � ������� ���� ���-������ � �������
 */
public class GetContainsProMAPIInfoRequestData extends UserInfoRequestDataBase
{
	private static final String REQUEST_DATA_NAME = "getContainsProMAPIInfoRq";

	private UserInfo userInfo;

	/**
	 * ctor
	 * @param userInfo ���������� � ������������
	 */
	public GetContainsProMAPIInfoRequestData(UserInfo userInfo)
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("���������� � ������������ �� ����� ���� null.");
		}
		this.userInfo = userInfo;
	}

	public String getName()
	{
		return REQUEST_DATA_NAME;
	}

	public Document getBody()
	{
		Document document = createRequest();
		fillUserInfo(document.getDocumentElement(), userInfo);

		return document;
	}
}
