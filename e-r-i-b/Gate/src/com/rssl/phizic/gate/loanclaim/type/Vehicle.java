package com.rssl.phizic.gate.loanclaim.type;

import com.rssl.phizic.gate.loanclaim.dictionary.TypeOfVehicle;
import com.rssl.phizic.common.types.annotation.Immutable;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;

/**
 * @author Erkin
 * @ created 23.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������������ ��������
 */
@Immutable
public class Vehicle
{
	private final TypeOfVehicle type;

	private final String registationNumber;

	private final int purchaseYear;

	private final String brandName;

	private final int age;

	private final BigDecimal cost;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param type - ��� �� (never null)
	 * @param registationNumber - ��������������� ����� �� (never null)
	 * @param purchaseYear - ��� ������������
	 * @param brandName - ����� �� (never null)
	 * @param age - ������� �� � �����
	 * @param cost - �������� ��������� � �������� ��� (never null)
	 */
	public Vehicle(TypeOfVehicle type, String registationNumber, int purchaseYear, String brandName, int age, BigDecimal cost)
	{
		if (type == null)
		    throw new IllegalArgumentException("�� ������ ��� ��");
		if (StringHelper.isEmpty(registationNumber))
		    throw new IllegalArgumentException("�� ������ ��������������� ����� ��");
		if (StringHelper.isEmpty(brandName))
			throw new IllegalArgumentException("�� ������� ����� ��");
		if (cost == null)
		    throw new IllegalArgumentException("�� ������� �������� ��������� ��");

		this.type = type;
		this.registationNumber = registationNumber;
		this.purchaseYear = purchaseYear;
		this.brandName = brandName;
		this.age = age;
		this.cost = cost;
	}

	/**
	 * @return ��� �� (never null)
	 */
	public TypeOfVehicle getType()
	{
		return type;
	}

	/**
	 * @return ��������������� ����� �� (never null)
	 */
	public String getRegistationNumber()
	{
		return registationNumber;
	}

	/**
	 * @return ��� ������������
	 */
	public int getPurchaseYear()
	{
		return purchaseYear;
	}

	/**
	 * @return ����� �� (never null)
	 */
	public String getBrandName()
	{
		return brandName;
	}

	/**
	 * @return ������� �� � �����
	 */
	public int getAge()
	{
		return age;
	}

	/**
	 * @return �������� ��������� � �������� ��� (never null)
	 */
	public BigDecimal getCost()
	{
		return cost;
	}

	@Override
	public int hashCode()
	{
		int result = type.hashCode();
		result = 31 * result + registationNumber.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Vehicle other = (Vehicle) o;

		boolean rc = type.equals(other.type);
		rc = rc && registationNumber.equals(other.registationNumber);
		rc = rc && brandName.equals(other.brandName);
		rc = rc && purchaseYear == other.purchaseYear;
		rc = rc && cost.compareTo(other.cost) == 0;
		rc = rc && age == other.age;
		return rc;
	}
}
