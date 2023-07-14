package com.rssl.phizic.web.client.login;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * ����� ��� ����������� �� �������� ���������� �������� ���� ��� ����� � ����
 * User: kichinova
 * Date: 15.12.14
 * Time: 10:16
 */
public class ConnectUdboLoginForm extends ActionFormBase
{
	private String cancelMessageTitle;
	private String cancelMessageText;

	/**
	 * @return ����� ��������� ��� ������ �� ����������� ����
	 */
	public String getCancelMessageText()
	{
		return cancelMessageText;
	}

	/**
	 * ������ ����� ��������� ��� ������ �� ����������� ����
	 * @param cancelMessageText - �����
	 */
	public void setCancelMessageText(String cancelMessageText)
	{
		this.cancelMessageText = cancelMessageText;
	}

	/**
	 * @return ��������� ��������� ��� ������ �� ����������� ����
	 */
	public String getCancelMessageTitle()
	{
		return cancelMessageTitle;
	}

	/**
	 * ������ ��������� ��������� ��� ������ �� ����������� ����
	 * @param cancelMessageTitle - ���������
	 */
	public void setCancelMessageTitle(String cancelMessageTitle)
	{
		this.cancelMessageTitle = cancelMessageTitle;
	}
}
