package com.rssl.phizic.operations.print;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.resources.external.DepositLink;
import com.rssl.phizic.gate.deposit.Deposit;
import com.rssl.phizic.gate.deposit.DepositInfo;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;

/**
 * User: Novikov_A
 * Date: 28.12.2006
 * Time: 19:24:51
 */
public class DepositInfoPrintOperation extends OperationBase implements ViewEntityOperation<Deposit>
{
	private Deposit deposit;
	private DepositInfo depositInfo;
	private Person owner;

	public void initialize(Long depositId) throws BusinessLogicException, BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		DepositLink depositLink = personData.getDeposit(depositId);
		if (depositLink == null){
			throw new BusinessLogicException("¬клад не найден id="+depositId);
		}
		deposit = depositLink.getDeposit();
		depositInfo = depositLink.getDepositInfo();
		owner = personData.getPerson();
	}
	public Deposit getEntity() throws BusinessException
	{
		return deposit;
	}

	public DepositInfo getDepositInfo()
	{
		return depositInfo;
	}

	public Person getOwner()
	{
		return owner;
	}
}
