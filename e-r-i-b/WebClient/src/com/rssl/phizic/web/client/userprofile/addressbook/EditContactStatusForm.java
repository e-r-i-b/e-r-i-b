package com.rssl.phizic.web.client.userprofile.addressbook;

import com.rssl.phizic.web.common.EditFormBase;

/**
 * ����� �������������� ������� ��������
 *
 * @author shapin
 * @ created 07.10.14
 * @ $Author$
 * @ $Revision$
 */

public class EditContactStatusForm extends EditFormBase
{
	/* ������� remove ����������, ������� �� ������� �� �������� ����� (true) ��� ��������������� (false) */
	private boolean remove;
	private String error;

	public void setRemove(boolean needRemove)
	{
		remove = needRemove;
	}

	public boolean getRemove()
	{
		return this.remove;
	}

	public String getError()
	{
		return error;
	}

	public void setError(String error)
	{
		this.error = error;
	}


}
