package com.rssl.phizic.auth.modes;

import com.rssl.phizic.common.types.csa.ProfileType;

/**
 * @author koptyaev
 * @ created 29.05.14
 * @ $Author$
 * @ $Revision$
 */
public class SynchProfileInfoStageVerifier implements StageVerifier
{
	/**
	 * @param context контекст аутентификации
	 * @param stage стадия аутентификации
	 * @return true == стадия необходима в этом контексте
	 */
	public boolean isRequired(AuthenticationContext context, Stage stage) throws SecurityException
	{
		return context.getProfileType() == ProfileType.TEMPORARY;
	}
}
