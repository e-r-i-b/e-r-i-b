package com.rssl.phizicgate.wsgateclient.services.recipients;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.services.StubUrlBackServiceWrapper;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.payments.systems.recipients.BusinessRecipientInfo;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.wsgateclient.WSGateClientException;
import com.rssl.phizicgate.wsgateclient.WSTemporalGateClientException;
import com.rssl.phizicgate.wsgateclient.services.recipients.generated.BackRefReceiverInfoService;
import com.rssl.phizicgate.wsgateclient.services.recipients.generated.BackRefReceiverInfoServiceImpl_Impl;
import com.rssl.phizicgate.wsgateclient.services.types.BusinessRecipientInfoImpl;
import com.sun.xml.rpc.client.ClientTransportException;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

/**
 * @author khudyakov
 * @ created 20.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class BackRefReceiverInfoServiceWrapper extends AbstractService implements com.rssl.phizic.gate.payments.systems.recipients.BackRefReceiverInfoService
{
	private StubUrlBackServiceWrapper<BackRefReceiverInfoService> wrapper;

	public BackRefReceiverInfoServiceWrapper(GateFactory factory)
	{
		super(factory);
		wrapper = new StubUrlBackServiceWrapper<BackRefReceiverInfoService>("BackRefReceiverInfoServiceImpl"){
			protected void createStub()
			{
				BackRefReceiverInfoServiceImpl_Impl service = new BackRefReceiverInfoServiceImpl_Impl();
				WSRequestHandlerUtil.addWSRequestHandlerToService(service);
				setStub(service.getBackRefReceiverInfoServicePort());
			}
		};
	}

	public BusinessRecipientInfo getRecipientInfo(String pointCode, String serviceCode) throws GateException
	{
		try
		{
			com.rssl.phizicgate.wsgateclient.services.recipients.generated.BusinessRecipientInfo generatedInfo
							= wrapper.getStub().getRecipientInfo(pointCode, serviceCode);

			if (generatedInfo == null)
				return null;

			BusinessRecipientInfo gateInfo = new BusinessRecipientInfoImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(gateInfo, generatedInfo, TypesCorrelation.getTypes());

			return gateInfo;
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
			if ((ex.detail instanceof ClientTransportException) || ex.getMessage().contains(WSTemporalGateClientException.MESSAGE_PREFIX))
			{
				throw new WSTemporalGateClientException(ex);
			}
			if (ex.getMessage().contains(WSGateClientException.MESSAGE_PREFIX))
			{
				throw new WSGateClientException(ex);
			}
			throw new GateException(ex);
		}
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}