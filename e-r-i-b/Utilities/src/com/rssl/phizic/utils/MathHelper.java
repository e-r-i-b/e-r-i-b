package com.rssl.phizic.utils;

/**
 *
 *
 * @author bogdanov
 * @ created 22.04.14
 * @ $Author$
 * @ $Revision$
 */

public class MathHelper
{
	/**
	 * Приближененое значение выражения по трем точкам.
	 * @param x0 первая точка.
	 * @param y0 первая точка.
	 * @param x1 вторая точка.
	 * @param y1 вторая точка.
	 * @param x2 третья точка.
	 * @param y2 третья точка.
	 * @param y значение точки, в которой необходимо найти решение.
	 * @return значение.
	 */
	public static double getApproxValue(double x0, double y0, double x1, double y1, double x2, double y2, double y)
	{
		double s0 = y0 / ((x1 - x0) * (x2 - x0));
		double s1 = y1 / ((x0 - x1) * (x2 - x1));
		double s2 = y2 / ((x0 - x2) * (x1 - x2));
		double a = s0 + s1 + s2;
		double b = -(x1 + x2) * s0 - (x0 + x2) * s1 - (x0 + x1) * s2;
		double c = x1 * x2 * s0 + x0 * x2 * s1 + x0 * x1 * s2;

		double min = evaluateQuadEquation(a, b, c - y, false);
		if (min < 1)
			return evaluateQuadEquation(a, b, c - y, true);
		return min;
	}

	/**
	 * Решение квадратного уравнения.
	 *
	 * @param a коэффициент a.
	 * @param b коэффициент b.
	 * @param c коэффициент с.
	 * @param getMax вернуть максимальное решение.
	 * @return решение уравнения.
	 */
	public static double evaluateQuadEquation(double a, double b, double c, boolean getMax)
	{
		if (a != 0)
		{
			double D = b * b - 4 * a * c;
			double x1 = (-b - Math.sqrt(D)) / (2 * a);
			double x2 = (-b + Math.sqrt(D)) / (2 * a);
			return getMax ? Math.max(x1, x2) : Math.min(x1, x2);
		}
		else if (b != 0)
		{
			return -c/b;
		}
		throw new RuntimeException();
	}
}
