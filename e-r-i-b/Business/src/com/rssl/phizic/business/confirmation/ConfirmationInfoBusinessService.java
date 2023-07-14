package com.rssl.phizic.business.confirmation;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.PrincipalImpl;
import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.auth.modes.AccessPolicyService;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.confirmation.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.permissions.ServicePermission;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

/**
 * @author akrenev
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Сервис работы с информации о подтверждении операций клиентом.
 */

public class ConfirmationInfoBusinessService extends AbstractService implements ConfirmationInfoService
{
	private static final PersonService personService = new PersonService();
	private static final AccessPolicyService accessService = new AccessPolicyService();
	private static final ExternalResourceService resourceService = new ExternalResourceService();

	protected ConfirmationInfoBusinessService(GateFactory factory)
	{
		super(factory);
	}

	@SuppressWarnings({"MethodWithTooManyParameters"})
	public ConfirmationInfo getConfirmationInfo(String firstName, String lastName, String middleName, String docNumber, Calendar birthDate, String tb) throws GateException, GateLogicException
	{
		try
		{
			Person person = personService.getByFIOAndDocUnique(lastName, firstName, middleName, "", docNumber, birthDate, tb);
			if (person == null)
				return null;
			ConfirmStrategyType preferredConfirmType = accessService.getUserOptionType(person.getLogin(), AccessType.simple);
			List<CardLink> cardLinks = resourceService.getInSystemLinks(person.getLogin(), CardLink.class);
			ArrayList<CardConfirmationSource> sources = new ArrayList<CardConfirmationSource>();
			for (CardLink cardLink : cardLinks)
				sources.add(new CardConfirmationSourceImpl(cardLink.getNumber()));

			AccessPolicy policy = ConfigFactory.getConfig(AuthenticationConfig.class).getPolicy(AccessType.simple);
			AccessPolicyService accessModeService = new AccessPolicyService();
			Properties policyProperties = accessModeService.getProperties(person.getLogin(), AccessType.simple);
			PrincipalImpl principal = new PrincipalImpl(person.getLogin(), policy, policyProperties);
			AuthModule tempModule = new AuthModule(principal);

			boolean pushAllowed = tempModule.implies(new ServicePermission("ClientProfilePush"));

			return new ConfirmationInfoImpl(preferredConfirmType == null? ConfirmStrategyType.sms: preferredConfirmType, sources, pushAllowed);
		}
		catch(BusinessException ex)
		{
			throw new GateException(ex);
		}
		catch(BusinessLogicException ex)
		{
			throw new GateLogicException(ex);
		}
		catch (SecurityDbException e)
		{
			throw new GateException(e);
		}
	}
}
