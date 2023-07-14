package com.rssl.phizic.operations.payment;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.BankDictionaryService;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.ext.sbrf.payments.forms.MemOrder;
import com.rssl.phizic.business.operations.Transactional;

import com.rssl.phizic.business.documents.*;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonDataProvider;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.UnknownAccountCurrencyException;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.dictionaries.Bank;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;

/**
 * @author Omeliyanchuk
 * @ created 31.07.2006
 * @ $Author$
 * @ $Revision$
 */
public class PrintMemOperation extends OperationBase
{
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static final BankDictionaryService     bankService = new BankDictionaryService();
	private static BankrollService    bankrollService    = GateSingleton.getFactory().service(BankrollService.class);

	private Long paymentId;
	public void setPayment(Long paymentId)
	{
		this.paymentId = paymentId;
	}

	public Long getPaymentId()
	{
		return paymentId;
	}

	@Transactional
	public MemOrder buildMemOrder( )throws BusinessException, UnknownAccountCurrencyException,
			GateException, BusinessLogicException
	{
		MemOrder order = new MemOrder();
		AbstractPaymentDocument payment = (AbstractPaymentDocument) getPaymentById(paymentId);

		if(payment != null)
        {   //загрузка платежа в форму
            order.setPayment(payment);
            //загрузка данных не входящих в PaymentBase
            if(payment instanceof RurPayment)
            {
                RurPayment rurPayment = (RurPayment)payment;
				String bic = rurPayment.getReceiverBIC();
				Bank bankInfo = getBankByBIC(bic);
				if(bankInfo != null)
					order.setReciverBank(bankInfo);
				else throw new BusinessLogicException("Не найден банк с указаным БИК");
				order.setreceiverINN( rurPayment.getReceiverINN() );
				order.setreceiverKPP( rurPayment.getReceiverKPP() );
			}

	        if(payment.getFormName().equals("InternalPayment") )
			{
				InternalTransfer transfer = (InternalTransfer) payment;
				//todo переделать, в послдеующих версиях. Тут привязка к конкретному платежу.
				Account account = getSBRFAccount( transfer.getReceiverAccount() );
				Office office = getSBRFAccountOffice(account);

				String payerBIC = office.getBIC();
				Bank bankInfo = getBankByBIC(payerBIC);
				order.setReciverBank(bankInfo);
				transfer.setReceiverName( transfer.getPayerName() );
				order.setPayment(payment);
			}

			Account account = getSBRFAccount(payment.getChargeOffAccount());
	        Office office = getSBRFAccountOffice(account);

			String payerBIC = office.getBIC();
			Bank bankInfo = getBankByBIC(payerBIC);

			order.setPayerBank(bankInfo);

			//загрузка персональных данных в форму
            PersonData data = PersonContext.getPersonDataProvider().getPersonData();
            order.setPerson( data.getPerson() );
		}
		else throw new BusinessLogicException("Платеж не найден");

		return order;
	}

	private Account getSBRFAccount(String accountId) throws UnknownAccountCurrencyException, GateException
	{
		try
		{
			return GroupResultHelper.getOneResult(bankrollService.getAccount(accountId));
		}
		catch(IKFLException ex)
		{
			throw new GateException(ex);
		}
	}

	private Office getSBRFAccountOffice(Account account) throws GateException
	{
		return account.getOffice();
	}

	private BusinessDocument getPaymentById(final Long paymentId) throws BusinessException,BusinessLogicException
	{
		BusinessDocumentBase payment = (BusinessDocumentBase) businessDocumentService.findById(paymentId);
		if(payment==null) throw new BusinessLogicException("Платеж не найден");
		PersonDataProvider provider = PersonContext.getPersonDataProvider();
		ActivePerson person = provider.getPersonData().getPerson();
		Long loginId = person.getLogin().getId();
		BusinessDocumentOwner documentOwner = payment.getOwner();
		if (documentOwner.isGuest())
			throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
		if(!documentOwner.getLogin().getId().equals(loginId))return null;
		else return payment;
	}

	private Bank getBankByBIC(String BIC) throws BusinessException
	{
		return bankService.findByBIC(BIC);
	}

}
