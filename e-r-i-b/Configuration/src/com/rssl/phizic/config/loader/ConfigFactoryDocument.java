package com.rssl.phizic.config.loader;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.ConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

/**
 * Настройка конфигов.
 *
 * @author bogdanov
 * @ created 18.07.2013
 * @ $Author$
 * @ $Revision$
 */

public class ConfigFactoryDocument
{
	private final Map<String, ConfigInfo> configs = new HashMap<String, ConfigInfo>();
	private final Map<String, DbInfo> dbInfoByName = new HashMap<String, DbInfo>();

	public ConfigFactoryDocument(Document configsDoc) throws Exception
	{
		loadDbInfos(configsDoc.getDocumentElement());
		loadConfigs(configsDoc.getDocumentElement());
	}

	private void loadDbInfos(Element configsConfig) throws Exception
	{
		Element refreshEveryElement = (Element) XmlHelper.selectNodeList(configsConfig, "/config-factory/dbs/refreshEvery").item(0);
		final int refreshEvery = Integer.parseInt(XmlHelper.getElementText(refreshEveryElement));

		XmlHelper.foreach(configsConfig, "/config-factory/dbs/db", new ForeachElementAction()
		{
			public void execute(Element readerConfig) throws Exception
			{
				DbInfo info = new DbInfo();
				info.setName(readerConfig.getAttribute("name"));
				info.setPeriodForUpdate(refreshEvery);
				if (dbInfoByName.containsKey(info.getName()))
					throw new RuntimeException("Не может двух ридеров с одинаковым названием " + info.getName());
				dbInfoByName.put(info.getName(), info);
				loadInstanceInfo(info, readerConfig);
			}
		});
	}

	private void loadInstanceInfo(final DbInfo info, Element readerConfig) throws Exception
	{
		XmlHelper.foreach(readerConfig, "./instanceInfo", new ForeachElementAction()
		{
			public void execute(Element instanceInfo) throws Exception
			{
				String dbInstance = instanceInfo.getAttribute("dbInstance");
				String appInstance = instanceInfo.hasAttribute("applicationInstance") ? instanceInfo.getAttribute("applicationInstance") : ApplicationConfig.getIt().getApplicationPrefix();
				info.setDbInstance(dbInstance, appInstance);
			}
		});
	}

	private void loadConfigs(Element configsConfig) throws Exception
	{
		final DbInfo defaultDB = dbInfoByName.get("defaultDB");
		XmlHelper.foreach(configsConfig, "/config-factory/configs/config", new ForeachElementAction()
		{
			public void execute(Element configConfig) throws Exception
			{
				ConfigInfo info = new ConfigInfo();
				info.setInstance(configConfig.getAttribute("instance"));

				loadImplementation(info, configConfig, defaultDB);

				if (configs.containsKey(info.getInstance()))
					throw new RuntimeException("Не может двух конфигов одного инстанса " + info.getInstance());
				configs.put(info.getInstance(), info);
			}
		});
	}

	private void loadImplementation(final ConfigInfo info, Element configConfig, final DbInfo defaultDB) throws Exception
	{
		XmlHelper.foreach(configConfig, "./implementation", new ForeachElementAction()
		{
			public void execute(Element configImplConfig) throws Exception
			{
				ImplementationInfo iinfo = new ImplementationInfo();
				iinfo.setClazz(configImplConfig.getAttribute("class"));
				String appl = configImplConfig.getAttribute("application");
				iinfo.setReader(configImplConfig.getAttribute("useReader"));
				if (StringHelper.isEmpty(appl))
					info.setDefaultImplementation(iinfo);
				else
				{
					iinfo.setApplication(Application.valueOf(appl));
					info.addImplementations(iinfo.getApplication(), iinfo);
				}
				loadReadFile(configImplConfig, iinfo);
				iinfo.setDbInfo(defaultDB);
			}
		});
	}

	private void loadReadFile(Element configImplConfig, final ImplementationInfo info) throws Exception
	{
		NodeList list = XmlHelper.selectNodeList(configImplConfig, "./readFile");
		if (list.getLength() == 0)
			return;
		if (list.getLength() > 1)
			throw new ConfigurationException("Для конфига может быть указан только один файл для чтения");

		Element fileNameElement = (Element) list.item(0);
		info.setFile(XmlHelper.getElementText(fileNameElement));
	}

	/**
	 * @param className имя класса конфига.
	 * @return информация по конфигу.
	 */
	public ConfigInfo getConfig(String className)
	{
		if (!configs.containsKey(className))
			throw new ConfigurationException("Невозможно найти конфиг для класса " + className);

		return configs.get(className);
	}

	/**
	 * @param name имя ридера.
	 * @return информация по ридеру.
	 */
	public DbInfo getDbInfoByName(String name)
	{
		if (!dbInfoByName.containsKey(name))
			throw new ConfigurationException("Невозможно найти описание базы данных по имени " + name);

		return dbInfoByName.get(name);
	}

	/**
	 * Добавляет ридер к списку настроек.
	 *
	 * @param dbInfo информация о ридере.
	 */
	public void addDbInfo(DbInfo dbInfo)
	{
		dbInfoByName.put(dbInfo.getName(), dbInfo);
	}
}
