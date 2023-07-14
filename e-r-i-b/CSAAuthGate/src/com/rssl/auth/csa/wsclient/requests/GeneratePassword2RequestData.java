package com.rssl.auth.csa.wsclient.requests;

import static com.rssl.auth.csa.wsclient.RequestConstants.EMPLOYEE_GUID;
import static com.rssl.auth.csa.wsclient.RequestConstants.EMPLOYEE_NAME;
import static com.rssl.auth.csa.wsclient.RequestConstants.IGNORE_IMSI_CHEK;

import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Map;

/**
 * @ author: Vagin
 * @ created: 09.01.2013
 * @ $Author
 * @ $Revision
 * запрос на генерацию пароля по профилю клиента
 */
public class GeneratePassword2RequestData extends RequestDataBase
{
	public static final String REQUEST_NAME = "generatePassword2Rq";
	private String SURNAME_TAG = "surname";
	private String FIRSTNAME_TAG = "firstname";
	private String PATRNAME_TAG = "patrname";
	private String BIRTHDATE_TAG = "birthdate";
	private String TB_TAG = "tb";
	private String PASSPORT_TAG = "passport";
	private Map<String, String> userInfo;
	private EmployeeInfo employeeInfo;
	private boolean ignoreImsiCheck;

	public String getName()
	{
		return REQUEST_NAME;
	}

	public GeneratePassword2RequestData(Map<String, String> userInfo, EmployeeInfo employeeInfo, boolean ignoreImsiCheck)
	{
		this.userInfo = userInfo;
		this.employeeInfo = employeeInfo;
		this.ignoreImsiCheck = ignoreImsiCheck;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, FIRSTNAME_TAG,  userInfo.get(FIRSTNAME_TAG)));
		root.appendChild(createTag(request, SURNAME_TAG,    userInfo.get(SURNAME_TAG)));
		root.appendChild(createTag(request, PATRNAME_TAG,   userInfo.get(PATRNAME_TAG)));
		root.appendChild(createTag(request, BIRTHDATE_TAG,  userInfo.get(BIRTHDATE_TAG)));
		root.appendChild(createTag(request, TB_TAG,         userInfo.get(TB_TAG)));
		root.appendChild(createTag(request, PASSPORT_TAG,   userInfo.get(PASSPORT_TAG)));
		if (employeeInfo != null)
		{
			root.appendChild(createTag(request, EMPLOYEE_NAME,  employeeInfo.getPersonName().getFullName()));
			root.appendChild(createTag(request, EMPLOYEE_GUID, employeeInfo.getGuid()));
		}
		root.appendChild(createTag(request, IGNORE_IMSI_CHEK, Boolean.toString(ignoreImsiCheck)));

		return request;
	}
}
