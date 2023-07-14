package com.rssl.phizic.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Данный конфиг читает значения свойств из базы, если они отсутствуют то берутся из проперти файла
 * @author Roshka
 * @ created 06.02.2006
 * @ $Author: bogdanov $
 * @ $Revision: 56916 $
 */
public class DbPropertyReader extends PropertyReader
{
	private final String dbInstance;

	/**
	 * Говорит о том, что началось обновление. В этом потоке мы должны перечитать настройки.
	 */
	private volatile boolean isUpdating = false;

	/**
	 * Следующее время обновления ридера..
	 */
	private final AtomicLong lastUpdateTime = new AtomicLong(0L);

	private long updatePeriod = -1;

	/**
	 * Принудительное обновление ридера.
	 */
	synchronized void sendRefresh()
	{
		lastUpdateTime.set(0);
	}

	public String getFileName()
	{
		throw new UnsupportedOperationException();
	}

	/**
	 * @param category категория в БД (оно же имя ресурса в class path, если ресурс с таким именем отсутсвует то используется только БД)
	 * @param dbInstance - имя экземпляр БД
	 */
	protected DbPropertyReader(String category, String dbInstance)
	{
		super(category);
		this.dbInstance = dbInstance;
	}

	protected Map<String, String> doRefresh()
	{
		//нужно обновить настройки. Вариантов два: либо настройки уже загружались (на время обновления настроек другие потоки берут старые настройки)
		//либо мы еще ни разу не загружали настройки (блокируем все потоки на время вычитывания настроек).
		isUpdating = lastUpdateTime.get() > 0;
		try
		{
			Map<String, String> temp = new HashMap<String, String>();
			List<Property> list = DbPropertyService.findProperties(category, dbInstance);
			for (Property property : list)
				temp.put(property.getKey(), property.getValue());

			lastUpdateTime.set(System.currentTimeMillis());
			return temp;
		}
		catch (Exception e)
		{
			throw new ConfigurationException(e.getMessage(), e);
		}
		finally
		{
			isUpdating = false;
		}
	}


	@Override
	protected boolean needUpdate(long configLastUpdateTime)
	{
		if (needUpdateReader())
			return true;

		return configLastUpdateTime < lastUpdateTime.get();
	}

	@Override
	protected boolean needUpdateReader()
	{
		if (isUpdating)
			return false;

		long lstUpdateTime = lastUpdateTime.get();
		return lstUpdateTime <= 0 || (0 < updatePeriod &&  lstUpdateTime + updatePeriod < System.currentTimeMillis()) || super.needUpdateReader();
	}

	/**
	 * @param periodForUpdate пероид обновления ридера.
	 */
	void setUpdatePeriod(int periodForUpdate)
	{
		updatePeriod = periodForUpdate;
	}
}
