package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.modes.generated.AccessRuleDescriptor;
import com.rssl.phizic.auth.modes.generated.AccessRulesDescriptor;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.resources.ResourceHelper;

import java.io.InputStream;
import java.util.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

/**
 * @author Evgrafov
 * @ created 12.12.2006
 * @ $Author: niculichev $
 * @ $Revision: 72565 $
 */
public class JAXBAuthenticationConfig extends AuthenticationConfig
{
	private List<AccessPolicy>            policies;
	private Map<AccessType, AccessPolicy> policyByAccess;
	private String                        xmlConfig;
	private Set<AccessType>               accessTypes;
	private Application                   application;

	public JAXBAuthenticationConfig(PropertyReader reader,Application application)
	{
		super(reader);
	    xmlConfig = reader.getFileName();
		this.application = application;
	}


	public void doRefresh()
	{
		AccessRulesDescriptor rulesDescriptor;

		try
		{
			ClassLoader  classLoader  = Thread.currentThread().getContextClassLoader();
			JAXBContext  context      = JAXBContext.newInstance("com.rssl.phizic.auth.modes.generated", classLoader);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			InputStream is            = ResourceHelper.loadResourceAsStream(xmlConfig);

			rulesDescriptor = (AccessRulesDescriptor) unmarshaller.unmarshal(is);
		}
		catch (JAXBException e)
		{
			throw new SecurityException(e);
		}

		policies = new ArrayList<AccessPolicy>();
		policyByAccess = new HashMap<AccessType, AccessPolicy>();
		accessTypes = new HashSet<AccessType>();

		addPolicy(AccessType.simple,     rulesDescriptor.getSimpleRule());
		addPolicy(AccessType.secure,     rulesDescriptor.getSecureRule());
		addPolicy(AccessType.anonymous,  rulesDescriptor.getAnonymousRule());
		addPolicy(AccessType.smsBanking, rulesDescriptor.getSmsBankingRule());
		addPolicy(AccessType.employee,   rulesDescriptor.getEmployeeRule());
		addPolicy(AccessType.mobileLimited, rulesDescriptor.getMobileLimitedRule());
		addPolicy(AccessType.guest,         rulesDescriptor.getGuestRule());

		if(policies.isEmpty())
			throw new RuntimeException("ƒолжна быть задана хот€ бы одна политика");
	}

	private void addPolicy(AccessType accessType, AccessRuleDescriptor rulesDescriptor)
	{
		if(rulesDescriptor == null)
			return;

		AccessPolicy policy = new AccessPolicy(rulesDescriptor, accessType, application);

		policies.add(policy);
		policyByAccess.put(accessType, policy);
		accessTypes.add(accessType);
	}

	public List<AccessPolicy> getPolicies()
	{
		return Collections.unmodifiableList(policies);
	}

	public AccessPolicy getPolicy(AccessType accessType)
	{
		return policyByAccess.get(accessType);
	}

	public Set<AccessType> getAccessTypes()
	{
		return Collections.unmodifiableSet(accessTypes);
	}
}
