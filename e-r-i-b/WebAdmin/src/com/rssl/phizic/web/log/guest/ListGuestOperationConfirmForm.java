package com.rssl.phizic.web.log.guest;

import com.rssl.phizic.logging.confirm.GuestOperationConfirmLogEntry;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author osminin
 * @ created 22.07.15
 * @ $Author$
 * @ $Revision$
 *
 * Форма просмотра гостевого журнала подтверждений операций
 */
public class ListGuestOperationConfirmForm extends ListFormBase<GuestOperationConfirmLogEntry>
{
	private String OUID;

	/**
	 * @return идентификатор операции
	 */
	public String getOUID()
	{
		return OUID;
	}

	/**
	 * @param OUID идентификатор операции
	 */
	public void setOUID(String OUID)
	{
		this.OUID = OUID;
	}
}
