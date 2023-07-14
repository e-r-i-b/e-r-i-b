package com.rssl.phizic.business.fund.initiator;

import com.rssl.phizic.common.types.fund.FundRequestState;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * @author osminin
 * @ created 29.09.14
 * @ $Author$
 * @ $Revision$
 *
 * �������� - ������ �� ���� �����, ��������� ��� ��������� ������ ��������� ��������
 */
public class FundListRequest
{
	private Long id;
	private FundRequestState state;
	private BigDecimal requiredSum;
	private Calendar closedDate;
	private Calendar createdDate;
	private BigDecimal accumulatedSum;

	/**
	 * @return ������������� �������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id ������������� �������
	 */
	public void setId(Long id)
	{
		this.id = id;
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
	 * @return ���� ��������
	 */
	public Calendar getClosedDate()
	{
		return closedDate;
	}

	/**
	 * @param closedDate ���� ��������
	 */
	public void setClosedDate(Calendar closedDate)
	{
		this.closedDate = closedDate;
	}

	/**
	 * @return ��������� �����
	 */
	public BigDecimal getRequiredSum()
	{
		return requiredSum;
	}

	/**
	 * @param requiredSum ��������� �����
	 */
	public void setRequiredSum(BigDecimal requiredSum)
	{
		this.requiredSum = requiredSum;
	}

	/**
	 * @return ������ �������
	 */
	public FundRequestState getState()
	{
		return state;
	}

	/**
	 * @param state ������ �������
	 */
	public void setState(FundRequestState state)
	{
		this.state = state;
	}

	/**
	 * @return ��������� �����
	 */
	public BigDecimal getAccumulatedSum()
	{
		return accumulatedSum;
	}

	/**
	 * @param accumulatedSum ��������� �����
	 */
	public void setAccumulatedSum(BigDecimal accumulatedSum)
	{
		this.accumulatedSum = accumulatedSum;
	}
}
