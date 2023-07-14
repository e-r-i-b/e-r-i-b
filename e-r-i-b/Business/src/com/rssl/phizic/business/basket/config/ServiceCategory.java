package com.rssl.phizic.business.basket.config;

/**
 * @author tisov
 * @ created 15.04.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * Категория услуг, служебный класс, используемый для представления информации
 * из файла настроек корзины услуг в конфиге BasketConfig
 */
public class ServiceCategory
{
	private Long id;                //идентификатор услуги
	private String code;            //код категории услуг
	private String buttonName;      //название кнопки для категории услуг
	private String accountName;     //название счёта для категории услуг

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
