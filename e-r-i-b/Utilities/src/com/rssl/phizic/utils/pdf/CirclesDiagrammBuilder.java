package com.rssl.phizic.utils.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.List;

/**
 * @author akrenev
 * @ created 07.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class CirclesDiagrammBuilder extends DiagrammBuilder
{
	private static final int FULL_ANGLE = 360;
	private static final float START_ANGLE = 90;
	private int allRowCount;
	private float partSum;
	private float diameter;
	private float weightSum;
	private float legendStartPosition;
	private float legendLabelIndex;

	/**
	 * Билдер для круговой диаграммы
	 * @param writer PdfWriter
	 * @param fontSize размер шрифта для легенды
	 * @param diameter диаметр диаграммы
	 */
	public CirclesDiagrammBuilder(PdfWriter writer, float fontSize, float diameter)
	{
		super(writer, fontSize);
		this.diameter = diameter;
	}

	protected float getWidth()
	{
		return diameter;
	}

	protected float getHeight()
	{
		return diameter;
	}

	public float getVisibleNecessaryHeight()
	{
		return getNecessaryHeight() + 25;
	}

	private float getNecessaryHeight()
	{
		return Math.max(diameter, getLegendRowHeight() * (allRowCount + getPartCount()));
	}

	protected float getTotalWeight()
	{
		Rectangle rectangle = getBoxSize();
		return (rectangle.getRight() + rectangle.getLeft()) / 2 - getWidth() / 2 - getLegendRowHeight() - 5;
	}

	protected float calculateStartY()
	{
		return getVerticalPosition() - (getNecessaryHeight() + getHeight()) / 2 - 15;
	}

	protected void setAllRowCount(int count)
	{
		allRowCount = count;
	}

	protected void setAdditionalData(List<DiagrammElement> data)
	{
		weightSum = 0;
		for (DiagrammElement element: data)
		{
			weightSum += element.getWeight();
		}
		partSum = 0;
		legendLabelIndex = 0;
	}

	protected void createEmptyDiagramm()
	{
		legendStartPosition = getStartY() + (diameter + getLegendRowHeight() * (allRowCount + getPartCount())) / 2;
		PdfContentByte canvas = getCanvas();
		canvas.saveState();
		canvas.setColorStroke(BaseColor.LIGHT_GRAY);
		canvas.setColorFill(BaseColor.LIGHT_GRAY);
		canvas.arc(getStartX(), getStartY(), getStartX() + diameter, getStartY() + diameter, START_ANGLE, FULL_ANGLE);
		canvas.fill();
		canvas.restoreState();
	}

	protected void createPartDiagramm(int partIndex, DiagrammElement part)
	{
		PdfContentByte canvas = getCanvas();
		canvas.saveState();
		canvas.setColorStroke(part.getColor());
		canvas.setColorFill(part.getColor());
		float startPosition = START_ANGLE + partSum / weightSum * FULL_ANGLE;
		float increment = part.getWeight() / weightSum * FULL_ANGLE;
		float x0 = getStartX() + diameter / 2;
		float y0 = getStartY() + diameter / 2;
		float x1 = (float) (x0 + diameter / 2 * Math.cos(Math.toRadians(startPosition)));
		float y1 = (float) (y0 + diameter / 2 * Math.sin(Math.toRadians(startPosition)));
		float x2 = (float) (x0 + diameter / 2 * Math.cos(Math.toRadians(startPosition + increment)));
		float y2 = (float) (y0 + diameter / 2 * Math.sin(Math.toRadians(startPosition + increment)));
		canvas.moveTo(x1, y1);
		canvas.lineTo(x0, y0);
		canvas.lineTo(x2, y2);
		canvas.arc(getStartX(), getStartY(), getStartX() + diameter, getStartY() + diameter, startPosition, increment);
		canvas.fill();
		canvas.restoreState();
		partSum += part.getWeight();
	}

	protected void createPartLegendDiagramm(int partIndex, DiagrammElement part)
	{
		float legendXPosition = getStartX() + diameter + 5;
		float height = getLegendRowHeight();
		float legendYPosition = legendStartPosition - legendLabelIndex * height;

		List<String> nameParts = getLabels().get(part);
		float x = legendXPosition + height + 5;
		float y = legendYPosition;
		int size = nameParts.size();
		for (int i = 0; i < size; i++)
		{
			y -= height;
			addText(nameParts.get(i), x, y, Element.ALIGN_LEFT);
			legendLabelIndex++;
		}
		addText(String.format("%.0f (%.2f%%)", part.getWeight(), part.getWeight() / weightSum * 100),
				x, y - height, Element.ALIGN_LEFT);
		legendLabelIndex++;

		addRectangle(legendXPosition, legendStartPosition - height * (legendLabelIndex - (size) / 2.0f) + legendLabelIndex / 2,
				height - 5, height - 5, part.getColor(), part.getColor());
	}
}