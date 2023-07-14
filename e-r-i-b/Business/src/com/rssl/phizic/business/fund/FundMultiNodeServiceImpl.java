package com.rssl.phizic.business.fund;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.fund.initiator.FundInitiatorResponseService;
import com.rssl.phizic.business.fund.initiator.FundRequestInfo;
import com.rssl.phizic.business.fund.initiator.FundRequestService;
import com.rssl.phizic.business.fund.sender.FundSenderResponseService;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.fund.*;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author osminin
 * @ created 21.09.14
 * @ $Author$
 * @ $Revision$
 *
 * Бизнес часть сервиса для работы с запрсами на сбор средств в разных блоках
 */
public class FundMultiNodeServiceImpl extends AbstractService implements FundMultiNodeService
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static FundSenderResponseService senderResponseService = new FundSenderResponseService();
	private static FundInitiatorResponseService initiatorResponseService = new FundInitiatorResponseService();
	private static FundRequestService requestService = new FundRequestService();

	/**
	 * ctor
	 * @param factory фабрика
	 */
	public FundMultiNodeServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	public List<String> createFundSenderResponses(List<FundInfo> fundInfoList, Long nodeNumber) throws GateException, GateLogicException
	{
		List<String> createdResponseIds = new ArrayList<String>(fundInfoList.size());

		for (FundInfo info : fundInfoList)
		{
			if (createFundSenderResponse(info))
			{
				createdResponseIds.add(info.getExternalResponseId());
			}
		}

		return createdResponseIds;
	}

	/**
	 * Создать заявку на сбор средств
	 * @param fundInfo Информация о сборе средств
	 * @return создана ли заявка
	 */
	private boolean createFundSenderResponse(FundInfo fundInfo)
	{
		try
		{
			senderResponseService.createResponse(fundInfo.getRequest(), fundInfo.getInitiatorGuid(), fundInfo.getSenderGuid(), fundInfo.getExternalResponseId(), fundInfo.getInitiatorPhones());
			return true;
		}
		catch (BusinessLogicException e)
		{
			log.error(e.getMessage(), e);
		}
		catch (Exception e)
		{
			log.error("Ошибка при создании ответа.", e);
		}
		return false;
	}

	public void updateResponseInfo(Response fundResponse) throws GateException, GateLogicException
	{
		try
		{
			initiatorResponseService.updateInfo(fundResponse);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new GateLogicException(e.getMessage(), e);
		}
	}

	public void updateResponseInfoBySum(Response fundResponse, BigDecimal requiredSum) throws GateException, GateLogicException
	{
		try
		{
			initiatorResponseService.updateInfoBySum(fundResponse, requiredSum);
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new GateLogicException(e.getMessage(), e);
		}
	}

	public void closeRequests(List<Request> requests, Long nodeNumber) throws GateException, GateLogicException
	{
		for (Request request : requests)
		{
			closeRequest(request);
		}
	}

	private void closeRequest(Request request)
	{
		try
		{
			senderResponseService.closeRequest(request);
		}
		catch (BusinessException e)
		{
			log.error("Ошибка при закрытии запроса " + request.getExternalId(), e);
		}
		catch (BusinessLogicException e)
		{
			log.error(e.getMessage(), e);
		}
	}

	public RequestInfo getRequestInfo(String externalResponseId) throws GateException, GateLogicException
	{
		try
		{
			FundRequestInfo fundRequestInfo = requestService.getRequestInfoByExternalResponseId(externalResponseId);
			if (fundRequestInfo == null)
			{
				return null;
			}

			RequestInfoImpl requestInfo = new RequestInfoImpl();
			requestInfo.setState(fundRequestInfo.getState());
			requestInfo.setInternalId(fundRequestInfo.getInternalId());
			requestInfo.setAccumulatedSum(initiatorResponseService.getAccumulatedSum(fundRequestInfo.getInternalId()));

			return requestInfo;
		}
		catch (BusinessException e)
		{
			throw new GateException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new GateLogicException(e.getMessage(), e);
		}
	}
}
