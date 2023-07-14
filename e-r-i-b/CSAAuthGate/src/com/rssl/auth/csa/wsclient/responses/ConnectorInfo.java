package com.rssl.auth.csa.wsclient.responses;

import java.util.Calendar;

/**
 * ���������� � ����������
 * @author niculichev
 * @ created 15.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class ConnectorInfo
{
	private Long id;                         // ������������ ����������
	private String deviceState;             // ���������� ��������� ����������
	private String deviceInfo;              // ���������� �� ����������
	private String cbCode;                  // ������������� ������������
	private String userId;                  // �������� ����� ������������
	private String cardNumber;              // "����� �����"
	private String login;                   // �����/�����
	private Type type;                      // ��� ����������(TERMINAL, CSA, MAPI)
	private Calendar creationDate;          // ���� �������� ����������
	private Calendar passwordCreationDate;  // ���� �������� ������
	private String guid;                    // ������������� ����������
	private boolean pushSupported;           // �������������� �� ������� push
	private String deviceID;
	private Calendar lastSessionDate;        //���� ���������� �����

	public static enum Type
	{
		CSA,
		TERMINAL,
		MAPI,
        SOCIAL,
		DISPOSABLE
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getDeviceInfo()
	{
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo)
	{
		this.deviceInfo = deviceInfo;
	}

	public String getCbCode()
	{
		return cbCode;
	}

	public void setCbCode(String cbCode)
	{
		this.cbCode = cbCode;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public String getLogin()
	{
		return login;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public Type getType()
	{
		return type;
	}

	public void setType(Type type)
	{
		this.type = type;
	}

	public Calendar getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	public Calendar getPasswordCreationDate()
	{
		return passwordCreationDate;
	}

	public void setPasswordCreationDate(Calendar passwordCreationDate)
	{
		this.passwordCreationDate = passwordCreationDate;
	}

	public String getDeviceState()
	{
		return deviceState;
	}

	public void setDeviceState(String deviceState)
	{
		this.deviceState = deviceState;
	}

	public String getGuid()
	{
		return guid;
	}

	public void setGuid(String guid)
	{
		this.guid = guid;
	}

	public boolean isPushSupported()
	{
		return pushSupported;
	}

	public void setPushSupported(boolean pushSupported)
	{
		this.pushSupported = pushSupported;
	}

	public void setDeviceID(String deviceID)
	{
		this.deviceID = deviceID;
	}

	public String getDeviceID()
	{
		return deviceID;
	}

	public Calendar getLastSessionDate()
	{
		return lastSessionDate;
	}

	public void setLastSessionDate(Calendar lastSessionDate)
	{
		this.lastSessionDate = lastSessionDate;
	}
}
