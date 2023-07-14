package com.rssl.phizic.gate.payments.systems.recipients;

import java.util.Calendar;
import java.util.List;
import java.io.Serializable;

/**
 * ���� �������
 * @author Gainanov
 * @ created 14.07.2008
 * @ $Author$
 * @ $Revision$
 */
public interface Debt extends Serializable
{
	/**
	 * �������� ��� �������������.
	 * @return ��� �������������.
	 */
	String getCode();

	/**
	 * �������� �������� �������������
	 * @return �������� �������������
	 */
	String getDescription();

	/**
	 * ��������� �� ������������
	 * @return true - ��� ������ ����� ����� ������ ���� ����� ����� �����. false - ����� ����� ���� �����. 
	 */
	boolean isFixed();

	/**
	 * �������� ������(����), �� ������� ���������� �������������
	 * @return ������ ������.
	 */
	Calendar getPeriod();

	/**
	 * ���� ���������� ���������
	 * @return ���� ���������� ���������
	 */
	Calendar getLastPayDate();

	/**
	 * �������� ������ ���������� �������������.
	 * @return ������ ���������� �������������.
	 */
	List<DebtRow> getRows();

	/**
	 * ��������� ������ ����� �������������
	 * @return ����� ����� �������������
	 */
	String getAccountNumber();
}