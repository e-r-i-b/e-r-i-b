package com.rssl.phizic.business.profile;

import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.skins.Skin;
import com.rssl.phizic.common.types.Entity;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.util.Calendar;
import java.util.Map;
import java.util.HashMap;

/**
 * @author gulov
 * @ created 27.05.2010
 * @ $Authors$
 * @ $Revision$
 */
public class Profile implements ConfirmableObject, Entity
{
	private Long id;
	private Long loginId;
	private String regionCode;  //Код региона, к которому привязан клиент
	private Skin skin; //Персональный стиль пользователя
    private Boolean showPersonalFinance = false;  //Признак доступности персональных финансов
    private Calendar startUsePersonalFinance;  //Дата начала использования персональных финансов
	private boolean regionSelected = false;
	private Money mobileWallet;                     //мобильный кошелек
	private String tariffPlanCode;    //актуальный тарифный план клиента (с проверкой доступности по правам "ReducedRateService")
	private String stash; //"Избранное" в мобильном приложении
	private Integer registrationWindowShowCount;  //количество выполненных отображений окна с предложением о регистрации
	private Calendar lastUsingFinancesDate; // дата последнего использования АЛФ
	private Integer usingFinancesCount; // количество дней использования АЛФ (с перерывом не более N дней, где N задается в настройках)
	private Integer usingFinancesEveryThreeDaysCount; // количество дней использования АЛФ (с перерывом не более 3-х дней)
	private Calendar lastUpdateCardOperationClaimsDate;
	private Long avatarImageId; //идентификатор аватара.

	public void selectRegion(Region region)
	{
		String code = region == null ? null : (String)region.getSynchKey();
		setRegionCode(code);
		setRegionSelected(true);
	}

	public boolean isRegionSelected()
	{
		return regionSelected;
	}

	/**
	 * Нельзя пользоваться этим методом!
	 * @see #selectRegion(Region)
	 * Этот метод предназначен только для hibernate
	 */
	private void setRegionSelected(boolean regionSelected)
	{
		this.regionSelected = regionSelected;
	}

	public Long getId()
	{
		return id;
	}

	public byte[] getSignableObject()
	{
		return new byte[0];
	}

	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{
		
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getLoginId()
	{
		return loginId;
	}

	public void setLoginId(Long loginId)
	{
		this.loginId = loginId;
	}

	public Skin getSkin()
	{
		return skin;
	}

	public void setSkin(Skin skin)
	{
		this.skin = skin;
	}

	public Boolean isShowPersonalFinance()
	{
		return showPersonalFinance;
	}

	public void setShowPersonalFinance(Boolean showPersonalFinance)
	{
		this.showPersonalFinance = showPersonalFinance;
	}

	public Money getMobileWallet()
	{
		return mobileWallet;
	}

	public void setMobileWallet(Money mobileWallet)
	{
		this.mobileWallet = mobileWallet;
	}

	public String getTariffPlanCode()
	{
		return tariffPlanCode;
	}

	public void setTariffPlanCode(String tariffPlanCode)
	{
		this.tariffPlanCode = tariffPlanCode;
	}

	public String getStash()
	{
		return stash;
	}

	public void setStash(String stash)
	{
		this.stash = stash;
	}

	public Integer getRegistrationWindowShowCount()
	{
		return registrationWindowShowCount;
	}

	public void setRegistrationWindowShowCount(Integer registrationWindowShowCount)
	{
		this.registrationWindowShowCount = registrationWindowShowCount;
	}

	public Calendar getStartUsePersonalFinance()
	{
		return startUsePersonalFinance;
	}

	public void setStartUsePersonalFinance(Calendar startUsePersonalFinance)
	{
		this.startUsePersonalFinance = startUsePersonalFinance;
	}

	/**
	 * @return дата последнего использования АЛФ
	 */
	public Calendar getLastUsingFinancesDate()
	{
		return lastUsingFinancesDate;
	}

	/**
	 * @param lastUsingFinancesDate - дата последнего использования АЛФ
	 */
	public void setLastUsingFinancesDate(Calendar lastUsingFinancesDate)
	{
		this.lastUsingFinancesDate = lastUsingFinancesDate;
	}

	/**
	 * @return количество дней использования АЛФ (с перерывом не более N дней, где N задается в настройках)
	 */
	public Integer getUsingFinancesCount()
	{
		return usingFinancesCount;
	}

	/**
	 * @param usingFinancesCount - количество дней использования АЛФ (с перерывом не более N дней, где N задается в настройках)
	 */
	public void setUsingFinancesCount(Integer usingFinancesCount)
	{
		this.usingFinancesCount = usingFinancesCount;
	}

	/**
	 * @return количество дней использования АЛФ (с перерывом не более 3-х дней)
	 */
	public Integer getUsingFinancesEveryThreeDaysCount()
	{
		return usingFinancesEveryThreeDaysCount;
	}

	/**
	 * @param usingFinancesEveryThreeDaysCount - количество дней использования АЛФ (с перерывом не более 3-х дней)
	 */
	public void setUsingFinancesEveryThreeDaysCount(Integer usingFinancesEveryThreeDaysCount)
	{
		this.usingFinancesEveryThreeDaysCount = usingFinancesEveryThreeDaysCount;
	}

	/**
	 * @return дата последнего обновления заявок на загрузку карточных операций джобом
	 */
	public Calendar getLastUpdateCardOperationClaimsDate()
	{
		return lastUpdateCardOperationClaimsDate;
	}

	/**
	 * @param lastUpdateCardOperationClaimsDate - дата последнего обновления заявок на загрузку карточных операций джобом
	 */
	public void setLastUpdateCardOperationClaimsDate(Calendar lastUpdateCardOperationClaimsDate)
	{
		this.lastUpdateCardOperationClaimsDate = lastUpdateCardOperationClaimsDate;
	}

	public Long getAvatarImageId()
	{
		return avatarImageId;
	}

	public void setAvatarImageId(Long avatarImageId)
	{
		this.avatarImageId = avatarImageId;
	}

	/**
	 * Получение кода региона, к которому привязан клиент.
	 * @return Код региона или null, если клиент не привязан к конкретному региону.
	 */
	public String getRegionCode()
	{
		return regionCode;
	}

	/**
	 * Установка кода региона, к которому привязан клиент.
	 * Для изменения региона нельзя пользоваться этим методом!
	 * Этот метод предназначен только для hibernate
	 * @see #selectRegion(Region)
	 */
	public void setRegionCode(String regionCode)
	{
		this.regionCode = regionCode;
	}
}
