package com.rssl.phizic.operations.config.exceptions;

import com.rssl.phizic.business.exception.ExceptionEntryType;
import com.rssl.phizic.business.exception.ExceptionSettingsService;
import com.rssl.phizic.business.exception.report.ExternalExceptionReportBuilder;
import com.rssl.phizic.business.exception.report.InnerExceptionReportBuilder;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.operations.config.EditPropertiesOperation;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 24.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * операци€ настройки автоматической архивации отчетов об ошибках и ручной выгрузки отчета за период
 */

public class ArchivingExceptionOperation extends EditPropertiesOperation
{
	/**
	 * @return путь к отчетам по ошибкам
	 */
	public String getArchivePath() throws Exception
	{
		return ConfigFactory.getConfig(ExceptionSettingsService.class).getArchivePath();
	}

	/**
	 * @param unloadingDate мес€ц, за который нужно формаровать отчет
	 * @param exceptionEntryType тип ошибок, которые нужно поместить в отчет
	 * @return данные по отчету заданного типа (exceptionEntryType) за указанный мес€ц (unloadingDate)
	 */
	public byte[] getReport(Calendar unloadingDate, ExceptionEntryType exceptionEntryType) throws Exception
	{
		switch (exceptionEntryType)
		{
			case internal: return new InnerExceptionReportBuilder(unloadingDate).build();
			case external: return new ExternalExceptionReportBuilder(unloadingDate).build();
			default: throw new IllegalArgumentException("Ќе определен тип ошибки");
		}
	}
}
