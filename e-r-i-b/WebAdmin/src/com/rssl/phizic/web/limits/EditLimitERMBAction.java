package com.rssl.phizic.web.limits;

import com.rssl.phizic.common.types.limits.ChannelType;

/**
 * �������������� ������������ ������� �� ������ ����� � ���� ������
 *
 * @author khudyakov
 * @ created 23.10.13
 * @ $Author$
 * @ $Revision$
 */
public class EditLimitERMBAction extends EditLimitAction
{
	private static final String CHANNEL_TYPE = "ermb";

	protected ChannelType getLimitChannelType()
	{
		return ChannelType.ERMB_SMS;
	}

	protected String getChannelType()
	{
		return CHANNEL_TYPE;
	}
}
