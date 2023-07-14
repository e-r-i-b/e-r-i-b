package com.rssl.phizic.business.persons;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.Stage;
import com.rssl.phizic.auth.modes.StageVerifier;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;

/**
 * @author Barinov
 * @ created 10.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class NeedToSelectRegionVerifier implements StageVerifier
{
	private static final ProfileService profileService = new ProfileService();

	public boolean isRequired(AuthenticationContext context, Stage stage) throws SecurityException
	{
		try
		{
			Profile profile = profileService.findByLogin(context.getLogin());
			if(profile == null)
				throw new SecurityException(
						"Не найден профиль с login_id = " +
						(context.getLogin()==null?"null":context.getLogin().getId())
				);
			return !profile.isRegionSelected();
		}
		catch(BusinessException e)
		{
			throw new SecurityException(e);
		}
	}
}
