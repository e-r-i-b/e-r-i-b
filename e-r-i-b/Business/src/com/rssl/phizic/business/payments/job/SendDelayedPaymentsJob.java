package com.rssl.phizic.business.payments.job;

import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.receptiontimes.ReceptionTimeService;
import com.rssl.phizic.business.receptiontimes.TimeMatching;
import org.quartz.JobExecutionException;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ���� ������� ��� ������� � ���������� ���������� � ��������� � ��������� ��������������, ���� ���-���� ����� ������� ����� ������
 *
 * @author egorova
 * @ created 23.09.2009
 * @ $Author$
 * @ $Revision$
 */
public class SendDelayedPaymentsJob extends SendDelayedPaymentsJobBase
{
	private static final AtomicInteger counter = new AtomicInteger(0);//������� �������� ���������� ������������ ���������� ������.

	private static final ReceptionTimeService receptionTimeService = new ReceptionTimeService();
	private static final String DELAYED_DISPATCH_STATE = "DELAYED_DISPATCH";

	/**
	 * ����������� �����
	 * @throws JobExecutionException
	 */
	public SendDelayedPaymentsJob() throws JobExecutionException
	{
		super();
	}

	protected String getStateCode()
	{
		return DELAYED_DISPATCH_STATE;
	}

	protected AtomicInteger getJobCounter()
	{
		return counter;
	}

	@Override
	protected boolean beforeFireEventCheckDocument(BusinessDocument document) throws BusinessException
	{
		boolean isTime = true;
		try
		{
			getLog().trace("���������� ����� ������ ��� ��������� " + document.getId());
			isTime = receptionTimeService.getWorkTimeInfo(document).isWorkTimeNow() == TimeMatching.RIGHT_NOW;
		}
		catch (Exception e)
		{
			//������ ��������� ������� ������ ��������� �� ������ ���������� �� ������� �������
			getLog().error("������ ��������� ������� ������ ��������� " + document.getId() + ". ���������� ��������.", e);
			addToProcessed(document);
			return false;
		}

		//���� �� �������� � �� ������� "�������" ����������
		if (isTime)
			return true;
		
		getLog().debug("����� ������ ��� ��������� " + document.getId() + " ��� �� �������. ���������� ��������.");
		addToProcessed(document);
		return false;
	}
}
