package com.rssl.phizic.web.gate.services.util;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author egorova
 * @ created 01.07.2009
 * @ $Author$
 * @ $Revision$
 */
public interface GateInfoService_PortType extends Remote
{
	/**
	 * @return ������, ���������������� ������� �������
	 */
	String getUID(Office office) throws RemoteException;

	/**
	 * @return true - ����� ����������� ������
	 */
	Boolean isNeedChargeOff(Office office) throws RemoteException;

	/**
	 * @return true - ������������ �������������
	 */
	Boolean isImportEnable(Office office) throws RemoteException;

	/**
	 * @return true - ����� �������� ������ ���������� ����������������(������������ ������������) �� ������� �������
	 */
	Boolean isRegistrationEnable(Office office) throws RemoteException;

	/**
	 * @return true - ����������� ���������� �������� ����� ������������
	 */
	Boolean isAgreementSignMandatory(Office office) throws RemoteException;

	/**
	 * @return true - ���������� ����������� ������� ����� ��������� ��������� �������
	 */
	Boolean isNeedAgrementCancellation(Office office) throws RemoteException;

	/**
	 * @return true - �������� ��������� ������ �����.
	 */
	Boolean isCurrencyRateAvailable(Office office) throws RemoteException;

	/**
	 * @return true - ���� ����������� ��������.
	 */
	Boolean isPaymentCommissionAvailable(Office office) throws RemoteException;

	/**
	 * @return true - �������� ��������� �������� ���������. (�� ����, ���������� �� CalendarGateService � �����)
	 */
	Boolean isCalendarAvailable(Office office) throws RemoteException;		
}
