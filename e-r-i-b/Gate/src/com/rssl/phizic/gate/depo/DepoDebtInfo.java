package com.rssl.phizic.gate.depo;

import com.rssl.phizic.common.types.Money;

import java.util.List;
import java.io.Serializable;

/**
 * @author mihaylov
 * @ created 16.08.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� �� ������������� �� ����� ����
 */
public interface DepoDebtInfo extends Serializable
{
	/**
	 * ����� ����� �������������
	 * @return debt
	 */
	Money getTotalDebt();

	/**
	 * �������� ������������� �� ����� ����
	 * @return list<����� ����������� �� ����� ����>
	 */
	List<DepoDebtItem> getDebtItems();
}
