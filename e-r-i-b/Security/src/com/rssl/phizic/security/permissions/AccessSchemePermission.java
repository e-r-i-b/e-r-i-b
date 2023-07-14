package com.rssl.phizic.security.permissions;

import java.security.Permission;

/**
 * @author Evgrafov
 * @ created 14.04.2006
 * @ $Author: dorzhinov $
 * @ $Revision: 45508 $
 */

public class AccessSchemePermission extends Permission
{
	private String schemeKey;

	public AccessSchemePermission(String schemeKey)
	{
		super("<<" + schemeKey + ">>");
		this.schemeKey = schemeKey;
	}

	public String getSchemeKey()
	{
		return schemeKey;
	}

	public boolean implies(Permission permission)
	{
		return false;
	}

	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final AccessSchemePermission that = (AccessSchemePermission) o;

		if (!schemeKey.equals(that.schemeKey)) return false;

		return true;
	}

	public int hashCode()
	{
		return schemeKey.hashCode();
	}

	public String getActions()
	{
		return null;
	}
}