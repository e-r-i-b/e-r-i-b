package com.rssl.phizicgate.wsgate.services.fund;

import com.rssl.phizic.ListenerConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.fund.*;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.node.NodeFactory;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizicgate.wsgate.WSGateException;
import com.rssl.phizicgate.wsgate.WSGateLogicException;
import com.rssl.phizicgate.wsgate.services.fund.generated.FundMultiNodeServiceImpl_Impl;
import com.rssl.phizicgate.wsgate.services.fund.impl.RequestInfoImpl;
import com.sun.xml.rpc.client.StubBase;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.rpc.Stub;

/**
 * @author osminin
 * @ created 21.09.14
 * @ $Author$
 * @ $Revision$
 *
 * Клиентская часть
 */
public class FundMultiNodeServiceWrapper extends AbstractService implements FundMultiNodeService
{
	private com.rssl.phizicgate.wsgate.services.fund.generated.FundMultiNodeService stub;

	/**
	 * ctor
	 * @param factory фабрика
	 */
	public FundMultiNodeServiceWrapper(GateFactory factory)
	{
		super(factory);

		stub = new FundMultiNodeServiceImpl_Impl().getFundMultiNodeServicePort();
		NodeFactory nodeFactory = (NodeFactory) factory;
		String url = String.format(ConfigFactory.getConfig(ListenerConfig.class).getNodeUrl(), nodeFactory.getListenerHost()) + "/FundMultiNodeServiceImpl";

		((StubBase)stub)._setProperty(Stub.ENDPOINT_ADDRESS_PROPERTY, url);
	}

	public List<String> createFundSenderResponses(List<FundInfo> fundInfoList, Long nodeNumber) throws GateException, GateLogicException
	{
		try
		{
			List<com.rssl.phizicgate.wsgate.services.fund.generated.FundInfo> fundInfoList_1 = new ArrayList<com.rssl.phizicgate.wsgate.services.fund.generated.FundInfo>(fundInfoList.size());
			BeanHelper.copyPropertiesWithDifferentTypes(fundInfoList_1, fundInfoList, TypesCorrelation.getTypes());

			return (List<String>) stub.createFundSenderResponses(fundInfoList_1, nodeNumber);
		}
		catch (RemoteException e)
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
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void updateResponseInfo(Response fundResponse) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.fund.generated.Response fundResponse_1 = new com.rssl.phizicgate.wsgate.services.fund.generated.Response();
			BeanHelper.copyPropertiesWithDifferentTypes(fundResponse_1, fundResponse, TypesCorrelation.getTypes());

			stub.updateResponseInfo(fundResponse_1);
		}
		catch (RemoteException e)
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
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void updateResponseInfoBySum(Response fundResponse, BigDecimal requiredSum) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.fund.generated.Response fundResponse_1 = new com.rssl.phizicgate.wsgate.services.fund.generated.Response();
			BeanHelper.copyPropertiesWithDifferentTypes(fundResponse_1, fundResponse, TypesCorrelation.getTypes());

			stub.updateResponseInfoBySum(fundResponse_1, requiredSum);
		}
		catch (RemoteException e)
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
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public RequestInfo getRequestInfo(String externalResponseId) throws GateException, GateLogicException
	{
		try
		{
			com.rssl.phizicgate.wsgate.services.fund.generated.RequestInfo generatedRequestInfo = stub.getRequestInfo(externalResponseId);
			com.rssl.phizicgate.wsgate.services.fund.impl.RequestInfoImpl requestInfo = new RequestInfoImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(requestInfo, generatedRequestInfo, TypesCorrelation.getTypes());

			return requestInfo;
		}
		catch (RemoteException e)
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
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}

	public void closeRequests(List<Request> requests, Long nodeNumber) throws GateException, GateLogicException
	{
		try
		{
			List<com.rssl.phizicgate.wsgate.services.fund.generated.Request> generatedRequest = new ArrayList<com.rssl.phizicgate.wsgate.services.fund.generated.Request>(requests.size());
			BeanHelper.copyPropertiesWithDifferentTypes(generatedRequest, requests, TypesCorrelation.getTypes());
			stub.closeRequests(generatedRequest, nodeNumber);
		}
		catch (RemoteException e)
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
		catch (Exception e)
		{
			throw new GateException(e);
		}
	}
}
