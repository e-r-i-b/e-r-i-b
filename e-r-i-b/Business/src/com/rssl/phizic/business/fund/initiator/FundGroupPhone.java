package com.rssl.phizic.business.fund.initiator;

import java.io.Serializable;

/**
 * @author osminin
 * @ created 25.09.14
 * @ $Author$
 * @ $Revision$
 *
 * �������� - �������� ������ �����������
 */
public class FundGroupPhone implements Serializable
{
	private Long groupId;  //������������� ������
	private String phone;  //����� �������� ����������

	public FundGroupPhone(Long groupId, String phone)
	{
		this.groupId = groupId;
		this.phone = phone;
	}

	public FundGroupPhone() {}

	/**
	 * @return ������������� ������ �����������
	 */
	public Long getGroupId()
	{
		return groupId;
	}

	/**
	 * @param groupId ������������� ������ �����������
	 */
	public void setGroupId(Long groupId)
	{
		this.groupId = groupId;
	}

	/**
	 * @return ����� �������� ���������
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * @param phone ����� �������� ���������
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
