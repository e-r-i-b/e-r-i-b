package com.rssl.phizicgate.wsgate.services.documents;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.WSGateConfig;
import com.rssl.phizic.gate.documents.DebtService;
import com.rssl.phizic.gate.exceptions.*;
import com.rssl.phizic.gate.payments.systems.recipients.Debt;
import com.rssl.phizic.gate.payments.systems.recipients.Field;
import com.rssl.phizic.gate.payments.systems.recipients.Recipient;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.wsgate.WSGateException;
import com.rssl.phizicgate.wsgate.WSGateLogicException;
import com.rssl.phizicgate.wsgate.WSTemporalGateException;
import com.rssl.phizicgate.wsgate.services.JAXRPCClientSideServiceBase;
import com.rssl.phizicgate.wsgate.services.documents.generated.DebtServiceImpl_Impl;
import com.rssl.phizicgate.wsgate.services.documents.generated.DebtService_Stub;
import com.rssl.phizicgate.wsgate.services.documents.generated.RecipientImpl;
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
public class DebtServiceWrapper extends JAXRPCClientSideServiceBase<DebtService_Stub> implements DebtService
{
	public DebtServiceWrapper(GateFactory factory)
	{
		super(factory);
		String url = getFactory().config(WSGateConfig.class).getURL() + "/DebtServiceImpl";
		DebtServiceImpl_Impl service = new DebtServiceImpl_Impl();
		WSRequestHandlerUtil.addWSRequestHandlerToService(service);
		initStub((DebtService_Stub) service.getDebtServicePort(), url);
	}

	public List<Debt> getDebts(Recipient recipient, List<Field>  keyFields) throws GateException, GateLogicException
	{
		try
		{
			RecipientImpl recipientImpl = new RecipientImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(recipientImpl, recipient, TypesCorrelation.getTypes());

			List listFields = new ArrayList();
			BeanHelper.copyPropertiesWithDifferentTypes(listFields, keyFields, TypesCorrelation.getTypes());

			List<Debt> debts = new ArrayList<Debt>();
			BeanHelper.copyPropertiesWithDifferentTypes(debts, getStub().getDebts(recipientImpl,  listFields), TypesCorrelation.getTypes());
			return debts;
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
			if(ex.getMessage().contains(OfflineExternalServiceException.MESSAGE_PREFIX))
			{
				throw new OfflineExternalServiceException(ex);
			}
			if(ex.getMessage().contains(InactiveExternalServiceException.MESSAGE_PREFIX))
			{
				throw new InactiveExternalServiceException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

}