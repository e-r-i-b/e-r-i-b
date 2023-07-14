package com.rssl.phizicgate.manager.services.persistent.systems.recipients;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.PaymentRecipientGateService;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.gate.payments.systems.recipients.RecipientInfo;
import com.rssl.phizicgate.manager.services.persistent.PersistentServiceBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gainanov
 * @ created 19.06.2009
 * @ $Author$
 * @ $Revision$
 */
public class PaymentRecipientGateServiceImpl extends PersistentServiceBase<PaymentRecipientGateService> implements PaymentRecipientGateService
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
		List<Recipient> listRecipients = delegate.getRecipientList(account, bic, inn, removeRouteInfo(billing));
		List<Recipient> recipients = new ArrayList<Recipient>();
		for (Recipient recipient : listRecipients)
		{
			recipients.add(storeRouteInfo(recipient));
		}
		return recipients;
	}

	public List<Recipient> getPersonalRecipientList(String billingClientId, Billing billing) throws GateException, GateLogicException
	{
		List<Recipient> listRecipients = delegate.getPersonalRecipientList(billingClientId, removeRouteInfo(billing));
		List<Recipient> recipients = new ArrayList<Recipient>();
		for (Recipient recipient : listRecipients)
		{
			recipients.add(storeRouteInfo(recipient));
		}
		return recipients;
	}

	public List<Field> getPersonalRecipientFieldsValues(Recipient recipient, String billingClientId) throws GateException, GateLogicException
	{
		return delegate.getPersonalRecipientFieldsValues(removeRouteInfo(recipient), billingClientId);
	}

	public RecipientInfo getRecipientInfo(Recipient recipient, List<Field> fields, String debtCode) throws GateException, GateLogicException
	{
		return delegate.getRecipientInfo(removeRouteInfo(recipient), fields, debtCode);
	}

	public Recipient getRecipient(String recipientId) throws GateException, GateLogicException
	{
		Recipient recipient = delegate.getRecipient(removeRouteInfo(recipientId));
		return storeRouteInfo(recipient);
	}

	public List<Recipient> getRecipientList(Billing billing) throws GateException, GateLogicException
	{
		List<Recipient> listRecipients = delegate.getRecipientList(removeRouteInfo(billing));
		List<Recipient> recipients = new ArrayList<Recipient>();
		for (Recipient recipient : listRecipients)
		{
			recipients.add(storeRouteInfo(recipient));
		}
		return recipients;
	}

	public List<Field> getRecipientFields(Recipient recipient, List<Field> keyFields) throws GateException, GateLogicException
	{
		return delegate.getRecipientFields(removeRouteInfo(recipient), keyFields);
	}

	public List<Field> getRecipientKeyFields(Recipient recipient) throws GateException, GateLogicException
	{
		return delegate.getRecipientKeyFields(removeRouteInfo(recipient));
	}

	public Client getCardOwner(String cardId, Billing billing) throws GateException, GateLogicException
	{
		return delegate.getCardOwner(cardId, removeRouteInfo(billing));
	}
}
