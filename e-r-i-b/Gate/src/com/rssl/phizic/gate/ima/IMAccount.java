package com.rssl.phizic.gate.ima;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.dictionaries.officies.Office;

import java.util.Calendar;
import java.io.Serializable;

/**
 * @ author Balovtsev
 * @ created 25.08.2010
 * @ $Author$
 * @ $Revision$
 */
public interface IMAccount extends Serializable
{
	/**
	 * ID ����� �� ������� �������
	 * @return Id ����� �� ������� �������
	 */
	String getId();

	/**
	 * ����� �����
	 *
	 * @return ����� �����
	 */
	String getNumber();

	/**
	 * ������������ ���
	 * @return ������������ ���
	 */
	String getName();

	/**
	 * @return ��� �������
	 */
	Currency getCurrency();

	/**
	 * ������� �� ����� ���
	 * @return ������� �� ����� ��� � �������
	 */
	Money getBalance();

	/**
	 * ������������ ����� ��������
	 * @return ������������ ����� ��������
	 */
	Money getMaxSumWrite();

	/**
	 * @return ����� �������� ����� ���
	 */
	String getAgreementNumber();

	/**
	 * ���� �������� ���
	 * @return ���� �������� ���
	 */
	Calendar getOpenDate();

	/**
	 * ���� �������� ���
	 * @return ���� �������� ���
	 */
	Calendar getClosingDate();

   /**
    * ��������� �������� ������� ��� (������, ������)
    *
    * @return IMAccountState
    */
   IMAccountState getState();

	/**
	 * ��������� ����� �������� ���
	 *
 	 * @return Office
	 */
   Office getOffice();	
}
