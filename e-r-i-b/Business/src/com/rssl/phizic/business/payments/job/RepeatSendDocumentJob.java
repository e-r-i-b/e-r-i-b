package com.rssl.phizic.business.payments.job;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.payments.BusinessTimeOutException;
import com.rssl.phizic.business.statemachine.PaymentStateMachineService;
import com.rssl.phizic.business.statemachine.StateMachine;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.util.Calendar;
import java.util.List;

/**
 * @author niculichev
 * @ created 15.06.2012
 * @ $Author$
 * @ $Revision$
 */
public class RepeatSendDocumentJob extends BaseJob implements StatefulJob
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_SCHEDULER);

	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private final PaymentStateMachineService stateMachineService = new PaymentStateMachineService();

	private static final String[] PAYMENT_STATE = {"DISPATCHED", "UNKNOW"}; // ������� ��� ���������

	private static final String[] REPEAT_IQW_FORM_NAMES = new String[]{
			FormConstants.INTERNAL_PAYMENT_FORM,
			FormConstants.RUR_PAYMENT_FORM,
			FormConstants.NEW_RUR_PAYMENT,
			FormConstants.CONVERT_CURRENCY_PAYMENT_FORM,
			FormConstants.SERVICE_PAYMENT_FORM,
			FormConstants.EXTERNAL_PROVIDER_PAYMENT_FORM,
			FormConstants.FNS_PAYMENT_FORM,
			FormConstants.AIRLINE_RESERVATION_PAYMENT_FORM};

	public RepeatSendDocumentJob() throws JobExecutionException
	{
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			JobRefreshConfig jobRefreshConfig = ConfigFactory.getConfig(JobRefreshConfig.class);
			int listLimit = jobRefreshConfig.getMaxRowsFromFetch();

			int seconds = jobRefreshConfig.getDocumentUpdateWaitingTime();
			Calendar fromDate = Calendar.getInstance();
			fromDate.add(Calendar.SECOND, - seconds);    //������ ��������=======
			int firstResult = 0;
			List<Long> selectProviderIds = jobRefreshConfig.getProviderNumbersExclude();

			// ��������������� ����������� ���, ������ �� �� ����
			if(CollectionUtils.isEmpty(selectProviderIds))
				return;

			while(true)
			{				//�������� ������� �������, ���������� ���������� � listLimit
				List<BusinessDocument> documents = businessDocumentService.findPaymentsByProviderIds(selectProviderIds, PAYMENT_STATE, REPEAT_IQW_FORM_NAMES, fromDate, firstResult, listLimit);

				// ��� ���������� �������
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
					}
				}
				// ��������� �� �������� �������� ������������(�������� ��� ������ ��������)
				firstResult += documents.size();
			}
		}
		catch (BusinessException e)
		{
			throw new JobExecutionException(e);
		}
	}


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
