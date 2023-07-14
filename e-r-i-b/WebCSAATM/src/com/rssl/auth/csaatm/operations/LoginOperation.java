package com.rssl.auth.csaatm.operations;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.BlockingRuleActiveException;
import com.rssl.auth.csa.front.exceptions.BlockingRuleException;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.auth.csa.front.operations.LoginOperationChannelBase;
import com.rssl.phizic.common.types.atm.Constants;
import org.w3c.dom.Document;

import java.util.Map;

/**
 * @author osminin
 * @ created 26.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Операция входа пользователя в АТМ
 */
public class LoginOperation extends LoginOperationChannelBase
{
	private String pan;

	public void initialize(Map<String, Object> data) throws FrontLogicException, FrontException
	{
		super.initialize(data);
		pan = (String) data.get(Constants.PAN_FIELD);
	}

	@Override
	protected void checkRestrict(Map<String, Object> data) throws FrontException, FrontLogicException
	{
	}

	@Override
	protected Document doRequest() throws FrontException, FrontLogicException
	{
		try
		{
			return CSABackRequestHelper.sendStartCreateATMSessionRq(pan);
		}
		catch (BlockingRuleActiveException e)
		{
			throw new BlockingRuleException(e.getMessage(), e);
		}
		catch (BackLogicException e)
		{
			throw new FrontLogicException(e.getMessage(), e);
		}
		catch (BackException e)
		{
			throw new FrontException(e);
		}
	}
}
