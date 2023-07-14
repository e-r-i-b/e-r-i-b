package com.rssl.phizic.config.loanreport;

import com.rssl.phizic.common.types.UUID;
import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;

import java.net.URL;

/**
 * User: Moshenko
 * Date: 30.09.14
 * Time: 11:15
 * ������ ���������� ����
 */

/**
 * ������ �� ���������� ����
 * �� ����������� �� Config, �.�. Config ����� ������������� 15-�������� ���:
 * � ������ ��� ����� ������ ��������� ����� ��� ��� �����, ��� ��� �������� � ��� ����������.
 */
@PlainOldJavaObject
@SuppressWarnings("PublicField")
public class CreditBureauConfig
{
	/**
	 * ������ [�����������] ������� ���� ����� (never null)
	 */
	public DayOfMonth jobStartDay;

	/**
	 * ������ �������� ��� ����� (never null)
	 */
	public TimeOfDay jobStartTime;

	/**
	 * ����� �������� ��� ����� (never null)
	 */
	public TimeOfDay jobEndTime;

	/**
	 * ������� ��������� �����
	 */
	public boolean jobEnabled;

	/**
	 * ������� ��������� ������������ ������ �����
	 */
	public boolean jobSuspended;

	/**
	 * ������ ����� �������������� ��������
	 */
	public int jobPackSize;

	/**
	 * ���� � �������(��� �������� PDF)
	 */
	public String pfdResourcePath;

	/**
	 * ����� ���
	 */
	public String okbAddress;

	/**
	 * ������� ���
	 */
	public String okbPhone;

	/**
	 * ����������������� ����� ���������� ����� ��� ���
	 */
	public UUID okbServiceProviderUUID;

	/**
	 * ������� ���
	 */
	public int bkiTimeoutInMinutes;

	/**
	 * URL ���
	 */
	public URL okbURL;

	/**
	 * ������� ����������� ������ ������� ����� � ������� pdf
	 */
	public boolean pdfButtonShow;

	/**
	 * JNDI-����� ������� ���� -> CRM
	 */
	public final String eribToCrmQueueName = "jms/esb/EribToCrmQueue";

	/**
	 * JNDI-����� ������� ���������� ���� -> CRM
	 */
	public final String eribToCrmQCFName = "jms/esb/EribToCrmQCF";

}
