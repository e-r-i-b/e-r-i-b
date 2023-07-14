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
 * Ищет клиентов, по которым нужно отправить запрос на проверку кредитной истории.
 * Отправляет запрос по этим клиентам в БКИ.
 *
 * @author Puzikov
 * @ created 14.10.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Ищет клиентов, по которым нужно отправить запрос на проверку кредитной истории.
 * Отправляет запрос по этим клиентам в БКИ.
 */
@Statefull
@NonThreadSafe
public class BKIJob extends BaseJob implements InterruptableJob, StatefulJob
{
	/**
	 * Ограничение по кол-ву клиентов, обрабатываемых одним экземпляром джоба
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
	 * Обновляет настройки и расписание джоба данными из базы
	 */
	private void reloadJobConfig()
	{
		bkiConfig = creditBureauConfigStorage.loadConfig();
		jobSchedule = new BKIJobSchedule(bkiConfig);
	}

	/**
	 * Возвращает текущее расписание джоба
	 * @return задача джоба на момент вызова
	 */
	private BKIJobActivity getJobActivity()
	{
		if (jobInterrupted)
		{
			log.warn("Джоб БКИ прерван");
			return BKIJobActivity.INTERRUPTED;
		}

		if (!bkiConfig.jobEnabled)
		{
			log.debug("Джоб БКИ выключен");
			return BKIJobActivity.DISABLED;
		}

		if (bkiConfig.jobSuspended)
		{
			log.info("Джоб БКИ приостановлен");
			return BKIJobActivity.SUSPENDED;
		}

		Calendar now = Calendar.getInstance();

		if (now.before(jobSchedule.startTime))
		{
			log.debug("Джоб БКИ ожидает начала рабочего дня");
			return BKIJobActivity.WAITING;
		}

		if (now.after(jobSchedule.endTime))
		{
			log.debug("Джоб БКИ завершил рабочий день");
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

			// 0.5 Проверим расписание: не пора ли уже поработать?
			reloadJobConfig();
			if (getJobActivity() != BKIJobActivity.WORKING)
				return;

			int totalProfilesCreated = 0;
			int totalRequestsSended = 0;
			while (true)
			{
				// Флажок "есть полезная работа на данной итерации"
				boolean progress = false;

				// 1. Создаём пачку кредитных профилей
				int newProfiles = creditProfileService.createCreditProfiles(bkiConfig.jobPackSize);
				totalProfilesCreated += newProfiles;
				progress = progress || newProfiles > 0;

				// 1.5 Создание кредитных профилей - дело не быстрое => проверим, можно ли продолжить работу
				reloadJobConfig();
				if (getJobActivity() != BKIJobActivity.WORKING)
					break;

				// 2. Выгружаем пачку клиентов, по которым надо сделать запрос в БКИ
				List<ActivePerson> persons = creditProfileService.queryBKIUncheckedPersons(jobSchedule.startDay, bkiConfig.jobPackSize);

				// 2.5 Загрузка пачки клиентов - дело не быстрое => проверим, можно ли продолжить работу
				reloadJobConfig();
				if (getJobActivity() != BKIJobActivity.WORKING)
					break;

				// 3. Отправляем запросы в БКИ
				for (ActivePerson person : persons)
				{
					creditBureauService.sendCheckCreditHistory(person.asClient());
					creditProfileService.updateLastCheckRequestTime(person.getId(), Calendar.getInstance());
					totalRequestsSended++;
					progress = true;
				}

				// 4. Отчитаемся по обработке пачки
				saveJobReport(startTime, Calendar.getInstance());

				// 4.4 Посмотрим на результат: если ничего нового не сделано или "пока хватит", завершаем цикл
				boolean enough = !progress;
				enough = enough || totalProfilesCreated > MAX_CLIENTS_PER_JOB;
				enough = enough || totalRequestsSended > MAX_CLIENTS_PER_JOB;
				if (enough)
					break;

				// 4.5 Проверим расписание: не пора ли уже отдохнуть?
				reloadJobConfig();
				if (getJobActivity() != BKIJobActivity.WORKING)
					break;
			}

			log.debug("Создано " + totalProfilesCreated + " кредитных профилей");
			log.debug("Отправлено " + totalRequestsSended + " запросов на проверку наличия КИ");
		}
		catch (Exception e)
		{
			log.error("Сбой на выполнении джоба БКИ", e);
		}
	}

	/**
	 * Сохранить результаты работы в журнал джоба
	 * @param jobStartTime - время начало работы джоба (never null)
	 * @param jobEndTime - время конца работы джоба (never null)
	 */
	private void saveJobReport(Calendar jobStartTime, Calendar jobEndTime)
	{
		try
		{
			// Ф2.4. Обновляем дату последнего запуска и период работы джоба.
			// Результаты сохраняем в конфиг Кредитного Бюро (п. 2.4).
			// Дата последнего запуска – это время начало выполнения джоба.
			// Начало периода работы определяем так:
			// + предыдущее значение, если оно есть и позже первого дня [текущего] ежемесячного периода джоба
			// + иначе время начала работы джоба.
			// Окончание периода работы – это время окончания работы джоба.
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
			log.error("Сбой на сохранении отчёта джоба БКИ", e);
		}
	}
}
