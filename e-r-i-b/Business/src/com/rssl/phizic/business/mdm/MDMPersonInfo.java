package com.rssl.phizic.business.mdm;

import java.util.Calendar;

/**
 * �������� ��� �������� ������ �� ������ �������
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
	 * @return ���
	 */
	public String getSex()
	{
		return sex;
	}

	/**
	 * @return ����� ����������
	 */
	public String getCitizenship()
	{
		return citizenship;
	}

	/**
	 * @return ���� ��������
	 */
	public String getBirthPlace()
	{
		return birthPlace;
	}

	/**
	 * @return ��������
	 */
	public Long getResident()
	{
		return resident;
	}

	/**
	 * @return ���
	 */
	public String getTaxPayerid()
	{
		return taxPayerid;
	}

	/**
	 * @return ���� ����� ��������
	 */
	public Calendar getChangeDay()
	{
		return changeDay;
	}

	/**
	 * @return �����������
	 */
	public String getLiterate()
	{
		return literate;
	}

	/**
	 * @return ����� ����������
	 */
	public String getActAddress()
	{
		return actAddress;
	}

	/**
	 * @return ����������� �����
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @return �������
	 */
	public String getMobphone()
	{
		return mobphone;
	}

	/**
	 * @return ������� �����
	 */
	public String getRiskLevel()
	{
		return riskLevel;
	}

	/**
	 * @return ��� ����
	 */
	public String getLife_state()
	{
		return life_state;
	}

	/**
	 * @return ���� ������
	 */
	public String getDeath_date()
	{
		return death_date;
	}

	/**
	 * @return �������� ����
	 */
	public String getVip_state()
	{
		return vip_state;
	}
}
