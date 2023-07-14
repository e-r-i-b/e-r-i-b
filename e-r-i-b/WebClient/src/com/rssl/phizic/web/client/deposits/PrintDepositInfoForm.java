package com.rssl.phizic.web.client.deposits;

import org.apache.struts.action.ActionForm;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositInfo;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.Set;

/**
 * User: Novikov_A
 * Date: 28.12.2006
 * Time: 18:51:38
 */
public class PrintDepositInfoForm extends EditFormBase
{
	private String depositId;
	private Deposit deposit;
	private String  fullNameClient;
	private DepositInfo depositInfo;

	public DepositInfo getDepositInfo()
	{
		return depositInfo;
	}

	public void setDepositInfo(DepositInfo depositInfo)
	{
		this.depositInfo = depositInfo;
	}

	public void setDeposit(Deposit deposit)
	{
		this.deposit = deposit;
	}

	public Deposit getDeposit()
	{
		return this.deposit;
	}
	public String getFullNameClient()
	{
		return fullNameClient;
	}

	public void setFullNameClient(String fullNameClient)
	{
		this.fullNameClient = fullNameClient;
	}

	public Set getAccountsSet()
    {
        return depositInfo.getFinalAccounts().entrySet();
    }

	public void setDepositId(String depositId)
	{
		this.depositId = depositId;
	}

	public String getDepositId()
	{
		return depositId;
	}
}
