package com.rssl.phizicgate.mobilebank.csa;

import com.rssl.phizic.gate.mobilebank.UserInfo;
import com.rssl.phizic.common.types.client.LoginType;

import java.util.Calendar;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author krenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 * Информация о пользователе
 */

public class MBUserInfo implements UserInfo, Serializable
{
	private String userId;
	private String cbCode;
	private String cardNumber;
	private Calendar birthdate;
	private String firstname;
	private String patrname;
	private String surname;
	private String passport;
	private LoginType loginType;
	private boolean mainCard;
	private boolean activeCard;


	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getCbCode()
	{
		return cbCode;
	}

	public void setCbCode(String cbCode)
	{
		this.cbCode = cbCode;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setBirthdate(Calendar birthdate)
	{
		this.birthdate = birthdate;
	}

	public Calendar getBirthdate()
	{
		return birthdate;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public String getFirstname()
	{
		return firstname;
	}

	public void setPatrname(String partname)
	{
		this.patrname = partname;
	}

	public String getPatrname()
	{
		return patrname;
	}

	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	public String getSurname()
	{
		return surname;
	}

	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	public String getPassport()
	{
		return passport;
	}

	public LoginType getLoginType()
	{
		return loginType;
	}

	public void setLoginType(LoginType loginType)
	{
		this.loginType = loginType;
	}

	/**
	 * @return признак, является ли карта основной
	 */
	public boolean isMainCard()
	{
		return mainCard;
	}

	/**
	 * Установить признак основная карта или нет.
	 * @param mainCard признак, является ли карта основной
	 */
	public void setMainCard(boolean mainCard)
	{
		this.mainCard = mainCard;
	}

	/**
	 * @return признак активная ли карта
	 */
	public boolean isActiveCard()
	{
		return activeCard;
	}

	/**
	 * Установить признак активности карты
	 * @param activeCard активная ли карта
	 */
	public void setActiveCard(boolean activeCard)
	{
		this.activeCard = activeCard;
	}

    public List<String> getCards()
    {
        return Collections.singletonList(cardNumber);
	}
}
