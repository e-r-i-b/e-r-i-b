package com.rssl.phizicgate.wsgate.dictionaries;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.WSGateConfig;
import com.rssl.phizic.gate.dictionaries.ContactMember;
import com.rssl.phizic.gate.dictionaries.MembersGateService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.exceptions.TemporalGateException;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.wsgate.WSGateException;
import com.rssl.phizicgate.wsgate.WSGateLogicException;
import com.rssl.phizicgate.wsgate.WSTemporalGateException;
import com.rssl.phizicgate.wsgate.dictionaries.generated.GateServiceImpl_Impl;
import com.rssl.phizicgate.wsgate.dictionaries.generated.GateService_Stub;
import com.rssl.phizicgate.wsgate.services.JAXRPCClientSideServiceBase;
import com.sun.xml.rpc.client.ClientTransportException;

import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lukina
 * @ created 08.10.2009
 * @ $Author$
 * @ $Revision$
 */
public class MembersGateServiceWrapper extends JAXRPCClientSideServiceBase<GateService_Stub> implements MembersGateService
{
	public MembersGateServiceWrapper(GateFactory factory) throws GateException
	{
		super(factory);
		String url = factory.config(WSGateConfig.class).getURL() + "/MembersGateServiceImpl";
		GateServiceImpl_Impl service = new GateServiceImpl_Impl();
		initStub((GateService_Stub) service.getGateServicePort(), url);
	}

	public List<ContactMember> getAll(int firstResult, int maxResults) throws GateException, GateLogicException
	{
		try
		{
			List<com.rssl.phizicgate.wsgate.dictionaries.generated.ContactMember>  generated =  getStub().getAll(firstResult, maxResults);
			List<ContactMember> records = new ArrayList<ContactMember>();

			BeanHelper.copyPropertiesWithDifferentTypes(records, generated, TypesCorrelation.types);

			return records;
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

	public	List<ContactMember> getAll(ContactMember template, int firstResult, int listLimit) throws GateException
	{
	   throw new UnsupportedOperationException();
	}
}