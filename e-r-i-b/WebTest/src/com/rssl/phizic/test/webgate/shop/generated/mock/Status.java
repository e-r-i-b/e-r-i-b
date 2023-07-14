package com.rssl.phizic.test.webgate.shop.generated.mock;

import com.rssl.phizic.test.webgate.shop.generated.StatusType;

import java.util.Random;

/**
 * @author gulov
 * @ created 14.01.2011
 * @ $Authors$
 * @ $Revision$
 */

/**
 * ������� ��������� �������� ������ ������� ���������
 */
public class Status
{
	/**
	 * ��� ��������� ����������
	 */
	private static final long SUCCESS_CODE = 0;

	/**
	 * ��� ������
	 */
	private static final long ERROR_CODE = -999;

	/**
	 * �������� ��� �������
	 */
	private static final int MAX_RANDOM = 3;

	/**
	 * ��������� �������� ��� ERROR_CODE
	 */
	private static final int RANDOM_ERROR_VALUE = 1;

	/**
	 * ��������� �� ������
	 */
	private static final String ERROR_TEXT = "������ ������� ��� ����������� ������������";

	public Status()
	{
	}

	/**
	 * ������� � ��������� ������ ������� ���������
	 * @return ������� ���������
	 */
	public StatusType getStatus()
	{
		StatusType result = new StatusType();

		result.setStatusCode(getStatusCode());
		result.setStatusDesc(getStatusDesc(result.getStatusCode()));

		return result;
	}

	private long getStatusCode()
	{
		return SUCCESS_CODE;
	}

	private String getStatusDesc(long statusCode)
	{
		return statusCode == ERROR_CODE ? ERROR_TEXT : "";
	}
}
