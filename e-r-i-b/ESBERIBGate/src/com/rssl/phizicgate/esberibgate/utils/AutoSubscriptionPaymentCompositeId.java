package com.rssl.phizicgate.esberibgate.utils;

import com.rssl.phizic.gate.utils.EntityCompositeId;

/**
 * @author bogdanov
 * @ created 06.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ������������� ���������� �������������� ������� �� �������� �� ����������.
 * ������
 * <id �������>^<systemId>^<rbBrchId>^<loginId>.
 */

public class AutoSubscriptionPaymentCompositeId extends EntityCompositeId
{
	AutoSubscriptionPaymentCompositeId(String id)
	{
		super(id);
	}

	AutoSubscriptionPaymentCompositeId(String entityId, String systemId, String rbBrchId, Long loginId)
	{
		super(entityId, systemId, rbBrchId, loginId);
	}
}
