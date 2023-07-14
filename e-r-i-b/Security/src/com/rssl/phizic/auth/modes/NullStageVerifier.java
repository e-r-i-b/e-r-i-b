package com.rssl.phizic.auth.modes;

/**
 * @author Evgrafov
 * @ created 12.12.2006
 * @ $Author: Evgrafov $
 * @ $Revision: 4036 $
 */

public class NullStageVerifier implements StageVerifier
{
	public static final StageVerifier INSTANCE = new NullStageVerifier();

	public boolean isRequired(AuthenticationContext context, Stage stage)
	{
		return true;
	}
}