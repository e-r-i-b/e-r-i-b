package com.rssl.phizic.security.permissions;

import java.security.Permission;

/**
 * @author Roshka
 * @ created 13.04.2006
 * @ $Author$
 * @ $Revision$
 */

public class ServicePermission extends Permission
{
	private String serviceKey;
    private boolean rigid = false; //является ли разрешение строгим. default - false

	public ServicePermission(String serviceKey)
	{
		super("<< Услуга " + serviceKey + " >>");

		this.serviceKey = serviceKey;
	}

    public ServicePermission(String serviceKey, boolean rigid)
	{
		this(serviceKey);
        this.rigid = rigid;
	}

	public String getServiceKey()
	{
		return serviceKey;
	}

    public boolean isRigid()
    {
        return rigid;
    }

	public boolean implies(Permission permission)
	{
		return false;
	}

	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		final ServicePermission that = (ServicePermission) o;

		if (!serviceKey.equals(that.serviceKey)) return false;

		return true;
	}

	public int hashCode()
	{
		return serviceKey.hashCode();
	}

	public String getActions()
	{
		return null;
	}
}