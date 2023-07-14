package com.rssl.phizic.business.dictionaries.basketident;

/**
 * @author osminin
 * @ created 02.07.15
 * @ $Author$
 * @ $Revision$
 *
 * �������� - ��������� ������� ��� ���� ��������� ����� � ������ � ��������������� �������
 */
public class FieldFormulaAttribute
{
	private Long id;
	private Long fieldFormulaId;
	private Long serial;
	private String value;
	private String systemId;
	private boolean last;

	/**
	 * @return ������������� �������
	 */
	public Long getFieldFormulaId()
	{
		return fieldFormulaId;
	}

	/**
	 * @param fieldFormulaId ������������� �������
	 */
	public void setFieldFormulaId(Long fieldFormulaId)
	{
		this.fieldFormulaId = fieldFormulaId;
	}

	/**
	 * @return ��������� � �������
	 */
	public Long getSerial()
	{
		return serial;
	}

	/**
	 * @param serial ��������� � �������
	 */
	public void setSerial(Long serial)
	{
		this.serial = serial;
	}

	/**
	 * @return �������� ����
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * @param value �������� ����
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * @return ��� �������� � �������������� �������
	 */
	public String getSystemId()
	{
		return systemId;
	}

	/**
	 * @param systemId ��� �������� � �������������� �������
	 */
	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}

	/**
	 * @return ������������� ��������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id ������������� ��������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return �������� �� �������� ��������� � ������� (�� ��������� � �������� ���������)
	 */
	public boolean isLast()
	{
		return last;
	}

	/**
	 * @param last �������� �� �������� ��������� � ������� (�� ��������� � �������� ���������)
	 */
	public void setLast(boolean last)
	{
		this.last = last;
	}
}
