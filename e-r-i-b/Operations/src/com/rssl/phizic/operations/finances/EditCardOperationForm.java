package com.rssl.phizic.operations.finances;

import java.math.BigDecimal;

/**
 * @author Erkin
 * @ created 18.08.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Бин с атрибутами карточной операции, которые приходят от пользователя после редактирования
 */
public class EditCardOperationForm
{
	private String description;

	private Long categoryId;

	private BigDecimal amount;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * @return описание карт-операции, которое написал пользователь
	 */
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return ID категории операции, которую выбрал ей пользователь
	 */
	public Long getCategoryId()
	{
		return categoryId;
	}

	public void setCategoryId(Long categoryId)
	{
		this.categoryId = categoryId;
	}

	/**
	 * @return Сумма, которую ввёл пользователь (в валюте карты операции)
	 */
	public BigDecimal getAmount()
	{
		return amount;
	}

	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}
}
