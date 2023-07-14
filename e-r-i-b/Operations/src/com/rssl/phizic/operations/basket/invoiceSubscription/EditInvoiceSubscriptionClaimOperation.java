package com.rssl.phizic.operations.basket.invoiceSubscription;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.accountingEntity.AccountingEntity;
import com.rssl.phizic.business.basket.accountingEntity.AccountingEntityService;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscription;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.documents.payments.EditInvoiceSubscriptionClaim;
import com.rssl.phizic.business.documents.payments.source.NewInvoiceSubscriptionClaimSource;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.operations.restrictions.InvoiceSubscriptionRestriction;
import com.rssl.phizic.operations.payment.EditDocumentOperationBase;
import com.rssl.phizic.utils.DateHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author saharnova
 * @ created 10.06.15
 * @ $Author$
 * @ $Revision$
 */

public class EditInvoiceSubscriptionClaimOperation extends EditDocumentOperationBase
{
	private static InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();
	private static AccountingEntityService accountingEntityService = new AccountingEntityService();

	private InvoiceSubscription invoiceSubscription;

	/**
	 * Инициализация операции
	 * @param id идентификатор подписки
	 * @throws com.rssl.phizic.business.BusinessException
	 * @throws com.rssl.phizic.business.BusinessLogicException
	 */
	public void initialize(Long id) throws BusinessException, BusinessLogicException
	{
		if (id == null)
		{
			throw new IllegalArgumentException("Идентификатор подписки не может быть null");
		}

		invoiceSubscription = invoiceSubscriptionService.findById(id);

		if (invoiceSubscription == null)
		{
			throw new BusinessException("Подписка с id " + id + " не найдена.");
		}

		if(!((InvoiceSubscriptionRestriction) getRestriction()).accept(invoiceSubscription))
		{
			throw new BusinessException("Подписка с id = " + id + " не принадлежит клиенту");
		}
		NewInvoiceSubscriptionClaimSource source = new NewInvoiceSubscriptionClaimSource(invoiceSubscription, FormConstants.EDIT_INVOICE_SUBSCRIPTION_CLAIM);
		Map<String, String> fieldsMap = new HashMap<String, String>();
		fieldsMap.put("subscriptionId", invoiceSubscription.getId().toString());
		fieldsMap.put("oldSubscriptionName", invoiceSubscription.getName());
		fieldsMap.put("oldAccountingEntityId", invoiceSubscription.getAccountingEntityId() != null ? invoiceSubscription.getAccountingEntityId().toString() : "");
		fieldsMap.put("oldFromResource", invoiceSubscription.getChargeOffCard());
		fieldsMap.put("oldDayPay", DateHelper.formatDateToStringWithPoint(invoiceSubscription.getPayDate()));
		fieldsMap.put("oldEventType", invoiceSubscription.getExecutionEventType().name());

		FieldValuesSource additionalSource = new MapValuesSource(fieldsMap);
		initialize(source, additionalSource);
	}

	@Transactional
	public Long save() throws BusinessException, BusinessLogicException
	{
		EditInvoiceSubscriptionClaim claim = (EditInvoiceSubscriptionClaim) document;
		Long accountingEntityId = claim.getAccountingEntityId();
		Long oldAccountingEntityId = claim.getOldAccountingEntityId();
		boolean changeGroup = accountingEntityId == null && oldAccountingEntityId != null || accountingEntityId != null && !accountingEntityId.equals(oldAccountingEntityId);
		if (changeGroup)
		{
			if (accountingEntityId == null || accountingEntityId == 0L)
			{
				invoiceSubscription.setAccountingEntityId(null);
			}
			else
			{
				AccountingEntity accountingEntity = accountingEntityService.findById(accountingEntityId);
				if (accountingEntity == null)
				{
					throw new BusinessException("Не удалось найти указанную группу");
				}
				if (accountingEntity.getLoginId() != document.getOwner().getPerson().getLogin().getId())
				{
					throw new BusinessException("Указанная группа Вам не принадлежит");
				}
				invoiceSubscription.setAccountingEntityId(accountingEntity.getId());
			}
		}

		//если изменилась только группа учета, то заявку не сохраняем
		//если изменились другие параметры, то сохраняем заявку (карта, название, период, дата платежа)
		boolean needSaveClaim = false;
		if (!claim.getSubscriptionName().equals(claim.getOldSubscriptionName()))
		{
			invoiceSubscription.setName(claim.getSubscriptionName());
			needSaveClaim = true;
		}
		if (!claim.getChargeOffCard().equals(claim.getOldFromResource()))
		{
			invoiceSubscription.setChargeOffCard(claim.getChargeOffCard());
			needSaveClaim = true;
		}
		if (!claim.getExecutionEventType().equals(claim.getOldEventType()))
		{
			invoiceSubscription.setExecutionEventType(claim.getExecutionEventType());
			needSaveClaim = true;
		}
		if (!claim.getDayPay().equals(claim.getOldDayPay()))
		{
			invoiceSubscription.setPayDate(claim.getDayPay());
			needSaveClaim = true;
		}

		if (changeGroup || needSaveClaim)
		{
			invoiceSubscriptionService.addOrUpdate(invoiceSubscription);
		}
		if (needSaveClaim)
		{
			super.save();
		}
		return document.getId();
	}

	public InvoiceSubscription getEntity() throws BusinessException, BusinessLogicException
	{
		return invoiceSubscription;
	}
}

