package com.rssl.phizicgate.wsgateclient.services.dictionary;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizgate.common.services.StubUrlBackServiceWrapper;
import com.rssl.phizgate.common.ws.WSRequestHandlerUtil;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.wsgateclient.WSGateClientException;
import com.rssl.phizicgate.wsgateclient.WSTemporalGateClientException;
import com.rssl.phizicgate.wsgateclient.services.dictionary.generated.BackRefBankInfoService;
import com.rssl.phizicgate.wsgateclient.services.dictionary.generated.BackRefBankInfoServiceImpl_Impl;
import com.sun.xml.rpc.client.ClientTransportException;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;

/**
 * @author khudyakov
 * @ created 22.03.2011
 * @ $Author$
 * @ $Revision$
 */
public class BackRefBankInfoServiceWrapper extends AbstractService implements com.rssl.phizic.gate.dictionaries.BackRefBankInfoService
{
	private StubUrlBackServiceWrapper<BackRefBankInfoService> wrapper;

	public BackRefBankInfoServiceWrapper(GateFactory factory)
	{
		super(factory);
		wrapper = new StubUrlBackServiceWrapper<BackRefBankInfoService>("BackRefBankInfoServiceImpl"){
			protected void createStub()
			{
				BackRefBankInfoServiceImpl_Impl service = new BackRefBankInfoServiceImpl_Impl();
				WSRequestHandlerUtil.addWSRequestHandlerToService(service);
				setStub(service.getBackRefBankInfoServicePort());
			}
		};
	}


	public ResidentBank findByBIC(String bic) throws GateException
	{
		try
		{
			com.rssl.phizicgate.wsgateclient.services.dictionary.generated.ResidentBank generatedBank
							= wrapper.getStub().findByBIC(bic);

			if (generatedBank == null)
				return null;

			ResidentBank gateBank = new ResidentBank();
			BeanHelper.copyPropertiesWithDifferentTypes(gateBank, generatedBank, TypesCorrelation.getTypes());

			return gateBank;
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
