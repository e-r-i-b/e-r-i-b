package com.rssl.phizic.web.gate.services.types;

import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.depo.DepoAccountState;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;

/**
 @author Pankin
 @ created 16.09.2010
 @ $Author$
 @ $Revision$
 */
public class DepoAccountImpl implements DepoAccount
{
	private String id;
	private DepoAccountState state;
	private String accountNumber;
	private String agreementNumber;
	private Calendar agreementDate;
	private Money debt;
	private boolean isOperationAllowed;
	private Office office;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public DepoAccountState getState()
	{
		return state;
	}

	public void setState(DepoAccountState state)
	{
		this.state = state;
	}

	public void setState(String state)
	{
		if(state == null || state.trim().length() == 0)
			return;
		this.state = Enum.valueOf(DepoAccountState.class, state);
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}

	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}

	public Calendar getAgreementDate()
	{
		return agreementDate;
	}

	public void setAgreementDate(Calendar agreementDate)
	{
		this.agreementDate = agreementDate;
	}

	public Money getDebt()
	{
		return debt;
	}

	public void setDebt(Money debt)
	{
		this.debt = debt;
	}

	public boolean getIsOperationAllowed()
	{
		return isOperationAllowed;
	}

	public void setOperationAllowed(boolean operationAllowed)
	{
		isOperationAllowed = operationAllowed;
	}

	public Office getOffice()
	{
		return office;
	}

	public void setOffice(Office office)
	{
		this.office = office;
	}
}
