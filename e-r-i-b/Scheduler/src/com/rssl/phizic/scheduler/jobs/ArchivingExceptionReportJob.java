package com.rssl.phizic.scheduler.jobs;

import com.rssl.phizic.business.exception.ExceptionSettingsService;
import com.rssl.phizic.business.exception.report.ExternalExceptionReportBuilder;
import com.rssl.phizic.business.exception.report.InnerExceptionReportBuilder;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.files.FileHelper;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.StatefulJob;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author akrenev
 * @ created 25.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Джоб архивации отчетов по ошибкам
 */

public class ArchivingExceptionReportJob extends BaseJob implements StatefulJob
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_SCHEDULER);
	private static final String INNER_EXCEPTION_REPORT_NAME_PREFIX = "Report_System_error_";
	private static final String EXTERNAL_EXCEPTION_REPORT_NAME_PREFIX = "Report_external_error_";
	private static final String DATE_FORMAT = "MM.yyyy";
	private static final String REPORT_FILE_TYPE = ".csv";

	/**
	 * конструктор 
	 * @throws JobExecutionException
	 */
	public ArchivingExceptionReportJob() throws JobExecutionException
	{
	}

	private void writeToFile(byte[] data, String fileNamePrefix, String filePath, String date)
	{
		String fileName = fileNamePrefix.concat(date).concat(REPORT_FILE_TYPE);
		try
		{
			FileHelper.write(new ByteArrayInputStream(data), FileHelper.getCurrentFilePath(filePath, fileName));
		}
		catch (IOException e)
		{
			log.error("Ошибка записи отчета в файл " + fileName, e);
		}
	}

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			ExceptionSettingsService service = ConfigFactory.getConfig(ExceptionSettingsService.class);
			boolean useArchiving = service.isUseArchiving();
			if (!useArchiving)
				return;

			String filePath = service.getArchivePath();
			Calendar unloadingDate = DateHelper.getPreviousMonth();
			String unloadingDateString = new SimpleDateFormat(DATE_FORMAT).format(unloadingDate.getTime());
			writeToFile(new InnerExceptionReportBuilder(unloadingDate).build(), INNER_EXCEPTION_REPORT_NAME_PREFIX, filePath, unloadingDateString);
			writeToFile(new ExternalExceptionReportBuilder(unloadingDate).build(), EXTERNAL_EXCEPTION_REPORT_NAME_PREFIX, filePath, unloadingDateString);
		}
		catch (Exception e)
		{
			log.error("Ошибка архивации отчета об ошибках.", e);
		}
	}
}
