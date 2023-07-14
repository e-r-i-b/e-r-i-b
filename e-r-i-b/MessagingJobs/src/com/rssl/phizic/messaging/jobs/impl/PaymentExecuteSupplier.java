package com.rssl.phizic.messaging.jobs.impl;

import com.rssl.phizic.BankContextConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.messaging.info.PersonalSubscriptionData;
import com.rssl.phizic.business.persons.LightPerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.claims.CardReportDeliveryClaim;
import com.rssl.phizic.gate.payments.RefuseLongOffer;
import com.rssl.phizic.gate.payments.autopayment.CreateAutoPayment;
import com.rssl.phizic.gate.payments.autopayment.EditAutoPayment;
import com.rssl.phizic.gate.payments.autopayment.RefuseAutoPayment;
import com.rssl.phizic.gate.payments.autosubscriptions.*;
import com.rssl.phizic.gate.payments.longoffer.*;
import com.rssl.phizic.messaging.TranslitMode;
import com.rssl.phizic.messaging.jobs.DataSupplier;
import com.rssl.phizic.notifications.Notification;
import com.rssl.phizic.notifications.impl.PaymentExecuteNotification;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringUtils;

import java.util.*;

/**
 * @author eMakarov
 * @ created 29.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class PaymentExecuteSupplier implements DataSupplier
{
	private static final PersonService personService = new PersonService();
	private final PaymentExecuteNotification notification;
	private static final Set<String> namesDocumentAutoPayment = new HashSet<String>();

	static
	{
		namesDocumentAutoPayment.add(EmployeeCloseCardPaymentSystemPaymentLongOffer.class.getSimpleName());
		namesDocumentAutoPayment.add(CloseCardPaymentSystemPaymentLongOffer.class.getSimpleName());
		namesDocumentAutoPayment.add(CreateAutoPayment.class.getSimpleName());
		namesDocumentAutoPayment.add(EditAutoPayment.class.getSimpleName());
		namesDocumentAutoPayment.add(EmployeeEditCardPaymentSystemPaymentLongOffer.class.getSimpleName());
		namesDocumentAutoPayment.add(EditCardPaymentSystemPaymentLongOffer.class.getSimpleName());
		namesDocumentAutoPayment.add(CardToAccountLongOffer.class.getSimpleName());
		namesDocumentAutoPayment.add(EmployeeCardPaymentSystemPaymentLongOffer.class.getSimpleName());
		namesDocumentAutoPayment.add(CardPaymentSystemPaymentLongOffer.class.getSimpleName());
		namesDocumentAutoPayment.add(AccountPaymentSystemPaymentLongOfer.class.getSimpleName());
		namesDocumentAutoPayment.add(LoanTransferLongOffer.class.getSimpleName());
		namesDocumentAutoPayment.add(CardIntraBankPaymentLongOffer.class.getSimpleName());
		namesDocumentAutoPayment.add(CardRUSPaymentLongOffer.class.getSimpleName());
		namesDocumentAutoPayment.add(AccountIntraBankPaymentLongOffer.class.getSimpleName());
		namesDocumentAutoPayment.add(AccountRUSPaymentLongOffer.class.getSimpleName());
		namesDocumentAutoPayment.add(EmployeeRecoveryCardPaymentSystemPaymentLongOffer.class.getSimpleName());
		namesDocumentAutoPayment.add(RecoveryCardPaymentSystemPaymentLongOffer.class.getSimpleName());
		namesDocumentAutoPayment.add(EmployeeDelayCardPaymentSystemPaymentLongOffer.class.getSimpleName());
		namesDocumentAutoPayment.add(DelayCardPaymentSystemPaymentLongOffer.class.getSimpleName());
		namesDocumentAutoPayment.add(RefuseAutoPayment.class.getSimpleName());
		namesDocumentAutoPayment.add(RefuseLongOffer.class.getSimpleName());
		namesDocumentAutoPayment.add(InternalCardsTransferLongOffer.class.getSimpleName());
		namesDocumentAutoPayment.add(ExternalCardsTransferToOurBankLongOffer.class.getSimpleName());
		namesDocumentAutoPayment.add(ExternalCardsTransferToOtherBankLongOffer.class.getSimpleName());
		namesDocumentAutoPayment.add(CloseCardToCardLongOffer.class.getSimpleName());
		namesDocumentAutoPayment.add(DelayCardToCardLongOffer.class.getSimpleName());
		namesDocumentAutoPayment.add(EditInternalP2PAutoTransfer.class.getSimpleName());
		namesDocumentAutoPayment.add(EditExternalP2PAutoTransfer.class.getSimpleName());
		namesDocumentAutoPayment.add(RecoveryCardToCardLongOffer.class.getSimpleName());
	}

	public PaymentExecuteSupplier(Notification notification)
	{
		if (!(notification instanceof PaymentExecuteNotification))
		{
			throw new IllegalArgumentException("Ожидается PaymentExecuteNotification. Получен " + notification);
		}
		this.notification = (PaymentExecuteNotification) notification;
	}

	public Object getData(final PersonalSubscriptionData data, final Calendar current) throws BusinessException
	{
		if (!notification.getLoginId().equals(data.getLogin().getId()))
		{
			throw new IllegalStateException("Логин подписки и логин оповещения не совпадают");
		}
		Map<String, Object> map = new HashMap<String, Object>();

		if (namesDocumentAutoPayment.contains(notification.getDocumentType()))
		{
			map.put("isLongOffer", "true");
			map.put("documentState", notification.getDocumentState());
			map.put("documentType", notification.getDocumentType());
			map.put("accountNumber", notification.getAccountNumber());
			map.put("accountType", notification.getAccountResourceType());
			map.put("nameOrType", notification.getNameOrType());
			map.put("nameAutoPayment", notification.getNameAutoPayment());
		}
		else if (CardReportDeliveryClaim.class.getSimpleName().equals(notification.getDocumentType()))
		{
			map.put("documentState", notification.getDocumentState());
			map.put("documentType",  notification.getDocumentType());
			map.put("accountNumber", notification.getAccountNumber());
			map.put("nameOrType",    notification.getNameOrType());
		}
		else if (notification instanceof PaymentExecuteNotification)
		{
			map.put("isLongOffer", "false");
			map.put("documentState", notification.getDocumentState());
			map.put("documentType", notification.getDocumentType());
			map.put("transactionSum", notification.getTransactionSum());
			map.put("currency", notification.getCurrencyCode());
			map.put("accountNumber", notification.getAccountNumber());
			map.put("accountType", notification.getAccountResourceType());
			map.put("recipientAccountNumber", notification.getRecipientAccountNumber());
			map.put("recipientAccountType", notification.getRecipientAccountResourceType());
			map.put("nameOrType", notification.getNameOrType());
		}
		else
		{
			return null;
		}

		List<Map<String, Object>> entries = new ArrayList<Map<String, Object>>();
		entries.add(map);

		BankContextConfig bankContext = ConfigFactory.getConfig(BankContextConfig.class);
		LightPerson person = personService.getLightPersonByLogin(data.getLogin().getId());

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("bankContext", bankContext);
		result.put("reportTime", current);
		result.put("translit", Boolean.toString(data.getSmsTranslitMode() == TranslitMode.DEFAULT));
		result.put("fullName", StringUtils.capitalizeStrings(person.getSurName(), person.getFirstName(), person.getPatrName()));
		result.put("shortName", person.getFirstName() + " " + person.getPatrName());
		Calendar creationDate = notification.getDateCreated();
		result.put("Date", DateHelper.formatDateToStringWithPoint(creationDate));
		result.put("Time", DateHelper.getTimeFormat(DateHelper.toDate(creationDate)));

		result.put("entries", entries);
		return result;
	}
}
