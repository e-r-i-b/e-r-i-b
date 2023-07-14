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
 * ����������� ������ � ��������� ������ � ������� CONFIGS
 */
public abstract class BeanConfigBase<ConfigData> extends Config
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private static final ConfigBeanService configBeanService = new ConfigBeanService();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * �����������
	 */
	protected BeanConfigBase(PropertyReader reader)
	{
		// ������ �����-���� property-���� ����� ������� �� ���������
		super(reader);
	}

	/**
	 * @return ����������� �������
	 */
	protected abstract String getCodename();

	/**
	 * @return ��� ������� � ���������������� ���������� �������
	 */
	protected String getDefaultsResourceName()
	{
		return getReader().getFileName();
	}

	protected abstract Class<ConfigData> getConfigDataClass();

	/**
	 * ���������� ������ �������
	 * @return ������ ������� (never null)
	 */
	protected ConfigData getConfigData() throws ConfigurationException
	{
		String dataString = loadData();

		if (StringHelper.isEmpty(dataString))
		{
			dataString = readDefaultData();

			if (StringHelper.isEmpty(dataString))
				throw new ConfigurationException("������� " + getCodename() + " ��� � ����, ��� � " + getDefaultsResourceName());

			saveData(dataString);
		}

		return unmarshalData(dataString);
	}

	/**
	 * ���������� ������� � ����
	 */
	public final void save()
	{
		try
		{
			//����� xml � ����
			String configData = JAXBUtils.marshalBean(doSave());
			configBeanService.saveConfig(this.getCodename(), configData);
		}

		catch (JAXBException e)
		{
			throw new ConfigurationException("������ �������� � ���� ������� " + this.getCodename(), e);
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
			log.debug("������ ���-������ " + codename + " �� ����");
		return configBeanService.getConfig(codename);
	}

	private String readDefaultData() throws ConfigurationException
	{
		String resourceName = getDefaultsResourceName();
		try
		{
			if (log.isDebugEnabled())
				log.debug("������ ���-������ " + getCodename() + " �� ������� " + resourceName);
			return ResourceHelper.loadResourceAsString(resourceName);
		}
		catch (IOException e)
		{
			throw new ConfigurationException("���� ��� ������ ������� " + getCodename() + " �� ������� " + resourceName, e);
		}
	}

	protected void saveData(String dataString)
	{
		String codename = getCodename();
		if (log.isDebugEnabled())
			log.debug("���������� ���-������ " + codename + " � ����");
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
			throw new ConfigurationException("���� ��� �������������� ������� " + getCodename(), e);
		}
	}
}
