package com.rssl.phizic.gate.clients;

import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 04.02.2010
 * @ $Author$
 * @ $Revision$
 */
public interface BackRefClientService extends Service
{
	/**
	 * ����� ������� �� id ������
	 * @param loginId - id ������
	 * @return ������
	 */
	Client getClientById(Long loginId) throws GateLogicException, GateException;

	/**
	 * ��������� ������� �� ���, ��������� � ���� ��������
	 * @param firstName - ��� �������
	 * @param lastName - ������� �������
	 * @param middleName - �������� �������
	 * @param docSeries - ����� ��������� �������
	 * @param docNumber - ����� ��������� �������
	 * @param birthDate - ���� �������� �������
	 * @param tb - �� �������
	 * @return ������������������� � ��� �������
	 * @throws GateLogicException, GateException
	 */
	@SuppressWarnings("MethodWithTooManyParameters")
	Client getClientByFIOAndDoc(String firstName, String lastName, String middleName, String docSeries, String docNumber, Calendar birthDate, String tb) throws GateLogicException, GateException;

	/**
	 * �������� ��� �������������, � �������� ��������� ����� �� ������� ������������ ����� � �������
	 * @param loginId id ������ �������
	 * @return  ������ ����  CB_CODE|�� ����� ���������� �����|��� ����� ���������� �����|��� ����� ���������� �����
	 * ��� CB_CODE -  ������������� �����, �� ������� ����� ������
	 */
	//todo ������ BUG025769
	//@Cachable(keyResolver = LongCacheKeyComposer.class, linkable = false)
	String getClientDepartmentCode(Long loginId) throws GateLogicException, GateException;

	/**
	 * ����� ���� ������� �� id �������
	 * @param clientId - id �������
	 * @return ��� �������
	 * */
	String getClientCreationType(String clientId) throws GateLogicException, GateException;
}
