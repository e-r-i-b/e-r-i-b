package com.rssl.phizic.gate.clients;

import com.rssl.phizic.auth.modes.UserRegistrationMode;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Calendar;

/**
 * ������ ��� ��������� �������������� ���������� � ��������
 * @author basharin
 * @ created 20.11.14
 * @ $Author$
 * @ $Revision$
 */

public interface AdditionalClientInfoService extends Service
{
	/**
	 * ��������� ���������� � �������������� ������ ��������������� ����������� .
	 * @param firstName - ��� �������
	 * @param lastName - ������� �������
	 * @param middleName - �������� �������
	 * @param docNumber - ����� ��������� �������
	 * @param birthDate - ���� �������� �������
	 * @param tb - �� �������
	 * @return �������������� ������ ��������������� �����������
	 * @throws com.rssl.phizic.gate.exceptions.GateLogicException, GateException
	 */
	@SuppressWarnings({"MethodWithTooManyParameters"})
	public UserRegistrationMode getUserRegistrationMode(String firstName, String lastName, String middleName, String docNumber, Calendar birthDate, String tb)
			throws GateException, GateLogicException;
}
