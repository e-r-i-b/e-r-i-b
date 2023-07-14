package com.rssl.phizic.utils.chart;

import org.jfree.chart.axis.NumberTickUnit;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * @author akrenev
 * @ created 26.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class MoneyTickUnit extends NumberTickUnit
{
	private static final double maxSimpleNumber = Math.pow(10, 18);
	private static final String EXPONENT_FORMAT_CHARACTER = "E";
	private static final String EXPONENT_REAL_CHARACTER = "*10^";
	private static final DecimalFormat EXPONENT_FORMAT = new DecimalFormat("0.0" + EXPONENT_FORMAT_CHARACTER + "0", new DecimalFormatSymbols(Locale.US));
	private static final DecimalFormat GROUPED_FORMAT = new DecimalFormat("#,##0", new DecimalFormatSymbols(Locale.getDefault()));

	public MoneyTickUnit(double v)
	{
		super(v);
	}

	public MoneyTickUnit(double v, NumberFormat numberFormat)
	{
		super(v, numberFormat);
	}

	public MoneyTickUnit(double v, NumberFormat numberFormat, int i)
	{
		super(v, numberFormat, i);
	}

	public String valueToString(double v)
	{
		if (v >= maxSimpleNumber)
		{
			return EXPONENT_FORMAT.format(v).replace(EXPONENT_FORMAT_CHARACTER, EXPONENT_REAL_CHARACTER);
		}
		return GROUPED_FORMAT.format(v);
	}
}
