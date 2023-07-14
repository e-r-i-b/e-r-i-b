package com.rssl.phizic.business.payments.job;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.forJob.ProcessedDocumentIdService;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.payments.BusinessTimeOutException;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachine;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.util.List;

/**
 * ���� ��������� �������� �������� � ���� ��:
 * - �������� ������
 * - �������� ���
 * - �������� � ���
 *
 * @ author: Gololobov
 * @ created: 14.12.2012
 * @ $Author$
 * @ $Revision$
 */
public class RepeatSendRequestDocumentJob extends BaseJob implements StatefulJob
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private static final ProcessedDocumentIdService processedDocumentService = new ProcessedDocumentIdService();
	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();

	// ������� ���������� ��� ��������� �������� ��������
	private static final String[] PAYMENT_STATE = {"UNKNOW"};
	//��������� ��� ������� ���������� ��������� �������� �������� �����
	private static final String[] REPEAT_REQUEST_FORM_NAMES = new String[]{
			//������ �� �������� ������
			FormConstants.ACCOUNT_OPENING_CLAIM_FORM,
			//������ �� �������� ������ ������ � ��������� ������� � ��������� �� �����
			FormConstants.ACCOUNT_OPENING_CLAIM_WITH_CLOSE_FORM,
			//������ �� �������/������� �������
			FormConstants.IMA_PAYMENT_FORM,
			//������ �� �������� ���
			FormConstants.IMA_OPENING_CLAIM,
			//������ �� ���������� ������� ������ ��������� �� ������
			FormConstants.ACCOUNT_CHANGE_INTEREST_DESTINATION,
			//������ �� ��������� ������������ �������
			FormConstants.CHANGE_DEPOSIT_MINIMUM_BALANCE};
	private static final int LIST_LIMIT = 20;
	private final String jobName;

	public RepeatSendRequestDocumentJob() throws JobExecutionException
	{
		jobName = this.getClass().getName();
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			//������ ��� ������ ����� �������� �����
			processedDocumentService.deleteAllForJob(jobName);
			while(true)
			{
				List<BusinessDocument> documents = businessDocumentService.findDocumentsByStateAndFormForJob(PAYMENT_STATE, REPEAT_REQUEST_FORM_NAMES, LIST_LIMIT, jobName);
				// ��� ������ �������
				if(CollectionUtils.isEmpty(documents))
					break;

				for(BusinessDocument document : documents)
				{
					try
					{
						StateMachineExecutor executor = getStateMachineExecutor(document);
						// ���� executor �� �����������������, ���������� ��������
						if(executor == null)
							continue;

						//������������ ��������
						processDocument(document, executor);

						// ��������� � ����
						businessDocumentService.addOrUpdate(document);
					}
					catch (Exception e)
					{
						log.error("������ ��������� ��������� c ���������������" + document.getId(), e);
						DocumentHelper.decrementPromoCode(document);
					}
				}
			}
		}
		catch (BusinessException e)
		{
			throw new JobExecutionException(e);
		}
	}

	/**
	 * ��������� ������ ��������� ��� ���������
	 * @param document - ��������, ��� �������� ���������� �������� ������ ���������
	 * @return - StateMachineExecutor
	 */
	private StateMachineExecutor getStateMachineExecutor(BusinessDocument document)
	{
		try
		{
			StateMachine stateMachine = stateMachineService.getStateMachineByFormName(document.getFormName());
			StateMachineExecutor executor = new StateMachineExecutor(stateMachine);
			executor.initialize(document);
			return executor;
		}
		catch (Exception e)
		{
			log.error("������ ��������� ������ ��������� ��� ��������� " + document.getId(), e);
			return null;
		}
	}

	private void processDocument(BusinessDocument document, StateMachineExecutor executor) throws Exception
	{
		try
		{
			// ��������� �������
			executor.fireEvent(new ObjectEvent(DocumentEvent.REPEAT_SEND, ObjectEvent.SYSTEM_EVENT_TYPE));
		}
		catch (BusinessTimeOutException e)
		{
			log.error("��� ��������� ������� id = " + document.getId() + " �������� �������� time-out. ��������� �������� � ������ 'UNKNOW'", e);
			DocumentHelper.fireDounknowEvent(executor, ObjectEvent.SYSTEM_EVENT_TYPE, e);
			processedDocumentService.add(document.getId(), jobName);
		}
		catch (BusinessLogicException e)
		{
			log.error("������ ��������� ��������� " + document.getId() + ". ��������� � ������ '�������'.", e);
			executor.fireEvent(new ObjectEvent(DocumentEvent.REFUSE, ObjectEvent.SYSTEM_EVENT_TYPE));
		}
		catch (Exception e)
		{
			log.error("������ ��������� ��������� " + document.getId() + ". ��������� � ������ '������'.", e);
			executor.fireEvent(new ObjectEvent(DocumentEvent.ERROR, ObjectEvent.SYSTEM_EVENT_TYPE));
		}
	}
}
