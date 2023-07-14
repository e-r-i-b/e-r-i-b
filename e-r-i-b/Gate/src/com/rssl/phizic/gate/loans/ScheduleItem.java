package com.rssl.phizic.gate.loans;

import com.rssl.phizic.common.types.Money;

import java.util.Calendar;
import java.util.Map;
import java.io.Serializable;

/**
 * @author Omeliyanchuk
 * @ created 13.03.2008
 * @ $Author$
 * @ $Revision$
 */
/**
 * ������ ������� ���������
 */
public interface ScheduleItem extends Serializable
{
/**
    * ���� �������
    * Domain: Date
    *
    * @return ����
    */
   Calendar getDate();
   /**
    * ����� ������� �� ��������� �����
    *
    *
    * @return �����
    */
   Money getPrincipalAmount();
   /**
    * ����� ������� �� ���������
    *
    *
    * @return �����
    */
   Money getInterestsAmount();
   /**
    * ����� ��������
    *
    *
    * @return �����
    */
   Money getCommissionAmount();
   /**
    * ����� ������� (�����).
    *
    *
    * @return �����
    */
   Money getTotalAmount();
   /**
    * ����� �����, ������� ������ ������ ��������� (��������) �� ���������� �������.
    * ����� �� ��������� � getTotalAmount, ��� ��� ����� �������������� � ������, ���������� �����, ������������ (������ � ��.)
    *
    *
    * @return �����
    */
   Money getTotalPaymentAmount();
   /**
    * ����� ��� ���������� ���������
    *
    *
    * @return �����
    */
   Money getEarlyPaymentAmount();
	/**
	 * ���������� ����� ������� � �������
	 * @return �����
	 */
	Long getPaymentNumber();
	/**
	 * ������ ������� �� �������
	 *
	 * @return ������
	 */
	LoanPaymentState getPaymentState();

	/**
	 * ������������� ��������
	 * @return �������������
	 */
	String getIdSpacing();

   /**
	 * ������ �������� � ������������ �������� �� �������
	 *
	 * @return ���<��� ������, ������>
	 */
	Map<PenaltyDateDebtItemType, DateDebtItem> getPenaltyDateDebtItemMap();

	/**
	 * @return ���������
	 */
	Money getOverpayment();

    /**
     * ������� ��������� �����
     * @return
     */
   Money getRemainDebt();
}
