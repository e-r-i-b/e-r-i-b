package com.rssl.phizic.userSettings;

/**
 * Обработчик изменения настроек пользователя.
 *
 * @author bogdanov
 * @ created 15.04.14
 * @ $Author$
 * @ $Revision$
 */
public interface SettingsProcessor<T>
{
	T onExecute(UserPropertiesConfig userProperties);
}
