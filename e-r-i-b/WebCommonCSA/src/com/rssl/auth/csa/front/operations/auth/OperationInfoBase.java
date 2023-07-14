package com.rssl.auth.csa.front.operations.auth;

import com.rssl.phizic.web.auth.Stage;

import java.util.Map;

/**
 * Информация о текущей операции
 * @author niculichev
 * @ created 13.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class OperationInfoBase implements OperationInfo
{
	private boolean valid = true;               // валидность контекста
	private Map<String, Object> confirmParams;    // параметры подтверждения
	private String ouid;                        // уникальный идентификатор операции
	private Stage stage;                        // текущий щаг
	private String authToken;
	private String redirect;

	public OperationInfoBase(Stage stage)
	{
		this.stage = stage;
	}

	/**
	 * @return true - некорректный контекст
	 */
	public boolean invalid()
	{
		return false;
	}

	public String getOUID()
	{
		return ouid;
	}

	/**
	 * @return Текущее состояние
	 */
	public Stage getCurrentStage()
	{
		return stage;
	}

	public String getCurrentName()
	{
		Stage currentStage = getCurrentStage();
		if(currentStage == null)
			return null;

		return currentStage.getName();
	}

	/**
	 * Переключить на следующее состояние
	 */
	public void nextStage()
	{
		stage = stage.next(this);
	}

	public void setOUID(String ouid)
	{
		this.ouid = ouid;
	}

	public boolean isValid()
	{
		return valid;
	}

	public void setValid(boolean valid)
	{
		this.valid = valid;
	}

	/**
	 * @return параметры подтверждения
	 */
	public Map<String, Object> getConfirmParams()
	{
		return confirmParams;
	}

	public void setConfirmParams(Map<String, Object> confirmParams)
	{
		this.confirmParams = confirmParams;
	}

	/**
	 * @return токен аутентификации
	 */
	public String getAuthToken()
	{
		return authToken;
	}

	/**
	 * задать токен аутентификации
	 * @param authToken токен аутентификации
	 */
	public void setAuthToken(String authToken)
	{
		this.authToken = authToken;
	}

	public String getRedirect()
	{
		return redirect;
	}

	public void setRedirect(String redirect)
	{
		this.redirect = redirect;
	}
}
