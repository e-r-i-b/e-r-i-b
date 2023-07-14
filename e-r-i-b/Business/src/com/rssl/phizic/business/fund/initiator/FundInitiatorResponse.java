package com.rssl.phizic.business.fund.initiator;

import com.rssl.phizic.common.types.fund.FundResponseState;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author osminin
 * @ created 15.09.14
 * @ $Author$
 * @ $Revision$
 *
 * �������� - ������ �� ������� ����� ������� � ����� ����������
 */
public class FundInitiatorResponse
{
	private Long id;
	private String externalId;
	private long requestId;
	private String phone;
	private FundResponseState state;
	private BigDecimal sum;
	private String message;
	private Calendar createdDate;
	private boolean accumulated;
	private String firstName;
	private String surName;
	private String patrName;

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * ������� �� ������ ����� ����������, ����������� '@' � ����������� ��������������
	 * @return  ������� �������������
	 */
	public String getExternalId()
	{
		return externalId;
	}

	/**
	 * @param externalId ������� �������������
	 */
	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	/**
	 * @return ������������� ������� �� ���� �������
	 */
	public long getRequestId()
	{
		return requestId;
	}

	/**
	 * @param requestId ������������� ������� �� ���� �������
	 */
	public void setRequestId(long requestId)
	{
		this.requestId = requestId;
	}

	/**
	 * @return ����� ��������
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * @param phone ����� ��������
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	/**
	 * @return �����, ������� �������� ������ �� ���� ����������
	 */
	public BigDecimal getSum()
	{
		return sum;
	}

	/**
	 * @param sum �����, ������� �������� ������ �� ���� ����������
	 */
	public void setSum(BigDecimal sum)
	{
		this.sum = sum;
	}

	/**
	 * @return ��������� ����������� �����
	 */
	public String getMessage()
	{
		return message;
	}

	/**
	 * @param message ��������� ����������� �����
	 */
	public void setMessage(String message)
	{
		this.message = message;
	}

	/**
	 * @return ������ ��������� �������
	 */
	public FundResponseState getState()
	{
		return state;
	}

	/**
	 * @param state ������ ��������� �������
	 */
	public void setState(FundResponseState state)
	{
		this.state = state;
	}

	/**
	 * @return ���� ��������
	 */
	public Calendar getCreatedDate()
	{
		return createdDate;
	}

	/**
	 * @param createdDate ���� ��������
	 */
	public void setCreatedDate(Calendar createdDate)
	{
		this.createdDate = createdDate;
	}

	/**
	 * @return ��������� �� ���� ������� ����� ����������� �����
	 */
	public boolean isAccumulated()
	{
		return accumulated;
	}

	/**
	 * @param accumulated ��������� �� ���� ������� ����� ����������� �����
	 */
	public void setAccumulated(boolean accumulated)
	{
		this.accumulated = accumulated;
	}

	/**
	 * @return ��� ����������� �����
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @param firstName ��� ����������� �����
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return ������� ����������� �����
	 */
	public String getSurName()
	{
		return surName;
	}

	/**
	 * @param surName ������� ����������� �����
	 */
	public void setSurName(String surName)
	{
		this.surName = surName;
	}

	/**
	 * @return �������� ����������� �����
	 */
	public String getPatrName()
	{
		return patrName;
	}

	/**
	 * @param patrName �������� ����������� �����
	 */
	public void setPatrName(String patrName)
	{
		this.patrName = patrName;
	}

	/**
	 * ��������� ������� ����� ������������ ��� ����������� �� ���������
	 * @return ������ ��� � �������: ��� �������� �
	 */
	public  String getDefaultFIO()
	{
		StringBuilder defaultFIO = new StringBuilder(30);
		if (StringHelper.isNotEmpty(firstName))
		{
			defaultFIO.append(firstName);
		}
		if (StringHelper.isNotEmpty(patrName))
		{
			defaultFIO.append(" ");
			defaultFIO.append(patrName);
		}
		if (StringHelper.isNotEmpty(surName))
		{
			defaultFIO.append(" ");
			defaultFIO.append(surName.charAt(0));
		}
		return defaultFIO.toString();
	}
}
