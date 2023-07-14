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
 * Транспортное средство
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
	 * @param type - вид ТС (never null)
	 * @param registationNumber - регистрационный номер ТС (never null)
	 * @param purchaseYear - год приобретения
	 * @param brandName - марка ТС (never null)
	 * @param age - возраст ТС в годах
	 * @param cost - рыночная стоимость в долларах США (never null)
	 */
	public Vehicle(TypeOfVehicle type, String registationNumber, int purchaseYear, String brandName, int age, BigDecimal cost)
	{
		if (type == null)
		    throw new IllegalArgumentException("Не указан вид ТС");
		if (StringHelper.isEmpty(registationNumber))
		    throw new IllegalArgumentException("Не указан регистрационный номер ТС");
		if (StringHelper.isEmpty(brandName))
			throw new IllegalArgumentException("Не указана марка ТС");
		if (cost == null)
		    throw new IllegalArgumentException("Не указана рыночная стоимость ТС");

		this.type = type;
		this.registationNumber = registationNumber;
		this.purchaseYear = purchaseYear;
		this.brandName = brandName;
		this.age = age;
		this.cost = cost;
	}

	/**
	 * @return вид ТС (never null)
	 */
	public TypeOfVehicle getType()
	{
		return type;
	}

	/**
	 * @return регистрационный номер ТС (never null)
	 */
	public String getRegistationNumber()
	{
		return registationNumber;
	}

	/**
	 * @return год приобретения
	 */
	public int getPurchaseYear()
	{
		return purchaseYear;
	}

	/**
	 * @return марка ТС (never null)
	 */
	public String getBrandName()
	{
		return brandName;
	}

	/**
	 * @return возраст ТС в годах
	 */
	public int getAge()
	{
		return age;
	}

	/**
	 * @return рыночная стоимость в долларах США (never null)
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
