package com.rssl.phizic.web.ext.sbrf.reports;

import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author Mescheryakova
 * @ created 23.07.2010
 * @ $Author$
 * @ $Revision$
 */

public class TBContractReportsListAction extends ReportsListAction
{
	protected String getQueryName(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return "ContractTBReport";
	}
}
