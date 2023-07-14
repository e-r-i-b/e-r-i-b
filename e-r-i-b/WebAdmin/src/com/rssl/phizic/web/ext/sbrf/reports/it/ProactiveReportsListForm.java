package com.rssl.phizic.web.ext.sbrf.reports.it;

import com.rssl.phizic.web.ext.sbrf.reports.ReportsListForm;

/**
 * @author lukina
 * @ created 11.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class ProactiveReportsListForm    extends ReportsListForm
{
	private boolean allBank;  // по всему банку или в разрезе ТБ строится отчет

	public boolean isAllBank()
	{
		return allBank;
	}

	public void setAllBank(boolean allBank)
	{
		this.allBank = allBank;
	}
}
