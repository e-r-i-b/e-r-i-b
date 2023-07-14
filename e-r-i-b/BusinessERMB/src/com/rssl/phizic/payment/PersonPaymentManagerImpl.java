package com.rssl.phizic.payment;

import com.rssl.phizic.module.Module;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonManagerBase;

/**
 * @author Erkin
 * @ created 22.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class PersonPaymentManagerImpl extends PersonManagerBase implements PersonPaymentManager
{
	public PersonPaymentManagerImpl(Module module, Person person)
	{
		super(module, person);
	}

	public InternalTransferTask createInternalTransferTask()
	{
		InternalTransferTaskImpl task = new InternalTransferTaskImpl();
		task.setModule(getModule());
		task.setPerson(getPerson());
		return task;
	}

	public CardTransferTask createCardTransferTask()
	{
		CardTransferTaskImpl task = new CardTransferTaskImpl();
		task.setModule(getModule());
		task.setPerson(getPerson());
		return task;
	}

	public PhoneTransferTask createPhoneTransferTask()
	{
		PhoneTransferTaskImpl task = new PhoneTransferTaskImpl();
		task.setModule(getModule());
		task.setPerson(getPerson());
		return task;
	}

	public LossPassbookTask createLossPassbookTask()
	{
		LossPassbookTaskImpl task = new LossPassbookTaskImpl();
		task.setModule(getModule());
		task.setPerson(getPerson());
		return task;
	}

	public BlockingCardTask createBlockingCardTask()
	{
		BlockingCardTaskImpl task = new BlockingCardTaskImpl();
		task.setModule(getModule());
		task.setPerson(getPerson());
		return task;
	}

	public ServicePaymentTask createServicePaymentTask()
	{
		ServicePaymentTaskImpl task = new ServicePaymentTaskImpl();
		task.setModule(getModule());
		task.setPerson(getPerson());
		return task;
	}

	public RechargePhoneTask createRechargePhoneTask()
	{
		RechargePhoneTaskImpl task = new RechargePhoneTaskImpl();
		task.setModule(getModule());
		task.setPerson(getPerson());
		return task;
	}

	public TemplatePaymentTask createTemplatePaymentTask()
	{
		TemplatePaymentTaskImpl task = new TemplatePaymentTaskImpl();
		task.setModule(getModule());
		task.setPerson(getPerson());
		return task;
	}

	public LoanPaymentTask createLoanPaymentTask()
	{
		LoanPaymentTaskImpl task = new LoanPaymentTaskImpl();
		task.setModule(getModule());
		task.setPerson(getPerson());
		return task;
	}

	public LoyaltyRegistrationPaymentTask createLoyalRegistrationPaymentTask()
	{
		LoyaltyRegistrationPaymentTaskImpl task = new LoyaltyRegistrationPaymentTaskImpl();
		task.setModule(getModule());
		task.setPerson(getPerson());
		return task;
	}

	public CreateAutoPaymentTask createAutoPaymentTask()
	{
		CreateAutoPaymentTaskImpl task = new CreateAutoPaymentTaskImpl();
		task.setModule(getModule());
		task.setPerson(getPerson());
		return task;
	}

	public RefuseAutoPaymentTask createRefuseAutoPaymentTask()
	{
		RefuseAutoPaymentTaskImpl task = new RefuseAutoPaymentTaskImpl();
		task.setModule(getModule());
		task.setPerson(getPerson());
		return task;
	}

	public CardIssueTask createCardIssueTask()
	{
		CardIssueTaskImpl task = new CardIssueTaskImpl();
		task.setModule(getModule());
		task.setPerson(getPerson());
		return task;
	}
}
