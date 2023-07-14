package com.rssl.phizic.web.migration;

import com.rssl.phizic.business.migration.TotalMigrationInfo;
import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author akrenev
 * @ created 26.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Форма редактирования состояния миграции
 */

public class EditMigrationInfoForm extends ActionFormBase
{
	private TotalMigrationInfo migrationInfo;
	private Long currentId;
	private Long batchSize;
	private long dataTimeout;

	/**
	 * @return информация о миграции
	 */
	@SuppressWarnings("UnusedDeclaration") //jsp
	public TotalMigrationInfo getMigrationInfo()
	{
		return migrationInfo;
	}

	/**
	 * задать информацию о миграции
	 * @param migrationInfo информация
	 */
	public void setMigrationInfo(TotalMigrationInfo migrationInfo)
	{
		this.migrationInfo = migrationInfo;
	}

	/**
	 * @return идентификатор наблюдаемой задачи
	 */
	public Long getCurrentId()
	{
		return currentId;
	}

	/**
	 * задать идентификатор наблюдаемой задачи
	 * @param currentId идентификатор
	 */
	public void setCurrentId(Long currentId)
	{
		this.currentId = currentId;
	}

	/**
	 * @return размер пачки мигрируемых клиентов
	 */
	public Long getBatchSize()
	{
		return batchSize;
	}

	/**
	 * задать размер пачки мигрируемых клиентов
	 * @param batchSize размер
	 */
	@SuppressWarnings("UnusedDeclaration") //ActionServlet
	public void setBatchSize(Long batchSize)
	{
		this.batchSize = batchSize;
	}

	/**
	 * @return время актуальности данных
	 */
	@SuppressWarnings("UnusedDeclaration") // jsp
	public long getDataTimeout()
	{
		return dataTimeout;
	}

	/**
	 * задать время актуальности данных
	 * @param dataTimeout время актуальности данных
	 */
	public void setDataTimeout(long dataTimeout)
	{
		this.dataTimeout = dataTimeout;
	}
}
