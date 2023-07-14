package com.rssl.phizic.scheduler.jobs.fund;

import com.rssl.phizic.business.fund.FundPushInfo;
import com.rssl.phizic.business.fund.initiator.FundInitiatorResponse;
import com.rssl.phizic.business.fund.initiator.FundRequest;
import com.rssl.phizic.common.types.fund.FundRequestState;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.fund.FundInfo;
import com.rssl.phizic.gate.fund.FundMultiNodeService;
import com.rssl.phizic.messaging.push.FundPushHelper;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author osminin
 * @ created 13.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Шедулер, оповещающий блоки отправителей денег о новом запросе на сбор средств
 */
public class CreateRequestJob extends BaseRequestJob
{
	private Map<Long, List<FundInfo>> synchronizeMap;
	private Map<String, FundPushInfo> pushInfoMap;

	/**
	 * ctor
	 */
	public CreateRequestJob()
	{
		synchronizeMap = new HashMap<Long, List<FundInfo>>();
		pushInfoMap = new HashMap<String, FundPushInfo>();
	}

	protected void executeRequest(FundRequest newRequest)
	{
		try
		{
			if (isNeedSynchronize(newRequest.getId()))
			{
				newRequest.setState(FundRequestState.OPEN);
				List<FundInitiatorResponse> responses = getRequestResponses(newRequest.getId());
				ResponseProcessor processor = new ResponseProcessor(newRequest, responses);
				processor.process(synchronizeMap, pushInfoMap);
			}
		}
		catch (Exception e)
		{
			log.error("Ошибка при обработке ответов в блоке инициатора", e);
		}
	}

	@Override
	protected void executeAdditional()
	{
	}

	@Override
	protected void synchronize()
	{
		//Создаем в блоках отправителей входящие запросы
		for (Long nodeId : synchronizeMap.keySet())
		{
			List<String> createdResponseIds = createSenderResponses(nodeId);

			//Отправляем пуш уведомления всем отправителям, которым смогли создать входящие запросы
			for (String responseId : createdResponseIds)
			{
				sendPush(responseId);
			}
		}
	}

	protected List<FundRequest> getRequests()
	{
		try
		{
			return requestService.getByStateForLifeTime(FundRequestState.SYNC_OPEN);
		}
		catch (Exception e)
		{
			log.error("Ошибка при получении списка новых запросов на сбор средств.", e);
			return Collections.emptyList();
		}
	}

	private void sendPush(String externalId)
	{
		try
		{
			//получаем по ИД из мапы push инфо, создаем и отправляем пуш
			FundPushHelper.sendPush(pushInfoMap.get(externalId));
		}
		catch (Exception e)
		{
			log.error("Ошибка при построении push-сообщения по ответу с внешним идентификатором " + externalId, e);
		}
	}

	private List<String> createSenderResponses(Long nodeId)
	{
		try
		{
			FundMultiNodeService multiNodeService = GateSingleton.getFactory().service(FundMultiNodeService.class);
			List<FundInfo> synchronizeList = synchronizeMap.get(nodeId);

			if (CollectionUtils.isNotEmpty(synchronizeList))
			{
				return multiNodeService.createFundSenderResponses(synchronizeList, nodeId);
			}
		}
		catch (GateLogicException e)
		{
			log.error(e.getMessage(), e);
		}
		catch (Exception e)
		{
			log.error("Ошибка при создании входящих запросов в блоке " + nodeId, e);
		}

		return Collections.emptyList();
	}

	/**
	 * Необходимо ли синхронизировать запрос между блоками
	 * Возможно до того, как шедулер обработал запрос клиент уже закрыл запрос, тогда нет смысла в синхронизации
	 * @param requestId идентификатор запроса
	 * @return нужна ли синхронизация
	 */
	private boolean isNeedSynchronize(Long requestId)
	{
		try
		{
			return requestService.finishOpen(requestId) == 1;
		}
		catch (Exception e)
		{
			log.error("Ошибка при обновлении запроса " + requestId, e);
			return false;
		}
	}

	/**
	 * Получить список ответов на запрос, в ответах хранится информация об отправителе
	 * @param requestId идентификатор запроса
	 * @return список ответов
	 */
	private List<FundInitiatorResponse> getRequestResponses(Long requestId)
	{
		try
		{
			return initiatorResponseService.getByRequestId(requestId);
		}
		catch (Exception e)
		{
			log.error("Ошибка при получении списка ответов по запросу " + requestId, e);
			return Collections.emptyList();
		}
	}
}
