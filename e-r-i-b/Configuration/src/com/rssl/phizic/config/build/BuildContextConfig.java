package com.rssl.phizic.config.build;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

import java.util.Set;

/**
 * @author Omeliyanchuk
 * @ created 11.08.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * Контекст в котором приложение было собрано
 */
public abstract class BuildContextConfig extends Config
{
	public static final String CONFIG_NAME  = "current.config.description";
	public static final String DB_ORACLE  = "dbserver.type.oracle";
	public static final String DB_MS_SQL  = "dbserver.type.ms-sql";
	public static final String APP_ORACLE  = "appserver.type.oracle";
	public static final String APP_JBOSS  = "appserver.type.jboss";
	public static final String APP_WEBSPHERE  = "appserver.type.websphere";
	public static final String GATE_COD  = "include.gates.SBRFCODGate";
	public static final String SHADOW_DATABASE  = "shadow.database.on";
	public static final String CRYPTO_PLUGIN_SBRF  = "include.SBRFCryptoPlugin";
	public static final String CRYPTO_PLUGIN  = "include.CryptoPlugin";
	public static final String RESOURCE_ADDITIONAL_PATH  = "app.version";

	protected BuildContextConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * имя конфигурации
	 * @return
	 */
	public abstract String getConfigurationName();

	/**
	 * Тип сервера БД
	 * @return
	 */
	public abstract DbType getDatabaseType();

	/**
	 * Тип сервера приложений
	 * @return
	 */
	public abstract AppServerType getApplicationServerType();

	/**
	 * Путь по которому установлен сервер приложений(при добавлении нового, реализацию надо исправлять)
	 * @return Путь по которому установлен сервер приложений
	 */
	public abstract String getApplicationServerInstallDir();

	/**
	 * Порт приложения
	 * @return порт
	 */
	public abstract String getApplicationPort();

	/**
	 * Типы шлюзов подключенных к системе
	 * @return
	 */
	public abstract Set<GateType> getGateTypes();

	/**
	 * Тип крипто-плагина.
	 * @return
	 */
	public abstract CryptoPluginType getCryptoPluginType();

	/**
	 * Необходима ли работа с теневой БД
	 */
	public abstract boolean isShadowDatabaseOn();

	/**
	 * Получить постфикс(номер ревизии приложения) для урл скинов
	 * Используется для механизма сброса кэша браузеров
	 * @return
	 */
	public abstract String getResourceAdditionalPath();
}
