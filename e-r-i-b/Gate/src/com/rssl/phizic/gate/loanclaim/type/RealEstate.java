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
 * ���������� �� ������� ������������
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
	 * @param type - ��� ������������� (never null)
	 * @param address - ����� (never null)
	 * @param purchaseYear - ��� ������������ (never null)
	 * @param square - ������� (never null)
	 * @param squareUnits - ������� ��������� ������� (never null)
	 * @param cost - �������� ��������� � USD (never null)
	 */
	public RealEstate(TypeOfRealty type, String address, int purchaseYear, BigDecimal square, SquareUnits squareUnits, BigDecimal cost)
	{
		if (type == null)
		    throw new IllegalArgumentException("�� ������ ��� �������������");
		if (StringHelper.isEmpty(address))
		    throw new IllegalArgumentException("�� ������ �����");
		if (square == null)
			throw new IllegalArgumentException("�� ������� �������");
		if (squareUnits == null)
			throw new IllegalArgumentException("�� ������� ������� ��������� �������");
		if (cost == null)
			throw new IllegalArgumentException("�� ������� �������� ���������");

		this.type = type;
		this.address = address;
		this.purchaseYear = purchaseYear;
		this.square = square;
		this.squareUnits = squareUnits;
		this.cost = cost;
	}

	/**
	 * @return ��� ������������� (never null)
	 */
	public TypeOfRealty getType()
	{
		return type;
	}

	/**
	 * @return ����� (never null)
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * @return ��� ������������ (never null)
	 */
	public int getPurchaseYear()
	{
		return purchaseYear;
	}

	/**
	 * @return ������� (never null)
	 */
	public BigDecimal getSquare()
	{
		return square;
	}

	/**
	 * @return ������� ��������� ������� (never null)
	 */
	public SquareUnits getSquareUnits()
	{
		return squareUnits;
	}

	/**
	 * @return �������� ��������� � USD (never null)
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
