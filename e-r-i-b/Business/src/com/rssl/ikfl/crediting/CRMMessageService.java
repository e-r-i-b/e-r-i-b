package com.rssl.ikfl.crediting;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.Person;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.jms.JMSException;
import javax.naming.NamingException;

/**
 * ������ ���������� � CRM
 *
 * @ author: Gololobov
 * @ created: 04.03.15
 * @ $Author$
 * @ $Revision$
 */
public class CRMMessageService
{
	private final CRMMessageBuilder messageBuilder = new CRMMessageBuilder();
	private final CRMMessageSender messageSender = new CRMMessageSender();
	private final CRMMessageLogger messageLogger = new CRMMessageLogger();
	private static final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * �������� ������� �� �������� ������
	 * @param claim - �������� ����������� ��������� ������
	 * @throws Exception
	 */
	public void createNewCallbackTask(ExtendedLoanClaim claim) throws Exception
	{
		CRMMessage crmMessage = messageBuilder.makePhoneCallback(claim);
		sendMessageToCRMForLoanClaim(crmMessage);
	}

	/**
	 * �������� ������� �� ������ � CRM
	 *
	 * @param claim - �������� ����������� ��������� ������
	 * @param employee
	 * @throws Exception
	 */
	public void createNewCallTask(ExtendedLoanClaim claim, Employee employee) throws Exception
	{
		CRMMessage crmMessage = messageBuilder.makePhoneCall(claim, employee);
		sendMessageToCRMForLoanClaim(crmMessage);
	}

	/**
	 * ����� ������������ � ETSM ������ ������� �� ������� �� ����
	 *
	 * @param person - ������
	 * @throws Exception
	 */
	public void searchApplicationRequest(Person person) throws Exception
	{
		CRMMessage crmMessage = messageBuilder.searchApplicationRequestMessage(person);
		sendMessageToCRMForLoanClaim(crmMessage);
	}


	/**
	 * �������� ����� ��������� ������ � CRM
	 * @param claim - ��������� ������
	 * @throws Exception
	 */
	public void createNewExtendedLoanClaim(ExtendedLoanClaim claim) throws Exception
	{
		CRMMessage crmMessage = messageBuilder.makeNewClaim(claim);
		sendMessageToCRMForLoanClaim(crmMessage);
	}

	/**
	 * ��������� �������� ����� ������. �������� ������ �� ���� � ETSM � ������� ������
	 */
	public void initiationCreateNewLoanClaim(ExtendedLoanClaim claim) throws Exception
	{
		//�������� �������� ������ � ETSM
		CRMStateType state = CRMStateType.OK_STATUS;
		//� ���� ���������� �� ���, ������������ ��� �������� � ETSM, ������ ���������
		if ("INITIAL".equals(claim.getState().getCode()) ||
			"INITIAL2".equals(claim.getState().getCode()))
			state = CRMStateType.INNER_ERROR;
		else
			state = sendLoanClaimToETSM(claim);
		//�������� ��������� � CRM �� ��������� ������� ��������� ������ ��� ������������ ������
		updateLoanClaimStatus(state, claim);
	}


	/**
	 * �������� � ETSM ��������� � �������� � ����� �� ������ �������� �� ������
	 * @param statusCode ��� �������. �-1� �  ����������� ������ ��������, �0� � �������� ��������� ���������, �1� � ������-������.
	 * @param error �������� ������
	 * @param rqUID ��������� ���������.
	 * @param rqTm ��������� ���������.
	 */
	public void sendOfferTicket(String rqUID ,Calendar rqTm,int statusCode, String error) throws Exception
	{
		///�������� � CRM ���������� � ������� ������ � ����. ������ �� CRM ����� ����� �� �����������������.
		CRMMessage crmMessage = messageBuilder.makeOfferTicket(rqUID, rqTm, statusCode, error);
		sendMessageToCRMForLoanClaim(crmMessage);
	}

	/**
	 * �������� � CRM ���������� �� ��������� ������� ������ � ����. ������ �� CRM ����� ����� �� �����������������.
	 * @param state
	 * @param claim
	 * @throws Exception
	 */
	public void updateLoanClaimStatus(CRMStateType state, ExtendedLoanClaim claim) throws Exception
	{
		LoanClaimConfig config = ConfigFactory.getConfig(LoanClaimConfig.class);
		//���� �������� �������� � CRM ��������
		if (config.isSendUpdStatusAvailable())
		{
			//�������� � CRM ���������� � ������� ������ � ����. ������ �� CRM ����� ����� �� �����������������.
			CRMMessage crmMessage = messageBuilder.makeUpdateClaimStatus(state, claim);
			sendMessageToCRMForLoanClaim(crmMessage);
		}
	}

	/**
	 * �������� ������ �� ���� � ETSM. ���� ���������� ������, �� � CRM ���������� ������� ����� �� ������
 	 * @param claim
	 */
	private CRMStateType sendLoanClaimToETSM(ExtendedLoanClaim claim)
	{
		try
		{
			StateMachineExecutor executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(claim.getFormName()));
			executor.initialize(claim);
			ObjectEvent event = new ObjectEvent(DocumentEvent.ETSM_SEND, ObjectEvent.SYSTEM_EVENT_TYPE);
			executor.fireEvent(event);
			//���������� ������ �� ������ � ��
			businessDocumentService.addOrUpdate(claim);
			return CRMStateType.OK_STATUS;
		}
		catch (BusinessLogicException e)
		{
			log.error(e);
			return CRMStateType.INNER_ERROR;
		}
		catch (BusinessException e)
		{
			log.error(e);
			return CRMStateType.INNER_ERROR;
		}
	}

	/**
	 * �������� ��������� � CRM
	 * @param crmMessage
	 * @throws JMSException
	 * @throws NamingException
	 */
	private void sendMessageToCRMForLoanClaim(CRMMessage crmMessage) throws JMSException, NamingException
	{
		messageSender.sendMessageToCRMForLoanClaim(crmMessage);
		//���������� ������ � CRM � ������� ���������
		messageLogger.logMessage(crmMessage);
	}

    /**
     * ����� �� ������
     * @param srcRqUID ������������� ��������� ��������� ������ �������� �� ������ (ETSM-����);
     * @param offerId ������������� ������ � ����
     */
    public void sendRefuseConsumerProductOfferResultRq(String srcRqUID, String offerId) throws Exception
    {
        CRMMessage message = messageBuilder.consumerProductOfferResultRq(srcRqUID, offerId, null, null, null, null, false);
        sendMessageToCRMForLoanClaim(message);
    }

	/**
	 * @param srcRqUID ������������� ��������� ��������� ������ �������� �� ������ (ETSM-����);
	 * @param offerId ������������� ������ � ����
	 * @param finalAmount �������� ����� ������� � ������
	 * @param finalPeriodM �������� ���� ������� � �������
	 * @param finalInterestRate �������� ���������� ������
	 * @param offerAgreeDate ���� � ����� ������������ ������
	 * @return  CRMMessage
	 * @throws Exception
	 */
	public void sendAgreeConsumerProductOfferResultRq(String srcRqUID,String offerId, BigDecimal finalAmount,
	                                                  Long finalPeriodM, BigDecimal finalInterestRate, Calendar offerAgreeDate) throws Exception
	{
		CRMMessage message = messageBuilder.consumerProductOfferResultRq(srcRqUID, offerId, finalAmount, finalPeriodM, finalInterestRate, offerAgreeDate, true);
		sendMessageToCRMForLoanClaim(message);
	}
}
