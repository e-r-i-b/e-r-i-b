package com.rssl.auth.csa.back.servises.operations.confirmations;

/**
 * Стратегия подтверждения: без стратегии подтверждения.
 *
 * @author bogdanov
 * @ created 04.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class NoConfirmStrategy implements ConfirmStrategy<Object, ConfirmationInfo>
{
	public void initialize() throws Exception
	{
	}

	public Object getConfirmCodeInfo()
	{
		return null;  
	}

	public void publishCode() throws Exception
	{
	}

	public void checkConfirmAllowed()
	{
	}

	public boolean check(String password) throws Exception
	{
		return true;
	}

	public boolean isFailed()
	{
		return false;
	}

	public ConfirmationInfo getConfirmationInfo()
	{
		return null; 
	}
}
