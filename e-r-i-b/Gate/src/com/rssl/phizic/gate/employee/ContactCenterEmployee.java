package com.rssl.phizic.gate.employee;

/**
 * @author akrenev
 * @ created 29.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * сотрудник контактного центра
 */

public interface ContactCenterEmployee
{
	/**
	 * @return иденитфикатор
	 */
	public Long getId();
	/**
	 * @return идентификатор
	 */
	public String getExternalId();

	/**
	 * @return имя
	 */
	public String getName();

	/**
	 * @return подразделение
	 */
	public String getDepartment();

	/**
	 * @return площадка
	 */
	public String getArea();
}
