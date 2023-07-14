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
 * �������� ��������� �������������� ��������� ������� �� ������� � ������ �������� ������ �� ������
 */

public class ArchivingExceptionOperation extends EditPropertiesOperation
{
	/**
	 * @return ���� � ������� �� �������
	 */
	public String getArchivePath() throws Exception
	{
		return ConfigFactory.getConfig(ExceptionSettingsService.class).getArchivePath();
	}

	/**
	 * @param unloadingDate �����, �� ������� ����� ����������� �����
	 * @param exceptionEntryType ��� ������, ������� ����� ��������� � �����
	 * @return ������ �� ������ ��������� ���� (exceptionEntryType) �� ��������� ����� (unloadingDate)
	 */
	public byte[] getReport(Calendar unloadingDate, ExceptionEntryType exceptionEntryType) throws Exception
	{
		switch (exceptionEntryType)
		{
			case internal: return new InnerExceptionReportBuilder(unloadingDate).build();
			case external: return new ExternalExceptionReportBuilder(unloadingDate).build();
			default: throw new IllegalArgumentException("�� ��������� ��� ������");
		}
	}
}
