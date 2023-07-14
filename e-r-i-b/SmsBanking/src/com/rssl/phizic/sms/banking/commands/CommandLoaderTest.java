package com.rssl.phizic.sms.banking.commands;

import com.rssl.phizic.test.BusinessTestCaseBase;

import java.io.Reader;

/**
 * @author hudyakov
 * @ created 30.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class CommandLoaderTest extends BusinessTestCaseBase
{
	private static final CommandFactory commandFactory = new CommandFactory();

	public void testCL() throws Exception
	{
		commandFactory.newInstance("Perevod;A00037;A00017;45");
		CommandLoader commandLoader = new CommandLoader();
		Object templateSource = commandLoader.findTemplateSource("Perevod" + "#" + "response");
		assertNotNull(templateSource);
		Reader reader = commandLoader.getReader(templateSource, "");
        assertNotNull(reader);
	}
}
