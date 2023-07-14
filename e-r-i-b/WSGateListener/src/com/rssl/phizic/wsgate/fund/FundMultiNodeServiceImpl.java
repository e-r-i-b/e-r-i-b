package com.rssl.phizic.wsgate.fund;

import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.web.security.Constants;
import com.rssl.phizic.wsgate.fund.generated.FundInfo;
import com.rssl.phizic.wsgate.fund.generated.FundMultiNodeService;
import com.rssl.phizic.wsgate.fund.generated.RequestInfo;
import com.rssl.phizic.wsgate.fund.generated.Response;
import com.rssl.phizic.wsgate.fund.types.ResponseImpl;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author osminin
 * @ created 21.09.14
 * @ $Author$
 * @ $Revision$
 *
 * Серверная часть сервиса
 */
public class FundMultiNodeServiceImpl implements FundMultiNodeService
{
	public List createFundSenderResponses(List fundInfoList, Long nodeNumber) throws RemoteException
	{
		com.rssl.phizic.gate.fund.FundMultiNodeService fundMultiNodeService = GateSingleton.getFactory().service(com.rssl.phizic.gate.fund.FundMultiNodeService.class);

		try
		{
			List<com.rssl.phizic.gate.fund.FundInfo> gateFundInfoList = new ArrayList<com.rssl.phizic.gate.fund.FundInfo>(fundInfoList.size());
			BeanHelper.copyPropertiesWithDifferentTypes(gateFundInfoList, fundInfoList, TypesCorrelation.getTypes());

			return fundMultiNodeService.createFundSenderResponses(gateFundInfoList, nodeNumber);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public void updateResponseInfo(Response fundResponse) throws RemoteException
	{
		com.rssl.phizic.gate.fund.FundMultiNodeService fundMultiNodeService = GateSingleton.getFactory().service(com.rssl.phizic.gate.fund.FundMultiNodeService.class);

		try
		{
			ResponseImpl fundResponse_1 = new ResponseImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(fundResponse_1, fundResponse, TypesCorrelation.getTypes());

			fundMultiNodeService.updateResponseInfo(fundResponse_1);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public void updateResponseInfoBySum(Response fundResponse, BigDecimal requiredSum) throws RemoteException
	{
		com.rssl.phizic.gate.fund.FundMultiNodeService fundMultiNodeService = GateSingleton.getFactory().service(com.rssl.phizic.gate.fund.FundMultiNodeService.class);

		try
		{
			ResponseImpl fundResponse_1 = new ResponseImpl();
			BeanHelper.copyPropertiesWithDifferentTypes(fundResponse_1, fundResponse, TypesCorrelation.getTypes());

			fundMultiNodeService.updateResponseInfoBySum(fundResponse_1, requiredSum);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public RequestInfo getRequestInfo(String externalResponseId) throws RemoteException
	{
		com.rssl.phizic.gate.fund.FundMultiNodeService fundMultiNodeService = GateSingleton.getFactory().service(com.rssl.phizic.gate.fund.FundMultiNodeService.class);

		try
		{
			com.rssl.phizic.gate.fund.RequestInfo requestInfo = fundMultiNodeService.getRequestInfo(externalResponseId);
			com.rssl.phizic.wsgate.fund.generated.RequestInfo generatedRequestInfo = new com.rssl.phizic.wsgate.fund.generated.RequestInfo();
			BeanHelper.copyPropertiesWithDifferentTypes(generatedRequestInfo, requestInfo, TypesCorrelation.getTypes());

			return generatedRequestInfo;
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public void closeRequests(List requests, Long nodeNumber) throws RemoteException
	{
		com.rssl.phizic.gate.fund.FundMultiNodeService fundMultiNodeService = GateSingleton.getFactory().service(com.rssl.phizic.gate.fund.FundMultiNodeService.class);

		try
		{
			List<com.rssl.phizic.gate.fund.Request> gateRequests = new ArrayList<com.rssl.phizic.gate.fund.Request>(requests.size());
			BeanHelper.copyPropertiesWithDifferentTypes(gateRequests, requests, TypesCorrelation.getTypes());

			fundMultiNodeService.closeRequests(gateRequests, nodeNumber);
		}
		catch(GateException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.MESSAGE_PREFIX + e.getMessage()+ Constants.MESSAGE_SUFFIX, e);
		}
		catch(GateLogicException e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(Constants.LOGIC_MESSAGE_PREFIX + e.getMessage() + Constants.LOGIC_MESSAGE_SUFFIX, e);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_WEB).error(e);
			throw new RemoteException(e.getMessage(), e);
		}
	}

	public FundInfo __forGenerateFundInfo() throws RemoteException
	{
		return null;
	}
}
