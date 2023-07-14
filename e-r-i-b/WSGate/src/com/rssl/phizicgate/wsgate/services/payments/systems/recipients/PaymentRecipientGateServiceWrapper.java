package com.rssl.phizicgate.wsgate.services.payments.systems.recipients;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.routable.RecipientImpl;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.config.WSGateConfig;
import com.rssl.phizic.gate.dictionaries.billing.Billing;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.PaymentRecipientGateService;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.gate.payments.systems.recipients.RecipientInfo;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.wsgate.WSGateException;
import com.rssl.phizicgate.wsgate.WSGateLogicException;
import com.rssl.phizicgate.wsgate.WSTemporalGateException;
import com.rssl.phizicgate.wsgate.services.JAXRPCClientSideServiceBase;
import com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.PaymentRecipientGateServiceImpl_Impl;
import com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.PaymentRecipientGateService_Stub;
import com.rssl.phizicgate.wsgate.services.types.ClientImpl;
import com.sun.xml.rpc.client.ClientTransportException;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 29.12.2009
 * @ $Author$
 * @ $Revision$
 */
public class PaymentRecipientGateServiceWrapper extends JAXRPCClientSideServiceBase<PaymentRecipientGateService_Stub> implements PaymentRecipientGateService
{
	public PaymentRecipientGateServiceWrapper(GateFactory factory)
	{
		super(factory);
		String url = getFactory().config(WSGateConfig.class).getURL() + "/PaymentRecipientGateServiceImpl";
		PaymentRecipientGateServiceImpl_Impl service = new PaymentRecipientGateServiceImpl_Impl();
		WSRequestHandlerUtil.addWSRequestHandlerToService(service);
		initStub((PaymentRecipientGateService_Stub) service.getPaymentRecipientGateServicePort(), url);
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
		try
		{
			com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Billing billingImpl = new com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Billing();
			BeanHelper.copyPropertiesWithDifferentTypes(billingImpl, billing, TypesCorrelation.types);
			List<com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Recipient> recipients = getStub().getRecipientList2(account, bic, inn,billingImpl);
			List<Recipient> result = new ArrayList<Recipient>();
			BeanHelper.copyPropertiesWithDifferentTypes(result, recipients, TypesCorrelation.types);
			return result;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<Recipient> getPersonalRecipientList(String billingClientId, Billing billing) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Billing billing_1 = new com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Billing();
			BeanHelper.copyPropertiesWithDifferentTypes(billing_1, billing, TypesCorrelation.types);

			List<Recipient> personalRecipientList = new ArrayList<Recipient>();
			List<com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Recipient> personalRecipientList_1 = getStub().getPersonalRecipientList(billingClientId, billing_1);
			BeanHelper.copyPropertiesWithDifferentTypes(personalRecipientList, personalRecipientList_1, TypesCorrelation.types);

			return personalRecipientList;
		}
		catch(RemoteException e)
		{
			if (e.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(e);
			}
			if (e.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(e);
			}
			throw new GateException(e);
		}
		catch(Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<Field> getPersonalRecipientFieldsValues(Recipient recipient, String billingClientId) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Recipient recipient_1 = new com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Recipient();
			BeanHelper.copyPropertiesWithDifferentTypes(recipient_1, recipient, TypesCorrelation.types);

			List<Field> personalRecipientFieldsValues = new ArrayList<Field>();
			List<com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Field> personalRecipientFieldsValues_1 = getStub().getPersonalRecipientFieldsValues(recipient_1,billingClientId);
			BeanHelper.copyPropertiesWithDifferentTypes(personalRecipientFieldsValues, personalRecipientFieldsValues_1, TypesCorrelation.types);

			return personalRecipientFieldsValues;
		}
		catch(RemoteException e)
		{
			if (e.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(e);
			}
			if (e.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(e);
			}
			throw new GateException(e);
		}
		catch(Exception e)
		{
			throw new GateException(e);
		}
	}

	public RecipientInfo getRecipientInfo(Recipient recipient, List<Field> fields, String debtCode) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Recipient recipientImpl = new com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Recipient();
			BeanHelper.copyPropertiesWithDifferentTypes(recipientImpl, recipient, TypesCorrelation.types);

			List<com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Field> fieldList = new ArrayList<com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Field>();
			BeanHelper.copyPropertiesWithDifferentTypes(fieldList, fields, TypesCorrelation.types);

			com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.RecipientInfo recipientInfo = getStub().getRecipientInfo(recipientImpl, fieldList, debtCode);
			RecipientInfo result = new com.rssl.phizgate.common.payments.systems.recipients.RecipientInfoImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(result, recipientInfo, TypesCorrelation.types);
			return result;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Recipient getRecipient(String recipientId) throws GateException, GateLogicException
	{
		try
		{
			String encoded = encodeData(recipientId);
			com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Recipient recipient = getStub().getRecipient(encoded);
			Recipient result = new RecipientImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(result, recipient, TypesCorrelation.types);
			return result;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<Recipient> getRecipientList(Billing billing) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Billing billingImpl = new com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Billing();
			BeanHelper.copyPropertiesWithDifferentTypes(billingImpl, billing, TypesCorrelation.types);
			List<com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Recipient> recipients = getStub().getRecipientList(billingImpl);
			List<Recipient> result = new ArrayList<Recipient>();
			BeanHelper.copyPropertiesWithDifferentTypes(result, recipients, TypesCorrelation.types);
			return result;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<Field> getRecipientFields(Recipient recipient, List<Field> keyFields) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Recipient recipientImpl = new com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Recipient();
			BeanHelper.copyPropertiesWithDifferentTypes(recipientImpl, recipient, TypesCorrelation.types);

			List<com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Field> fieldList = new ArrayList<com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Field>();
			BeanHelper.copyPropertiesWithDifferentTypes(fieldList, keyFields, TypesCorrelation.types);

			List<com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Field> fields = getStub().getRecipientFields(recipientImpl, fieldList);
			List<Field> result = new ArrayList<Field>();
			BeanHelper.copyPropertiesWithDifferentTypes(result, fields, TypesCorrelation.types);
			return result;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public List<Field> getRecipientKeyFields(Recipient recipient) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Recipient recipientImpl = new com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Recipient();
			BeanHelper.copyPropertiesWithDifferentTypes(recipientImpl, recipient, TypesCorrelation.types);

			List<com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Field> fields = getStub().getRecipientKeyFields(recipientImpl);
			List<Field> result = new ArrayList<Field>();
			BeanHelper.copyPropertiesWithDifferentTypes(result, fields, TypesCorrelation.types);
			return result;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public Client getCardOwner(String cardId, Billing billing) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Billing billingImpl
					= new com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Billing();
			BeanHelper.copyPropertiesWithDifferentTypes(billingImpl, billing, TypesCorrelation.types);

			com.rssl.phizicgate.wsgate.services.payments.systems.recipients.generated.Client client
					= getStub().getCardOwner(cardId, billingImpl);

			if (client == null)
				return null;

			Client result = new ClientImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(result, client, TypesCorrelation.types);
			return result;
		}
		catch (InvocationTargetException e)
		{
			if (e.getTargetException() instanceof TemporalDocumentException)
			{
				throw new TemporalGateException(e);
			}
			throw new GateException(e);
		}
		catch (RemoteException ex)
		{
			checkTimeoutException(ex);

			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateException(ex);
			}
			if (ex.getMessage().contains(WSGateLogicException.MESSAGE_PREFIX))
			{
				throw new WSGateLogicException(ex);
			}
			if (ex.getMessage().contains(WSGateException.MESSAGE_PREFIX))
			{
				throw new WSGateException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}