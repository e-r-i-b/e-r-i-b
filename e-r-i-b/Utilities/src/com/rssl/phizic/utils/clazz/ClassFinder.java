package com.rssl.phizic.utils.clazz;

import java.util.List;
import java.util.ArrayList;
import java.io.File;

/**
 * @author Evgrafov
 * @ created 31.03.2006
 * @ $Author: kosyakov $
 * @ $Revision: 2453 $
 */

public class ClassFinder
{
	private String  rootPackageName;
	private boolean recursive;

	public ClassFinder(String rootPackageName, boolean recursive)
	{
		this.recursive = recursive;
		this.rootPackageName = rootPackageName;
	}

	public List<Class>find(ClassFilter filter) throws ClassNotFoundException
	{
		List<Class> classes = new ArrayList<Class>();
		loadClasses(filter, classes, rootPackageName);
		return classes;
	}

	private List<Class> loadClasses(ClassFilter filter, List<Class> classes, String pckgname) throws ClassNotFoundException
	{
		// Get a File object for the package
		File directory = null;
		try
		{
			String packageResourceName = pckgname.replace('.', '/');
			String packagePath = Thread.currentThread().getContextClassLoader().getResource(packageResourceName).getFile();
			directory = new File(packagePath.replaceAll("%20"," "));
		}
		catch (NullPointerException x)
		{
			throw new ClassNotFoundException(pckgname + " does not appear to be a valid package");
		}
		if (directory.exists())
		{
			// Get the list of the files contained in the package
			File[] files = directory.listFiles();
            for (File file : files)
            {
                // we are only interested in .class files
                String fileName = file.getName();
                if (fileName.endsWith(".class"))
                {
                    // removes the .class extension
                    String className = fileName.substring(0, fileName.length() - 6);
                    Class<?> clazz = Class.forName(pckgname + '.' + className);
                    if (filter.accept(clazz))
                    {
                        classes.add(clazz);
                    }
                }
                if (file.isDirectory() && recursive)
                {
                    loadClasses(filter, classes, pckgname + "." + fileName);
                }
            }
		}
		else
		{
			throw new ClassNotFoundException(pckgname + " does not appear to be a valid package");
		}
		return classes;
	}

}