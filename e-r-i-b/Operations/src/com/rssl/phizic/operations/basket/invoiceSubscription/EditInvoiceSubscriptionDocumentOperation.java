package com.rssl.phizic.operations.basket.invoiceSubscription;

import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.basket.accountingEntity.AccountingEntity;
import com.rssl.phizic.business.documents.payments.CreateInvoiceSubscriptionPayment;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.operations.payment.CreateFormPaymentOperation;

/**
 * Операция редактирования заявки на создания подписки на получение инвойсов
 * @author niculichev
 * @ created 15.06.14
 * @ $Author$
 * @ $Revision$
 */
public class EditInvoiceSubscriptionDocumentOperation extends CreateFormPaymentOperation
{
	private static final SimpleService simpleService = new SimpleService();
	private AccountingEntity accountingEntity;

	public void initialize(DocumentSource source, FieldValuesSource additionalFieldValuesSource) throws BusinessException, BusinessLogicException
	{
		CreateInvoiceSubscriptionPayment payment = (CreateInvoiceSubscriptionPayment) source.getDocument();

		Long accountingEntityId = payment.getAccountingEntityId();
		if(accountingEntityId != null && accountingEntityId != 0L)
		{
			AccountingEntity temp = simpleService.findById(AccountingEntity.class, accountingEntityId);
			if(temp == null)
				throw new BusinessException("Не найден объект учета с id = " + accountingEntityId);

			this.accountingEntity = temp;
		}

		super.initialize(source, additionalFieldValuesSource);
	}

	public AccountingEntity getAccountingEntity()
	{
		return accountingEntity;
	}
}
