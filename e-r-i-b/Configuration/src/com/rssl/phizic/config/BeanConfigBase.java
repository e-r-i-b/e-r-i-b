package com.rssl.phizic.config;

import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.resources.ResourceHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;

import java.io.IOException;
import javax.xml.bind.JAXBException;

/**
 * @author Erkin
 * @ created 22.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Обновляемый конфиг с хранением данных в таблице CONFIGS
 */
public abstract class BeanConfigBase<ConfigData> extends Config
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private static final ConfigBeanService configBeanService = new ConfigBeanService();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * конструктор
	 */
	protected BeanConfigBase(PropertyReader reader)
	{
		// Читать какой-либо property-файл этому конфигу не требуется
		super(reader);
	}

	/**
	 * @return кодификатор конфига
	 */
	protected abstract String getCodename();

	/**
	 * @return имя ресурса с предопределёнными значениями конфига
	 */
	protected String getDefaultsResourceName()
	{
		return getReader().getFileName();
	}

	protected abstract Class<ConfigData> getConfigDataClass();

	/**
	 * Возвращает данные конфига
	 * @return данные конфига (never null)
	 */
	protected ConfigData getConfigData() throws ConfigurationException
	{
		String dataString = loadData();

		if (StringHelper.isEmpty(dataString))
		{
			dataString = readDefaultData();

			if (StringHelper.isEmpty(dataString))
				throw new ConfigurationException("Конфига " + getCodename() + " нет в базе, нет в " + getDefaultsResourceName());

			saveData(dataString);
		}

		return unmarshalData(dataString);
	}

	/**
	 * сохранение конфига в базу
	 */
	public final void save()
	{
		try
		{
			//Кладём xml в базу
			String configData = JAXBUtils.marshalBean(doSave());
			configBeanService.saveConfig(this.getCodename(), configData);
		}

		catch (JAXBException e)
		{
			throw new ConfigurationException("Ошибка загрузки в базу конфига " + this.getCodename(), e);
		}
	}

	protected <T> T doSave()
	{
		throw new UnsupportedOperationException();
	}

	private String loadData() throws ConfigurationException
	{
		String codename = getCodename();
		if (log.isDebugEnabled())
			log.debug("Читаем бин-конфиг " + codename + " из базы");
		return configBeanService.getConfig(codename);
	}

	private String readDefaultData() throws ConfigurationException
	{
		String resourceName = getDefaultsResourceName();
		try
		{
			if (log.isDebugEnabled())
				log.debug("Читаем бин-конфиг " + getCodename() + " из ресурса " + resourceName);
			return ResourceHelper.loadResourceAsString(resourceName);
		}
		catch (IOException e)
		{
			throw new ConfigurationException("Сбой при чтении конфига " + getCodename() + " из ресурса " + resourceName, e);
		}
	}

	protected void saveData(String dataString)
	{
		String codename = getCodename();
		if (log.isDebugEnabled())
			log.debug("Записываем бин-конфиг " + codename + " в базу");
		configBeanService.saveConfig(codename, dataString);
	}

	private ConfigData unmarshalData(String dataString)
	{
		try
		{
			return JAXBUtils.unmarshalBean(getConfigDataClass(), dataString);
		}
		catch (JAXBException e)
		{
			throw new ConfigurationException("Сбой при десериализации конфига " + getCodename(), e);
		}
	}
}
