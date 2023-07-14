package com.rssl.phizic.gate.depo;

import com.rssl.phizic.common.types.Currency;

/**
 * @author lukina
 * @ created 18.10.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� � ����� ���������
 */
public interface DepositorAccount
{
	/**
	 * @return ����� �����
	 */
	public String getAccountNumber();

	/**
	 * @return ��� ����������� �����
	 */
	public String getCardType();

	/**
	 * @return ����� ����������� �����
	 */
	public String getCardId();

	/**
	 * @return ������������ �����, ��� ������ ����
	 */
	public String getBankName();

	/**
	 * @return ���
	 */
	public String getBIC();

	/**
	 * @return ����������������� ����
	 */
	public String getCorAccount();

	/**
	 * @return ������������ �����, ��� ������ ����������������� ����
	 */
	public String getCorBankName();

	/**
	 * @return ���������� �����
	 */
	public String getDestination();

	/**
	 * @return ������ �����
	 */
	public Currency getCurrency();
	
}
