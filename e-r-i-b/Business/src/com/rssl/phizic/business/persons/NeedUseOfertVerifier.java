package com.rssl.phizic.business.persons;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.Stage;
import com.rssl.phizic.auth.modes.StageVerifier;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.config.authService.AuthServiceConfig;
import com.rssl.phizic.config.csa.CSAConfig;
import com.rssl.phizic.userSettings.SettingsProcessor;
import com.rssl.phizic.userSettings.UserPropertiesConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author osminin
 * @ created 19.10.2009
 * @ $Author$
 * @ $Revision$
 */

public class NeedUseOfertVerifier implements StageVerifier
{
	public boolean isRequired(AuthenticationContext context, Stage stage) throws SecurityException
	{
		try
		{
			if (!ConfigFactory.getConfig(CSAConfig.class).isCsaModeTransition())
				return false;

			return UserPropertiesConfig.processUserSettingsWithoutPersonContext(context.getLogin(), new SettingsProcessor<Boolean>()
			{
				public Boolean onExecute(UserPropertiesConfig userProperties)
				{
					return !userProperties.getUseOfert();
				}
			});
		}
		catch(BusinessException e)
		{
			throw new SecurityException(e);
		}
	}
}
