package com.rssl.phizic.gate.loans;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.clients.Client;

import java.util.Calendar;

/**
 * @author gladishev
 * @ created 06.05.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������� �����������.
 */
public interface GuaranteeContract
{
	/**
	 * ����� �������� ��������������
	 *
	 * @return �����
	 */
	public String getNumber();

	/**
	 * ��� ��������
	 *
	 * @return ���
	 */
	public ContractType getType();

	/**
	 * ������ ��������
	 *
	 * @return ������
	 */
	public ContractState getState();

	/**
	 * ���� ������ ��������
	 *
	 * @return ����
	 */
	public Calendar getOpenDate();

	/**
	 * ���� ����� ��������
	 *
	 * @return ����
	 */
	public Calendar getCloseDate();

	/**
	 * ����� ��������
	 *
	 * @return �����
	 */
	public Money getSumm();

	/**
	 * ����������
	 *
	 * @return ������
	 */
	public Client getGuarantee();
}
