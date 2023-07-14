package com.rssl.phizic.sms.banking.commands;

import freemarker.cache.TemplateLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Map;

/**
 * @author hudyakov
 * @ created 30.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class CommandLoader implements TemplateLoader
{
	private CommandService commandService = new CommandService();

	public Object findTemplateSource(String name) throws IOException
	{
		String[] strings = name.split("#");
		if(strings.length != 2)
			throw new IOException("Invalid template name: " + name);

		String commandName = strings[0].toUpperCase();
		String template = strings[1];
		Map<String, String> templates = commandService.getTemplates(commandName);

		return templates.get(template);  
	}

	public long getLastModified(Object templateSource)
	{
		return -1;
	}

	public Reader getReader(Object templateSource, String encoding) throws IOException
	{
		return new StringReader((String) templateSource);
	}

	public void closeTemplateSource(Object templateSource) throws IOException
	{
	}
}
