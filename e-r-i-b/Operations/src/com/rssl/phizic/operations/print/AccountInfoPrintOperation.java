package com.rssl.phizic.operations.print;

import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.bankroll.Account;

/**
 * User: Novikov_A
 * Date: 27.12.2006
 * Time: 19:06:42
 */
public class AccountInfoPrintOperation extends OperationBase implements ViewEntityOperation<Account>
{
	private Account account;
	private Person  owner;

	public void initialize(Long accountId) throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		AccountLink accountLink = personData.getAccount(accountId);
		if (accountLink == null){
			throw new BusinessLogicException("¬клад не найден id="+accountId);
		}
		account = accountLink.getAccount();
		owner = personData.getPerson();
	}

	public Account getEntity() throws BusinessException
	{
		return account;
	}

	public Person getOwner()
	{
		return owner;
	}
}
