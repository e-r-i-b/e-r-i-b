package com.rssl.phizic.web.loans.claims;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.math.BigDecimal;

/**
 * Форма просмотра и редактирования настроек заявки на кредит
 * @author Rtischeva
 * @ created 06.02.15
 * @ $Author$
 * @ $Revision$
 */
public class LoanClaimSettingsEditForm extends ActionFormBase
{
	private boolean callAvailable;
	private boolean needConfirmDebitOperationERKC;
	private BigDecimal minRUBSumDebitOperationERKC;
	private BigDecimal minUSDSumDebitOperationERKC;
	private BigDecimal minEURSumDebitOperationERKC;
	private boolean lockOperationDebit;
	private int periodLockedOperationDebit;
	private int maxRUBSumUnlockRestriction;
	private int maxUSDSumUnlockRestriction;
	private int maxEURSumUnlockRestriction;

	public boolean isCallAvailable()
	{
		return callAvailable;
	}

	public void setCallAvailable(boolean callAvailable)
	{
		this.callAvailable = callAvailable;
	}

	public boolean isNeedConfirmDebitOperationERKC()
	{
		return needConfirmDebitOperationERKC;
	}

	public void setNeedConfirmDebitOperationERKC(boolean needConfirmDebitOperationERKC)
	{
		this.needConfirmDebitOperationERKC = needConfirmDebitOperationERKC;
	}

	public BigDecimal getMinRUBSumDebitOperationERKC()
	{
		return minRUBSumDebitOperationERKC;
	}

	public void setMinRUBSumDebitOperationERKC(BigDecimal minRUBSumDebitOperationERKC)
	{
		this.minRUBSumDebitOperationERKC = minRUBSumDebitOperationERKC;
	}

	public BigDecimal getMinUSDSumDebitOperationERKC()
	{
		return minUSDSumDebitOperationERKC;
	}

	public void setMinUSDSumDebitOperationERKC(BigDecimal minUSDSumDebitOperationERKC)
	{
		this.minUSDSumDebitOperationERKC = minUSDSumDebitOperationERKC;
	}

	public BigDecimal getMinEURSumDebitOperationERKC()
	{
		return minEURSumDebitOperationERKC;
	}

	public void setMinEURSumDebitOperationERKC(BigDecimal minEURSumDebitOperationERKC)
	{
		this.minEURSumDebitOperationERKC = minEURSumDebitOperationERKC;
	}

	public boolean isLockOperationDebit()
	{
		return lockOperationDebit;
	}

	public void setLockOperationDebit(boolean lockOperationDebit)
	{
		this.lockOperationDebit = lockOperationDebit;
	}

	public int getPeriodLockedOperationDebit()
	{
		return periodLockedOperationDebit;
	}

	public void setPeriodLockedOperationDebit(int periodLockedOperationDebit)
	{
		this.periodLockedOperationDebit = periodLockedOperationDebit;
	}

	public int getMaxRUBSumUnlockRestriction()
	{
		return maxRUBSumUnlockRestriction;
	}

	public void setMaxRUBSumUnlockRestriction(int maxRUBSumUnlockRestriction)
	{
		this.maxRUBSumUnlockRestriction = maxRUBSumUnlockRestriction;
	}

	public int getMaxUSDSumUnlockRestriction()
	{
		return maxUSDSumUnlockRestriction;
	}

	public void setMaxUSDSumUnlockRestriction(int maxUSDSumUnlockRestriction)
	{
		this.maxUSDSumUnlockRestriction = maxUSDSumUnlockRestriction;
	}

	public int getMaxEURSumUnlockRestriction()
	{
		return maxEURSumUnlockRestriction;
	}

	public void setMaxEURSumUnlockRestriction(int maxEURSumUnlockRestriction)
	{
		this.maxEURSumUnlockRestriction = maxEURSumUnlockRestriction;
	}
}
