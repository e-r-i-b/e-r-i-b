package com.rssl.phizicgate.wsgate.services.types;

import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.gate.mobilebank.UserInfo;

import java.util.Calendar;
import java.util.List;

/**
 * @author Jatsky
 * @ created 22.05.15
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
	private List<String> cards;

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

	public String getCardNumber()
	{
		return cardNumber;
	}

	public void setCardNumber(String cardNumber)
	{
		this.cardNumber = cardNumber;
	}

	public Calendar getBirthdate()
	{
		return birthdate;
	}

	public void setBirthdate(Calendar birthdate)
	{
		this.birthdate = birthdate;
	}

	public String getFirstname()
	{
		return firstname;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public String getPatrname()
	{
		return patrname;
	}

	public void setPatrname(String patrname)
	{
		this.patrname = patrname;
	}

	public String getSurname()
	{
		return surname;
	}

	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	public String getPassport()
	{
		return passport;
	}

	public void setPassport(String passport)
	{
		this.passport = passport;
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
		return cards;
	}

	public void setCards(List<String> cards)
	{
		this.cards = cards;
	}
}

