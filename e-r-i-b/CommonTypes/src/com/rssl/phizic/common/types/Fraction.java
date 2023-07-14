package com.rssl.phizic.common.types;

import java.math.BigDecimal;

/**
 * @author Erkin
 * @ created 18.08.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * Дробь
 */
public class Fraction extends Number implements Comparable<Fraction>
{
	private final BigDecimal numerator;

	private final BigDecimal denominator;

	private Fraction(BigDecimal numerator, BigDecimal denominator)
	{
		this.numerator = numerator;
		this.denominator = denominator;
	}

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Возвращает дробь с указанным числителем и знаменателем
	 * @param numerator - числитель
	 * @param denominator - знаменатель
	 * @return дробь
	 */
	public static Fraction getFraction(BigDecimal numerator, BigDecimal denominator)
	{
		if (numerator == null)
			throw new NullPointerException("Аргумент 'numerator' не может быть null");
		if (denominator == null)
			throw new NullPointerException("Аргумент 'denominator' не может быть null");
		if (denominator.doubleValue() == 0.0)
		    throw new ArithmeticException("Знаменатель не может быть равен 0");

		return new Fraction(numerator, denominator);
	}

	/**
	 * Возвращает произведение указанной величины на дробь
	 * @param value
	 * @param fraction
	 * @return произведение
	 */
	public static BigDecimal multiply(BigDecimal value, Fraction fraction)
	{
		return value.multiply(fraction.numerator).divide(fraction.denominator);
	}

	/**
	 * Возвращает частное от деления указанной величины на дробь
	 * @param value
	 * @param fraction
	 * @return частное
	 */
	public static BigDecimal divide(BigDecimal value, Fraction fraction)
	{
		return value.multiply(fraction.denominator).divide(fraction.numerator, BigDecimal.ROUND_HALF_UP);
	}

	///////////////////////////////////////////////////////////////////////////

	public int intValue()
	{
		// throws ArithmeticException if not integer
		return numerator.divide(denominator).intValueExact();
	}

	public long longValue()
	{
		// throws ArithmeticException if not integer
		return numerator.divide(denominator).longValueExact();
	}

	public float floatValue()
	{
		return numerator.divide(denominator).floatValue();
	}

	public double doubleValue()
	{
		return numerator.divide(denominator).doubleValue();
	}

	public int compareTo(Fraction o)
	{
		return (numerator.multiply(o.denominator))
				.compareTo(denominator.multiply(o.numerator));
	}

	public int hashCode()
	{
		int result = numerator.hashCode();
		result = 31 * result + denominator.hashCode();
		return result;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		Fraction fraction = (Fraction) o;

		return numerator.compareTo(fraction.numerator) == 0
				&& denominator.compareTo(fraction.denominator) == 0;
	}
}
