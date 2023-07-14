package com.rssl.phizic.gate.depo;

import java.util.List;
import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 17.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� � ������� ����� ����
 */
public interface DepoAccountDivision extends Serializable
{
	/**
	 * @return ��� ������� ����� ����
	 */
	String getDivisionType();

	/**
	 * @return ����� ������� ����� ����
	 */
	String getDivisionNumber();

	/**	 
	 * @return ������ ������ �������
	 */
	List<DepoAccountSecurity> getDepoAccountSecurities();
}
