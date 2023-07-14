package com.rssl.phizic.security.permissions;

import java.security.Permission;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 16.09.2005
 * Time: 17:18:08
 */
public class AdminPermission extends Permission
{
    public AdminPermission()
    {
        super("<iccs admin permission>");
    }

    public AdminPermission(String name)
    {
        this();
    }

    public int hashCode()
    {
        return 1;
    }

    public boolean equals(Object obj)
    {
        return (obj instanceof AdminPermission);
    }

    public String getActions()
    {
        return "<all actions>";
    }

    public boolean implies(Permission permission)
    {
        return true;
    }
}
