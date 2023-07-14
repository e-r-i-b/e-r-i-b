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
 * �������� � ������� ���������� ���� �������
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
	 * ��� ������������
	 * @return
	 */
	public abstract String getConfigurationName();

	/**
	 * ��� ������� ��
	 * @return
	 */
	public abstract DbType getDatabaseType();

	/**
	 * ��� ������� ����������
	 * @return
	 */
	public abstract AppServerType getApplicationServerType();

	/**
	 * ���� �� �������� ���������� ������ ����������(��� ���������� ������, ���������� ���� ����������)
	 * @return ���� �� �������� ���������� ������ ����������
	 */
	public abstract String getApplicationServerInstallDir();

	/**
	 * ���� ����������
	 * @return ����
	 */
	public abstract String getApplicationPort();

	/**
	 * ���� ������ ������������ � �������
	 * @return
	 */
	public abstract Set<GateType> getGateTypes();

	/**
	 * ��� ������-�������.
	 * @return
	 */
	public abstract CryptoPluginType getCryptoPluginType();

	/**
	 * ���������� �� ������ � ������� ��
	 */
	public abstract boolean isShadowDatabaseOn();

	/**
	 * �������� ��������(����� ������� ����������) ��� ��� ������
	 * ������������ ��� ��������� ������ ���� ���������
	 * @return
	 */
	public abstract String getResourceAdditionalPath();
}
