package com.rssl.phizic.business.migration;

/**
 * @author akrenev
 * @ created 25.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Информация о миграции
 */

public abstract class MigrationInfo
{
	private Long id;
	private Long batchSize;

	protected MigrationInfo(){}

	protected MigrationInfo(Long id, Long batchSize)
	{
		this.id = id;
		this.batchSize = batchSize;
	}

	/**
	 * @return идентификатор записи
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @return размер пачки миграции
	 */
	public Long getBatchSize()
	{
		return batchSize;
	}
}
