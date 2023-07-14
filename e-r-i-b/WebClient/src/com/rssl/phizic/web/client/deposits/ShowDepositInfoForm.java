package com.rssl.phizic.web.client.deposits;

import org.apache.struts.action.ActionForm;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositInfo;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Set;

/**
 * Created by IntelliJ IDEA. User: Novikov_A Date: 28.12.2006 Time: 18:50:55 To change this template use File
 * | Settings | File Templates.
 */
public class ShowDepositInfoForm extends EditFormBase
{
	private Deposit deposit;
	private DepositInfo depositInfo;

	public void setDeposit(Deposit deposit)
	{
		this.deposit = deposit;
	}

	public Deposit getDeposit()
	{
		return this.deposit;
	}

	public DepositInfo getDepositInfo()
	{
		return depositInfo;
	}

	public void setDepositInfo(DepositInfo depositInfo)
	{
		this.depositInfo = depositInfo;
	}

	public Set getAccountsSet()
    {
        return depositInfo.getFinalAccounts().entrySet();
    }
}
