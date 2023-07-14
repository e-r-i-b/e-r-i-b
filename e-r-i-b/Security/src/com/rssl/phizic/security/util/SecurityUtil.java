package com.rssl.phizic.security.util;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * ����� ������ �������� ������������
 * @author shapin
 * @ created 18.11.14
 * @ $Author$
 * @ $Revision$
 */
public class SecurityUtil
{

	/**
	 * ��������� ������������� ���������� ���������� �� ���������� ������������
	 *
	 * @param lastLogonDate - ���� ���������� �����
	 * @return - ������� ������������� ����������
	 */
	public static boolean needBlockByLongInactivity(Calendar lastLogonDate)
	{
		long timeToBlock =  ConfigFactory.getConfig(SecurityConfig.class).getTimeToBlock();
		return lastLogonDate != null && timeToBlock > 0 && DateHelper.diff(Calendar.getInstance(), lastLogonDate) > timeToBlock;
	}
}
