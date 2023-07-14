package com.rssl.phizicgate.manager.services.routablePersistent.systems.recipients;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.PaymentRecipientGateService;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.gate.payments.systems.recipients.RecipientInfo;
import com.rssl.phizicgate.manager.services.objects.BillingWithoutRouteInfo;
import com.rssl.phizicgate.manager.services.objects.RecipientWithoutRouteInfo;
import com.rssl.phizicgate.manager.services.objects.RouteInfoReturner;
import com.rssl.phizicgate.manager.services.routablePersistent.RoutablePersistentServiceBase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bogdanov
 * @ created 29.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class PaymentRecipientGateServiceImpl extends RoutablePersistentServiceBase<PaymentRecipientGateService> implements PaymentRecipientGateService
{
	public PaymentRecipientGateServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	protected PaymentRecipientGateService endService(String routeInfo) throws GateLogicException, GateException
	{
		return getDelegateFactory(routeInfo).service(PaymentRecipientGateService.class);
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
		BillingWithoutRouteInfo bi = removeRouteInfo(billing);
		List<Recipient> listRecipients = endService(bi.getRouteInfo()).getRecipientList(account, bic, inn, bi);
		List<Recipient> recipients = new ArrayList<Recipient>();
		for (Recipient recipient : listRecipients)
		{
			recipients.add(storeRouteInfo(recipient, bi.getRouteInfo()));
		}
		return recipients;
	}

	public List<Recipient> getPersonalRecipientList(String billingClientId, Billing billing) throws GateException, GateLogicException
	{
		BillingWithoutRouteInfo bi = removeRouteInfo(billing);
		List<Recipient> listRecipients = endService(bi.getRouteInfo()).getPersonalRecipientList(billingClientId, bi);
		List<Recipient> recipients = new ArrayList<Recipient>();
		for (Recipient recipient : listRecipients)
		{
			recipients.add(storeRouteInfo(recipient, bi.getRouteInfo()));
		}
		return recipients;
	}

	public List<Field> getPersonalRecipientFieldsValues(Recipient recipient, String billingClientId) throws GateException, GateLogicException
	{
		RecipientWithoutRouteInfo ri = removeRouteInfo(recipient);
		return endService(ri.getRouteInfo()).getPersonalRecipientFieldsValues(ri, billingClientId);
	}

	public RecipientInfo getRecipientInfo(Recipient recipient, List<Field> fields, String debtCode) throws GateException, GateLogicException
	{
		RecipientWithoutRouteInfo ri = removeRouteInfo(recipient);
		return endService(ri.getRouteInfo()).getRecipientInfo(ri, fields, debtCode);
	}

	public Recipient getRecipient(String recipientId) throws GateException, GateLogicException
	{
		RouteInfoReturner si = removeRouteInfoString(recipientId);
		Recipient recipient = endService(si.getRouteInfo()).getRecipient(si.getId());
		return storeRouteInfo(recipient, si.getRouteInfo());
	}

	public List<Recipient> getRecipientList(Billing billing) throws GateException, GateLogicException
	{
		BillingWithoutRouteInfo bi = removeRouteInfo(billing);
		List<Recipient> listRecipients = endService(bi.getRouteInfo()).getRecipientList(bi);
		List<Recipient> recipients = new ArrayList<Recipient>();
		for (Recipient recipient : listRecipients)
		{
			recipients.add(storeRouteInfo(recipient, bi.getRouteInfo()));
		}
		return recipients;
	}

	public List<Field> getRecipientFields(Recipient recipient, List<Field> keyFields) throws GateException, GateLogicException
	{
		RecipientWithoutRouteInfo ri = removeRouteInfo(recipient);
		return endService(ri.getRouteInfo()).getRecipientFields(ri, keyFields);
	}

	public List<Field> getRecipientKeyFields(Recipient recipient) throws GateException, GateLogicException
	{
		RecipientWithoutRouteInfo ri = removeRouteInfo(recipient);
		return endService(ri.getRouteInfo()).getRecipientKeyFields(ri);
	}

	public Client getCardOwner(String cardId, Billing billing) throws GateException, GateLogicException
	{
		BillingWithoutRouteInfo bi = removeRouteInfo(billing);
		return endService(bi.getRouteInfo()).getCardOwner(cardId, bi);
	}
}
