package com.rssl.phizic.business.schemes;

/**
 * @author Kosyakov
 * @ created 14.04.2006
 * @ $Author: omeliyanchuk $
 * @ $Revision: 8632 $
 */
public class PersonalAccessScheme extends AccessSchemeBase
{
	public boolean canDelete()
	{
		return false;
	}

	public PersonalAccessScheme()
	{
		super();
	}

	public PersonalAccessScheme(PersonalAccessScheme scheme)
	{
		super(scheme);
	}

}
