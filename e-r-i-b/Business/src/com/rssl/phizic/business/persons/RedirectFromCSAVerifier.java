package com.rssl.phizic.business.persons;

import com.rssl.phizic.auth.modes.StageVerifier;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.Stage;
import com.rssl.phizic.auth.modes.KeyStageVerifier;

import java.util.Properties;

/**
 * @author Gainanov
 * @ created 09.11.2009
 * @ $Author$
 * @ $Revision$
 */
public class RedirectFromCSAVerifier extends KeyStageVerifier
{
	/**
	 * @param properties ctor
	 */
	public RedirectFromCSAVerifier(Properties properties)
	{
		super(properties);
	}

	public boolean isRequired(AuthenticationContext context, Stage stage) throws SecurityException
	{
		if(super.isRequired(context, stage))
			return !context.isMoveSession();
		return false;
	}
}
