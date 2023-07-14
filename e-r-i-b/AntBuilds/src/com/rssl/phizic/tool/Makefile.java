package com.rssl.phizic.tool;

import com.rssl.phizic.common.types.annotation.Immutable;
import com.rssl.phizic.config.build.AppServerType;

import java.util.Collection;

/**
 * @author Erkin
 * @ created 10.03.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Профиль сборки проекта
 */
@Immutable
interface Makefile
{
	/**
	 * @return сервер приложений, для которого собирается проект
	 */
	AppServerType getAppServerType();

	/**
	 * @return перечень (имён) приложений, которые нужно собрать
	 */
	Collection<String> getApplications();
}
