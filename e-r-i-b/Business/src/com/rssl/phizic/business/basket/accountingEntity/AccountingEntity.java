package com.rssl.phizic.business.basket.accountingEntity;

import com.rssl.phizic.common.types.basket.AccountingEntityType;

/**
 * @author osminin
 * @ created 09.04.14
 * @ $Author$
 * @ $Revision$
 *
 * Объект учета
 */
public interface AccountingEntity
{
	/**
	 * @return идентификатор объекта учета
	 */
	public Long getId();

	/**
	 * @return идентификатор логина пользователя
	 */
	public long getLoginId();

	/**
	 * @param loginId идентификатор логина пользователя
	 */
	public void setLoginId(long loginId);

	/**
	 * @return наименование объекта учета
	 */
	public String getName();

	/**
	 * @param name наименование объекта учета
	 */
	public void setName(String name);

	/**
	 * @return тип объекта учета
	 */
	public AccountingEntityType getType();
}
