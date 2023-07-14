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
	 * ������������� �������� ��������� �� ���� ������.
	 * @param x0 ������ �����.
	 * @param y0 ������ �����.
	 * @param x1 ������ �����.
	 * @param y1 ������ �����.
	 * @param x2 ������ �����.
	 * @param y2 ������ �����.
	 * @param y �������� �����, � ������� ���������� ����� �������.
	 * @return ��������.
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
	 * ������� ����������� ���������.
	 *
	 * @param a ����������� a.
	 * @param b ����������� b.
	 * @param c ����������� �.
	 * @param getMax ������� ������������ �������.
	 * @return ������� ���������.
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
