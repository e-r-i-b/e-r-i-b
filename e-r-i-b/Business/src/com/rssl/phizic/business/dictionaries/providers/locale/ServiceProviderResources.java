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
	private String commissionMessage;   //сообщение о комиссии
	private String nameOnBill;
	private String nameService;

	/**
	 * @return Полное наименование
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name Полное наименование
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return Юридическое наименование поставщика услуг
	 */
	public String getLegalName()
	{
		return legalName;
	}

	/**
	 * @param legalName Юридическое наименование поставщика услуг
	 */
	public void setLegalName(String legalName)
	{
		this.legalName = legalName;
	}

	/**
	 * @return Псевдонимы поставщика услуг
	 */
	public String getAlias()
	{
		return alias;
	}

	/**
	 * @param alias Псевдонимы поставщика услуг
	 */
	public void setAlias(String alias)
	{
		this.alias = alias;
	}

	/**
	 * @return Наименование банка
	 */
	public String getBankName()
	{
		return bankName;
	}

	/**
	 * @param bankName Наименование банка
	 */
	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	/**
	 * @return Комментарий для клиентов
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description Комментарий для клиентов
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return Подсказка по поставщику услуг
	 */
	public String getTipOfProvider()
	{
		return tipOfProvider;
	}

	/**
	 * @param tipOfProvider Подсказка по поставщику услуг
	 */
	public void setTipOfProvider(String tipOfProvider)
	{
		this.tipOfProvider = tipOfProvider;
	}

	/**
	 * @return Сообщение о комиссии
	 */
	public String getCommissionMessage()
	{
		return commissionMessage;
	}

	/**
	 * @param commissionMessage Сообщение о комиссии
	 */
	public void setCommissionMessage(String commissionMessage)
	{
		this.commissionMessage = commissionMessage;
	}

	/**
	 * @return Наименование поставщика, выводимое в чеке
	 */
	public String getNameOnBill()
	{
		return nameOnBill;
	}

	/**
	 * @param nameOnBill Наименование поставщика, выводимое в чеке
	 */
	public void setNameOnBill(String nameOnBill)
	{
		this.nameOnBill = nameOnBill;
	}

	/**
	 * @return название сервиса
	 */
	public String getNameService()
	{
		return nameService;
	}

	/**
	 * @param nameService название сервиса
	 */
	public void setNameService(String nameService)
	{
		this.nameService = nameService;
	}
}
