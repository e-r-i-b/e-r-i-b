package com.rssl.phizic.web.ext.sbrf.reports;

import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author Mescheryakova
 * @ created 16.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class OSBContractReportsListAction extends ReportsListAction
{
	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return "ContractOSBReport";
	}
}
