package com.rssl.phizicgate.manager.services.routable.schemes;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.config.csaadmin.CSAAdminGateConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.gate.schemes.AccessScheme;
import com.rssl.phizic.gate.schemes.AccessSchemeService;
import org.apache.commons.lang.ArrayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author akrenev
 * @ created 04.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Маршрутизарующий сервис работы со схемами прав сотрудников
 */

public class AccessSchemeServiceImpl extends AbstractService implements AccessSchemeService
{
	private static final String BUSINESS_DELEGATE_FOR_GATE_KEY = BUSINESS_DELEGATE_KEY + ".for.gate";
	private static final String CLIENT_TYPE = "C";
	private static final String[] CLIENT_CATEGORIES = new String[]{CLIENT_TYPE};

	private final Object serviceCreatorLocker = new Object();
	private volatile AccessSchemeService businessDelegate;
	private volatile AccessSchemeService businessDelegateForGate;
	private volatile AccessSchemeService gateDelegate;

	/**
	 * конструктор
	 * @param factory фабрика гейта, в рамках которой создается сервис
	 */
	public AccessSchemeServiceImpl(GateFactory factory)
	{
		super(factory);
	}

	private AccessSchemeService getBusinessDelegate()
	{
		if (businessDelegate == null)
		{
			synchronized (serviceCreatorLocker)
			{
				if (businessDelegate == null)
					businessDelegate = (AccessSchemeService) getDelegate(AccessSchemeService.class.getName() + BUSINESS_DELEGATE_KEY);
			}
		}
		return businessDelegate;
	}

	private AccessSchemeService getBusinessDelegateForGate()
	{
		if (businessDelegateForGate == null)
		{
			synchronized (serviceCreatorLocker)
			{
				if (businessDelegateForGate == null)
					businessDelegateForGate = (AccessSchemeService) getDelegate(AccessSchemeService.class.getName() + BUSINESS_DELEGATE_FOR_GATE_KEY);
			}
		}
		return businessDelegateForGate;
	}

	private AccessSchemeService getGateDelegate()
	{
		if (gateDelegate == null)
		{
			synchronized (serviceCreatorLocker)
			{
				if (gateDelegate == null)
					gateDelegate = (AccessSchemeService) getDelegate(AccessSchemeService.class.getName() + GATE_DELEGATE_KEY);
			}
		}
		return gateDelegate;
	}

	private boolean isMultiBlockMode()
	{
		return ConfigFactory.getConfig(CSAAdminGateConfig.class).isMultiBlockMode();
	}

	private AccessScheme getBusinessAccessScheme(AccessScheme gateScheme) throws GateException, GateLogicException
	{
		AccessScheme businessScheme = getBusinessDelegateForGate().getById(gateScheme.getExternalId());
		businessScheme.setCategory(gateScheme.getCategory());
		businessScheme.setName(gateScheme.getName());
		businessScheme.setExternalId(gateScheme.getExternalId());
		businessScheme.setCAAdminScheme(gateScheme.isCAAdminScheme());
		businessScheme.setVSPEmployeeScheme(gateScheme.isVSPEmployeeScheme());
		return businessScheme;
	}

	public AccessScheme getById(Long id) throws GateException, GateLogicException
	{
		if (!isMultiBlockMode() )
			return getBusinessDelegate().getById(id);

		AccessScheme gateScheme = getGateDelegate().getById(id);
		return getBusinessAccessScheme(gateScheme);
	}

	public List<AccessScheme> getList(String[] categories) throws GateException, GateLogicException
	{
		if (!isMultiBlockMode())
			return getBusinessDelegate().getList(categories);

		List<AccessScheme> schemes = getGateDelegate().getList(categories);
		ArrayList<AccessScheme> accessSchemes = new ArrayList<AccessScheme>();
		for (AccessScheme scheme : schemes)
			accessSchemes.add(getBusinessAccessScheme(scheme));

		return accessSchemes;
	}

	public AccessScheme save(AccessScheme schema) throws GateException, GateLogicException
	{
		if (isMultiBlockMode())
		{
			AccessScheme savedSchema = getGateDelegate().save(schema);
			schema.setExternalId(savedSchema.getExternalId());
		}
		return getBusinessDelegate().save(schema);
	}

	public void delete(AccessScheme schema) throws GateException, GateLogicException
	{
		if (isMultiBlockMode())
			getGateDelegate().delete(schema);
		else
			getBusinessDelegate().delete(schema);
	}
}
