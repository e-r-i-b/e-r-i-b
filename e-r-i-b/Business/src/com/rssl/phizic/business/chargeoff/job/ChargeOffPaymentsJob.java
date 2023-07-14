package com.rssl.phizic.business.chargeoff.job;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.chargeoff.ChargeOffLog;
import com.rssl.phizic.business.chargeoff.ChargeOffLogService;
import com.rssl.phizic.business.chargeoff.ChargeOffPayService;
import com.rssl.phizic.business.payments.job.JobRefreshConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.util.ArrayList;
import java.util.List;

/**
 * @author egorova
 * @ created 19.01.2009
 * @ $Author$
 * @ $Revision$
 */
public class ChargeOffPaymentsJob extends BaseJob implements StatefulJob
{
	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);
		ChargeOffLogService chargeOfflogService = new ChargeOffLogService();
		ChargeOffPayService chargeOffPayService = new ChargeOffPayService();

		log.info("�������� ������� ����� �� ����������� ������������.");
//�������� ��� ����� �� ������.

		List<ChargeOffLog> debts;

		List<Long> processed = new ArrayList<Long>(0);

		while (true)
		{
			int maxRows = ConfigFactory.getConfig(JobRefreshConfig.class).getMaxRowsFromFetch();
			try
			{
				debts = chargeOfflogService.getAllDebtsForJob(processed, maxRows);
			}
			catch(BusinessException e)
			{
				log.error("������ ��������� ������ ������", e);
				debts = new ArrayList<ChargeOffLog>();
			}

			if (debts.isEmpty())
				break;

			for (ChargeOffLog debt : debts)
			{
				try
				{
					chargeOffPayService.payMonthlyCharge(debt);
				}
				catch(BusinessException e)
				{
					processed.add(debt.getId());
					log.error("������ ��� �������� ����������� ����� �������" +
							debt.getLogin().getId() != null ? " � login.id = " + debt.getLogin().getId() : "", e);
				}
				catch(BusinessLogicException e)
				{
					processed.add(debt.getId());
					log.error("������ ��� �������� ����������� ����� �������" +
							debt.getLogin().getId() != null ? " � login.id = " + debt.getLogin().getId() : "", e);
				}
			}
		}
//�������� ��� ��������� �������.
		List<ChargeOffLog> preparedPayments;

		processed = new ArrayList<Long>(0);

		while (true)
		{
			int maxRows = ConfigFactory.getConfig(JobRefreshConfig.class).getMaxRowsFromFetch();
			try
			{
				preparedPayments = chargeOfflogService.getAppropriativePaymentsForJob(processed, maxRows);
			}
			catch(BusinessException e)
			{
				log.error("������ ��������� ������ ��������� ��������", e);
				preparedPayments = new ArrayList<ChargeOffLog>();
			}

			if (preparedPayments.isEmpty())
				break;

			for (ChargeOffLog preparedPayment : preparedPayments)
			{
				try
				{
					chargeOffPayService.payMonthlyCharge(preparedPayment);
				}
				catch(BusinessException e)
				{
					processed.add(preparedPayment.getId());
					throw new JobExecutionException("������ ��� �������� ����������� ����� �������" +
							preparedPayment.getLogin().getId() != null ? " � login.id = " +
							preparedPayment.getLogin().getId() : "", e);
				}
				catch(BusinessLogicException e)
				{
					processed.add(preparedPayment.getId());
					throw new JobExecutionException("������ ��� �������� ����������� ����� �������" +
							preparedPayment.getLogin().getId() != null ? " � login.id = " +
							preparedPayment.getLogin().getId() : "", e);
				}
			}
		}
		log.info("�������� ����� ���������.");
	}
}
