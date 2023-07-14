package com.rssl.phizic.business.ermb.bankroll.config;

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
 * Конфиг загрузки в бд правил включения видимости продуктов по умолчанию
 * @author Rtischeva
 * @ created 03.12.13
 * @ $Author$
 * @ $Revision$
 */
public class LoadBankrollProductRuleConfigTask extends SafeTaskBase
{
	private static final String CONFIG_XML = "Settings/configs/sbrf/PhizMB/bankroll-product-rules-config.xml";

	private static final ConfigBeanService configBeanService = new ConfigBeanService();

	@Override
	public void safeExecute() throws Exception
	{
		try
		{
			// 1. Читаем xml с настройками (проверяем валидность)
			File projectRoot = AntUtils.getProjectRoot(getProject());
			File configXml = new File(projectRoot, CONFIG_XML);
			BankrollProductRulesConfigBean configBean = JAXBUtils.unmarshalBean(BankrollProductRulesConfigBean.class, configXml);

			// 2. Кладём xml в базу
			String configData = JAXBUtils.marshalBean(configBean);
			configBeanService.saveConfig(BankrollProductRulesConfig.CODENAME, configData);
		}
		catch (JAXBException e)
		{
			throw new BuildException("Ошибка загрузки в базу правил включения видимости продуктов по умолчанию", e);
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
