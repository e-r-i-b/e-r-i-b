package com.rssl.phizic.business.basket.accountingEntity;

import com.rssl.phizic.common.types.basket.AccountingEntityType;

/**
 * @author osminin
 * @ created 09.04.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ �����
 */
public interface AccountingEntity
{
	/**
	 * @return ������������� ������� �����
	 */
	public Long getId();

	/**
	 * @return ������������� ������ ������������
	 */
	public long getLoginId();

	/**
	 * @param loginId ������������� ������ ������������
	 */
	public void setLoginId(long loginId);

	/**
	 * @return ������������ ������� �����
	 */
	public String getName();

	/**
	 * @param name ������������ ������� �����
	 */
	public void setName(String name);

	/**
	 * @return ��� ������� �����
	 */
	public AccountingEntityType getType();
}
