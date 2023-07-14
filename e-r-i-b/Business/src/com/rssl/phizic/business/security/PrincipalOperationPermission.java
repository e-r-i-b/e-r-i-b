package com.rssl.phizic.business.security;

import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.security.permissions.OperationPermission;
import com.rssl.phizic.security.permissions.AccessSchemePermission;
import com.rssl.phizic.security.permissions.ServicePermission;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.ClassHelper;

import java.security.Permission;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 13.10.2005
 * Time: 18:57:54
 */
public class PrincipalOperationPermission extends Permission
{
	private PermissionCalculator calculator;
	private UserPrincipal principal;

	/**
	 * ctor
	 * @param principal принципал для котороо надо вычислить права
	 */
	public PrincipalOperationPermission(UserPrincipal principal) throws BusinessException
	{
	    super("<< Principal [" + principal.getName() + "] permission >>");
		this.principal  = principal;
		this.calculator = getPermissionCalculator(principal);
	}

    public boolean implies(Permission permission)
    {
	    boolean result = false;

	    if( permission instanceof OperationPermission )
		{
			OperationPermission op = (OperationPermission) permission;
			result = calculator.impliesOperation(op.getServiceKey(), op.getOperationKey(), op.isRigid());
		}
	    else if ( permission instanceof ServicePermission)
	    {
		    ServicePermission servicePermission = (ServicePermission) permission;
		    result = calculator.impliesService(servicePermission.getServiceKey(), servicePermission.isRigid());
	    }
	    else if (permission instanceof AccessSchemePermission)
	    {
		    AccessSchemePermission schemePermission = (AccessSchemePermission) permission;
		    result = calculator.impliesAccessScheme(schemePermission.getSchemeKey());
	    }

	    return result;
    }

	public int hashCode()
	{
	    return 0;
	}

    public boolean equals(Object obj)
    {
        return false;
    }

    public String getActions()
    {
        return null;
    }

	protected UserPrincipal getPrincipal()
	{
		return principal;
	}

	protected PermissionCalculator getPermissionCalculator(UserPrincipal principal) throws BusinessException
	{
		try
		{
			SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
			return (PermissionCalculator) ClassHelper.loadClass(securityConfig.getPermissionCalculatorClassName()).getConstructor(UserPrincipal.class).newInstance(principal);
		}
		catch (ClassNotFoundException e)
		{
			throw new BusinessException(e);
		}
		catch (InvocationTargetException e)
		{
			throw new BusinessException(e);
		}
		catch (NoSuchMethodException e)
		{
			throw new BusinessException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new BusinessException(e);
		}
		catch (InstantiationException e)
		{
			throw new BusinessException(e);
		}
	}
}
