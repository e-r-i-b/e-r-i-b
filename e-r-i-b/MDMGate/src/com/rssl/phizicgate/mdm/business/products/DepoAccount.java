package com.rssl.phizicgate.mdm.business.products;

/**
 * @author akrenev
 * @ created 13.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Счет ДЕПО
 */

public class DepoAccount
{
	private Long id;
	private Long profileId;
	private String number;

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификатор
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return идентификатор профиля
	 */
	public Long getProfileId()
	{
		return profileId;
	}

	/**
	 * задать идентификатор профиля
	 * @param profileId идентификатор
	 */
	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}

	/**
	 * @return номер
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * задать номер
	 * @param number номер
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}
}
