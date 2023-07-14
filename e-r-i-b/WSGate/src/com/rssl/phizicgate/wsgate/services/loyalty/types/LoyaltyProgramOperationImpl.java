package com.rssl.phizicgate.wsgate.services.loyalty.types;

import com.rssl.phizic.gate.loyalty.LoyaltyProgramOperation;
import com.rssl.phizic.gate.loyalty.LoyaltyOperationKind;
import com.rssl.phizic.common.types.Money;

import java.util.Calendar;
import java.math.BigDecimal;

/**
 * @author gladishev
 * @ created 02.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoyaltyProgramOperationImpl implements LoyaltyProgramOperation
{
	private Calendar operationDate;
	private LoyaltyOperationKind operationKind;
	private BigDecimal operationalBalance;
	private Money moneyOperationalBalance;
	private String operationInfo;

	public Calendar getOperationDate()
	{
		return operationDate;
	}

	public void setOperationDate(Calendar operationDate)
	{
		this.operationDate = operationDate;
	}

	public LoyaltyOperationKind getOperationKind()
	{
		return operationKind;
	}

	public void setOperationKind(LoyaltyOperationKind operationKind)
	{
		this.operationKind = operationKind;
	}

	public void setOperationKind(String operationKind)
	{
		if(operationKind == null || operationKind.trim().length() == 0)
			return;

		this.operationKind = Enum.valueOf(LoyaltyOperationKind.class, operationKind);
	}

	public BigDecimal getOperationalBalance()
	{
		return operationalBalance;
	}

	public void setOperationalBalance(BigDecimal operationalBalance)
	{
		this.operationalBalance = operationalBalance;
	}

	public Money getMoneyOperationalBalance()
	{
		return moneyOperationalBalance;
	}

	public void setMoneyOperationalBalance(Money moneyOperationalBalance)
	{
		this.moneyOperationalBalance = moneyOperationalBalance;
	}

	public String getOperationInfo()
	{
		return operationInfo;
	}

	public void setOperationInfo(String operationInfo)
	{
		this.operationInfo = operationInfo;
	}
}
