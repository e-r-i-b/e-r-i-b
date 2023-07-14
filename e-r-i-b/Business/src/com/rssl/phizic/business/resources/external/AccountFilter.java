package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.operations.restrictions.Restriction;

/**
 * ������������ ��� ���������� ������ ������ ��
 * ������������� ���������
 * @author Evgrafov
 * @ created 14.11.2005
 * @ $Author$
 * @ $Revision$
 */

public interface AccountFilter extends Restriction
{
    /**
     * �������� �� ���� ��� ��������
     * @param accountLink
     * @return true - ��������, false - �� ��������
     */
    boolean accept(AccountLink accountLink);
}
