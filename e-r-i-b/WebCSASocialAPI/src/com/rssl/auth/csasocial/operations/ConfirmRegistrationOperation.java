package com.rssl.auth.csasocial.operations;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.FailureIdentificationException;
import com.rssl.auth.csa.wsclient.exceptions.InvalidCodeConfirmException;
import com.rssl.auth.csasocial.exceptions.RegistrationErrorException;
import com.rssl.auth.csasocial.exceptions.WrongCodeConfirmException;
import com.rssl.auth.csasocial.operations.restrictions.SocialDataCompositeRestriction;
import com.rssl.auth.csasocial.operations.restrictions.SocialMGUIDDataRestriction;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.front.operations.InterchangeCSABackOperationBase;
import com.rssl.phizic.common.types.mobile.Constants;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.DocumentConfig;
import org.w3c.dom.Document;

import java.util.Map;

/**
 * @author osminin
 * @ created 04.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Операция подтверждения регистрации
 */
public class ConfirmRegistrationOperation extends InterchangeCSABackOperationBase
{
	private String guid;
	private String smsPassword;

	@Override
	public void initialize(Map<String, Object> data) throws FrontLogicException, FrontException
	{
		super.initialize(data);

		guid = (String) data.get(Constants.MGUID_FIELD);
		smsPassword = (String) data.get(Constants.SMS_PASSWORD_FIELD);
	}

	@Override
	protected void checkRestrict(Map<String, Object> data) throws FrontException, FrontLogicException
	{
		new SocialDataCompositeRestriction(new SocialMGUIDDataRestriction()).accept(data);
	}

	@Override
	protected Document doRequest() throws FrontException, FrontLogicException
	{
		try
		{
			return CSABackRequestHelper.sendConfirmOperationRq(guid, smsPassword);
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

	@Override
	protected void processResponse(Document response) throws FrontException, FrontLogicException
	{
	}
}
