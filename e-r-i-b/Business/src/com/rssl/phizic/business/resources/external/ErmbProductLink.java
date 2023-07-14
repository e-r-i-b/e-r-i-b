package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ermb.ErmbConfig;
import com.rssl.phizic.utils.StringHelper;

/**
 * ������� ����� ��� ������ �� ��������, ��� ������� �������� ����������� �������������� ���-��������� � ��������� �����������
 @author: Egorovaa
 @ created: 26.10.2012
 @ $Author$
 @ $Revision$
 */
public abstract class ErmbProductLink extends ShowInMobileProductLink implements BankrollProductLink
{
	private String autoSmsAlias;

	private boolean ermbNotification;

	private String ermbSmsAlias;

    private boolean showInSms;

	private Boolean showedArchiveMessage;

	///////////////////////////////////////////////////////////////////////////

	public String getAutoSmsAlias()
	{
		return autoSmsAlias;
	}

	public void setAutoSmsAlias(String autoSmsAlias)
	{
		this.autoSmsAlias = autoSmsAlias;
	}

	public boolean getErmbNotification()
	{
		return ermbNotification;
	}

	public void setErmbNotification(boolean ermbNotification)
	{
		this.ermbNotification = ermbNotification;
		if (ConfigFactory.getConfig(ErmbConfig.class).getProductAvailabilityCommonAttribute())
			this.showInSms = ermbNotification;
	}

	public String getErmbSmsAlias()
	{
		return ermbSmsAlias;
	}

	public void setErmbSmsAlias(String ermbSmsAlias)
	{
		this.ermbSmsAlias = ermbSmsAlias;
	}

    public boolean getShowInSms()
    {
	    if (ConfigFactory.getConfig(ErmbConfig.class).getProductAvailabilityCommonAttribute())
            return ermbNotification;
	    return showInSms;
    }

    public void setShowInSms(boolean showInSms)
    {
        this.showInSms = showInSms;
	    if (ConfigFactory.getConfig(ErmbConfig.class).getProductAvailabilityCommonAttribute())
            this.ermbNotification = showInSms;
    }

	/**
	 * @return ������� ��� ����������� ��������� ������� � ����������� �������� � �����
	 */
	public Boolean getShowedArchiveMessage()
	{
	    return showedArchiveMessage;
	}

	/**
	 * ������������ ������� ��� ����������� ��������� ������� � ����������� �������� � �����
	 * @param showedArchiveMessage - �������
	 */
	public void setShowedArchiveMessage(Boolean showedArchiveMessage)
	{
	    this.showedArchiveMessage = showedArchiveMessage;
	}

	/**
	 * ������� ���-����� ��� ��������� ��������� �������
	 * @return  ���-����� ��� ��������� ��������� �������:
	 * ���� ���������� ����� �� ����� - ���������� �������������� ���-�����, ����� ���������� ����� � ������� "����������_���_����� (��������������_���_�����)"
	 */
	public String getSmsResponseAlias()
	{
		if (StringHelper.isEmpty(ermbSmsAlias))
			return autoSmsAlias;

		return ermbSmsAlias + " (" + autoSmsAlias + ")";
	}
}
