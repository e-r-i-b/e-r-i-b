package com.rssl.phizgate.common.profile;

import com.rssl.phizic.gate.exceptions.GateException;

/**
 * �������� ��� ���������� ��� �������� ��������.
 *
 * @author bogdanov
 * @ created 29.01.15
 * @ $Author$
 * @ $Revision$
 */

public interface MBKPhoneLoaderCallBack
{
	/**
	 * ��� ���������� ��������.
	 *
	 * @param phone �������.
	 */
	public void onAdd(String phone);

	/**
	 * ��� �������� ��������.
	 *
	 * @param phone �������.
	 */
	public void onDelete(String phone) throws GateException;
}
