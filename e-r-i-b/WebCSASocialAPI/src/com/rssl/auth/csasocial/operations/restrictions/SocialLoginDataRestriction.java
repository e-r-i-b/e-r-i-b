package com.rssl.auth.csasocial.operations.restrictions;

import com.rssl.auth.csasocial.exceptions.LoginOrPasswordWrongLoginException;
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
 * ����������� �� ������������� ������
 */
public class SocialLoginDataRestriction implements SocialDataRestriction
{
	public boolean accept(Map<String, Object> data) throws FrontException, FrontLogicException
	{
		String    login     = (String) data.get("login");
		CSAConfig csaConfig = ConfigFactory.getConfig(CSAConfig.class);

		if (!(csaConfig.getLoginPattern().matcher(login).matches() || csaConfig.getAliasPattern().matcher(login).matches()))
		{
			throw new LoginOrPasswordWrongLoginException("������ ������������ �� ������ � �������. ����������, ��������� ��������� �������������.");
		}
		return true;
	}
}
