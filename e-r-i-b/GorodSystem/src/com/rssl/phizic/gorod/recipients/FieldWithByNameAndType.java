package com.rssl.phizic.gorod.recipients;

import com.rssl.phizic.gate.payments.systems.recipients.Field;

/**
 * Created by IntelliJ IDEA.
 * User: Stolbovskiy
 * Date: 23.11.2010
 * Time: 12:22:01
 * To change this template use File | Settings | File Templates.
 */

//����� ��������� ��� �������� ���������� � ����, �������� byname � ���� ���� (��� ����� �� ������� - isArray=true � list)
//byname � type ���������� ��� ���������� ������� �� ����� �������, � ������ ������� ����� ������ �� ���� � ��������� isArray=true
//���� �������� ������, �������� �������� ���������� �������� ";". ������ ������� ������ ������ ������� �� ���� ������, ����������� �������� "="
// byname ������������ ��� ����������� ����, �������� ������ ��� ����� �� ����� "=" ����� ������������ ��� ���������� ������
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
