package com.rssl.phizic.gate.loanclaim.type;

import com.rssl.phizic.gate.loanclaim.dictionary.TypeOfRealty;
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
 * »нформаци€ об объекте недвижимости
 */
@Immutable
public class RealEstate
{
	private final TypeOfRealty type;

	private final String address;

	private final int purchaseYear;

	private final BigDecimal square;

	private final SquareUnits squareUnits;

	private final BigDecimal cost;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param type - вид собственности (never null)
	 * @param address - адрес (never null)
	 * @param purchaseYear - год приобретени€ (never null)
	 * @param square - площадь (never null)
	 * @param squareUnits - единицы измерени€ площади (never null)
	 * @param cost - рыночна€ стоимость в USD (never null)
	 */
	public RealEstate(TypeOfRealty type, String address, int purchaseYear, BigDecimal square, SquareUnits squareUnits, BigDecimal cost)
	{
		if (type == null)
		    throw new IllegalArgumentException("Ќе указан вид собственности");
		if (StringHelper.isEmpty(address))
		    throw new IllegalArgumentException("Ќе указан адрес");
		if (square == null)
			throw new IllegalArgumentException("Ќе указана площадь");
		if (squareUnits == null)
			throw new IllegalArgumentException("Ќе указаны единицы измерени€ площади");
		if (cost == null)
			throw new IllegalArgumentException("Ќе указана рыночна€ стоимость");

		this.type = type;
		this.address = address;
		this.purchaseYear = purchaseYear;
		this.square = square;
		this.squareUnits = squareUnits;
		this.cost = cost;
	}

	/**
	 * @return вид собственности (never null)
	 */
	public TypeOfRealty getType()
	{
		return type;
	}

	/**
	 * @return адрес (never null)
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * @return год приобретени€ (never null)
	 */
	public int getPurchaseYear()
	{
		return purchaseYear;
	}

	/**
	 * @return площадь (never null)
	 */
	public BigDecimal getSquare()
	{
		return square;
	}

	/**
	 * @return единицы измерени€ площади (never null)
	 */
	public SquareUnits getSquareUnits()
	{
		return squareUnits;
	}

	/**
	 * @return рыночна€ стоимость в USD (never null)
	 */
	public BigDecimal getCost()
	{
		return cost;
	}

	@Override
	public int hashCode()
	{
		return address.hashCode();
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		RealEstate other = (RealEstate) o;

		boolean rc = type.equals(other.type);
		rc = rc && address.equals(other.address);
		rc = rc && purchaseYear == other.purchaseYear;
		rc = rc && square.compareTo(other.square) == 0;
		rc = rc && squareUnits == other.squareUnits;
		rc = rc && cost.compareTo(other.cost) == 0;
		return rc;
	}
}
