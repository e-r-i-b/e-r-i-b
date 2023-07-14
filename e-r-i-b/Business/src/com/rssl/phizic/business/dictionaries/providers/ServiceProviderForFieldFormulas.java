package com.rssl.phizic.business.dictionaries.providers;

/**
 * @author osminin
 * @ created 14.07.15
 * @ $Author$
 * @ $Revision$
 *
 * Заглушка для работы с поставщиком услуг в функционале связок с документами профиля
 */
public class ServiceProviderForFieldFormulas
{
	private Long id;
	private String  name;
	private String  INN;
	private String  account;
	private ServiceProviderState state;
	private Long imageId;
	private String nameService;
	private Long imageHelpId;

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return наименование
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name наименование
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return инн
	 */
	public String getINN()
	{
		return INN;
	}

	/**
	 * @param INN инн
	 */
	public void setINN(String INN)
	{
		this.INN = INN;
	}

	/**
	 * @return счет
	 */
	public String getAccount()
	{
		return account;
	}

	/**
	 * @param account счет
	 */
	public void setAccount(String account)
	{
		this.account = account;
	}

	/**
	 * @return статус
	 */
	public ServiceProviderState getState()
	{
		return state;
	}

	/**
	 * @param state статус
	 */
	public void setState(ServiceProviderState state)
	{
		this.state = state;
	}

	/**
	 * @return идентификатор иконки
	 */
	public Long getImageId()
	{
		return imageId;
	}

	/**
	 * @param imageId идентификатор иконки
	 */
	public void setImageId(Long imageId)
	{
		this.imageId = imageId;
	}

	/**
	 * @return услуга
	 */
	public String getNameService()
	{
		return nameService;
	}

	/**
	 * @param nameService услуга
	 */
	public void setNameService(String nameService)
	{
		this.nameService = nameService;
	}

	/**
	 * @return идентификатор графической подсказки
	 */
	public Long getImageHelpId()
	{
		return imageHelpId;
	}

	/**
	 * @param imageHelpId идентификатор графической подсказки
	 */
	public void setImageHelpId(Long imageHelpId)
	{
		this.imageHelpId = imageHelpId;
	}
}
