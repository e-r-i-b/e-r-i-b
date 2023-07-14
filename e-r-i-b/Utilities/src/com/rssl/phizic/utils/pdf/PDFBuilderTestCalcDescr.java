package com.rssl.phizic.utils.pdf;

import com.itextpdf.text.Chunk;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.chart.*;
import junit.framework.TestCase;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;
import javax.imageio.ImageIO;

/**
 * @author lepihina
 * @ created 13.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class PDFBuilderTestCalcDescr  extends TestCase
{
	private static final String resoursePath = "D:\\NEW_IKFL\\v.1.18_release_11.2\\Docs\\resources\\pfp\\";
	private static final String bgChartPath = resoursePath + "green_arrow.png";
	private PDFBuilder builder;
	private ChartBuilder chartBiulder;

	private static final Map<String, Color> productColor= new HashMap<String, Color>();
	private static final Map<String, Color> barColor= new HashMap<String, Color>();

	private static final int PDF_CHART_WIDTH = 500;
	private static final int PDF_CHART_HEIGHT= 250;
	private static final int PDF_PIE_CHART_HEIGHT = 170;
	private static final int LOGO_WIDTH = 185;
	private static final int LOGO_HEIGHT = 50;
	private static final int CHART_WIDTH = 1000;
	private static final int CHART_HEIGHT = 500;
	private static final int PIE_CHART_HEIGHT = 340;

	private static final Color GRAY_BG_COLOR = new Color(242,242,242);

	public static final ParagraphStyle EXAMPLE_TITLE_PARAGRAPH = new ParagraphStyle(12f, 15f, 4f, 0f, Alignment.center);
	public static final FontStyle EXAMPLE_TITLE_FONT = new FontStyle(12, FontDecoration.italic, Color.GRAY);

	private static final CellStyle CELL_STYLE = new CellStyle();
	private static final CellStyle CELL_STYLE_CENTER = new CellStyle(CELL_STYLE);

	static
	{
		productColor.put("account",   new Color(75,184,9));
		productColor.put("fund",      new Color(255,96,0));
		productColor.put("IMA",       new Color(146,79,196));
		productColor.put("insurance", new Color(0,176,240));

		barColor.put("account",    new Color(57,210,241));
		barColor.put("ima",        new Color(255,134,7));
		barColor.put("prognostic", new Color(49,154,35));
		barColor.put("accurate",    new Color(190,10,73));

		CELL_STYLE.setBorderWidth(0);
		CELL_STYLE.setBackgroundColor(GRAY_BG_COLOR);

		CELL_STYLE_CENTER.setHorizontalAlignment(Alignment.center);
	}

	public void testPDFBuilder() throws IOException, PDFBuilderException
	{
		byte[] data = createPDF();
		writeToFile(data, "C:\\temp\\PDFBuilderTestCalcDescr.pdf");
	}

	private byte[] createPDF() throws PDFBuilderException, IOException
	{
		HeaderFooterBuilder headerFooterBuilder = new HeaderFooterBuilder();
		headerFooterBuilder.addImageInstance(resoursePath + "logoSBRFNew.png", LOGO_WIDTH, LOGO_HEIGHT, Alignment.right);
		headerFooterBuilder.addPageNumberInstance();
		builder = PDFBuilder.getInstance(headerFooterBuilder, resoursePath + PDFBuilder.FONT_NAME, 12, DocumentOrientation.VERTICAL);
		chartBiulder = new ChartBuilder();

		builder.addParagraph("��� �������������� ������� ��������� ��������?", PDFBuilder.TITLE_PARAGRAPH, PDFBuilder.TITLE_FONT);
		builder.addParagraph("��� ������� � �������� �������� ���������������� � �������� �� ����������� ����������. � �������� �� ����������� ������������� " +
				"��������� �� �������. ��� �������� � ������������ �������� �� ���������� ����������� ���������� ���������, ������� ������������ �� �������:", PDFBuilder.BASE_PARAGRAPH);
		addQuartIncomeFormula();
		addExample1();
		addExample2();
		addMethodCalc();
		addProductTerm();
		addInsuranceCalc();

	    return builder.build();
	}

	private void addQuartIncomeFormula() throws PDFBuilderException
	{
		CellStyle numeratorStyle = new CellStyle();
		numeratorStyle.setBorderWidth(0);
		numeratorStyle.setBorderWidthBottom(0.4f);
		numeratorStyle.setBorderColor(Color.GRAY);

		PDFTableBuilder fraction = builder.getTableBuilderInstance(2, resoursePath + PDFBuilder.FONT_NAME, new ParagraphStyle(12f, 0f, 4f, 0f));

		int[] widths = {25, 10}; // ������ �������� �������
		fraction.setWidths(360, widths);

		java.util.List<Chunk> leftPart = new java.util.ArrayList<Chunk>();
		leftPart.add(builder.textString("I",PDFTableStyles.BOLD_FONT));
		leftPart.add(builder.subString("��.", PDFBuilder.SUB_SUP_BOLD_FONT));
		leftPart.add(builder.textString(" = ",PDFTableStyles.BOLD_FONT));

		java.util.List<Chunk> numerator = new java.util.ArrayList<Chunk>();
		numerator.add(builder.textString("I",PDFTableStyles.BOLD_FONT));
		numerator.add(builder.subString("�.", PDFBuilder.SUB_SUP_BOLD_FONT));

		fraction.addElementsToCell(leftPart, 1, 2, PDFTableStyles.CELL_WITHOUT_BORDER);
		fraction.addElementsToCell(numerator, 1, 1, numeratorStyle);
		fraction.addValueToCell("4", PDFTableStyles.BOLD_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);

		fraction.addToPage(TableBreakRule.wholeTableInPage);

		builder.addParagraph("���:");
		builder.addTextString("I", PDFTableStyles.BOLD_FONT);
		builder.addSubString("�.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString(" � ������� ���������� ��������;");

		builder.addEmptyParagraph();
		builder.addTextString("I", PDFTableStyles.BOLD_FONT);
		builder.addSubString("��.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString(" � ����������� ���������� ��������.");

		builder.addParagraph("� ����� � ���� ���������� ������� ��������� �������� ����� ���������� �� �������� ��������.", PDFBuilder.BASE_PARAGRAPH);
	}

	private void addExample1() throws PDFBuilderException
	{
		PDFTableBuilder grayBg = builder.getTableBuilderInstance(1, resoursePath + PDFBuilder.FONT_NAME, PDFBuilder.BASE_PARAGRAPH);
		grayBg.setTableWidthPercentage(100);

		List<Chunk> paragraphs = new ArrayList<Chunk>();
		grayBg.addValueToCell("������ 1", EXAMPLE_TITLE_FONT, CELL_STYLE_CENTER);
		grayBg.addValueToCell("�� ���������� ����� �� ���������� �����������:", PDFBuilder.TEXT_FONT, CELL_STYLE);

		paragraphs.add(builder.textString("- ����� ������: 100 000 ������;", PDFBuilder.TEXT_FONT));
		grayBg.addElementsToCell(paragraphs, 1, 1, CELL_STYLE);

		paragraphs.clear();
		paragraphs.add(builder.textString("- ���� ������: 1 ��� (4 ��������);",PDFBuilder.TEXT_FONT));
		grayBg.addElementsToCell(paragraphs, 1, 1, CELL_STYLE);

		paragraphs.clear();
		paragraphs.add(builder.textString("- ������ �� ������: 10% �������.",PDFBuilder.TEXT_FONT));
		grayBg.addElementsToCell(paragraphs, 1, 1, CELL_STYLE);

		grayBg.addValueToCell("��� ���� �� ������ ������������� ����������� ������������� ���������.", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("������� ������ ������ � ��������� �� �������� ���������.", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("", PDFBuilder.TEXT_FONT, CELL_STYLE);

		java.util.List<BarChartItem> productList = new ArrayList<BarChartItem>();
		double incomeMoney = 100000;
		double outcomeMoney = 110381;
		double freeMoney = 110471;

		productList.add(new BarChartItem(incomeMoney, "", "����� ������", barColor.get("account")));
		productList.add(new BarChartItem(outcomeMoney, "", "�������", barColor.get("prognostic")));
		productList.add(new BarChartItem(freeMoney, "", "������ ������", barColor.get("accurate")));

		ChartStyle chartStyle = new ChartStyle();
		chartStyle.setBackgroundColor(GRAY_BG_COLOR);
		chartStyle.setPlotBackgroundColor(GRAY_BG_COLOR);
		ChartSettings chartSettings = new ChartSettings(CHART_WIDTH, CHART_HEIGHT, chartStyle);
		chartSettings.setChartTitle("������� 1. ��������� �������� ����������");
		chartSettings.setTitleAxisY("�����, ���.");

		grayBg.addImageToCurrentRow(chartBiulder.generate3DBarChart(productList, chartSettings, 3), PDF_CHART_WIDTH, PDF_CHART_HEIGHT, 1, 1, CELL_STYLE_CENTER);
		
		grayBg.addToPage(TableBreakRule.twoLineMinimumInPage);
	}

	private void addExample2() throws PDFBuilderException
	{
		PDFTableBuilder grayBg = builder.getTableBuilderInstance(1, resoursePath + PDFBuilder.FONT_NAME, PDFBuilder.BASE_PARAGRAPH);
		grayBg.setTableWidthPercentage(100);

		grayBg.addValueToCell("������ 2", EXAMPLE_TITLE_FONT, CELL_STYLE_CENTER);
		grayBg.addValueToCell("� ��� � �������� �� 100 000 ������ ������� ������ (������������ ������������� ����).", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("�����������, �� �������� ���������� �� ����� �������� 30% ������� (7,5% � �������).", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("��� ��� ������ ������������ �� ����������� ������, �� ���������� ������� ����� ���������� �� �������� ��������.", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("���������� ������� � �������� ��� ���������� ������� 1 ��� (4 ��������).", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("��� ���� �� ������ ������������� ����������� ������������� ���������.", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("������� ������� �� ������������ �� ����� ����� (������ ������) � ��������� ������� ��������� �������� (�������).", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("", PDFBuilder.TEXT_FONT, CELL_STYLE);

		java.util.List<BarChartItem> productList = new ArrayList<BarChartItem>();
		double incomeMoney = 100000;
		double outcomeMoney = 133547;
		double freeMoney = 130000;

		productList.add(new BarChartItem(incomeMoney, "", "������", barColor.get("ima")));
		productList.add(new BarChartItem(outcomeMoney, "", "�������", barColor.get("prognostic")));
		productList.add(new BarChartItem(freeMoney, "", "������ ������", barColor.get("accurate")));

		ChartStyle chartStyle = new ChartStyle();
		chartStyle.setBackgroundColor(GRAY_BG_COLOR);
		chartStyle.setPlotBackgroundColor(GRAY_BG_COLOR);
		ChartSettings chartSettings = new ChartSettings(CHART_WIDTH, CHART_HEIGHT, chartStyle);
		chartSettings.setChartTitle("������� 2. ��������� �������� ����������");
		chartSettings.setTitleAxisY("�����, ���.");

		grayBg.addImageToCurrentRow(chartBiulder.generate3DBarChart(productList, chartSettings, 3), PDF_CHART_WIDTH, PDF_CHART_HEIGHT, 1, 1, CELL_STYLE_CENTER);
		grayBg.addValueToCell("", PDFBuilder.TEXT_FONT, CELL_STYLE);

		grayBg.addToPage(TableBreakRule.twoLineMinimumInPage);
	}

	private void addExample3() throws PDFBuilderException
	{
		PDFTableBuilder grayBg = builder.getTableBuilderInstance(1, resoursePath + PDFBuilder.FONT_NAME, PDFBuilder.BASE_PARAGRAPH);
		grayBg.setTableWidthPercentage(100);

		grayBg.addValueToCell("������ 3", EXAMPLE_TITLE_FONT, CELL_STYLE_CENTER);
		grayBg.addValueToCell("�� ������������ �������� ��������� �������:", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("- ��������� �������� ���������� ������� = 1 000 000 ������; ", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("- ���������� �������� ���������� ������� = 20% �������. (����������� ���������� 5,0%).", PDFBuilder.TEXT_FONT, CELL_STYLE);

		Calendar date = DateHelper.getCurrentDate();
		grayBg.addValueToCell("������ " + DateHelper.formatDateWithQuarters(date) + " ����.", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("��������� ������� ��������� �������� � ������� ��������� 6 ���������.", PDFBuilder.TEXT_FONT, CELL_STYLE);

		java.util.List<StackedBarChartItem> graphList = new ArrayList<StackedBarChartItem>();
		Category category1 = new Category("��������� �����", new Color(43, 205, 255), Color.black, "��������� �����", new ImageLegendItem("��������� �����", resoursePath + "light_blue.png"));
		Category category2 = new Category("�����", new Color(255, 134, 7), Color.black, "�����", new ImageLegendItem("�����", resoursePath + "orange.png"));
		graphList.add(new StackedBarChartItem(category1, 1000000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(category2, 0, DateHelper.formatDateWithQuarters(date)));
		date.add(Calendar.MONTH, 3);
		graphList.add(new StackedBarChartItem(category1, 1000000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(category2, 50000, DateHelper.formatDateWithQuarters(date)));
		date.add(Calendar.MONTH, 3);
		graphList.add(new StackedBarChartItem(category1, 1000000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(category2, 102500, DateHelper.formatDateWithQuarters(date)));
		date.add(Calendar.MONTH, 3);
		graphList.add(new StackedBarChartItem(category1, 1000000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(category2, 157625, DateHelper.formatDateWithQuarters(date)));
		date.add(Calendar.MONTH, 3);
		graphList.add(new StackedBarChartItem(category1, 1000000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(category2, 215506, DateHelper.formatDateWithQuarters(date)));
		date.add(Calendar.MONTH, 3);
		graphList.add(new StackedBarChartItem(category1, 1000000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(category2, 276282, DateHelper.formatDateWithQuarters(date)));
		date.add(Calendar.MONTH, 3);
		graphList.add(new StackedBarChartItem(category1, 1000000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(category2, 340096, DateHelper.formatDateWithQuarters(date)));

		ChartStyle chartStyle = new ChartStyle();
		chartStyle.setBackgroundColor(GRAY_BG_COLOR);
		chartStyle.setPlotBackgroundColor(GRAY_BG_COLOR);
		ChartSettings chartSettings = new ChartSettings(CHART_WIDTH, CHART_HEIGHT, chartStyle);
		chartSettings.setChartTitle("������� 3. �������� �������� ���������� �������");
		chartSettings.setTitleAxisX("");
		chartSettings.setTitleAxisY("��������, ���.");

		grayBg.addImageToCurrentRow(chartBiulder.generate3DStakedBarChartWithSeveralDomainAxis(graphList, chartSettings, 2), PDF_CHART_WIDTH, PDF_CHART_HEIGHT, CELL_STYLE_CENTER);
		grayBg.addValueToCell("", PDFBuilder.TEXT_FONT, CELL_STYLE);

		grayBg.addToPage(TableBreakRule.twoLineMinimumInPage);
	}

	private void addExample4() throws PDFBuilderException
	{
		PDFTableBuilder grayBg = builder.getTableBuilderInstance(1, resoursePath + PDFBuilder.FONT_NAME, PDFBuilder.BASE_PARAGRAPH);
		grayBg.setTableWidthPercentage(100);

		grayBg.addValueToCell("������ 4", EXAMPLE_TITLE_FONT, CELL_STYLE_CENTER);
		Calendar dateNext = DateHelper.getCurrentDate();

		grayBg.addValueToCell("������������� �� ������ ���������� �� 245 000 ������.", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("���������� �������� ��������������� ��������� = 17,0% �������. (����������� ���������� 4,3%)", PDFBuilder.TEXT_FONT, CELL_STYLE);
		Calendar startDate = (Calendar) dateNext.clone();
		startDate.add(Calendar.MONTH, 3);
		grayBg.addValueToCell("������ "+ DateHelper.formatDateWithQuarters(DateHelper.getCurrentDate()) +" ����. " +
				"������ ���������� � " + DateHelper.getQuarter(startDate) + " �������� " + startDate.get(Calendar.YEAR) + " ����.",
				PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("��������� ������� ��������� �������� � ������� ��������� 6 ���������:", PDFBuilder.TEXT_FONT, CELL_STYLE);

		java.util.List<StackedBarChartItem> graphList = new ArrayList<StackedBarChartItem>();
		Category category1 = new Category("��������� �����", new Color(124, 180, 24), Color.black, "��������� �����", new ImageLegendItem("��������� �����", resoursePath + "light_green.png"));
		Category category2 = new Category("�������", new Color(255, 134, 7), Color.black, "�����", new ImageLegendItem("�����", resoursePath + "orange.png"));

		graphList.add(new StackedBarChartItem(category1, 0, DateHelper.formatDateWithQuarters(dateNext)));
		graphList.add(new StackedBarChartItem(category2, 0, DateHelper.formatDateWithQuarters(dateNext)));
		dateNext.add(Calendar.MONTH, 3);
		graphList.add(new StackedBarChartItem(category1, 245000, DateHelper.formatDateWithQuarters(dateNext)));
		graphList.add(new StackedBarChartItem(category2, 0, DateHelper.formatDateWithQuarters(dateNext)));
		dateNext.add(Calendar.MONTH, 3);
		graphList.add(new StackedBarChartItem(category1, 490000, DateHelper.formatDateWithQuarters(dateNext)));
		graphList.add(new StackedBarChartItem(category2, 10413, DateHelper.formatDateWithQuarters(dateNext)));
		dateNext.add(Calendar.MONTH, 3);
		graphList.add(new StackedBarChartItem(category1, 735000, DateHelper.formatDateWithQuarters(dateNext)));
		graphList.add(new StackedBarChartItem(category2, 31680, DateHelper.formatDateWithQuarters(dateNext)));
		dateNext.add(Calendar.MONTH, 3);
		graphList.add(new StackedBarChartItem(category1, 980000, DateHelper.formatDateWithQuarters(dateNext)));
		graphList.add(new StackedBarChartItem(category2, 64264, DateHelper.formatDateWithQuarters(dateNext)));
		dateNext.add(Calendar.MONTH, 3);
		graphList.add(new StackedBarChartItem(category1, 1225000, DateHelper.formatDateWithQuarters(dateNext)));
		graphList.add(new StackedBarChartItem(category2, 108645, DateHelper.formatDateWithQuarters(dateNext)));
		dateNext.add(Calendar.MONTH, 3);
		graphList.add(new StackedBarChartItem(category1, 1470000, DateHelper.formatDateWithQuarters(dateNext)));
		graphList.add(new StackedBarChartItem(category2, 165325, DateHelper.formatDateWithQuarters(dateNext)));

		ChartStyle chartStyle = new ChartStyle();
		chartStyle.setBackgroundColor(GRAY_BG_COLOR);
		ChartSettings chartSettings = new ChartSettings(CHART_WIDTH, CHART_HEIGHT, chartStyle);
		chartSettings.setChartTitle("������� 4. �������� �������� ��������������� ���������");
		chartSettings.setTitleAxisX("");
		chartSettings.setTitleAxisY("��������, ���.");

		try
		{
			chartStyle.setPlotBackgroundColor(new TexturePaint(ImageIO.read(new File(resoursePath + "example4_bg.jpg")), new Rectangle(0, 0, CHART_WIDTH, CHART_HEIGHT)));
		}
		catch (IOException ignore)
		{
			chartStyle.setBackgroundColor(GRAY_BG_COLOR);
		}

		grayBg.addImageToCurrentRow(chartBiulder.generate3DStakedBarChartWithSeveralDomainAxis(graphList, chartSettings, 2), PDF_CHART_WIDTH, PDF_CHART_HEIGHT, CELL_STYLE_CENTER);
		grayBg.addValueToCell("", PDFBuilder.TEXT_FONT, CELL_STYLE);

		grayBg.addToPage(TableBreakRule.twoLineMinimumInPage);
	}

	private void addExample5() throws PDFBuilderException
	{
		PDFTableBuilder grayBg = builder.getTableBuilderInstance(1, resoursePath + PDFBuilder.FONT_NAME, PDFBuilder.BASE_PARAGRAPH);
		grayBg.setTableWidthPercentage(100);

		grayBg.addValueToCell("������ 5", EXAMPLE_TITLE_FONT, CELL_STYLE_CENTER);
		grayBg.addValueToCell("��� ��������� ���� �������� 3 � 4 ��������� ������� ��������� ���������� ���������:", PDFBuilder.TEXT_FONT, CELL_STYLE);

		Calendar date = DateHelper.getCurrentDate();
		java.util.List<StackedBarChartItem> graphList = new ArrayList<StackedBarChartItem>();
		Category category1 = new Category("��������� �����", new Color(170, 62, 172), Color.black, "��������� �����", new ImageLegendItem("��������� �����", resoursePath + "violet.png"));
		Category category2 = new Category("�����", new Color(255, 134, 7), Color.black, "�����", new ImageLegendItem("�����", resoursePath + "orange.png"));

		graphList.add(new StackedBarChartItem(category1, 1000000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(category2, 0, DateHelper.formatDateWithQuarters(date)));
		date.add(Calendar.MONTH, 3);
		graphList.add(new StackedBarChartItem(category1, 1250000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(category2, 50000, DateHelper.formatDateWithQuarters(date)));
		date.add(Calendar.MONTH, 3);
		graphList.add(new StackedBarChartItem(category1, 1490000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(category2, 112913, DateHelper.formatDateWithQuarters(date)));
		date.add(Calendar.MONTH, 3);
		graphList.add(new StackedBarChartItem(category1, 1735000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(category2, 189305, DateHelper.formatDateWithQuarters(date)));
		date.add(Calendar.MONTH, 3);
		graphList.add(new StackedBarChartItem(category1, 1980000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(category2, 279770, DateHelper.formatDateWithQuarters(date)));
		date.add(Calendar.MONTH, 3);
		graphList.add(new StackedBarChartItem(category1, 2225000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(category2, 384927, DateHelper.formatDateWithQuarters(date)));
		date.add(Calendar.MONTH, 3);
		graphList.add(new StackedBarChartItem(category1, 2470000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(category2, 505421, DateHelper.formatDateWithQuarters(date)));

		ChartStyle chartStyle = new ChartStyle();
		chartStyle.setBackgroundColor(GRAY_BG_COLOR);
		chartStyle.setPlotBackgroundColor(GRAY_BG_COLOR);
		ChartSettings chartSettings = new ChartSettings(CHART_WIDTH, CHART_HEIGHT, chartStyle);
		chartSettings.setChartTitle("������� 5. �������� ��������� ��������");
		chartSettings.setTitleAxisX("");
		chartSettings.setTitleAxisY("��������, ���.");

		grayBg.addImageToCurrentRow(chartBiulder.generate3DStakedBarChartWithSeveralDomainAxis(graphList, chartSettings, 2), PDF_CHART_WIDTH, PDF_CHART_HEIGHT, CELL_STYLE_CENTER);
		grayBg.addValueToCell("", PDFBuilder.TEXT_FONT, CELL_STYLE);

		grayBg.addToPage(TableBreakRule.twoLineMinimumInPage);
	}

	private void addExample6() throws PDFBuilderException
	{
		PDFTableBuilder grayBg = builder.getTableBuilderInstance(1, resoursePath + PDFBuilder.FONT_NAME, PDFBuilder.BASE_PARAGRAPH);
		grayBg.setTableWidthPercentage(100);

		grayBg.addValueToCell("������ 6", EXAMPLE_TITLE_FONT, CELL_STYLE_CENTER);
		grayBg.addValueToCell("���������� ������, ��� ������� � �������������� ���� �������� �� �������� �������� ����������� ���������. " +
				"��� ���������� ��-�� ����, ��� ������� �������� ������������� ������ ����������.", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("��� �������� ������� �� 3-� ���������:", PDFBuilder.TEXT_FONT, CELL_STYLE);

		// ���������� �������
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(3, resoursePath + PDFBuilder.FONT_NAME, PDFBuilder.BASE_PARAGRAPH);
		tableBuilder.setTableWidthPercentage(100);

		CellStyle cellStyle = new CellStyle();
		cellStyle.setBorderWidth(0);
		cellStyle.setBackgroundColor(GRAY_BG_COLOR);
		cellStyle.setBorderWidthBottom(0.2f); // ������������� ������ ������ �������
		cellStyle.setBorderColor(Color.GRAY); // ���� ������ �������

		tableBuilder.addValueToCell("�������", PDFTableStyles.TEXT_FONT, PDFTableStyles.TH_CELL_STYLE);
		tableBuilder.addValueToCell("����������", PDFTableStyles.TEXT_FONT, PDFTableStyles.TH_CELL_STYLE);
		tableBuilder.addValueToCell("�����", PDFTableStyles.TEXT_FONT, PDFTableStyles.TH_CELL_STYLE);

		tableBuilder.addValueToCell("����� ���������", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("6,1%", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("200 000,00 ���.", PDFTableStyles.TEXT_FONT, cellStyle);

		tableBuilder.addValueToCell("��� ������", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("22,3%", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("500 000,00 ���.", PDFTableStyles.TEXT_FONT, cellStyle);

		tableBuilder.addValueToCell("��� �������", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("18,5%", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("300 000,00 ���.", PDFTableStyles.TEXT_FONT, cellStyle);
		// end ���������� �������

		grayBg.addTableToCell(tableBuilder, CELL_STYLE);

		grayBg.addValueToCell("����� ���������� �� ��������� ������� ����������� ������� � ����� ��������:", PDFBuilder.TEXT_FONT, CELL_STYLE);

		java.util.List<PieChartItem> productList = new ArrayList<PieChartItem>();
		productList.add(new PieChartItem("������", 20, productColor.get("account")));
		productList.add(new PieChartItem("���", 30, productColor.get("IMA")));
		productList.add(new PieChartItem("����", 50, productColor.get("fund")));

		ChartStyle chartStyle = new ChartStyle();
		chartStyle.setBackgroundColor(GRAY_BG_COLOR);
		chartStyle.setPlotBackgroundColor(GRAY_BG_COLOR);
		ChartSettings chartSettings = new ChartSettings(CHART_WIDTH, PIE_CHART_HEIGHT, chartStyle);
		chartSettings.setChartTitle("������� 6. ��������� ����������� ��������� � ��������");

		grayBg.addImageToCurrentRow(chartBiulder.generate3DPieChart(productList, chartSettings), PDF_CHART_WIDTH, PDF_PIE_CHART_HEIGHT, CELL_STYLE_CENTER);

		grayBg.addValueToCell("� ������ ��������� ����������� ����� ��� �� ��������:", PDFBuilder.TEXT_FONT, CELL_STYLE);

		// ���������� �������
		tableBuilder = builder.getTableBuilderInstance(3, resoursePath + PDFBuilder.FONT_NAME, PDFBuilder.BASE_PARAGRAPH);
		tableBuilder.setTableWidthPercentage(100);

		tableBuilder.addValueToCell("�������", PDFTableStyles.TEXT_FONT, PDFTableStyles.TH_CELL_STYLE);
		tableBuilder.addValueToCell("����������", PDFTableStyles.TEXT_FONT, PDFTableStyles.TH_CELL_STYLE);
		tableBuilder.addValueToCell("����� ����� ���", PDFTableStyles.TEXT_FONT, PDFTableStyles.TH_CELL_STYLE);

		tableBuilder.addValueToCell("����� ���������", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("6,1%", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("212 481,90 ���.", PDFTableStyles.TEXT_FONT, cellStyle);

		tableBuilder.addValueToCell("��� ������", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("22,3%", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("621 175,60 ���.", PDFTableStyles.TEXT_FONT, cellStyle);

		tableBuilder.addValueToCell("��� �������", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("18,5%", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("359 470,40 ���.", PDFTableStyles.TEXT_FONT, cellStyle);
		// end ���������� �������

		grayBg.addTableToCell(tableBuilder, CELL_STYLE);

		grayBg.addValueToCell("� ���������� ����������� ��������� � �������� ����� ���������:", PDFBuilder.TEXT_FONT, CELL_STYLE);

		productList.clear();
		productList.add(new PieChartItem("������", 18, productColor.get("account")));
		productList.add(new PieChartItem("���", 30, productColor.get("IMA")));
		productList.add(new PieChartItem("����", 52, productColor.get("fund")));

		chartSettings.setChartTitle("������� 7. ����������� ��������� � �������� ����� ���");
		grayBg.addImageToCurrentRow(chartBiulder.generate3DPieChart(productList, chartSettings), PDF_CHART_WIDTH, PDF_PIE_CHART_HEIGHT, CELL_STYLE_CENTER);

		grayBg.addValueToCell("� ����� � ���, ��� ���������� � ������ ��������� ����������, ��������� �������� �������� ��������.", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("� ������ ������� ��� ������������ �������� ��������� �������� ����� ������� ���������� ������� ���������� �� ������, " +
				"� ������� � ������������� � ���, ����� ������������ �������������� ������������� ��������.", PDFBuilder.TEXT_FONT, CELL_STYLE);

		grayBg.addToPage(TableBreakRule.twoLineMinimumInPage);
	}

	private void addExample7() throws PDFBuilderException
	{
		PDFTableBuilder grayBg = builder.getTableBuilderInstance(1, resoursePath + PDFBuilder.FONT_NAME, PDFBuilder.BASE_PARAGRAPH);
		grayBg.setTableWidthPercentage(100);

		grayBg.addValueToCell("������ 7", EXAMPLE_TITLE_FONT, CELL_STYLE_CENTER);
		grayBg.addValueToCell("�� ���������� ��������� �������������� ����������� ����� � ��������� �������    1 000 000 ������.", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("���������� �� �������� 10% ������� (2,5% �� �������).", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("��� ������� ����� ������, ��� 1 000 000 ������ �� ��� ���������� ��� 10% ������� � ������ ������� ������ ��� " +
				"������������� ���������� �� 250 000 ������ (1 000 000/4) ��� ����� �� ���������� (10% �������), � ����� ��� �������� ��������� " +
				"����� �� 1 000 000 ������ (�����, ����������� �� 4 ��������).", PDFBuilder.TEXT_FONT, CELL_STYLE);

		Calendar date = DateHelper.getCurrentDate();

		java.util.List<StackedBarChartItem> graphList = new ArrayList<StackedBarChartItem>();
		final String label = "{2}";
		Category catogory1 = new Category("1", new Color(1,176,235), Color.white, label);
		Category catogory2 = new Category("2", new Color(0, 94, 87), Color.white, label);
		Category catogory3 = new Category("3", new Color(0, 181, 171), label);
		Category category4 = new Category("4", new Color(128, 206, 203), label);
		Category catogory5 = new Category("5", new Color(160, 231, 227), label);
		graphList.add(new StackedBarChartItem(catogory1, 1000000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(catogory2, 0, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(catogory3, 0, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(category4, 0, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(catogory5, 0, DateHelper.formatDateWithQuarters(date)));
		date.add(Calendar.MONTH, 3);
		graphList.add(new StackedBarChartItem(catogory1, 0, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(catogory2, 250000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(catogory3, 0, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(category4, 0, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(catogory5, 0, DateHelper.formatDateWithQuarters(date)));
		date.add(Calendar.MONTH, 3);
		graphList.add(new StackedBarChartItem(catogory1, 0, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(catogory2, 250000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(catogory3, 250000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(category4, 0, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(catogory5, 0, DateHelper.formatDateWithQuarters(date)));
		date.add(Calendar.MONTH, 3);
		graphList.add(new StackedBarChartItem(catogory1, 0, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(catogory2, 250000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(catogory3, 250000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(category4, 250000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(catogory5, 0, DateHelper.formatDateWithQuarters(date)));
		date.add(Calendar.MONTH, 3);
		graphList.add(new StackedBarChartItem(catogory1, 0, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(catogory2, 250000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(catogory3, 250000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(category4, 250000, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(catogory5, 250000, DateHelper.formatDateWithQuarters(date)));

		ChartStyle chartStyle = new ChartStyle();
		chartStyle.setBackgroundColor(GRAY_BG_COLOR);
		chartStyle.setPlotBackgroundColor(GRAY_BG_COLOR);
		chartStyle.setBackgroundImagePath(bgChartPath);
		ChartSettings chartSettings = new ChartSettings(CHART_WIDTH, CHART_HEIGHT, chartStyle);
		chartSettings.setChartTitle("������� 9. ������� ���������� ��������");
		chartSettings.setShowLegend(false);

		String[] yAxisValues = new String[]{"", "10%", "10%", "10%", "10%", "", ""};

		grayBg.addImageToCurrentRow(chartBiulder.generate3DStakedBarChart(graphList, chartSettings, 5, yAxisValues), PDF_CHART_WIDTH, PDF_CHART_HEIGHT, CELL_STYLE_CENTER);
		grayBg.addValueToCell("", PDFBuilder.TEXT_FONT, CELL_STYLE);

		grayBg.addToPage(TableBreakRule.twoLineMinimumInPage);
	}

	private void addMethodCalc() throws PDFBuilderException
	{
		builder.addParagraph("�������� �������", PDFBuilder.SUB_TITLE_PARAGRAPH, PDFBuilder.SUB_TITLE_FONT);
		builder.addParagraph("��� ������� ������� ��������� ���������� ��������� ����������� ������� ��������� ��������� ���������� ������� � ��������������� ���������.", PDFBuilder.BASE_PARAGRAPH);
		builder.addParagraph("������� ��������� �������� ���������� ������� �������������� �� �������:", PDFBuilder.BASE_PARAGRAPH);

		CellStyle cellStyle = new CellStyle(PDFTableStyles.CELL_WITHOUT_BORDER);
		cellStyle.setHorizontalAlignment(Alignment.center);

		CellStyle cellStyleRight = new CellStyle(PDFTableStyles.CELL_WITHOUT_BORDER);
		cellStyleRight.setHorizontalAlignment(Alignment.right);

		//���� ������� (�������� � �������)
		PDFTableBuilder formulaParagpaph = builder.getTableBuilderInstance(1, resoursePath + PDFBuilder.FONT_NAME, new ParagraphStyle(12f, 0f, 4f, 0f));
		formulaParagpaph.setWidths(360, new int[] {100});

		java.util.List<Chunk> formula = new java.util.ArrayList<Chunk>();
		formula.add(builder.textString("��",PDFTableStyles.BOLD_FONT));
		formula.add(builder.subString("��.�.", PDFBuilder.SUB_SUP_BOLD_FONT));
		formula.add(builder.textString(" = ",PDFTableStyles.BOLD_FONT));
		formula.add(builder.textString("�*(1+R", PDFTableStyles.BOLD_FONT));
		formula.add(builder.subString("��.", PDFBuilder.SUB_SUP_BOLD_FONT));
		formula.add(builder.textString(")", PDFTableStyles.BOLD_FONT));
		formula.add(builder.supString("N", PDFBuilder.SUB_SUP_BOLD_FONT));
		formulaParagpaph.addElementsToCell(formula, 1, 1, cellStyle);
		formulaParagpaph.addToPage(TableBreakRule.wholeTableInPage);

		builder.addParagraph("���:");
		builder.addTextString("��", PDFTableStyles.BOLD_FONT);
		builder.addSubString("��.�.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString(" � ������� ��������� �������� ���������� �������;");

		builder.addEmptyParagraph();
		builder.addTextString("�", PDFTableStyles.BOLD_FONT);
		builder.addTextString(" � ������ ��������� ��������������� �������� ���������� �������;");

		builder.addEmptyParagraph();
		builder.addTextString("R", PDFTableStyles.BOLD_FONT);
		builder.addSubString("��.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString(" � ������� ���������� �������� �� �������. ������������ ��� ������� ������� ���������� ��������, ������� �� 4. (R");
		builder.addSubString("��.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString(" = R");
		builder.addSubString("�.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString("/4);");

		builder.addEmptyParagraph();
		builder.addTextString("N", PDFTableStyles.BOLD_FONT);
		builder.addTextString(" � ���-�� ���������.");

		addExample3();

		builder.addParagraph("������� ��������� �������� ��������������� ��������� ������������ �� �������:", PDFBuilder.BASE_PARAGRAPH);

		CellStyle numeratorStyle = new CellStyle();
		numeratorStyle.setBorderWidth(0);
		numeratorStyle.setBorderWidthBottom(0.4f);
		numeratorStyle.setBorderColor(Color.GRAY);

		PDFTableBuilder fraction = builder.getTableBuilderInstance(2, resoursePath + PDFBuilder.FONT_NAME, new ParagraphStyle(12f, 0f, 4f, 0f));
		fraction.setWidths(PDFTableBuilder.TABLE_WIDTH_HORIZ_LIST, new int[]{88, 150});

		java.util.List<Chunk> leftPart = new java.util.ArrayList<Chunk>();
		leftPart.add(builder.textString("��",PDFTableStyles.BOLD_FONT));
		leftPart.add(builder.subString("��.��.", PDFBuilder.SUB_SUP_BOLD_FONT));
		leftPart.add(builder.textString(" = ��+",PDFTableStyles.BOLD_FONT));

		java.util.List<Chunk> numerator = new java.util.ArrayList<Chunk>();
		numerator.add(builder.textString("��*(1+Z",PDFTableStyles.BOLD_FONT));
		numerator.add(builder.subString("��.", PDFBuilder.SUB_SUP_BOLD_FONT));
		numerator.add(builder.textString(")*(1-(1+Z",PDFTableStyles.BOLD_FONT));
		numerator.add(builder.subString("��.", PDFBuilder.SUB_SUP_BOLD_FONT));
		numerator.add(builder.textString(")",PDFTableStyles.BOLD_FONT));
		numerator.add(builder.supString("N-1", PDFBuilder.SUB_SUP_BOLD_FONT));
		numerator.add(builder.textString(")",PDFTableStyles.BOLD_FONT));

		java.util.List<Chunk> denomerator = new java.util.ArrayList<Chunk>();
		denomerator.add(builder.textString("1-(1+Z",PDFTableStyles.BOLD_FONT));
		denomerator.add(builder.subString("��.", PDFBuilder.SUB_SUP_BOLD_FONT));
		denomerator.add(builder.textString(")",PDFTableStyles.BOLD_FONT));

		fraction.addElementsToCell(leftPart, 1, 2, cellStyleRight);
		fraction.addElementsToCell(numerator, 1, 1, numeratorStyle);
		fraction.addElementsToCell(denomerator, 1, 1, cellStyle);

		fraction.addToPage(TableBreakRule.wholeTableInPage);

		builder.addParagraph("���:");
		builder.addTextString("��", PDFTableStyles.BOLD_FONT);
		builder.addSubString("��.��.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString(" � ������� ��������� �������� ��������������� ���������;");

		builder.addEmptyParagraph();
		builder.addTextString("��", PDFTableStyles.BOLD_FONT);
		builder.addTextString(" � ������ ��������� ��������������� �������� ��������������� ���������;");

		builder.addEmptyParagraph();
		builder.addTextString("Z", PDFTableStyles.BOLD_FONT);
		builder.addSubString("��.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString(" � ������� ���������� �������� ��������������� ��������� �� �������. ������������ ��� ������� ������� ���������� ��������, ������� �� 4. (Z");
		builder.addSubString("��.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString(" = Z");
		builder.addSubString("�.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString("/4);");

		builder.addEmptyParagraph();
		builder.addTextString("N", PDFTableStyles.BOLD_FONT);
		builder.addTextString(" � ���-�� ���������, �� ������� ������������ �������.");

		addExample4();

		builder.addParagraph("������� ��������� ���������� ��������� ������������ �� �������:", PDFBuilder.BASE_PARAGRAPH);
		PDFTableBuilder tableFormula3 = builder.getTableBuilderInstance(1, resoursePath + PDFBuilder.FONT_NAME, new ParagraphStyle(12f, 0f, 4f, 0f));
		formulaParagpaph.setWidths(360, new int[] {100});

		java.util.List<Chunk> formula3 = new java.util.ArrayList<Chunk>();
		formula3.add(builder.textString("��",PDFTableStyles.BOLD_FONT));
		formula3.add(builder.subString("����", PDFBuilder.SUB_SUP_BOLD_FONT));
		formula3.add(builder.textString(" = ",PDFTableStyles.BOLD_FONT));
		formula3.add(builder.textString("��",PDFTableStyles.BOLD_FONT));
		formula3.add(builder.subString("��.�.", PDFBuilder.SUB_SUP_BOLD_FONT));
		formula3.add(builder.textString("+��",PDFTableStyles.BOLD_FONT));
		formula3.add(builder.subString("��.��.", PDFBuilder.SUB_SUP_BOLD_FONT));
		tableFormula3.addElementsToCell(formula3, 1, 1, cellStyle);
		tableFormula3.addToPage(TableBreakRule.wholeTableInPage);

		builder.addParagraph("���:");
		builder.addTextString("��", PDFTableStyles.BOLD_FONT);
		builder.addSubString("��.�.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString(" � ������� ��������� �������� ���������� ������� � N-�� ��������;");

		builder.addEmptyParagraph();
		builder.addTextString("��", PDFTableStyles.BOLD_FONT);
		builder.addSubString("��.��.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString(" � ������� ��������� �������� ��������������� ��������� � N-�� ��������.");

		addExample5();

		PDFTableBuilder tableImportant = builder.getTableBuilderInstance(2, resoursePath + PDFBuilder.FONT_NAME, new ParagraphStyle(12f, 0f, 4f, 0f));
		tableImportant.setWidths(360, new int[] {35, 325});
		CellStyle importantTableCellStyle = new CellStyle();
		cellStyle.setBorderWidth(0);
		tableImportant.addImageToCurrentRow(resoursePath + "important.png", 31, 35, importantTableCellStyle);
		tableImportant.addValueToCell("�������� ���� ��������, ��� ������� �������� ������������, " +
				"���� ��������� ����������� ����������� ��������� ����� ���������. " +
				"������� ����������� ��� �� ��������� ������ �������������� ��������� ����� ���������" +
				" � ����������� �� �������� ��������� ���� ���������.", PDFBuilder.TEXT_FONT, importantTableCellStyle);

		tableImportant.addToPage(TableBreakRule.wholeTableInPage);

		addExample6();
	}

	private void addProductTerm() throws PDFBuilderException
	{
		builder.addParagraph("��������� ���������", PDFBuilder.SUB_TITLE_PARAGRAPH, PDFBuilder.SUB_TITLE_FONT);
		builder.addParagraph("� �������� �� ����������� ��������� ���������. ��� �������� ������������ ���������, ��� ������� ����� ������������� ������������/������������� �� ����� ���� � ������ �� ���������.", PDFBuilder.BASE_PARAGRAPH);
		builder.addParagraph("��������, ���� �� ���������� ����� �� 3 ���� � � ������ ������������ ������ 6% �������, �� � ������� �� 10 ��� ����� �����������, " +
				"��� ��� ����� ��������� �������������� ��� ����� �� ������, ������� �� ����� ���������� (6% ������� ��� ������ �������).", PDFBuilder.BASE_PARAGRAPH);

		Calendar date = DateHelper.getCurrentDate();

		java.util.List<StackedBarChartItem> graphList = new ArrayList<StackedBarChartItem>();
		Category category1 = new Category("����� ������", new Color(190,10,73), "{0}");
		Category category2 = new Category("�����", new Color(255, 134, 7), "{0}");
		graphList.add(new StackedBarChartItem(category1, 100, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(category2, 0, DateHelper.formatDateWithQuarters(date)));
		date.add(Calendar.YEAR, 3);
		graphList.add(new StackedBarChartItem(category1, 100, DateHelper.formatDateWithQuarters(date)));
		graphList.add(new StackedBarChartItem(category2, 50, DateHelper.formatDateWithQuarters(date)));

		ChartStyle chartStyle = new ChartStyle();
		chartStyle.setBackgroundColor(GRAY_BG_COLOR);
		chartStyle.setPlotBackgroundColor(GRAY_BG_COLOR);
		chartStyle.setBackgroundImagePath(bgChartPath);
		ChartSettings chartSettings = new ChartSettings(CHART_WIDTH, CHART_HEIGHT, chartStyle);
		chartSettings.setChartTitle("������� 8. ��������������/�����������");
		chartSettings.setShowLegend(false);
		chartSettings.setShowAxisY(false);

		builder.addImage(chartBiulder.generate3DStakedBarChart(graphList, chartSettings, 2), PDF_CHART_WIDTH, PDF_CHART_HEIGHT, Alignment.center);
	}

	private void addInsuranceCalc() throws PDFBuilderException
	{
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);
		builder.addParagraph("������ �� ��������� ���������", PDFBuilder.SUB_TITLE_PARAGRAPH, PDFBuilder.SUB_TITLE_FONT);
		builder.addParagraph("���� �� ���������� � �������� ��������� �������, �� ������ �������� ��������� �������.", PDFBuilder.BASE_PARAGRAPH);
		builder.addParagraph("����� ��������� �������, ��������� ��� ���������� �������� � �������� ���������� �������, ��������� � ������� ���������� ���������� ��������.", PDFBuilder.BASE_PARAGRAPH);
		builder.addParagraph("� ������, ���� �� ������������� ������������� ��������� ������, �� ������������ ������ �����, ������� ��� ��������� ����������� �� " +
				"�������������� ������ ��� ������ ��������� �������. ��� ����� ����������� � �������� ��������������� ���������. ��� ���� �����������, ��� ������������� " +
				"�� ��� ����� ����������� ��������, ������ �� ��������� ���� ���������� �� ���������� ��������.", PDFBuilder.BASE_PARAGRAPH);

		addExample7();
	}

	private void writeToFile(byte[] data, String fileName) throws IOException
	{
		FileOutputStream fileOutputStream = null;
		try
		{
			fileOutputStream = new FileOutputStream(fileName);
			fileOutputStream.write(data);
		}
		finally
		{
			if (fileOutputStream != null)
				fileOutputStream.close();
		}
	}
}
