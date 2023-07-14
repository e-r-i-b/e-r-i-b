package com.rssl.phizicgate.csaadmin.service.types;

import com.rssl.phizic.gate.schemes.AccessScheme;
import com.rssl.phizicgate.csaadmin.service.generated.AccessSchemeType;

/**
 * @author akrenev
 * @ created 19.10.13
 * @ $Author$
 * @ $Revision$
 */

public class AccessSchemeImpl implements AccessScheme
{
	private AccessSchemeType scheme;

	/**
	 * конструктор
	 * @param scheme исходная схема
	 */
	public AccessSchemeImpl(AccessSchemeType scheme)
	{
		this.scheme = scheme;
	}

	public Long getId()
	{
		return getExternalId();
	}

	public Long getExternalId()
	{
		return scheme.getExternalId();
	}

	public void setExternalId(Long externalId)
	{
		scheme.setExternalId(externalId);
	}

	public String getName()
	{
		return scheme.getName();
	}

	public void setName(String name)
	{
		scheme.setName(name);
	}

	public String getCategory()
	{
		return scheme.getCategory();
	}

	public void setCategory(String category)
	{
		scheme.setCategory(category);
	}

	public boolean isCAAdminScheme()
	{
		return scheme.isCAAdminScheme();
	}

	public void setCAAdminScheme(boolean caAdminScheme)
	{
		scheme.setCAAdminScheme(caAdminScheme);
	}

	public boolean isVSPEmployeeScheme()
	{
		return scheme.isVSPEmployeeScheme();
	}

	public void setVSPEmployeeScheme(boolean vspEmployeeScheme)
	{
		scheme.setVSPEmployeeScheme(vspEmployeeScheme);
	}

	public boolean isMailManagement()
	{
		return scheme.isMailManagement();
	}

	public void setMailManagement(boolean mailManagement)
	{
		scheme.setMailManagement(mailManagement);
	}
}
