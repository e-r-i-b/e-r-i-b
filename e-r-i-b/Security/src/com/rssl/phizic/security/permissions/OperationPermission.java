package com.rssl.phizic.security.permissions;

import java.security.Permission;

/** Created by IntelliJ IDEA. User: Evgrafov Date: 29.09.2005 Time: 16:06:42 */
public class OperationPermission extends Permission
{
	public static final String SERVICE_WILDCARD = "*";

    private String operationKey;
	private String serviceKey;

	public OperationPermission(String operationKey, String serviceKey)
	{
		super(serviceKey + " " + operationKey);

		this.operationKey = operationKey;
		this.serviceKey = serviceKey;
	}

	public String getOperationKey()
	{
		return operationKey;
	}

	public void setOperationKey(String operationKey)
	{
		this.operationKey = operationKey;
	}

	public String getServiceKey()
	{
		return serviceKey;
	}

	public void setServiceKey(String serviceKey)
	{
		this.serviceKey = serviceKey;
	}

	public boolean isRigid()
	{
		return !SERVICE_WILDCARD.equals(serviceKey);
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		OperationPermission that = (OperationPermission) o;

		if (!operationKey.equals(that.operationKey))
			return false;

		if (serviceKey != null ? !serviceKey.equals(that.serviceKey) : that.serviceKey != null)
			return false;

		return true;
	}

	public int hashCode()
	{
		int result;
		result = operationKey.hashCode();
		result = 31 * result + (serviceKey != null ? serviceKey.hashCode() : 0);
		return result;
	}

	public String getActions()
    {
        return null;
    }

    public boolean implies(Permission permission)
    {
	    if(!(permission instanceof OperationPermission))
	        return false;
	    
	    OperationPermission that = (OperationPermission) permission;

	    // не даем доступа по маске
	    if(!that.isRigid())
	        return false;

	    if (!operationKey.equals(that.operationKey))
		    return false;

	    if(!isRigid())
	        return true;

	    if (serviceKey != null ? !serviceKey.equals(that.serviceKey) : that.serviceKey != null)
		    return false;

	    return true;
    }
}
