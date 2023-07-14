package com.rssl.phizic.business.login;

import com.rssl.phizic.authgate.AuthData;
import com.rssl.phizic.authgate.AuthentificationSource;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.person.PersonDocument;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.utils.StringHelper;

/**
 * ������ ����������� ��������� ��� ������������� ������� � ����
 *
 * @author khudyakov
 * @ created 30.12.2011
 * @ $Author$
 * @ $Revision$
 */
public class LoginInfoHelper
{
	/**
	 * �������� ������ �������
	 * @param authData - ������ �������������� �������
	 * @param authToken - ���� ��������������
	 * @param header - ������������
	 * @param authSrc - �������� ��������������
	 * @return ������ � ������� ������� ��� �����������
	 */
	public static StringBuilder getPersonLogInfo(AuthData authData, String authToken, String header, AuthentificationSource authSrc)
	{
		return getLogInfo(authSrc, authToken, header, authData.getLastName(), authData.getFirstName(), authData.getMiddleName(), null, authData.getDocument(), authData.getBirthDate());
	}

	/**
	 * �������� ������ �������
	 * @param client - ������
	 * @param authToken - ���� ��������������
	 * @param header - ���������
	 * @param authSrc - �������� ��������������
	 * @return ������ � ������� ������� ��� �����������
	 */
	public static StringBuilder getPersonLogInfo(Client client, String authToken, String header, AuthentificationSource authSrc)
	{
		ClientDocument document = client.getDocuments().get(0);
		return getLogInfo(authSrc, authToken, header, client.getSurName(), client.getFirstName(), client.getPatrName(), document.getDocSeries(), document.getDocNumber(), String.format("%1$tY-%1$tm-%1$td", client.getBirthDay().getTime()));
	}

	/**
	 * �������� ������ �������
	 * @param person - ������� �� ����� ��
	 * @param authToken - ���� ��������������
	 * @param header - ���������
	 * @param authSrc - �������� ��������������
	 * @return ������ � ������� ������� ��� �����������
	 */
	public static StringBuilder getPersonLogInfo(Person person, String authToken, String header, AuthentificationSource authSrc)
	{
		PersonDocument document = person.getPersonDocuments().iterator().next();
		return getLogInfo(authSrc, authToken, header, person.getSurName(), person.getFirstName(), person.getPatrName(), document.getDocumentSeries(), document.getDocumentNumber(), String.format("%1$tY-%1$tm-%1$td", person.getBirthDay().getTime()));
	}

	/**
	 * �������� ������ �������
	 * @param client ������
	 * @param authSrc �������� ��������������
	 * @param authToken ���� ��������������
	 * @return ������ � ������� ������� ��� �����������
	 */
	public static StringBuilder getClientLogInfo(Client client, AuthentificationSource authSrc, String authToken)
	{
		StringBuilder logText = new StringBuilder();
		if (client == null)
		{
			logText = LoginInfoHelper.getMainLogInfo(authSrc, authToken, "���������� �� ������� �� ��������, ������ ������ �� ��������� �����, �� ���+���+�� ���������� � ��� ����� ���.");
		}
		else
		{
			if (client.isUDBO())
			{
				logText = LoginInfoHelper.getPersonLogInfo(client, authToken, ". ���������� �� ������� ��������. ������ ����.", authSrc);
			}
			else
			{
				logText = LoginInfoHelper.getMainLogInfo(authSrc, authToken, "���������� �� ������� �� ��������. ������ ���� ���������, ���� ����.");
			}
		}

		return logText;
	}

	/**
	 * @param authSrc - �������� ��������������
	 * @param authToken - ���� ��������������
	 * @param text - ����� ��� ������ ������������
	 * @return �������������� ����������� ����� ��� ����������� �����
	 */
	public static StringBuilder getMainLogInfo(AuthentificationSource authSrc, String authToken, String text)
	{
		StringBuilder logText = new StringBuilder();
		if (authSrc!= null && !StringHelper.isEmpty(authSrc.getDescription()))
			logText.append("[").append(authSrc.getDescription()).append("] ");
		if (!StringHelper.isEmpty(authToken))
			logText.append("AuthToken = ").append(authToken);
		if (!StringHelper.isEmpty(text))
			logText.append(" ").append(text);
		return logText;
	}

	/**
	 * ��������� ������ � ������� ������������ ��� �����������
	 */
	private static StringBuilder getLogInfo(AuthentificationSource authSrc, String authToken, String header, String surName, String firstName, String patrName, String series, String num, String birthDay)
	{
		StringBuilder logText = getMainLogInfo(authSrc, authToken, null);
		if (!StringHelper.isEmpty(header))
				logText.append(" ").append(header);
		logText.append(" ��� = ").append(surName).append(" ").append(firstName).append(" ").append(patrName);
		if (!StringHelper.isEmpty(series) || !StringHelper.isEmpty(num))
		{
			logText.append(" ��� = ");
			if (!StringHelper.isEmpty(series))
				logText.append(series).append(" ");
			if (!StringHelper.isEmpty(num))
				logText.append(num);
		}
		logText.append(" ���� �������� = ").append(birthDay);
		return logText;
	}
}
