package com.rssl.phizic.auth;

import java.io.Serializable;
import java.security.Principal;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 07.09.2005
 * Time: 14:03:33
 */
abstract class PrincipalBase implements Principal, Serializable
{
    /**
     * @serial
     */
    private String name;

    /**
     * Create a Principal with a username.
     *
     * @param name the Sample username for this user.
     * @throws NullPointerException if the <code>name</code>
     *                              is <code>null</code>.
     */
    protected PrincipalBase(String name)
    {
        if(name == null)
            throw new NullPointerException("Имя не может быть null");
        this.name = name;
    }


    /**
     * @return Имя принципала
     */
    public String getName()
    {
        return name;
    }

    /**
     * @return Строковое представление принципала
     */
    public String toString()
    {
        return this.getClass().toString() + " " + name;
    }

    /**
     * @param o Object to be compared for equality with this
     *          <code>PrincipalImpl</code>.
     * @return true if the specified Object is equal equal to this
     *         <code>PrincipalImpl</code>.
     */
    public boolean equals(Object o)
    {
        if (o == null)
            return false;

        if (this == o)
            return true;

        if (!(o instanceof Principal))
            return false;

        Principal that = (Principal) o;
        if (this.getName().equals(that.getName()))
            return true;
        return false;
    }

    /**
     * @return a hash code for this <code>PrincipalImpl</code>.
     */
    public int hashCode()
    {
        return name.hashCode();
    }
}
