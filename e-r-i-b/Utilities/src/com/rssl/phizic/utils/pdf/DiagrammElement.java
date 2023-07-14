package com.rssl.phizic.utils.pdf;

import com.itextpdf.text.BaseColor;

/**
 * @author akrenev
 * @ created 06.03.2012
 * @ $Author$
 * @ $Revision$
 *
 * �������� ����� ���������
 */
public class DiagrammElement
{
	private String name; // ������������ (������������ � �������)
	private BaseColor color; // ���� �����
	private Float weight; // ��� �����

	public DiagrammElement(String name, Float weight, float red, float green, float blue)
	{
		this.name = name;
		this.weight = weight;
		this.color = new BaseColor(red, green, blue);
	}

	public String getName()
	{
		return name;
	}

	public BaseColor getColor()
	{
		return color;
	}

	public Float getWeight()
	{
		return weight;
	}
}
