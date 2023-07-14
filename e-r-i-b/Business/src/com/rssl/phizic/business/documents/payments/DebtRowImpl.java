package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.gate.payments.systems.recipients.DebtRow;
import com.rssl.phizic.common.types.Money;

/**
 * @author krenev
 * @ created 06.05.2010
 * @ $Author$
 * @ $Revision$
 */
public class DebtRowImpl implements DebtRow
{
	private String code;
	private String description;
	private Money debt;
	private Money fine;
	private Money commission;

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Money getDebt()
	{
		return debt;
	}

	public void setDebt(Money debt)
	{
		this.debt = debt;
	}

	public Money getFine()
	{
		return fine;
	}

	public void setFine(Money fine)
	{
		this.fine = fine;
	}

	public Money getCommission()
	{
		return commission;
	}

	public void setCommission(Money commission)
	{
		this.commission = commission;
	}
}
