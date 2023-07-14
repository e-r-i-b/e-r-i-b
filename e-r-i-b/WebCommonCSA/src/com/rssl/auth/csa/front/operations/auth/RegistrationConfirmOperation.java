package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.wsclient.CSABackResponseSerializer;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;

/**
 * Операция на подтверждение регистрации
 * @author niculichev
 * @ created 05.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class RegistrationConfirmOperation extends ConfirmOperationBase
{
	protected void processResponce(Document responce) throws FrontLogicException, FrontException
	{
		try
		{
			((RegistrationOperationInfo) info).setUserInfo(CSABackResponseSerializer.getUserInfo(responce));
		}
		catch (TransformerException e)
		{
			throw new FrontException(e);
		}
	}
}
