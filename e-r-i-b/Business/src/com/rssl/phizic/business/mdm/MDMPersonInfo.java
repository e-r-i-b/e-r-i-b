package com.rssl.phizic.business.mdm;

import java.util.Calendar;

/**
 * Сущность для выгрузки отчёта по анкете клиента
 * @author komarov
 * @ created 21.07.15
 * @ $Author$
 * @ $Revision$
 */
public class MDMPersonInfo extends MDMClientInfo
{
	private String sex;
	private String citizenship;
	private String birthPlace;
	private Long resident;
	private String taxPayerid;
	private Calendar changeDay;
	private String literate;
	private String actAddress;
	private String email;
	private String mobphone;
	private String riskLevel;
	private String life_state;
	private String death_date;
	private String vip_state;

	/**
	 * @return пол
	 */
	public String getSex()
	{
		return sex;
	}

	/**
	 * @return место жительства
	 */
	public String getCitizenship()
	{
		return citizenship;
	}

	/**
	 * @return дата рождения
	 */
	public String getBirthPlace()
	{
		return birthPlace;
	}

	/**
	 * @return резидент
	 */
	public Long getResident()
	{
		return resident;
	}

	/**
	 * @return инн
	 */
	public String getTaxPayerid()
	{
		return taxPayerid;
	}

	/**
	 * @return дата смены паспорта
	 */
	public Calendar getChangeDay()
	{
		return changeDay;
	}

	/**
	 * @return грамотность
	 */
	public String getLiterate()
	{
		return literate;
	}

	/**
	 * @return место жительства
	 */
	public String getActAddress()
	{
		return actAddress;
	}

	/**
	 * @return электронная почта
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @return телефон
	 */
	public String getMobphone()
	{
		return mobphone;
	}

	/**
	 * @return уровень риска
	 */
	public String getRiskLevel()
	{
		return riskLevel;
	}

	/**
	 * @return Жив мёртв
	 */
	public String getLife_state()
	{
		return life_state;
	}

	/**
	 * @return дата смерти
	 */
	public String getDeath_date()
	{
		return death_date;
	}

	/**
	 * @return тарифный план
	 */
	public String getVip_state()
	{
		return vip_state;
	}
}
