package com.rssl.phizic.business.ant;

import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.files.FileHelper;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.resources.FileResource;

import java.io.*;
import java.util.Iterator;
import java.util.Properties;

/**
 * User: Balovtsev
 * Date: 25.01.2012
 * Time: 17:29:47
 */
public class SetupProjectFolderPathTask extends Task
{
	private String  fix;
	private String  value;
	private FileSet fileSet = new FileSet();

	public void execute() throws BuildException
	{
		if (StringHelper.isEmpty(fix) || StringHelper.isEmpty(value) || fileSet.size() == 0)
		{
			return;
		}

		System.out.println("***************** Fix absolute path ******************");
		System.out.println("Overwrite: " + fix);
		System.out.println("Replaced by: " + value);
		System.out.println("Number of files: " + fileSet.size());
		System.out.println("******************************************************");

		Iterator<FileResource> i = fileSet.iterator();
		while (i.hasNext())
		{
			FileResource resource = i.next();
			Properties properties = new Properties();
			try
			{
				FileHelper.loadProperties(resource.getFile(), properties);
				if ( fix(properties) )
				{
				    FileHelper.storeProperties(resource.getFile(), properties);
				}
			}
			catch (IOException e)
			{
				throw new BuildException(e);
			}
		}
	}

	private boolean fix(Properties properties)
	{
		boolean hasChanges = false;

		for (Object key : properties.keySet())
		{
			String tmp = (String) properties.get(key);
			if ( tmp.indexOf(fix) > -1 )
			{
				System.out.println("Overwrite - " + key);
				
				hasChanges = true;
				properties.put(key, tmp.replace(fix, value));
			}
		}

		return hasChanges;
	}

	public void setFix(String fix)
	{
		this.fix = fix;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public void addFileset(FileSet fileSet)
	{
		this.fileSet = fileSet;		
	}
}
