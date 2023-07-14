package com.rssl.phizic.operations.userprofile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.csa.IncognitoService;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityLogicException;

import java.util.HashMap;
import java.util.Map;

/**
 * Операция для изменения настройки приватности клиента и обновления справочника "инкогнито" при изменении настройки клиентом
 *
 * @author EgorovaA
 * @ created 18.10.13
 * @ $Author$
 * @ $Revision$
 */
public class IncognitoSettingOperation extends ConfirmableOperationBase
{
	private boolean incognitoSetting;
	private String sid;

	public void initialize(String sid, boolean incognitoSetting)
	{
		this.sid = sid;
		this.incognitoSetting = incognitoSetting;
	}

	public void initialize(String sid)
	{
		this.sid = sid;
	}

	/**
	 * Сохранение настройки "инкогнито" без подтверждения
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 * @throws SecurityLogicException
	 */
	public void saveWithoutConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		saveConfirm();
	}

	@Override
	protected void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		IncognitoService.changeIncognitoSetting(sid, incognitoSetting);
	}

	public ConfirmableObject getConfirmableObject()
	{
		Map<String, String> changes = new HashMap<String, String>();
		return new IncognitoSettings(changes);
	}

	/**
	 * Получить настройку приватности клиента
	 * @return
	 * @throws BusinessException
	 */
	public boolean getClientIncognito() throws BusinessException, BusinessLogicException
	{
		return IncognitoService.getIncognitoSettings(sid);
	}
}
