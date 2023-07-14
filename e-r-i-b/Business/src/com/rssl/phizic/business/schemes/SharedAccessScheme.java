package com.rssl.phizic.business.schemes;

/**
 * @author Kosyakov
 * @ created 14.04.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 4093 $
 */
public class SharedAccessScheme extends AccessSchemeBase
{
	private String key;

	/**
	 * Код схемы
	 */
	public String getKey ()
	{
		return key;
	}

	/**
	 * Код схемы
	 */
	public void setKey ( String key )
	{
		this.key = key;
	}

	public boolean canDelete()
	{
		return !getCategory().equals(AccessCategory.CATEGORY_SYSTEM);
	}

	public String toString()
	{
		return "[" + key + "]" + super.toString();
	}
}
