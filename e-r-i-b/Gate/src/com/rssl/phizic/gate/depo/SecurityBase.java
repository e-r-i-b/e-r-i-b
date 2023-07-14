package com.rssl.phizic.gate.depo;

import com.rssl.phizic.common.types.Money;

import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 23.01.2011
 * @ $Author$
 * @ $Revision$
 */

public interface SecurityBase extends Serializable
{
	/**
	 * @return ������������ ��� ������� ������ ������ (��������������� �����)
	 */
	String getInsideCode();

	/**
	 * @return ������������ ������ ������
	 */
	String getName();

	/**
	 * @return ����������� ������� ������� ������ ������
	 */
	Money getNominal();

	/**
	 * @return ��������������� ����� ������ ������
	 */
	String getRegistrationNumber();
}
