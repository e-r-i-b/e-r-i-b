package com.rssl.auth.csa.wsclient.requests;

import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static com.rssl.auth.csa.wsclient.RequestConstants.*;

/**
 * запрос на генерацию пароля
 * @author basharin
 * @ created 13.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class GeneratePasswordRequestData extends RequestDataBase
{
	public static final String REQUEST_NAME = "generatePasswordRq";

	private String login;
	private EmployeeInfo employeeInfo;
	private boolean ignoreImsiCheck;

	public String getName()
	{
		return REQUEST_NAME;
	}

	public GeneratePasswordRequestData(String login, EmployeeInfo employeeInfo, boolean ignoreImsiCheck)
	{
		this.login = login;
		this.employeeInfo = employeeInfo;
		this.ignoreImsiCheck = ignoreImsiCheck;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, LOGIN_PARAM_NAME, login));
		if (employeeInfo != null)
		{
			root.appendChild(createTag(request, EMPLOYEE_NAME,  employeeInfo.getPersonName().getFullName()));
			root.appendChild(createTag(request, EMPLOYEE_GUID, employeeInfo.getGuid()));
			root.appendChild(createTag(request, IGNORE_IMSI_CHEK, Boolean.toString(ignoreImsiCheck)));
		}

		return request;
	}
}
