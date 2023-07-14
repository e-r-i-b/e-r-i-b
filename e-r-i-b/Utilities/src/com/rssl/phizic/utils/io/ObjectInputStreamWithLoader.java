package com.rssl.phizic.utils.io;

import java.io.ObjectInputStream;
import java.io.InputStream;
import java.io.IOException;
import java.io.ObjectStreamClass;

/**
 * This class deserializes an object in the context of a specific class loader.
 * Этот класс - копия package-private класса com.sun.jmx.mbeanserver.ObjectInputStreamWithLoader
 * По сути, work around исключения oracle.classloader.util.AnnotatedClassNotFoundException при десериализации.
 * см. http://stackoverflow.com/questions/5211057/need-to-load-some-class-by-bootstrap-classloader
 * и http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=4340158
 * @author Dorzhinov
 * @ created 25.11.2011
 * @ $Author$
 * @ $Revision$
 */
public class ObjectInputStreamWithLoader extends ObjectInputStream
{
    private ClassLoader loader;

    /**
     * @exception java.io.IOException Signals that an I/O exception of some
     * sort has occurred.
     * @exception java.io.StreamCorruptedException The object stream is corrupt.
     */
    public ObjectInputStreamWithLoader(InputStream in, ClassLoader theLoader)
	    throws IOException
    {
	super(in);
	this.loader = theLoader;
    }

	protected Class resolveClass(ObjectStreamClass aClass)
	    throws IOException, ClassNotFoundException {
	if (loader == null) {
	    return super.resolveClass(aClass);
	} else {
	    String name = aClass.getName();
	    // Query the class loader ...
	    return Class.forName(name, false, loader);
	}
    }
}
