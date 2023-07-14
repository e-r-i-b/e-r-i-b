package com.rssl.phizic.config.build;

/**
 * @author Omeliyanchuk
 * @ created 11.08.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * “ип сервера приложений
 * при изменении помен€ть com.rssl.phizic.config.build.BuildContextConfigImpl#getApplicationServerInstallDir()
 */
public enum AppServerType
{
	/**
	 *  Oracle IAS
	 */
	oracle,
	/**
	 * JBOSS
	 */
	jboss,
	/**
	 * WebSphere
	 */
	websphere
}
