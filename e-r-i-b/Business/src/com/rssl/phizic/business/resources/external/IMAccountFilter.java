package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.operations.restrictions.Restriction;

/**
 * ������������ ��� ���������� ������ ���
 * @author Pankin
 * @ created 28.06.2011
 * @ $Author$
 * @ $Revision$
 */

public interface IMAccountFilter extends Restriction
{
	/**
     * �������� �� ��� ��� ��������
     * @param imAccountLink ������ �� ���
     * @return true - ��������, false - �� ��������
     */
    boolean accept(IMAccountLink imAccountLink);
}
