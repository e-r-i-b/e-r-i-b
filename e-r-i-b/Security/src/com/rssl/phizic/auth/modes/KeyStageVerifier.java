package com.rssl.phizic.auth.modes;

import java.util.Properties;

/**
 * @author Evgrafov
 * @ created 13.04.2007
 * @ $Author: gololobov $
 * @ $Revision: 61219 $
 */

public class KeyStageVerifier implements StageVerifier
{
	protected static final String PROPERTY_NAME = "property";

	private String matchKey;

	/**
	 * @param properties ctor
	 */
	public KeyStageVerifier(Properties properties)
	{
		String value = properties.getProperty(PROPERTY_NAME);

		if(value == null)
			throw new RuntimeException("Не задано свойство property");

		matchKey = value;
	}

	public boolean isRequired(AuthenticationContext context, Stage stage) throws SecurityException
	{
		Properties properties = context.getPolicyProperties();
		
		String value = properties.getProperty(matchKey);
		if (value == null)
			throw new SecurityException("Не установлено свойство " + matchKey);

		return stage.getKey().equals(value);
	}
}