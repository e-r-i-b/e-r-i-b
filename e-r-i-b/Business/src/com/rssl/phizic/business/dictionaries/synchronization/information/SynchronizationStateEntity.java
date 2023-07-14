package com.rssl.phizic.business.dictionaries.synchronization.information;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author akrenev
 * @ created 22.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * Состояние синхронизации справочников
 */

public class SynchronizationStateEntity implements Serializable
{
	private Long lastUpdateId;
	private Calendar lastUpdateDate;
	private SynchronizationState state;
	private Long errorCount;

	/**
	 * @return идентификатор последней обновленной записи
	 */
	public Long getLastUpdateId()
	{
		return lastUpdateId;
	}

	/**
	 * задать идентификатор последней обновленной записи
	 * @param lastUpdateId идентификатор последней обновленной записи
	 */
	public void setLastUpdateId(Long lastUpdateId)
	{
		this.lastUpdateId = lastUpdateId;
	}

	/**
	 * @return дата последней обновленной записи
	 */
	public Calendar getLastUpdateDate()
	{
		return lastUpdateDate;
	}

	/**
	 * задать датупоследней обновленной записи
	 * @param lastUpdateDate дата последней обновленной записи
	 */
	public void setLastUpdateDate(Calendar lastUpdateDate)
	{
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * @return состояние обновления
	 */
	public SynchronizationState getState()
	{
		return state;
	}

	/**
	 * задать состояние обновления
	 * @param state состояние обновления
	 */
	public void setState(SynchronizationState state)
	{
		this.state = state;
	}

	/**
	 * @return количество ошибок
	 */
	public Long getErrorCount()
	{
		return errorCount;
	}

	/**
	 * задать количество ошибок
	 * @param errorCount количество ошибок
	 */
	public void setErrorCount(Long errorCount)
	{
		this.errorCount = errorCount;
	}
}
