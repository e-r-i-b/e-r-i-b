package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.*;
import com.rssl.phizic.business.documents.payments.*;
import com.rssl.phizic.business.resources.external.EditableExternalResourceLink;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.payments.RefuseLongOffer;
import com.rssl.phizic.gate.payments.autopayment.RefuseAutoPayment;
import com.rssl.phizic.gate.payments.basket.CloseInvoiceSubscription;
import com.rssl.phizic.gate.payments.basket.DelayInvoiceSubscription;
import com.rssl.phizic.gate.payments.basket.InvoiceAcceptPayment;
import com.rssl.phizic.gate.payments.basket.RecoveryInvoiceSubscription;
import com.rssl.phizic.notifications.BusinessNotificationException;
import com.rssl.phizic.notifications.impl.PaymentExecuteNotification;
import com.rssl.phizic.notifications.service.NotificationService;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 * Хендлер, выполняющий создание оповещений
 * @author basharin
 * @ created 11.04.14
 * @ $Author$
 * @ $Revision$
 */

public class CreateNotificationHandler extends BusinessDocumentHandlerBase
{
	private static final NotificationService notificationService = new NotificationService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		try
		{
			GateExecutableDocument gateDocument = (GateExecutableDocument) document;

			//если документ создан в смс-канале, не создаем оповещение
			CreationType creationType = gateDocument.getClientCreationChannel();
			if (creationType == CreationType.sms)
				return;

			if (!(document instanceof AbstractPaymentDocument)
					&& (!gateDocument.getFormName().equals("LossPassbookApplication"))
					&& !(gateDocument instanceof RefuseLongOffer)
					&& !(gateDocument instanceof LoyaltyProgramRegistrationClaim)
					&& !(gateDocument instanceof CardReportDeliveryClaim)
					&& !(gateDocument instanceof ShortLoanClaim)
					&& !(gateDocument instanceof LoanCardClaim)
					&& !(gateDocument instanceof ExtendedLoanClaim))
			{
				log.error("Ошибка при создание оповещения для платежа с идентификатором " + gateDocument.getId() + ": Ожидается наследник AbstractPaymentDocument, RefuseLongOffer или LossPassbookApplication.");
				return;
			}

			if (gateDocument.getOwner().isGuest())
				return;
			if (gateDocument.getFormName().equals("LossPassbookApplication"))
			{
				createNotification((LossPassbookApplication) document, stateMachineEvent);
			}
			else if (gateDocument instanceof CardReportDeliveryClaim)
			{
				CardReportDeliveryClaim claim = (CardReportDeliveryClaim) gateDocument;
				PaymentExecuteNotification notification = createNotificationBase(claim, stateMachineEvent);
				notification.setAccountNumber(claim.getCardNumberReportDelivery());
				notification.setNameOrType(claim.getEmailReportDelivery());
				notificationService.save(notification);
			}
			else if (gateDocument instanceof RefuseLongOffer)
			{
				createNotification((RefuseLongOfferClaim) document, stateMachineEvent);
			}
			else if (gateDocument instanceof RefuseAutoPayment || (document instanceof AbstractLongOfferDocument && ((AbstractLongOfferDocument) document).isLongOffer()))
			{
				createNotification((AbstractLongOfferDocument) document, stateMachineEvent);
			}
			else if (gateDocument instanceof DelayInvoiceSubscription || gateDocument instanceof CloseInvoiceSubscription || gateDocument instanceof RecoveryInvoiceSubscription)
			{
				createNotification((InvoiceSubscriptionOperationClaim) document, stateMachineEvent);
			}
			else if (gateDocument instanceof InvoiceAcceptPayment)
			{
				createNotification((InvoiceAcceptPayment) document, stateMachineEvent);
			}
			else if (gateDocument instanceof ShortLoanClaim)
			{
				createNotification((ShortLoanClaim) document, stateMachineEvent);
			}
			else if (gateDocument instanceof LoanCardClaim)
			{
				createNotification((LoanCardClaim) document, stateMachineEvent);
			}
			else if (gateDocument instanceof ExtendedLoanClaim)
			{
				createNotification((ExtendedLoanClaim) document, stateMachineEvent);
			}
			else
			{
				AbstractPaymentDocument abstr = (AbstractPaymentDocument) document;
				if (abstr.getType() != LoanProductClaim.class && abstr.getType() != VirtualCardClaim.class)
				{
					createNotification(abstr, stateMachineEvent);
				}
			}
		}
		catch (Exception e)
		{
			// При любых ислючениях пишем ошибку в лог
			// т.к. платеж успешно исполнился. и его надо отобразить клиенту.
			log.error("Ошибка при создание оповещения для платежа с идентификатором " + document.getId(), e);
		}
	}

	/**
	 * Создание оповещения об исполнении оплаты задолженности
	 * @param document платеж
	 * @param stateMachineEvent событие машины состояний
	 * @throws DocumentException
	 */
	private void createNotification(InvoiceAcceptPayment document, StateMachineEvent stateMachineEvent) throws DocumentException
	{
		try
		{
			InvoicePayment payment = (InvoicePayment) document;
			PaymentExecuteNotification notification = createNotificationBase(payment, stateMachineEvent);
			notification.setAccountNumber(payment.getChargeOffAccount());
			notification.setAccountResourceType(ResourceType.CARD.toString());
			notification.setNameOrType(payment.getFromAccountName());

			notificationService.save(notification);
		}
		catch (BusinessNotificationException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Создание оповещения для операции над подпиской
	 * @param payment платеж
	 * @param stateMachineEvent событие машины состояний
	 * @throws DocumentException
	 */
	private void createNotification(InvoiceSubscriptionOperationClaim payment, StateMachineEvent stateMachineEvent) throws DocumentException
	{
		try
		{
			PaymentExecuteNotification notification = createNotificationBase(payment, stateMachineEvent);
			notification.setAccountNumber(payment.getChargeOffAccount());
			notification.setAccountResourceType(ResourceType.CARD.toString());
			notification.setNameOrType(payment.getFromAccountName());

			notificationService.save(notification);
		}
		catch (BusinessNotificationException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Создание оповещения для быстрой заявки на кредит
	 * @param document - платеж
	 * @throws DocumentException
	 */
	private void createNotification(ShortLoanClaim document, StateMachineEvent stateMachineEvent) throws DocumentException
	{
		try
		{
			Money amount = document.getLoanAmount();
			String curr = amount.getCurrency().getCode();
			if (StringHelper.isEmpty(curr))
				throw new DocumentException("Не удалось получить код валюты для документа с id:" + document.getId());
			PaymentExecuteNotification notification = createNotificationBase(document, stateMachineEvent);
			notification.setAccountResourceType(ResourceType.LOAN.toString());
			notification.setTransactionSum(amount.getDecimal().doubleValue());
			notification.setCurrencyCode(curr);
			notificationService.save(notification);
		}
		catch (BusinessNotificationException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Создание оповещения для быстрой заявки на кредит
	 * @param document - платеж
	 * @throws DocumentException
	 */
	private void createNotification(ExtendedLoanClaim document, StateMachineEvent stateMachineEvent) throws DocumentException
	{
		try
		{
			Money amount = document.getLoanAmount();
			String curr = amount.getCurrency().getCode();
			if (StringHelper.isEmpty(curr))
				throw new DocumentException("Не удалось получить код валюты для документа с id:" + document.getId());
			PaymentExecuteNotification notification = createNotificationBase(document, stateMachineEvent);
			notification.setAccountResourceType(ResourceType.LOAN.toString());
			notification.setTransactionSum(amount.getDecimal().doubleValue());
			notification.setCurrencyCode(curr);
			notificationService.save(notification);
		}
		catch (BusinessNotificationException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Создание оповещения для операции "заявить об утере сберегательной книжки"
	 * @param document - платеж
	 * @throws DocumentException
	 */
	private void createNotification(LossPassbookApplication document, StateMachineEvent stateMachineEvent) throws DocumentException
	{
		try
		{
			PaymentExecuteNotification notification = createNotificationBase(document, stateMachineEvent);
			notification.setAccountNumber(document.getDepositAccount());
			notification.setAccountResourceType(ResourceType.ACCOUNT.toString());

			String temp = document.getFromAccountName();
			notification.setNameOrType(StringHelper.isEmpty(temp) ?
					document.getAttribute("deposit-account-type").getStringValue() : temp);

			notificationService.save(notification);
		}
		catch (BusinessNotificationException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Создание оповещения на создание/отмену автоплатежа
	 * @param document - платеж
	 * @throws DocumentException
	 */
	private void createNotification(AbstractLongOfferDocument document, StateMachineEvent stateMachineEvent) throws DocumentException
	{
		try
		{
			PaymentExecuteNotification notification =  createNotificationBase(document, stateMachineEvent);
			notification.setNameAutoPayment(document.getFriendlyName());
			notification.setAccountNumber(document.getChargeOffAccount());
			Money amount = document.getAmount();
			if (amount != null)
			{
				notification.setTransactionSum(amount.getDecimal().doubleValue());
				notification.setCurrencyCode(amount.getCurrency().getCode());
			}
			if (document.getChargeOffResourceType() != null)
				notification.setAccountResourceType(document.getChargeOffResourceType().toString());

			EditableExternalResourceLink link = (EditableExternalResourceLink) document.getChargeOffResourceLink();
			if (link != null)
				notification.setNameOrType(link.getName());

			notificationService.save(notification);
		}
		catch (BusinessNotificationException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Создание оповещения для отмены длительного поручения
	 * @param document - платеж
	 * @throws DocumentException
	 */
	private void createNotification(RefuseLongOfferClaim document, StateMachineEvent stateMachineEvent) throws DocumentException
	{
		try
		{
			PaymentExecuteNotification notification = createNotificationBase(document, stateMachineEvent);
			notification.setNameAutoPayment(document.getName());
			notification.setAccountNumber(document.getPayerAccount());
			if (document.getChargeOffResourceType() != null)
				notification.setAccountResourceType(document.getChargeOffResourceType().toString());
			notification.setNameOrType(document.getPayerAccountName());

			notificationService.save(notification);
		}
		catch (BusinessNotificationException e)
		{
			throw new DocumentException(e);
		}
	}

	/**
	 * Создание оповещения
	 * @param document - платеж
	 * @throws DocumentException
	 */
	private void createNotification(AbstractPaymentDocument document, StateMachineEvent stateMachineEvent) throws DocumentException
	{
		try
		{
			PaymentExecuteNotification notification = createNotificationBase(document, stateMachineEvent);
			notification.setAccountNumber(document.getChargeOffAccount());
			if (document.getChargeOffResourceType() != null)
				notification.setAccountResourceType(document.getChargeOffResourceType().toString());

			if (document instanceof AbstractAccountsTransfer)
			{
				notification.setRecipientAccountNumber(((AbstractAccountsTransfer) document).getReceiverAccount());
				ResourceType destResourceType = ((AbstractAccountsTransfer)document).getDestinationResourceType();
				if (destResourceType != null)
					notification.setRecipientAccountResourceType(destResourceType.toString());
			}

			Money amount = document.getExactAmount();
			notification.setTransactionSum(amount.getDecimal().doubleValue());
			notification.setCurrencyCode(amount.getCurrency().getCode());
			EditableExternalResourceLink link = (EditableExternalResourceLink) document.getChargeOffResourceLink();
			if (link != null)
				notification.setNameOrType(link.getName());
			notificationService.save(notification);
		}
		catch (BusinessNotificationException e)
		{
			throw new DocumentException(e);
		}
	}

	private PaymentExecuteNotification createNotificationBase(GateExecutableDocument document, StateMachineEvent stateMachineEvent) throws DocumentException
	{
		PaymentExecuteNotification notification = new PaymentExecuteNotification();

		notification.setDateCreated(Calendar.getInstance());
		try
		{
			BusinessDocumentOwner documentOwner = document.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");
			notification.setLoginId(document.getOwner().getLogin().getId());
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		if (stateMachineEvent.getNextState() == null)
			throw new DocumentException("Хендлер CreateNotificationHandler должен выполняться в handlers у next-states а не в handlers у event, т.к. там не определен следующий state");
		notification.setDocumentState(stateMachineEvent.getNextState());
		notification.setDocumentType(document.asGateDocument().getType().getSimpleName());

		return notification;
	}

	private void createNotification(LoanCardClaim document, StateMachineEvent stateMachineEvent) throws DocumentException
	{
		try
		{
			PaymentExecuteNotification notification = createNotificationBase(document, stateMachineEvent);

			Money amount = document.getExactAmount();
			notification.setCurrencyCode(amount.getCurrency().getCode());
			notification.setTransactionSum(amount.getDecimal().doubleValue());
			notificationService.save(notification);
		}
		catch (BusinessNotificationException e)
		{
			throw new DocumentException(e);
		}
	}
}
