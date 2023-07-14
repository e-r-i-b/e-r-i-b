package com.rssl.auth.csa.wsclient.requests;

import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static com.rssl.auth.csa.wsclient.RequestConstants.*;

/**
 * Запрос проверки IMSI
 * @author Jatsky
 * @ created 28.01.15
 * @ $Author$
 * @ $Revision$
 */

public class CheckIMSIRequestData extends RequestDataBase
{
	public static final String REQUEST_NAME = "checkIMSIRq";

	private String login;
	private EmployeeInfo employeeInfo;

	public String getName()
	{
		return REQUEST_NAME;
	}

	public CheckIMSIRequestData(String login, EmployeeInfo employeeInfo)
	{
		this.login = login;
		this.employeeInfo = employeeInfo;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, LOGIN_PARAM_NAME, login));
		if (employeeInfo != null)
		{
			root.appendChild(createTag(request, EMPLOYEE_NAME, employeeInfo.getPersonName().getFullName()));
			root.appendChild(createTag(request, EMPLOYEE_GUID, employeeInfo.getGuid()));
		}

		return request;
	}
}
