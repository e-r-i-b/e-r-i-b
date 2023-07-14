package com.rssl.phizic.gate.mobilebank;

import java.util.List;
import java.io.Serializable;

/**
 * @author Erkin
 * @ created 22.04.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ "�������� ����� - ��������� �����" � ��������� ������.
 */
public interface MobileBankRegistration extends Serializable
{
	/**
	 * @return ��������� ����� �����������.
	 */
	MobileBankCardInfo getMainCardInfo();

	/**
	 * @return ������ ��������� (��������������) ����
	 */
	List<MobileBankCardInfo> getLinkedCards();

	/**
	 * @return ������ �����������
	 */
	MobileBankRegistrationStatus getStatus();

	/**
	 * @return ����� �����������.
	 */
	MobileBankTariff getTariff();
}