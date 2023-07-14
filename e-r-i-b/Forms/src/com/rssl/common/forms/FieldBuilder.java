package com.rssl.common.forms;

import com.rssl.common.forms.expressions.ConstantExpression;
import com.rssl.common.forms.expressions.Expression;
import com.rssl.common.forms.parsers.FieldValueParser;
import com.rssl.common.forms.types.*;
import com.rssl.common.forms.validators.FieldValidator;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.ListUtil;
import org.apache.commons.lang.ArrayUtils;

import java.util.List;

/**
 * @author Evgrafov
 * @ created 08.12.2005
 * @ $Author: komarov $
 * @ $Revision: 66838 $
 */
public class FieldBuilder
{

	private FieldImpl field;

	public FieldBuilder()
	{
		field=new FieldImpl();
		setType(StringType.INSTANCE);
		setSignable(false);
		setEnabledExpression(new ConstantExpression(true));
	}

	public String getName ()
	{
		return field.getName();
	}

	public void setName ( String name )
	{
		field.setName(name);
	}

	public Type getType ()
	{
		return field.getType();
	}

	public boolean isSignable()
	{
		return field.isSignable();
	}

	public void setSignable(boolean value)
	{
		field.setSignable(value);
	}

	/**
	 * Устанавливает тип поля и присваивает значения по умолчанию для парсера и списка валидаторов
	 * @param typeString строковое обозначение типа
	 */
	public void setType ( String typeString )
	{
		TypesConfig typesConfig = ConfigFactory.getConfig(TypesConfig.class);
		Type type = typesConfig.getType(typeString);
		setType(type);
	}

	/**
	 * Устанавливает тип поля и присваивает значения по умолчанию для парсера и списка валидаторов
	 * @param type тип
	 */
	private void setType(Type type)
	{
		field.setType(type);
		field.setValidators(type.getDefaultValidators());
		field.setParser(type.getDefaultParser());
	}

	public void setSubType(String subType)
	{
		field.setSubType(SubType.fromValue(subType));
	}

	public void setSubType(SubType subType)
	{
		field.setSubType(subType);
	}

	public List<FieldValidator> getValidators ()
	{
		return field.getValidators();
	}

	/**
	 * @deprecated Использовать addValidators и clearValidators
	 */
	@Deprecated
	public void setValidators (FieldValidator... validators )
	{
		field.setValidators(ListUtil.fromArray(validators));
	}

	public void addValidators(FieldValidator... validators )
	{
		field.addValidators(ListUtil.fromArray(validators));
	}

	public void clearValidators()
	{
		field.clearValidators();
	}

	public String getDescription ()
	{
		return field.getDescription();
	}

	public void setDescription ( String description )
	{
		field.setDescription(description);
	}

	public FieldValueParser getParser ()
	{
		return field.getParser();
	}

	public void setParser (FieldValueParser parser )
	{
		field.setParser(parser);
	}

	public Field build ()
	{
		return field;
	}

	public String getSource()
	{
		return field.getSource();
	}

	public void setSource(String value)
	{
		field.setSource(value);
	}

	public void setEnabledExpression(Expression value)
	{
		field.setEnabledExpression(value);
	}

	public void setValueExpression(Expression value)
	{
		field.setValueExpression(value);
	}

	public void setInitalValueExpression(Expression value){
		field.setInitalValueExpression(value);
	}
	
	public void setFromApi(VersionNumber fromApi)
	{
		field.setFromApi(fromApi);
	}

	public void setToApi(VersionNumber toApi)
	{
		field.setToApi(toApi);
	}

	/**
	 * @deprecated для поддержания старого алгоритма маскирования
	 */
	@Deprecated
	public void setKey(boolean key)
	{
		field.setKey(key);
	}

	public void setBusinessCategory(BusinessCategory businessCategory)
	{
		field.setBusinessCategory(businessCategory);
	}

	public void setMask(String mask)
	{
		field.setMask(mask);
	}

	/**
	 * собрать поле типа String
	 * @param name  имя поля
	 * @param description описание поля
	 * @param validators валидаторы поля
	 * @return поле
	 */
	public static Field buildStringField(String name, String description, FieldValidator... validators)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(StringType.INSTANCE.getName());
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		if (ArrayUtils.isNotEmpty(validators))
			fieldBuilder.addValidators(validators);
		return fieldBuilder.build();
	}

	/**
	 * собрать поле типа Long
	 * @param name  имя поля
	 * @param description описание поля
	 * @param validators валидаторы поля
	 * @return поле
	 */
	public static Field buildLongField(String name, String description, FieldValidator... validators)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(LongType.INSTANCE.getName());
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		if (ArrayUtils.isNotEmpty(validators))
			fieldBuilder.addValidators(validators);
		return fieldBuilder.build();
	}

	/**
	 * собрать поле типа Date
	 * @param name  имя поля
	 * @param description описание поля
	 * @param validators валидаторы поля
	 * @return поле
	 */
	public static Field buildDateField(String name, String description, FieldValidator... validators)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(DateType.INSTANCE.getName());
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		if (ArrayUtils.isNotEmpty(validators))
			fieldBuilder.addValidators(validators);
		return fieldBuilder.build();
	}

	/**
	 * собрать поле типа Boolean
	 * @param name  имя поля
	 * @param description описание поля
	 * @param validators валидаторы поля
	 * @return поле
	 */
	public static Field buildBooleanField(String name, String description, FieldValidator... validators)
	{
		FieldBuilder fieldBuilder = new FieldBuilder();
		fieldBuilder.setType(BooleanType.INSTANCE.getName());
		fieldBuilder.setName(name);
		fieldBuilder.setDescription(description);
		if (ArrayUtils.isNotEmpty(validators))
			fieldBuilder.addValidators(validators);
		return fieldBuilder.build();
	}
}
