package com.rssl.phizic.armour;

import com.rssl.phizic.utils.naming.NamingHelper;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.NameNotFoundException;

/**
 * @author Krenev
 * @ created 05.06.2008
 * @ $Author$
 * @ $Revision$
 */
public class ArmourProviderHelper
{
	private static final String ARMOUR_PROVIDE_FACTORY = "rssl/phizic/armour-factory";

	public ArmourProviderFactory lookupFactory()
	{
		ArmourProviderFactory factory = lookupFactoryUnSafe();
		if (factory == null)
		{
			//"Не удалось найти armour factory с именем "+ARMOUR_PROVIDE_FACTORY
			throw new ArmourException("Лицензионная защита не найдена");
		}
		return factory;
	}

	private ArmourProviderFactory lookupFactoryUnSafe()
	{
		try
		{
			InitialContext initialContext = NamingHelper.getInitialContext();
			return (ArmourProviderFactory) initialContext.lookup(ARMOUR_PROVIDE_FACTORY);
		}
		catch (NamingException e)
		{
			throw new ArmourException(e);
		}
	}

	public void putFactory(ArmourProviderFactory factory)
	{
		try
		{
			InitialContext initialContext = NamingHelper.getInitialContext();
			NamingHelper.bind(initialContext, ARMOUR_PROVIDE_FACTORY, factory);
		}
		catch (NamingException e)
		{
			throw new ArmourException(e);
		}
	}

	public static boolean isFactoryExists()
	{
		try
		{
			InitialContext initialContext = NamingHelper.getInitialContext();
			return initialContext.lookup(ARMOUR_PROVIDE_FACTORY)!=null;
		}
		catch(NameNotFoundException e)
		{
			return false;
		}
		catch (NamingException e)
		{
			throw new ArmourException(e);
		}
	}
}
