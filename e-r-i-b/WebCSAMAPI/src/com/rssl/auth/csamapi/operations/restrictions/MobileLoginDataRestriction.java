package com.rssl.auth.csamapi.operations.restrictions;

import com.rssl.auth.csamapi.exceptions.LoginOrPasswordWrongLoginException;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.csa.CSAConfig;

import java.util.Map;

/**
 * @author osminin
 * @ created 02.08.13
 * @ $Author$
 * @ $Revision$
 * Ограничение на использование логина
 */
public class MobileLoginDataRestriction implements MobileDataRestriction
{
	public boolean accept(Map<String, Object> data) throws FrontException, FrontLogicException
	{
		String login = (String) data.get("login");

		CSAConfig csaConfig = ConfigFactory.getConfig(CSAConfig.class);
		if (!(csaConfig.getLoginPattern().matcher(login).matches() || csaConfig.getAliasPattern().matcher(login).matches()))
		{
			throw new LoginOrPasswordWrongLoginException("Данный пользователь не найден в системе. Пожалуйста, проверьте указанный идентификатор.");
		}
		return true;
	}
}
