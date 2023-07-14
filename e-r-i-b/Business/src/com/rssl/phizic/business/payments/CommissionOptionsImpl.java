package com.rssl.phizic.business.payments;

import com.rssl.phizic.gate.documents.CommissionTarget;
import com.rssl.phizic.gate.documents.CommissionOptions;

/**
 * User: novikov_a
 * Способ удержания комиссии
 * Date: 17.09.2009
 * Time: 12:55:17
 */
public class CommissionOptionsImpl implements CommissionOptions
{
	private CommissionTarget target;
	private String           account;

	public CommissionOptionsImpl()
	{

	}

	public CommissionOptionsImpl(CommissionTarget target, String account)
    {
		this.target = target;
	    this.account = account;
    }

	/**
    * Откуда удерживать комиссию
    */
	public CommissionTarget getTarget()
    {
		return this.target;
    }

	public void setTarget(CommissionTarget target)
    {
		this.target = target;
    }

   /**
    * Счет с которого удерживать комиссию. Обязателен для заполнения в случае getTarget == other.
    * В остальных случаях значение свойства игнорируется.
    */
	public String getAccount()
    {
		return this.account;
    }

	public void setAccount(String account)
    {
		this.account = account;
    }
}
