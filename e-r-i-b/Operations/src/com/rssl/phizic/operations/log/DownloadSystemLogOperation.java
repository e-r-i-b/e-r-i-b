package com.rssl.phizic.operations.log;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.logging.system.SystemLogEntry;
/**
 * @author Roshka
 * @ created 14.03.2006
 * @ $Author$
 * @ $Revision$
 */

public class DownloadSystemLogOperation extends LogFilterOperationBase
{
	private static final SimpleService service = new SimpleService();

	private SystemLogEntry systemLogEntry= null;
	private static final String ERORR_MESSAGE = "Не удалось найти запись в системном логе с идентификатором id=";

	public void initialize(Long id) throws BusinessException
	{
		if(id==null)
			throw new BusinessException(ERORR_MESSAGE +id);
		SystemLogEntry temp = service.findById(SystemLogEntry.class, id, getInstanceName());
		if (temp == null)
			throw new BusinessException(ERORR_MESSAGE +id);

		systemLogEntry = temp;
	}

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return systemLogEntry; 
	}
}