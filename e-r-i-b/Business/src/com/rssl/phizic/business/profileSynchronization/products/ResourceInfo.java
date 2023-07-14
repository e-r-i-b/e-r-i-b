package com.rssl.phizic.business.profileSynchronization.products;

/**
 * @author lepihina
 * @ created 28.05.14
 * $Author$
 * $Revision$
 *
 * Настройки отображения и видимости продуктов (для хранения в централизованном хранилище)
 */
public class ResourceInfo
{
	private String number;
	private String name;
	private Boolean isShowInSystem;
	private Boolean isShowInMobile;
	private Boolean isShowInSocial;
	private Boolean isShowInATM;
	private Boolean isShowInSms;
	private Boolean isShowInMain;
	private Integer positionNumber;

	/**
	 * @return идентификатор продукта(например, номер карты, счета и пр.)
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * @param number - идентификатор продукта(например, номер карты, счета и пр.)
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}

	/**
	 * @return алиас продукта
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name - алиас продукта
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return признак видимости объекта в системе
	 */
	public Boolean getShowInSystem()
	{
		return isShowInSystem;
	}

	/**
	 * @param showInSystem - признак видимости объекта в системе
	 */
	public void setShowInSystem(Boolean showInSystem)
	{
		isShowInSystem = showInSystem;
	}

	/**
	 * @return признак видимости объекта в мобильном приложении
	 */
	public Boolean getShowInMobile()
	{
		return isShowInMobile;
	}

	/**
	 * @param showInMobile - признак видимости объекта в мобильном приложении
	 */
	public void setShowInMobile(Boolean showInMobile)
	{
		isShowInMobile = showInMobile;
	}

    /**
     * @return признак видимости объекта в соц. приложении
     */
    public Boolean getShowInSocial()
    {
        return isShowInSocial;
    }

    /**
     * @param showInSocial - признак видимости объекта в соц. приложении
     */
    public void setShowInSocial(Boolean showInSocial)
    {
        isShowInSocial = showInSocial;
	}

	/**
	 * @return признак видимости объекта в устройствах самообслуживания
	 */
	public Boolean getShowInATM()
	{
		return isShowInATM;
	}

	/**
	 * @param showInATM - признак видимости объекта в устройствах самообслуживания
	 */
	public void setShowInATM(Boolean showInATM)
	{
		isShowInATM = showInATM;
	}

	/**
	 * @return признак видимости объекта в СМС канале
	 */
	public Boolean getShowInSms()
	{
		return isShowInSms;
	}

	/**
	 * @param showInSms - признак видимости объекта в СМС канале
	 */
	public void setShowInSms(Boolean showInSms)
	{
		isShowInSms = showInSms;
	}

	/**
	 * @return признак отображения объекта на главной странице
	 */
	public Boolean getShowInMain()
	{
		return isShowInMain;
	}

	/**
	 * @param showInMain - признак отображения объекта на главной странице
	 */
	public void setShowInMain(Boolean showInMain)
	{
		isShowInMain = showInMain;
	}

	/**
	 * @return порядковый номер продукта в списке
	 */
	public Integer getPositionNumber()
	{
		return positionNumber;
	}

	/**
	 * @param positionNumber - порядковый номер продукта в списке
	 */
	public void setPositionNumber(Integer positionNumber)
	{
		this.positionNumber = positionNumber;
	}
}
