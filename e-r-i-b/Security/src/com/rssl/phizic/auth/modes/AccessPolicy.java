package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.modes.generated.AccessRuleDescriptor;
import com.rssl.phizic.auth.modes.generated.AuthenticationModeDescriptor;
import com.rssl.phizic.common.types.Application;
import org.apache.commons.collections.CollectionUtils;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 13.04.2007
 * @ $Author: basharin $
 * @ $Revision: 58938 $
 */

public class AccessPolicy implements Serializable
{
	private final Map<UserVisitingMode, AuthenticationMode> authenticationModes;
	private final ConfirmationMode   confirmationMode;
	private final AccessType         accessType;
	private final String             description;
	private final Choice             confirmationChoice;
	private final Choice             authenticationChoice;

	/**
	 * ctor
	 * @param rulesDescriptor описание политики
	 * @param accessType тип доступа
	 */
	public AccessPolicy(AccessRuleDescriptor rulesDescriptor, AccessType accessType, Application application)
	{
		this.accessType           = accessType;
		this.authenticationModes  = mapAuthenticationModes(rulesDescriptor);
		this.confirmationMode     = new ConfirmationMode(rulesDescriptor.getConfirmationMode(), application);
		this.description          = rulesDescriptor.getDescription();
		
		if(rulesDescriptor.getAuthenticationChoice() != null)
			this.authenticationChoice = new Choice(rulesDescriptor.getAuthenticationChoice());
		else this.authenticationChoice = null;

		if(rulesDescriptor.getConfirmationChoice() != null)
			this.confirmationChoice   = new Choice(rulesDescriptor.getConfirmationChoice());
		else this.confirmationChoice  = null;
	}

	private Map<UserVisitingMode, AuthenticationMode> mapAuthenticationModes(AccessRuleDescriptor rulesDescriptor)
	{
		List list = rulesDescriptor.getAuthenticationMode();
		if (CollectionUtils.isEmpty(list))
			return Collections.emptyMap();

		Map<UserVisitingMode, AuthenticationMode> map = new LinkedHashMap<UserVisitingMode, AuthenticationMode>(list.size());
		for (Object oModeDescriptor : list)
		{
			AuthenticationModeDescriptor amd = (AuthenticationModeDescriptor) oModeDescriptor;
			AuthenticationMode mode = new AuthenticationMode(amd);
			map.put(mode.getUserVisitingMode(), mode);
		}
		return map;
	}

	/**
	 * Возвращает режим аутентификации в зависимости от выбранного режима работы пользователя
	 * @param visitingMode - режим работы пользователя
	 * @return режим аутентификации
	 * (can be null)
	 */
	public AuthenticationMode getAuthenticationMode(UserVisitingMode visitingMode)
	{
		return authenticationModes.get(visitingMode);
	}

	/**
	 * @return режим подтверждения операций
	 */
	public ConfirmationMode getConfirmationMode()
	{
		return confirmationMode;
	}

	/**
	 * @return тип доступа
	 */
	public AccessType getAccessType()
	{
		return accessType;
	}

	/**
	 * @return описание политики
	 */
	public String getDescription()
	{
		return description;
	}

	public String toString()
	{
		return "[" + accessType + "] " + description;
	}

	public Choice getAuthenticationChoice()
	{
		return authenticationChoice;
	}

	public Choice getConfirmationChoice()
	{
		return confirmationChoice;
	}
}
