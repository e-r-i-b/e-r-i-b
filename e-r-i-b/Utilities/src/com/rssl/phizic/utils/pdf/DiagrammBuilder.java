package com.rssl.phizic.utils.pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author akrenev
 * @ created 06.03.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class DiagrammBuilder
{
	private PdfWriter writer;  //������
	private BaseFont baseFont; //�����
	private float fontSize;    //������ ������
	private float startX;      //��������� �������������� ������� ���������
	private float startY;      //��������� ������������ ������� ���������
	private int partCount;     //���������� ������ (�����) �� ���������
	private List<DiagrammElement> data; //����, �� ������� �������� ���������
	private Map<DiagrammElement, List<String>> labels; //��������� �������� ������ �� ������ (������������ word wrap)
	private float legendRowHeight; // ������ ������ �������

	protected DiagrammBuilder(PdfWriter writer, float fontSize)
	{
		this.writer = writer;
		this.fontSize = fontSize;
		baseFont = new Font(Font.FontFamily.TIMES_ROMAN, fontSize).getCalculatedBaseFont(false);
		legendRowHeight = fontSize + 3;
	}

	protected PdfContentByte getCanvas()
	{
		return writer.getDirectContent();
	}

	protected Rectangle getBoxSize()
	{
		return writer.getBoxSize(PDFBuilder.MAIN_BOX_NAME);
	}

	protected float getVerticalPosition()
	{
		return writer.getVerticalPosition(true);
	}

	protected BaseFont getBaseFont()
	{
		return baseFont;
	}

	protected float getFontSize()
	{
		return fontSize;
	}

	protected float getStartX()
	{
		return startX;
	}

	protected float getStartY()
	{
		return startY;
	}

	protected int getPartCount()
	{
		return partCount;
	}

	protected Map<DiagrammElement, List<String>> getLabels()
	{
		return labels;
	}

	protected float getLegendRowHeight()
	{
		return legendRowHeight;
	}

	protected abstract float getWidth();
	protected abstract float getHeight();
	protected abstract float getTotalWeight();
	protected abstract float calculateStartY();

	/**
	 * @return ������ ���������
	 */
	public abstract float getVisibleNecessaryHeight();

	/**
	 * ������������� ������� ��� �����������
	 * @param data ����������
	 */
	protected void setAdditionalData(List<DiagrammElement> data){}

	/**
	 * �������� ����� ��� ���������
	 */
	protected void createEmptyDiagramm(){}

	/**
	 * �������� ����� ���������
	 */
	protected void createPartDiagramm(int partIndex, DiagrammElement part){}

	/**
	 * �������� ������� ��� ������� ����� ���������
	 */
	protected void createPartLegendDiagramm(int partIndex, DiagrammElement part){}

	/**
	 * @param count ����� ���������� ����� � �������
	 */
	protected void setAllRowCount(int count){};

	/**
	 * @param count ������������ ���������� ����� � ������� �� ���� ������
	 */
	protected void setMaxRowCount(int count){};

	/**
	 * ������������� �������
	 * @param data �������� ������ ���������
	 */
	public void initialize(List<DiagrammElement> data)
	{
		this.data = data;
		if (data == null || data.size() == 0)
		{
			partCount = 0;
			return;
		}

		partCount = 0;
		for (DiagrammElement key: data)
		{
			if (key.getWeight() != null && key.getWeight() != 0)
				partCount++;
		}

		setAdditionalData(data);
		divideNames(data);

		Rectangle rectangle = getBoxSize();
		startX = (rectangle.getRight() + rectangle.getLeft()) / 2 - getWidth() / 2;
		startY = calculateStartY();
	}

	/**
	 * ���������� ���������
	 */
	public void build()
	{
		createEmptyDiagramm();
		for (int index = 0; index < data.size(); index++)
		{
			DiagrammElement element = data.get(index);
			Float weight = element.getWeight();
			if (weight == null || weight == 0)
				continue;

			createPartDiagramm(index, element);
			createPartLegendDiagramm(index, element);
		}
	}

	private float getTextSize(String text)
	{
		return text.length() * getFontSize() / 2;
	}

	/**
	 * ������� �������� ������ ��������� �� ������ (��������� � �����)
	 * @param data �������� ������ ���������
	 */
	private void divideNames(List<DiagrammElement> data)
	{
		int allRowCount = 0;
		int maxRowCount = 1;
		labels = new HashMap<DiagrammElement, List<String>>();
		float totalWeight = getTotalWeight();
		for (DiagrammElement element : data)
		{
			List<String> nameParts = new ArrayList<String>();
			String text = element.getName();
			if (totalWeight > getTextSize(text))
			{
				nameParts.add(text);
				labels.put(element, nameParts);
				allRowCount++;
				continue;
			}

			String[] parts = text.split(" ");
			String currentText = parts[0];
			int rowCount = 1;
			for (int i = 1; i < parts.length; i++)
			{
				if (totalWeight > getTextSize(currentText + " " + parts[i]))
				{
					currentText = currentText + " " + parts[i];
					continue;
				}
				nameParts.add(currentText);
				currentText = parts[i];
				allRowCount++;
				rowCount++;
			}
			nameParts.add(currentText);
			labels.put(element, nameParts);
			maxRowCount = Math.max(maxRowCount, rowCount);
		}
		setAllRowCount(allRowCount);
		setMaxRowCount(maxRowCount);
	}

	protected void addText(String text, float x0, float y0, int align)
	{
		PdfContentByte canvas = getCanvas();
		canvas.beginText();
        canvas.setFontAndSize(getBaseFont(), getFontSize());
		canvas.showTextAligned(align, text, x0, y0, 0);
        canvas.endText();
	}

	protected void addLine(float x0, float y0, float x1, float y1, BaseColor color)
	{
		PdfContentByte canvas = getCanvas();
		canvas.saveState();
		canvas.setColorStroke(color);
		canvas.setColorFill(color);
		canvas.moveTo(x0, y0);
		canvas.lineTo(x1, y1);
		canvas.fill();
		canvas.restoreState();
	}

	protected void addRectangle(float x0, float y0, float dx, float dy, BaseColor lineColor, BaseColor fillColor)
	{
		PdfContentByte canvas = getCanvas();
		canvas.saveState();
		canvas.setColorStroke(lineColor);
		canvas.setColorFill(fillColor);
		canvas.rectangle(x0, y0, dx, dy);
		canvas.fill();
		canvas.restoreState();
	}
}
