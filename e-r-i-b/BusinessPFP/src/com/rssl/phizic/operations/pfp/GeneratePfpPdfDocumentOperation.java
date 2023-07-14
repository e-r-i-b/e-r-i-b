package com.rssl.phizic.operations.pfp;

import com.rssl.phizic.business.dictionaries.pfp.PfpConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ResourcePropertyReader;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.chart.BarChartItem;
import com.rssl.phizic.utils.chart.ChartBuilder;
import com.rssl.phizic.utils.chart.ChartSettings;
import com.rssl.phizic.utils.pdf.*;

import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * @author lepihina
 * @ created 11.09.2012
 * @ $Author$
 * @ $Revision$
 */
public abstract class GeneratePfpPdfDocumentOperation extends NotCheckStateEditPfpOperationBase
{
	protected static final int LOGO_WIDTH = 185;
	protected static final int LOGO_HEIGHT = 50;
	protected static final int CHART_WIDTH = 1000;
	protected static final int CHART_HEIGHT = 500;
	protected static final int PIE_CHART_HEIGHT = 340;

	private static final int QUARTER_IN_YEAR = 4;
	private static final int CALCULATION_SCALE = 10;

	private static final Color POSITIVE_COLOR = new Color(130,204,59);
	private static final Color NEGATIVE_COLOR = new Color(255,162,0);
	private static final int[] TABLE_WIDTHS = new int[]{30, 60, 60, 60, 40, 40, 40};

	private FinancePlanCalculator financePlanCalculator = null;

	/**
	 * Метод для построения таблицы с рассчетом будущей стоимости портфеля
	 * @param builder билдер документа
	 * @throws PDFBuilderException
	 */
	protected void drawFuturePortfolioSum(PDFBuilder builder) throws PDFBuilderException
	{
		if (financePlanCalculator == null)
			financePlanCalculator = new FinancePlanCalculator(personalFinanceProfile);

		Calendar executionDate = personalFinanceProfile.getStartPlaningDate();
		int quarterCount = financePlanCalculator.getQuarterCount();

		// Шапка таблицы
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(7, ConfigFactory.getConfig(PfpConfig.class).getResourcePath() + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);
		tableBuilder.setTableWidthPercentage(100);
		tableBuilder.setWidths(PDFTableBuilder.TABLE_WIDTH_VERT_LIST - 30, TABLE_WIDTHS);

		CellStyle thCellStyleCenter = new CellStyle(PDFTableStyles.CELL_STYLE);
		thCellStyleCenter.setHorizontalAlignment(Alignment.center);

		tableBuilder.addValueToCell("КВАРТАЛ", PDFTableStyles.TEXT_FONT, thCellStyleCenter);
		tableBuilder.addValueToCell("СУММА ЕЖЕКВАРТАЛЬНЫХ ВЛОЖЕНИЙ", PDFTableStyles.TEXT_FONT, thCellStyleCenter);
		tableBuilder.addValueToCell("ПОРТФЕЛЬ \"ЕЖЕКВАРТАЛЬНЫЕ ВЛОЖЕНИЯ\"", PDFTableStyles.TEXT_FONT, thCellStyleCenter);
		tableBuilder.addValueToCell("ПОРТФЕЛЬ \"СТАРТОВЫЙ КАПИТАЛ\"", PDFTableStyles.TEXT_FONT, thCellStyleCenter);
		tableBuilder.addValueToCell("КРЕДИТЫ", PDFTableStyles.TEXT_FONT, thCellStyleCenter);
		tableBuilder.addValueToCell("ПЛАТЕЖИ ПО КРЕДИТАМ", PDFTableStyles.TEXT_FONT, thCellStyleCenter);
		tableBuilder.addValueToCell("ИТОГОВЫЙ ПОРТФЕЛЬ", PDFTableStyles.TEXT_FONT, thCellStyleCenter);

		CellStyle cellStyleRight = new CellStyle(PDFTableStyles.CELL_STYLE);
		cellStyleRight.setHorizontalAlignment(Alignment.right);

		Map<Integer, BigDecimal> quarterlyInvestQuarterAmountList = financePlanCalculator.getQuarterlyInvestQuarterAmountList();
		Map<Integer, BigDecimal> quarterlyInvestAmountList = financePlanCalculator.getQuarterlyInvestAmountList();
		Map<Integer, BigDecimal> startCapitalAmountList = financePlanCalculator.getStartCapitalAmountList();
		Map<Integer, BigDecimal> totalAmountList = financePlanCalculator.getTotalAmountList();
		Map<Integer, BigDecimal> loanQuarterAmountList = financePlanCalculator.getLoanQuarterAmountList();
		Map<Integer, BigDecimal> loanQuarterPaymentList = financePlanCalculator.getLoanQuarterPaymentList();
		Calendar currentDate = executionDate;
		for(int i = 0; i <= quarterCount; i++)
		{
			String quarter = DateHelper.getQuarter(currentDate) + "." + currentDate.get(Calendar.YEAR);
			String quarterlyInvestQuarterAmount = formatNotZeroAmountToString(quarterlyInvestQuarterAmountList.get(i));
			String quarterlyInvestAmount = formatNotZeroAmountToString(quarterlyInvestAmountList.get(i));
			String startCapitalAmount = formatAmountToString(startCapitalAmountList.get(i));
			String loanQuarterAmount = loanQuarterAmountList.get(i) != null ? formatAmountToString(loanQuarterAmountList.get(i)) : "0";
			String loanQuarterPayment = loanQuarterPaymentList.get(i) != null ? formatAmountToString(loanQuarterPaymentList.get(i)) : "0";
			String totalAmount = formatAmountToString(totalAmountList.get(i));

			tableBuilder.addValueToCell(quarter, PDFTableStyles.TEXT_FONT, PDFTableStyles.QUARTER_STYLE); // КВАРТАЛ
			tableBuilder.addValueToCell(quarterlyInvestQuarterAmount, PDFTableStyles.TEXT_FONT, cellStyleRight); // СУММА ЕЖЕКВАРТАЛЬНЫХ ВЛОЖЕНИЙ
			tableBuilder.addValueToCell(quarterlyInvestAmount, PDFTableStyles.TEXT_FONT, cellStyleRight); // ПОРТФЕЛЬ "ЕЖЕКВАРТАЛЬНЫЕ ВЛОЖЕНИЯ"
			tableBuilder.addValueToCell(startCapitalAmount, PDFTableStyles.TEXT_FONT, cellStyleRight); // ПОРТФЕЛЬ "СТАРТОВЫЙ КАПИТАЛ"
			tableBuilder.addValueToCell(loanQuarterAmount, PDFTableStyles.TEXT_FONT, cellStyleRight); // КРЕДИТЫ
			tableBuilder.addValueToCell(loanQuarterPayment, PDFTableStyles.TEXT_FONT, cellStyleRight); // ПЛАТЕЖИ ПО КРЕДИТАМ
			tableBuilder.addValueToCell(totalAmount, PDFTableStyles.BOLD_FONT, cellStyleRight); // ИТОГОВЫЙ ПОРТФЕЛЬ

			currentDate = DateHelper.getNextQuarter(currentDate);
		}
		tableBuilder.addToPage(TableBreakRule.twoLineMinimumInPage, Alignment.bottom);

	}

