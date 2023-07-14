package com.rssl.phizic.business.dictionaries.providers.locale;

import com.rssl.phizic.business.locale.dynamic.resources.multi.block.MultiBlockLanguageResources;

/**
 * @author komarov
 * @ created 06.10.2014
 * @ $Author$
 * @ $Revision$
 */
public class ServiceProviderResources extends MultiBlockLanguageResources
{
	private String name;
	private String legalName;
	private String alias;
	private String bankName;
	private String description;
	private String tipOfProvider;
	private String commissionMessage;   //��������� � ��������
	private String nameOnBill;
	private String nameService;

	/**
	 * @return ������ ������������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name ������ ������������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ����������� ������������ ���������� �����
	 */
	public String getLegalName()
	{
		return legalName;
	}

	/**
	 * @param legalName ����������� ������������ ���������� �����
	 */
	public void setLegalName(String legalName)
	{
		this.legalName = legalName;
	}

	/**
	 * @return ���������� ���������� �����
	 */
	public String getAlias()
	{
		return alias;
	}

	/**
	 * @param alias ���������� ���������� �����
	 */
	public void setAlias(String alias)
	{
		this.alias = alias;
	}

	/**
	 * @return ������������ �����
	 */
	public String getBankName()
	{
		return bankName;
	}

	/**
	 * @param bankName ������������ �����
	 */
	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	/**
	 * @return ����������� ��� ��������
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description ����������� ��� ��������
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return ��������� �� ���������� �����
	 */
	public String getTipOfProvider()
	{
		return tipOfProvider;
	}

	/**
	 * @param tipOfProvider ��������� �� ���������� �����
	 */
	public void setTipOfProvider(String tipOfProvider)
	{
		this.tipOfProvider = tipOfProvider;
	}

	/**
	 * @return ��������� � ��������
	 */
	public String getCommissionMessage()
	{
		return commissionMessage;
	}

	/**
	 * @param commissionMessage ��������� � ��������
	 */
	public void setCommissionMessage(String commissionMessage)
	{
		this.commissionMessage = commissionMessage;
	}

	/**
	 * @return ������������ ����������, ��������� � ����
	 */
	public String getNameOnBill()
	{
		return nameOnBill;
	}

	/**
	 * @param nameOnBill ������������ ����������, ��������� � ����
	 */
	public void setNameOnBill(String nameOnBill)
	{
		this.nameOnBill = nameOnBill;
	}

	/**
	 * @return �������� �������
	 */
	public String getNameService()
	{
		return nameService;
	}

	/**
	 * @param nameService �������� �������
	 */
	public void setNameService(String nameService)
	{
		this.nameService = nameService;
	}
}
