package com.rssl.phizic.gate.loans;

import com.rssl.phizic.common.types.Money;

import java.util.List;
import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 15.07.2010
 * @ $Author$
 * @ $Revision$
 */
//������ �������� �� �������.
public interface ScheduleAbstract extends Serializable
{		
	/**
	 * ����� ���� ������, ������� ���� �����������, � ������ ������� � ����. 
	 * @return
	 */
	Money getDoneAmount();

	/**
	 * ����� ������� ��� �������� �������
	 * @return
	 */
	Money getRemainAmount();

	/**
	 * ����� ���� ������� � �����
	 * @return
	 */
	Money getPenaltyAmount();

	/**
	 * ����� ���������� �������� � �������
	 * @return ���������� ��������
	 */
	Long getPaymentCount();
	/**
	 * ������ ������� ���������, ������������� �� ����
	 * @return ������ ������� ���������
	 */
	List<ScheduleItem> getSchedules();

	/**
	 * �������� �� ���������� �� �������
	 * @return
	 */
	boolean getIsAvailable();

    List<LoanYearPayment> getYearPayments();
}
