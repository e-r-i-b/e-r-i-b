package com.rssl.phizic.auth;

import java.security.Principal;

/**
 * <p> This class implements the <code>Principal</code> interface
 * and represents a Sample user.
 * <p/>
 * <p> Principals such as this <code>TestPrincipal</code>
 * may be associated with a particular <code>Subject</code>
 * to augment that <code>Subject</code> with an additional
 * identity.  Refer to the <code>Subject</code> class for more information
 * on how to achieve this.  Authorization decisions can then be based upon
 * the Principals associated with a <code>Subject</code>.
 *
 * @version 1.4, 01/11/00
 * @see java.security.Principal
 * @see javax.security.auth.Subject
 */
class TestPrincipal implements Principal, java.io.Serializable
{

    /**
     * @serial
     */
    private String name;

    /**
     * Create a TestPrincipal with a Sample username.
     * <p/>
     * <p/>
     *
     * @param name the Sample username for this user.
     * @throws NullPointerException if the <code>name</code>
     *                              is <code>null</code>.
     */
    public TestPrincipal(String name)
    {
        if (name == null)
            throw new NullPointerException("illegal null input");

        this.name = name;
    }

    /**
     * Return the Sample username for this <code>TestPrincipal</code>.
     * <p/>
     * <p/>
     *
     * @return the Sample username for this <code>TestPrincipal</code>
     */
    public String getName()
    {
        return name;
    }

    /**
     * Return a string representation of this <code>TestPrincipal</code>.
     * <p/>
     * <p/>
     *
     * @return a string representation of this <code>TestPrincipal</code>.
     */
    public String toString()
    {
        return ("TestPrincipal:  " + name);
    }

    /**
     * Compares the specified Object with this <code>TestPrincipal</code>
     * for equality.  Returns true if the given object is also a
     * <code>TestPrincipal</code> and the two SamplePrincipals
     * have the same username.
     * <p/>
     * <p/>
     *
     * @param o Object to be compared for equality with this
     *          <code>TestPrincipal</code>.
     * @return true if the specified Object is equal equal to this
     *         <code>TestPrincipal</code>.
     */
    public boolean equals(Object o)
    {
        if (o == null)
            return false;

        if (this == o)
            return true;

        if (!(o instanceof TestPrincipal))
            return false;
        TestPrincipal that = (TestPrincipal) o;

        if (this.getName().equals(that.getName()))
            return true;
        return false;
    }

    /**
     * Return a hash code for this <code>TestPrincipal</code>.
     * <p/>
     * <p/>
     *
     * @return a hash code for this <code>TestPrincipal</code>.
     */
    public int hashCode()
    {
        return name.hashCode();
    }
}