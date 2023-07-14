package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author osminin
 * @ created 20.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ������ �� ���������� ������� ���
 */
public class UpdateProfileRequestData extends UserInfoRequestDataBase
{
	private static final String REQUEST_DATA_NAME = "updateProfileRq";

	private UserInfo newUserInfo;
	private UserInfo oldUserInfo;

	/**
	 * ctor
	 * @param newUserInfo ����� ���������� � ������������
	 * @param oldUserInfo ������ ���������� � ������������
	 */
	public UpdateProfileRequestData(UserInfo newUserInfo, UserInfo oldUserInfo)
	{
		if (newUserInfo == null)
		{
			throw new IllegalArgumentException("����� ���������� � ������������ �� ����� ���� null");
		}
		if (oldUserInfo == null)
		{
			throw new IllegalArgumentException("������ ���������� � ������������ �� ����� ���� null");
		}
		this.newUserInfo = newUserInfo;
		this.oldUserInfo = oldUserInfo;
	}

	public String getName()
	{
		return REQUEST_DATA_NAME;
	}

	public Document getBody()
	{
		Document document = createRequest();
		Element root = document.getDocumentElement();

		fillUserInfo(XmlHelper.appendSimpleElement(root, RequestConstants.NEW_USER_INFO_TAG), newUserInfo);
		fillUserInfo(XmlHelper.appendSimpleElement(root, RequestConstants.OLD_USER_INFO_TAG), oldUserInfo);

		return document;
	}
}
