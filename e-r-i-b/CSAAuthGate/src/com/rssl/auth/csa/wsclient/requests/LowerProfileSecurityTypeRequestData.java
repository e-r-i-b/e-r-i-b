package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

/**
 * @author osminin
 * @ created 05.02.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ �� ���������� ������� ������� ������ ������������
 */
public class LowerProfileSecurityTypeRequestData extends UserInfoRequestDataBase
{
	private static final String REQUEST_DATA_NAME = "lowerProfileSecurityTypeRq";

	private List<UserInfo> history;

	/**
	 * ctor
	 * @param history ������� ��������� ������ �������
	 */
	public LowerProfileSecurityTypeRequestData(List<UserInfo> history)
	{
		if (history == null)
		{
			throw new IllegalArgumentException("���������� � ������������ �� ����� ���� null");
		}
		this.history = history;
	}

	public String getName()
	{
		return REQUEST_DATA_NAME;
	}

	public Document getBody()
	{
		Document document = createRequest();
		Element element = document.getDocumentElement();

		Element historyElement = XmlHelper.appendSimpleElement(element, RequestConstants.USER_INFO_LIST_TAG);
		for (UserInfo userInfo : history)
		{
			Element userInfoElement = XmlHelper.appendSimpleElement(historyElement, RequestConstants.USER_INFO_TAG);
			fillUserInfo(userInfoElement, userInfo);
		}

		return document;
	}
}
