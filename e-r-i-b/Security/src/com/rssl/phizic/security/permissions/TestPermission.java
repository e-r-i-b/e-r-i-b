package com.rssl.phizic.security.permissions;

import java.security.BasicPermission;
import java.security.Permission;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 22.08.2005
 * Time: 19:13:30
 */
public class TestPermission extends BasicPermission
{
    public TestPermission(String name)
    {
        super(name);
    }

    public boolean implies(Permission p)
    {
        return super.implies(p);
    }

    public String toString()
    {
        return getName() + " " + getActions();
    }
}
