package com.rssl.phizic.business.forms.types;

import com.rssl.phizic.config.PropertyReader;

/**
 * @author gladishev
 * @ created 07.10.2008
 * @ $Author$
 * @ $Revision$
 */

public class SmsBusinessTypesConfig extends BusinessTypesConfig
{
	public SmsBusinessTypesConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected void registerExtendedTypes()
	{
		super.registerExtendedTypes();
		registerType(new DepartmentType() );
		registerType(new ContactMemberType());
		registerType(new SmsAccountType());
		registerType(new SmsCardType());
		registerType(new SmsDepositType());
	}
}
