package com.rssl.phizic.web.actions.payments.forms;

/**
 * Интерфейс для форм, содержащих обычный (не уникальный по блокам) и кросблочный (уникальный в рамках блоков) идентификатор региона
 * @ author: Gololobov
 * @ created: 21.06.14
 * @ $Author$
 * @ $Revision$
 */
public interface ContainRegionGuidActionFormInterface
{
	//Идентификатор региона
	Long getRegionId();
	//Кросблочный идентификатор региона
	String getRegionGuid();

	void setRegionId(Long regionId);
	void setRegionGuid(String regionGuid);
}
