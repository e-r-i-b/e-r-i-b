package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import org.apache.commons.collections.Predicate;

/**
 * @author mihaylov
 * @ created 03.09.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������������ ��� ���������� ������ ������ ����
 */
public interface DepoAccountFilter extends Restriction, Predicate
{
	/**
	 * �������� �� ���� ���� ��� ��������
	 * @param depoAccount - ���� ����
	 * @return true -��������, false - �� ��������
	 */
	boolean accept(DepoAccount depoAccount);
}
