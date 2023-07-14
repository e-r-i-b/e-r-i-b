package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.business.dictionaries.PaymentReceiverBase;

/**
 * @author osminin
 * @ created 03.03.2009
 * @ $Author$
 * @ $Revision$
 *
 * ������ �� ���������, �������� ���� ��������������
 */

public class NullReceiverFilter implements ReceiverFilter
{
	public boolean accept(PaymentReceiverBase receiver)
	{
		return true;
	}
}
