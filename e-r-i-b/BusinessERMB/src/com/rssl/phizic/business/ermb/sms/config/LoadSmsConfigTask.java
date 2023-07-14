package com.rssl.phizic.business.ermb.sms.config;

import com.rssl.phizic.config.ConfigBeanService;
import com.rssl.phizic.dataaccess.config.ErmbDataSourceConfig;
import com.rssl.phizic.utils.AntUtils;
import com.rssl.phizic.utils.test.JUnitDatabaseConfig;
import com.rssl.phizic.utils.test.SafeTaskBase;
import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;
import org.apache.tools.ant.BuildException;

import java.io.File;
import javax.xml.bind.JAXBException;

/**
 * @author Erkin
 * @ created 06.01.2013
 * @ $Author$
 * @ $Revision$
 */

public class LoadSmsConfigTask extends SafeTaskBase
{
	private static final String CONFIG_XML = "Settings/configs/sbrf/PhizMB/sms-config.xml";

	private static final ConfigBeanService configBeanService = new ConfigBeanService();

	@Override
	public void safeExecute() throws BuildException
	{
		try
		{
			// 1. Читаем xml с настройками (проверяем валидность)
			File projectRoot = AntUtils.getProjectRoot(getProject());
			File configXml = new File(projectRoot, CONFIG_XML);
			SmsConfigBean configBean = JAXBUtils.unmarshalBean(SmsConfigBean.class, configXml);

			// 2. Кладём xml в базу
			String configData = JAXBUtils.marshalBean(configBean);
			configBeanService.saveConfig(SmsConfig.CODENAME, configData);
		}
		catch (JAXBException e)
		{
			throw new BuildException("Ошибка загрузки в базу настроек СМС-канала ЕРМБ", e);
		}
	}

	@Override
	protected JUnitDatabaseConfig getDatabaseConfig()
	{
		return new ErmbDataSourceConfig();
	}

	@Override
	public Object clone() throws CloneNotSupportedException
	{
		return super.clone();
	}
}
