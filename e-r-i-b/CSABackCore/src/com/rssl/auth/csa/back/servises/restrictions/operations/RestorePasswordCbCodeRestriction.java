package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.servises.operations.RestorePasswordOperation;
import com.rssl.phizic.config.ConfigFactory;

import java.util.regex.Pattern;

/**
 * @author krenev
 * @ created 16.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class RestorePasswordCbCodeRestriction extends CbCodeRestrictionBase<RestorePasswordOperation>
{
	private static final RestorePasswordCbCodeRestriction INSTANCE = new RestorePasswordCbCodeRestriction();

	public static RestorePasswordCbCodeRestriction getInstance()
	{
		return INSTANCE;
	}

	public String getRestrictionViolatedMessage()
	{
		return "��� ������������� �����, � ������� �� ��������������, �������������� ������ � ������� �������� ����������.";
	}

	public Pattern getTBDenyPattern()
	{
		return ConfigFactory.getConfig(Config.class).getRestorePasswordCbCodeDenyPattern();
	}
}