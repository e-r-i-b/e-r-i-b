package com.rssl.phizic.gate.loans;

import com.rssl.phizic.common.types.Money;

import java.util.Calendar;
import java.io.Serializable;

/**
 * ������ ������������� �� ��������� ����
 * @author gladishev
 * @ created 14.07.2010
 * @ $Author$
 * @ $Revision$
 */
public interface DateDebtItem extends Serializable
{
	/**
	 * ��� ������ �������������
	 * @return ���
	 */
	PenaltyDateDebtItemType getCode();

	/**
	* ��������� ��������� ������
	* @return ���������
	*/
	Long getPriority();

	/**
	* ���� ����� ������
	* @return ����
	*/
	String getAccountCount();

	/**
	* ����������� ���� ���������
	* (��� ����������� ���������)
	* @return ����
	*/
	Calendar getAnnuityDate();

	/**
	 * ������������ ������ �������������
	 * @return ��������
	 */
	String getName();

	/**
	 * ����� �������������
	 * @return �����
	 */
	Money getAmount();
}