package com.rssl.auth.csa.wsclient.requests;

import static com.rssl.auth.csa.wsclient.RequestConstants.*;
import static com.rssl.phizic.context.Constants.*;
import static com.rssl.phizic.context.Constants.DEVICE_TOKEN_FSO_PARAMETER_NAME;
import static com.rssl.phizic.context.Constants.MOBILE_SDK_DATA_PARAMETER_NAME;

import com.rssl.phizic.context.HeaderContext;
import com.rssl.phizic.context.RSAContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author niculichev
 * @ created 16.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class FinishUserRegistrationRequestData extends RequestDataBase
{
	private static final String REQUEST_NAME = "finishUserRegistrationRq";

	private String ouid;
	private String login;
	private String password;
	private boolean notification;

	public FinishUserRegistrationRequestData(String ouid, String login, String password, boolean notification)
	{
		this.ouid = ouid;
		this.login = login;
		this.password = password;
		this.notification = notification;
	}

	public String getName()
	{
		return REQUEST_NAME;
	}

	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, OUID_PARAM_NAME, ouid));
		root.appendChild(createTag(request, LOGIN_PARAM_NAME, login));
		root.appendChild(createTag(request, PASSWORD_PARAM_NAME, password));
		if (!notification)
			root.appendChild(createTag(request, "notification", Boolean.toString(notification)));

		root.appendChild(createRSADataTag(request));
		root.appendChild(createHeaderDataTag(request));
		return request;
	}

	private Element createHeaderDataTag(Document request)
	{
		Element result = request.createElement(HEADER_DATA_NAME);

		result.appendChild(createTag(request, HTTP_ACCEPT_HEADER_NAME,            HeaderContext.getHttpAccept()));
		result.appendChild(createTag(request, HTTP_REFERRER_HEADER_NAME,          HeaderContext.getHttpReferrer()));
		result.appendChild(createTag(request, PAGE_ID_PARAMETER_NAME,             HeaderContext.getPageId()));
		result.appendChild(createTag(request, HTTP_ACCEPT_CHARS_HEADER_NAME,      HeaderContext.getHttpAcceptChars()));
		result.appendChild(createTag(request, HTTP_ACCEPT_ENCODING_HEADER_NAME,   HeaderContext.getHttpAcceptEncoding()));
		result.appendChild(createTag(request, HTTP_ACCEPT_LANGUAGE_HEADER_NAME,   HeaderContext.getHttpAcceptLanguage()));
		result.appendChild(createTag(request, HTTP_USER_AGENT_HEADER_NAME,        HeaderContext.getUserAgent()));

		return result;
	}

	private Element createRSADataTag(Document request)
	{
		Element result = request.createElement(RSA_DATA_NAME);

		result.appendChild(createTag(request, DEVICE_PRINT_PARAMETER_NAME,          RSAContext.getDevicePrint()));
		result.appendChild(createTag(request, DOM_ELEMENTS_PARAMETER_NAME,          RSAContext.getDomElements()));
		result.appendChild(createTag(request, JS_EVENTS_PARAMETER_NAME,             RSAContext.getJsEvents()));
		result.appendChild(createTag(request, DEVICE_TOKEN_COOKIE_PARAMETER_NAME,   RSAContext.getDeviceTokenCookie()));
		result.appendChild(createTag(request, DEVICE_TOKEN_FSO_PARAMETER_NAME,      RSAContext.getDeviceTokenFSO()));
		result.appendChild(createTag(request, MOBILE_SDK_DATA_PARAMETER_NAME,       RSAContext.getMobileSdkData()));

		return result;
	}
}
