package com.rssl.phizic.operations.pfp;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.dictionaries.pfp.PfpConfig;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.common.PortfolioType;
import com.rssl.phizic.business.dictionaries.pfp.products.card.Card;
import com.rssl.phizic.business.dictionaries.pfp.products.card.CardDiagramParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.card.CardProgrammType;
import com.rssl.phizic.business.dictionaries.pfp.products.card.CardService;
import com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation.UseCreditCardRecommendation;
import com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation.UseCreditCardRecommendationService;
import com.rssl.phizic.business.dictionaries.pfp.products.card.recommendation.UseCreditCardRecommendationStep;
import com.rssl.phizic.business.dictionaries.pfp.products.loan.LoanKindProduct;
import com.rssl.phizic.business.dictionaries.pfp.products.loan.LoanKindProductService;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.ProductType;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfile;
import com.rssl.phizic.business.employees.Employee;
import com.rssl.phizic.business.employees.MultiInstanceEmployeeService;
import com.rssl.phizic.business.image.Image;
import com.rssl.phizic.business.image.ImageService;
import com.rssl.phizic.business.pfp.PersonLoan;
import com.rssl.phizic.business.pfp.PersonTarget;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.pfp.PersonalFinanceProfileService;
import com.rssl.phizic.business.pfp.portfolio.PersonPortfolio;
import com.rssl.phizic.business.pfp.portfolio.PortfolioProduct;
import com.rssl.phizic.business.pfp.portfolio.ProductAmount;
import com.rssl.phizic.business.pfp.portfolio.product.BaseProduct;
import com.rssl.phizic.business.pfp.portfolio.product.InsuranceBaseProduct;
import com.rssl.phizic.business.pfp.portfolio.product.InvestmentBaseProduct;
import com.rssl.phizic.business.pfp.portfolio.product.PensionBaseProduct;
import com.rssl.phizic.business.pfp.risk.profile.PersonRiskProfile;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.*;
import com.rssl.phizic.utils.chart.*;
import com.rssl.phizic.utils.pdf.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lepihina
 * @ created 20.04.2012
 * @ $Author$
 * @ $Revision$
 */
public class ShowPfpResultOperation extends GeneratePfpPdfDocumentOperation
{
	private static final ImageService imageService = new ImageService();
	private static final MultiInstanceEmployeeService multiInstanceEmployeeService = new MultiInstanceEmployeeService();
	private static final UseCreditCardRecommendationService recommendationService = new UseCreditCardRecommendationService();
	private static final CardService cardService = new CardService();
	private static final LoanKindProductService loanProductService = new LoanKindProductService();

	private static final Map<ProductType, Color> productColor= new HashMap<ProductType, Color>();
	private static final Map<String, Color> barColor= new HashMap<String, Color>();

	private static final int PDF_CHART_WIDTH = 500;
	private static final int PDF_CHART_HEIGHT = 250;
	private static final int PDF_PIE_CHART_HEIGHT = 170;

	private static final int PDF_PRODUCT_ICON_WIDTH = 50;
	private static final int PDF_PRODUCT_ICON_HEIGHT = 50;

	private static final int CARD_ICON_MIN_WIDTH = 64;
	private static final int CARD_ICON_MIN_HEIGHT = 64;
	private static final int CARD_ICON_MAX_WIDTH = 100;
	private static final int CARD_ICON_MAX_HEIGHT = 64;

	private static final Color ACCOUNT_COLOR = new Color(0,191,243);
	private static final Color THANKS_COLOR = new Color(193,238,0);

	private static final ParagraphStyle NO_LEADING_PARAGRAPH_STYLE = new ParagraphStyle(0f, 0f, 12f, 0f);
	public static final FontStyle FOOTER_TEXT_FONT = new FontStyle(12, FontDecoration.normal, Color.GRAY); // стиль для подписей в футере страницы
	private static final CellStyle CELL_WITHOUT_BORDER_CENTER = new CellStyle(PDFTableStyles.CELL_WITHOUT_BORDER);

	private Employee employee;
	private Map<String, PersonalFinanceProfile> profiles;
	private int imgNum = 0;

	private static final Map<DictionaryProductType,String> nameMap = new HashMap<DictionaryProductType, String>();

	static
	{		
		productColor.put(ProductType.account,       new Color(92,200,55));
		productColor.put(ProductType.fund,          new Color(255,158,1));
		productColor.put(ProductType.IMA,           new Color(182,0,255));
		productColor.put(ProductType.insurance,     new Color(0,176,240));
		productColor.put(ProductType.trustManaging, new Color(237,28,36));

		barColor.put("income",  new Color(255,211,6));
		barColor.put("outcome", new Color(132,178,212));
		barColor.put("free",    new Color(245,135,40));

		CELL_WITHOUT_BORDER_CENTER.setHorizontalAlignment(Alignment.center);
		CELL_WITHOUT_BORDER_CENTER.setVerticalAlignment(Alignment.top);

		nameMap.put(DictionaryProductType.ACCOUNT,"Вклад");
		nameMap.put(DictionaryProductType.IMA,"ОМС");
		nameMap.put(DictionaryProductType.FUND,"ПИФ");
		nameMap.put(DictionaryProductType.INSURANCE,"Страховой продукт");
		nameMap.put(DictionaryProductType.TRUST_MANAGING,"Доверительное управление");
		nameMap.put(DictionaryProductType.PENSION,"Пенсионный продукт");
	}

	private String resourcePath;

	public void initialize(Long id, Boolean canWorkWithVIP) throws BusinessException, BusinessLogicException
	{
		profiles = getAllProfiles();
		PersonalFinanceProfile completedFinanceProfile = profiles.get(PersonalFinanceProfileService.COMPLETED_PFP);
		if (completedFinanceProfile == null)
			throw new BusinessLogicException("Для даного пользователя не найдено завершенного планирования.");

		super.initialize(completedFinanceProfile);

		if(isEmployee() && !canWorkWithVIP && getCurrentClientSegment() == SegmentCodeType.VIP)
			throw new AccessException("У вас не хватает прав для работы с данным клиентом.");
	}

	public PersonalFinanceProfile getNotCompletedPFP()
	{
		return profiles.get(PersonalFinanceProfileService.NOT_COMPLETED_PFP);
	}

	/**
	 * @return имя файла
	 */
	public String getFileName()
	{
		return DateHelper.formatDateToStringWithSlash(personalFinanceProfile.getExecutionDate());
	}

	/**
	 * Формирует pdf
	 * @return pdf-файл
	 * @throws PDFBuilderException
	 * @throws BusinessException
	 */
	public byte[] createPDF() throws PDFBuilderException, BusinessException
	{
		resourcePath = ConfigFactory.getConfig(PfpConfig.class).getResourcePath();
		HeaderFooterBuilder headerFooterBuilder = new HeaderFooterBuilder();
		headerFooterBuilder.addOnlyTitleInstance("Составлен: " + DateHelper.formatDateToStringWithPoint(personalFinanceProfile.getExecutionDate()), FOOTER_TEXT_FONT);
		headerFooterBuilder.addImageInstance(resourcePath + "logoSBRFNew.png", LOGO_WIDTH, LOGO_HEIGHT, Alignment.right);
		headerFooterBuilder.addPageNumberInstance();
		PDFBuilder builder = PDFBuilder.getInstance(headerFooterBuilder, resourcePath + PDFBuilder.FONT_NAME, 12, DocumentOrientation.VERTICAL);
		ChartBuilder chartBuilder = new ChartBuilder();

		// Создаем стили для параграфов документа
		ParagraphStyle mainTitleParagraph = new ParagraphStyle(15f, 0f, 12f, 25f, Alignment.center);

		// Создаем стили для шрифтов документа
		FontStyle mainTitleFont = new FontStyle(16, FontDecoration.bold);

		drawFrontPage(builder);

		builder.addParagraph("Уважаемый клиент, " + getPerson().getFirstPatrName() + "!", mainTitleParagraph, mainTitleFont);
	    builder.addParagraph("Благодарим Вас, что Вы выбрали ОАО «Сбербанк России» своим финансовым партнёром.", PDFBuilder.BASE_PARAGRAPH);
		builder.addParagraph("Финансовый план основывается на предоставленной Вами информации.", PDFBuilder.BASE_PARAGRAPH);
		builder.addParagraph("Мы надеемся, что выбранные продукты помогут Вам достичь финансовых целей.", PDFBuilder.BASE_PARAGRAPH);

		drawTargetsTable(builder);
		drawDefaultRiskProfile(builder, chartBuilder);
		if (getProfile().getPersonRiskProfile() != null)
		{
			drawPersonRiskProfile(builder, chartBuilder);
		}
		drawMonthBudget(builder, chartBuilder);

		String paragraph = "Рекомендуем Вам поддерживать на сберегательном счете сумму " + getStrAmountWithCurrency(getOutcomeMoney()) +
				", которая в совокупности с лимитом по кредитной карте является надежным резервом на случай непредвиденных обстоятельств.";

		builder.addParagraph(paragraph, PDFBuilder.BASE_LAST_ON_PAGE_PARAGRAPH);
		builder.newPage();

		if (person.getSegmentCodeType() != SegmentCodeType.VIP)
		{
			drawUseCreditCard(builder); // Грамотное использование кредитной карты
			drawHowUsingCreditCard(builder, chartBuilder); // Как правильно пользоваться кредитной картой
			if (getProfile().getCardId() != null)
				drawCreditCard(builder, chartBuilder); // Кредитная карта, выбранная клиентом
			builder.newPage();
		}

		if (CollectionUtils.isNotEmpty(getProfile().getPersonLoans()))
		{
			drawLoans(builder);
		}

		// Распределение продуктов в портфеле "Стартовый капитал"
		PersonPortfolio portfolioStartCap = personalFinanceProfile.getPortfolioByType(PortfolioType.START_CAPITAL);
		if (!portfolioStartCap.getIsEmpty())
		{
			drawPortfolioStartCap(builder, chartBuilder, portfolioStartCap);
			builder.newPage();
		}

		// Распределение продуктов в портфеле "Ежеквартальные вложения"
		PersonPortfolio portfolioQuartInv = personalFinanceProfile.getPortfolioByType(PortfolioType.QUARTERLY_INVEST);
		if (!portfolioQuartInv.getIsEmpty())
		{
			drawPortfolioQuartInv(builder, chartBuilder, portfolioQuartInv);
			builder.newPage();
		}

		futurePortfoliosAmounts(builder, chartBuilder, portfolioStartCap, portfolioQuartInv);

		if (employee != null)
		{
			String managerText = "Сформировать Ваш финансовый портфель и оформить документы Вам поможет персональный менеджер " + employee.getFullName();
			String managerPhone = new String(employee.getManagerPhone());
			if (StringHelper.isNotEmpty(managerPhone))
			{
				if (managerPhone.matches("[+][0-9][(][0-9]{3}[)][0-9]{3}[-][0-9]{2}[-][0-9]{2}"))
					managerPhone = managerPhone.replaceAll("\\("," ").replaceAll("\\)"," ").replaceAll("-"," ");
				managerText += ", тел. " + managerPhone;
			}
			builder.startParagraph(PDFBuilder.BASE_JUSTIFIED_PARAGRAPH);
			builder.addPhrase(managerText);
			if (StringHelper.isNotEmpty(employee.getManagerEMail()))
			{
				builder.addPhrase(", адрес электронной почты: ");
				builder.addHyperlinkPhrase(employee.getManagerEMail(), "mailto:" + employee.getManagerEMail(), PDFBuilder.HYPERLINK_FONT);
			}
			builder.addPhrase(".");
			builder.endParagraph();
		}
		else
		{
			builder.addParagraph("Сформировать Ваш финансовый портфель и оформить документы Вы можете у персонального менеджера Сбербанк Премьер. " +
					"Чтобы назначить встречу, обратитесь в контактный центр «Сбербанк Премьер» по телефону 8 800 555 02 55.", PDFBuilder.BASE_JUSTIFIED_PARAGRAPH);
		}

		builder.addParagraph("Мы очень ценим наше сотрудничество и сделаем всё возможное, чтобы оно было комфортным и результативным.", PDFBuilder.BASE_JUSTIFIED_PARAGRAPH);

		builder.addParagraph("С уважением, ОАО «Сбербанк России».", Alignment.right);
		builder.addParagraph("Дата: " + DateHelper.formatDateToStringWithPoint(personalFinanceProfile.getExecutionDate()), Alignment.right);
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);

