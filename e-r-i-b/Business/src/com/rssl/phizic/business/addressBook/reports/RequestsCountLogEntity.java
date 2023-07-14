package com.rssl.phizic.business.addressBook.reports;

import java.util.Date;

/**
 * @author akrenev
 * @ created 19.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * Сущность лога синхронизации адресной книги
 */

public class RequestsCountLogEntity extends ReportEntityBase
{
	private Long id;
	private Date synchronizationDate;
	private Long count;

	/**
	 * @return идентификатор записи
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификатор записи
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}
	/**
	 * @return дата синхронизации
	 */
	public Date getSynchronizationDate()
	{
		return synchronizationDate;
	}

	/**
	 * задать дату синхронизации
	 * @param synchronizationDate дата
	 */
	public void setSynchronizationDate(Date synchronizationDate)
	{
		this.synchronizationDate = synchronizationDate;
	}

	/**
	 * @return количество синхронизированных контактов
	 */
	public Long getCount()
	{
		return count;
	}

	/**
	 * Установить количество синхронизированных контактов
	 * @param count количество синхронизированных контактов
	 */
	public void setCount(Long count)
	{
		this.count = count;
	}
}
