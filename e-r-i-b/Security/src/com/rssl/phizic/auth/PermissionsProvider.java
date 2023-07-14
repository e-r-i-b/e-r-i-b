package com.rssl.phizic.auth;

import java.security.PermissionCollection;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 26.08.2005
 * Time: 14:50:45
 */
public interface PermissionsProvider
{
    PermissionCollection load(UserPrincipal principal);
}
