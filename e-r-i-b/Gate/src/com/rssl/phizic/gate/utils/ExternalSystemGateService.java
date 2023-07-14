package com.rssl.phizic.gate.utils;

import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.external.systems.AutoStopSystemType;
import com.rssl.phizic.gate.Service;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;

import java.util.Calendar;
import java.util.List;

/**
 * C����� ������� ������.
 *
 * @author khudyakov
 * @ created 19.10.2011
 * @ $Author$
 * @ $Revision$
 */
public interface ExternalSystemGateService extends Service
{
	/**
	 * �������� �� ���������� ������� �������. ���� ������� �� ������� ��������� InactiveExternalSystemException
	 * � ���������� �� ��� �������
	 * @param code ��� (uuid �������� ���� SystemId ������� �������)
	 * @throws GateException
	 */
	public void check(String code) throws GateException;

	/**
	 * ���������� ������� �� � ������ ������ ������� �������
	 * (���� ������� ������� �� ������� � ��, �� ������� ������� ���������)
	 * @param externalSystem ������� �������
	 * @return true - �������
	 */
	boolean isActive(ExternalSystem externalSystem) throws GateException;

	/**
	 * �������� �� ���������� ������� �������. ���� ������� �� ������� ��������� InactiveExternalSystemException
	 * � ���������� �� ��� �������
	 * @param externalSystem ������� �������
	 * @throws GateException
	 */
	public void check(ExternalSystem externalSystem) throws GateException;

	/**
	 * ���������� ������ ������� ������ �� ������������� � ��������
	 * @param office �������������
	 * @param product �������
	 * @return ������ ������� ������
	 * @throws GateException
	 */
	public List<? extends ExternalSystem> findByProduct(Office office, BankProductType product) throws GateException;

	/**
	 * ���������� ������ ������� ������ �� ���� ��������
	 * @param codeTB �������������
	 * @return ������ ������� ������
	 * @throws GateException
	 */
	public List<? extends ExternalSystem> findByCodeTB(String codeTB) throws GateException;

	/**
	 * ���������� ����� ��������� ���������������� ��������, ���� ���� ��������. � ������ �������
	 * �������-�������� �������� InactiveExternalSystemException
	 * @param externalSystemUUID uuid �������� ���� SystemId ������� �������
	 * @return ����� ��������� ���������������� ��������, ���� null, ���� ������� �������.
	 * @throws GateException
	 */
	public Calendar getTechnoBreakToDateWithAllowPayments(String externalSystemUUID) throws GateException;

	/**
	 * �������� ������ � ������������� ���
	 * ������������ ����� �����
	 */
	@Deprecated
	public void addMBKOfflineEvent() throws GateException;

	/**
	 * �������� ������ �� ������ �� ��������� ������� �������
	 * @param systemCode ��� ������� �������, ��� ������� ���������� �������� ������ �� ������
	 * @param systemType ��� ������� �������, ��� ������� ���������� �������� ������ �� ������
	 * @throws GateException
	 */
	public void addOfflineEvent(String systemCode, AutoStopSystemType systemType) throws GateException;
}
