package com.rssl.phizic.web.util;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

/**
 * @author komarov
 * @ created 08.02.2012
 * @ $Author$
 * @ $Revision$
 */

public class GridUtil
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * �������� ������ �����.
	 * @param gridId �������������
	 * @return ������
	 */
	public static Long getPaginationSize(String gridId)
	{
		try
		{
			return EmployeeContext.getEmployeeDataProvider().getEmployeeData().getNumberDisplayedEntries(gridId);			
		}
		catch(BusinessException be)
		{
			log.error("������ ��������� ������� grid" + gridId, be);
			return null;
		}
	}
}
