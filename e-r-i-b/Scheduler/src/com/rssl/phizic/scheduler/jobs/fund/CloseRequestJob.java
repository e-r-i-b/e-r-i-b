package com.rssl.phizic.scheduler.jobs.fund;

import com.rssl.auth.csa.utils.CSAResponseUtils;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.fund.initiator.FundRequest;
import com.rssl.phizic.common.types.fund.ClosedReasonType;
import com.rssl.phizic.common.types.fund.FundRequestState;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.fund.FundMultiNodeService;
import com.rssl.phizic.gate.fund.Request;
import com.rssl.phizic.messaging.push.FundPushHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * @author osminin
 * @ created 13.10.14
 * @ $Author$
 * @ $Revision$
 *
 * Шедулер, оповещающий блоки отправителей денег о закрытии запроса на сбор средств и
 * закрывающий запросы, набравшие необходимую сумму.
 */
public class CloseRequestJob extends BaseRequestJob
{
	private List<NodeInfo> nodeInfoList;
	private List<Request> updatedRequests;

	@Override
	protected void executeAdditional()
	{
		//получаем информацию о блоках
		initializeNodeInfo();
		//сначала закрываем запросы, набравшие необходимую сумму
		closeAccumulatedRequests();
	}

	@Override
	protected void synchronize()
	{
		//рассылаем по блокам список запросов, которые необходимо закрыть
		for (NodeInfo nodeInfo: nodeInfoList)
		{
			synchronizeNode(nodeInfo.getId());
		}
		//закрываем запросы в блоке инициатора
		closeRequests();
	}

	@Override
	protected List<FundRequest> getRequests()
	{
		try
		{
			List<FundRequest> requests = requestService.getByStateForLifeTime(FundRequestState.SYNC_CLOSED);
			updatedRequests = new ArrayList<Request>(requests.size());

			return requests;
		}
		catch (BusinessLogicException e)
		{
			log.error(e.getMessage(), e);
		}
		catch (Exception e)
		{
			log.error("Ошибка при получении запросов на сбор средств, ожидающих закрытия.", e);
		}
		return Collections.emptyList();
	}

	@Override
	protected void executeRequest(FundRequest request)
	{
		//Запрос со статусом закрыт мог попасть сюда только по выборке запросов, набравших необходимую сумму
		//запрос второй раз закрывать не нужно, признаки сбрасываем
		if (FundRequestState.CLOSED == request.getState())
		{
			resetAccumulated(request.getId());
			return;
		}

		//был ли закрыт запрос по причине набора необходимой суммы (у запроса указана необходимая сумма и он не был закрыт явно)
		boolean isFundCompleted = request.getRequiredSum() != null && FundRequestState.OPEN == request.getState();

		//Заполняем характеристики закрытия запроса
		request.setClosedReason(isFundCompleted ? ClosedReasonType.FUND_COMPLETED : ClosedReasonType.FUND_CLOSED);
		request.setState(FundRequestState.CLOSED);
		request.setClosedDate(Calendar.getInstance());

		//добавляем запрос в список на обновление
		updatedRequests.add(request);
	}

	private void closeAccumulatedRequests()
	{
		List<FundRequest> accumulatedRequests = getAccumulatedRequests();
		//крутимся, пока не обработаем все запросы, собравшие необходимую сумму
		if (CollectionUtils.isNotEmpty(accumulatedRequests))
		{
			//инициализируем список обновленных запросов
			updatedRequests = new ArrayList<Request>(accumulatedRequests.size());
			//заполняем список
			for (FundRequest request : accumulatedRequests)
			{
				executeRequest(request);
			}
			//синхронизируем закрытые запросы между блоками и закрываем в блоке иницаитора
			synchronize();
			//переходим к следующей пачке
			closeAccumulatedRequests();
		}
	}

	private List<FundRequest> getAccumulatedRequests()
	{
		try
		{
			return requestService.getAccumulated();
		}
		catch (Exception e)
		{
			log.error("Ошибка при получении запросов, набравших необходимую сумму.", e);
		}
		return Collections.emptyList();
	}

	private void closeRequests()
	{
		for (Request request: updatedRequests)
		{
			FundRequest fundRequest = (FundRequest) request;

			if (closeRequest(fundRequest) && ClosedReasonType.FUND_COMPLETED == fundRequest.getClosedReason())
			{
				sendPush(fundRequest);
			}
		}
	}

	private void sendPush(FundRequest fundRequest)
	{
		try
		{
			FundPushHelper.sendAccumulatedPush(fundRequest);
		}
		catch (BusinessLogicException e)
		{
			log.error(e.getMessage(), e);
		}
		catch (Exception e)
		{
			log.error("Ошибка при отправке пуш-уведомления для инициатора запроса с id " + fundRequest.getId(), e);
		}
	}

	private boolean closeRequest(final FundRequest request)
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					session.update(request);
					//если в запросе была указана необходимая сумма, сбрасываем признаки, что сумма набралась, у ответов
					if (request.getRequiredSum() != null)
					{
						resetAccumulated(request.getId());
					}

					return Boolean.TRUE;
				}
			});
		}
		catch (Exception e)
		{
			log.error("Ошибка при закрытии запроса с id " + request.getId(), e);
			return Boolean.FALSE;
		}
	}

	private void resetAccumulated(Long requestId)
	{
		try
		{
			initiatorResponseService.resetAccumulatedByRequestId(requestId);
		}
		catch (Exception e)
		{
			log.error("Ошибка при сбросе признака, что необходимая сумма набрана, у ответов для запроса с id " + requestId, e);
		}
	}

	private void synchronizeNode(Long nodeId)
	{
		try
		{
			if (CollectionUtils.isNotEmpty(updatedRequests))
			{
				FundMultiNodeService fundMultiNodeService = GateSingleton.getFactory().service(FundMultiNodeService.class);
				fundMultiNodeService.closeRequests(updatedRequests, nodeId);
			}
		}
		catch (GateLogicException e)
		{
			log.error(e.getMessage(), e);
		}
		catch (Exception e)
		{
			log.error("Ошибка при закрытии запросов в блоке с id " + nodeId, e);
		}
	}

	private void initializeNodeInfo()
	{
		try
		{
			nodeInfoList = CSAResponseUtils.getNodesInfo();
		}
		catch (Exception e)
		{
			log.error("Ошибка при получении информации о блоках.", e);
			nodeInfoList = Collections.emptyList();
		}
	}
}
