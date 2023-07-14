package com.rssl.phizgate.sbnkd.limits;

import java.util.Calendar;

/**
 * лимит на число за€вок выпуска карт дл€ —ЅЌ ƒ
 * @author basharin
 * @ created 26.01.15
 * @ $Author$
 * @ $Revision$
 */

public class PhoneLimit
{
	private Long id;                                //уникальный код лимита
	private String phone;                           //номер телефона клиента
	private PhoneLimitType type;                    //тип лимита
	private Long count;                             //количестов за€вок
	private Calendar lastDate;                      //дата. нужна дл€ обнул€ени€ счетчика раз в мес€ц

	/**
	 * @return - id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id - id
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return - номер телефона клиента
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * @param phone - номер телефона клиента
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	/**
	 * @return - тип лимита
	 */
	public PhoneLimitType getType()
	{
		return type;
	}

	/**
	 * @param type - тип лимита
	 */
	public void setType(PhoneLimitType type)
	{
		this.type = type;
	}

	/**
	 * @return количестов за€вок
	 */
	public Long getCount()
	{
		return count;
	}

	/**
	 * @param count - количестов за€вок
	 */
	public void setCount(Long count)
	{
		this.count = count;
	}

	/**
	 * @return дата. нужна дл€ обнул€ени€ счетчика раз в мес€ц
	 */
	public Calendar getLastDate()
	{
		return lastDate;
	}

	/**
	 * @param date - дата. нужна дл€ обнул€ени€ счетчика раз в мес€ц
	 */
	public void setLastDate(Calendar date)
	{
		this.lastDate = date;
	}
}
