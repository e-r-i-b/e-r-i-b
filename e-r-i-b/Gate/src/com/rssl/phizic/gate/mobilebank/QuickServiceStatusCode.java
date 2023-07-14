package com.rssl.phizic.gate.mobilebank;

/**
 *
 * �������� ������� ����� ��������� ������ ������� ��������
 *
 * User: Balovtsev
 * Date: 24.05.2012
 * Time: 9:40:48
 */
public enum QuickServiceStatusCode
{
	/*
	 * �����������
	 */
	QUICK_SERVICE_STATUS_ON,

	/*
	 * ���������
	 */
	QUICK_SERVICE_STATUS_OFF,

	/*
	 * ������ ������������, ��� �������� ������ ���������� ��������� ��������
	 */
	QUICK_SERVICE_STATUS_REPEAT_QUERY,

	/*
	 * ������ ������������, ��� �������� ���������, ������������ ������(�������� �������� ��� ������ ���������
	 * � �.�)
	 */
	QUICK_SERVICE_STATUS_UNKNOWN;
}
