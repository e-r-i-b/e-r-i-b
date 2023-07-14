package com.rssl.phizic.web.ext.sbrf.reports.it;

import com.rssl.phizic.web.ext.sbrf.reports.ReportsListAction;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author Mescheryakova
 * @ created 24.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class ITReportsListAction extends ReportsListAction
{
	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return "listITReports";
	}	
}
