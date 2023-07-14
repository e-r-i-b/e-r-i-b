package com.rssl.phizic.business.ext.sbrf.deposits;

import java.util.Calendar;

/**
 * Упрощенное описание вклада
 * @author Pankin
 * @ created 03.03.15
 * @ $Author$
 * @ $Revision$
 */
public class DepositProductShortCut
{
	private Long id; // Идентификатор записи
	private String name; // Название вклада
	private Long productId; // Вид вклада
	private boolean availableOnline; // Признак доступности вклада для открытия онлайн
	private Calendar lastUpdateDate; // Дата последнего изменения записи
	private boolean withMinimumBalance; // Признак первоначального взноса
	private boolean capitalization; // Признак капитализации

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

	public Long getProductId()
	{
		return productId;
	}

	public void setProductId(Long productId)
	{
		this.productId = productId;
	}

	public boolean isAvailableOnline()
	{
		return availableOnline;
	}

	public void setAvailableOnline(boolean availableOnline)
	{
		this.availableOnline = availableOnline;
	}

	public Calendar getLastUpdateDate()
	{
		return lastUpdateDate;
	}

	public void setLastUpdateDate(Calendar lastUpdateDate)
	{
		this.lastUpdateDate = lastUpdateDate;
	}

	public boolean isWithMinimumBalance()
	{
		return withMinimumBalance;
	}

	public void setWithMinimumBalance(boolean withMinimumBalance)
	{
		this.withMinimumBalance = withMinimumBalance;
	}

	public boolean isCapitalization()
	{
		return capitalization;
	}

	public void setCapitalization(boolean capitalization)
	{
		this.capitalization = capitalization;
	}
}
