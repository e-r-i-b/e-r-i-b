package com.rssl.phizic.business.depo;

import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.depo.DepoAccountState;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.MockObject;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 19.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class MockDepoAccount implements DepoAccount, MockObject
{
	private String id;
	private DepoAccountState state;
	private String accountNumber;
	private String agreementNumber;
	private Calendar agreementDate;
	private Money debt;
	private boolean isOperationAllowed;
	private Office office;

	public MockDepoAccount()
	{

	}

	public MockDepoAccount(String id, DepoAccountState state, String accountNumber, String agreementNumber, Calendar agreementDate, Money debt, boolean operationAllowed, Office office)
	{
		this.id = id;
		this.state = state;
		this.accountNumber = accountNumber;
		this.agreementNumber = agreementNumber;
		this.agreementDate = agreementDate;
		this.debt = debt;
		isOperationAllowed = operationAllowed;
		this.office = office;
	}

	public String getId()
	{
		return id;
	}

	public DepoAccountState getState()
	{
		return state;
	}

	public String getAccountNumber()
	{
		return accountNumber;
	}

	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	public Calendar getAgreementDate()
	{
		return agreementDate;
	}

	public Money getDebt()
	{
		return debt;
	}

	public boolean getIsOperationAllowed()
	{
		return isOperationAllowed;
	}

	public Office getOffice()
	{
		return office;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setAccountNumber(String accountNumber)
	{
		this.accountNumber = accountNumber;
	}
}
