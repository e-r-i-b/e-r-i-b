package com.rssl.phizic.business.persons;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.Stage;
import com.rssl.phizic.auth.modes.StageVerifier;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.userSettings.SettingsProcessor;
import com.rssl.phizic.userSettings.UserPropertiesConfig;

/**
 * @author Rydvanskiy
 * @ created 11.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class NeedUseInternetSecurityVerifier implements StageVerifier
{
	public boolean isRequired(AuthenticationContext context, Stage stage) throws SecurityException
	{
		try
		{
			return UserPropertiesConfig.processUserSettingsWithoutPersonContext(context.getLogin(), new SettingsProcessor<Boolean>()
			{
				public Boolean onExecute(UserPropertiesConfig userProperties)
				{
					return !userProperties.getUseInternetSecurity();
				}
			});
		}
		catch (BusinessException e)
		{
			throw new SecurityException(e);
		}
	}
}
