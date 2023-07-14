package com.rssl.phizic.web.log.guest;

import com.rssl.phizic.logging.confirm.GuestOperationConfirmLogEntry;
import com.rssl.phizic.web.common.ListFormBase;

/**
 * @author osminin
 * @ created 22.07.15
 * @ $Author$
 * @ $Revision$
 *
 * ����� ��������� ��������� ������� ������������� ��������
 */
public class ListGuestOperationConfirmForm extends ListFormBase<GuestOperationConfirmLogEntry>
{
	private String OUID;

	/**
	 * @return ������������� ��������
	 */
	public String getOUID()
	{
		return OUID;
	}

	/**
	 * @param OUID ������������� ��������
	 */
	public void setOUID(String OUID)
	{
		this.OUID = OUID;
	}
}
