package com.rssl.phizic.business.exception.report;

import com.rssl.phizic.test.BusinessTestCaseBase;

import java.io.FileOutputStream;
import java.util.Calendar;

/**
 * @author akrenev
 * @ created 23.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * ���� �� �������� �������
 */

public class ReportBuilderTest extends BusinessTestCaseBase
{
	private void makeReport(ReportBuilderBase builderBase) throws Exception
	{
		FileOutputStream stream = new FileOutputStream("d:\\temp\\report\\" + builderBase.getClass().getSimpleName() + ".csv");
		try
		{
			stream.write(builderBase.build());
			stream.flush();
		}
		finally
		{
			stream.close();
		}
	}

	/**
	 * �������� ������
	 * @throws Exception
	 */
	public void testBuildReport() throws Exception
	{
		makeReport(new InnerExceptionReportBuilder(Calendar.getInstance()));
		makeReport(new ExternalExceptionReportBuilder(Calendar.getInstance()));
	}
}
