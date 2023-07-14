package com.rssl.phizic.messaging.payments.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.fund.sender.FundSenderResponse;
import com.rssl.phizic.business.fund.sender.FundSenderResponseService;
import com.rssl.phizic.common.types.fund.FundResponseState;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.fund.FundMultiNodeService;
import com.rssl.phizic.messaging.push.FundPushHelper;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author osminin
 * @ created 25.12.14
 * @ $Author$
 * @ $Revision$
 *
 * ������� ��� �������� ��������� ������� �� ���� ������� � ������ "������������"
 * ������ �������������� ������ ��� ��������� executed
 */
public class SetExecutedFundHandler extends BusinessDocumentHandlerBase
{
	private static FundSenderResponseService fundSenderResponseService = new FundSenderResponseService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		if (!(document instanceof RurPayment))
		{
			throw new DocumentException("�������� ��� ������� - ��������� RurPayment");
		}

		RurPayment payment = (RurPayment) document;
		String fundResponseId = payment.getFundResponseId();

		if (StringHelper.isEmpty(fundResponseId))
		{
			throw new DocumentException("������������� ��������� ������� �� ���� ������� �� ����� ���� null.");
		}

		FundSenderResponse response = getResponse(fundResponseId);

		if (response == null)
		{
			throw new DocumentException("�������� ������ �� ���� ������� �� id " + fundResponseId + " �� ������.");
		}

		if (FundResponseState.READ == response.getState())
		{
			process(response, payment);
			sendPush(response);
		}
	}

	private void sendPush(FundSenderResponse response)
	{
		try
		{
			FundPushHelper.sendExecutedPush(response);
		}
		catch (BusinessException e)
		{
			log.error("������ ��� �������� ���-����������� �� �������������� ������� �� ���� ������� � id " + response.getExternalId(), e);
		}
		catch (BusinessLogicException e)
		{
			log.error(e.getMessage(), e);
		}
	}

	private void process(FundSenderResponse response, RurPayment payment) throws DocumentException
	{
		response.setMessage(payment.getMessageToReceiver());
		response.setSum(payment.getDestinationAmount().getDecimal());
		response.setState(FundResponseState.SUCCESS);

		if (setSenderSuccess(response, payment.getId()) == 1)
		{
			setInitiatorSuccess(response);
		}
	}

	private int setSenderSuccess(FundSenderResponse response, Long paymentId) throws DocumentException
	{
		try
		{
			return fundSenderResponseService.setSuccessState(response.getExternalId(), response.getSum(), response.getMessage(), paymentId);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}

	private void setInitiatorSuccess(FundSenderResponse response)
	{
		try
		{
			FundMultiNodeService fundMultiNodeService = GateSingleton.getFactory().service(FundMultiNodeService.class);

			if (response.getRequiredSum() == null)
			{
				fundMultiNodeService.updateResponseInfo(response);
			}
			else
			{
				fundMultiNodeService.updateResponseInfoBySum(response, response.getRequiredSum());
			}
		}
		catch (GateException e)
		{
			log.error("������ ��� ���������� ������� ��������� ������� � id " + response.getExternalId() + " �� ���� ������� � ����� ����������.", e);
		}
		catch (GateLogicException e)
		{
			log.error(e.getMessage(), e);
		}
	}

	private FundSenderResponse getResponse(String externalId) throws DocumentException
	{
		try
		{
			return fundSenderResponseService.getByExternalId(externalId);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
