package com.rssl.phizic.operations.erkc.context;

import com.rssl.phizic.gate.csa.ProfileWithFullNodeInfo;

import java.io.Serializable;
import java.util.Map;

/**
 * @author akrenev
 * @ created 11.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * Данные необходимые для работы сотрудника ЕРКЦ
 */

public class ERKCEmployeeData implements Serializable
{
	private ProfileWithFullNodeInfo csaPersonInfo;
	private Functional currentFunctional;
	private Map<String, Object> userInfo;
	private Long currentPersonId;
	private Long userLoginId;
	private Map<String, Object> additionalParameters;

	/**
	 * @return информация о текущем клиенте (из ЦСА)
	 */
	public ProfileWithFullNodeInfo getCsaPersonInfo()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return csaPersonInfo;
	}

	/**
	 * задать информацию о текущем клиенте (из ЦСА)
	 * @param csaPersonInfo информация о текущем клиенте
	 */
	public void setCsaPersonInfo(ProfileWithFullNodeInfo csaPersonInfo)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.csaPersonInfo = csaPersonInfo;
	}

	/**
	 * @return текущий функционал, с которым работает сотрудник
	 */
	public Functional getCurrentFunctional()
	{
		return currentFunctional;
	}

	/**
	 * задать текущий функционал, с которым работает сотрудник
	 * @param currentFunctional функционал, с которым работает сотрудник
	 */
	public void setCurrentFunctional(Functional currentFunctional)
	{
		this.currentFunctional = currentFunctional;
	}

	/**
	 * @return информация о клиенте (блочная)
	 */
	public Map<String, Object> getUserInfo()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return userInfo;
	}

	/**
	 * задать информацию о клиенте (блочную)
	 * @param userInfo информация о клиенте
	 */
	public void setUserInfo(Map<String, Object> userInfo)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.userInfo = userInfo;
	}

	/**
	 * @return идентификатор клиента
	 */
	public Long getCurrentPersonId()
	{
		return currentPersonId;
	}

	/**
	 * задать идентификатор клиента
	 * @param currentPersonId идентификатор клиента
	 */
	public void setCurrentPersonId(Long currentPersonId)
	{
		this.currentPersonId = currentPersonId;
	}

	/**
	 * @return идентификатор логина клиента
	 */
	public Long getUserLoginId()
	{
		return userLoginId;
	}

	/**
	 * задать идентификатор логина клиента
	 * @param userLoginId идентификатор
	 */
	public void setUserLoginId(Long userLoginId)
	{
		this.userLoginId = userLoginId;
	}

	/**
	 * @return дополнительные параметры
	 */
	public Map<String, Object> getAdditionalParameters()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return additionalParameters;
	}

	/**
	 * задать дополнительные параметры
	 * @param additionalParameters параметры
	 */
	public void setAdditionalParameters(Map<String, Object> additionalParameters)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.additionalParameters = additionalParameters;
	}
}
