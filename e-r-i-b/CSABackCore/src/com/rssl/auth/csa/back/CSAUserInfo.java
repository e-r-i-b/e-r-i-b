package com.rssl.auth.csa.back;

import com.rssl.phizic.gate.mobilebank.UserInfo;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.mobilebank.csa.MBUserInfo;

import java.util.Calendar;
import java.util.List;

/**
 * @author krenev
 * @ created 23.08.2012
 * @ $Author$
 * @ $Revision$
 * Информация о пользователе
 */

public class CSAUserInfo
{
	/**
	 * источник получения данных о клиенте
	 */
	public enum Source
	{
		CSA,
		WAY4U,
		TEMPLATE //данные получены на основе шаблона.
	}

	private Source source;
	private String userId;
	private String cbCode;
	private String cardNumber;
	private Calendar birthdate;
	private String firstname;
	private String patrname;
	private String surname;
	private String passport;
	private List<String> cards;

	public CSAUserInfo(Source source)
	{
		this.source = source;
	}

	public CSAUserInfo(String cbCode, String firstname, String surname, String patrname, String passport, Calendar birthdate)
	{
		if (StringHelper.isEmpty(firstname))
		{
			throw new IllegalArgumentException("Имя не может быть null");
		}
		if (StringHelper.isEmpty(surname))
		{
			throw new IllegalArgumentException("Фамилия не может быть null");
		}
		if (StringHelper.isEmpty(passport))
		{
			throw new IllegalArgumentException("ДУЛ не может быть null");
		}
		if (birthdate == null)
		{
			throw new IllegalArgumentException("Дата рождения не может быть null");
		}
		this.birthdate = birthdate;
		this.cbCode = cbCode;
		this.firstname = firstname;
		this.surname = surname;
		this.patrname = patrname;
		this.passport = passport;
		this.source = Source.TEMPLATE;
	}

	public CSAUserInfo(UserInfo userInfo, Source source)
	{
		if (userInfo == null)
		{
			throw new IllegalArgumentException("Информация о пользователе не может быть null");
		}
		setFirstname(userInfo.getFirstname());
		setPatrname(userInfo.getPatrname());
		setSurname(userInfo.getSurname());
		setBirthdate(userInfo.getBirthdate());
		setPassport(userInfo.getPassport());
		setCbCode(userInfo.getCbCode());
		setUserId(userInfo.getUserId());
		setCardNumber(userInfo.getCardNumber());
		setCards(userInfo.getCards());
		this.source = source;
	}

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

	public Source getSource()
	{
		return source;
	}

	public void setSource(Source source)
	{
		this.source = source;
	}

	/**
	 * @return Список карт идентифицированного пользователя. Заполняется по возможности.
	 * В случае null, карты не были получены(контекст строился без интергации с ВС)
	 */
	public List<String> getCards()
	{
		return cards;
	}

	public void setCards(List<String> cards)
	{
		this.cards = cards;
	}
}