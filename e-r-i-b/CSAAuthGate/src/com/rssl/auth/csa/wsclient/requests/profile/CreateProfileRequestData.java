package com.rssl.auth.csa.wsclient.requests.profile;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 10.11.13
 * @ $Author$
 * @ $Revision$
 *
 * ������ ��� ������� �������� ������� ������� � ���
 */
public class CreateProfileRequestData extends FindOrCreateProfileInformationRequestDataBase
{
	private static final String REQUEST_NAME = "createProfileRq";

	/**
	 * �����������
	 * @param surName - �������
     * @param firstName - ���
	 * @param patrName - ��������
	 * @param passport - ����� � ����� ���������
	 * @param birthdate - ���� ��������
	 * @param tb - �������
	 */
	public CreateProfileRequestData(String surName, String firstName, String patrName, String passport, Calendar birthdate, String tb)
	{
		super(surName, firstName, patrName, passport, birthdate, tb);
	}

	public String getName()
	{
		return REQUEST_NAME;
	}
}
