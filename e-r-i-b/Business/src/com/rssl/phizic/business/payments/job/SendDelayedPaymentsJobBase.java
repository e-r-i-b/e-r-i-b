package com.rssl.phizic.business.payments.job;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.payments.BusinessOfflineDocumentException;
import com.rssl.phizic.business.payments.BusinessSendTimeOutException;
import com.rssl.phizic.business.payments.BusinessTimeOutException;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.DateHelper;
import org.quartz.JobExecutionException;

import java.util.Calendar;

/**
 * ������� ���� �������� ���������� ��������.
 * @author vagin
 * @ created 26.03.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class SendDelayedPaymentsJobBase extends ReworkingPaymentJob
{
	protected SendDelayedPaymentsJobBase() throws JobExecutionException{}

	private Integer getNumberOfHourAfterDocumentIsExpired()
	{
		return ConfigFactory.getConfig(JobRefreshConfig.class).getNumberOfHourAfterDocumentIsExpired(getJobSimpleName());
	}

	private boolean checkDate(BusinessDocument document)
	{
		if (document == null || document.getOperationDate() == null)
			return false;

		Integer actualHourInterval = getNumberOfHourAfterDocumentIsExpired();
		if (actualHourInterval == null)
			return false;

		return document.getOperationDate().before(DateHelper.getPreviousHours(actualHourInterval));
	}

	protected void dispatch(BusinessDocument document, DocumentEvent nextEvent, String description) throws Exception
	{
		StateMachineExecutor executor = getStateMachineExecutor(document);

		if (checkDate(document))
		{
			log.debug(DateHelper.formatDateToStringWithSlash2(Calendar.getInstance()) + ": ��������� � ������ '��������' (REFUSE)" +
					" �������� id = " + document.getId() +
					", ���� ��������� = " + DateHelper.formatDateToStringWithSlash2(document.getOperationDate()) +
					", ������������� ����� ������������ ���������� = " + getNumberOfHourAfterDocumentIsExpired() + " �.");

			process(executor, new ObjectEvent(DocumentEvent.REFUSE, JOB_EVENT_TYPE, "�������������"), document);
			return;
		}

		try
		{
			log.debug("�������� �������� " + document.getId() + " �� ������� �������.");
			process(executor, new ObjectEvent(nextEvent, JOB_EVENT_TYPE, description), document);
		}
		catch (BusinessSendTimeOutException e)
		{
			//��� ������ ��� ���������� ������� ��� �������� ��������� � unknow
			log.error("��� ��������� ������� id = " + document.getId() + " �������� �������� time-out. ��������� �������� � ������ 'UNKNOW'", e);
			process(executor, new ObjectEvent(DocumentEvent.DOUNKNOW, JOB_EVENT_TYPE), document);
		}
		catch (BusinessTimeOutException e)
		{
			//��� ������ ��� ���������� ������� ��� �������� ��������� � unknow
			log.error("��� ��������� ������� id = " + document.getId() + " �������� �������� time-out. ��������� �������� � ������ 'UNKNOW'", e);
			process(executor, new ObjectEvent(DocumentEvent.DOUNKNOW, JOB_EVENT_TYPE), document);
		}
		catch (BusinessOfflineDocumentException ignore)
		{
			//������ �� ������, �������� �������� � ��������� ���, ����� �������� ��������������� ������.
			addToProcessed(document);
		}
		catch (Exception e)
		{
			//��� ������ ������� � ��������
			log.error("������ ��������� ��������� " + document.getId() + ". ��������� � ������ '��������'.", e);
			DocumentHelper.decrementPromoCode(document);
			process(executor, new ObjectEvent(DocumentEvent.REFUSE, JOB_EVENT_TYPE, "�������������"), document);
		}
	}
}
