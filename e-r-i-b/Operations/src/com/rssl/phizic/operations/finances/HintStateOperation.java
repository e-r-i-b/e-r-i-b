package com.rssl.phizic.operations.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.userSettings.UserPropertiesConfig;

/**
 * Операция для работы с подсказками в бюджетировании
 * @author lepihina
 * @ created 15.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class HintStateOperation extends OperationBase
{
	/**
	 * Запомнить, что клиент прочитал все подсказки в бюджетировании
	 * @throws BusinessException
	 */
	public void setupHintsRead() throws BusinessException
	{
		ConfigFactory.getConfig(UserPropertiesConfig.class).setHintsRead(true);
	}

	/**
	 * Прочитаны все подсказки в бюджетировании
	 * @return true - подсказки прочитаны
	 * @throws BusinessException
	 */
	public boolean isReadAllHints() throws BusinessException
	{
		return ConfigFactory.getConfig(UserPropertiesConfig.class).isHintsRead();
	}
}
