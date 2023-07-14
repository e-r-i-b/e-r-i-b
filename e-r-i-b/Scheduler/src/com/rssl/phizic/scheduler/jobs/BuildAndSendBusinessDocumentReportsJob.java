package com.rssl.phizic.scheduler.jobs;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mail.MailHelper;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.reports.BusinessReportConfig;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.monitoring.BusinessReportHelper;
import com.rssl.phizic.logging.monitoring.MonitoringReportService;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.MailFormat;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Джоб лдя формирования и рассылки отчетов по бизнес операциям.
 *
 * @author bogdanov
 * @ created 11.03.15
 * @ $Author$
 * @ $Revision$
 */

public class BuildAndSendBusinessDocumentReportsJob extends BaseJob implements StatefulJob
{
	private static final MonitoringReportService MONITORING_REPORT_SERVICE = new MonitoringReportService(null);
	private Log log;

	@Override
	protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		log = PhizICLogFactory.getLog(LogModule.Scheduler);
		BusinessReportConfig config = ConfigFactory.getConfig(BusinessReportConfig.class);
		if (!config.isNeedSendMail(Calendar.getInstance()))
		    //Время выгрузки не наступило.
			return;

		try
		{
			if (!config.isSendOn())
			{
				//Отправка отчетов запрещена.
				return;
			}

			byte[] report = createReport();
			sendReport(report);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(LogModule.Scheduler).error(e);
		}
	}

	private byte[] createReport() throws BusinessException
	{
		Map<String,Object> paramsMap = BusinessReportHelper.getParamsFromConfig();
		try
		{
			return MONITORING_REPORT_SERVICE.buildReport(paramsMap);
		}
		catch (LogicException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
	}

	private void sendReport(byte[] report)
	{
		try
		{
			Map<String, byte[]> attach = new HashMap<String, byte[]>();
			attach.put("monitoring_report_" + new SimpleDateFormat("dd.MM.yyyy").format(Calendar.getInstance().getTime()) + ".xls|application/vnd.ms-excel", report);
			BusinessReportConfig config = ConfigFactory.getConfig(BusinessReportConfig.class);
			MailHelper.sendEMail(config.getMailTheme(), new StringReader(config.getMailText()), config.getMailReceivers(), MailFormat.PLAIN_TEXT, MailHelper.getCurrentDateFields(), attach);
		}
		catch (IOException e)
		{
			log.error("Не удалось отправить сообщение о мониторинге бизнес операций", e);
		}
		catch (BusinessException e)
		{
			log.error("Не удалось отправить сообщение о мониторинге бизнес операций", e);
		}
	}
}
