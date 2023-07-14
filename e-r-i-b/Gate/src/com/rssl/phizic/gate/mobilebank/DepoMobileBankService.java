package com.rssl.phizic.gate.mobilebank;

import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.mbv.ClientAccPh;
import com.rssl.phizic.gate.mbv.MbvClientIdentity;

import java.util.List;

/**
 * User: Moshenko
 * Date: 03.09.13
 * Time: 15:57
 * ��������� ���������������� ����� � ���
 */
public interface DepoMobileBankService extends Service
{
	/**
	 * ����� �������� �������������� ������ �������� � ���
	 * @param phoneNumber  ����� ��������
	 * @return  ������ MbvClientIdentity
	 */
	List<MbvClientIdentity> checkPhoneOwn(String phoneNumber) throws GateException;

    /**
     * ����� ��������� ������ �������, ������ ��������� � �������� ���������� ������������� �� ���, ���, �� �������
     * @param clientIdentity ����������������� ������ �������
     * @return  �������� � ������� ������� � ���
     * @throws GateException
     */
    ClientAccPh getClientAccPh(MbvClientIdentity clientIdentity)throws GateException;

    /**
     * ����� ��� ������ ���������� �������� �������
     * @param clientIdentity ����������������� ������ �������
       @return  ������������� �������� �������
     * @throws GateException
     */
    UUID beginMigration(MbvClientIdentity clientIdentity)throws GateException;

    /**
     * ����� ���������� ���������� �������� �������
     * @param migrationId ������������� �������� �������
     * @throws GateException
     */
    void commitMigration(UUID migrationId)throws GateException;

    /**
     * ����� ������ ������ ���������� �������� �������
     * @param migrationId ������������� �������� �������
     * @throws GateException
     */
    void rollbackMigration(UUID migrationId)throws GateException;

	/**
	 * ����� ������ �������� �������
	 * @param migrationId ������������� �������� �������
	 * @throws GateException
	 */
	void reverseMigration(UUID migrationId)throws GateException;

    /**
     * ����� ���������� �������� ��� �� ������ ��������
     * @param phoneNumber ����� ��������
     * @throws GateException
     */
    void discByPhone(String phoneNumber)throws GateException;
}
