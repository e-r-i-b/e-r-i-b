package com.rssl.phizic.security.permissions;

import java.security.PermissionCollection;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 22.08.2005
 * Time: 19:18:10
 */
public class PermissionCollectionImpl extends PermissionCollection
{
    private List<Permission> perms = new ArrayList<Permission>();

    public void add(Permission p)
    {
        perms.add(p);
    }

    public PermissionCollectionImpl()
    {
    }

    public PermissionCollectionImpl(Permission[] permission)
    {
        for(int i=0; i<permission.length; i++)
            perms.add(permission[i]);
    }

    public boolean implies(Permission p)
    {
        for (Iterator i = perms.iterator(); i.hasNext();)
        {
            if (((Permission) i.next()).implies(p))
            {
                return true;
            }
        }
        return false;
    }

    public Enumeration<Permission> elements()
    {
        return null;
    }

    public void setReadOnly()
    {
    }

    public boolean isReadOnly()
    {
        return false;
    }

    public String toString()
    {
        return super.toString();
    }
}