	private String formatNotZeroAmountToString(BigDecimal amount)
	{
		if (BigDecimal.ZERO.compareTo(amount) > 0)
			return formatAmountToString(BigDecimal.ZERO);

		return formatAmountToString(amount);
	}

	private String formatAmountToString(BigDecimal amount)
	{
		return StringHelper.getStringInNumberFormat(amount.setScale(0, RoundingMode.HALF_UP).toString());
	}

	/**
	 * Строит график "Будущая стоимость портфеля"
	 * @param builder билдер документа
	 * @param chartBuilder билдер графиков
	 * @param chartTitle заголовок графика
	 * @throws com.rssl.phizic.utils.pdf.PDFBuilderException
	 */
	protected void drawFinancePlanChart(PDFBuilder builder, ChartBuilder chartBuilder, String chartTitle, int width, int height) throws PDFBuilderException
	{
		if (financePlanCalculator == null)
			financePlanCalculator = new FinancePlanCalculator(personalFinanceProfile);

        Calendar executionDate = (Calendar) personalFinanceProfile.getStartPlaningDate().clone();
		int quartersDiff = financePlanCalculator.getQuarterCount();

		// Если период менее 4-x лет или ровно 4 года, то шаг равен одному кварталу (значение по умолчанию).
		int quarterStep = 1;
		// Если период больше 26-ти лет, то шаг равен 16-ти кварталам (4 года).
		if(quartersDiff > 26*QUARTER_IN_YEAR)
		{
	        quarterStep = 4*QUARTER_IN_YEAR;
		}
		// Если период больше 8-ми лет и меньше 26 лет, то шаг равен 8-ми кварталам (2 года).
		else if(quartersDiff > 8*QUARTER_IN_YEAR)
		{
			quarterStep = 2*QUARTER_IN_YEAR;
		}
		// Если период больше 4-x лет и меньше 8 лет, то шаг равен 4-м кварталам (год).
		else if(quartersDiff > 4*QUARTER_IN_YEAR)
		{
			quarterStep = QUARTER_IN_YEAR;
		}

		int delta = 0;
		// шаг отображения - год/несколько лет, поэтому отображаем значения на последний квартал года
		if (quarterStep > 1)
			delta = QUARTER_IN_YEAR - DateHelper.getQuarter(executionDate);

		List<BarChartItem> productList = new ArrayList<BarChartItem>();
		Map<Integer, BigDecimal> totalAmountList = financePlanCalculator.getTotalAmountList();
        for(int i = delta; i <= quartersDiff; i = i + quarterStep)
        {
	        String categoryName = String.valueOf(executionDate.get(Calendar.YEAR));
	        if (quarterStep == 1)
		        categoryName = DateHelper.getQuarter(executionDate) + " кв. " + categoryName;

	        productList.add(new BarChartItem(totalAmountList.get(i).setScale(CALCULATION_SCALE, RoundingMode.HALF_UP).doubleValue(),  categoryName, "", POSITIVE_COLOR, NEGATIVE_COLOR));
	        executionDate.add(Calendar.MONTH, quarterStep*3);
        }

		ChartSettings chartSettings = new ChartSettings(CHART_WIDTH, CHART_HEIGHT);
		chartSettings.setChartTitle(chartTitle);
		chartSettings.setShowLegend(false);
		chartSettings.setShowLabelsOnChart(false);
		chartSettings.setTitleAxisY("Сумма, руб.");
		builder.addImage(chartBuilder.generate3DBarChart(productList, chartSettings, 1), width, height, Alignment.center);
	}
}
