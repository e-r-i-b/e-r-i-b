package com.rssl.phizic.operations.log.guest;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.logging.system.guest.GuestSystemLogEntry;
import com.rssl.phizic.operations.log.LogFilterOperationBase;

/**
 * @author saharnova
 * @ created 13.02.15
 * @ $Author$
 * @ $Revision$
 */
public class GuestSystemLogOperation extends LogFilterOperationBase
{
	private static final SimpleService service = new SimpleService();

	private GuestSystemLogEntry guestSystemLogEntry = null;
	private static final String ERROR_MESSAGE_ENTRY = "Ќе удалось найти запись в системном логе с идентификатором id=";
	private static final String ERROR_MESSAGE_ID = "id не может быть null";

	/**
	 * инициализаци€ лога
	 * @param id идентификатор лога
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		if(id == null)
			throw new BusinessException(ERROR_MESSAGE_ID);
		GuestSystemLogEntry temp = service.findById(GuestSystemLogEntry.class, id, getInstanceName());
		if (temp == null)
			throw new BusinessException(ERROR_MESSAGE_ENTRY + id);

		guestSystemLogEntry = temp;
	}

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return guestSystemLogEntry;
	}
}
