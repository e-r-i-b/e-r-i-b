package com.rssl.phizic.config.mobile;

/**
 * ���������� �� ��������� ��������� �� ��������
 * @author Jatsky
 * @ created 09.08.13
 * @ $Author$
 * @ $Revision$
 */

public class MobilePlatformInfo
{
	private String platformId; //�� ���������
	private String name; //�������� �������
	private Integer version; //����������� ���������� ������
	private String errText; //��������� ��� �������, ���� ������ �����������
	private String bankURL;//������ ��� ���������� �� ����� �����
	private String externalURL;//������ ��� ���������� �� ����� ������������
	private String qrCode; //�������� QR ����
	private Boolean social; //������� ���������� ��� ���.�����
	private Boolean useCaptcha; //������� ������������� ����� ��� �����������
	private String unallowedBrowsers; //����������� ��������

	public String getPlatformId()
	{
		return platformId;
	}

	public void setPlatformId(String platformId)
	{
		this.platformId = platformId;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public Integer getVersion()
	{
		return version;
	}

	public void setVersion(Integer version)
	{
		this.version = version;
	}

	public String getErrText()
	{
		return errText;
	}

	public void setErrText(String errText)
	{
		this.errText = errText;
	}

	public String getBankURL()
	{
		return bankURL;
	}

	public void setBankURL(String bankURL)
	{
		this.bankURL = bankURL;
	}

	public String getExternalURL()
	{
		return externalURL;
	}

	public void setExternalURL(String externalURL)
	{
		this.externalURL = externalURL;
	}

	public String getQrCode()
	{
		return qrCode;
	}

	public void setQrCode(String qrCode)
	{
		this.qrCode = qrCode;
	}

	public Boolean getSocial()
	{
		return social;
	}

	public void setSocial(Boolean social)
	{
		this.social = social;
	}

	public Boolean getUseCaptcha()
	{
		return useCaptcha;
	}

	public void setUseCaptcha(Boolean useCaptcha)
	{
		this.useCaptcha = useCaptcha;
	}

	public String getUnallowedBrowsers()
	{
		return unallowedBrowsers;
	}

	public void setUnallowedBrowsers(String unallowedBrowsers)
	{
		this.unallowedBrowsers = unallowedBrowsers;
	}
}
