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
 * �������� ��� ������ � ����������� � ��������������
 * @author lepihina
 * @ created 15.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class HintStateOperation extends OperationBase
{
	/**
	 * ���������, ��� ������ �������� ��� ��������� � ��������������
	 * @throws BusinessException
	 */
	public void setupHintsRead() throws BusinessException
	{
		ConfigFactory.getConfig(UserPropertiesConfig.class).setHintsRead(true);
	}

	/**
	 * ��������� ��� ��������� � ��������������
	 * @return true - ��������� ���������
	 * @throws BusinessException
	 */
	public boolean isReadAllHints() throws BusinessException
	{
		return ConfigFactory.getConfig(UserPropertiesConfig.class).isHintsRead();
	}
}
