package com.rssl.common.forms.validators;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.sbnkd.SBNKDConfig;

import java.util.List;

/**
 * проверяем доступен ли ТБ для услуги сбербанк на каждый день
 * @author basharin
 * @ created 11.02.15
 * @ $Author$
 * @ $Revision$
 */

public class AllowedTbForSBNKDValidator extends FieldValidatorBase
{

	public AllowedTbForSBNKDValidator()
	{
		super();
	}

	public AllowedTbForSBNKDValidator(String message)
	{
		super(message);
	}

	public boolean validate(String value)
	{
		List<String> allowedTb = ConfigFactory.getConfig(SBNKDConfig.class).getAllowedTB();
		return allowedTb.contains(value);
	}
}
