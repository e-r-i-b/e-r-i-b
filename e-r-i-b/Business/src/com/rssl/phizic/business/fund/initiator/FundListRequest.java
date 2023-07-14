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
 * Сущность - зарпос на сбор денег, выводимый при получении списка исходящих запросов
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
	 * @return идентификатор запроса
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор запроса
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return дата создания
	 */
	public Calendar getCreatedDate()
	{
		return createdDate;
	}

	/**
	 * @param createdDate дата создания
	 */
	public void setCreatedDate(Calendar createdDate)
	{
		this.createdDate = createdDate;
	}

	/**
	 * @return дата закрытия
	 */
	public Calendar getClosedDate()
	{
		return closedDate;
	}

	/**
	 * @param closedDate дата закрытия
	 */
	public void setClosedDate(Calendar closedDate)
	{
		this.closedDate = closedDate;
	}

	/**
	 * @return ожидаемая сумма
	 */
	public BigDecimal getRequiredSum()
	{
		return requiredSum;
	}

	/**
	 * @param requiredSum ожидаемая сумма
	 */
	public void setRequiredSum(BigDecimal requiredSum)
	{
		this.requiredSum = requiredSum;
	}

	/**
	 * @return статус запроса
	 */
	public FundRequestState getState()
	{
		return state;
	}

	/**
	 * @param state статус запроса
	 */
	public void setState(FundRequestState state)
	{
		this.state = state;
	}

	/**
	 * @return собранная сумма
	 */
	public BigDecimal getAccumulatedSum()
	{
		return accumulatedSum;
	}

	/**
	 * @param accumulatedSum собранная сумма
	 */
	public void setAccumulatedSum(BigDecimal accumulatedSum)
	{
		this.accumulatedSum = accumulatedSum;
	}
}
