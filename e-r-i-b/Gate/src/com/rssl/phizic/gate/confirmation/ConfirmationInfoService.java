package com.rssl.phizic.gate.confirmation;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.Service;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ � ���������� � ������������� �������� ��������.
 */

public interface ConfirmationInfoService extends Service
{
	/**
	 * ��������� ���������� � ������������� �������� ��������.
	 * @param firstName - ��� �������
	 * @param lastName - ������� �������
	 * @param middleName - �������� �������
	 * @param docNumber - ����� ��������� �������
	 * @param birthDate - ���� �������� �������
	 * @param tb - �� �������
	 * @return ���������� � �������������
	 * @throws GateLogicException, GateException
	 */
	@SuppressWarnings({"MethodWithTooManyParameters"})
	public ConfirmationInfo getConfirmationInfo(String firstName, String lastName, String middleName, String docNumber, Calendar birthDate, String tb)
			throws GateException, GateLogicException;
}
