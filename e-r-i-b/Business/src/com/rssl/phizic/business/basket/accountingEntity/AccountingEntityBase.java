package com.rssl.phizic.business.basket.accountingEntity;

import com.rssl.phizic.common.types.basket.AccountingEntityType;

/**
 * @author osminin
 * @ created 09.04.14
 * @ $Author$
 * @ $Revision$
 *
 * �������� - ������ �����
 */
public abstract class AccountingEntityBase implements AccountingEntity
{
	private Long id;                                //�������������
	private long loginId;                            //����� ������������
	private String name;                            //����������� ������� �����

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * 4hibernate
	 */
	public void setType(AccountingEntityType type)
	{}

	public long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(long loginId)
	{
		this.loginId = loginId;
	}
}
