package com.rssl.phizic.node.manager.servises.fund;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.fund.*;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.node.manager.NodeManager;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author osminin
 * @ created 06.10.14
 * @ $Author$
 * @ $Revision$
 */
public class FundMultiNodeServiceImpl extends AbstractService implements FundMultiNodeService
{
	private FundMultiNodeService businessDelegate;

	/**
	 * ctor
	 * @param factory фабрика
	 */
	public FundMultiNodeServiceImpl(GateFactory factory)
	{
		super(factory);
		businessDelegate = (FundMultiNodeService) getDelegate(FundMultiNodeService.class.getName() + BUSINESS_DELEGATE_KEY);
	}

	public List<String> createFundSenderResponses(List<FundInfo> fundInfoList, Long nodeNumber) throws GateException, GateLogicException
	{
		if (isSingleNode(nodeNumber))
		{
			return businessDelegate.createFundSenderResponses(fundInfoList, nodeNumber);
		}

		return getNodeDelegate(nodeNumber).createFundSenderResponses(fundInfoList, nodeNumber);
	}

	public void updateResponseInfo(Response fundResponse) throws GateException, GateLogicException
	{
		Long nodeNumber = getNodeNumber(fundResponse.getExternalId());
		if (isSingleNode(nodeNumber))
		{
			businessDelegate.updateResponseInfo(fundResponse);
		}
		else
		{
			getNodeDelegate(nodeNumber).updateResponseInfo(fundResponse);
		}
	}

	public void updateResponseInfoBySum(Response fundResponse, BigDecimal requiredSum) throws GateException, GateLogicException
	{
		Long nodeNumber = getNodeNumber(fundResponse.getExternalId());
		if (isSingleNode(nodeNumber))
		{
			businessDelegate.updateResponseInfoBySum(fundResponse, requiredSum);
		}
		else
		{
			getNodeDelegate(nodeNumber).updateResponseInfoBySum(fundResponse, requiredSum);
		}
	}

	public void closeRequests(List<Request> requests, Long nodeNumber) throws GateException, GateLogicException
	{
		if (isSingleNode(nodeNumber))
		{
			businessDelegate.closeRequests(requests, nodeNumber);
		}
		else
		{
			getNodeDelegate(nodeNumber).closeRequests(requests, nodeNumber);
		}
	}

	public RequestInfo getRequestInfo(String externalResponseId) throws GateException, GateLogicException
	{
		Long nodeNumber = getNodeNumber(externalResponseId);
		if (isSingleNode(nodeNumber))
		{
			return businessDelegate.getRequestInfo(externalResponseId);
		}
		return getNodeDelegate(nodeNumber).getRequestInfo(externalResponseId);
	}

	private FundMultiNodeService getNodeDelegate(Long nodeNumber) throws GateException
	{
		return NodeManager.getInstance().getService(nodeNumber, FundMultiNodeService.class);
	}

	private Long getNodeNumber(String externalId) throws GateException
	{
		ExternalIdImpl externalIdImpl = new ExternalIdImpl(externalId);
		return externalIdImpl.getNodeNumber();
	}

	private boolean isSingleNode(Long nodeNumber)
	{
		ApplicationAutoRefreshConfig config = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class);
		Long nodeId = config.getNodeNumber();

		return nodeNumber.compareTo(nodeId) == 0;
	}
}
