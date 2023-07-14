package com.rssl.auth.csa.front.operations;

import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author osminin
 * @ created 22.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Базовая операция входа в канал через ЦСА
 */
public abstract class LoginOperationChannelBase extends InterchangeCSABackOperationBase
{
	private String host;
	private String token;

	/**
	 * @return хост
	 */
	public String getHost()
	{
		return host;
	}

	/**
	 * @return токен аутентификации
	 */
	public String getToken()
	{
		return token;
	}

	@Override
	protected void processResponse(Document response) throws FrontException, FrontLogicException
	{
		Element element = response.getDocumentElement();
		host = XmlHelper.getSimpleElementValue(element, "host");
		token = XmlHelper.getSimpleElementValue(element, "OUID");
	}
}
