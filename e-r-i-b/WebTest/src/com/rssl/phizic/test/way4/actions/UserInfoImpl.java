package com.rssl.phizic.test.way4.actions;

import com.rssl.phizic.gate.mobilebank.UserInfo;
import com.rssl.phizic.common.types.client.LoginType;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Информация о пользователе
 * @author niculichev
 * @ created 24.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class UserInfoImpl implements UserInfo
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

	public boolean isMainCard()
	{
		return mainCard;
	}

	public void setMainCard(boolean mainCard)
	{
		this.mainCard = mainCard;
	}

	public boolean isActiveCard()
	{
		return activeCard;
	}

	public void setActiveCard(boolean activeCard)
	{
		this.activeCard = activeCard;
	}

    public List<String> getCards()
    {
        return Collections.singletonList(cardNumber);
	}
}
