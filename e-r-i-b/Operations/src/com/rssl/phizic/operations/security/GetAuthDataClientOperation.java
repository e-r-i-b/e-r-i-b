package com.rssl.phizic.operations.security;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.responses.AuthenticationHelper;
import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Map;
import java.util.TreeMap;

import static com.rssl.phizic.context.Constants.*;

/**
 * Операция по получению данных авторизации клиента.
 *
 * @author bogdanov
 * @ created 24.09.2012
 * @ $Author$
 * @ $Revision$
 */

public class GetAuthDataClientOperation extends OperationBase implements GetAuthDataOperation
{
	private AuthData authData = new AuthData();

	public GetAuthDataClientOperation(String authToken, String browserInfo) throws BusinessLogicException, BusinessException
	{
		try
		{
			Document document = CSABackRequestHelper.sendFinishCreateSessionRq(authToken);
			AuthenticationHelper.fillFromERIBCSAData(authData, document, false, browserInfo);
			fillRSAData(document);

			if (LoginType.CSA != authData.getLoginType() && LoginType.TERMINAL != authData.getLoginType() && LoginType.DISPOSABLE != authData.getLoginType())
			{
				throw new BusinessException("Вход в браузерной версии возможен только через CSA или TERMINAL коннекторы, используется тип " + authData.getLoginType());
			}
		}
		catch (BackLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (BackException e)
		{
			throw new BusinessException(e);
		}
	}

	private void fillRSAData(Document document)
	{
		Element element = document.getDocumentElement();
		Map<String, String> rsaData = authData.getRsaData();
		rsaData.put(JS_EVENTS_PARAMETER_NAME, XmlHelper.getSimpleElementValue(element, JS_EVENTS_PARAMETER_NAME));
		rsaData.put(DEVICE_PRINT_PARAMETER_NAME, XmlHelper.getSimpleElementValue(element, DEVICE_PRINT_PARAMETER_NAME));
		rsaData.put(DOM_ELEMENTS_PARAMETER_NAME, XmlHelper.getSimpleElementValue(element, DOM_ELEMENTS_PARAMETER_NAME));
		rsaData.put(DEVICE_TOKEN_FSO_PARAMETER_NAME, XmlHelper.getSimpleElementValue(element, DEVICE_TOKEN_FSO_PARAMETER_NAME));
		rsaData.put(DEVICE_TOKEN_COOKIE_PARAMETER_NAME, XmlHelper.getSimpleElementValue(element, DEVICE_TOKEN_COOKIE_PARAMETER_NAME));

		rsaData.put(HTTP_ACCEPT_HEADER_NAME, XmlHelper.getSimpleElementValue(element, HTTP_ACCEPT_HEADER_NAME));
		rsaData.put(HTTP_ACCEPT_CHARS_HEADER_NAME, XmlHelper.getSimpleElementValue(element, HTTP_ACCEPT_CHARS_HEADER_NAME));
		rsaData.put(HTTP_ACCEPT_ENCODING_HEADER_NAME, XmlHelper.getSimpleElementValue(element, HTTP_ACCEPT_ENCODING_HEADER_NAME));
		rsaData.put(HTTP_ACCEPT_LANGUAGE_HEADER_NAME, XmlHelper.getSimpleElementValue(element, HTTP_ACCEPT_LANGUAGE_HEADER_NAME));
		rsaData.put(HTTP_REFERRER_HEADER_NAME, XmlHelper.getSimpleElementValue(element, HTTP_REFERRER_HEADER_NAME));
		rsaData.put(HTTP_USER_AGENT_HEADER_NAME, XmlHelper.getSimpleElementValue(element, HTTP_USER_AGENT_HEADER_NAME));
		rsaData.put(PAGE_ID_PARAMETER_NAME, XmlHelper.getSimpleElementValue(element, PAGE_ID_PARAMETER_NAME));
	}

	public AuthData getAuthData()
	{
		return authData;
	}

	public TreeMap<String, String> getRsaData()
	{
		return authData.getRsaData();
	}
}
