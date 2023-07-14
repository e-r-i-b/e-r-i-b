package com.rssl.ikfl.crediting;

/**
 * Канал создания заявки
 *
 * @ author: Gololobov
 * @ created: 05.03.15
 * @ $Author$
 * @ $Revision$
 */
public enum CRMClaimChannel
{
	WEB("1"),
	/**
	 * ТМ внутренний
	 */
	TM_INNER("2"),
	/**
	 * ТМ внешний
	 */
	TM_OUTER("3"),
	/**
	 * ВСП
 	 */
	VSP("4"),
	/**
	 * ЕРИБ-СБОЛ
	 */
	ERIB_SBOL("5"),
	/**
	 * ЕРИБ-МП
	 */
	ERIB_MP("6"),
	/**
	 * ЕРИБ-УС
	 */
	ERIB_US("7"),
	/**
	 * ЕРИБ-МБ
	 */
	ERIB_MB("8"),
	/**
	 * ЕРИБ-ГОСТЕВОЙ
	 */
	ERIB_GUEST("9");

	/**
	 * Код канала в CRM
	 */
	public final String code;

	private CRMClaimChannel(String code)
	{
		this.code = code;
	}

	public String getCode()
	{
		return code;
	}
}
