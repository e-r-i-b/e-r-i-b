package com.rssl.phizic.operations.loanclaim;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.operations.OperationBase;

import java.math.BigDecimal;

/**
 * Операция просмотра и редактирования настроек заявки на кредит
 * @author Rtischeva
 * @ created 06.02.15
 * @ $Author$
 * @ $Revision$
 */
public class LoanClaimSettingsEditOperation extends OperationBase
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

	public void initialize()
	{
		LoanClaimConfig config = ConfigFactory.getConfig(LoanClaimConfig.class);
		callAvailable = config.isCallAvailable();
		needConfirmDebitOperationERKC = config.isNeedConfirmDebitOperationERKC();
		minRUBSumDebitOperationERKC = config.getMinRUBSumDebitOperationERKC();
		minUSDSumDebitOperationERKC = config.getMinUSDSumDebitOperationERKC();
		minEURSumDebitOperationERKC = config.getMinEURSumDebitOperationERKC();
		lockOperationDebit = config.isLockOperationDebit();
		periodLockedOperationDebit = config.getPeriodLockedOperationDebit();
		maxRUBSumUnlockRestriction = config.getMaxRUBSumUnlockRestriction();
		maxUSDSumUnlockRestriction = config.getMaxUSDSumUnlockRestriction();
		maxEURSumUnlockRestriction = config.getMaxEURSumUnlockRestriction();
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		LoanClaimConfig config = ConfigFactory.getConfig(LoanClaimConfig.class);
		config.setCallAvailable(callAvailable);
		config.setNeedConfirmDebitOperationERKC(needConfirmDebitOperationERKC);
		config.setMinRUBSumDebitOperationERKC(minRUBSumDebitOperationERKC);
		config.setMinUSDSumDebitOperationERKC(minUSDSumDebitOperationERKC);
		config.setMinEURSumDebitOperationERKC(minEURSumDebitOperationERKC);
		config.setLockOperationDebit(lockOperationDebit);
		config.setPeriodLockedOperationDebit(periodLockedOperationDebit);
		config.setMaxRUBSumUnlockRestriction(maxRUBSumUnlockRestriction);
		config.setMaxUSDSumUnlockRestriction(maxUSDSumUnlockRestriction);
		config.setMaxEURSumUnlockRestriction(getMaxEURSumUnlockRestriction());
		config.save();
	}

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
