package com.rssl.phizic.gate.utils;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.util.Calendar;

/**
 * @author Gainanov
 * @ created 02.02.2009
 * @ $Author$
 * @ $Revision$
 */
public interface CalendarGateService extends Service
{
	/**
	 * �������� ��������� ������� ���� (������������ ��������� ������� �������)
	 * @param fromDate ����, �� ������� ��������� ��������� ������� ����
	 * @param document - ��������
	 * @return ���� ���������� �������� ���
	 */
	public Calendar getNextWorkDay(Calendar fromDate, GateDocument document) throws GateException, GateLogicException;

	/**
	 * ����������, �������� �� ��������� ���� �������� (��������� ����).
	 * ������������ ��������� �� ������� �������
	 * @param date ���� ��� ��������
	 * @param document - ��������
	 * @return ������� ���� ��� ���
	 */
	public boolean isHoliday(Calendar date, GateDocument document) throws GateException, GateLogicException;
}