		drawSignPlace(builder);

	    return builder.build();
	}

	/**
	 * Генерирует титульный лист
	 * @param builder - билдер pdf
	 * @throws PDFBuilderException
	 * @throws BusinessException
	 */
	private void drawFrontPage(PDFBuilder builder) throws PDFBuilderException, BusinessException
	{
		builder.addEmptyParagraph(new ParagraphStyle(15f, 50f, 0f, 0f));
		builder.addImage(resourcePath + "sbrfTitulLogo.png", 551, 91, Alignment.center);
		builder.addParagraph("Персональный финансовый план", PDFBuilder.TITLE_FILE_PARAGRAPH_CENTER, PDFBuilder.TITLE_LIST_FONT);

		builder.addEmptyParagraph();
		builder.addPhrase("Клиент: ");
		builder.addPhrase(getPerson().getFirstPatrName(), PDFBuilder.BOLD_FONT);
		builder.addEmptyParagraph();

		if (StringHelper.isNotEmpty(getPerson().getManagerId()))
		{
		    employee = multiInstanceEmployeeService.findByManagerId(getPerson().getManagerId(), null);
			if (employee != null && StringHelper.isNotEmpty(getProfile().getEmployeeFIO()))
			{
				builder.addPhrase("Персональный менеджер: ");
				builder.addPhrase(employee.getFullName());
			}
		}
		builder.newPage();
	}

	/**
	 * Генерирует диаграмму для риск профиля по умолчанию
	 * @param builder - билдер pdf
	 * @param chartBuilder - билдер графиков
	 * @throws PDFBuilderException
	 */
	private void drawDefaultRiskProfile(PDFBuilder builder, ChartBuilder chartBuilder) throws PDFBuilderException
	{
		RiskProfile riskProfile = getProfile().getDefaultRiskProfile();
		List<PieChartItem> productList = new ArrayList<PieChartItem>();
		builder.addParagraph("Ваш риск-профиль – " + riskProfile.getName(), PDFBuilder.TITLE_PARAGRAPH, PDFBuilder.TITLE_FONT);
		builder.addParagraph(riskProfile.getDescription(), PDFBuilder.BASE_LAST_ON_PAGE_PARAGRAPH);
		builder.newPage();

		builder.addParagraph("Расчётное распределение средств по риск-профилю", NO_LEADING_PARAGRAPH_STYLE, PDFBuilder.TITLE_FONT);
		builder.addParagraph("Диаграмма показывает рекомендуемое распределение Ваших свободных денежных средств.", PDFBuilder.BASE_PARAGRAPH);

		for(Map.Entry<ProductType, Long> entry: riskProfile.getProductsWeights().entrySet())
		{
			if(entry.getValue() > 0)
			{
				productList.add(new PieChartItem(entry.getKey().getDescription(), entry.getValue(), productColor.get(entry.getKey())));
			}
		}

		ChartSettings chartSettings = new ChartSettings(CHART_WIDTH, PIE_CHART_HEIGHT);
		chartSettings.setChartTitle("Рисунок " + ++imgNum + " Рекомендуемое распределение Ваших свободных денежных средств");
		chartSettings.setShowLegend(false);
		builder.addImage(chartBuilder.generate3DPieChart(productList, chartSettings), PDF_CHART_WIDTH, PDF_PIE_CHART_HEIGHT, Alignment.center);

		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);
	}

	/**
	 * Генерирует диаграмму для риск профиля клиента
	 * @param builder - билдер pdf
	 * @param chartBuilder - билдер графиков
	 * @throws PDFBuilderException
	 */
	private void drawPersonRiskProfile(PDFBuilder builder, ChartBuilder chartBuilder) throws PDFBuilderException
	{
		PersonRiskProfile personRiskProfile = getProfile().getPersonRiskProfile();
		List<PieChartItem> productList = new ArrayList<PieChartItem>();

		NotWrappedElementBuilder notWrappedElementBuilder = builder.getNotWrappedElementBuilderInstance();
		notWrappedElementBuilder.addParagraph("Ваше распределение портфеля", NO_LEADING_PARAGRAPH_STYLE, PDFBuilder.TITLE_FONT);

		for(Map.Entry<ProductType, Long> entry: personRiskProfile.getProductsWeights().entrySet())
		{
			if(entry.getValue() > 0)
			{
				productList.add(new PieChartItem(entry.getKey().getDescription(), entry.getValue(), productColor.get(entry.getKey())));
			}
		}

		ChartSettings chartSettings = new ChartSettings(CHART_WIDTH, PIE_CHART_HEIGHT);
		chartSettings.setChartTitle("Рисунок " + ++imgNum + " Ваше распределение портфеля");
		chartSettings.setShowLegend(false);
		notWrappedElementBuilder.addImage(chartBuilder.generate3DPieChart(productList, chartSettings), PDF_CHART_WIDTH, PDF_PIE_CHART_HEIGHT, Alignment.center);
		notWrappedElementBuilder.addToPage();

		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);
	}

	/**
	 * Генерирует таблицу "Мои цели"
	 * @param builder - билдер pdf
	 * @throws PDFBuilderException
	 */
	private void drawTargetsTable(PDFBuilder builder) throws PDFBuilderException, BusinessException
	{
		int countColumns = 4;
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(countColumns, resourcePath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);
		tableBuilder.setTableTitle("Мои цели", PDFBuilder.TITLE_PARAGRAPH, PDFBuilder.TITLE_FONT);
		// Стиль для ячейки таблицы с картинкой
		CellStyle cellImgStyle = new CellStyle();
		cellImgStyle.setBorderWidth(0);
		cellImgStyle.setBorderWidthBottom(PDFTableStyles.TABLE_BORDER_WIDTH);
		cellImgStyle.setBorderColor(Color.GRAY);
		cellImgStyle.setMinimumHeight(34f);

		CellStyle TH_CELL_STYLE = new CellStyle(); // ячейки заголовка таблицы
		TH_CELL_STYLE.setBorderWidth(0); // убираем границы в таблице
		TH_CELL_STYLE.setBorderWidthBottom(PDFTableStyles.TABLE_BORDER_WIDTH); // устанавлтваем только нижнюю границу
		TH_CELL_STYLE.setBorderColor(Color.GRAY); // цвет нижней границы
		TH_CELL_STYLE.setMinimumHeight(25f); // минимальная высота ячейки

		CellStyle TH_CELL_STYLE_CENTER = new CellStyle(TH_CELL_STYLE); // ячейки заголовка таблицы с выравниванием по центру
		TH_CELL_STYLE_CENTER.setHorizontalAlignment(Alignment.center); // выравнивание по центру

		CellStyle TH_CELL_STYLE_RIGHT = new CellStyle(TH_CELL_STYLE); // ячейки заголовка таблицы с выравниванием по правому краю
		TH_CELL_STYLE_RIGHT.setHorizontalAlignment(Alignment.right); // выравнивание по правому краю

		FontStyle TH_TEXT_FONT = new FontStyle(11, FontDecoration.normal, Color.GRAY);

		// Заголовки в таблице
		tableBuilder.addValueToCell("ЦЕЛЬ", TH_TEXT_FONT, TH_CELL_STYLE);
		tableBuilder.addValueToCell("", TH_TEXT_FONT, TH_CELL_STYLE);
		tableBuilder.addValueToCell("ПЛАНИРУЮ ДОСТИЧЬ", TH_TEXT_FONT, TH_CELL_STYLE_CENTER);
		tableBuilder.addValueToCell("СТОИМОСТЬ", TH_TEXT_FONT, TH_CELL_STYLE_RIGHT);

		final int imgWidth = 30;

		int w = (PDFTableBuilder.TABLE_WIDTH_VERT_LIST - (imgWidth + 2)) / (countColumns-1); // считаем ширину для столбцов без картинки (TABLE_WIDTH_VERT_LIST - вся ширина таблицы)
		int[] widths = {imgWidth + 2, w, w, w}; // ширина столбцов таблицы
		tableBuilder.setWidths(PDFTableBuilder.TABLE_WIDTH_VERT_LIST, widths);

		// Заполняем таблицу данными
		final int imgHeight = 30;
		for (PersonTarget target : getProfile().getPersonTargets())
		{
			String comment = StringHelper.isEmpty(target.getNameComment()) ? "" : " – " + target.getNameComment();

			Image image = imageService.findById(target.getImageId(), null);
			if (image != null)
				addImageToCell(tableBuilder, image, imgWidth, imgHeight, 1, 1, cellImgStyle);
			else
				tableBuilder.addImageToCurrentRow(resourcePath + "pfp/otherIcon.jpg", imgWidth, imgHeight, 1, 1, cellImgStyle);

			tableBuilder.addValueToCell(target.getName() + comment, PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_STYLE);
			tableBuilder.addValueToCell(DateHelper.formatDateWithQuarters(target.getPlannedDate()), PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_TD_ALIGNMENT_CENTER);
			tableBuilder.addValueToCell(getStrAmountWithCurrency(target.getAmount()), PDFTableStyles.BOLD_FONT, PDFTableStyles.CELL_TD_ALIGNMENT_RIGHT);
		}

		tableBuilder.addToPage(TableBreakRule.twoLineMinimumInPage);
	}

	/**
	 * Строит столбцовую диаграмму "Месячный бюджет"
	 * @param builder - билдер pdf
	 * @param chartBuilder - билдер диаграммы
	 * @throws PDFBuilderException
	 */
	private void drawMonthBudget(PDFBuilder builder, ChartBuilder chartBuilder) throws PDFBuilderException
	{
		List<BarChartItem> productList = new ArrayList<BarChartItem>();

		double incomeMoney = getProfile().getIncomeMoney().getDecimal().doubleValue();
		double outcomeMoney = getOutcomeMoney().getDecimal().doubleValue();
		double freeMoney = getProfile().getFreeMoney().getDecimal().doubleValue();

		productList.add(new BarChartItem(incomeMoney, "", "Ваши ежемесячные доходы", barColor.get("income")));
		productList.add(new BarChartItem(outcomeMoney, "", "Рекомендуемая сумма на сберегательном счете", barColor.get("outcome")));
		productList.add(new BarChartItem(freeMoney, "", "Ежемесячные свободные средства", barColor.get("free")));

		NotWrappedElementBuilder notWrappedElementBuilder = builder.getNotWrappedElementBuilderInstance();
		notWrappedElementBuilder.addParagraph("Ваш бюджет", PDFBuilder.TITLE_PARAGRAPH, PDFBuilder.TITLE_FONT);

		ChartSettings chartSettings = new ChartSettings(CHART_WIDTH, CHART_HEIGHT);
		chartSettings.setChartTitle("Рисунок " + ++imgNum + " Рекомендуемое ежемесячное распределение средств");
		chartSettings.setTitleAxisY("Сумма, руб.");
		chartSettings.setShowLegend(false);
		BufferedImage chart = chartBuilder.generate3DBarChart(productList, chartSettings, 3);
		notWrappedElementBuilder.addImage(chart, PDF_CHART_WIDTH, PDF_CHART_HEIGHT, Alignment.center);
		notWrappedElementBuilder.addToPage();
	}

	/**
	 * Рисует схему отражающую использование кредитных карт
	 * @param builder - билдер pdf
	 * @throws PDFBuilderException
	 * @throws BusinessException
	 */
	private void drawUseCreditCard(PDFBuilder builder) throws PDFBuilderException, BusinessException
	{
		builder.addParagraph("Грамотное использование кредитной карты", PDFBuilder.TITLE_PARAGRAPH, PDFBuilder.TITLE_FONT);

		int countColumns = 7;
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(countColumns, resourcePath + PDFBuilder.FONT_NAME, new ParagraphStyle(14f, 0f, 5f, 0f, Alignment.center));
		int[] widths = {75, 20, 75, 20, 75, 20, 75}; // ширина столбцов таблицы
		tableBuilder.setWidths(PDFTableBuilder.TABLE_WIDTH_VERT_LIST, widths);

		FontStyle thTextFont = new FontStyle(13);

		// Заголовки в таблице
		CellStyle cellGreenStyle = new CellStyle();
		cellGreenStyle.setBorderWidth(0);
		cellGreenStyle.setBackgroundColor(new Color(196, 231, 163));
		cellGreenStyle.setHorizontalAlignment(Alignment.center);

		// 1я строка с заголовками
		Calendar date = DateHelper.getCurrentDate();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
		tableBuilder.addValueToCell(dateFormat.format(date.getTime()).toUpperCase(), thTextFont, cellGreenStyle);
		for (int i = 1; i < 4; i++)
		{
			date.add(Calendar.MONTH, 1);
			tableBuilder.addImageToCurrentRow(resourcePath + "arrowNextStep.png", 18, 36, 1, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
			tableBuilder.addValueToCell(dateFormat.format(date.getTime()).toUpperCase(), thTextFont, cellGreenStyle);
		}

		CellStyle textCellStyle = new CellStyle(); // ячейки заголовка таблицы
		textCellStyle.setBorderWidth(0); // убираем границы в таблице
		textCellStyle.setHorizontalAlignment(Alignment.center);

		// 2я строка
		CellStyle cellImgStyle = new CellStyle();
		cellImgStyle.setBorderWidth(0);
		cellImgStyle.setFixedHeight(80);
		List<UseCreditCardRecommendationStep> recommendationSteps = getRecommendation().getSteps();
		tableBuilder.addImageToCurrentRow(resourcePath + "creditCard.gif", 98, 62, cellImgStyle);
		for (int i = 1; i < recommendationSteps.size(); i++)
		{
			tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
			tableBuilder.addValueToCell(recommendationSteps.get(i).getName(), PDFTableStyles.TEXT_FONT, textCellStyle);
		}

		CellStyle greenCellStyle = new CellStyle();
		greenCellStyle.setBorderWidth(0);
		greenCellStyle.setBackgroundColor(new Color(188, 237, 0));
		greenCellStyle.setHorizontalAlignment(Alignment.center);

		// 3я строка
		tableBuilder.addValueToCell(recommendationSteps.get(0).getName(), PDFTableStyles.TEXT_FONT, textCellStyle);
		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addValueToCell("СПАСИБО", PDFTableStyles.TEXT_FONT, greenCellStyle);

		// 4я строка
		CellStyle blueCellStyle = new CellStyle();
		blueCellStyle.setBorderWidth(0);
		blueCellStyle.setBackgroundColor(new Color(0, 178, 241));
		blueCellStyle.setFixedHeight(40);
		blueCellStyle.setHorizontalAlignment(Alignment.center);

		FontStyle whiteTextFont = new FontStyle(PDFTableStyles.TEXT_FONT);
		whiteTextFont.setColor(Color.WHITE);

		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addValueToCell("СПАСИБО", PDFTableStyles.TEXT_FONT, greenCellStyle);
		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addValueToCell("% Доход", 1, 2, whiteTextFont, blueCellStyle);

		// 5я строка
		blueCellStyle.setFixedHeight(20);

		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addValueToCell("% Доход", whiteTextFont, blueCellStyle);
		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);

		// 6я строка
		CellStyle purpleCellStyle = new CellStyle();
		purpleCellStyle.setBorderWidth(0); // убираем все границы
		purpleCellStyle.setBorderWidthBottom(PDFTableStyles.TABLE_BORDER_WIDTH); // устанавлтваем только нижнюю границу
		purpleCellStyle.setBorderColor(new Color(204, 204, 204)); // цвет нижней границы
		purpleCellStyle.setBackgroundColor(new Color(181, 41, 181));
		purpleCellStyle.setFixedHeight(80);
		purpleCellStyle.setHorizontalAlignment(Alignment.center);

		CellStyle borderCellStyle = new CellStyle();
		borderCellStyle.setBorderWidth(0); // убираем все границы
		borderCellStyle.setBorderWidthBottom(PDFTableStyles.TABLE_BORDER_WIDTH); // устанавлтваем только нижнюю границу
		borderCellStyle.setBorderColor(new Color(204, 204, 204)); // цвет нижней границы

		String outcomeMoneyText = getStrAmountWithCurrency(getOutcomeMoney()) + " Вложенные средства";

		tableBuilder.addEmptyValueToCell(borderCellStyle);
		tableBuilder.addEmptyValueToCell(borderCellStyle);
		tableBuilder.addValueToCell(outcomeMoneyText, whiteTextFont, purpleCellStyle);
		tableBuilder.addEmptyValueToCell(borderCellStyle);
		tableBuilder.addValueToCell(outcomeMoneyText, whiteTextFont, purpleCellStyle);
		tableBuilder.addEmptyValueToCell(borderCellStyle);
		tableBuilder.addValueToCell(outcomeMoneyText, whiteTextFont, purpleCellStyle);

		// 7я строка
		CellStyle grayCellStyle = new CellStyle();
		grayCellStyle.setBorderWidth(0);
		grayCellStyle.setBackgroundColor(new Color(234, 234, 234));
		grayCellStyle.setFixedHeight(80);
		grayCellStyle.setHorizontalAlignment(Alignment.center);

		CellStyle orangeCellStyle = new CellStyle();
		orangeCellStyle.setBorderWidth(0);
		orangeCellStyle.setBackgroundColor(new Color(255, 171, 0));
		orangeCellStyle.setFixedHeight(80);
		orangeCellStyle.setHorizontalAlignment(Alignment.center);

		String outcomeMoney = getStrAmountWithCurrency(getOutcomeMoney());
		List<String> outcomeCardAmountText = new ArrayList<String>(2);
		outcomeCardAmountText.add("-" + outcomeMoney);
		outcomeCardAmountText.add("Расходы по карте");
		List<String> outcomeAmountText = new ArrayList<String>(2);
		outcomeAmountText.add("-" + outcomeMoney);
		outcomeAmountText.add("Ваши расходы");

		tableBuilder.addValuesToCell(outcomeAmountText, 1, 1, PDFTableStyles.TEXT_FONT, grayCellStyle);
		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addValuesToCell(outcomeCardAmountText, 1, 1, PDFTableStyles.TEXT_FONT, orangeCellStyle);
		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addValuesToCell(outcomeCardAmountText, 1, 1, PDFTableStyles.TEXT_FONT, orangeCellStyle);
		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addValuesToCell(outcomeCardAmountText, 1, 1, PDFTableStyles.TEXT_FONT, orangeCellStyle);

		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);

		builder.addParagraph("Рисунок " + ++imgNum + " Грамотное использование кредитной карты", PDFBuilder.BASE_PARAGRAPH, PDFBuilder.BOLD_FONT);

		drawUseCreditCardDescription(builder); // Грамотное использование кредитной карты (описание схемы)
	}

	private UseCreditCardRecommendation getRecommendation() throws BusinessException
	{
		return recommendationService.getRecommendation();
	}

	private void drawUseCreditCardDescription(PDFBuilder builder) throws PDFBuilderException, BusinessException
	{
		// Заголовки в таблице
		CellStyle cellGreenStyle = new CellStyle();
		cellGreenStyle.setBorderWidth(0);
		cellGreenStyle.setBackgroundColor(new Color(146, 208, 80));
		cellGreenStyle.setHorizontalAlignment(Alignment.center);

		FontStyle monthTextFont = new FontStyle(13, FontDecoration.bold, Color.WHITE);
		FontStyle titleTextFont = new FontStyle(13, FontDecoration.bold);

		List<UseCreditCardRecommendationStep> recommendationSteps = getRecommendation().getSteps();
		Calendar date = DateHelper.getCurrentDate();
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
		for (int i = 0; i < recommendationSteps.size(); i++)
		{
			int countColumns = 2;
			PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(countColumns, resourcePath + PDFBuilder.FONT_NAME, new ParagraphStyle(11f, 0f, 3f, 0f));
			int[] widths = {50, 310}; // ширина столбцов таблицы
			tableBuilder.setWidths(PDFTableBuilder.TABLE_WIDTH_VERT_LIST, widths);
			tableBuilder.addValueToCell(dateFormat.format(date.getTime()), monthTextFont, cellGreenStyle);
			tableBuilder.addValueToCell(recommendationSteps.get(i).getName(), titleTextFont, PDFTableStyles.CELL_WITHOUT_BORDER);
			tableBuilder.addToPage(TableBreakRule.twoLineMinimumInPage);

			builder.addParagraph(recommendationSteps.get(i).getDescription(), PDFBuilder.BASE_PARAGRAPH);
			date.add(Calendar.MONTH, 1);
		}
	}

	private void drawHowUsingCreditCard(PDFBuilder builder, ChartBuilder chartBuilder) throws PDFBuilderException, BusinessException
	{
		BigDecimal accountIncome = personalFinanceProfile.getAccountValue() != null ? personalFinanceProfile.getAccountValue() : BigDecimal.ZERO;
		Category accountCategory = new Category("Доход по вкладу", ACCOUNT_COLOR, Color.black, "Доход по вкладу");

		BigDecimal thanksIncome = personalFinanceProfile.getThanksValue() != null ? personalFinanceProfile.getThanksValue() : BigDecimal.ZERO;
		Category thanksCategory = new Category("Накоплено Спасибо", THANKS_COLOR, Color.black, "Накоплено Спасибо");

		BigDecimal outcome = getOutcomeMoney().getDecimal();
		List<StackedBarChartItem> productList = new ArrayList<StackedBarChartItem>();
		for(int i = 1; i <= 5; i++)
        {
	        if (personalFinanceProfile.getUseAccount())
            {
		        ProductAmount accountProduct = PfpMathUtils.calculateAccountIncomeForCreditCard(outcome, accountIncome, i);
		        productList.add(new StackedBarChartItem(accountCategory, accountProduct.getIncome().doubleValue(), getYearName(i)));
            }
	        if (personalFinanceProfile.getUseThanks())
            {
                ProductAmount thanksProduct = PfpMathUtils.calculateThanksIncomeForCreditCard(outcome, thanksIncome, i);
                productList.add(new StackedBarChartItem(thanksCategory, thanksProduct.getIncome().doubleValue(), getYearName(i)));
            }
        }

		int seriesCount = 0;
		if (personalFinanceProfile.getUseAccount())
            seriesCount++;
		if (personalFinanceProfile.getUseThanks())
			seriesCount++;

		if (seriesCount > 0)
		{
			ChartStyle chartStyle = new ChartStyle();
			chartStyle.setDomainGridlinesVisible(true);
			chartStyle.setDomainGridlineColor(Color.GRAY);
			chartStyle.setRangeGridlinesVisible(true);
			chartStyle.setRangeGridlineColor(Color.GRAY);
			ChartSettings chartSettings = new ChartSettings(CHART_WIDTH, CHART_HEIGHT, chartStyle);
			chartSettings.setShowLegend(false);

			PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(2, resourcePath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);
			int[] widths = {25, 335}; // ширина столбцов таблицы
			tableBuilder.setWidths(PDFTableBuilder.TABLE_WIDTH_VERT_LIST, widths);

			if (personalFinanceProfile.getUseAccount())
			{
				String accountLegendTitle = "Доход по вкладу (" + String.valueOf(accountIncome) + "% годовых)";
				tableBuilder.addImageToCurrentRow(resourcePath + "creditCardAccount.png", 30, 14, 1, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
				tableBuilder.addValueToCell(accountLegendTitle, new FontStyle(10), PDFTableStyles.CELL_WITHOUT_BORDER);
			}
			if (personalFinanceProfile.getUseThanks())
			{
				String thanksLegendTitle = "Накоплено «Спасибо» (" + String.valueOf(thanksIncome) + " от каждой покупки)";
				tableBuilder.addImageToCurrentRow(resourcePath + "creditCardThanks.png", 30, 14, 1, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
				tableBuilder.addValueToCell(thanksLegendTitle, new FontStyle(10), PDFTableStyles.CELL_WITHOUT_BORDER);
			}
			tableBuilder.addValueToCell("Рисунок " + ++imgNum + " Эффект использования кредитной карты", 2, 1, new FontStyle(10, FontDecoration.bold), PDFTableStyles.CELL_WITHOUT_BORDER);

			NotWrappedElementBuilder notWrappedElementBuilder = builder.getNotWrappedElementBuilderInstance();
			notWrappedElementBuilder.addParagraph("Эффект использования кредитной карты", PDFBuilder.TITLE_PARAGRAPH, PDFBuilder.TITLE_FONT);
			notWrappedElementBuilder.addImage(chartBuilder.generate3DStakedBarChart(productList, chartSettings, seriesCount), PDF_CHART_WIDTH, PDF_CHART_HEIGHT, Alignment.center);
			notWrappedElementBuilder.addTable(tableBuilder);
			notWrappedElementBuilder.addToPage();

			builder.addEmptyParagraph();
			builder.addPhrase("Вы можете потратить накопленные «СПАСИБО» в магазинах-партнерах. Узнайте больше на сайте программы: ");
			builder.addHyperlinkPhrase("www.spasibosberbank.ru", "www.spasibosberbank.ru", PDFBuilder.HYPERLINK_FONT);
		}
	}

	private void drawCreditCard(PDFBuilder builder, ChartBuilder chartBuilder) throws PDFBuilderException, BusinessException
	{
		Long cardId = getProfile().getCardId();
		Card card = cardService.findById(cardId);
		CardDiagramParameters cardDiagramParameters = card.getDiagramParameters();

		NotWrappedElementBuilder notWrappedElementBuilder = builder.getNotWrappedElementBuilderInstance();
		notWrappedElementBuilder.addParagraph("Ваша кредитная карта: " + card.getName(), PDFBuilder.TITLE_PARAGRAPH, PDFBuilder.TITLE_FONT);
		if (StringHelper.isNotEmpty(card.getClause()))
			notWrappedElementBuilder.addParagraph(card.getClause(), PDFBuilder.BASE_PARAGRAPH, PDFBuilder.TEXT_FONT);

		Image cardIcon = imageService.findById(card.getCardIconId(), null);
		ImageWrapper imageWrapper = getImageWrapper(cardIcon, CARD_ICON_MIN_WIDTH, CARD_ICON_MIN_HEIGHT, CARD_ICON_MAX_WIDTH, CARD_ICON_MAX_HEIGHT);
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(2, resourcePath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);
		int imageWidth = CARD_ICON_MIN_WIDTH;
		if (imageWrapper != null)
			imageWidth = imageWrapper.getWidth();
		int[] widths = {imageWidth, 360 - imageWidth}; // ширина столбцов таблицы
		tableBuilder.setWidths(PDFTableBuilder.TABLE_WIDTH_VERT_LIST, widths);

		addImageToCell(tableBuilder, imageWrapper, 1, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addValueToCell(card.getDescription(), PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);
		notWrappedElementBuilder.addTable(tableBuilder);

		BigDecimal outcome = getOutcomeMoney().getDecimal();
		List<BarChartItem> productList = new ArrayList<BarChartItem>();

		for(int i = 1; i <= 5; i++)
        {
	        ProductAmount accountProduct = PfpMathUtils.calculateIncomeForCreditCardByType(outcome, i, card);
	        productList.add(new BarChartItem(accountProduct.getIncome().doubleValue(), getYearName(i), "" , getRGBColor(cardDiagramParameters.getColor())));
        }

		if (card.getProgrammType() != CardProgrammType.empty)
		{
			BufferedImage diagram = getCreditCardDiagram(card, chartBuilder, productList);
			notWrappedElementBuilder.addImage(diagram, PDF_CHART_WIDTH, PDF_CHART_HEIGHT, Alignment.center);
		}
		notWrappedElementBuilder.addToPage();
	}

	private BufferedImage getCreditCardDiagram(Card card, ChartBuilder chartBuilder, List<BarChartItem> productList) throws BusinessException
	{
		try
		{
			ChartSettings chartSettings = new ChartSettings(CHART_WIDTH, CHART_HEIGHT);
			chartSettings.setChartTitle("Рисунок " + ++imgNum + " Использование  кредитной  карты с программой лояльности");
			chartSettings.setShowLegend(false);
			chartSettings.setShowLabelsOnChart(false);

			String titleAxisY = "";
			switch (card.getProgrammType())
			{
				case aeroflot: titleAxisY = "Накоплено, миль."; break;
				case beneficent: titleAxisY = "Перечислено в фонд, руб."; break;
				case mts: titleAxisY = "Накоплено баллов."; break;
			}
			chartSettings.setTitleAxisY(titleAxisY);
			if (!card.getDiagramParameters().isUseImage())
				return chartBuilder.generate3DBarChart(productList, chartSettings, 1);

			Image image = imageService.findById(card.getDiagramParameters().getImageId(), null);
			if (StringHelper.isNotEmpty(image.getExtendImage()))
				return chartBuilder.generateImageBarChart(productList, chartSettings, 1, image.getExtendImage());

			if (image.isHaveImageBlob())
			{
				byte[] imgData = imageService.loadImageData(image, null);
				return chartBuilder.generateImageBarChart(productList, chartSettings, 1, imgData);
			}

			return chartBuilder.generateImageBarChart(productList, chartSettings, 1, resourcePath + image.getInnerImage());
		}
		catch (ChartException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * Добавляет в отчет информацию о кредитах клиента
	 * @param builder - билдер
	 * @throws PDFBuilderException
	 * @throws BusinessException
	 */
	private void drawLoans(PDFBuilder builder) throws PDFBuilderException, BusinessException
	{
		builder.addParagraph("Ваши кредиты", PDFBuilder.TITLE_PARAGRAPH, PDFBuilder.TITLE_FONT);

		List<PersonLoan> loans = getProfile().getPersonLoans();

		CellStyle cellWithBorders = new CellStyle();
		cellWithBorders.setBorderWidth(PDFTableStyles.TABLE_BORDER_WIDTH); // устанавливаем границы в таблице
		cellWithBorders.setBorderColor(Color.LIGHT_GRAY); // цвет границы

		PDFTableBuilder tableBuilder = null;
		boolean needNewTable = true;
		for(int i = 0; i < loans.size(); i++)
		{
			PersonLoan loan = loans.get(i);

			LoanKindProduct loanKind = loanProductService.getById(loan.getDictionaryLoan());
			Image image = imageService.findById(loanKind.getImageId(), null);
			String comment = loan.getComment() != null ? loan.getComment() : "";
			String name = loan.getName() + " " + comment;

			List<Pair<String, FontStyle>> amount = new ArrayList<Pair<String, FontStyle>>(3);
			amount.add(new Pair<String, FontStyle>("Сумма кредита: ", PDFTableStyles.TEXT_FONT));
			amount.add(new Pair<String, FontStyle>(getStrAmount(loan.getAmount()) + " ", PDFTableStyles.BOLD_FONT));
			amount.add(new Pair<String, FontStyle>(getStrCurrency(loan.getAmount()), PDFTableStyles.TEXT_FONT));

			List<Pair<String, FontStyle>> rate = new ArrayList<Pair<String, FontStyle>>(3);
			rate.add(new Pair<String, FontStyle>("Ставка: ", PDFTableStyles.TEXT_FONT));
			rate.add(new Pair<String, FontStyle>(loan.getRate().toString(), PDFTableStyles.BOLD_FONT));
			rate.add(new Pair<String, FontStyle>("%", PDFTableStyles.TEXT_FONT));

			List<Pair<String, FontStyle>> payment = new ArrayList<Pair<String, FontStyle>>(3);
			payment.add(new Pair<String, FontStyle>("Ежеквартальный платеж: ", PDFTableStyles.TEXT_FONT));
			payment.add(new Pair<String, FontStyle>(StringHelper.getStringInNumberFormat(PfpMathUtils.calcQuarterPayment(loan).toString()), PDFTableStyles.BOLD_FONT));
			payment.add(new Pair<String, FontStyle>(" руб.", PDFTableStyles.TEXT_FONT));

			String open = "Открытие кредита: " + DateHelper.formatDateWithQuarters(loan.getStartDate());
			String end = "Окончание платежей: " + DateHelper.formatDateWithQuarters(loan.getEndDate());

			FontStyle grayFont = new FontStyle(PDFTableStyles.TEXT_FONT);
			grayFont.setColor(Color.GRAY);
			List<Pair<String, FontStyle>> period = new ArrayList<Pair<String, FontStyle>>(3);
			period.add(new Pair<String, FontStyle>("Срок кредита: ", PDFTableStyles.TEXT_FONT));
			period.add(new Pair<String, FontStyle>(Integer.toString(loan.getQuarterDuration()) + " кварталов", PDFTableStyles.TEXT_FONT));
			period.add(new Pair<String, FontStyle>(" (" + loan.getQuarterDuration()*3 + " месяцев)", grayFont));

			if (needNewTable)
			{
				tableBuilder = builder.getTableBuilderInstance(3, resourcePath + PDFBuilder.FONT_NAME, new ParagraphStyle(14f, 0f, 11f, 5f));
				int[] widths = {175, 10, 175}; // ширина столбцов таблицы
				tableBuilder.setWidths(PDFTableBuilder.TABLE_WIDTH_VERT_LIST, widths);
				needNewTable = false;
			}

			PDFTableBuilder loanTableBuilder = builder.getTableBuilderInstance(2, resourcePath + PDFBuilder.FONT_NAME, new ParagraphStyle(14f, 0f, 11f, 5f));
			int[] widths = {50, 125}; // ширина столбцов таблицы
			loanTableBuilder.setWidths(175, widths);

			addImageToCell(loanTableBuilder, image, PDF_PRODUCT_ICON_WIDTH, PDF_PRODUCT_ICON_HEIGHT, 1, 2, CELL_WITHOUT_BORDER_CENTER);
			loanTableBuilder.addValueToCell(name, PDFTableStyles.BOLD_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);
			loanTableBuilder.addValuesToCell(amount, 1, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
			loanTableBuilder.addValuesToCell(rate, 2, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
			loanTableBuilder.addValuesToCell(payment, 2, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
			loanTableBuilder.addValueToCell(open, 2, 1, PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);
			loanTableBuilder.addValueToCell(end, 2, 1, PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);
			loanTableBuilder.addValuesToCell(period, 2, 1, PDFTableStyles.CELL_WITHOUT_BORDER);

			tableBuilder.addTableToCell(loanTableBuilder, cellWithBorders);
			if (i % 2 != 0)
			{
				tableBuilder.addToPage(TableBreakRule.wholeTableInPage);
				needNewTable = true;
			}
			else
			{
				tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
				if (i == loans.size() - 1)
				{
					tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
					tableBuilder.addToPage(TableBreakRule.wholeTableInPage);
				}
			}
		}

		builder.addParagraph("Все расчёты по кредитным продуктам используются исключительно в целях прогноза. " +
				"Сформированный финансовый план не гарантирует получение выбранных кредитных продуктов в ОАО " +
				"«Сбербанк России».", PDFBuilder.BASE_LAST_ON_PAGE_PARAGRAPH, PDFBuilder.TEXT_FONT);
		builder.newPage();
	}

	private void drawPortfolioStartCap(PDFBuilder builder, ChartBuilder chartBuilder, PersonPortfolio portfolio) throws PDFBuilderException, BusinessException
	{
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(2, resourcePath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);
		int[] widths = {70, 290}; // ширина столбцов таблицы
		tableBuilder.setWidths(PDFTableBuilder.TABLE_WIDTH_VERT_LIST, widths);

		tableBuilder.addImageToCurrentRow(resourcePath + portfolio.getType().toString() + "_icon.png", 64, 64, 1, 2, CELL_WITHOUT_BORDER_CENTER);
		tableBuilder.addValueToCell("Портфель «Стартовый капитал»", PDFBuilder.TITLE_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);

		List<Pair<String, FontStyle>> phrases = new ArrayList<Pair<String, FontStyle>>(2);
		phrases.add(new Pair<String, FontStyle>("Полная стоимость портфеля «Стартовый капитал»: ", PDFTableStyles.TEXT_FONT));
		phrases.add(new Pair<String, FontStyle>(getStrAmountWithCurrency(portfolio.getProductSum()), PDFTableStyles.BOLD_FONT));
		tableBuilder.addValuesToCell(phrases, 1, 1, PDFTableStyles.CELL_WITHOUT_BORDER);

		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);

		drawPortfolioProductList(builder, portfolio, "Ниже указаны выбранные Вами продукты для первоначального распределения свободных средств.");

		builder.newPage();
		List<PieChartItem> productList = new ArrayList<PieChartItem>();
		NotWrappedElementBuilder notWrappedElementBuilder = builder.getNotWrappedElementBuilderInstance();
		notWrappedElementBuilder.addParagraph("Распределение средств в портфеле «Стартовый капитал»", PDFBuilder.SUB_TITLE_PARAGRAPH, PDFBuilder.SUB_TITLE_FONT);
		for(Map.Entry<ProductType, Long> entry: portfolio.getProductWeightPercentMap().entrySet())
			if(entry.getValue() > 0)
				productList.add(new PieChartItem(entry.getKey().getDescription(), entry.getValue(), productColor.get(entry.getKey())));

		ChartSettings chartSettings = new ChartSettings(CHART_WIDTH, PIE_CHART_HEIGHT);
		chartSettings.setChartTitle("Рисунок " + ++imgNum + " Портфель «Стартовый капитал»");
		notWrappedElementBuilder.addImage(chartBuilder.generate3DPieChart(productList, chartSettings), PDF_CHART_WIDTH, PDF_PIE_CHART_HEIGHT, Alignment.center);
		notWrappedElementBuilder.addToPage();
	}

	private void drawPortfolioQuartInv(PDFBuilder builder, ChartBuilder chartBuilder, PersonPortfolio portfolio) throws PDFBuilderException, BusinessException
	{
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(2, resourcePath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);
		int[] widths = {70, 290}; // ширина столбцов таблицы
		tableBuilder.setWidths(PDFTableBuilder.TABLE_WIDTH_VERT_LIST, widths);

		tableBuilder.addImageToCurrentRow(resourcePath + portfolio.getType().toString() + "_icon.png", 64, 64, 1, 2, CELL_WITHOUT_BORDER_CENTER);
		tableBuilder.addValueToCell("Портфель «Ежеквартальные вложения»", PDFBuilder.TITLE_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);

		List<Pair<String, FontStyle>> phrases = new ArrayList<Pair<String, FontStyle>>(2);
		phrases.add(new Pair<String, FontStyle>("Полная стоимость портфеля «Ежеквартальные вложения»: ", PDFTableStyles.TEXT_FONT));
		phrases.add(new Pair<String, FontStyle>(getStrAmountWithCurrency(portfolio.getProductSum()), PDFTableStyles.BOLD_FONT));
		tableBuilder.addValuesToCell(phrases, 1, 1, PDFTableStyles.CELL_WITHOUT_BORDER);

		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);

		drawPortfolioProductList(builder, portfolio, "Ниже указаны выбранные Вами продукты для распределения свободных средств на ежеквартальной основе.");

		Money insuranceSum = portfolio.getProductWeightMap().get(ProductType.insurance);
		if (!insuranceSum.isZero())
			builder.addParagraph("Ежеквартально рекомендуем Вам накапливать на Сберегательном счете или вкладе «Управляй» " +
					getStrAmountWithCurrency(insuranceSum) + " Данная сумма необходима Вам для оплаты взносов по страховым продуктам.", PDFBuilder.BASE_LAST_ON_PAGE_PARAGRAPH);

		builder.newPage();
		List<PieChartItem> productList = new ArrayList<PieChartItem>();
		NotWrappedElementBuilder notWrappedElementBuilder = builder.getNotWrappedElementBuilderInstance();
		notWrappedElementBuilder.addParagraph("Распределение средств в портфеле «Ежеквартальные вложения»", PDFBuilder.SUB_TITLE_PARAGRAPH, PDFBuilder.SUB_TITLE_FONT);
		for(Map.Entry<ProductType, Long> entry: portfolio.getProductWeightPercentMap().entrySet())
			if(entry.getValue() > 0)
				productList.add(new PieChartItem(entry.getKey().getDescription(), entry.getValue(), productColor.get(entry.getKey())));

		ChartSettings chartSettings = new ChartSettings(CHART_WIDTH, PIE_CHART_HEIGHT);
		chartSettings.setChartTitle("Рисунок " + ++imgNum + " Портфель «Ежеквартальные вложения»");
		notWrappedElementBuilder.addImage(chartBuilder.generate3DPieChart(productList, chartSettings), PDF_CHART_WIDTH, PDF_PIE_CHART_HEIGHT, Alignment.center);
		notWrappedElementBuilder.addToPage();
	}

	private void futurePortfoliosAmounts(PDFBuilder builder, ChartBuilder chartBuilder, PersonPortfolio portfolioStartCap, PersonPortfolio portfolioQuartInv) throws PDFBuilderException
	{
		builder.addParagraph("Мое благосостояние", PDFBuilder.TITLE_PARAGRAPH, PDFBuilder.TITLE_FONT);

		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(4, resourcePath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);
		int[] widths = {80, 85, 110, 85}; // ширина столбцов таблицы
		tableBuilder.setWidths(PDFTableBuilder.TABLE_WIDTH_VERT_LIST, widths);

		CellStyle cellStyleAlignmentRight = new CellStyle(PDFTableStyles.CELL_WITHOUT_BORDER);
		cellStyleAlignmentRight.setHorizontalAlignment(Alignment.right);

		tableBuilder.addValueToCell("Стартовый капитал:", PDFTableStyles.TEXT_FONT, cellStyleAlignmentRight);
		tableBuilder.addValueToCell(getStrAmountWithCurrency(portfolioStartCap.getProductSum()), PDFBuilder.BOLD_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);

		tableBuilder.addValueToCell("Ежеквартальные вложения:", PDFTableStyles.TEXT_FONT, cellStyleAlignmentRight);
		tableBuilder.addValueToCell(getStrAmountWithCurrency(portfolioQuartInv.getProductSum()), PDFBuilder.BOLD_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);

		tableBuilder.addValueToCell("Доходность:", PDFTableStyles.TEXT_FONT, cellStyleAlignmentRight);
		tableBuilder.addValueToCell(portfolioStartCap.getIncome().setScale(1, RoundingMode.HALF_UP).toString() + "% в год", PDFBuilder.BOLD_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);

		tableBuilder.addValueToCell("Доходность:", PDFTableStyles.TEXT_FONT, cellStyleAlignmentRight);
		tableBuilder.addValueToCell(portfolioQuartInv.getIncome().setScale(1, RoundingMode.HALF_UP).toString() + "% в год", PDFBuilder.BOLD_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);

		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);

		drawFinancePlanChart(builder, chartBuilder, "Рисунок " + ++imgNum + " Мое благосостояние", PDF_CHART_WIDTH, PDF_CHART_HEIGHT);
		builder.setOrientation(DocumentOrientation.HORIZONTAL);
		builder.newPage();
		drawFuturePortfolioSum(builder);
		builder.setOrientation(DocumentOrientation.VERTICAL);
		builder.newPage();

		builder.addParagraph("Данные основаны на ряде приближений, и доходность продуктов, использованная в  " +
				"текущих расчётах, не является гарантированной и может отличаться от будущих результатов. " +
				"Стоимость инвестиционных паев и обезличенных металлических счетов может увеличиваться и " +
				"уменьшаться, результаты инвестирования в прошлом не определяют доходы в будущем, государство " +
				"не гарантирует доходность инвестиций в паевые инвестиционные фонды и обезличенные металлические " +
				"счета. Прежде чем приобрести инвестиционный пай, следует внимательно ознакомиться с правилами " +
				"доверительного управления паевым инвестиционным фондом. Получить подробную информацию о паевых " +
				"инвестиционных фондах и ознакомиться с правилами доверительного управления паевыми " +
				"инвестиционными фондами и иными документами можно по адресу: 123317 Москва, Пресненская наб., " +
				"дом 10. Правилами доверительного управления паевыми инвестиционными фондами предусмотрены надбавки " +
				"(скидки) к (с) расчетной стоимости инвестиционных паев при их выдаче (погашении). Взимание надбавок " +
				"(скидок) уменьшит доходность инвестиций в инвестиционные паи паевого инвестиционного фонда.", PDFBuilder.BASE_JUSTIFIED_PARAGRAPH, PDFBuilder.TEXT_FONT);
	}

	/**
	 * Генерирует таблицу "Распледеление средств в портфеле"
	 * @param builder - билдер
	 * @param portfolio - портфель
	 * @throws PDFBuilderException
	 */
	private void drawPortfolioProductList(PDFBuilder builder, PersonPortfolio portfolio, String headerMessage) throws PDFBuilderException, BusinessException
	{
		List<PortfolioProduct> portfolioProductList = portfolio.getCurrentProductList();
		PortfolioType portfolioType = portfolio.getType();

		CellStyle cellWithBorder = new CellStyle();
		cellWithBorder.setBorderWidth(PDFTableStyles.TABLE_BORDER_WIDTH); // устанавливаем границы в таблице
		cellWithBorder.setBorderColor(Color.LIGHT_GRAY); // цвет границы
		cellWithBorder.setVerticalAlignment(Alignment.top); // выравнивание по вертикали - поверху

		CellStyle cellBorderBottomLeft = new CellStyle(PDFTableStyles.CELL_BORDER_BOTTOM_LEFT);
		cellBorderBottomLeft.setVerticalAlignment(Alignment.top); // выравнивание по вертикали - поверху

		CellStyle cellBorderBottomRight = new CellStyle(PDFTableStyles.CELL_BORDER_BOTTOM_RIGHT);
		cellBorderBottomRight.setVerticalAlignment(Alignment.top); // выравнивание по вертикали - поверху

		CellStyle cellBorderLeft = new CellStyle(PDFTableStyles.CELL_BORDER_LEFT);
		cellBorderLeft.setVerticalAlignment(Alignment.top); // выравнивание по вертикали - поверху

		PDFTableBuilder tableBuilder = null;
		int notComplexCount = 0;
		boolean needNewTable = true;
		boolean needHeaderMessage = true;
		for (PortfolioProduct portfolioProduct : portfolioProductList)
		{
			DictionaryProductType productType = portfolioProduct.getProductType();

			if(!portfolioType.isProductTypeAvailable(productType))
				continue;

			if (needHeaderMessage)
			{
				needHeaderMessage = false;
				builder.addParagraph(headerMessage, PDFBuilder.BASE_PARAGRAPH, PDFBuilder.TEXT_FONT);
			}

			boolean isComplex = productType == DictionaryProductType.COMPLEX_INVESTMENT_FUND_IMA ||
								productType == DictionaryProductType.COMPLEX_INVESTMENT_FUND ||
								productType == DictionaryProductType.COMPLEX_INSURANCE;

			// предыдущий продукт - некомплексный и не добавлен на страницу
			if (notComplexCount % 2 != 0 && isComplex && !needNewTable)
			{
				tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
				tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
				tableBuilder.addToPage(TableBreakRule.wholeTableInPage);
				needNewTable = true;
			}

			if (!isComplex)
				notComplexCount++;

			if (needNewTable)
			{
				tableBuilder = builder.getTableBuilderInstance(3, resourcePath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);
				int[] widths = {175, 10, 175}; // ширина столбцов таблицы
				tableBuilder.setWidths(PDFTableBuilder.TABLE_WIDTH_VERT_LIST, widths);
				needNewTable = false;
			}

			PDFTableBuilder accountTableBuilder = null;
			PDFTableBuilder fundTableBuilder = null;
			PDFTableBuilder imaTableBuilder = null;
			PDFTableBuilder insuranceTableBuilder = null;
			PDFTableBuilder trustManagingTableBuilder = null;
			PDFTableBuilder pensionTableBuilder = null;

			for (BaseProduct baseProduct : portfolioProduct.getBaseProductList())
			{
				switch (baseProduct.getProductType())
				{
					case ACCOUNT:
						if (isComplex)
							accountTableBuilder = createTableWithAccountInComplex(builder, baseProduct, portfolioProduct);
						else
							accountTableBuilder = createTableWithAccount(builder, baseProduct, portfolioProduct);
						break;
					case FUND:
						if (isComplex)
							fundTableBuilder = createTableWithFundInComplex(builder, (InvestmentBaseProduct) baseProduct, portfolioProduct);
						else
							fundTableBuilder = createTableWithFund(builder, (InvestmentBaseProduct) baseProduct, portfolioProduct);
						break;
					case IMA:
						if (isComplex)
							imaTableBuilder = createTableWithIMAInComplex(builder, (InvestmentBaseProduct) baseProduct, portfolioProduct);
						else
							imaTableBuilder = createTableWithIMA(builder, (InvestmentBaseProduct) baseProduct, portfolioProduct);
						break;
					case INSURANCE:
						if (isComplex)
							insuranceTableBuilder = createTableWithInsuranceInComplex(builder, baseProduct, portfolioProduct);
						else
							insuranceTableBuilder = createTableWithInsurance(builder, baseProduct, portfolioProduct);
						break;
					case TRUST_MANAGING:
						trustManagingTableBuilder = createTableWithTrustManaging(builder, (InvestmentBaseProduct) baseProduct, portfolioProduct);
						break;
					case PENSION:
						pensionTableBuilder = createTableWithPension(builder, (PensionBaseProduct) baseProduct, portfolioProduct);
						break;
				}
			}

			if (productType == DictionaryProductType.COMPLEX_INVESTMENT_FUND_IMA)
			{
				tableBuilder.addValueToCell(portfolioProduct.getName(), 3, 1, PDFTableStyles.BOLD_FONT, PDFTableStyles.CELL_BORDER_TOP_SIDES);
				tableBuilder.addTableToCell(accountTableBuilder, cellBorderLeft);
				tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
				tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_BORDER_RIGHT);
				tableBuilder.addTableToCell(imaTableBuilder, cellBorderBottomLeft);
				tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_BORDER_BOTTOM);
				tableBuilder.addTableToCell(fundTableBuilder, cellBorderBottomRight);
			}
			else if (productType == DictionaryProductType.COMPLEX_INVESTMENT_FUND)
			{
				tableBuilder.addValueToCell(portfolioProduct.getName(), 3, 1, PDFTableStyles.BOLD_FONT, PDFTableStyles.CELL_BORDER_TOP_SIDES);
				tableBuilder.addTableToCell(accountTableBuilder, cellBorderBottomLeft);
				tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_BORDER_BOTTOM);
				tableBuilder.addTableToCell(fundTableBuilder, cellBorderBottomRight);
			}
			else if (productType == DictionaryProductType.COMPLEX_INSURANCE)
			{
				tableBuilder.addValueToCell(portfolioProduct.getName(), 3, 1, PDFTableStyles.BOLD_FONT, PDFTableStyles.CELL_BORDER_TOP_SIDES);
				tableBuilder.addTableToCell(insuranceTableBuilder, cellBorderBottomLeft);
				tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_BORDER_BOTTOM);
				tableBuilder.addTableToCell(accountTableBuilder, cellBorderBottomRight);
			}
			else if (productType == DictionaryProductType.ACCOUNT)
			{
				tableBuilder.addTableToCell(accountTableBuilder, cellWithBorder);
			}
			else if (productType == DictionaryProductType.FUND)
			{
				tableBuilder.addTableToCell(fundTableBuilder, cellWithBorder);
			}
			else if (productType == DictionaryProductType.IMA)
			{
				tableBuilder.addTableToCell(imaTableBuilder, cellWithBorder);
			}
			else if (productType == DictionaryProductType.TRUST_MANAGING)
			{
				tableBuilder.addTableToCell(trustManagingTableBuilder, cellWithBorder);
			}
			else if (productType == DictionaryProductType.INSURANCE)
			{
				tableBuilder.addTableToCell(insuranceTableBuilder, cellWithBorder);
			}
			else if (productType == DictionaryProductType.PENSION)
			{
				tableBuilder.addTableToCell(pensionTableBuilder, cellWithBorder);
			}

			// если продукт комплексный - то его полностью в таблице добавляем на страницу
			// если в таблицу добавили 2 некомплексных продукта, то добавляем её на страницу (2 продукта в строке)
			if (isComplex || notComplexCount % 2 == 0)
			{
				tableBuilder.addToPage(TableBreakRule.wholeTableInPage);
				needNewTable = true;
				notComplexCount = 0;
			}
			else
			{
				tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
			}
		}

		// в таблице 1 некомплексных продукт, надо дополнить её и добавить на страницу
		if (notComplexCount % 2 != 0)
		{
			tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
			tableBuilder.addToPage(TableBreakRule.wholeTableInPage);
		}
	}

	private void addCommonFieldsInProductTable(PDFTableBuilder tableBuilder, BaseProduct product, boolean isComplex) throws PDFBuilderException
	{
		tableBuilder.addValueToCell(nameMap.get(product.getProductType()) + " «" + product.getProductName() + "»", PDFTableStyles.BOLD_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);

		List<Pair<String, FontStyle>> amount = new ArrayList<Pair<String, FontStyle>>(3);
		amount.add(new Pair<String, FontStyle>("Сумма: ", PDFTableStyles.TEXT_FONT));
		amount.add(new Pair<String, FontStyle>(getStrAmount(product.getAmount()) + " ", PDFTableStyles.BOLD_FONT));
		amount.add(new Pair<String, FontStyle>(getStrCurrency(product.getAmount()), PDFTableStyles.TEXT_FONT));
		tableBuilder.addValuesToCell(amount, 1, 1, PDFTableStyles.CELL_WITHOUT_BORDER);

		List<Pair<String, FontStyle>> income = new ArrayList<Pair<String, FontStyle>>(3);
		income.add(new Pair<String, FontStyle>("Доходность: ", PDFTableStyles.TEXT_FONT));
		income.add(new Pair<String, FontStyle>(product.getIncome().toString(), PDFTableStyles.BOLD_FONT));
		income.add(new Pair<String, FontStyle>("%", PDFTableStyles.TEXT_FONT));
		if (isComplex)
			tableBuilder.addValuesToCell(income, 1, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
		else
			tableBuilder.addValuesToCell(income, 2, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
	}

	private PDFTableBuilder createTableWithAccount(PDFBuilder builder, BaseProduct product, PortfolioProduct portfolioProduct) throws PDFBuilderException, BusinessException
	{
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(2, resourcePath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);
		int[] accountTableWidths = {50, 130}; // ширина столбцов таблицы
		tableBuilder.setWidths(PDFTableBuilder.TABLE_WIDTH_VERT_LIST/2, accountTableWidths);

		Long imageId = portfolioProduct.getImageId();
		Image image = imageId != null ? imageService.findById(imageId, null) : null;
		if (image != null)
			addImageToCell(tableBuilder, image, PDF_PRODUCT_ICON_WIDTH, PDF_PRODUCT_ICON_HEIGHT, 1, 2, CELL_WITHOUT_BORDER_CENTER);
		else
			tableBuilder.addImageToCurrentRow(resourcePath + "icon_" + product.getProductType() + ".png",
					PDF_PRODUCT_ICON_WIDTH, PDF_PRODUCT_ICON_HEIGHT, 1, 2, CELL_WITHOUT_BORDER_CENTER);

		addCommonFieldsInProductTable(tableBuilder, product, false);

		if (StringHelper.isNotEmpty(product.getDescription()))
			tableBuilder.addValueToCell(product.getDescription(), 2, 1, PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);

		return tableBuilder;
	}

	private PDFTableBuilder createTableWithAccountInComplex(PDFBuilder builder, BaseProduct product, PortfolioProduct portfolioProduct) throws PDFBuilderException, BusinessException
	{
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(2, resourcePath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);
		int[] accountTableWidths = {50, 130}; // ширина столбцов таблицы
		tableBuilder.setWidths(PDFTableBuilder.TABLE_WIDTH_VERT_LIST/2, accountTableWidths);

		Long imageId = portfolioProduct.getImageId();
		Image image = imageId != null ? imageService.findById(imageId, null) : null;
		if (image != null)
			addImageToCell(tableBuilder, image, PDF_PRODUCT_ICON_WIDTH, PDF_PRODUCT_ICON_HEIGHT, 1, 3, CELL_WITHOUT_BORDER_CENTER);
		else
			tableBuilder.addImageToCurrentRow(resourcePath + "icon_" + product.getProductType() + ".png",
					PDF_PRODUCT_ICON_WIDTH, PDF_PRODUCT_ICON_HEIGHT, 1, 3, CELL_WITHOUT_BORDER_CENTER);

		addCommonFieldsInProductTable(tableBuilder, product, true);

		return tableBuilder;
	}

	private PDFTableBuilder createTableWithFund(PDFBuilder builder, InvestmentBaseProduct product, PortfolioProduct portfolioProduct) throws PDFBuilderException, BusinessException
	{
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(2, resourcePath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);
		int[] tableWidths = {50, 130}; // ширина столбцов таблицы
		tableBuilder.setWidths(PDFTableBuilder.TABLE_WIDTH_VERT_LIST/2, tableWidths);

		Long imageId = portfolioProduct.getImageId();
		Image image = imageId != null ? imageService.findById(imageId, null) : null;
		if (image != null)
			addImageToCell(tableBuilder, image, PDF_PRODUCT_ICON_WIDTH, PDF_PRODUCT_ICON_HEIGHT, 1, 2, CELL_WITHOUT_BORDER_CENTER);
		else
			tableBuilder.addImageToCurrentRow(resourcePath + "icon_" + product.getProductType() + ".png",
					PDF_PRODUCT_ICON_WIDTH, PDF_PRODUCT_ICON_HEIGHT, 1, 2, CELL_WITHOUT_BORDER_CENTER);

		addCommonFieldsInProductTable(tableBuilder, product, false);

		if (StringHelper.isNotEmpty(product.getRiskName()))
			tableBuilder.addValueToCell("Риск: " + product.getRiskName(), 2, 1, PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);
		if (StringHelper.isNotEmpty(product.getInvestmentPeriod()))
			tableBuilder.addValueToCell("Горизонт инвестирования: " + product.getInvestmentPeriod(), 2, 1, PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);
		if (StringHelper.isNotEmpty(product.getDescription()))
			tableBuilder.addValueToCell(product.getDescription(), 2, 1, PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);

		return tableBuilder;
	}

	private PDFTableBuilder createTableWithFundInComplex(PDFBuilder builder, InvestmentBaseProduct product, PortfolioProduct portfolioProduct) throws PDFBuilderException, BusinessException
	{
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(2, resourcePath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);
		int[] accountTableWidths = {50, 130}; // ширина столбцов таблицы
		tableBuilder.setWidths(PDFTableBuilder.TABLE_WIDTH_VERT_LIST/2, accountTableWidths);

		Long imageId = portfolioProduct.getImageId();
		Image image = imageId != null ? imageService.findById(imageId, null) : null;
		if (image != null)
			addImageToCell(tableBuilder, image, PDF_PRODUCT_ICON_WIDTH, PDF_PRODUCT_ICON_HEIGHT, 1, 4, CELL_WITHOUT_BORDER_CENTER);
		else
			tableBuilder.addImageToCurrentRow(resourcePath + "icon_" + product.getProductType() + ".png",
					PDF_PRODUCT_ICON_WIDTH, PDF_PRODUCT_ICON_HEIGHT, 1, 4, CELL_WITHOUT_BORDER_CENTER);

		addCommonFieldsInProductTable(tableBuilder, product, true);

		List<String> prodDescription = new ArrayList<String>();
		if (StringHelper.isNotEmpty(product.getRiskName()))
			prodDescription.add("Риск: " + product.getRiskName());
		if (StringHelper.isNotEmpty(product.getInvestmentPeriod()))
			prodDescription.add("Горизонт инвестирования: " + product.getInvestmentPeriod());
		if (!prodDescription.isEmpty())
			tableBuilder.addValuesToCell(prodDescription, 1, 1, PDFTableStyles.TEXT_FONT, CELL_WITHOUT_BORDER_CENTER);

		return tableBuilder;
	}

	private PDFTableBuilder createTableWithIMA(PDFBuilder builder, InvestmentBaseProduct product, PortfolioProduct portfolioProduct) throws PDFBuilderException, BusinessException
	{
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(2, resourcePath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);
		int[] accountTableWidths = {50, 130}; // ширина столбцов таблицы
		tableBuilder.setWidths(PDFTableBuilder.TABLE_WIDTH_VERT_LIST/2, accountTableWidths);

		Long imageId = portfolioProduct.getImageId();
		Image image = imageId != null ? imageService.findById(imageId, null) : null;
		if (image != null)
			addImageToCell(tableBuilder, image, PDF_PRODUCT_ICON_WIDTH, PDF_PRODUCT_ICON_HEIGHT, 1, 2, CELL_WITHOUT_BORDER_CENTER);
		else
			tableBuilder.addImageToCurrentRow(resourcePath + "icon_" + product.getProductType() + ".png",
					PDF_PRODUCT_ICON_WIDTH, PDF_PRODUCT_ICON_HEIGHT, 1, 2, CELL_WITHOUT_BORDER_CENTER);

		addCommonFieldsInProductTable(tableBuilder, product, false);

		if (StringHelper.isNotEmpty(product.getRiskName()))
			tableBuilder.addValueToCell("Риск: " + product.getRiskName(), 2, 1, PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);
		if (StringHelper.isNotEmpty(product.getInvestmentPeriod()))
			tableBuilder.addValueToCell("Горизонт инвестирования: " + product.getInvestmentPeriod(), 2, 1, PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);
		if (StringHelper.isNotEmpty(product.getDescription()))
			tableBuilder.addValueToCell(product.getDescription(), 2, 1, PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);

		return tableBuilder;
	}

	private PDFTableBuilder createTableWithIMAInComplex(PDFBuilder builder, InvestmentBaseProduct product, PortfolioProduct portfolioProduct) throws PDFBuilderException, BusinessException
	{
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(2, resourcePath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);
		int[] accountTableWidths = {50, 130}; // ширина столбцов таблицы
		tableBuilder.setWidths(PDFTableBuilder.TABLE_WIDTH_VERT_LIST/2, accountTableWidths);

		Long imageId = portfolioProduct.getImageId();
		Image image = imageId != null ? imageService.findById(imageId, null) : null;
		if (image != null)
			addImageToCell(tableBuilder, image, PDF_PRODUCT_ICON_WIDTH, PDF_PRODUCT_ICON_HEIGHT, 1, 4, CELL_WITHOUT_BORDER_CENTER);
		else
			tableBuilder.addImageToCurrentRow(resourcePath + "icon_" + product.getProductType() + ".png",
					PDF_PRODUCT_ICON_WIDTH, PDF_PRODUCT_ICON_HEIGHT, 1, 4, CELL_WITHOUT_BORDER_CENTER);

		addCommonFieldsInProductTable(tableBuilder, product, true);

		List<String> prodDescription = new ArrayList<String>();
		if (StringHelper.isNotEmpty(product.getRiskName()))
			prodDescription.add("Риск: " + product.getRiskName());
		if (StringHelper.isNotEmpty(product.getInvestmentPeriod()))
			prodDescription.add("Горизонт инвестирования: " + product.getInvestmentPeriod());
		if (!prodDescription.isEmpty())
			tableBuilder.addValuesToCell(prodDescription, 1, 1, PDFTableStyles.TEXT_FONT, CELL_WITHOUT_BORDER_CENTER);

		return tableBuilder;
	}

	private PDFTableBuilder createTableWithInsurance(PDFBuilder builder, BaseProduct product, PortfolioProduct portfolioProduct) throws PDFBuilderException, BusinessException
	{
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(2, resourcePath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);
		int[] accountTableWidths = {50, 130}; // ширина столбцов таблицы
		tableBuilder.setWidths(PDFTableBuilder.TABLE_WIDTH_VERT_LIST/2, accountTableWidths);

		InsuranceBaseProduct insuranceProduct = (InsuranceBaseProduct) product;
		Long imageId = portfolioProduct.getImageId();
		Image image = imageId != null ? imageService.findById(imageId, null) : null;
		if (image != null)
			addImageToCell(tableBuilder, image, PDF_PRODUCT_ICON_WIDTH, PDF_PRODUCT_ICON_HEIGHT, 1, 2, CELL_WITHOUT_BORDER_CENTER);
		else
			tableBuilder.addImageToCurrentRow(resourcePath + "icon_" + product.getProductType() + ".png",
					PDF_PRODUCT_ICON_WIDTH, PDF_PRODUCT_ICON_HEIGHT, 1, 2, CELL_WITHOUT_BORDER_CENTER);

		addCommonFieldsInProductTable(tableBuilder, product, false);

		tableBuilder.addValueToCell("Страховая компания: " + insuranceProduct.getInsuranceCompanyName(), 2, 1, PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addValueToCell("Периодичность взносов: " + insuranceProduct.getSelectedPeriodName(), 2, 1, PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addValueToCell("Срок программы: " + getYearName(insuranceProduct.getSelectedTermValue()), 2, 1, PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);

		if (StringHelper.isNotEmpty(product.getDescription()))
			tableBuilder.addValueToCell(product.getDescription(), 2, 1, PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);

		return tableBuilder;
	}

	private PDFTableBuilder createTableWithInsuranceInComplex(PDFBuilder builder, BaseProduct product, PortfolioProduct portfolioProduct) throws PDFBuilderException, BusinessException
	{
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(2, resourcePath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);
		int[] accountTableWidths = {50, 130}; // ширина столбцов таблицы
		tableBuilder.setWidths(PDFTableBuilder.TABLE_WIDTH_VERT_LIST/2, accountTableWidths);

		InsuranceBaseProduct insuranceProduct = (InsuranceBaseProduct) product;
		Long imageId = portfolioProduct.getImageId();
		Image image = imageId != null ? imageService.findById(imageId, null) : null;
		if (image != null)
			addImageToCell(tableBuilder, image, PDF_PRODUCT_ICON_WIDTH, PDF_PRODUCT_ICON_HEIGHT, 1, 4, CELL_WITHOUT_BORDER_CENTER);
		else
			tableBuilder.addImageToCurrentRow(resourcePath + "icon_" + product.getProductType() + ".png",
					PDF_PRODUCT_ICON_WIDTH, PDF_PRODUCT_ICON_HEIGHT, 1, 4, CELL_WITHOUT_BORDER_CENTER);

		addCommonFieldsInProductTable(tableBuilder, product, true);

		List<String> prodDescription = new ArrayList<String>();
		prodDescription.add("Страховая компания: " + insuranceProduct.getInsuranceCompanyName());
		prodDescription.add("Периодичность взносов: " + insuranceProduct.getSelectedPeriodName());
		prodDescription.add("Срок программы: " + getYearName(insuranceProduct.getSelectedTermValue()));
		tableBuilder.addValuesToCell(prodDescription, 1, 1, PDFTableStyles.TEXT_FONT, CELL_WITHOUT_BORDER_CENTER);

		return tableBuilder;
	}

	private PDFTableBuilder createTableWithTrustManaging(PDFBuilder builder, InvestmentBaseProduct product, PortfolioProduct portfolioProduct) throws PDFBuilderException, BusinessException
	{
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(2, resourcePath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);
		int[] tableWidths = {50, 130}; // ширина столбцов таблицы
		tableBuilder.setWidths(PDFTableBuilder.TABLE_WIDTH_VERT_LIST/2, tableWidths);

		Long imageId = portfolioProduct.getImageId();
		Image image = imageId != null ? imageService.findById(imageId, null) : null;
		if (image != null)
			addImageToCell(tableBuilder, image, PDF_PRODUCT_ICON_WIDTH, PDF_PRODUCT_ICON_HEIGHT, 1, 2, CELL_WITHOUT_BORDER_CENTER);
		else
			tableBuilder.addImageToCurrentRow(resourcePath + "icon_" + product.getProductType() + ".png",
					PDF_PRODUCT_ICON_WIDTH, PDF_PRODUCT_ICON_HEIGHT, 1, 2, CELL_WITHOUT_BORDER_CENTER);

		addCommonFieldsInProductTable(tableBuilder, product, false);

		if (StringHelper.isNotEmpty(product.getRiskName()))
			tableBuilder.addValueToCell("Риск: " + product.getRiskName(), 2, 1, PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);
		if (StringHelper.isNotEmpty(product.getInvestmentPeriod()))
			tableBuilder.addValueToCell("Горизонт инвестирования: " + product.getInvestmentPeriod(), 2, 1, PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);
		if (StringHelper.isNotEmpty(product.getDescription()))
			tableBuilder.addValueToCell(product.getDescription(), 2, 1, PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);

		return tableBuilder;
	}

	private PDFTableBuilder createTableWithPension(PDFBuilder builder, PensionBaseProduct product, PortfolioProduct portfolioProduct) throws PDFBuilderException, BusinessException
	{
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(2, resourcePath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);
		int[] tableWidths = {50, 130}; // ширина столбцов таблицы
		tableBuilder.setWidths(PDFTableBuilder.TABLE_WIDTH_VERT_LIST/2, tableWidths);

		Long imageId = portfolioProduct.getImageId();
		Image image = imageId != null ? imageService.findById(imageId, null) : null;
		if (image != null)
			addImageToCell(tableBuilder, image, PDF_PRODUCT_ICON_WIDTH, PDF_PRODUCT_ICON_HEIGHT, 1, 2, CELL_WITHOUT_BORDER_CENTER);
		else
			tableBuilder.addImageToCurrentRow(resourcePath + "icon_" + product.getProductType() + ".png",
					PDF_PRODUCT_ICON_WIDTH, PDF_PRODUCT_ICON_HEIGHT, 1, 2, CELL_WITHOUT_BORDER_CENTER);

		addCommonFieldsInProductTable(tableBuilder, product, false);

		if (StringHelper.isNotEmpty(product.getPensionFund()))
			tableBuilder.addValueToCell("Пенсионный фонд: " + product.getPensionFund(), 2, 1, PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addValueToCell("Стартовый взнос: " + getStrAmountWithCurrency(product.getAmount()), 2, 1, PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addValueToCell("Ежеквартальный взнос: " + getStrAmount(product.getQurterlyInvest()), 2, 1, PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addValueToCell("Срок: " + product.getSelectedPeriod() + " кварталов", 2, 1, PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);

		if (StringHelper.isNotEmpty(product.getDescription()))
			tableBuilder.addValueToCell(product.getDescription(), 2, 1, PDFTableStyles.TEXT_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);

		return tableBuilder;
	}

	/**
	 * Отрисовка области для подписи клиента
	 * @throws PDFBuilderException
	 */
	private void drawSignPlace(PDFBuilder builder) throws PDFBuilderException
	{
		PDFTableBuilder sign = builder.getTableBuilderInstance(2, resourcePath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);
		sign.setTableWidthPercentage(100);

		sign.addValueToCell("Я проинформирован о том, что доходность по инвестиционным и страховым продуктам не гарантируется", PDFBuilder.TEXT_FONT, PDFTableStyles.CELL_WITHOUT_BORDER);
		sign.addValueToCell("____________________________ \n (Подпись, ФИО)", PDFTableStyles.TEXT_FONT, CELL_WITHOUT_BORDER_CENTER);

		sign.addToPage(TableBreakRule.wholeTableInPage);
	}

	/**
	 * Преобразует значение к виду: "1 000.00 руб"
	 * @param amount - значение
	 * @return строка
	 */
	private String getStrAmountWithCurrency(Money amount)
	{
		return getStrAmount(amount) + " " + getStrCurrency(amount);
	}

	/**
	 * Преобразует значение к виду: "1 000.00"
	 * @param amount - значение
	 * @return строка
	 */
	private String getStrAmount(Money amount)
	{
		return StringHelper.getStringInNumberFormat(amount.getDecimal().toString());
	}

	private String getStrCurrency(Money amount)
	{
		return CurrencyUtils.getCurrencySign(amount.getCurrency().getCode());
	}

	/**
	 * Преобразует значение к виду: "1 000.00 руб"
	 * @param amount - значение
	 * @return строка
	 */
	private String getStrAmount(BigDecimal amount)
	{
		return StringHelper.getStringInNumberFormat(amount.toString()) + " руб.";
	}

	/**
	 * Склонение слова "год"
	 * @param year - количество лет
	 * @return склоненное слово
	 */
	private String getYearName(long year)
	{
		return year + " " + DeclensionUtils.numeral(year, "", "год", "года", "лет");
	}

	private Money getOutcomeMoney()
	{
		return getProfile().getOutcomeMoney();
	}

	private Color getRGBColor(String colorStr)
	{
		if (StringHelper.isEmpty(colorStr))
			return Color.LIGHT_GRAY;

		Pattern pattern = Pattern.compile("^rgb\\((\\d{1,3}),(\\d{1,3}),(\\d{1,3})\\)");
		Matcher matcher = pattern.matcher(colorStr);
		if (matcher.find())
			return new Color(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)), Integer.parseInt(matcher.group(3)));
		return Color.LIGHT_GRAY;
	}

	/**
	 * Вставляет картинку в ячейку таблицы
	 * @param tableBuilder - билдер
	 * @param image - картинка
	 * @param width - ширина картинки
	 * @param height - высота картинки
	 * @param colSpan - число столбцов, объединяемых данной ячейкой
	 * @param rowSpan - число строк, объединяемых данной ячейкой
	 * @param cellStyle - стиль ячейки
	 * @throws PDFBuilderException
	 */
	private void addImageToCell(PDFTableBuilder tableBuilder, Image image, int width, int height, int colSpan, int rowSpan, CellStyle cellStyle) throws PDFBuilderException, BusinessException
	{
		if (image == null)
			return;

		if (StringHelper.isNotEmpty(image.getExtendImage()))
		{
			tableBuilder.addImageToCurrentRow(image.getExtendImage(), width, height, colSpan, rowSpan, cellStyle);
			return;
		}
		byte[] imgData = imageService.loadImageData(image, null);
		if (ArrayUtils.isNotEmpty(imgData))
		{
			tableBuilder.addImageToCurrentRow(imgData, width, height, colSpan, rowSpan, cellStyle);
			return;
		}

		if (StringHelper.isNotEmpty(image.getInnerImage()))
			tableBuilder.addImageToCurrentRow(resourcePath + image.getInnerImage(), width, height, colSpan, rowSpan, cellStyle);
	}

	/**
	 * Вставляет картинку в ячейку таблицы
	 * @param tableBuilder - билдер
	 * @param image - картинка
	 * @param colSpan - число столбцов, объединяемых данной ячейкой
	 * @param rowSpan - число строк, объединяемых данной ячейкой
	 * @param cellStyle - стиль ячейки
	 * @throws PDFBuilderException
	 */
	private void addImageToCell(PDFTableBuilder tableBuilder, ImageWrapper image, int colSpan, int rowSpan, CellStyle cellStyle) throws PDFBuilderException
	{
		if (image == null)
			return;

		tableBuilder.addImageToCurrentRow(image, colSpan, rowSpan, cellStyle);
	}

	private ImageWrapper getImageWrapper(Image image, int minWidth, int minHeight, int maxWidth, int maxHeight) throws PDFBuilderException, BusinessException
	{
		if (image == null)
			return null;

		if (StringHelper.isNotEmpty(image.getExtendImage()))
			return new ImageWrapper(image.getExtendImage(), minWidth, minHeight, maxWidth, maxHeight);
		byte[] imgData = imageService.loadImageData(image, null);
		if (ArrayUtils.isNotEmpty(imgData))
			return new ImageWrapper(imgData, minWidth, minHeight, maxWidth, maxHeight);

		if (StringHelper.isNotEmpty(image.getInnerImage()))
			return new ImageWrapper(resourcePath + image.getInnerImage(), minWidth, minHeight, maxWidth, maxHeight);

		return null;
	}

}
