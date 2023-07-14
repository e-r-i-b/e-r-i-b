package com.rssl.phizic.utils.chart;

/**
 * @author akrenev
 * @ created 18.10.2012
 * @ $Author$
 * @ $Revision$
 *
 * ���� �������� �������� ��������
 */

public class DublicateKeyedValue implements Comparable
{
	private Double value;
	private String label;

	public DublicateKeyedValue(Double value, String label)
	{
		this.value = value;
		this.label = label;
	}

	/**
	 * @return �������� ����
	 */
	public Double getValue()
	{
		return value;
	}

	/**
	 * @return �������� �������� ����
	 */
	public String getLabel()
	{
		return label;
	}

	public int compareTo(Object o)
	{
		if (!(o instanceof DublicateKeyedValue))
			return -1;

		DublicateKeyedValue other = (DublicateKeyedValue) o;
		if (value == null && other.value == null)
			return 0;
		
		return value.compareTo(other.value);
	}
}
