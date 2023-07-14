package com.rssl.phizic.business.ant.services.groups;

import com.rssl.phizic.utils.test.SafeTaskBase;

/**
 * @author akrenev
 * @ created 21.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * Таск обновления групп сервисов прав доступа
 */

public class UpdateServicesGroupTask extends SafeTaskBase
{
	@Override
	public void safeExecute() throws Exception
	{
		log("Начинаем обновление группы сервисов");
		new ServicesGroupLoader().load();
		log("Закончили обновление группы сервисов");
	}

	public UpdateServicesGroupTask clone() throws CloneNotSupportedException
	{
		return (UpdateServicesGroupTask) super.clone();
	}
}
