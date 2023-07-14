package com.rssl.phizic.web.ext.sbrf.reports.it;

import com.rssl.phizic.web.ext.sbrf.reports.ReportsListAction;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author gladishev
 * @ created 28.06.2011
 * @ $Author$
 * @ $Revision$
 */
public class SystemIdleReportsListAction extends ReportsListAction
{
	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return "SystemIdleReport";
	}
}
