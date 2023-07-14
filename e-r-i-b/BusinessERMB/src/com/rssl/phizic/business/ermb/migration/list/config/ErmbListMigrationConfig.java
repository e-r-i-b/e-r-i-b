package com.rssl.phizic.business.ermb.migration.list.config;

import com.rssl.phizic.config.BeanConfigBase;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Конфиг для списковой миграции
 * @author Puzikov
 * @ created 25.12.13
 * @ $Author$
 * @ $Revision$
 */

public class ErmbListMigrationConfig extends BeanConfigBase<ErmbListMigrationConfigBean>
{
	private static final String CODENAME = "ErmbListMigrationConfig";

	public ErmbListMigrationConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * разрешение доступа к откату миграции клиента из МБК/МБВ в ЕРИБ
	 */
	private boolean rollbackAccess;

	/**
	 * url сервиса для менеджера задач мигратора
	 */
	private String serviceUrl;

	/**
	 * максимальный размер загружаемого файла в килобайтах
	 */
	private int csvFileMaxSize;

	/**
	 * расшареннный каталог с csv-файлами
	 */
	private String csvSharedDir;

	/**
	 * каталог с логами миграции
	 */
	private String logDir;

	/**
	 * Строка с последовательностью блоков для миграции
	 */
	private String migrationBlockSequence;

	/**
	 * Блок приложения, в который складываются новые данные
	 */
	private long inflatableBlockNumber;

	/**
	 * максимальное количество клиентов, мигрируемых за один запуск джоба
	 */
	private int migrationBatchSize;

	/**
	 * тербанк, в котором проводится миграция
	 */
	private String tb;

	/**
	 * Отправлять ли приветственные смс по умолчанию (в дополнение к миграционным)
	 */
	private boolean defaultWelcomeSms;

	public boolean getRollbackAccess()
	{
		return rollbackAccess;
	}

	public String getServiceUrl()
	{
		return serviceUrl;
	}

	public int getCsvFileMaxSize()
	{
		return csvFileMaxSize;
	}

	public String getCsvSharedDir()
	{
		return csvSharedDir;
	}

	public String getLogDir()
	{
		return logDir;
	}

	public List<Long> getMigrationBlockSequence()
	{
		String value = migrationBlockSequence;

		List<Long> result = new ArrayList<Long>();
		for (String string : StringUtils.split(value, ','))
		{
			String trimmedString = StringUtils.trim(string);
			result.add(Long.parseLong(trimmedString));
		}
		return result;
	}

	public long getInflatableBlockNumber()
	{
		return inflatableBlockNumber;
	}

	public int getMigrationBatchSize()
	{
		return migrationBatchSize;
	}

	public String getTb()
	{
		return tb;
	}

	public boolean isDefaultWelcomeSms()
	{
		return defaultWelcomeSms;
	}

	public void setDefaultWelcomeSms(boolean defaultWelcomeSms)
	{
		this.defaultWelcomeSms = defaultWelcomeSms;
	}

	@Override
	protected String getCodename()
	{
		return CODENAME;
	}

	@Override
	protected Class<ErmbListMigrationConfigBean> getConfigDataClass()
	{
		return ErmbListMigrationConfigBean.class;
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		ErmbListMigrationConfigBean configBean = getConfigData();

		rollbackAccess = configBean.isRollbackAccess();
		serviceUrl = configBean.getServiceUrl();
		csvFileMaxSize = configBean.getCsvFileMaxSize();
		csvSharedDir = configBean.getCsvSharedDir();
		logDir = configBean.getLogDir();
		migrationBlockSequence = configBean.getMigrationBlockSequence();
		inflatableBlockNumber = configBean.getInflatableBlockNumber();
		migrationBatchSize = configBean.getMigrationBatchSize();
		tb = configBean.getTb();
		defaultWelcomeSms = configBean.getDefaultWelcomeSms();
	}
}
