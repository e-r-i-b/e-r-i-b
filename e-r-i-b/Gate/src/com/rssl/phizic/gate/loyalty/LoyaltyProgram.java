package com.rssl.phizic.gate.loyalty;

import java.math.BigDecimal;
import java.io.Serializable;

/**
 * ���������� �� ��������� ���������� �������
 * @author gladishev
 * @ created 02.08.2012
 * @ $Author$
 * @ $Revision$
 */
public interface LoyaltyProgram extends Serializable
{
	/**
	 * @return ������� ������������� ��������� ����������.
	 */
	String getExternalId();

	/**
	 * @return ���������� ����������� ������
	 */
	BigDecimal getBalance();

	/**
	 * @return ����� �������� �������
	 */
	String getPhone();

	/**
	 * @return ����� ����������� ����� �������
	 */
	String getEmail();
}
