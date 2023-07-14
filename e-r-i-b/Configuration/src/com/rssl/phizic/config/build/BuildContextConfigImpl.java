package com.rssl.phizic.config.build;

import com.rssl.phizic.config.*;
import com.rssl.phizic.utils.ClassHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlFileReader;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

/**
 * @author Omeliyanchuk
 * @ created 11.08.2008
 * @ $Author$
 * @ $Revision$
 */

public class BuildContextConfigImpl extends BuildContextConfig
{
	private static final String WS_ADMIN_SERVICE_FACTORY = "com.ibm.websphere.management.AdminServiceFactory";
	private static final Log log = LogFactory.getLog(BuildContextConfigImpl.class);
	private static final String WS_PORT_ERROR = "Ошибка при получении порта приложения на WebSphere: ";
	private static final String WS_PORT_TRACE = "Получение порта приложения на WS: ";
	private AppServerType applicationServerType;
	private String configurationName;
	private String applicationPort;
	private DbType databaseType;
	private Set<GateType> gateTypes;
	private boolean shadowDatabaseOn;
	private CryptoPluginType cryptoPluginType;
	private String resourceAdditionalPath;

	public BuildContextConfigImpl(PropertyReader propertyReader)
	{
		super(propertyReader);
	}

	public AppServerType getApplicationServerType()
	{
		return applicationServerType;
	}

	public String getApplicationServerInstallDir()
	{
		switch(applicationServerType)
		{
			case oracle:return System.getProperty("oracle.home");
			case websphere:return System.getProperty("was.install.root");
			case jboss:return System.getProperty("jboss.server.home.dir");
		}
		throw new RuntimeException("Неизвестный СП");

	}

	/**
	 * Возвращает порт приложения
	 * Берем из конфигурационного файла СП
	 * OC4J:      [OC4J]/j2ee/home/config/default-web-site.xml
	 * WebSphere: [WAS]/profiles/<profile_name>/config/cells/<cell_name>/nodes/<node_name>/serverindex.xml
	 * @return порт приложения
	 */
	public String getApplicationPort()
	{
		if (StringHelper.isEmpty(applicationPort))
			applicationPort = readApplicationPort();

		return applicationPort;
	}

	public String getConfigurationName()
	{
		return configurationName;
	}

	public DbType getDatabaseType()
	{
		return databaseType;
	}

	public Set<GateType> getGateTypes()
	{
		return gateTypes;
	}

	public boolean isShadowDatabaseOn()
	{
		return shadowDatabaseOn;
	}

	public CryptoPluginType getCryptoPluginType()
	{
		return cryptoPluginType;
	}

	public String getResourceAdditionalPath()
	{
		return resourceAdditionalPath;
	}

	public void doRefresh() throws ConfigurationException
	{
		Boolean appServer = getBooleanProperty(APP_ORACLE);
		if(appServer)
		{
			applicationServerType = AppServerType.oracle;
		}
		else
		{
			appServer = getBooleanProperty(APP_JBOSS);
			if(appServer)
			{
				applicationServerType = AppServerType.jboss;
			}
			else
			{
				appServer = getBooleanProperty(APP_WEBSPHERE);
				if(appServer)
				{
					applicationServerType = AppServerType.websphere;
				}
				else
					throw new ConfigurationException("Неизвестный тип сервера приложений:"+appServer);
			}
		}

		configurationName = getProperty(CONFIG_NAME);

		resourceAdditionalPath = getProperty(RESOURCE_ADDITIONAL_PATH);

		Boolean dbServer = getBooleanProperty(DB_ORACLE);
		if(dbServer)
		{
			databaseType = DbType.oracle;
		}
		else
		{
			dbServer = getBooleanProperty(DB_MS_SQL);
			if(dbServer)
			{
				databaseType = DbType.mssql;
			}
			else
				throw new ConfigurationException("Неизвестный тип сервера приложений:"+appServer);
		}

		Boolean cryptoType = getBooleanProperty(CRYPTO_PLUGIN);
		if(cryptoType)
		{
			cryptoPluginType = CryptoPluginType.rs;
		}

		cryptoType = getBooleanProperty(CRYPTO_PLUGIN_SBRF);
		if(cryptoType)
		{
			cryptoPluginType = CryptoPluginType.sbrf;
		}


		gateTypes = new HashSet<GateType>();
		Boolean gateType = getBooleanProperty(GATE_COD);
		if(gateType)
			gateTypes.add(GateType.cod);

		shadowDatabaseOn = getBooleanProperty(SHADOW_DATABASE);
	}

