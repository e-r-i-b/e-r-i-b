package com.rssl.phizic.operations.config;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.DbPropertyService;
import com.rssl.phizic.config.Property;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.config.csa.Constants;
import com.rssl.phizic.operations.config.writers.FraudMonitoringPropertiesWriter;
import com.rssl.phizic.operations.config.writers.PropertiesWriter;

import java.util.*;

/**
 * операция для работы с настройками конкретной внешней системы.
 *
 * @author bogdanov
 * @ created 11.01.2013
 * @ $Author$
 * @ $Revision$
 */

public class ConfigureConcreteExternalSystemOperation extends EditPropertiesOperation
{
	//на форме редактирования настроек цса-бек часть настроек читаются/пишутся из БД основного приложения
	private static final Set<String> NOT_CSA_BACK_PROPERTIES = new HashSet<String>();
	private static final Set<String> GENERAL_PROPERTIES = new HashSet<String>();
	private static final Map<PropertyCategory, PropertiesWriter> WRITERS = new HashMap<PropertyCategory, PropertiesWriter>();

	private Set propertyKeys;

	static
	{
		NOT_CSA_BACK_PROPERTIES.add("csa.back.webservice.url");
		NOT_CSA_BACK_PROPERTIES.add("com.rssl.iccs.ban.ipas.login");
		NOT_CSA_BACK_PROPERTIES.add("com.rssl.iccs.registration.mode");
		NOT_CSA_BACK_PROPERTIES.add("com.rssl.iccs.self.registration.new.design");
		NOT_CSA_BACK_PROPERTIES.add("com.rssl.iccs.self.registration.show.login.self.registration.screen");
		NOT_CSA_BACK_PROPERTIES.add("com.rssl.iccs.registration.user.deny-multiple");
		NOT_CSA_BACK_PROPERTIES.add("com.rssl.phizic.web.client.SelfRegistrationHelper.SOFT_NOT_EXIST.mode");
		NOT_CSA_BACK_PROPERTIES.add("com.rssl.phizic.web.client.SelfRegistrationHelper.HARD_NOT_EXIST.mode");
		NOT_CSA_BACK_PROPERTIES.add("com.rssl.phizic.web.client.SelfRegistrationHelper.SOFT_EXIST.mode");
		NOT_CSA_BACK_PROPERTIES.add("com.rssl.phizic.web.client.SelfRegistrationHelper.HARD_EXIST.mode");
		NOT_CSA_BACK_PROPERTIES.add("com.rssl.phizic.web.client.SelfRegistrationForm.message");
		NOT_CSA_BACK_PROPERTIES.add("com.rssl.phizic.web.client.SelfRegistrationWindow.title");
		NOT_CSA_BACK_PROPERTIES.add("com.rssl.iccs.multiple.registration.part.visible");
		NOT_CSA_BACK_PROPERTIES.add(Constants.GUEST_ENTRY_CLAIMS_SHOW_PERIOD);

		//Общие настройки для блока и бека
		GENERAL_PROPERTIES.add(Constants.COMMON_GUEST_CAPTCHA_DELAY);
		GENERAL_PROPERTIES.add(Constants.UNTRUSTED_GUEST_CAPTCHA_DELAY);
		GENERAL_PROPERTIES.add(Constants.CAPTCHA_CONTROL_ENABLED_PROPERTY_NAME);
		GENERAL_PROPERTIES.add(Constants.GUEST_ENTRY_MODE);
		GENERAL_PROPERTIES.add(Constants.GUEST_ENTRY_PHONES_LIMIT);
		GENERAL_PROPERTIES.add(Constants.GUEST_ENTRY_PHONES_LIMIT_MAX);
		GENERAL_PROPERTIES.add(Constants.GUEST_ENTRY_PHONES_LIMIT_COOLDOWN);

		WRITERS.put(PropertyCategory.RSA, new FraudMonitoringPropertiesWriter());
	}

	@Override
	public void initialize(PropertyCategory propertyCategory, Set propertyKeys) throws BusinessException
	{
		this.propertyKeys = propertyKeys;
		super.initialize(propertyCategory, propertyKeys);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected List<Property> findProperties(PropertyCategory category, Set propertyKeys)
	{
		if (!category.equals(PropertyCategory.CSABack))
			return super.findProperties(category, propertyKeys);

		Set<String> csaBackPropertyKeys = new HashSet<String>(propertyKeys);
		Set<String> phizICPropertyKeys = new HashSet<String>(propertyKeys);

		phizICPropertyKeys.retainAll(NOT_CSA_BACK_PROPERTIES);
		csaBackPropertyKeys.removeAll(NOT_CSA_BACK_PROPERTIES);

		List<Property> result = new ArrayList<Property>();
		if (!csaBackPropertyKeys.isEmpty())
			result.addAll(super.findProperties(category, csaBackPropertyKeys));

		if (!phizICPropertyKeys.isEmpty())
			result.addAll(super.findProperties(PropertyCategory.Phizic, phizICPropertyKeys));

		result.addAll(super.findProperties(PropertyCategory.CSAFront, GENERAL_PROPERTIES));
		return result;
	}

	@SuppressWarnings("unchecked")
	public void save() throws BusinessException, BusinessLogicException
	{
		//сделать отдельные лоадеры по категории
		PropertyCategory category = getPropertyCategory();

		PropertiesWriter writer = WRITERS.get(category);
		if (writer != null)
		{
			writer.write(getEntity());
			return;
		}

		if (!(PropertyCategory.CSABack == category || PropertyCategory.RSA == category))
		{
			super.save();
			return;
		}

		Map<String, String> csaBackProperties = new HashMap<String, String>(getEntity());
		Map<String, String> otherProperties = new HashMap<String, String>();
		Map<String, String> csaPhizProperties = new HashMap<String, String>();
		for (String key : NOT_CSA_BACK_PROPERTIES)
		{
			otherProperties.put(key, csaBackProperties.remove(key));
		}
		for(String key: GENERAL_PROPERTIES)
		{
			csaPhizProperties.put(key, csaBackProperties.remove(key));
		}

		DbPropertyService.updateProperties(csaBackProperties, PropertyCategory.CSABack, getDbInstance(PropertyCategory.CSABack));
		DbPropertyService.updateProperties(otherProperties, PropertyCategory.Phizic, getDbInstance(PropertyCategory.Phizic));
		DbPropertyService.updateProperties(csaPhizProperties, PropertyCategory.CSAFront, getDbInstance(PropertyCategory.CSAFront));
	}

	@Override
	public Set getBlackListFieldForReplication() throws BusinessLogicException, BusinessException
	{
		if (!getPropertyCategory().equals(PropertyCategory.CSABack))
			return super.getBlackListFieldForReplication();

		Set<String> blackList = new HashSet<String>(propertyKeys);
		blackList.removeAll(NOT_CSA_BACK_PROPERTIES);
		return blackList;
	}
}
