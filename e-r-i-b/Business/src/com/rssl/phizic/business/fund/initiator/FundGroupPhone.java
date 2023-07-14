package com.rssl.phizic.business.fund.initiator;

import java.io.Serializable;

/**
 * @author osminin
 * @ created 25.09.14
 * @ $Author$
 * @ $Revision$
 *
 * Сущность - участник группы получателей
 */
public class FundGroupPhone implements Serializable
{
	private Long groupId;  //идентификатор группы
	private String phone;  //номер телефона получателя

	public FundGroupPhone(Long groupId, String phone)
	{
		this.groupId = groupId;
		this.phone = phone;
	}

	public FundGroupPhone() {}

	/**
	 * @return идентификатор группы получателей
	 */
	public Long getGroupId()
	{
		return groupId;
	}

	/**
	 * @param groupId идентификатор группы получателей
	 */
	public void setGroupId(Long groupId)
	{
		this.groupId = groupId;
	}

	/**
	 * @return номер телефона участника
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * @param phone номер телефона участника
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public boolean equals(Object obj)
	{
		if (!(obj instanceof FundGroupPhone))
			return false;

		FundGroupPhone second = (FundGroupPhone)obj;
		return (this.getGroupId().equals(second.getGroupId())
			&& this.getPhone().equals(second.getPhone()));
	}
}
