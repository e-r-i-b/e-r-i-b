package com.rssl.auth.csa.wsclient.requests.profile;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 27.05.14
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ������� ��������� ������ ���� ������� �� ����������� ��� �� �������
 */
public class FindProfileCardNumberListRequestData extends FindOrCreateProfileInformationRequestDataBase
{
	private static final String REQUEST_NAME = "findProfileCardNumberListRq";

	/**
	 * �����������
	 * @param surName - �������
	 * @param firstName - ���
	 * @param patrName - ��������
	 * @param passport - ����� � ����� ���������
	 * @param birthdate - ���� ��������
	 * @param tb - �������
	 */
	public FindProfileCardNumberListRequestData(String surName, String firstName, String patrName, String passport, Calendar birthdate, String tb)
	{
		super(surName, firstName, patrName, passport, birthdate, tb);
	}

	public String getName()
	{
		return REQUEST_NAME;
	}
}
