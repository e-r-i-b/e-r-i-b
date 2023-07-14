package com.rssl.phizic.business.ext.sbrf.reports;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.Calendar;

/**
 * @author Mescheryakova
 * @ created 05.10.2010
 * @ $Author$
 * @ $Revision$
 */

public class ReportTest extends BusinessTestCaseBase
{
	public void testGetMaxMinUserLog() throws BusinessException
	{
		ReportServiceSBRF reportService = new ReportServiceSBRF();
		Object[] minMax = reportService.getPeriodUserLog("");
		Calendar minDate = (Calendar) minMax[0];
		Calendar maxDate = (Calendar) minMax[1];

		System.out.println("mindate: " + minDate.toString());
		System.out.println("maxdate: " + maxDate.toString());
	}
}
