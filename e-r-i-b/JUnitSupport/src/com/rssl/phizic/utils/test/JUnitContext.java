package com.rssl.phizic.utils.test;


import com.sun.jndi.cosnaming.CNNameParser;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * <p>Title: JUnitContext</p>
 * <p>Description: A very thin Context for use by JNDIUnitTestHelper</p>
 * <p>Copyright: Copyright (c) 2002</p>
    - - - - - - - - - - - - - - - - - <p>
    You are welcome to do whatever you want to with this source file provided
    that you maintain this comment fragment (between the dashed lines).
    Modify it, change the package name, change the class name ... personal or business
    use ...  sell it, share it ... add a copyright for the portions you add ... <p>

    My goal in giving this away and maintaining the copyright is to hopefully direct
    developers back to JavaRanch. <p>

    The original source can be found at <a href=http://www.javaranch.com>JavaRanch</a> <p>

    - - - - - - - - - - - - - - - - - <p>
 * <p>Company: JavaRanch</p>
 * @author Carl Trusiak, Sheriff
 * @version 1.0
 * @noinspection CollectionDeclaredAsConcreteClass
 */
public final class JUnitContext implements Context
{

    private Map<String,Object> table = new HashMap<String, Object>();
	private String prefix = "";

    /**
     * Method lookup not yet implemented.
     */
    public Object lookup( Name name )
    {
        throw new java.lang.UnsupportedOperationException("Method lookup() not yet implemented.");
    }

    /**
     * Method lookup Returns the JUnitDataSource.
     */
    public Object lookup( String name )
    {
        return table.get( name );
    }

    /**
     * Method bind not yet implemented.
     */
    public void bind( Name name, Object obj )
    {
        throw new java.lang.UnsupportedOperationException("Method bind() not yet implemented." );
    }

    /**
     * Method bind the JUnitDataSource for use.
     */
    public void bind( String name, Object obj )
    {
        table.put( name, obj );
    }

    /**
     * Method rebind not yet implemented.
     */
    public void rebind( Name name, Object obj)
    {
        throw new java.lang.UnsupportedOperationException("Method rebind() not yet implemented." );
    }

    /**
     * Method rebind not yet implemented.
     */
    public void rebind( String name, Object obj )
    {
        table.put(name, obj);
    }

    /**
     * Method unbind not yet implemented.
     */
    public void unbind( Name name )
    {
        throw new java.lang.UnsupportedOperationException("Method unbind() not yet implemented.");
    }

    /**
     * Method unbind not yet implemented.
     */
    public void unbind( String name )
    {
        table.remove( name );
    }

    /**
     * Method rename not yet implemented.
     */
    public void rename( Name oldName, Name newName )
    {
        throw new java.lang.UnsupportedOperationException("Method rename() not yet implemented.");
    }

    /**
     * Method rename not yet implemented.
     */
    public void rename( String oldName, String newName )
    {
        throw new java.lang.UnsupportedOperationException("Method rename() not yet implemented." );
    }

    /**
     * Method list not yet implemented.
     */
    public NamingEnumeration list( Name name )
    {
        throw new java.lang.UnsupportedOperationException("Method list() not yet implemented.");
    }

    /**
     * Method list not yet implemented.
     */
    public NamingEnumeration list( String name )
    {
        throw new java.lang.UnsupportedOperationException("Method list() not yet implemented.");
    }

    /**
     * Method listBindings not yet implemented.
     */
    public NamingEnumeration listBindings( Name name )
    {
        throw new java.lang.UnsupportedOperationException("Method listBindings() not yet implemented.");
    }

    /**
     * Method listBindings not yet implemented.
     */
    public NamingEnumeration listBindings( String name )
    {
        throw new java.lang.UnsupportedOperationException("Method listBindings() not yet implemented.");
    }

    /**
     * Method destroySubcontext not yet implemented.
     */
    public void destroySubcontext( Name name )
    {
        throw new java.lang.UnsupportedOperationException("Method destroySubcontext() not yet implemented.");
    }

    /**
     * Method destroySubcontext not yet implemented.
     */
    public void destroySubcontext( String name )
    {
        throw new java.lang.UnsupportedOperationException("Method destroySubcontext() not yet implemented.");
    }

    /**
     * Method createSubcontext not yet implemented.
     */
    public Context createSubcontext( Name name )
    {
        return this;
    }

    /**
     * Method createSubcontext not yet implemented.
     */
    public Context createSubcontext( String name )
    {
	    return this;
    }

    /**
     * Method lookupLink not yet implemented.
     */
    public Object lookupLink( Name name )
    {
        throw new java.lang.UnsupportedOperationException("Method lookupLink() not yet implemented." );
    }

    /**
     * Method lookupLink not yet implemented.
     */
    public Object lookupLink( String name )
    {
        throw new java.lang.UnsupportedOperationException("Method lookupLink() not yet implemented.");
    }

    /**
     * Method getNameParser not yet implemented.
     */
    public NameParser getNameParser( Name name )
    {
        throw new java.lang.UnsupportedOperationException("Method getNameParser() not yet implemented.");
    }

    /**
     * Method getNameParser not yet implemented.
     */
    public NameParser getNameParser( String name )
    {
        return new CNNameParser();
    }

    /**
     * Method composeName not yet implemented.
     */
    public Name composeName( Name name, Name prefix )
    {
        throw new java.lang.UnsupportedOperationException("Method composeName() not yet implemented.");
    }

    /**
     * Method composeName not yet implemented.
     */
    public String composeName( String name, String prefix )
    {
        throw new java.lang.UnsupportedOperationException("Method composeName() not yet implemented.");
    }

    /**
     * Method addToEnvironment not yet implemented.
     */
    public Object addToEnvironment( String propName, Object propVal )
    {
        throw new java.lang.UnsupportedOperationException("Method addToEnvironment() not yet implemented.");
    }

    /**
     * Method removeFromEnvironment not yet implemented.
     */
    public Object removeFromEnvironment( String propName )
    {
        throw new java.lang.UnsupportedOperationException(
            "Method removeFromEnvironment() not yet implemented." );
    }

    public Hashtable getEnvironment()
    {
        throw new java.lang.UnsupportedOperationException("Method getEnvironment() not yet implemented.");
    }

    public void close()
    {
	    // do nothing
    }

    /**
     * Method getNameInNamespace not yet implemented.
     */
    public String getNameInNamespace()
    {
        throw new java.lang.UnsupportedOperationException("Method getNameInNamespace() not yet implemented.");
    }
}