	protected Boolean getBooleanProperty ( String key )
	{
		String value = getProperty (key );
		if(value == null)
	            return false;
		return Boolean.parseBoolean(value);
	}

	private String readApplicationPort()
	{
		try
		{
			switch(getApplicationServerType())
			{
				case oracle:  ///j2ee/home/config/default-web-site.xml
				{
					StringBuilder filePath = new StringBuilder(getApplicationServerInstallDir());
					filePath.append(File.separator).append("j2ee");
					filePath.append(File.separator).append("home");
					filePath.append(File.separator).append("config");
					filePath.append(File.separator).append("default-web-site.xml");
					Document document = new XmlFileReader(new File(filePath.toString())).readDocument();

					return document.getDocumentElement().getAttribute("port");
				}
				case websphere: //[WAS]/profiles/<profile_name>/config/cells/<cell_name>/nodes/<node_name>/serverindex.xml
				{
					Class adminServiceFactoryClass = ClassHelper.loadClass(WS_ADMIN_SERVICE_FACTORY);
					Method getAdminServiceMethod = adminServiceFactoryClass.getMethod("getAdminService");
					Object adminService = getAdminServiceMethod.invoke(null);
					Class adminServiceClass = adminService.getClass();
					String cellName = (String) adminServiceClass.getMethod("getCellName").invoke(adminService);
					String nodeName = (String) adminServiceClass.getMethod("getNodeName").invoke(adminService);
					String serverName = (String) adminServiceClass.getMethod("getProcessName").invoke(adminService);

					if (StringHelper.isEmpty(cellName) || StringHelper.isEmpty(nodeName)|| StringHelper.isEmpty(serverName))
						throw new ConfigurationException(WS_PORT_ERROR + "Не найдены необходимые параметры конфигурирования СП");

					log.trace(StringUtils.join(new String[]{WS_PORT_TRACE, "serverName=", serverName, " cellName=", cellName, " nodeName=", nodeName}));

					StringBuilder cofigFilePath = new StringBuilder(System.getProperty("user.install.root"));
					appendPathElements(cofigFilePath, "config", "cells", cellName, "nodes", nodeName, "serverindex.xml");
					String cofigFilePathStr = cofigFilePath.toString();

					log.trace(StringUtils.join(new String[]{WS_PORT_TRACE, "cofigFilePath=", cofigFilePathStr}));

					XPathFactory xPathFactory = XPathFactory.newInstance();
					XPath xpath = xPathFactory.newXPath();
					Document serverIndexDocument = new XmlFileReader(new File(cofigFilePathStr)).readDocument();
					XPathExpression portPathExpression = xpath.compile("//serverEntries[@serverName='" + serverName + "']/specialEndpoints[@endPointName='WC_defaulthost']/endPoint/@port");
					return (String) portPathExpression.evaluate(serverIndexDocument.getDocumentElement(), XPathConstants.STRING);
				}

			}
		}
		catch (Exception e)
		{
			throw new ConfigurationException("Ошибка при чтении порта приложения.", e);
		}

		throw new ConfigurationException("Неизвестный СП");
	}

	private void appendPathElements(StringBuilder path, String... pathElements)
	{
		for (String el : pathElements)
		{
			path.append(File.separator).append(el);
		}
	}
}
