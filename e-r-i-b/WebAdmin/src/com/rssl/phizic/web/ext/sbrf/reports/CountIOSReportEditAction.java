package com.rssl.phizic.web.ext.sbrf.reports;

import com.rssl.phizic.operations.ext.sbrf.reports.CountIOSReportOperation;
import com.rssl.phizic.operations.ext.sbrf.reports.ReportEditOperation;

import java.util.List;

/**
 * @author mihaylov
 * @ created 21.11.2011
 * @ $Author$
 * @ $Revision$
 */

public class CountIOSReportEditAction extends ReportsEditAction
{

	public ReportEditOperation getReportOperation() throws Exception
    {
	    return createOperation(CountIOSReportOperation.class);
    }

	/**
	 * ��� ������� ������ ��� ������ �������������, ������� ���������� ������ false
	 * @return false - ������
	 */
	protected boolean checkSelectedIds(List<String> selectedIds)
	{
		return false;
	}


}
