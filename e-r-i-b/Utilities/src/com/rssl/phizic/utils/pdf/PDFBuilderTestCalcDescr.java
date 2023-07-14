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

		builder.addParagraph("Как рассчитывается будущая стоимость портфеля?", PDFBuilder.TITLE_PARAGRAPH, PDFBuilder.TITLE_FONT);
		builder.addParagraph("Все расчёты и прогнозы являются приблизительными и основаны на определённых допущениях. В расчётах не учитывается капитализация " +
				"процентов по вкладам. Для простоты и единообразия расчётов мы используем квартальную доходность продуктов, которая определяется по формуле:", PDFBuilder.BASE_PARAGRAPH);
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

		int[] widths = {25, 10}; // ширина столбцов таблицы
		fraction.setWidths(360, widths);

		java.util.List<Chunk> leftPart = new java.util.ArrayList<Chunk>();
		leftPart.add(builder.textString("I",PDFTableStyles.BOLD_FONT));
		leftPart.add(builder.subString("кв.", PDFBuilder.SUB_SUP_BOLD_FONT));
		leftPart.add(builder.textString(" = ",PDFTableStyles.BOLD_FONT));

		java.util.List<Chunk> numerator = new java.util.ArrayList<Chunk>();
		numerator.add(builder.textString("I",PDFTableStyles.BOLD_FONT));
		numerator.add(builder.subString("г.", PDFBuilder.SUB_SUP_BOLD_FONT));

		fraction.addElementsToCell(leftPart, 1, 2, PDFTableStyles.CELL_WITHOUT_BORDER);
		fraction.addElementsToCell(numerator, 1, 1, numeratorStyle);
		fraction.addValueToCell("4", PDFTableStyles.BOLD_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);

		fraction.addToPage(TableBreakRule.wholeTableInPage);

		builder.addParagraph("Где:");
		builder.addTextString("I", PDFTableStyles.BOLD_FONT);
		builder.addSubString("г.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString(" – годовая доходность продукта;");

		builder.addEmptyParagraph();
		builder.addTextString("I", PDFTableStyles.BOLD_FONT);
		builder.addSubString("кв.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString(" – квартальная доходность продукта.");

		builder.addParagraph("В связи с этим результаты расчёта стоимости портфеля могут отличаться от реальных значений.", PDFBuilder.BASE_PARAGRAPH);
	}

	private void addExample1() throws PDFBuilderException
	{
		PDFTableBuilder grayBg = builder.getTableBuilderInstance(1, resoursePath + PDFBuilder.FONT_NAME, PDFBuilder.BASE_PARAGRAPH);
		grayBg.setTableWidthPercentage(100);

		List<Chunk> paragraphs = new ArrayList<Chunk>();
		grayBg.addValueToCell("Пример 1", EXAMPLE_TITLE_FONT, CELL_STYLE_CENTER);
		grayBg.addValueToCell("Вы открываете вклад со следующими параметрами:", PDFBuilder.TEXT_FONT, CELL_STYLE);

		paragraphs.add(builder.textString("- сумма вклада: 100 000 рублей;", PDFBuilder.TEXT_FONT));
		grayBg.addElementsToCell(paragraphs, 1, 1, CELL_STYLE);

		paragraphs.clear();
		paragraphs.add(builder.textString("- срок вклада: 1 год (4 квартала);",PDFBuilder.TEXT_FONT));
		grayBg.addElementsToCell(paragraphs, 1, 1, CELL_STYLE);

		paragraphs.clear();
		paragraphs.add(builder.textString("- ставка по вкладу: 10% годовых.",PDFBuilder.TEXT_FONT));
		grayBg.addElementsToCell(paragraphs, 1, 1, CELL_STYLE);

		grayBg.addValueToCell("При этом по вкладу предусмотрена ежемесячная капитализация процентов.", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("Сравним точный расчёт с прогнозом по расчетам программы.", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("", PDFBuilder.TEXT_FONT, CELL_STYLE);

		java.util.List<BarChartItem> productList = new ArrayList<BarChartItem>();
		double incomeMoney = 100000;
		double outcomeMoney = 110381;
		double freeMoney = 110471;

		productList.add(new BarChartItem(incomeMoney, "", "Сумма вклада", barColor.get("account")));
		productList.add(new BarChartItem(outcomeMoney, "", "Прогноз", barColor.get("prognostic")));
		productList.add(new BarChartItem(freeMoney, "", "Точный расчет", barColor.get("accurate")));

		ChartStyle chartStyle = new ChartStyle();
		chartStyle.setBackgroundColor(GRAY_BG_COLOR);
		chartStyle.setPlotBackgroundColor(GRAY_BG_COLOR);
		ChartSettings chartSettings = new ChartSettings(CHART_WIDTH, CHART_HEIGHT, chartStyle);
		chartSettings.setChartTitle("Рисунок 1. Сравнение расчетов доходности");
		chartSettings.setTitleAxisY("Сумма, руб.");

		grayBg.addImageToCurrentRow(chartBiulder.generate3DBarChart(productList, chartSettings, 3), PDF_CHART_WIDTH, PDF_CHART_HEIGHT, 1, 1, CELL_STYLE_CENTER);
		
		grayBg.addToPage(TableBreakRule.twoLineMinimumInPage);
	}

	private void addExample2() throws PDFBuilderException
	{
		PDFTableBuilder grayBg = builder.getTableBuilderInstance(1, resoursePath + PDFBuilder.FONT_NAME, PDFBuilder.BASE_PARAGRAPH);
		grayBg.setTableWidthPercentage(100);

		grayBg.addValueToCell("Пример 2", EXAMPLE_TITLE_FONT, CELL_STYLE_CENTER);
		grayBg.addValueToCell("У Вас в портфеле на 100 000 рублей куплено золото (Обезличенный металлический счёт).", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("Предположим, Вы ожидаете доходность по этому продукту 30% годовых (7,5% в квартал).", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("Так как расчёт производится на квартальной основе, то результаты расчёта будут отличаться от реальных значений.", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("Рассмотрим разницу в расчётах для временного периода 1 год (4 квартала).", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("При этом по вкладу предусмотрена ежемесячная капитализация процентов.", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("Сравним расчёты по калькулятору на сайте банка (точный расчёт) с прогнозом будущей стоимости портфеля (прогноз).", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("", PDFBuilder.TEXT_FONT, CELL_STYLE);

		java.util.List<BarChartItem> productList = new ArrayList<BarChartItem>();
		double incomeMoney = 100000;
		double outcomeMoney = 133547;
		double freeMoney = 130000;

		productList.add(new BarChartItem(incomeMoney, "", "Золото", barColor.get("ima")));
		productList.add(new BarChartItem(outcomeMoney, "", "Прогноз", barColor.get("prognostic")));
		productList.add(new BarChartItem(freeMoney, "", "Точный расчет", barColor.get("accurate")));

		ChartStyle chartStyle = new ChartStyle();
		chartStyle.setBackgroundColor(GRAY_BG_COLOR);
		chartStyle.setPlotBackgroundColor(GRAY_BG_COLOR);
		ChartSettings chartSettings = new ChartSettings(CHART_WIDTH, CHART_HEIGHT, chartStyle);
		chartSettings.setChartTitle("Рисунок 2. Сравнение расчетов доходности");
		chartSettings.setTitleAxisY("Сумма, руб.");

		grayBg.addImageToCurrentRow(chartBiulder.generate3DBarChart(productList, chartSettings, 3), PDF_CHART_WIDTH, PDF_CHART_HEIGHT, 1, 1, CELL_STYLE_CENTER);
		grayBg.addValueToCell("", PDFBuilder.TEXT_FONT, CELL_STYLE);

		grayBg.addToPage(TableBreakRule.twoLineMinimumInPage);
	}

	private void addExample3() throws PDFBuilderException
	{
		PDFTableBuilder grayBg = builder.getTableBuilderInstance(1, resoursePath + PDFBuilder.FONT_NAME, PDFBuilder.BASE_PARAGRAPH);
		grayBg.setTableWidthPercentage(100);

		grayBg.addValueToCell("Пример 3", EXAMPLE_TITLE_FONT, CELL_STYLE_CENTER);
		grayBg.addValueToCell("Вы распределили средства следующим образом:", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("- стоимость портфеля «Стартовый капитал» = 1 000 000 рублей; ", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("- доходность портфеля «Стартовый капитал» = 20% годовых. (Квартальная доходность 5,0%).", PDFBuilder.TEXT_FONT, CELL_STYLE);

		Calendar date = DateHelper.getCurrentDate();
		grayBg.addValueToCell("Сейчас " + DateHelper.formatDateWithQuarters(date) + " года.", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("Определим будущую стоимость портфеля в течение следующих 6 кварталов.", PDFBuilder.TEXT_FONT, CELL_STYLE);

		java.util.List<StackedBarChartItem> graphList = new ArrayList<StackedBarChartItem>();
		Category category1 = new Category("Внесенная сумма", new Color(43, 205, 255), Color.black, "Внесенная сумма", new ImageLegendItem("Внесенная сумма", resoursePath + "light_blue.png"));
		Category category2 = new Category("Доход", new Color(255, 134, 7), Color.black, "Доход", new ImageLegendItem("Доход", resoursePath + "orange.png"));
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
		chartSettings.setChartTitle("Рисунок 3. Динамика портфеля «Стартовый капитал»");
		chartSettings.setTitleAxisX("");
		chartSettings.setTitleAxisY("Средства, руб.");

		grayBg.addImageToCurrentRow(chartBiulder.generate3DStakedBarChartWithSeveralDomainAxis(graphList, chartSettings, 2), PDF_CHART_WIDTH, PDF_CHART_HEIGHT, CELL_STYLE_CENTER);
		grayBg.addValueToCell("", PDFBuilder.TEXT_FONT, CELL_STYLE);

		grayBg.addToPage(TableBreakRule.twoLineMinimumInPage);
	}

	private void addExample4() throws PDFBuilderException
	{
		PDFTableBuilder grayBg = builder.getTableBuilderInstance(1, resoursePath + PDFBuilder.FONT_NAME, PDFBuilder.BASE_PARAGRAPH);
		grayBg.setTableWidthPercentage(100);

		grayBg.addValueToCell("Пример 4", EXAMPLE_TITLE_FONT, CELL_STYLE_CENTER);
		Calendar dateNext = DateHelper.getCurrentDate();

		grayBg.addValueToCell("Ежеквартально Вы готовы вкладывать по 245 000 рублей.", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("Доходность портфеля «Ежеквартальные вложения» = 17,0% годовых. (Квартальная доходность 4,3%)", PDFBuilder.TEXT_FONT, CELL_STYLE);
		Calendar startDate = (Calendar) dateNext.clone();
		startDate.add(Calendar.MONTH, 3);
		grayBg.addValueToCell("Сейчас "+ DateHelper.formatDateWithQuarters(DateHelper.getCurrentDate()) +" года. " +
				"Взносы начинаются с " + DateHelper.getQuarter(startDate) + " квартала " + startDate.get(Calendar.YEAR) + " года.",
				PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("Определим будущую стоимость портфеля в течение следующих 6 кварталов:", PDFBuilder.TEXT_FONT, CELL_STYLE);

		java.util.List<StackedBarChartItem> graphList = new ArrayList<StackedBarChartItem>();
		Category category1 = new Category("Внесенная сумма", new Color(124, 180, 24), Color.black, "Внесенная сумма", new ImageLegendItem("Внесенная сумма", resoursePath + "light_green.png"));
		Category category2 = new Category("Прибыль", new Color(255, 134, 7), Color.black, "Доход", new ImageLegendItem("Доход", resoursePath + "orange.png"));

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
		chartSettings.setChartTitle("Рисунок 4. Динамика портфеля «Ежеквартальные вложения»");
		chartSettings.setTitleAxisX("");
		chartSettings.setTitleAxisY("Средства, руб.");

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

		grayBg.addValueToCell("Пример 5", EXAMPLE_TITLE_FONT, CELL_STYLE_CENTER);
		grayBg.addValueToCell("Для описанных выше примеров 3 и 4 определим будущую стоимость «Итогового портфеля»:", PDFBuilder.TEXT_FONT, CELL_STYLE);

		Calendar date = DateHelper.getCurrentDate();
		java.util.List<StackedBarChartItem> graphList = new ArrayList<StackedBarChartItem>();
		Category category1 = new Category("Внесенная сумма", new Color(170, 62, 172), Color.black, "Внесенная сумма", new ImageLegendItem("Внесенная сумма", resoursePath + "violet.png"));
		Category category2 = new Category("Доход", new Color(255, 134, 7), Color.black, "Доход", new ImageLegendItem("Доход", resoursePath + "orange.png"));

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
		chartSettings.setChartTitle("Рисунок 5. Динамика итогового портфеля");
		chartSettings.setTitleAxisX("");
		chartSettings.setTitleAxisY("Средства, руб.");

		grayBg.addImageToCurrentRow(chartBiulder.generate3DStakedBarChartWithSeveralDomainAxis(graphList, chartSettings, 2), PDF_CHART_WIDTH, PDF_CHART_HEIGHT, CELL_STYLE_CENTER);
		grayBg.addValueToCell("", PDFBuilder.TEXT_FONT, CELL_STYLE);

		grayBg.addToPage(TableBreakRule.twoLineMinimumInPage);
	}

	private void addExample6() throws PDFBuilderException
	{
		PDFTableBuilder grayBg = builder.getTableBuilderInstance(1, resoursePath + PDFBuilder.FONT_NAME, PDFBuilder.BASE_PARAGRAPH);
		grayBg.setTableWidthPercentage(100);

		grayBg.addValueToCell("Пример 6", EXAMPLE_TITLE_FONT, CELL_STYLE_CENTER);
		grayBg.addValueToCell("Рассмотрим случай, при котором в сформированном Вами портфеле со временем меняется соотношение продуктов. " +
				"Это происходит из-за того, что каждому продукту соответствует разная доходность.", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("Ваш портфель состоит из 3-х продуктов:", PDFBuilder.TEXT_FONT, CELL_STYLE);

		// Внутренняя таблица
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(3, resoursePath + PDFBuilder.FONT_NAME, PDFBuilder.BASE_PARAGRAPH);
		tableBuilder.setTableWidthPercentage(100);

		CellStyle cellStyle = new CellStyle();
		cellStyle.setBorderWidth(0);
		cellStyle.setBackgroundColor(GRAY_BG_COLOR);
		cellStyle.setBorderWidthBottom(0.2f); // устанавлтваем только нижнюю границу
		cellStyle.setBorderColor(Color.GRAY); // цвет нижней границы

		tableBuilder.addValueToCell("Продукт", PDFTableStyles.TEXT_FONT, PDFTableStyles.TH_CELL_STYLE);
		tableBuilder.addValueToCell("Доходность", PDFTableStyles.TEXT_FONT, PDFTableStyles.TH_CELL_STYLE);
		tableBuilder.addValueToCell("Сумма", PDFTableStyles.TEXT_FONT, PDFTableStyles.TH_CELL_STYLE);

		tableBuilder.addValueToCell("Вклад «Управляй»", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("6,1%", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("200 000,00 руб.", PDFTableStyles.TEXT_FONT, cellStyle);

		tableBuilder.addValueToCell("ПИФ «Акций»", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("22,3%", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("500 000,00 руб.", PDFTableStyles.TEXT_FONT, cellStyle);

		tableBuilder.addValueToCell("ОМС «Золото»", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("18,5%", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("300 000,00 руб.", PDFTableStyles.TEXT_FONT, cellStyle);
		// end Внутренняя таблица

		grayBg.addTableToCell(tableBuilder, CELL_STYLE);

		grayBg.addValueToCell("Далее представим на диаграмме текущее соотношение средств в Вашем портфеле:", PDFBuilder.TEXT_FONT, CELL_STYLE);

		java.util.List<PieChartItem> productList = new ArrayList<PieChartItem>();
		productList.add(new PieChartItem("Вклады", 20, productColor.get("account")));
		productList.add(new PieChartItem("ОМС", 30, productColor.get("IMA")));
		productList.add(new PieChartItem("ПИФы", 50, productColor.get("fund")));

		ChartStyle chartStyle = new ChartStyle();
		chartStyle.setBackgroundColor(GRAY_BG_COLOR);
		chartStyle.setPlotBackgroundColor(GRAY_BG_COLOR);
		ChartSettings chartSettings = new ChartSettings(CHART_WIDTH, PIE_CHART_HEIGHT, chartStyle);
		chartSettings.setChartTitle("Рисунок 6. Начальное соотношение продуктов в портфеле");

		grayBg.addImageToCurrentRow(chartBiulder.generate3DPieChart(productList, chartSettings), PDF_CHART_WIDTH, PDF_PIE_CHART_HEIGHT, CELL_STYLE_CENTER);

		grayBg.addValueToCell("С учётом указанных доходностей через год Вы получите:", PDFBuilder.TEXT_FONT, CELL_STYLE);

		// Внутренняя таблица
		tableBuilder = builder.getTableBuilderInstance(3, resoursePath + PDFBuilder.FONT_NAME, PDFBuilder.BASE_PARAGRAPH);
		tableBuilder.setTableWidthPercentage(100);

		tableBuilder.addValueToCell("Продукт", PDFTableStyles.TEXT_FONT, PDFTableStyles.TH_CELL_STYLE);
		tableBuilder.addValueToCell("Доходность", PDFTableStyles.TEXT_FONT, PDFTableStyles.TH_CELL_STYLE);
		tableBuilder.addValueToCell("Сумма через год", PDFTableStyles.TEXT_FONT, PDFTableStyles.TH_CELL_STYLE);

		tableBuilder.addValueToCell("Вклад «Управляй»", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("6,1%", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("212 481,90 руб.", PDFTableStyles.TEXT_FONT, cellStyle);

		tableBuilder.addValueToCell("ПИФ «Акций»", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("22,3%", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("621 175,60 руб.", PDFTableStyles.TEXT_FONT, cellStyle);

		tableBuilder.addValueToCell("ОМС «Золото»", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("18,5%", PDFTableStyles.TEXT_FONT, cellStyle);
		tableBuilder.addValueToCell("359 470,40 руб.", PDFTableStyles.TEXT_FONT, cellStyle);
		// end Внутренняя таблица

		grayBg.addTableToCell(tableBuilder, CELL_STYLE);

		grayBg.addValueToCell("В результате соотношение продуктов в портфеле будет следующим:", PDFBuilder.TEXT_FONT, CELL_STYLE);

		productList.clear();
		productList.add(new PieChartItem("Вклады", 18, productColor.get("account")));
		productList.add(new PieChartItem("ОМС", 30, productColor.get("IMA")));
		productList.add(new PieChartItem("ПИФы", 52, productColor.get("fund")));

		chartSettings.setChartTitle("Рисунок 7. Соотношение продуктов в портфеле через год");
		grayBg.addImageToCurrentRow(chartBiulder.generate3DPieChart(productList, chartSettings), PDF_CHART_WIDTH, PDF_PIE_CHART_HEIGHT, CELL_STYLE_CENTER);

		grayBg.addValueToCell("В связи с тем, что доходность у разных продуктов отличается, структура портфеля начинает меняться.", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("В данном примере для выравнивания исходной структуры портфеля нужно большее количество средств разместить на вкладе, " +
				"а меньшее – инвестировать в ПИФ, чтобы восстановить первоначальное распределение портфеля.", PDFBuilder.TEXT_FONT, CELL_STYLE);

		grayBg.addToPage(TableBreakRule.twoLineMinimumInPage);
	}

	private void addExample7() throws PDFBuilderException
	{
		PDFTableBuilder grayBg = builder.getTableBuilderInstance(1, resoursePath + PDFBuilder.FONT_NAME, PDFBuilder.BASE_PARAGRAPH);
		grayBg.setTableWidthPercentage(100);

		grayBg.addValueToCell("Пример 7", EXAMPLE_TITLE_FONT, CELL_STYLE_CENTER);
		grayBg.addValueToCell("Вы оформляете программу накопительного страхования жизни с ежегодным взносом    1 000 000 рублей.", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("Доходность по продукту 10% годовых (2,5% за квартал).", PDFBuilder.TEXT_FONT, CELL_STYLE);
		grayBg.addValueToCell("При расчёте будет учтено, что 1 000 000 рублей Вы уже разместили под 10% годовых и каждый квартал будете ещё " +
				"дополнительно вкладывать по 250 000 рублей (1 000 000/4) под такую же доходность (10% годовых), а через год оплатите страховой " +
				"взнос на 1 000 000 рублей (сумма, накопленная за 4 квартала).", PDFBuilder.TEXT_FONT, CELL_STYLE);

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
		chartSettings.setChartTitle("Рисунок 9. Годовая доходность продукта");
		chartSettings.setShowLegend(false);

		String[] yAxisValues = new String[]{"", "10%", "10%", "10%", "10%", "", ""};

		grayBg.addImageToCurrentRow(chartBiulder.generate3DStakedBarChart(graphList, chartSettings, 5, yAxisValues), PDF_CHART_WIDTH, PDF_CHART_HEIGHT, CELL_STYLE_CENTER);
		grayBg.addValueToCell("", PDFBuilder.TEXT_FONT, CELL_STYLE);

		grayBg.addToPage(TableBreakRule.twoLineMinimumInPage);
	}

	private void addMethodCalc() throws PDFBuilderException
	{
		builder.addParagraph("Методика расчёта", PDFBuilder.SUB_TITLE_PARAGRAPH, PDFBuilder.SUB_TITLE_FONT);
		builder.addParagraph("Для расчёта будущей стоимости «Итогового портфеля» суммируется будущая стоимость портфелей «Стартовый капитал» и «Ежеквартальные вложения».", PDFBuilder.BASE_PARAGRAPH);
		builder.addParagraph("Будущая стоимость портфеля «Стартовый капитал» рассчитывается по формуле:", PDFBuilder.BASE_PARAGRAPH);

		CellStyle cellStyle = new CellStyle(PDFTableStyles.CELL_WITHOUT_BORDER);
		cellStyle.setHorizontalAlignment(Alignment.center);

		CellStyle cellStyleRight = new CellStyle(PDFTableStyles.CELL_WITHOUT_BORDER);
		cellStyleRight.setHorizontalAlignment(Alignment.right);

		//сама формула (обернута в таблицу)
		PDFTableBuilder formulaParagpaph = builder.getTableBuilderInstance(1, resoursePath + PDFBuilder.FONT_NAME, new ParagraphStyle(12f, 0f, 4f, 0f));
		formulaParagpaph.setWidths(360, new int[] {100});

		java.util.List<Chunk> formula = new java.util.ArrayList<Chunk>();
		formula.add(builder.textString("БС",PDFTableStyles.BOLD_FONT));
		formula.add(builder.subString("ст.к.", PDFBuilder.SUB_SUP_BOLD_FONT));
		formula.add(builder.textString(" = ",PDFTableStyles.BOLD_FONT));
		formula.add(builder.textString("П*(1+R", PDFTableStyles.BOLD_FONT));
		formula.add(builder.subString("кв.", PDFBuilder.SUB_SUP_BOLD_FONT));
		formula.add(builder.textString(")", PDFTableStyles.BOLD_FONT));
		formula.add(builder.supString("N", PDFBuilder.SUB_SUP_BOLD_FONT));
		formulaParagpaph.addElementsToCell(formula, 1, 1, cellStyle);
		formulaParagpaph.addToPage(TableBreakRule.wholeTableInPage);

		builder.addParagraph("Где:");
		builder.addTextString("БС", PDFTableStyles.BOLD_FONT);
		builder.addSubString("ст.к.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString(" – будущая стоимость портфеля «Стартовый капитал»;");

		builder.addEmptyParagraph();
		builder.addTextString("П", PDFTableStyles.BOLD_FONT);
		builder.addTextString(" – полная стоимость сформированного портфеля «Стартовый капитал»;");

		builder.addEmptyParagraph();
		builder.addTextString("R", PDFTableStyles.BOLD_FONT);
		builder.addSubString("кв.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString(" – средняя доходность портфеля за квартал. Определяется как средняя годовая доходность портфеля, делённая на 4. (R");
		builder.addSubString("кв.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString(" = R");
		builder.addSubString("г.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString("/4);");

		builder.addEmptyParagraph();
		builder.addTextString("N", PDFTableStyles.BOLD_FONT);
		builder.addTextString(" – кол-во кварталов.");

		addExample3();

		builder.addParagraph("Будущая стоимость портфеля «Ежеквартальные вложения» определяется по формуле:", PDFBuilder.BASE_PARAGRAPH);

		CellStyle numeratorStyle = new CellStyle();
		numeratorStyle.setBorderWidth(0);
		numeratorStyle.setBorderWidthBottom(0.4f);
		numeratorStyle.setBorderColor(Color.GRAY);

		PDFTableBuilder fraction = builder.getTableBuilderInstance(2, resoursePath + PDFBuilder.FONT_NAME, new ParagraphStyle(12f, 0f, 4f, 0f));
		fraction.setWidths(PDFTableBuilder.TABLE_WIDTH_HORIZ_LIST, new int[]{88, 150});

		java.util.List<Chunk> leftPart = new java.util.ArrayList<Chunk>();
		leftPart.add(builder.textString("БС",PDFTableStyles.BOLD_FONT));
		leftPart.add(builder.subString("еж.вл.", PDFBuilder.SUB_SUP_BOLD_FONT));
		leftPart.add(builder.textString(" = ВЛ+",PDFTableStyles.BOLD_FONT));

		java.util.List<Chunk> numerator = new java.util.ArrayList<Chunk>();
		numerator.add(builder.textString("ВЛ*(1+Z",PDFTableStyles.BOLD_FONT));
		numerator.add(builder.subString("кв.", PDFBuilder.SUB_SUP_BOLD_FONT));
		numerator.add(builder.textString(")*(1-(1+Z",PDFTableStyles.BOLD_FONT));
		numerator.add(builder.subString("кв.", PDFBuilder.SUB_SUP_BOLD_FONT));
		numerator.add(builder.textString(")",PDFTableStyles.BOLD_FONT));
		numerator.add(builder.supString("N-1", PDFBuilder.SUB_SUP_BOLD_FONT));
		numerator.add(builder.textString(")",PDFTableStyles.BOLD_FONT));

		java.util.List<Chunk> denomerator = new java.util.ArrayList<Chunk>();
		denomerator.add(builder.textString("1-(1+Z",PDFTableStyles.BOLD_FONT));
		denomerator.add(builder.subString("кв.", PDFBuilder.SUB_SUP_BOLD_FONT));
		denomerator.add(builder.textString(")",PDFTableStyles.BOLD_FONT));

		fraction.addElementsToCell(leftPart, 1, 2, cellStyleRight);
		fraction.addElementsToCell(numerator, 1, 1, numeratorStyle);
		fraction.addElementsToCell(denomerator, 1, 1, cellStyle);

		fraction.addToPage(TableBreakRule.wholeTableInPage);

		builder.addParagraph("Где:");
		builder.addTextString("БС", PDFTableStyles.BOLD_FONT);
		builder.addSubString("еж.вл.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString(" – будущая стоимость портфеля «Ежеквартальные вложения»;");

		builder.addEmptyParagraph();
		builder.addTextString("ВЛ", PDFTableStyles.BOLD_FONT);
		builder.addTextString(" – полная стоимость сформированного портфеля «Ежеквартальные вложения»;");

		builder.addEmptyParagraph();
		builder.addTextString("Z", PDFTableStyles.BOLD_FONT);
		builder.addSubString("кв.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString(" – средняя доходность портфеля «Ежеквартальные вложения» за квартал. Определяется как средняя годовая доходность портфеля, делённая на 4. (Z");
		builder.addSubString("кв.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString(" = Z");
		builder.addSubString("г.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString("/4);");

		builder.addEmptyParagraph();
		builder.addTextString("N", PDFTableStyles.BOLD_FONT);
		builder.addTextString(" – кол-во кварталов, на которое производится прогноз.");

		addExample4();

		builder.addParagraph("Будущая стоимость «Итогового портфеля» определяется по формуле:", PDFBuilder.BASE_PARAGRAPH);
		PDFTableBuilder tableFormula3 = builder.getTableBuilderInstance(1, resoursePath + PDFBuilder.FONT_NAME, new ParagraphStyle(12f, 0f, 4f, 0f));
		formulaParagpaph.setWidths(360, new int[] {100});

		java.util.List<Chunk> formula3 = new java.util.ArrayList<Chunk>();
		formula3.add(builder.textString("БС",PDFTableStyles.BOLD_FONT));
		formula3.add(builder.subString("итог", PDFBuilder.SUB_SUP_BOLD_FONT));
		formula3.add(builder.textString(" = ",PDFTableStyles.BOLD_FONT));
		formula3.add(builder.textString("БС",PDFTableStyles.BOLD_FONT));
		formula3.add(builder.subString("ст.к.", PDFBuilder.SUB_SUP_BOLD_FONT));
		formula3.add(builder.textString("+БС",PDFTableStyles.BOLD_FONT));
		formula3.add(builder.subString("еж.вл.", PDFBuilder.SUB_SUP_BOLD_FONT));
		tableFormula3.addElementsToCell(formula3, 1, 1, cellStyle);
		tableFormula3.addToPage(TableBreakRule.wholeTableInPage);

		builder.addParagraph("Где:");
		builder.addTextString("БС", PDFTableStyles.BOLD_FONT);
		builder.addSubString("ст.к.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString(" – будущая стоимость портфеля «Стартовый капитал» в N-ом квартале;");

		builder.addEmptyParagraph();
		builder.addTextString("БС", PDFTableStyles.BOLD_FONT);
		builder.addSubString("еж.вл.", PDFBuilder.SUB_SUP_BOLD_FONT);
		builder.addTextString(" – будущая стоимость портфеля «Ежеквартальные вложения» в N-ом квартале.");

		addExample5();

		PDFTableBuilder tableImportant = builder.getTableBuilderInstance(2, resoursePath + PDFBuilder.FONT_NAME, new ParagraphStyle(12f, 0f, 4f, 0f));
		tableImportant.setWidths(360, new int[] {35, 325});
		CellStyle importantTableCellStyle = new CellStyle();
		cellStyle.setBorderWidth(0);
		tableImportant.addImageToCurrentRow(resoursePath + "important.png", 31, 35, importantTableCellStyle);
		tableImportant.addValueToCell("Обращаем Ваше внимание, что прогноз является справедливым, " +
				"если постоянно сохраняются изначальные пропорции Ваших портфелей. " +
				"Поэтому рекомендуем Вам на ежегодной основе корректировать структуру Ваших портфелей" +
				" в зависимости от динамики выбранных Вами продуктов.", PDFBuilder.TEXT_FONT, importantTableCellStyle);

		tableImportant.addToPage(TableBreakRule.wholeTableInPage);

		addExample6();
	}

	private void addProductTerm() throws PDFBuilderException
	{
		builder.addParagraph("Срочность продуктов", PDFBuilder.SUB_TITLE_PARAGRAPH, PDFBuilder.SUB_TITLE_FONT);
		builder.addParagraph("В расчётах не учитывается срочность продуктов. Для расчётов используется допущение, что продукт будет автоматически переоформлен/пролонгирован на новый срок с такими же условиями.", PDFBuilder.BASE_PARAGRAPH);
		builder.addParagraph("Например, если Вы оформляете вклад на 3 года и в модели закладываете ставку 6% годовых, то в расчёте на 10 лет будет учитываться, " +
				"что Ваш вклад регулярно пролонгируется под такую же ставку, которую Вы ввели изначально (6% годовых для нашего примера).", PDFBuilder.BASE_PARAGRAPH);

		Calendar date = DateHelper.getCurrentDate();

		java.util.List<StackedBarChartItem> graphList = new ArrayList<StackedBarChartItem>();
		Category category1 = new Category("Сумма вклада", new Color(190,10,73), "{0}");
		Category category2 = new Category("Доход", new Color(255, 134, 7), "{0}");
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
		chartSettings.setChartTitle("Рисунок 8. Переоформление/пролонгация");
		chartSettings.setShowLegend(false);
		chartSettings.setShowAxisY(false);

		builder.addImage(chartBiulder.generate3DStakedBarChart(graphList, chartSettings, 2), PDF_CHART_WIDTH, PDF_CHART_HEIGHT, Alignment.center);
	}

	private void addInsuranceCalc() throws PDFBuilderException
	{
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);
		builder.addParagraph("Расчёт по страховым продуктам", PDFBuilder.SUB_TITLE_PARAGRAPH, PDFBuilder.SUB_TITLE_FONT);
		builder.addParagraph("Если Вы добавляете в портфель страховой продукт, то расчёт строится следующим образом.", PDFBuilder.BASE_PARAGRAPH);
		builder.addParagraph("Сумма страховых взносов, указанная при добавлении продукта в портфель «Стартовый капитал», участвует в расчёте доходности стартового капитала.", PDFBuilder.BASE_PARAGRAPH);
		builder.addParagraph("В случае, если Вы осуществляете периодические страховые взносы, то производится расчёт суммы, которую Вам требуется накапливать на " +
				"ежеквартальной основе для оплаты страховых взносов. Эта сумма учитывается в портфеле «Ежеквартальные вложения». При этом учитывается, что ежеквартально " +
				"на эту сумму начисляются проценты, исходя из указанной Вами доходности по страховому продукту.", PDFBuilder.BASE_PARAGRAPH);

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
