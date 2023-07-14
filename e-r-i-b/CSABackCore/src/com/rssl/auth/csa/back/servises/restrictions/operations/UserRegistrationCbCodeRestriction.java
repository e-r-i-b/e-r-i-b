package com.rssl.auth.csa.back.servises.restrictions.operations;

import com.rssl.auth.csa.back.Config;
import com.rssl.phizic.config.ConfigFactory;

import java.util.regex.Pattern;

/**
 * @author krenev
 * @ created 16.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class UserRegistrationCbCodeRestriction extends CbCodeRestrictionBase
{
	private static final UserRegistrationCbCodeRestriction INSTANCE = new UserRegistrationCbCodeRestriction();
	private static final String CB_CODE_RESTRICTION_MESSAGE =
			"К сожалению, для регионального подразделения банка, которое выпустило вашу карту, регистрация онлайн пока недоступна. " +
			"Попробуйте пройти процесс регистрации заново с другой активной картой. Или обратитесь в Контактный центр Сбербанка за помощью: +7(495)500 5550, 8(800)555 5550.";

	public static UserRegistrationCbCodeRestriction getInstance()
	{
		return INSTANCE;
	}

	public String getRestrictionViolatedMessage()
	{
		return CB_CODE_RESTRICTION_MESSAGE;
	}

	public Pattern getTBDenyPattern()
	{
		return ConfigFactory.getConfig(Config.class).getUserRegistrationCbCodeDenyPattern();
	}
}
