package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.util.MoneyUtil;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.depo.DepoAccountState;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 * User: Balovtsev
 * Date: 23.10.2012
 * Time: 12:04:18
 */
public class StoredDepoAccount extends AbstractStoredResource<DepoAccount, Void> implements DepoAccount
{
	private DepoAccountState    state;
	private String              agreementNumber;
	private Calendar            agreementDate;
	private Money               debt;
	private Boolean             isOperationAllowed;

	private DepoAccountLink     depoAccountLink;

	public DepoAccountLink getDepoAccountLink()
	{
		return depoAccountLink;
	}

	public void setDepoAccountLink(DepoAccountLink depoAccountLink)
	{
		this.depoAccountLink = depoAccountLink;
	}

	public void setState(DepoAccountState state)
	{
		this.state = state;
	}

	public DepoAccountState getState()
	{
		return state;
	}

	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}

	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	public void setAgreementDate(Calendar agreementDate)
	{
		this.agreementDate = agreementDate;
	}

	public Calendar getAgreementDate()
	{
		return agreementDate;
	}

	public void setDebt(Money debt)
	{
		this.debt = debt;
	}

	public Money getDebt()
	{
		return debt;
	}

	public void setIsOperationAllowed(Boolean operationAllowed)
	{
		isOperationAllowed = operationAllowed;
	}

	public boolean getIsOperationAllowed()
	{
		return isOperationAllowed;
	}

	public String getId()
	{
		return getResourceLink().getExternalId();
	}

	public String getAccountNumber()
	{
		return ((DepoAccountLink) getResourceLink()).getAccountNumber();
	}

	public void update(DepoAccount depoAccount)
	{
		this.state              = depoAccount.getState();
		this.agreementNumber    = depoAccount.getAgreementNumber();
		this.agreementDate      = depoAccount.getAgreementDate();
		this.debt               = depoAccount.getDebt();
		this.isOperationAllowed = depoAccount.getIsOperationAllowed();

		updateOffice(depoAccount.getOffice());
		setEntityUpdateTime( Calendar.getInstance() );
	}

	public boolean needUpdate(DepoAccount depoAccount)
	{
		if (state != depoAccount.getState())
			return true;

		if (!StringHelper.equalsNullIgnore(agreementNumber, depoAccount.getAgreementNumber()))
			return true;

		if (DateHelper.nullSafeCompare(agreementDate, depoAccount.getAgreementDate()) != 0)
			return true;

		if (!MoneyUtil.equalsNullIgnore(debt, depoAccount.getDebt()))
			return true;

		if (isOperationAllowed != null && !isOperationAllowed.equals(depoAccount.getIsOperationAllowed()))
			return true;

		return false;
	}
}
