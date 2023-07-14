package com.rssl.phizic.web.struts.forms;

import org.apache.struts.Globals;

/** ����� ��� ���������� ��������� � �������
 * @author akrenev
 * @ created 25.11.2011
 * @ $Author$
 * @ $Revision$
 */
public enum ActionMessagesKeys
{
	error(Globals.ERROR_KEY), // ������
	message(Globals.MESSAGE_KEY), // ���������
	inactiveExternalSystem("com.rssl.phizgate.ext.sbrf.technobreaks.TechnoBreak.class"),                // ������������� ������� �������
	sessionError("com.rssl.phizic.web.actions.SESSION_ERRORS_KEY"),     // ���������(�) �� ������, ���������� � ������
    sessionNotificationsTarget("com.rssl.phizic.web.actions.SESSION_NOTIFICATIONS_TARGET_KEY"), //URL �������� ��������� � ������
	additionalMessage("com.rssl.phizic.web.actions.SESSION_ADDITIONAL_MESSAGE_KEY");  // �������������� ��������� � ��������� �����

	private String key;

	private ActionMessagesKeys(String key)
	{
		this.key = key;
	}

	public String getKey()
	{
		return key;
	}
}
