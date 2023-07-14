package com.rssl.auth.csasocial.operations;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.FailureIdentificationException;
import com.rssl.auth.csa.wsclient.exceptions.InvalidCodeConfirmException;
import com.rssl.auth.csasocial.exceptions.RegistrationErrorException;
import com.rssl.auth.csasocial.exceptions.WrongCodeConfirmException;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.phizic.common.types.mobile.Constants;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.DocumentConfig;
import org.w3c.dom.Document;

import java.util.Map;

/**
 * @author osminin
 * @ created 29.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * ќпераци€ подтверждени€ и завершени€ регистрации
 */
public class ConfirmAndFinishRegistrationOperation extends FinishRegistrationOperation
{
	private String smsPassword;

	@Override
	public void initialize(Map<String, Object> data) throws FrontLogicException, FrontException
	{
		super.initialize(data);

		smsPassword = (String) data.get(Constants.SMS_PASSWORD_FIELD);
	}

	protected Document doRequest() throws FrontLogicException, FrontException
	{
		sendConfirmRequest();

		return super.doRequest();
	}

	protected void sendConfirmRequest() throws FrontException, FrontLogicException
	{
		try
		{
			CSABackRequestHelper.sendConfirmOperationRq(guid, smsPassword);
		}
		catch (InvalidCodeConfirmException e)
		{
			throw new WrongCodeConfirmException(ConfigFactory.getConfig(DocumentConfig.class).getInvalidConfirmCodeRequest(), e.getTime(), e.getAttempts(), e);
		}
		catch (FailureIdentificationException e)
		{
			throw new RegistrationErrorException(e.getMessage(), e);
		}
		catch (BackException e)
		{
			throw new FrontException(e);
		}
		catch (BackLogicException e)
		{
			throw new FrontLogicException(e.getMessage(), e);
		}
	}
}
