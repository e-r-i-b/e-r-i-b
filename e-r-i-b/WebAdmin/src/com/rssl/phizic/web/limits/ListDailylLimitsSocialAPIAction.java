package com.rssl.phizic.web.limits;

import com.rssl.phizic.common.types.limits.ChannelType;

/**
 * ����� ��� ��������� ������ �������� ������������ ������� (���������� ���)
 *
 * @author sergunin
 * @ created 16.07.2014
 * @ $Author$
 * @ $Revision$
 */
public class ListDailylLimitsSocialAPIAction extends ListDailyLimitsAction
{
	@Override
	protected ChannelType getChannelType()
	{
		return ChannelType.SOCIAL_API;
	}
}
