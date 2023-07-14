package com.rssl.phizic.business.operations.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.login.exceptions.LoginOrPasswordWrongLoginExeption;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.csa.CSAConfig;

import java.util.Map;

/**
 * Ограничение на использование логина
 *
 * @author khudyakov
 * @ created 23.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class MobileLoginDataRestriction implements MobileDataRestriction
{
	public boolean accept(Map<String, Object> data) throws BusinessException, BusinessLogicException
	{
		String login = (String) data.get("login");

		CSAConfig csaConfig = ConfigFactory.getConfig(CSAConfig.class);
		if (!(csaConfig.getLoginPattern().matcher(login).matches() || csaConfig.getAliasPattern().matcher(login).matches()))
			throw new LoginOrPasswordWrongLoginExeption("Данный пользователь не найден в системе. Пожалуйста, проверьте указанный идентификатор.");

		return true;
	}
}
