package com.rssl.phizic.business.ermb;

import org.apache.commons.lang.ObjectUtils;

import java.util.Calendar;

/**
 * User: Moshenko
 * Date: 03.10.12
 * Time: 13:21
 * Сущность телефон клиента ЕРМБ
 */
public class ErmbClientPhone extends ErmbTmpPhone
{

	private boolean main = false;
	private Calendar creationDate = Calendar.getInstance();
	private ErmbProfileImpl profile;

	public ErmbClientPhone()
	{
	}

	public ErmbClientPhone(String number, ErmbProfileImpl profile)
	{
		super(number);
		this.profile = profile;
	}


	/**
	 * @return Признак того что телефон основной
	 */
	public boolean isMain()
	{
		return main;
	}

	public void setMain(boolean main)
	{
		this.main = main;
	}

	/**
	 * @return профиль ЕРМБ которому принадлежит телефон
	 */
	public ErmbProfileImpl getProfile()
	{
		return profile;
	}

	public void setProfile(ErmbProfileImpl profile)
	{
		this.profile = profile;
	}

    public String toString()
    {
        return getNumber();
    }

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;

		if (o == null)
			return false;

		else if (getClass() != o.getClass())
			return false;

		if (!super.equals(o))
			return false;

		ErmbClientPhone that = (ErmbClientPhone) o;

		if (!ObjectUtils.equals(this.profile.getId(), that.profile.getId()))
			return false;

		if (main != that.main)
			return false;

		return true;
	}

	/**
	 * @return Дата создания.
	 */
	public Calendar getCreationDate()
	{
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}
}
