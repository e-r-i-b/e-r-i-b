package com.rssl.phizic.business.finances;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;

import java.io.Serializable;

/**
 * @author komarov
 * @ created 30.04.2013 
 * @ $Author$
 * @ $Revision$
 * Бюджет для категории АЛФ
 */

public class Budget implements Serializable
{
	private Login login;
	private Long categoryId;
	private Double budget;

	/**
	 * @return логин клиента
	 */
	public Login getLogin()
	{
		return login;
	}

	/**
	 * @param login логин клиента
	 */
	public void setLogin(Login login)
	{
		this.login = login;
	}

	/**
	 * @return идентификатор категории
	 */
	public Long getCategoryId()
	{
		return categoryId;
	}

	/**
	 * @param categoryId идентификатор категории
	 */
	public void setCategoryId(Long categoryId)
	{
		this.categoryId = categoryId;
	}

	/**
	 * @return сумма бюджета
	 */
	public Double getBudget()
	{
		return budget;
	}

	/**
	 * @param budget сумма бюджета
	 */
	public void setBudget(Double budget)
	{
		this.budget = budget;
	}
}
