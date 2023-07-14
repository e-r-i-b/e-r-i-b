package com.rssl.phizic.business.basket.config;

/**
 * @author tisov
 * @ created 15.04.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ��������� �����, ��������� �����, ������������ ��� ������������� ����������
 * �� ����� �������� ������� ����� � ������� BasketConfig
 */
public class ServiceCategory
{
	private Long id;                //������������� ������
	private String code;            //��� ��������� �����
	private String buttonName;      //�������� ������ ��� ��������� �����
	private String accountName;     //�������� ����� ��� ��������� �����

	public ServiceCategory(String code,String buttonName, String accountName)
	{
		this.code = code;
		this.buttonName = buttonName;
		this.accountName = accountName;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	public String getButtonName()
	{
		return buttonName;
	}

	public void setButtonName(String buttonName)
	{
		this.buttonName = buttonName;
	}

	public String getAccountName()
	{
		return accountName;
	}

	public void setAccountName(String accountName)
	{
		this.accountName = accountName;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}
}
