package com.rssl.phizic.security;

import com.rssl.phizic.auth.PermissionsProvider;
import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.security.permissions.AdminPermission;
import com.rssl.phizic.security.permissions.PermissionCollectionImpl;

import java.security.PermissionCollection;

public class AdminPermissionProvider implements PermissionsProvider
{

    public PermissionCollection load(UserPrincipal principal)
    {
        PermissionCollection perms = new PermissionCollectionImpl();
        perms.add(new AdminPermission());
        return perms;
    }
}
