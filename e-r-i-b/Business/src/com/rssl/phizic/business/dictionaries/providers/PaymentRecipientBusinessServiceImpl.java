package com.rssl.phizic.business.dictionaries.providers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.payments.systems.recipients.*;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * @author hudyakov
 * @ created 29.12.2009
 * @ $Author$
 * @ $Revision$
 */

public class PaymentRecipientBusinessServiceImpl  extends AbstractService implements PaymentRecipientGateService
{
	private static final ServiceProviderService serviceProviderService = new ServiceProviderService();

	public PaymentRecipientBusinessServiceImpl(GateFactory factory)
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
		throw new UnsupportedOperationException();
	}

	public List<Recipient> getPersonalRecipientList(String billingClientId, Billing billing) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public List<Field> getPersonalRecipientFieldsValues(Recipient recipient, String billingClientId) throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}

	public RecipientInfo getRecipientInfo(Recipient recipient, List<Field> fields, String debtCode) throws GateException, GateLogicException
	{
		//Небольшой оптимайз.
		if (recipient instanceof RecipientInfo){
			return (RecipientInfo)recipient;
		}
		return getRecipient((String) recipient.getSynchKey());
	}

	public ServiceProviderBase getRecipient(final String synchKey) throws GateException, GateLogicException
	{
		try
		{
			return serviceProviderService.findBySynchKey(synchKey);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	public List<Recipient> getRecipientList(final Billing billing) throws GateException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Recipient>>()
			{
				public List<Recipient> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery(ServiceProviderBase.class.getName() + ".getRecipientsList");
					query.setParameter("billing", billing);
					//noinspection unchecked
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityException(e);
		}
	}

	public List<Field> getRecipientFields(Recipient recipient, List<Field> keyFields) throws GateException, GateLogicException
	{
		try
		{
			return (List<Field>) serviceProviderService.getRecipientFields(recipient);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	public List<Field> getRecipientKeyFields(Recipient recipient) throws GateException, GateLogicException
	{
		try
		{
			return (List<Field>) serviceProviderService.getRecipientKeyFields(recipient);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
	}

	public Client getCardOwner(String cardId, Billing billing)
			throws GateException, GateLogicException
	{
		throw new UnsupportedOperationException();
	}
}
