package test.junit;

import org.apache.tools.ant.Task;

import java.util.Hashtable;

/**
 * @author Kosyakov
 * @ created 31.03.2006
 * @ $Author: Roshka $
 * @ $Revision: 2842 $
 */
public class SampleTask extends Task
{
	public void execute ()
	{
		Hashtable<String, String> properties = getProject().getProperties();
		for (String key : properties.keySet())
		{
			System.out.println(key+"-"+properties.get(key));
			System.setProperty(key, properties.get(key));
		}
	}

}
