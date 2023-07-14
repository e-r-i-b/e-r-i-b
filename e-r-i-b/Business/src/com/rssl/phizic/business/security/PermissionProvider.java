package com.rssl.phizic.business.security;

import com.rssl.phizic.auth.PermissionsProvider;
import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.security.permissions.PermissionCollectionImpl;
import com.rssl.phizic.utils.annotation.PublicDefaultCreatable;

import java.security.Permission;
import java.security.PermissionCollection;

@PublicDefaultCreatable
public class PermissionProvider implements PermissionsProvider
{
    public PermissionCollection load(UserPrincipal principal)
    {
        PermissionCollectionImpl perms = new PermissionCollectionImpl();
        Permission pp;

	    try
        {
	        pp = getPermission(principal);
        }
        catch (BusinessException e)
        {
            throw new RuntimeException(e);
        }

        perms.add(pp);

	    return perms;
    }

	protected Permission getPermission(UserPrincipal principal) throws BusinessException
	{
		return new PrincipalOperationPermission(principal);
	}
}
