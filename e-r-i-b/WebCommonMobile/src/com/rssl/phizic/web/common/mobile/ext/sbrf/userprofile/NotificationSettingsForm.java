package com.rssl.phizic.web.common.mobile.ext.sbrf.userprofile;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author EgorovaA
 * @ created 14.08.13
 * @ $Author$
 * @ $Revision$
 *
 * ����� ��������� �������� ����������
 */
public class NotificationSettingsForm extends ActionFormBase
{
	/**
	 * ��� ���������
	 */
	// ���������� � ����� � � �������
	private String notificationType;
	// ���������� � ��������� ������ �� ������ ������
	private String mailNotificationType;
	// ���������� �� ���������� ��������
	private String deliveryNotificationType;
	// ����� �� ������������ ���� �������
	private boolean needLoginConfirm;
	// ����� ��� ������������� ��������
	private String operationConfirmNotificationType;

	/**
	 * ���� ����������
	 */
	// �� email
	private String email;
	// �� �������� (����� ��������) - ������ ��������� ��� ���������� � ����� � �������
	private String phone;
	// push-����������� (�������� ��������� ��������� �������)
	private String push;
	// �� �������� (����� ��������) - ������ ��������� ��� ���������� � �������
	private String phoneForMail;

	public String getNotificationType()
	{
		return notificationType;
	}

	public void setNotificationType(String notificationType)
	{
		this.notificationType = notificationType;
	}

	public String getMailNotificationType()
	{
		return mailNotificationType;
	}

	public void setMailNotificationType(String mailNotificationType)
	{
		this.mailNotificationType = mailNotificationType;
	}

	public String getDeliveryNotificationType()
	{
		return deliveryNotificationType;
	}

	public void setDeliveryNotificationType(String deliveryNotificationType)
	{
		this.deliveryNotificationType = deliveryNotificationType;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getPush()
	{
		return push;
	}

	public void setPush(String push)
	{
		this.push = push;
	}

	public String getPhoneForMail()
	{
		return phoneForMail;
	}

	public void setPhoneForMail(String phoneForMail)
	{
		this.phoneForMail = phoneForMail;
	}

	public void setOperationConfirmNotificationType(String operationConfirmNotificationType)
	{
		this.operationConfirmNotificationType = operationConfirmNotificationType;
	}

	public String getOperationConfirmNotificationType()
	{
		return operationConfirmNotificationType;
	}

	public void setNeedLoginConfirm(boolean needLoginConfirm)
	{
		this.needLoginConfirm = needLoginConfirm;
	}

	public boolean isNeedLoginConfirm()
	{
		return needLoginConfirm;
	}
}
