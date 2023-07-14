package com.rssl.phizic.business.schemes;

import com.rssl.phizic.business.services.Service;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 06.04.2006
 * @ $Author: krenev_a $
 * @ $Revision: 54593 $
 */
public interface AccessScheme extends com.rssl.phizic.gate.schemes.AccessScheme
{
	/**
	 * @return идентификатор
	 */
	public Long getId();

	/**
	 * @return услуги схемы
	 */
	List<Service> getServices();

	/**
	 * @return может ли схема быть удалена
	 */
	boolean canDelete();

	/**
	 * @return признак наличия в схеме сервиса "Доступ по-умолчанию ко всем ТБ" (AllTBAccess)
	 */
	boolean isContainAllTbAccessService();
}
