package com.rssl.phizic.business.security.auth;

import com.rssl.phizic.auth.PermissionsProvider;
import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.auth.modes.AllowOperationBundle;
import com.rssl.phizic.auth.modes.Stage;
import com.rssl.phizic.security.permissions.OperationPermission;
import com.rssl.phizic.security.permissions.PermissionCollectionImpl;

import java.security.PermissionCollection;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 15.12.2006
 * @ $Author: sergunin $
 * @ $Revision: 55894 $
 */

public class StagePermissionProvider implements PermissionsProvider
{
	private Stage stage;

	/**
	 * @param stage стадия для которой формируются права
	 */
	public StagePermissionProvider(Stage stage)
	{
		this.stage = stage;
	}

	public PermissionCollection load(UserPrincipal principal) throws SecurityException
	{
		List<AllowOperationBundle> operations = stage.getAllowedOperations();

		PermissionCollection permissions = new PermissionCollectionImpl();

		for (AllowOperationBundle operation : operations)
		{
		   	String clazz = operation.getOperationName();
			String key = operation.getServiceKey();
			if (operation.getOperationVerifier().isAllow(clazz,principal.getLogin()))
				permissions.add(new OperationPermission(clazz, key));
		}

		permissions.setReadOnly();
		return permissions;
	}
}