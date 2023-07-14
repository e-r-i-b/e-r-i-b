package com.rssl.phizic.business.fund.initiator;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author osminin
 * @ created 16.09.14
 * @ $Author$
 * @ $Revision$
 *
 * �������� - ������ �����������
 */
public class FundGroup
{
	private static final char PHONES_SEPARATOR = ',';

	private Long id;         //������������� ������
	private String name;     //��� ������
	private long loginId;    //������������� ������ �������
	private List<FundGroupPhone> phones = new ArrayList<FundGroupPhone>();    //������ �����������

	public FundGroup(long loginId, String name)
	{
		this.loginId = loginId;
		this.name = name;
	}

	public FundGroup() {}

	/**
	 * @return ������������� ������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id ������������� ������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ������������ ������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name ������������ ������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return �����
	 */
	public long getLoginId()
	{
		return loginId;
	}

	/**
	 * @param loginId �����
	 */
	public void setLoginId(long loginId)
	{
		this.loginId = loginId;
	}

	public List<FundGroupPhone> getPhones()
	{
		return phones;
	}

	public void setPhones(List<FundGroupPhone> phones)
	{
		this.phones = phones;
	}

	public void setPhones(String phones)
	{
		List<String> phonesList= Arrays.asList(StringUtils.split(phones, PHONES_SEPARATOR));
		for (String phone:phonesList)
		{
			this.phones.add(new FundGroupPhone(getId(), phone));
		}
	}
}
