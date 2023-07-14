package com.rssl.phizic.csaadmin.business.access;

/**
 * @author akrenev
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 *
 * схема прав
 */

public class AccessScheme
{
	private Long externalId;
	private String name;
	private String category;
	private boolean CAAdminScheme;
	private boolean VSPEmployeeScheme;
	private boolean mailManagement;

	public Long getExternalId()
	{
		return externalId;
	}

	public void setExternalId(Long externalId)
	{
		this.externalId = externalId;
	}

	/**
	 * @return название
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * задать название
	 * @param name название
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return тип
	 */
	public String getCategory()
	{
		return category;
	}

	/**
	 * задать тип
	 * @param category тип
	 */
	public void setCategory(String category)
	{
		this.category = category;
	}

	/**
	 * @return признак доступности схемы только администраторам ЦА
	 */
	public boolean isCAAdminScheme()
	{
		return CAAdminScheme;
	}

	/**
	 * задать  признак доступности схемы только администраторам ЦА
	 * @param CAAdminScheme признак доступности схемы только администраторам ЦА
	 */
	public void setCAAdminScheme(boolean CAAdminScheme)
	{
		this.CAAdminScheme = CAAdminScheme;
	}

	/**
	 * @return признак доступности схемы только сотрудникам ВСП
	 */
	public boolean isVSPEmployeeScheme()
	{
		return VSPEmployeeScheme;
	}

	/**
	 * задать признак доступности схемы только сотрудникам ВСП
	 * @param VSPEmployeeScheme признак доступности схемы только сотрудникам ВСП
	 */
	public void setVSPEmployeeScheme(boolean VSPEmployeeScheme)
	{
		this.VSPEmployeeScheme = VSPEmployeeScheme;
	}

	/**
	 * @return есть ли возможность работать с письмами
	 */
	public boolean isMailManagement()
	{
		return mailManagement;
	}

	/**
	 * задать признак возможности работать с письмами
	 * @param mailManagement есть ли возможность работать с письмами
	 */
	public void setMailManagement(boolean mailManagement)
	{
		this.mailManagement = mailManagement;
	}
}
