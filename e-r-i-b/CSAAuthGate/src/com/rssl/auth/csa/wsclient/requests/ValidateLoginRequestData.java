package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.requests.info.ValidateLoginInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static com.rssl.auth.csa.wsclient.RequestConstants.*;

/**
 * ������ ������� �� ��������� ������ ������ � ������ ������
 * @author niculichev
 * @ created 28.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class ValidateLoginRequestData extends RequestDataBase
{
	public static final String REQUEST_NAME = "validateLoginRq";

	private ValidateLoginInfo validateLoginInfo;

	/**
	 * ctor
	 * @param validateLoginInfo ���������� ��� ��������� ������
	 */
	public ValidateLoginRequestData(ValidateLoginInfo validateLoginInfo)
	{
		if (validateLoginInfo == null)
		{
			throw new IllegalArgumentException("���������� � ��������� ������ �� ����� ���� null");
		}
		this.validateLoginInfo = validateLoginInfo;
	}

	public String getName()
	{
		return REQUEST_NAME;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, SID_PARAM_NAME, validateLoginInfo.getSid()));
		root.appendChild(createTag(request, LOGIN_PARAM_NAME, validateLoginInfo.getLogin()));
		root.appendChild(createTag(request, SAME_LOGIN_PARAM_NAME, Boolean.toString(validateLoginInfo.isSameLogin())));
		root.appendChild(createTag(request, CHECK_PASSWORD_PARAM_NAME, Boolean.toString(validateLoginInfo.isCheckPassword())));

		return request;
	}
}
