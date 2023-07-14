package com.rssl.auth.csa.wsclient.requests;

import com.rssl.auth.csa.wsclient.RequestConstants;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.context.HeaderContext;
import com.rssl.phizic.context.RSAContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import static com.rssl.auth.csa.wsclient.RequestConstants.LOGIN_PARAM_NAME;
import static com.rssl.phizic.context.Constants.*;

/**
 *
 * @author niculichev
 * @ created 19.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class StartRestorePasswordRequestData extends RequestDataBase
{
	public static final String REQUEST_NAME = "startRestorePasswordRq";

	private String login;
	private ConfirmStrategyType confirmStrategyType;

	public StartRestorePasswordRequestData(String login, ConfirmStrategyType confirmStrategyType)
	{
		this.login = login;
		this.confirmStrategyType = confirmStrategyType;
	}

	/**
	 * @return Имя запроса
	 */
	public String getName()
	{
		return REQUEST_NAME;
	}

	/**
	 * @return тело запроса ввиде строки
	 */
	public Document getBody()
	{
		Document request = createRequest();
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, LOGIN_PARAM_NAME, login));
		root.appendChild(createTag(request, RequestConstants.CONFIRMATION_PARAM_NAME, confirmStrategyType.name()));

		appendRsaData(request);
		appendHeaderData(request);

		return request;
	}

	private void appendRsaData(Document request)
	{
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, DEVICE_TOKEN_COOKIE_PARAMETER_NAME, RSAContext.getDeviceTokenCookie()));
		root.appendChild(createTag(request, DOM_ELEMENTS_PARAMETER_NAME, RSAContext.getDomElements()));
		root.appendChild(createTag(request, DEVICE_PRINT_PARAMETER_NAME, RSAContext.getDevicePrint()));
		root.appendChild(createTag(request, JS_EVENTS_PARAMETER_NAME, RSAContext.getJsEvents()));
		root.appendChild(createTag(request, DEVICE_TOKEN_FSO_PARAMETER_NAME, RSAContext.getDeviceTokenFSO()));
	}

	private void appendHeaderData(Document request)
	{
		Element root = request.getDocumentElement();

		root.appendChild(createTag(request, PAGE_ID_PARAMETER_NAME, HeaderContext.getPageId()));
		root.appendChild(createTag(request, HTTP_ACCEPT_LANGUAGE_HEADER_NAME, HeaderContext.getHttpAcceptLanguage()));
		root.appendChild(createTag(request, HTTP_ACCEPT_ENCODING_HEADER_NAME, HeaderContext.getHttpAcceptEncoding()));
		root.appendChild(createTag(request, HTTP_USER_AGENT_HEADER_NAME, HeaderContext.getUserAgent()));
		root.appendChild(createTag(request, HTTP_REFERRER_HEADER_NAME, HeaderContext.getHttpReferrer()));
		root.appendChild(createTag(request, HTTP_ACCEPT_CHARS_HEADER_NAME, HeaderContext.getHttpAcceptChars()));
		root.appendChild(createTag(request, HTTP_ACCEPT_HEADER_NAME, HeaderContext.getHttpAccept()));



	}
}
