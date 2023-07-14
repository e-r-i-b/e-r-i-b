package com.rssl.phizic.gate.schemes;

/**
 * @author akrenev
 * @ created 03.10.13
 * @ $Author$
 * @ $Revision$
 *
 * гейтовый интерфейс схемы прав
 */

public interface AccessScheme
{
	/**
	 * @return идентификатор
	 */
	public Long getId();

	/**
	 * @return внешний ключ
	 */
	public Long getExternalId();

	/**
	 * задать внешний ключ
	 * @param externalId внешний ключ
	 */
	public void setExternalId(Long externalId);

	/**
	 * @return название
	 */
	public String getName();

	/**
	 * задать название
	 * @param name название
	 */
	public void setName(String name);

	/**
	 * @return тип
	 */
	public String getCategory();

	/**
	 * задать тип
	 * @param category тип
	 */
	public void setCategory(String category);

	/**
	 * @return признак доступности схемы только администраторам ЦА
	 */
	boolean isCAAdminScheme();

	/**
	 * задать  признак доступности схемы только администраторам ЦА
	 * @param CAAdminScheme признак доступности схемы только администраторам ЦА
	 */
	public void setCAAdminScheme(boolean CAAdminScheme);

	/**
	 * @return признак доступности схемы только сотрудникам ВСП
	 */
	public boolean isVSPEmployeeScheme();

	/**
	 * задать признак доступности схемы только сотрудникам ВСП
	 * @param VSPEmployeeScheme признак доступности схемы только сотрудникам ВСП
	 */
	public void setVSPEmployeeScheme(boolean VSPEmployeeScheme);

	/**
	 * @return есть ли возможность работать с письмами
	 */
	public boolean isMailManagement();
	/**
	 * задать признак возможности работать с письмами
	 * @param mailManagement есть ли возможность работать с письмами
	 */
	public void setMailManagement(boolean mailManagement);

}
