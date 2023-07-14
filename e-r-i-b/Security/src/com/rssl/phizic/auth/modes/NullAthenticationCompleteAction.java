package com.rssl.phizic.auth.modes;

/**
 * @author Evgrafov
 * @ created 16.08.2007
 * @ $Author: omeliyanchuk $
 * @ $Revision: 20696 $
 */

/**
 * ¬ –≈¿À»«¿÷»ﬂ’ Õ≈ ƒŒÀ∆ÕŒ ¡€“‹ —Œ—“ŒﬂÕ»ﬂ!!!!!!
 */
public class NullAthenticationCompleteAction implements AthenticationCompleteAction
{
	public static final AthenticationCompleteAction INSTANCE = new NullAthenticationCompleteAction();

	public void execute(AuthenticationContext context) throws SecurityException
	{
	}
}