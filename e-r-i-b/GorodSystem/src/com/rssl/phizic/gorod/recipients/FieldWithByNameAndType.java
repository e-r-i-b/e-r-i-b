package com.rssl.phizic.gorod.recipients;

import com.rssl.phizic.gate.payments.systems.recipients.Field;

/**
 * Created by IntelliJ IDEA.
 * User: Stolbovskiy
 * Date: 23.11.2010
 * Time: 12:22:01
 * To change this template use File | Settings | File Templates.
 */

//Класс необходим для хранения информации о поле, признаке byname и типу поля (для полей со списком - isArray=true и list)
//byname и type необходимы для построения списков на форме платежа, в основе которых лежат данные из поля с атрибутом isArray=true
//поле содержит список, значения которого разделенны символом ";". Каждый элемент такого списка состоит из двух частей, разделенных символом "="
// byname используется для определения того, значение справа или слева от знака "=" будет использовано для заполнения списка
public class FieldWithByNameAndType
{
	private Field field;
	private String byName;
	private String fieldType;

	public void setField(Field field)
	{
		this.field=field;
	}

	public void setByName(String byName)
	{
		this.byName=byName;
	}

	public void setFieldType(String fieldType)
	{
		this.fieldType=fieldType;
	}

	public Field getField()
	{
		return field;
	}
	
	public String getByName()
	{
		return byName;
	}

	public String getFieldType()
	{
		return fieldType;
	}
}
