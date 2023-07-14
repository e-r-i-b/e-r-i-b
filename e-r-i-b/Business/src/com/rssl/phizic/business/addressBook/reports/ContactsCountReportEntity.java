package com.rssl.phizic.business.addressBook.reports;

/**
 * @author akrenev
 * @ created 11.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * Сущность отчета "По количеству контактов"
 */

public class ContactsCountReportEntity extends ReportEntityBase
{
	private Long count;

	/**
	 * @return количество контактов
	 */
	public Long getCount()
	{
		return count;
	}

	/**
	 * задать количество контактов
	 * @param count количество контактов
	 */
	public void setCount(Long count)
	{
		this.count = count;
	}
}
