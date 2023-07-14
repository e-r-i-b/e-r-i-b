package com.rssl.phizic.gate.notification;

import com.rssl.phizic.notifications.Notification;

/**
 * ��������� ������� ���������
 */
public interface StatusDocumentChangeNotification extends Notification
{
	public static String PAYMENT_STATUS_REJECT   = "R";// �������
	public static String PAYMENT_STATUS_ACCEPT   = "C";// �������
	public static String PAYMENT_STATUS_DELETED   = "D";// �����

    @Deprecated
	long getApplicationKind();

	@Deprecated
	String getApplicationKey();


   /**
    * ������� ������������� ���������
    *
    * @return ������� ID ���������
    */
   String getDocumentId();
   /**
    * ����� ������ ���������.
    *
    * @return ����� ������ ���������.
    */
   String getStatus();
   /**
    * ����� ������, ���� ������ ��������.
    *
    * @return ����� ������, ���� ������ ��������.
    */
   String getError();
}
