package com.rssl.phizic.business.security;

import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.business.BusinessException;

import java.security.Permission;

/**
 * @author niculichev
 * @ created 11.01.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestPermissionProvider extends PermissionProvider
{
	protected Permission getPermission(UserPrincipal principal) throws BusinessException
	{
		return new PrincipalOperationPermission(principal)
		{
			protected PermissionCalculator getPermissionCalculator(UserPrincipal principal) throws BusinessException
			{
				return new GuestPrincipalPermissionCalculator(principal);
			}
		};
	}
}
