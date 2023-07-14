package com.rssl.phizic.business.ext.sbrf.csa.blockingrules.locale;

import com.rssl.phizic.locale.dynamic.resources.LanguageResource;

/**
 * ����� � ���������������� ����������� ��� ����������
 * @author komarov
 * @ created 18.05.2015
 * @ $Author$
 * @ $Revision$
 */
public class BlockingRulesResources extends LanguageResource
{
	private String ERIBMessage;     //��������� ��� ������ ����
	private String mapiMessage;     //��������� ��� ������ ����
	private String ATMMessage;      //��������� ��� ������ ���
	private String ERMBMessage;     //��������� ��� ������ ����

	/**
	 * @return ��������� ��� ������ ����
	 */
	public String getERIBMessage()
	{
		return ERIBMessage;
	}

	/**
	 * @param ERIBMessage ��������� ��� ������ ����
	 */

	public void setERIBMessage(String ERIBMessage)
	{
		this.ERIBMessage = ERIBMessage;
	}

	/**
	 * @return ��������� ��� ������ ����
	 */
	public String getMapiMessage()
	{
		return mapiMessage;
	}

	/**
	 * @param mapiMessage ��������� ��� ������ ����
	 */
	public void setMapiMessage(String mapiMessage)
	{
		this.mapiMessage = mapiMessage;
	}

	/**
	 * @return ��������� ��� ������ ���
	 */
	public String getATMMessage()
	{
		return ATMMessage;
	}

	/**
	 * @param ATMMessage ��������� ��� ������ ���
	 */
	public void setATMMessage(String ATMMessage)
	{
		this.ATMMessage = ATMMessage;
	}

	/**
	 * @return ��������� ��� ������ ����
	 */
	public String getERMBMessage()
	{
		return ERMBMessage;
	}

	/**
	 * @param ERMBMessage ��������� ��� ������ ����
	 */
	public void setERMBMessage(String ERMBMessage)
	{
		this.ERMBMessage = ERMBMessage;
	}
}
