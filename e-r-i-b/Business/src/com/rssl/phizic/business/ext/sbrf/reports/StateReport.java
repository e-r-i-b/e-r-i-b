package com.rssl.phizic.business.ext.sbrf.reports;

import java.util.Map;
import java.util.HashMap;

/**
 * @author Mescheryakova
 * @ created 09.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class StateReport
{
	/*
	 ������ � ����������
	 */
	public final static char SEND = 's';

	/*
	��������
	 */
	public final static char EXECUTED = 'o';

	/*
	������
	 */
	public final static char ERROR = 'e';

	/*
	������������ �������� ��������� ������ �� ���� ��������� (������������ � jsp)
	 */
	public final static Map<Object, String> NAME_STATE_BY_CODE = new HashMap<Object, String>(){{
		put(SEND,       "��������������");
		put(EXECUTED,   "��������");
		put(ERROR,      "������");

	}};
}
