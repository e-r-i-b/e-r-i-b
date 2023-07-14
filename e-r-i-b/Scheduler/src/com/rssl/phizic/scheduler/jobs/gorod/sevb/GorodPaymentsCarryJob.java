package com.rssl.phizic.scheduler.jobs.gorod.sevb;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.documents.DocumentService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.documents.UpdateDocumentService;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.systems.AccountPaymentSystemPayment;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.LogThreadContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.quartz.*;

import java.io.InvalidClassException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 22.02.2008
 * @ $Author$
 * @ $Revision$
 */

/***
 * ���� ��� �������� � ����������(�� ����������) �������� � ������
 */
public class GorodPaymentsCarryJob extends BaseJob implements StatefulJob, InterruptableJob
{
	public static final String OK_ERROR_CODE = "0";
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);
	private volatile boolean  isInterrupt = false;

	/**
	 * ������������� �����
	 */
	public GorodPaymentsCarryJob() throws JobExecutionException, InvalidClassException
	{
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			//������� �������������� ��� ��������� � ������, �� �� ����������� ��������� �������
//���������� ��-�� ������� � �������������� ��������
//������������, ��� ������� �������� ��� � 40 �, ������� ������������� � ����������� ������� � ������ ���
//			prepareGorodPayments();
			//������������ � ������ ��� ��������� �������, ����������� � �������
			confirmGorodPayments();
		}
		catch (Exception e)
		{
			throw new JobExecutionException(e);
		}
	}

	/**
	 * ����������� ��������� ������ � ������ ����� �������� ��� � �������
	 */
	private void confirmGorodPayments() throws GateLogicException, GateException
	{
		UpdateDocumentService updateService = GateSingleton.getFactory().service(UpdateDocumentService.class);
		List<GateDocument> list = updateService.findUnRegisteredPayments(new State("PARTLY_EXECUTED", "������"));
		DocumentService documentService = GateSingleton.getFactory().service(DocumentService.class);
		for (GateDocument doc : list)
		{
			// ���� ��������� �����������
			if(isInterrupt)
			{
				break;
			}

			if (!(doc instanceof SynchronizableDocument))
			{
				continue;
			}
			SynchronizableDocument payment = (SynchronizableDocument) doc;
			if (payment.getType() != AccountPaymentSystemPayment.class)
			{
				continue;
			}
			try
			{
				LogThreadContext.setLoginId(doc.getInternalOwnerId());
				log.debug("������������ � ������ ������ " + doc.getId());
				documentService.confirm(doc);
			}
			catch (InactiveExternalSystemException e)
			{
				//������� ������� � ������ ������ ���������, ��������� ������� ����������
				log.error("������� ������� ���������. �������������� ������ id = " + doc.getId(), e);
			}
			catch (GateLogicException e)
			{
				log.info("������ ��� ������������� ������� � ������. ���������������� ������ id=" + doc.getId(), e);
				Map<String, Object> additionalFields = new HashMap<String, Object>();
				additionalFields.put(DocumentCommand.ERROR_TEXT, e.getMessage());
				updateService.update(payment, new DocumentCommand(DocumentEvent.ERROR, additionalFields));
			}
			catch (GateException e)
			{
				log.info("������ ��� ������������� ������� � ������. ���������������� ������ id=" + doc.getId(), e);
				Map<String, Object> additionalFields = new HashMap<String, Object>();
				additionalFields.put(DocumentCommand.ERROR_TEXT, e.getMessage());
				updateService.update(payment, new DocumentCommand(DocumentEvent.ERROR, additionalFields));
			}
			catch (Exception e)
			{
				log.error("������ ������������� ������� id=" + doc.getId(), e);
			}
			finally
			{
				LogThreadContext.setLoginId(null);
			}
		}
	}

	/**
	 * �������� ������ � ������, �� ��� ������ ���� �� ��� ���� ��������� ������
	 * ����� ����� ��������� ��������� ������� � ������, ���� ��� ���� ��������� ���������� �� ������
	 */
	private void prepareGorodPayments() throws GateLogicException, GateException
	{
		UpdateDocumentService updateService = GateSingleton.getFactory().service(UpdateDocumentService.class);
		DocumentService documentService = GateSingleton.getFactory().service(DocumentService.class);
		List<GateDocument> list = updateService.findUnRegisteredPayments(new State("DISPATCHED", "������"));
		for (GateDocument doc : list)
		{
			if (!(doc instanceof SynchronizableDocument))
			{
				continue;
			}
			SynchronizableDocument payment = (SynchronizableDocument) doc;
			if (payment.getType() != AccountPaymentSystemPayment.class)
			{
				continue;
			}
			try
			{
				BusinessDocumentOwner documentOwner = ((BusinessDocument) doc).getOwner();
				if (!documentOwner.isGuest())
					LogThreadContext.setLoginId(documentOwner.getLogin().getId());
				log.debug("��������� � ������ ������ " + doc.getId());
				documentService.send(doc);
				//��������� � ������� ���������� ����
				log.debug("��������� � ������� ����������� � ������ ������ " + doc.getId());
				updateService.update(payment);
			}
			catch (Exception e)
			{
				log.error("������ ��� ���������� ������� id=" + doc.getId(), e);
			}
			finally
			{
				LogThreadContext.setLoginId(null);
			}
		}
	}

	public void interrupt() throws UnableToInterruptJobException
	{
		isInterrupt = true;
	}
}