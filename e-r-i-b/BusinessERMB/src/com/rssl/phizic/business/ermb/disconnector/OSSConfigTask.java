package com.rssl.phizic.business.ermb.disconnector;

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
 * @author Gulov
 * @ created 10.09.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Загрузка в таблицу настроек конфига отключения телефона от ЕРМБ
 */
public class OSSConfigTask extends SafeTaskBase
{
	private static final String CONFIG_XML = "Settings/configs/sbrf/PhizMB/oss-config.xml";
	private static final ConfigBeanService configBeanService = new ConfigBeanService();
	public static final String ERROR_MESSAGE = "Ошибка загрузки в таблицу настроек конфига отключения телефона от ЕРМБ";

	@Override
	public void safeExecute() throws Exception
	{
		try
		{
			// 1. Читаем xml с настройками (проверяем валидность)
			File projectRoot = AntUtils.getProjectRoot(getProject());
			File configXml = new File(projectRoot, CONFIG_XML);
			OSSConfigBean configBean = JAXBUtils.unmarshalBean(OSSConfigBean.class, configXml);

			// 2. Кладём xml в базу
			String configData = JAXBUtils.marshalBean(configBean);
			configBeanService.saveConfig(OSSConfig.CODENAME, configData);
		}
		catch (JAXBException e)
		{
			throw new BuildException(ERROR_MESSAGE, e);
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
