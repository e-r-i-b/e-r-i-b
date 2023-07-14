package com.rssl.phizic.business.finances;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;

import java.io.Serializable;

/**
 * @author komarov
 * @ created 30.04.2013 
 * @ $Author$
 * @ $Revision$
 * ������ ��� ��������� ���
 */

public class Budget implements Serializable
{
	private Login login;
	private Long categoryId;
	private Double budget;

	/**
	 * @return ����� �������
	 */
	public Login getLogin()
	{
		return login;
	}

	/**
	 * @param login ����� �������
	 */
	public void setLogin(Login login)
	{
		this.login = login;
	}

	/**
	 * @return ������������� ���������
	 */
	public Long getCategoryId()
	{
		return categoryId;
	}

	/**
	 * @param categoryId ������������� ���������
	 */
	public void setCategoryId(Long categoryId)
	{
		this.categoryId = categoryId;
	}

	/**
	 * @return ����� �������
	 */
	public Double getBudget()
	{
		return budget;
	}

	/**
	 * @param budget ����� �������
	 */
	public void setBudget(Double budget)
	{
		this.budget = budget;
	}
}
