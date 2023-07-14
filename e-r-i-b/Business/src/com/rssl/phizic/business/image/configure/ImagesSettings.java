package com.rssl.phizic.business.image.configure;

/**
 * @author akrenev
 * @ created 26.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ����������� ����������� � ��
 */

public class ImagesSettings
{
	private Integer advertising;
	private Integer providerLogo;
	private Integer providerPanel;
	private Integer providerHelp;

	/**
	 * @return ����������� �� ������������ ������ �������� ��� ������� �� ������� ��������
	 */
	public Integer getAdvertising()
	{
		return advertising;
	}

	/**
	 * ������ ����������� �� ������������ ������ �������� ��� ������� �� ������� ��������
	 * @param advertising �����������
	 */
	public void setAdvertising(Integer advertising)
	{
		this.advertising = advertising;
	}

	/**
	 * @return ����������� �� ������������ ������ �������� ��� �������� ���������� �����
	 */
	public Integer getProviderLogo()
	{
		return providerLogo;
	}

	/**
	 * ������ ����������� �� ������������ ������ �������� ��� �������� ���������� �����
	 * @param providerLogo �����������
	 */
	public void setProviderLogo(Integer providerLogo)
	{
		this.providerLogo = providerLogo;
	}

	/**
	 * @return ����������� �� ������������ ������ �������� ��� ������ �� ������ ������� ������
	 */
	public Integer getProviderPanel()
	{
		return providerPanel;
	}

	/**
	 * ������ ����������� �� ������������ ������ �������� ��� ������ �� ������ ������� ������
	 * @param providerPanel �����������
	 */
	public void setProviderPanel(Integer providerPanel)
	{
		this.providerPanel = providerPanel;
	}

	/**
	 * @return ����������� �� ������������ ������ �������� ��� ����������� ��������� ���������� �����
	 */
	public Integer getProviderHelp()
	{
		return providerHelp;
	}

	/**
	 * ������ ����������� �� ������������ ������ �������� ��� ����������� ��������� ���������� �����
	 * @param providerHelp �����������
	 */
	public void setProviderHelp(Integer providerHelp)
	{
		this.providerHelp = providerHelp;
	}
}
