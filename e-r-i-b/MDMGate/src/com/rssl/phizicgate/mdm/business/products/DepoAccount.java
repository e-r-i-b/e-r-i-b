package com.rssl.phizicgate.mdm.business.products;

/**
 * @author akrenev
 * @ created 13.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ���� ����
 */

public class DepoAccount
{
	private Long id;
	private Long profileId;
	private String number;

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ �������������
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ������������� �������
	 */
	public Long getProfileId()
	{
		return profileId;
	}

	/**
	 * ������ ������������� �������
	 * @param profileId �������������
	 */
	public void setProfileId(Long profileId)
	{
		this.profileId = profileId;
	}

	/**
	 * @return �����
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * ������ �����
	 * @param number �����
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}
}
