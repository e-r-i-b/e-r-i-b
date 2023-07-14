package com.rssl.phizicgate.manager.services.routable.payments.recipients;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.PaymentRecipientGateService;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.gate.payments.systems.recipients.RecipientInfo;
import com.rssl.phizicgate.manager.services.routable.RoutableServiceBase;

import java.util.List;

/**
 * @author hudyakov
 * @ created 11.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class PaymentRecipientGateServiceImpl extends RoutableServiceBase implements PaymentRecipientGateService
{
	public PaymentRecipientGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * Найти получателей в биллинге по счету, БИКу и ИНН
	 * @param account счет получателя
	 * @param bic бик банка получателя
	 * @param inn инн получателя
	 * @param billing биллинг, в котором нуджно найти получателя
	 * @return список получателей удовлетворяющих результату. если получтели не найдены - пустой список
	 */
	public List<Recipient> getRecipientList(String account, String bic, String inn, Billing billing) throws GateException, GateLogicException
	{
		PaymentRecipientGateService delegate = getDelegateFactory(billing).service(PaymentRecipientGateService.class);
		return delegate.getRecipientList(account, bic, inn, billing);
	}

	public List<Recipient> getPersonalRecipientList(String billingClientId, Billing billing) throws GateException, GateLogicException
	{
		PaymentRecipientGateService delegate = getDelegateFactory(billing).service(PaymentRecipientGateService.class);
		return delegate.getPersonalRecipientList(billingClientId, billing);
	}

	public List<Field> getPersonalRecipientFieldsValues(Recipient recipient, String billingClientId) throws GateException, GateLogicException
	{
		PaymentRecipientGateService delegate = getDelegateFactory(recipient).service(PaymentRecipientGateService.class);
		return delegate.getPersonalRecipientFieldsValues(recipient, billingClientId);
	}

	public RecipientInfo getRecipientInfo(Recipient recipient, List<Field> fields, String debtCode) throws GateException, GateLogicException
	{
		PaymentRecipientGateService delegate = getDelegateFactory(recipient).service(PaymentRecipientGateService.class);
		return delegate.getRecipientInfo(recipient, fields, debtCode);
	}

	public Recipient getRecipient(String recipientId) throws GateException, GateLogicException
	{
		PaymentRecipientGateService delegate = getDelegateFactory(recipientId).service(PaymentRecipientGateService.class);
		return delegate.getRecipient(recipientId);
	}

	public List<Recipient> getRecipientList(Billing billing) throws GateException, GateLogicException
	{
		PaymentRecipientGateService delegate = getDelegateFactory(billing).service(PaymentRecipientGateService.class);
		return delegate.getRecipientList(billing);
	}

	public List<Field> getRecipientFields(Recipient recipient, List<Field> keyFields) throws GateException, GateLogicException
	{
		PaymentRecipientGateService delegate = getDelegateFactory(recipient).service(PaymentRecipientGateService.class);
		return delegate.getRecipientFields(recipient, keyFields);
	}

	public List<Field> getRecipientKeyFields(Recipient recipient) throws GateException, GateLogicException
	{
		PaymentRecipientGateService delegate = getDelegateFactory(recipient).service(PaymentRecipientGateService.class);
		return delegate.getRecipientKeyFields(recipient);
	}

	public Client getCardOwner(String cardId, Billing billing) throws GateException, GateLogicException
	{
		PaymentRecipientGateService delegate = getDelegateFactory(billing).service(PaymentRecipientGateService.class);
		return delegate.getCardOwner(cardId, billing);
	}
}
