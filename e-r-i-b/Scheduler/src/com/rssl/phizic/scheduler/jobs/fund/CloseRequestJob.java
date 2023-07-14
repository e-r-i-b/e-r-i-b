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
 * �������, ����������� ����� ������������ ����� � �������� ������� �� ���� ������� �
 * ����������� �������, ��������� ����������� �����.
 */
public class CloseRequestJob extends BaseRequestJob
{
	private List<NodeInfo> nodeInfoList;
	private List<Request> updatedRequests;

	@Override
	protected void executeAdditional()
	{
		//�������� ���������� � ������
		initializeNodeInfo();
		//������� ��������� �������, ��������� ����������� �����
		closeAccumulatedRequests();
	}

	@Override
	protected void synchronize()
	{
		//��������� �� ������ ������ ��������, ������� ���������� �������
		for (NodeInfo nodeInfo: nodeInfoList)
		{
			synchronizeNode(nodeInfo.getId());
		}
		//��������� ������� � ����� ����������
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
			log.error("������ ��� ��������� �������� �� ���� �������, ��������� ��������.", e);
		}
		return Collections.emptyList();
	}

	@Override
	protected void executeRequest(FundRequest request)
	{
		//������ �� �������� ������ ��� ������� ���� ������ �� ������� ��������, ��������� ����������� �����
		//������ ������ ��� ��������� �� �����, �������� ����������
		if (FundRequestState.CLOSED == request.getState())
		{
			resetAccumulated(request.getId());
			return;
		}

		//��� �� ������ ������ �� ������� ������ ����������� ����� (� ������� ������� ����������� ����� � �� �� ��� ������ ����)
		boolean isFundCompleted = request.getRequiredSum() != null && FundRequestState.OPEN == request.getState();

		//��������� �������������� �������� �������
		request.setClosedReason(isFundCompleted ? ClosedReasonType.FUND_COMPLETED : ClosedReasonType.FUND_CLOSED);
		request.setState(FundRequestState.CLOSED);
		request.setClosedDate(Calendar.getInstance());

		//��������� ������ � ������ �� ����������
		updatedRequests.add(request);
	}

	private void closeAccumulatedRequests()
	{
		List<FundRequest> accumulatedRequests = getAccumulatedRequests();
		//��������, ���� �� ���������� ��� �������, ��������� ����������� �����
		if (CollectionUtils.isNotEmpty(accumulatedRequests))
		{
			//�������������� ������ ����������� ��������
			updatedRequests = new ArrayList<Request>(accumulatedRequests.size());
			//��������� ������
			for (FundRequest request : accumulatedRequests)
			{
				executeRequest(request);
			}
			//�������������� �������� ������� ����� ������� � ��������� � ����� ����������
			synchronize();
			//��������� � ��������� �����
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
			log.error("������ ��� ��������� ��������, ��������� ����������� �����.", e);
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
			log.error("������ ��� �������� ���-����������� ��� ���������� ������� � id " + fundRequest.getId(), e);
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
					//���� � ������� ���� ������� ����������� �����, ���������� ��������, ��� ����� ���������, � �������
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
			log.error("������ ��� �������� ������� � id " + request.getId(), e);
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
			log.error("������ ��� ������ ��������, ��� ����������� ����� �������, � ������� ��� ������� � id " + requestId, e);
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
			log.error("������ ��� �������� �������� � ����� � id " + nodeId, e);
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
			log.error("������ ��� ��������� ���������� � ������.", e);
			nodeInfoList = Collections.emptyList();
		}
	}
}
