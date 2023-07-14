package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.dictionaries.PaymentReceiverBase;

/**
 * @author osminin
 * @ created 03.03.2009
 * @ $Author$
 * @ $Revision$
 */

public interface ReceiverFilter extends Restriction
{
    /**
     * �������� �� ���������� ��� ��������
     * @param receiver
     * @return true - ��������, false - �� ��������
     */
    boolean accept(PaymentReceiverBase receiver);
}