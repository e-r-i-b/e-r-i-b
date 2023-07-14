package com.rssl.phizic.gate.security;

import com.rssl.phizic.common.types.Money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * ���������� �� ��������������� �����������
 * @author lukina
 * @ created 04.09.13
 * @ $Author$
 * @ $Revision$
 */

public interface SecurityAccount extends Serializable
{
	/**
    * @return ������� ID
    */
	String getId();

	/**
	 * @return ��� ������
	 */
	String getBlankType();

	/**
	 * @return  ���������� ����� ������
	 */
	String getSerialNumber();

	/**
	 * @return ����� �������� ������ ������
	 */
	Money  getNominalAmount();

	/**
	 * @return  ����� ������ �� ������ ������
	 */
	Money  getIncomeAmt();

	/**
	 * @return ���������� ������ ������ �� ������ ������
	 */
	BigDecimal getIncomeRate();

	/**
	 * @return ��� ����� ������� �� ������ ������
	 */
	String  getTermType();

	/**
	 * @return ���� ������ ������ ������ � ����
	 */
	Long      getTermDays();

	/**
	 * @return ���� ����������� (�������������) ������ ������
	 */
	Calendar  getComposeDt();

	/**
	 * @return ���� ������ ����� ������ ������ ������
	 */
	Calendar  getTermStartDt();

	/**
	 * @return ���� ��������� ����� ������ ������ ������
	 */
	Calendar  getTermFinishDt();

	/**
	 * @return ���� ����������� ����� �������� �� ������
	 */
	Calendar  getTermLimitDt();

	/**
	 * @return ������� ���������� �� �������� � �����
	 */
	boolean getOnStorageInBank();
	/**
	 * @return  ������� �������� - ����� ��������
	 */
	String getDocNum();
	/**
	 * @return ������� ��������  - ���� ���������
	 */
	Calendar getDocDt();
	/**
	 * @return ���, �������������� ���� �������� �������� (��� ������������� ���������)
	 */
	String getBankId();
	/**
	 * @return ���, �������������� ���� �������� �������� (������������)
	 */
	String getBankName();
	/**
	 * @return ���, �������������� ���� �������� �������� (�����)
	 */
	String getBankPostAddr();
	/**
	 * @return  ��� ������������� ���������, ��������� ����������
	 */
	String getIssuerBankId();
	/**
	 * @return  ������������ �����, ��������� ����������
	 */
	String getIssuerBankName();
}
