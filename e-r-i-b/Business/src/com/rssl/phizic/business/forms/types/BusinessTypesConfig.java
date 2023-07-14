package com.rssl.phizic.business.forms.types;

import com.rssl.common.forms.types.SimpleTypesConfig;
import com.rssl.phizic.config.PropertyReader;

/**
 * @author Omeliyanchuk
 * @ created 04.02.2008
 * @ $Author$
 * @ $Revision$
 */

public class BusinessTypesConfig extends SimpleTypesConfig
{
	public BusinessTypesConfig(PropertyReader reader)
	{
		super(reader);
	}

	protected void registerExtendedTypes()
	{
		registerType(new DepartmentType());
		registerType(new ContactMemberType());
		registerType(new AccountType());
		registerType(new CardType());
		registerType(new DepositType());
		registerType(new UserResourceType());
	}
}
