package com.rssl.phizic.scheduler.jobs;

import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.loanclaim.ukoClaim.ConfirmClaim;
import com.rssl.phizic.business.loanclaim.ukoClaim.ConfirmPhoneCallback;
import com.rssl.phizic.business.loanclaim.ukoClaim.Confirmable;
import com.rssl.phizic.business.mbuesi.UESICancelLimitMessage;
import com.rssl.phizic.business.mbuesi.UESIService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.mobilebank.MobileBankService;
import com.rssl.phizic.gate.mobilebank.UESIMessage;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.OperationType;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBUtils;
import com.rssl.phizicgate.mobilebank.BkiMessageBean;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.util.*;
import javax.xml.bind.JAXBException;

/**
 * ���� ��������� ������� �� ��������� ������� �� ��� � ��������� �� ����������� �������
 * @author Pankin
 * @ created 29.12.14
 * @ $Author$
 * @ $Revision$
 */
public class CardOfferRefusesJob extends BaseJob implements StatefulJob
{
	private static final Log LOG = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);
	private static final UESIService UESI_SERVICE = new UESIService();
	private static final String CANCEL_LIMIT = "CANCEL_LIMIT";
	private static final String BKISBOL = "BKISBOL";
	private static final String CLAIM_SEND_SMS_ERROR_MESSAGE = "������ ��� ������� ��������� ���-��������� ��� ��������� ������. %s";
	private static final String DONT_UNIQ_CLAIM_BY_NUMBER_ERROR_MESSAGE = "������ ��� ��������� ��������� �� ���. � �� ���� ���������� ����� ����� ������ � �������� ������� \"%s\"";
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		MobileBankService mobileBankService = GateSingleton.getFactory().service(MobileBankService.class);
		try
		{
			//��������� �� ��� ��� ���������
			List<UESIMessage> messages = mobileBankService.getUESIMessagesFromMBK();
			if (CollectionUtils.isEmpty(messages))
				return;
			//������ ID-��������� ��� ����������� ��� �� �� ���������
			List<Long> confirmedMessageIds = new ArrayList<Long>();
			//������ ��������� � �������� �� ��������� �������
			List<UESICancelLimitMessage> cancelLimitMessages = new ArrayList<UESICancelLimitMessage>();
			//������ ������� ������ ��� �������� �� ��� ���-��������� �������-���������� ������
			Map<Long, BkiMessageBean> bkiClaimNumbersWithMessageIdMap = new HashMap<Long, BkiMessageBean>();

			for (UESIMessage message : messages)
			{
				LOG.trace("CardOfferRefusesJob: ��������� ��������� �� ���: "+message.getMessageText());
				if (CANCEL_LIMIT.equals(message.getMessageType()))
				{
					try
					{
						cancelLimitMessages.add(new UESICancelLimitMessage(message));
						confirmedMessageIds.add(message.getMessageId());
					}
					catch (BusinessException e)
					{
						LOG.error("������ ��� ���������� UESI ��������� ���� " + CANCEL_LIMIT + ". ID = " + message.getMessageId(), e);
					}
				}
				else if (BKISBOL.equals(message.getMessageType()))
				{
					BkiMessageBean bkiMessage = parseBkiMessage(message.getMessageText());
					if (bkiMessage != null)
						bkiClaimNumbersWithMessageIdMap.put(message.getMessageId(), bkiMessage);
				}
				else
				{
					LOG.error("����������� ��� ��������� :" + message.getMessageType() + ". ID = " + message.getMessageId());
				}
			}
			if (CollectionUtils.isNotEmpty(cancelLimitMessages))
				UESI_SERVICE.addList(cancelLimitMessages);

			if (CollectionUtils.isNotEmpty(bkiClaimNumbersWithMessageIdMap.entrySet()))
			{
				//�������� ��� �������
				List<Long> messageIdsList = sendSmsToClaimOwner(bkiClaimNumbersWithMessageIdMap);
				//���������� �� ��������� �� ������� ������� ��������� ����� ���
				if (CollectionUtils.isNotEmpty(messageIdsList))
					confirmedMessageIds.addAll(messageIdsList);
			}

			//������������� ��������� ��������� ���������������� ���������� ���
			if (CollectionUtils.isNotEmpty(confirmedMessageIds))
				mobileBankService.confirmUESIMessages(confirmedMessageIds);
		}
		catch (Exception e)
		{
			throw new JobExecutionException(e);
		}
	}

	private BkiMessageBean parseBkiMessage(String text)
	{
		try
		{
			return JAXBUtils.unmarshalBean(BkiMessageBean.class, text);
		}
		catch (JAXBException e)
		{
			LOG.error("�� ������� ��������� xml ��������� ���", e);
			return null;
		}
	}

	/**
	 * �������� ���-��������� ������� � ��������� �� ������ �����
	 * @param bkiClaimNumbersWithMessageIdMap - ���� key-�� ��������� �� ���, value - ���-����� ������ ��� �������������
	 * @return  List<Long> - ������ �� ��������� �� ���, ������� ������� ����������
	 */
	private List<Long> sendSmsToClaimOwner(Map<Long, BkiMessageBean> bkiClaimNumbersWithMessageIdMap) throws BusinessException, BusinessLogicException
	{
		if (CollectionUtils.isEmpty(bkiClaimNumbersWithMessageIdMap.entrySet()))
			return null;
		LOG.trace("CardOfferRefusesJob: �������� ��� ���������");
		//����������� ����������� ���� ������� � ������� ����� �������� ��������� ������
		LoanClaimConfig config = ConfigFactory.getConfig(LoanClaimConfig.class);
		int confirmAvailableDays = config.getConfirmAvailableDays();
		Calendar minClaimConfirmationDate = DateHelper.getOnlyDate(DateHelper.addDays(Calendar.getInstance(), -confirmAvailableDays));
		//������ �� ���������, �� ������� ���������� ������ �� ������ � ���������� ��������� � ����� �������������
		List<Long> messageIdsList = new ArrayList<Long>();

		for (Map.Entry<Long, BkiMessageBean> bkiMessageEntry : bkiClaimNumbersWithMessageIdMap.entrySet())
		{
			BkiMessageBean bkiMessage = bkiMessageEntry.getValue();

			//����� ������ ��������� �� ���
			String bkiClaimNumber = bkiMessage.getQuery();
			//������ ������������ ���� ������, �� �.�. �������� ����������� ����, ��� �������� ��������� ������ � ���������� �������� �������, �� ������������ ������
			List<ExtendedLoanClaim> loanClaimList = businessDocumentService.findLoanClaimsByNumbers(bkiClaimNumber, minClaimConfirmationDate);
			//���������� ����� ����� ������
			if (loanClaimList.size() > 1)
			{
				LOG.error(String.format(DONT_UNIQ_CLAIM_BY_NUMBER_ERROR_MESSAGE, bkiClaimNumber));
				continue;
			}
			//�� ���������� ������ � ����� ������� � �� ����, �.�. ��� �������� ������ ������� �����
			if (CollectionUtils.isEmpty(loanClaimList))
			{
				LOG.trace("CardOfferRefusesJob: �� ���������� ��������� ������ � ������� ���="+bkiClaimNumber);
				continue;
			}
			//"OperationUID" ������ �������� �� ������������� �������� �������� + ����� � �������� + ������ �����.
			//��������������� ������ ��������������� �� ��� �������� �����. ���� ��� ������ �� ������ �����, �� ����� � ���������� ���� ������� �����.
			//����� �����, � ������� �������� ����
			Long nodeNumber = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber();
			ExtendedLoanClaim claim = loanClaimList.get(0);
			LOG.trace("CardOfferRefusesJob: ���������� ��������� ������ ��="+claim.getId());
			if (claim.getOperationUID().endsWith(StringHelper.appendLeadingZeros(nodeNumber.toString(), 2)))
			{
				//��������������� �� ��������� ������
				if ("WAIT_TM".equals(claim.getState().getCode()))
					continue;
				//��������������� �� ������������ � ������� ������ (���_�����)
				String claimNumber = claim.getBkiClaimNumber();
				if (StringHelper.isEmpty(claimNumber) || !bkiClaimNumber.equalsIgnoreCase(claimNumber))
					continue;
				//�� ���� ���� ���-���� ���������, ��, ������ �����, ������������� ��� ��������� ��� ������ �� ������ ������ �����
				//���������� ������� ��� � ����� �������������, ������ �� ������
				if (isSMSPasswordSended(claim, bkiMessage.getPhone()))
				{
					LOG.trace("CardOfferRefusesJob: ��� ��������� ����������");
					Long messageId = bkiMessageEntry.getKey();
					//���������� �� ��������� �� ���, �� �������� ������� ��������� ���������
					messageIdsList.add(messageId);
				}
				else
					LOG.trace("CardOfferRefusesJob: ��� ��������� �� ����������");
			}
		}
		return messageIdsList;
	}

	/**
	 * �������� ������� ���-��������� � ����� �������������
	 *
	 * @param claim - ������ �� ������� ������ ������������ �������� �������
	 * @param phone �������, �� ������� ������������ �������� (��������� �� ���)
	 * @return true - ���� ��������� ����������
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	private boolean isSMSPasswordSended(ExtendedLoanClaim claim, String phone) throws BusinessLogicException, BusinessException
	{
		try
		{
			if (claim == null || StringHelper.isEmpty(claim.getConfirmPassword()))
			{
				String errorMessage = String.format(CLAIM_SEND_SMS_ERROR_MESSAGE, claim == null ? "�������� �� ���������." : "ID="+claim.getId());
				LOG.error(errorMessage);
				return false;
			}

			Confirmable confirmable = getConfirmObject(claim);
			CallBackHandlerSmsImpl callBackHandler = new CallBackHandlerSmsImpl();
			callBackHandler.setConfirmableObject(confirmable);
			callBackHandler.setLogin(claim.getOwner().getLogin());
			//������ ��� ����� � ������� ������
			callBackHandler.setPassword(claim.getConfirmPassword());
			//����� �������� �� ���
			callBackHandler.setPhoneNumber(phone);
			//������������� ������� � ��� ��� ������ ��������� ������
			callBackHandler.setOperationType(OperationType.CALLBACK_BCI_CONFIRM_OPERATION);
			callBackHandler.setUseRecipientMobilePhoneOnly(true);
			callBackHandler.proceed();
		}
		catch (Exception e)
		{
			LOG.error(e);
			return false;
		}
		return true;
	}

	private Confirmable getConfirmObject(ExtendedLoanClaim claim)
	{
		return ExtendedLoanClaim.CONFIRM_GUEST_ACTION.equals(claim.getActionSign()) ? new ConfirmClaim(claim.getId()) : new ConfirmPhoneCallback(claim.getId());
	}
}
