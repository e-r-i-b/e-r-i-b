package com.rssl.phizic.auth.imsi;

import com.rssl.phizic.auth.CommonLogin;

import java.io.Serializable;
import java.util.Date;

/**
 * —одержит информацию об ошибке IMSI по конкретной дате дл€ конкретного пользовател€.
 *
 * @author bogdanov
 * @ created 01.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class LoginIMSIError implements Serializable
{
	/**
	 * »дентификатор записи.
	 */
	private Long id;
	/**
	 * ƒата выполнени€ проверки на ошибку IMSI.
	 */
	private Date checkDate;
	/**
	 * логин клиента, дл€ номеров телефонов которого выполн€лась проверка IMSI.
	 */
	private CommonLogin login;
	/**
	 * ѕризнак того, что сим-карта не была смененна.
	 */
	private boolean goodIMSI;

	public LoginIMSIError()
	{
	}

	public LoginIMSIError(Long id, Date checkDate, CommonLogin login, boolean goodIMSI)
	{
		this.checkDate = checkDate;
		this.goodIMSI = goodIMSI;
		this.id = id;
		this.login = login;
	}

	/**
	 * @return дату выполнени€ проверки на смену сим-карты.
	 */
	public Date getCheckDate()
	{
		return checkDate;
	}

	/**
	 * @param checkDate дата выполнени€ проверки на смену сим-карты.
	 */
	public void setCheckDate(Date checkDate)
	{
		this.checkDate = checkDate;
	}

	/**
	 * @return признак того, что сим-карта не была сменена.
	 */
	public boolean isGoodIMSI()
	{
		return goodIMSI;
	}

	/**
	 * @param goodIMSI признак того, что сим-карта не была сменена.
	 */
	public void setGoodIMSI(boolean goodIMSI)
	{
		this.goodIMSI = goodIMSI;
	}

	/**
	 * @return идентификатор.
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор.
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return логин клиента, дл€ номеров телефона которого выполн€лась проверка смены сим-карты.
	 */
	public CommonLogin getLogin()
	{
		return login;
	}

	/**
	 * @param login логин клиента, дл€ номеров телефона которого выполн€лась проверка смены сим-карты.
	 */
	public void setLogin(CommonLogin login)
	{
		this.login = login;
	}
}
