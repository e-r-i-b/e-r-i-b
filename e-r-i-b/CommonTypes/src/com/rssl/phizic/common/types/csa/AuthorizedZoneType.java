package com.rssl.phizic.common.types.csa;

/**
 * @author osminin
 * @ created 10.11.13
 * @ $Author$
 * @ $Revision$
 *
 * “ип зоны входа пользовател€
 * пути должны быть идентичны пут€м mobileN-authentication-modes.xml, где N - текуща€ верси€ мјпи
 */
public enum AuthorizedZoneType
{
	POST_REGISTRATION("/postCSALogin.do"),    //авторизованна€ зона
	AUTHORIZATION("/postCSALogin.do"),    //авторизованна€ зона
	PRE_AUTHORIZATION("/login.do");       //доавторизованна€ зона

	private String actionPath;

	AuthorizedZoneType(String actionPath)
	{
		this.actionPath = actionPath;
	}

	/**
	 * @return экшен путь
	 */
	public String getActionPath()
	{
		return actionPath;
	}
}
