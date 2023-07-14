package com.rssl.phizic.operations.basket.invoice;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.basket.BasketHelper;
import com.rssl.phizic.business.basket.invoice.Invoice;
import com.rssl.phizic.business.basket.invoice.InvoiceService;
import com.rssl.phizic.business.basket.invoiceSubscription.InvoiceSubscriptionService;
import com.rssl.phizic.business.basket.invoiceSubscription.LightInvoiceSubscription;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.source.NewInvoicePaymentSource;
import com.rssl.phizic.business.documents.strategies.DocumentAction;
import com.rssl.phizic.business.documents.strategies.DocumentLimitManager;
import com.rssl.phizic.business.documents.strategies.ProcessDocumentStrategy;
import com.rssl.phizic.business.documents.strategies.limits.*;
import com.rssl.phizic.business.limits.ImpossiblePerformOperationException;
import com.rssl.phizic.business.limits.RequireAdditionConfirmLimitException;
import com.rssl.phizic.business.limits.RestrictionType;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.business.operations.restrictions.OwnInvoiceRestriction;
import com.rssl.phizic.business.payments.BusinessTimeOutException;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.basket.InvoiceStatus;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.operations.payment.EditDocumentOperationBase;
import com.rssl.phizic.utils.MoneyHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.rssl.phizic.business.documents.strategies.limits.Constants.TIME_OUT_ERROR_MESSAGE;

/**
 * @author osminin
 * @ created 10.06.14
 * @ $Author$
 * @ $Revision$
 *
 * ќпераци€ создани€ оплаты задолженности
 */
public class CreateInvoicePaymentOperation extends EditDocumentOperationBase
{
	private static InvoiceService invoiceService = new InvoiceService();
	private static InvoiceSubscriptionService invoiceSubscriptionService = new InvoiceSubscriptionService();
	private Invoice invoice;
	private ConfirmStrategyType confirmStrategyType;

	/**
	 * »нициализаци€ операции
	 * @param invoiceId идентификатор задолженности
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(Long invoiceId) throws BusinessException, BusinessLogicException
	{
		if (invoiceId == null)
		{
			throw new IllegalArgumentException("»дентификатор задолженности не может быть null.");
		}

		Pair<Invoice, ConfirmStrategyType> result = invoiceService.getInvoiceWithConfirmStrategyType(invoiceId);

		this.invoice = result.getFirst();
		this.confirmStrategyType = result.getSecond();

		if (invoice == null)
		{
			throw new BusinessException("«адолженность с id=" + invoiceId + " не найдена.");
		}
		if (!((OwnInvoiceRestriction) getRestriction()).accept(invoice))
		{
			throw new BusinessException("«адолженность с id=" + invoiceId + " не принадлежит клиенту.");
		}
		if (!availablePay(invoice.getState()))
		{
			throw new BusinessException("ќплатить можно только новую или отложенную задолженность.");
		}
		if (!BasketHelper.isInvoicePayAvailable(getSubscription(invoice.getInvoiceSubscriptionId())))
		{
			throw new BusinessException("ќплата задолженности по услуге, ожидающей удалени€, запрещена.");
		}

		initialize(new NewInvoicePaymentSource(invoice));
	}

	private LightInvoiceSubscription getSubscription(long invoiceSubscriptionId) throws BusinessException
	{
		LightInvoiceSubscription subscription = invoiceSubscriptionService.getLightSubscriptionById(invoiceSubscriptionId);
		if (subscription == null)
		{
			throw new BusinessException("”слуга по идентификатору " + invoiceSubscriptionId + " не найдена.");
		}
		return subscription;
	}

	public boolean isConfirmSubscription() throws BusinessException
	{
		return confirmStrategyType != null;
	}

	public Invoice getInvoice()
	{
		return invoice;
	}

	/**
	 * ¬ыполнить оплату задолженности
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	@Transactional
	public void execute() throws BusinessException, BusinessLogicException
	{
		Calendar operationDate = Calendar.getInstance();

		try
		{
			createDocumentLimitManager(document, document.getOperationUID(), operationDate).processLimits(new DocumentAction()
			{
				public void action(BusinessDocument document) throws BusinessLogicException, BusinessException
				{
					try
					{
						executor.fireEvent(new ObjectEvent(DocumentEvent.CONFIRM, getSourceEvent()));
					}
					catch (BusinessTimeOutException e)
					{
						log.error(String.format(Constants.TIME_OUT_ERROR_MESSAGE, document.getId()), e);
						DocumentHelper.fireDounknowEvent(executor, ObjectEvent.CLIENT_EVENT_TYPE, e);
					}
				}
			});
		}
		catch (ImpossiblePerformOperationException e)
		{
			RestrictionType restrictionType = e.getLimit().getRestrictionType();
			String message = message("paymentsBundle", "payment.limit.impossible.perform.confirm." + restrictionType.name());
			// если ограничение суммы, то нужно выставить суммы
			if(restrictionType == RestrictionType.AMOUNT_IN_DAY)
				message = String.format(message, MoneyHelper.formatAmount(e.getAmount()), MoneyHelper.formatAmount(e.getLimit().getAmount()));

			throw new BusinessLogicException(message, e);
		}
		catch (RequireAdditionConfirmLimitException e)
		{
			if(e.getRestrictionType() != null)
			{
				//устанавливаем в платеж причину дополнительного подтверждени€
				document.setReasonForAdditionalConfirm(e.getRestrictionType().name());
			}
			// требуетс€ доп подтверждение
			executor.fireEvent(new ObjectEvent(DocumentEvent.DOWAITCONFIRM, ObjectEvent.CLIENT_EVENT_TYPE));
			String message = message("paymentsBundle", "payment.limit.exceeded.view." + e.getRestrictionType().name());
			getMessageCollector().addMessage(message);
		}
		catch (BusinessTimeOutException e)
		{
			log.error(String.format(TIME_OUT_ERROR_MESSAGE, document.getId()), e);
			DocumentHelper.fireDounknowEvent(executor, ObjectEvent.CLIENT_EVENT_TYPE, e);
		}

		document.setOperationDate(operationDate);
		target.save(document);
	}

	private DocumentLimitManager createDocumentLimitManager(BusinessDocument document, String operationUID, Calendar operationDate) throws BusinessException, BusinessLogicException
	{
		List<Class<? extends ProcessDocumentStrategy>> strategies = new ArrayList<Class<? extends ProcessDocumentStrategy>>();
		strategies.add(ObstructionDocumentLimitStrategy.class);

		strategies.add(RecipientByPhoneDocumentLimitStrategy.class);
		strategies.add(RecipientByCardDocumentLimitStrategy.class);

		strategies.add(IMSIDocumentLimitStrategy.class);
		strategies.add(GroupRiskDocumentLimitStrategy.class);
		strategies.add(MobileLightPlusLimitStrategy.class);

		return DocumentLimitManager.createProcessLimitManager(document, operationUID, operationDate, getPerson(), strategies);
	}

	private boolean availablePay(InvoiceStatus status)
	{
		return InvoiceStatus.NEW == status || InvoiceStatus.DELAYED == status;
	}
}
