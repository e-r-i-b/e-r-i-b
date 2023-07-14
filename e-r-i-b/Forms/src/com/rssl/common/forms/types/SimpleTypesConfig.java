package com.rssl.common.forms.types;

import com.rssl.phizic.config.PropertyReader;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Evgrafov
 * @ created 13.02.2006
 * @ $Author: bogdanov $
 * @ $Revision: 57189 $
 */

public class SimpleTypesConfig extends TypesConfig
{
	private Map<String,Type> typesByName;
	private StringType defaultType;

	public SimpleTypesConfig(PropertyReader reader)
	{
		super(reader);
	}

	/** ¬озвращает тип по его имени и бросает исключение если тип не найден */
	public Type getType(String name)
	{
		Type type = typesByName.get(name);
		if(type == null)
			throw new IllegalArgumentException("Type with name=" + name + " not found");
		return type;
	}

	public Type defaultType()
	{
		return defaultType;
	}

	public void doRefresh()
	{
		typesByName = new HashMap<String, Type>();
		defaultType = new StringType();
		registerType(defaultType);

		registerType(new DateType());
		registerType(new MoneyType());
		registerType(new BooleanType());
		registerType(new IntType());
		registerType(new IntegerType());
		registerType(new LongType());
		registerExtendedTypes();
	}

	protected void registerType(Type type)
	{
		typesByName.put(type.getName(), type);
	}

	/**
	 * метод дл€ расширени€ набора типов.
	 * т.е. при наследовании доп. типы необходимо определ€ть в этом методе.
	 */
	protected void registerExtendedTypes()
	{

	}

}