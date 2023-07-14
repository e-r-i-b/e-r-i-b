package com.rssl.phizic.security.permissions;

import java.security.Permission;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 16.09.2005
 * Time: 17:31:39
 */
public abstract class ObjectPermissionBase extends Permission
{
    private Long objectId;
    private int  mask;

    protected ObjectPermissionBase(String name, Long objectId, int mask)
    {
        super(name);

        /*if(objectId == null)
            throw new NullPointerException("name cannot be null");
            */

        this.objectId = objectId;
        this.mask  = mask;

    }

    public Long getObjectId()
    {
        return objectId;
    }

    /*public boolean equals(Object obj)
    {
        ObjectPermissionBase that = (ObjectPermissionBase) obj;

        if(this.mask != that.mask)
            return false;

        if(!this.objectId.equals(that.objectId))
            return false;

        return true;
    } */

    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || !(o instanceof ObjectPermissionBase)) return false;

        final ObjectPermissionBase that = (ObjectPermissionBase) o;

        if (mask != that.mask) return false;
        if (objectId != null ? !objectId.equals(that.objectId) : that.objectId != null) return false;

        return true;
    }

    public boolean implies(Permission permission)
    {
        ObjectPermissionBase that = (ObjectPermissionBase) permission;

        if((this.mask & that.mask) != this.mask)
            return false;

        if (objectId != null ? !objectId.equals(that.objectId) : that.objectId != null)
            return false;

        /*if(!this.objectId.equals(that.objectId))
            return false;*/

        return true;
    }

    public int hashCode()
    {
        return objectId!=null ? getName().hashCode() ^ objectId.hashCode() : getName().hashCode();
    }

    public String getActions()
    {
        String[] actionNames = getActionNames();
        StringBuffer result = new StringBuffer();
        int j = 1;
        for(int i = 0; i < actionNames.length; i++)
        {
            if((mask & j)!=0)
            {
                result.append(actionNames[i]);
                result.append(",");
            }
            j*=2;
        }
        int ln = result.length();
        if(ln > 2)
            result.delete(ln - 1, ln);
        return result.toString();
    }

    protected abstract String[] getActionNames();

}
