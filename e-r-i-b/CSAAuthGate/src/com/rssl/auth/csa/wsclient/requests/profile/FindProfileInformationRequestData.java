package com.rssl.auth.csa.wsclient.requests.profile;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 30.10.13
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ������� ������ ���������� � ������� �������
 */
public class FindProfileInformationRequestData extends FindOrCreateProfileInformationRequestDataBase
{
	private static final String REQUEST_NAME = "findProfileInformationRq";

	/**
	 * �����������
	 * @param surName - �������
     * @param firstName - ���
	 * @param patrName - ��������
	 * @param passport - ����� � ����� ���������
	 * @param birthdate - ���� ��������
	 * @param tb - �������
	 */
	public FindProfileInformationRequestData(String surName, String firstName, String patrName, String passport, Calendar birthdate, String tb)
	{
		super(surName,firstName,patrName,passport,birthdate,tb);
	}

	public String getName()
	{
		return REQUEST_NAME;
	}


}
