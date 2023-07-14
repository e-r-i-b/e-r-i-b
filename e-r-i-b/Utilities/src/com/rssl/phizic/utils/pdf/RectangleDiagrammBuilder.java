package com.rssl.phizic.utils.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.List;

/**
 * @author akrenev
 * @ created 06.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class RectangleDiagrammBuilder extends DiagrammBuilder
{
	private float maxValue;
	private float minValue;
	private int stepCount;
	private float leftPadding;
	private float rightPadding;
	private float height;
	private float width;
	private float partWidth;
	private float scale;
	private int maxRowCount;

	/**
	 * Ѕилдер дл€ столбцовой диаграммы
	 * @param writer PdfWriter
	 * @param maxValue верхн€€ граница значений дл€ диаграммы
	 * @param minValue нижн€€ граница значений дл€ диаграммы
	 * @param stepCount количетво промежуточных значений (между minValue и maxValue)
	 * @param leftPadding пространство от предыдущей части до левой границы следующей части
	 * @param rightPadding пространство от правой границы предыдущей части до следующей части
	 * @param fontSize размер шрифта легенды
	 * @param width длина диаграммы
	 * @param height высота диаграммы
	 */
	public RectangleDiagrammBuilder(PdfWriter writer, float maxValue, float minValue, int stepCount, float leftPadding, float rightPadding, float fontSize, float width, float height)
	{
		super(writer, fontSize);
		this.maxValue = maxValue;
		this.minValue = minValue;
		this.stepCount = stepCount;
		this.leftPadding = leftPadding;
		this.rightPadding = rightPadding;
		this.height = height;
		this.width = width;
		float difference = maxValue - minValue;
		scale = difference == 0 ? 0 : height / difference;
	}

	protected float getWidth()
	{
		return width - String.format("%.0f", maxValue).length() * getFontSize() / 2;
	}

	protected float getHeight()
	{
		return height;
	}

	public float getVisibleNecessaryHeight()
	{
		return height + maxRowCount * getLegendRowHeight() + 10;
	}

	protected float getTotalWeight()
	{
		return leftPadding / 2 + partWidth + rightPadding / 2;
	}

	protected float calculateStartY()
	{
		return getVerticalPosition() - getHeight() - 15;
	}

	protected void setMaxRowCount(int count)
	{
		maxRowCount = count;
	}

	protected void setAdditionalData(List<DiagrammElement> data)
	{
		partWidth = (width - (leftPadding + rightPadding) * getPartCount() - rightPadding) / getPartCount();
	}

	protected void createEmptyDiagramm()
	{
		float stepHeight = height / stepCount;
		float x0 = getStartX();
		float y0 = getStartY();
		addLine(x0, y0, x0, y0 + height + 5, BaseColor.LIGHT_GRAY);
		for (int i = 0; i <= stepCount; i++)
		{
			addLine(x0 - 5, y0 + i * stepHeight, x0 - 5 + width, y0 + i * stepHeight, BaseColor.LIGHT_GRAY);

			String label = String.format("%.0f", (minValue + i * (maxValue - minValue) / stepCount));
			addText(label, x0 - 5, y0 + i * stepHeight - getFontSize() * 2 / 5, Element.ALIGN_RIGHT);
		}
	}

	protected void createPartDiagramm(int partIndex, DiagrammElement part)
	{
		float valueHeight = scale * (part.getWeight() - minValue);
		addRectangle(leftPadding + getStartX() + partIndex * (leftPadding + partWidth + rightPadding), getStartY(),
				partWidth, valueHeight,
				part.getColor(), part.getColor());
	}

	protected void createPartLegendDiagramm(int partIndex, DiagrammElement part)
	{
		float valueHeight = scale * (part.getWeight() - minValue);
		String weight = String.format("%.0f", part.getWeight());
		addText(weight, leftPadding + getStartX() + partIndex * (leftPadding + partWidth + rightPadding) + partWidth / 2,
				getStartY() + valueHeight + 2, Element.ALIGN_CENTER);

		List<String> nameParts = getLabels().get(part);
		for (int i = 0; i < nameParts.size(); i++)
		{
			addText(nameParts.get(i), leftPadding + getStartX() + partIndex * (leftPadding + partWidth + rightPadding) + partWidth / 2,
					getStartY() - getFontSize() - 2 - getFontSize() * i, Element.ALIGN_CENTER);
		}
	}
}
