package com.rssl.phizic.web.ext.sbrf.reports;

import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author Mescheryakova
 * @ created 18.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class SBRFOperationReportsListAction extends ReportsListAction
{
	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return "OperationsSBRFReport";
	}
}
