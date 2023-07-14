package com.rssl.phizic.auth.modes;

import com.rssl.phizic.auth.modes.generated.CompositeStrategyDescriptor;
import com.rssl.phizic.auth.modes.generated.ConfirmationModeDescriptor;
import com.rssl.phizic.auth.modes.generated.ConfirmationStrategyDescriptor;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.utils.ClassHelper;

import java.io.Serializable;
import java.util.*;

/**
 * @author Evgrafov
 * @ created 13.04.2007
 * @ $Author: saharnova $
 * @ $Revision: 73671 $
 */

public class ConfirmationMode implements Serializable
{
	private String                       keyProperty;
	private List<ConfirmStrategy>        strategies;
	private Map<String, ConfirmStrategy> strategyByKey;
	private String                       defaultKeyStrategy;

	/**
	 * ctor
	 * @param descriptor �������� ������ �������������
	 */
	ConfirmationMode(ConfirmationModeDescriptor descriptor, Application application)
	{

		this.keyProperty = descriptor.getKeyProperty();

		strategies    = new ArrayList<ConfirmStrategy>();
		strategyByKey = new HashMap<String, ConfirmStrategy>();

		//noinspection unchecked
		List<ConfirmationStrategyDescriptor> list = descriptor.getStrategy();
		for (ConfirmationStrategyDescriptor strategyDescriptor : list)
		{
			String key       = strategyDescriptor.getKey();
			if(defaultKeyStrategy == null)
				defaultKeyStrategy = key;

			//������ ���������
			if  (strategyDescriptor.getClazz().size()!=0)
			{
				List<String> clazzNames = strategyDescriptor.getClazz();

				CompositeConfirmStrategy composite = new CompositeConfirmStrategy();
				for (String clazzName : clazzNames)
				{
					ConfirmStrategy strategy = crateStrategy(clazzName);
					composite.addStrategy(strategy);
					strategies.add(strategy);
				}

				strategyByKey.put(key, composite);
			}
			//����� compos ���������
			else if (strategyDescriptor.getCompas().size()!=0)
			{
				List<CompositeStrategyDescriptor> compasNames = strategyDescriptor.getCompas();

				CompositeConfirmStrategy composite;
                if (ApplicationInfo.isMobileApi(application))
					composite = new MobilePlatformIPasCompositeConfirmStrategy();
                else if (application == Application.socialApi)
                    composite = new SocialPlatformIPasCompositeConfirmStrategy();
				else
					composite = new iPasCompositeConfirmStrategy();

				for (CompositeStrategyDescriptor compasName : compasNames)
				{
					ConfirmStrategy strategy = crateStrategy(compasName.getStrategy());
					if (compasName.isDefault()) composite.setDefaultStrategy(strategy.getType());
					composite.addStrategy(strategy);
					strategies.add(strategy);
				}
				strategyByKey.put(key, composite);
			}
		}
	}

	private ConfirmStrategy crateStrategy(String clazzName)
	{
		try
		{
			return ClassHelper.<ConfirmStrategy>loadClass(clazzName).newInstance();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	/**
	 * @return �������� � ������� ������ ������ ����
	 */
	public String getKeyProperty()
	{
		return keyProperty;
	}

	/**
	 * @return ������ ��������� ���������
	 */
	public List<ConfirmStrategy> getStrategies()
	{
		return Collections.unmodifiableList(strategies);
	}

	/**
	 * @param key ���� ��� ������ ���������
	 * @return ��������
	 */
	public ConfirmStrategy findStrategy(String key)
	{
		return strategyByKey.get(key);
	}

	/**
	 * @return ���������� ��������� �� ��������� (������ ��������� � client-authentication-modes.xml)
	 */
	public String getDefaultKeyStrategy()
	{
		return defaultKeyStrategy;
	}
}