package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.business.BusinessException;

/**
 * ������ � �������� ����� ���������� ������ � ������
 * @author niculichev
 * @ created 30.11.2010
 * @ $Author$
 * @ $Revision$
 */
public interface PaymentAbilityERL<LinkedObject> extends ExternalResourceLink
{
	/**
	 * ������� �� �������, ������� �������� � �����
	 */
	Money getRest();

	/**
	 *
	 * @return ����, � ������� ������� ������� ������
	 */
	Office getOffice() throws BusinessException;

	/**
	* ������������ ����� ��������.
	* ������ ��������� ������ ������ ���� �����.
	* ���� ����������� ��� �� == null
	*
	* @return Money
	*/
	Money getMaxSumWrite();

	/**
	 * @return ������, ��������� � ������ � �������, ������� �������� � ��.
	 */
	LinkedObject toLinkedObjectInDBView();
}
