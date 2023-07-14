package com.rssl.phizic.operations.widget;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.userSettings.UserPropertiesConfig;

/**
 * �������� ��� ��������� �������� ����������
 * @author lukina
 * @ created 20.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class SetupInterfaceModeOperation extends OperationBase
{
	private boolean showWidget;
	/**
	 * ������������� ��������
	 */
	public void initialize() throws BusinessException
	{
		showWidget = ConfigFactory.getConfig(UserPropertiesConfig.class).isShowWidget();
	}

	public boolean isShowWidget(){
		return showWidget;
	}
	
	public void setShowWidget(boolean showWidget)
	{
		this.showWidget = showWidget;
	}
	/**
	 * ��������� ������� ������������
	 * @throws BusinessException
	 */
	public void save() throws BusinessException
	{
		ConfigFactory.getConfig(UserPropertiesConfig.class).setShowWidget(showWidget);
	}
}
