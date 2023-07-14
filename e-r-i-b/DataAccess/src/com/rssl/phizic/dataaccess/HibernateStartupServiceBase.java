package com.rssl.phizic.dataaccess;

import com.rssl.phizic.dataaccess.config.DataSourceConfig;
import com.rssl.phizic.startup.StartupService;
import com.rssl.phizic.utils.PropertyHelper;
import com.rssl.phizic.utils.StringHelper;
import com.sun.org.apache.xpath.internal.CachedXPathAPI;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Environment;
import org.hibernate.util.ExternalSessionFactoryConfig;
import org.hibernate.util.PropertiesHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

/**
 * @author Evgrafov
 * @ created 27.06.2006
 * @ $Author: erkin $
 * @ $Revision: 35497 $
 */

public abstract class HibernateStartupServiceBase extends ExternalSessionFactoryConfig implements StartupService
{
	private static final String HIBERNATE_CONNECTION_DATASOURCE = "hibernate.connection.datasource";
	private static final String APPLICATION_INSTANCE = "com.rssl.iccs.application.instance.prefix";
	private static final String APPLICATION_INSTANCE_PREFIX_CONSTANT = "@application.instance.prefix@";

	protected static final Log log = LogFactory.getLog(HibernateStartupServiceBase.class);
	private static final String HIBERNATE_DTD_PUBLIC_ID = "-//Hibernate/Hibernate Mapping DTD 3.0//EN";

	private Properties properties = new Properties();
	private String configResources;
	private String hibernateCfg;
	private String datasourceConfigClass;

	private DocumentBuilder documentBuilder;
	private CachedXPathAPI xPathAPI = new CachedXPathAPI();

	/////////////////////////////////////////////////////////////////////////////////////

	protected HibernateStartupServiceBase()
	{
		try
		{
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilder = documentBuilderFactory.newDocumentBuilder();

			// Отключаем загрузку hibernate-mapping-3.0.dtd с сайта hibernate
			// Взято с http://stackoverflow.com/questions/4002885/unknown-host-exception-while-parsing-an-xml-file
			documentBuilder.setEntityResolver(new EntityResolver()
			{
				public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException
				{
					if(publicId.equals(HIBERNATE_DTD_PUBLIC_ID))
						return new InputSource(new StringReader(""));
					else return null;
				}
			});
		}
		catch (ParserConfigurationException e)
		{
			throw new RuntimeException(e);
		}
	}

	public String getDatasource()
	{
		return getProperty(Environment.DATASOURCE);
	}

	public void setDatasource(String datasource)
	{
		setProperty(Environment.DATASOURCE, datasource);
	}

	public String getConfigResources()
	{
		return configResources;
	}

	public void setConfigResources(String configResources)
	{
		this.configResources = configResources;
	}

	public void addConfigResource(String config)
	{
		if (StringHelper.isEmpty(configResources))
			configResources = config.trim();
		else configResources += ", " + config.trim();
	}

	public String getHibernateCfg()
	{
		return hibernateCfg;
	}

	public void setHibernateCfg(String hibernateCfg)
	{
		this.hibernateCfg = hibernateCfg;
	}

	public String getDatasourceConfigClass()
	{
		return datasourceConfigClass;
	}

	public void setDatasourceConfigClass(String datasourceConfigClass)
	{
		this.datasourceConfigClass = datasourceConfigClass;
	}

	protected void readDatasourceConfig()
	{
		if(datasourceConfigClass == null)
			return;

		DataSourceConfig dataSourceConfig;

		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			Class<?> clazz = classLoader.loadClass(datasourceConfigClass);
			dataSourceConfig = (DataSourceConfig) clazz.newInstance();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}

		setDatasource(dataSourceConfig.getDataSourceName());
		setDialect(dataSourceConfig.getHibernateDialect());
	}

	protected void loadHibernateCfg()
	{
		try
		{
			if(hibernateCfg == null)
				return;

			Document document = loadDocument(hibernateCfg);

			setProperties(document);
			addMappings(document);
		}
		catch (HibernateException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new HibernateException(e);
		}
	}

	private void setProperties(Document cfg) throws HibernateException
	{
		try
		{
			Element root = cfg.getDocumentElement();

			NodeList nodeList = xPathAPI.selectNodeList(root, "session-factory/property/@name");
			for (int i = 0; i < nodeList.getLength(); i++)
			{
				Node node = nodeList.item(i);
				String name = node.getNodeValue();
				String value = xPathAPI.selectSingleNode(root, "session-factory/property[@name='" + name + "']/text()").getNodeValue();
				//подменяем ключ на префикс приложения. см. rsRetailV6r4.hibernate.cfg.xml
				if(HIBERNATE_CONNECTION_DATASOURCE.equals(name))
				{
					value = value.replaceAll(APPLICATION_INSTANCE_PREFIX_CONSTANT,getAppPrefix());
				}
				setProperty(name, value);
			}
		}
		catch (TransformerException e)
		{
			throw new HibernateException(e);
		}

	}

	private String getAppPrefix()
	{
		Properties iccsProperties = PropertyHelper.readProperties("iccs.properties");
		return iccsProperties.getProperty(APPLICATION_INSTANCE);
	}

	protected void addMappings() throws HibernateException
	{
		try
		{
			String[] configs = PropertiesHelper.toStringArray(configResources, " ,\n\t\r\f");


			for (String config : configs)
			{
				Document cfg = loadDocument(config);
				addMappings(cfg);
			}
		}
		catch (HibernateException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new HibernateException(e);
		}
	}

	private void addMappings(Document cfg) throws TransformerException
	{
		NodeList nodeList = xPathAPI.selectNodeList(cfg.getDocumentElement(), "session-factory/mapping/@resource");
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Node node = nodeList.item(i);
			addMapResource(node.getNodeValue());
		}
	}

	private Document loadDocument(String resource) throws HibernateException
	{
		try
		{
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream stream = classLoader.getResourceAsStream(resource);
			if(stream == null)
				throw new HibernateException("Configuration " + resource + " not found in class path");
			return documentBuilder.parse(stream);
		}
		catch (HibernateException e)
		{
			throw e;
		}
		catch (Throwable e)
		{
			throw new HibernateException(e);
		}
	}

	protected Map getExtraProperties()
	{
		return Collections.unmodifiableMap(properties);
	}

	public String getPropertyList()
	{
		return buildProperties().toString();
	}

	public String getProperty(String property)
	{
		return properties.getProperty(property);
	}

	public void setProperty(String property, String value)
	{
		properties.setProperty(property, value);
	}

	// TODO: Создать отдельный Logging service и вынести туда эти два метода
	protected void startLogging()
	{
		Properties props = PropertyHelper.readProperties("phizic-log4j.properties");
		PropertyConfigurator.configure(props);
	}

	protected void stopLogging()
	{
		Logger.getRootLogger().removeAppender("SYS_LOG");
	}
}
