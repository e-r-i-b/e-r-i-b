package com.rssl.phizic.business.ermb.profile.events;

import com.rssl.phizic.business.ermb.ErmbProfileImpl;

/**
 * @author EgorovaA
 * @ created 10.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * �������� ��� �������� ����������� ������ �� ���������� ����-�������
 */
public class ErmbProfileEvent
{
	private ErmbProfileImpl oldProfile;
	private ErmbProfileImpl newProfile;
	private boolean sendSms = true;         //�� ��������� ���������� ��� �� ���������

	/**
	 * ctor
	 * @param oldProfile ��������� ������� �� ��������� (null ���� �����)
	 */
	public ErmbProfileEvent(ErmbProfileImpl oldProfile)
	{
		this.oldProfile = oldProfile;
	}

	public ErmbProfileImpl getOldProfile()
	{
		return oldProfile;
	}

	public ErmbProfileImpl getNewProfile()
	{
		return newProfile;
	}

	public void setNewProfile(ErmbProfileImpl newProfile)
	{
		this.newProfile = newProfile;
	}

	public boolean isSendSms()
	{
		return sendSms;
	}

	public void setSendSms(boolean sendSms)
	{
		this.sendSms = sendSms;
	}
}
