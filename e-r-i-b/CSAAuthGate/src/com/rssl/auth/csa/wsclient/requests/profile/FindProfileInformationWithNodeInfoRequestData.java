package com.rssl.auth.csa.wsclient.requests.profile;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 13.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ������� ������ ���������� � ������� ������� � ������ ����������� �� ����� � ������� ������� ������
 */

public class FindProfileInformationWithNodeInfoRequestData extends FindProfileInformationRequestData
{
	private static final String REQUEST_NAME = "findProfileInformationWithNodeInfoRq";

	/**
	 * �����������
	 * @param surName - �������
	 * @param firstName - ���
	 * @param patrName - ��������
	 * @param passport - ����� � ����� ���������
	 * @param birthdate - ���� ��������
	 * @param tb - �������
	 */
	public FindProfileInformationWithNodeInfoRequestData(String surName, String firstName, String patrName, String passport, Calendar birthdate, String tb)
	{
		super(surName, firstName, patrName, passport, birthdate, tb);
	}

	@Override
	public String getName()
	{
		return REQUEST_NAME;
	}
}
