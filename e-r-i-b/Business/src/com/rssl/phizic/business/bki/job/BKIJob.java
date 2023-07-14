package com.rssl.phizic.business.bki.job;

import com.rssl.phizic.business.bki.CreditProfileService;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.annotation.NonThreadSafe;
import com.rssl.phizic.common.types.annotation.Statefull;
import com.rssl.phizic.config.loanreport.BKIJobReport;
import com.rssl.phizic.config.loanreport.BKIJobReportStorage;
import com.rssl.phizic.config.loanreport.CreditBureauConfig;
import com.rssl.phizic.config.loanreport.CreditBureauConfigStorage;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bki.CreditBureauService;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.StatefulJob;

import java.util.Calendar;
import java.util.List;

/**
 * ���� ��������, �� ������� ����� ��������� ������ �� �������� ��������� �������.
 * ���������� ������ �� ���� �������� � ���.
 *
 * @author Puzikov
 * @ created 14.10.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���� ��������, �� ������� ����� ��������� ������ �� �������� ��������� �������.
 * ���������� ������ �� ���� �������� � ���.
 */
@Statefull
@NonThreadSafe
public class BKIJob extends BaseJob implements InterruptableJob, StatefulJob
{
	/**
	 * ����������� �� ���-�� ��������, �������������� ����� ����������� �����
	 */
	private static final int MAX_CLIENTS_PER_JOB = 100000;

	private final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);

	private final CreditProfileService creditProfileService = new CreditProfileService();

	private final CreditBureauConfigStorage creditBureauConfigStorage = new CreditBureauConfigStorage();

	private final BKIJobReportStorage jobReportStorage = new BKIJobReportStorage();

	private CreditBureauConfig bkiConfig;

	private BKIJobSchedule jobSchedule;

	private volatile boolean jobInterrupted = false;

	/////////////////////////////////////////////////////////////////////////////////////

	public void interrupt()
	{
		jobInterrupted = true;
	}

	/**
	 * ��������� ��������� � ���������� ����� ������� �� ����
	 */
	private void reloadJobConfig()
	{
		bkiConfig = creditBureauConfigStorage.loadConfig();
		jobSchedule = new BKIJobSchedule(bkiConfig);
	}

	/**
	 * ���������� ������� ���������� �����
	 * @return ������ ����� �� ������ ������
	 */
	private BKIJobActivity getJobActivity()
	{
		if (jobInterrupted)
		{
			log.warn("���� ��� �������");
			return BKIJobActivity.INTERRUPTED;
		}

		if (!bkiConfig.jobEnabled)
		{
			log.debug("���� ��� ��������");
			return BKIJobActivity.DISABLED;
		}

		if (bkiConfig.jobSuspended)
		{
			log.info("���� ��� �������������");
			return BKIJobActivity.SUSPENDED;
		}

		Calendar now = Calendar.getInstance();

		if (now.before(jobSchedule.startTime))
		{
			log.debug("���� ��� ������� ������ �������� ���");
			return BKIJobActivity.WAITING;
		}

		if (now.after(jobSchedule.endTime))
		{
			log.debug("���� ��� �������� ������� ����");
			return BKIJobActivity.WAITING;
		}

		return BKIJobActivity.WORKING;
	}

	@Override
	protected void executeJob(JobExecutionContext context)
	{
		try
		{
			Calendar startTime = Calendar.getInstance();

			CreditBureauService creditBureauService = GateSingleton.getFactory().service(CreditBureauService.class);

			// 0.5 �������� ����������: �� ���� �� ��� ����������?
			reloadJobConfig();
			if (getJobActivity() != BKIJobActivity.WORKING)
				return;

			int totalProfilesCreated = 0;
			int totalRequestsSended = 0;
			while (true)
			{
				// ������ "���� �������� ������ �� ������ ��������"
				boolean progress = false;

				// 1. ������ ����� ��������� ��������
				int newProfiles = creditProfileService.createCreditProfiles(bkiConfig.jobPackSize);
				totalProfilesCreated += newProfiles;
				progress = progress || newProfiles > 0;

				// 1.5 �������� ��������� �������� - ���� �� ������� => ��������, ����� �� ���������� ������
				reloadJobConfig();
				if (getJobActivity() != BKIJobActivity.WORKING)
					break;

				// 2. ��������� ����� ��������, �� ������� ���� ������� ������ � ���
				List<ActivePerson> persons = creditProfileService.queryBKIUncheckedPersons(jobSchedule.startDay, bkiConfig.jobPackSize);

				// 2.5 �������� ����� �������� - ���� �� ������� => ��������, ����� �� ���������� ������
				reloadJobConfig();
				if (getJobActivity() != BKIJobActivity.WORKING)
					break;

				// 3. ���������� ������� � ���
				for (ActivePerson person : persons)
				{
					creditBureauService.sendCheckCreditHistory(person.asClient());
					creditProfileService.updateLastCheckRequestTime(person.getId(), Calendar.getInstance());
					totalRequestsSended++;
					progress = true;
				}

				// 4. ���������� �� ��������� �����
				saveJobReport(startTime, Calendar.getInstance());

				// 4.4 ��������� �� ���������: ���� ������ ������ �� ������� ��� "���� ������", ��������� ����
				boolean enough = !progress;
				enough = enough || totalProfilesCreated > MAX_CLIENTS_PER_JOB;
				enough = enough || totalRequestsSended > MAX_CLIENTS_PER_JOB;
				if (enough)
					break;

				// 4.5 �������� ����������: �� ���� �� ��� ���������?
				reloadJobConfig();
				if (getJobActivity() != BKIJobActivity.WORKING)
					break;
			}

			log.debug("������� " + totalProfilesCreated + " ��������� ��������");
			log.debug("���������� " + totalRequestsSended + " �������� �� �������� ������� ��");
		}
		catch (Exception e)
		{
			log.error("���� �� ���������� ����� ���", e);
		}
	}

	/**
	 * ��������� ���������� ������ � ������ �����
	 * @param jobStartTime - ����� ������ ������ ����� (never null)
	 * @param jobEndTime - ����� ����� ������ ����� (never null)
	 */
	private void saveJobReport(Calendar jobStartTime, Calendar jobEndTime)
	{
		try
		{
			// �2.4. ��������� ���� ���������� ������� � ������ ������ �����.
			// ���������� ��������� � ������ ���������� ���� (�. 2.4).
			// ���� ���������� ������� � ��� ����� ������ ���������� �����.
			// ������ ������� ������ ���������� ���:
			// + ���������� ��������, ���� ��� ���� � ����� ������� ��� [��������] ������������ ������� �����
			// + ����� ����� ������ ������ �����.
			// ��������� ������� ������ � ��� ����� ��������� ������ �����.
			BKIJobReport oldReport = jobReportStorage.loadReport();

			Calendar jobPeriodBegin = null;
			if (oldReport != null)
			{
				if (oldReport.lastPeriodBegin != null && oldReport.lastPeriodBegin.after(jobSchedule.startDay))
					jobPeriodBegin = oldReport.lastPeriodBegin;
			}
			if (jobPeriodBegin == null)
				jobPeriodBegin = jobStartTime;

			BKIJobReport newReport = new BKIJobReport();
			newReport.lastStartTime = jobStartTime;
			newReport.lastPeriodBegin = jobPeriodBegin;
			newReport.lastPeriodEnd = jobEndTime;

			jobReportStorage.saveReport(newReport);
		}
		catch (Exception e)
		{
			log.error("���� �� ���������� ������ ����� ���", e);
		}
	}
}
