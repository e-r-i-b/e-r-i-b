package com.rssl.phizic.business.fraudMonitoring.senders.events.builders;

import com.rssl.phizic.rsa.senders.types.EventsType;

/**
 * ������ ������� �� ������� ��������� �������� ����������
 *
 * @author khudyakov
 * @ created 19.06.15
 * @ $Author$
 * @ $Revision$
 */
public class AnalyzeChangeNotificationSettingsRequestBuilder extends AnalyzeEventRequestBuilderBase
{
	@Override
	protected EventsType getEventsType()
	{
		return EventsType.CHANGE_ALERT_SETTINGS;
	}

	@Override
	protected String getEventDescription()
	{
		return "��������� ����� SMS-�������������� � \\ ��� E-Mail ��������������";
	}
}
