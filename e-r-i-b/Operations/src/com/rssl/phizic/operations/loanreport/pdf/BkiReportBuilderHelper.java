package com.rssl.phizic.operations.loanreport.pdf;

import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.bki.CreditBuilderFormatter;
import com.rssl.phizic.business.bki.PersonCreditProfile;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.loanreport.CreditBureauConfig;
import com.rssl.phizic.config.loanreport.CreditBureauConfigStorage;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.chart.DocumentOrientation;
import com.rssl.phizic.utils.pdf.*;
import com.rssl.phizic.utils.pdf.event.AddSubLineEvent;
import com.rssl.phizic.utils.pdf.event.CellSpacingEvent;
import com.rssl.phizic.web.util.MoneyFunctions;
import com.rssl.phizicgate.esberibgate.bki.generated.EnquiryResponseERIB;
import com.rssl.phizicgate.esberibgate.bki.CreditResponseParser;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.List;
import java.util.Queue;
import javax.xml.bind.JAXBException;

/**
 * User: Moshenko
 * Date: 29.10.14
 * Time: 22:59
 * Хелпер для построения отчета pdf БКИ.
 */
public class BkiReportBuilderHelper
{
	private static final String PDF_EMTY ="—";

	//Ключи срабатываения для отрисовки шапак
	private static String TRIG_KEY = "BLUE_HEADER";
	//Толщина линий
	private static final float BORDER_WIDTH_0_8 = 0.8f;
	private static final float BORDER_WIDTH_1_2 = 1.2f;

	private static final Color BLUE_241 = new Color(235, 241, 243);
	private static final Color BLUE_213 = new Color(200, 213, 217);
	private static final Color BLUE_175 = new Color(0, 175, 180);
	private static final Color BLUE_137 = new Color(7, 137, 190);
	private static final Color GRAY_102 = new Color(102, 102, 102);
	private static final Color GRAY_147 = new Color(138, 147, 155);
	private static final Color GRAY_200 = new Color(201, 200, 196);
	private static final Color GRAY_227 = new Color(219, 227, 229);
	private static final Color GRAY_223 = new Color(231, 231, 226);
	private static final Color GREEN_236 = new Color(227, 236, 231);

	private static final FontStyle GRAY_5_5 = new FontStyle(5.5f,FontDecoration.normal,GRAY_147);
	private static final FontStyle GRAY_5_5_102 = new FontStyle(5.5f,FontDecoration.normal,GRAY_102);
	private static final FontStyle GRAY_7 = new FontStyle(7, FontDecoration.normal,GRAY_102);
	private static final FontStyle GRAY_11_5 = new FontStyle(11.5f, FontDecoration.normal,GRAY_147);
	private static final FontStyle GRAY_12 = new FontStyle(12, FontDecoration.normal,GRAY_147);
	private static final FontStyle GRAY_18 = new FontStyle(18, FontDecoration.normal,GRAY_147);
	private static final FontStyle WHITE_13 = new FontStyle(13,FontDecoration.normal,Color.WHITE);
	private static final FontStyle WHITE_42 = new FontStyle(42,FontDecoration.normal,Color.WHITE);
	private static final FontStyle WHITE_11 = new FontStyle(11,FontDecoration.normal,Color.WHITE);
	private static final FontStyle WHITE_9_5 = new FontStyle(9.5f,FontDecoration.normal,Color.WHITE);
	private static final FontStyle BLACK_5_5 = new FontStyle(5.5f,FontDecoration.normal);
	private static final FontStyle BLACK_7 = new FontStyle(7,FontDecoration.normal);
	private static final FontStyle BLACK_B_7 = new FontStyle(7,FontDecoration.bold);
	private static final FontStyle BLACK_8 = new FontStyle(8,FontDecoration.normal);
	private static final FontStyle BLACK_B_8 = new FontStyle(8,FontDecoration.bold);
	private static final FontStyle BLACK_8_5 = new FontStyle(8.5f,FontDecoration.normal);
	private static final FontStyle BLACK_9_5 = new FontStyle(9.5f,FontDecoration.normal);
	private static final FontStyle BLACK_11 = new FontStyle(11,FontDecoration.normal);
	private static final FontStyle BLACK_12 = new FontStyle(12,FontDecoration.normal);
	private static final FontStyle BLACK_B_9_5 = new FontStyle(9.5f,FontDecoration.bold);
	private static final FontStyle BLACK_14_5 = new FontStyle(14.5f,FontDecoration.normal);
	private static final FontStyle BLACK_B_18 = new FontStyle(18,FontDecoration.bold);
	private static final FontStyle BLACK_20 = new FontStyle(20,FontDecoration.normal);
	private static final FontStyle BLACK_23 = new FontStyle(23,FontDecoration.normal);
	private static final FontStyle BLACK_BOLD_9_5 = new FontStyle(9.5f,FontDecoration.bold);
	private static final FontStyle GRAY_8 = new FontStyle(8);
	private static final FontStyle GRAY_6 = new FontStyle(6);
	private static final FontStyle BLACK_BOLD_8 = new FontStyle(8,FontDecoration.bold);
	private static final FontStyle BLUE_8 = new FontStyle(8);
	private static final FontStyle BLUE_30 = new FontStyle(30);
	private static final FontStyle BLUE_37 = new FontStyle(37);
	private static final FontStyle RED_8 = new FontStyle(8,FontDecoration.normal, Color.RED);

	private static final CellStyle AL_LEFT = new CellStyle();
	private static final CellStyle AL_RIGHT = new CellStyle();
	private static final CellStyle AL_CENTER = new CellStyle();
	private static final CellStyle UNDRL_BLACK_AL_LEFT = new CellStyle();
	private static final CellStyle UNDRL_GRAY_AL_LEFT = new CellStyle();
	private static final CellStyle BLUE_AL_LEFT = new CellStyle();
	private static final CellStyle BLUE_AL_CENER = new CellStyle();
	private static final CellStyle GRAY_AL_LEFT = new CellStyle();
	private static final CellStyle BLUE_AL_RIGHT = new CellStyle();
	private static final CellStyle GRAY_AL_RIGHT = new CellStyle();
	private static final CellStyle LEFT_BLUE_UNDRL_GRAY = new CellStyle();
	private static final CellStyle LEFT_BLUE_UNDRL_BLACK = new CellStyle();
	private static final CellStyle CENTER_GRAY_UNDERLINE = new CellStyle();
	private static final CellStyle CENTER_BLACK_UNDERLINE = new CellStyle();
	private static final CellStyle CENTER_GRAY = new CellStyle();
	private static final CellStyle BLUE_UNDRL_BLACK_AL_CENTER = new CellStyle();
	private static final CellStyle BLUE_UNDRL_GRAY_AL_CENTER = new CellStyle();
	public static final CellStyle BORDER_RIGHT_GARAY_AL_LEFT = new CellStyle();
	public static final CellStyle BORDER_LEFT_GARAY_AL_LEFT = new CellStyle();
	public static final CellStyle BORDER_LEFT_BOTTOM_RIGHT_GARAY = new CellStyle();
	public static final CellStyle BORDER_BOTTOM_GARAY = new CellStyle();
	public static final CellStyle WITHOUT_BORDER_AL_LEFT = new CellStyle();
	public static final CellStyle WITHOUT_BORDER_AL_CENTER = new CellStyle();
	public static final CellStyle BORDER_BOTTOM_BLUE = new CellStyle();
	public static final CellStyle GREEN_AL_LEFT = new CellStyle();
	public static final CellStyle GREEN_AL_RIGHT = new CellStyle();
	public static final CellStyle GREEN_AL_CENTER = new CellStyle();
	public static final CellStyle GRAY_227_AL_CENTER = new CellStyle();

	private static final CellSpacingEvent GRAY_4_7 = new CellSpacingEvent(4, GRAY_200, 0.8f, 0, 7);
	private static final CellSpacingEvent GRAY_6_7 = new CellSpacingEvent(6, GRAY_200, 0.8f, 0, 7);
	private static final CellSpacingEvent BLACK_4_7 = new CellSpacingEvent(4, GRAY_102, 0.8f, 0, 7);
	private static final CellSpacingEvent BLACK_6_7 = new CellSpacingEvent(6, GRAY_102, 0.8f, 0, 7);
	private static final CellSpacingEvent GRAY_LEFT_4_7 = new CellSpacingEvent(4, GRAY_200, 0.8f, -1, 7);
	private static final CellSpacingEvent GRAY_LEFT_8_3 = new CellSpacingEvent(8, GRAY_200, 0.8f, -1, 3);
	private static final CellSpacingEvent GRAY_LEFT_12_3 = new CellSpacingEvent(12, GRAY_200, 0.8f, -1, 3);
	private static final CellSpacingEvent GRAY_RIGHT_4_7 = new CellSpacingEvent(4, GRAY_200, 0.8f, 1, 7);
	private static final CellSpacingEvent GRAY_RIGHT_8_3 = new CellSpacingEvent(8, GRAY_200, 0.8f, 1, 3);
	private static final CellSpacingEvent GRAY_RIGHT_12_3 = new CellSpacingEvent(12, GRAY_200, 0.8f, 1, 3);
	private static final AddSubLineEvent GRAY_LINE_7 = new AddSubLineEvent(GRAY_200, 0.8f, 7);
	private static final AddSubLineEvent GRAY_LINE_3 = new AddSubLineEvent(GRAY_200, 0.8f, 3);
	private static final CellSpacingEvent BLACK_LEFT_8_3 = new CellSpacingEvent(8, GRAY_102, 0.8f, -1, 3);
	private static final CellSpacingEvent BLACK_RIGHT_8_3 = new CellSpacingEvent(8, GRAY_102, 0.8f, 1, 3);
	private static final CellSpacingEvent BLACK_RIGHT_12_3 = new CellSpacingEvent(12, GRAY_102, 0.8f, 1, 3);
	private static final AddSubLineEvent BLACK_LINE_3 = new AddSubLineEvent(GRAY_102, 0.8f, 3);
	static
	{
		GRAY_8.setColor(GRAY_147);
		GRAY_6.setColor(GRAY_102);
		BLUE_8.setColor(BLUE_137);
		BLUE_30.setColor(BLUE_137);
		BLUE_37.setColor(BLUE_137);

		AL_LEFT.setHorizontalAlignment(Alignment.left);
		AL_CENTER.setHorizontalAlignment(Alignment.center);
		AL_RIGHT.setHorizontalAlignment(Alignment.right);

		UNDRL_BLACK_AL_LEFT.setHorizontalAlignment(Alignment.left);
		UNDRL_BLACK_AL_LEFT.setBorderWidth(0);
		UNDRL_BLACK_AL_LEFT.setBorderWidthBottom(BORDER_WIDTH_0_8);
		UNDRL_BLACK_AL_LEFT.setBorderColor(GRAY_102);

		UNDRL_GRAY_AL_LEFT.setBorderWidth(0);
		UNDRL_GRAY_AL_LEFT.setBorderWidthBottom(BORDER_WIDTH_0_8);
		UNDRL_GRAY_AL_LEFT.setBorderColor(GRAY_200);

		CENTER_BLACK_UNDERLINE.setHorizontalAlignment(Alignment.center);
		CENTER_BLACK_UNDERLINE.setBorderWidth(0);
		CENTER_BLACK_UNDERLINE.setBorderWidthBottom(BORDER_WIDTH_0_8);
		CENTER_BLACK_UNDERLINE.setBorderColor(GRAY_102);

		CENTER_GRAY_UNDERLINE.setHorizontalAlignment(Alignment.center);
		CENTER_GRAY_UNDERLINE.setBorderWidth(0);
		CENTER_GRAY_UNDERLINE.setBorderWidthBottom(BORDER_WIDTH_0_8);
		CENTER_GRAY_UNDERLINE.setBorderColor(GRAY_200);

		BLUE_AL_LEFT.setHorizontalAlignment(Alignment.left);
		BLUE_AL_LEFT.setBorderWidth(0);
		BLUE_AL_LEFT.setBackgroundColor(BLUE_241);

		BLUE_AL_CENER.setHorizontalAlignment(Alignment.center);
		BLUE_AL_CENER.setBorderWidth(0);
		BLUE_AL_CENER.setBackgroundColor(BLUE_241);

		BLUE_AL_RIGHT.setHorizontalAlignment(Alignment.right);
		BLUE_AL_RIGHT.setBorderWidth(0);
		BLUE_AL_RIGHT.setBackgroundColor(BLUE_241);

		LEFT_BLUE_UNDRL_GRAY.setHorizontalAlignment(Alignment.left);
		LEFT_BLUE_UNDRL_GRAY.setBorderWidth(0);
		LEFT_BLUE_UNDRL_GRAY.setBorderWidthBottom(BORDER_WIDTH_0_8);
		LEFT_BLUE_UNDRL_GRAY.setBorderColor(GRAY_200);
		LEFT_BLUE_UNDRL_GRAY.setBackgroundColor(BLUE_241);

		LEFT_BLUE_UNDRL_BLACK.setHorizontalAlignment(Alignment.left);
		LEFT_BLUE_UNDRL_BLACK.setBorderWidth(0);
		LEFT_BLUE_UNDRL_BLACK.setBorderWidthBottom(BORDER_WIDTH_0_8);
		LEFT_BLUE_UNDRL_BLACK.setBorderColor(GRAY_102);
		LEFT_BLUE_UNDRL_BLACK.setBackgroundColor(BLUE_241);

		BLUE_UNDRL_BLACK_AL_CENTER.setHorizontalAlignment(Alignment.center);
		BLUE_UNDRL_BLACK_AL_CENTER.setBorderWidth(0);
		BLUE_UNDRL_BLACK_AL_CENTER.setBorderWidthBottom(BORDER_WIDTH_0_8);
		BLUE_UNDRL_BLACK_AL_CENTER.setBorderColor(GRAY_102);
		BLUE_UNDRL_BLACK_AL_CENTER.setBackgroundColor(BLUE_241);

		BLUE_UNDRL_GRAY_AL_CENTER.setHorizontalAlignment(Alignment.center);
		BLUE_UNDRL_GRAY_AL_CENTER.setBorderWidth(0);
		BLUE_UNDRL_GRAY_AL_CENTER.setBorderWidthBottom(BORDER_WIDTH_0_8);
		BLUE_UNDRL_GRAY_AL_CENTER.setBorderColor(GRAY_200);
		BLUE_UNDRL_GRAY_AL_CENTER.setBackgroundColor(BLUE_241);

		GRAY_AL_LEFT.setHorizontalAlignment(Alignment.left);
		GRAY_AL_LEFT.setBorderWidth(0);
		GRAY_AL_LEFT.setBackgroundColor(GRAY_223);

		CENTER_GRAY.setHorizontalAlignment(Alignment.center);
		CENTER_GRAY.setBorderWidth(0);
		CENTER_GRAY.setBackgroundColor(GRAY_223);

		GRAY_AL_RIGHT.setHorizontalAlignment(Alignment.right);
		GRAY_AL_RIGHT.setBorderWidth(0);
		GRAY_AL_RIGHT.setBackgroundColor(GRAY_223);

		BORDER_RIGHT_GARAY_AL_LEFT.setHorizontalAlignment(Alignment.left);
		BORDER_RIGHT_GARAY_AL_LEFT.setBorderWidthRight(BORDER_WIDTH_1_2);
		BORDER_RIGHT_GARAY_AL_LEFT.setBorderColor(GRAY_223);

		BORDER_LEFT_GARAY_AL_LEFT.setBorderWidthLeft(BORDER_WIDTH_1_2);
		BORDER_LEFT_GARAY_AL_LEFT.setBorderColor(GRAY_223);
		BORDER_LEFT_GARAY_AL_LEFT.setHorizontalAlignment(Alignment.left);

		BORDER_BOTTOM_GARAY.setBorderWidthBottom(BORDER_WIDTH_1_2);
		BORDER_BOTTOM_GARAY.setBorderColor(GRAY_223);

		BORDER_LEFT_BOTTOM_RIGHT_GARAY.setBorderWidthLeft(BORDER_WIDTH_1_2);
		BORDER_LEFT_BOTTOM_RIGHT_GARAY.setBorderWidthRight(BORDER_WIDTH_1_2);
		BORDER_LEFT_BOTTOM_RIGHT_GARAY.setBorderWidthBottom(BORDER_WIDTH_1_2);
		BORDER_LEFT_BOTTOM_RIGHT_GARAY.setBorderColor(GRAY_223);

		WITHOUT_BORDER_AL_LEFT.setBorderWidth(0);
		WITHOUT_BORDER_AL_LEFT.setHorizontalAlignment(Alignment.left);

		WITHOUT_BORDER_AL_CENTER.setBorderWidth(0);
		WITHOUT_BORDER_AL_CENTER.setHorizontalAlignment(Alignment.center);

		BORDER_BOTTOM_BLUE.setBorderWidth(0);
		BORDER_BOTTOM_BLUE.setBorderWidthBottom(BORDER_WIDTH_1_2);
		BORDER_BOTTOM_BLUE.setBorderColor(BLUE_213);
		BORDER_BOTTOM_BLUE.setBackgroundColor(BLUE_241);

		GREEN_AL_CENTER.setHorizontalAlignment(Alignment.center);
		GREEN_AL_CENTER.setBorderWidth(0);
		GREEN_AL_CENTER.setBackgroundColor(GREEN_236);

		GREEN_AL_RIGHT.setHorizontalAlignment(Alignment.right);
		GREEN_AL_RIGHT.setBorderWidth(0);
		GREEN_AL_RIGHT.setBackgroundColor(GREEN_236);

		GREEN_AL_LEFT.setHorizontalAlignment(Alignment.left);
		GREEN_AL_LEFT.setBorderWidth(0);
		GREEN_AL_LEFT.setBackgroundColor(GREEN_236);

		GRAY_227_AL_CENTER.setHorizontalAlignment(Alignment.center);
		GRAY_227_AL_CENTER.setBorderWidth(0);
		GRAY_227_AL_CENTER.setBackgroundColor(GRAY_227);

	}

	/**
	 * Формирует pdf
	 * @return pdf-файл
	 * @throws com.rssl.phizic.utils.pdf.PDFBuilderException
	 * @throws BusinessException
	 */
	public byte[] createPDF(PersonCreditProfile profile, Person person) throws PDFBuilderException, BusinessException, BusinessLogicException, ParseException
	{
		CreditBureauConfigStorage creditBureauConfigStorage = new CreditBureauConfigStorage();
		CreditBureauConfig config = creditBureauConfigStorage.loadConfig();

		String resoursePath = config.pfdResourcePath;
		EnquiryResponseERIB.Consumers.S s = getS(profile.getReport());

		HeaderFooterBuilder headerFooterBuilder = new HeaderFooterBuilder();
		AddRoundBlueHeaderEvent applayForLoanHeader = new AddRoundBlueHeaderEvent(BLACK_8_5, BLUE_241, 4.8f);
		headerFooterBuilder.addCustomHeaderFooter(applayForLoanHeader);
		headerFooterBuilder.addPageNumberInstance();
		//TODO повторная инициализация биллдера,  иначе при первом запуске не устанавливается фон. Придумать другой путь решения. Исполнитель: Мошенко А.М.
		PDFBuilder builder = PDFBuilder.getInstance(headerFooterBuilder, getMainFontStr(resoursePath), 12, DocumentOrientation.VERTICAL_575_800);
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);
		builder.setBackgroundColor(new BaseColor(BLUE_175));
		builder = PDFBuilder.getInstance(headerFooterBuilder, getMainFontStr(resoursePath), 12, DocumentOrientation.VERTICAL_575_800);
		//титульная страница
		addMain(builder, resoursePath, person);
		builder.newPage();
		//Информационная страница
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(2, getMainFontStr(resoursePath), new ParagraphStyle(10f, 0f, 11f, 0f), 100f);
		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addEmptyValueToCell(GRAY_227_AL_CENTER);
		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addValueToCell("Вы смотрите кредитный отчет - документ, который отражает\n" +
				"Вашу кредитную историю, то есть обобщенную информацию\n" +
				"по кредитным обязательствам и их состоянию на текущий момент.\n\n" +
				"Кредитный отчет содержит детальную информацию\n" +
				"о действующих и ранее выплаченных кредитах, а также\n" +
				"о запросах, которые делали банки и другие организации\n" +
				"для проверки Вашей кредитной истории.",BLACK_8_5, AL_LEFT);

		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);
		tableBuilder = builder.getTableBuilderInstance(2, getMainFontStr(resoursePath), PDFTableStyles.BASE_PARAGRAPH);
		tableBuilder.addImageToCurrentRow(resoursePath + "\\bki-logo.png", 98, 62, PDFTableStyles.CELL_WITHOUT_BORDER);
		List<Chunk> paragraphs = new ArrayList<Chunk>();
		paragraphs.add(builder.textString("Отчет предоставлен Объединненым кредитным бюро\n\n", BLACK_BOLD_9_5));
		paragraphs.add(builder.textString(config.okbAddress + "\n\n", BLACK_8_5));
		paragraphs.add(builder.textString(config.okbPhone, BLACK_14_5));
		tableBuilder.addElementsToCell(paragraphs, 1, 1, AL_LEFT);
		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);
		builder.addEmptyParagraph(PDFBuilder.LONG_PARAGRAPH);
		builder.addPhrase("\n\n");
		tableBuilder = builder.getTableBuilderInstance(3, getMainFontStr(resoursePath), new ParagraphStyle(0f, 0f, 0f, 0f));
		tableBuilder.addValueToCell("©2014 Закрытое акционерное общество “Объединённое кредитное бюро”", 2, 1, GRAY_7, AL_LEFT);
		tableBuilder.addImageToCurrentRow(resoursePath + "\\sbol-logo.gif", 45, 15, AL_RIGHT);
		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);
		builder.newPage();
		//Содержание
		LinkedHashMap<String, Integer> contents = new LinkedHashMap<String, Integer>();
		if (s!=null)
		{
		//«Информация по результатам поиска (заявитель 1из 1)»
			addSearchInfoBlock(builder, getMainFontStr(resoursePath), s.getSummary());
			builder.newPage();

			int creditRatingPage = applayForLoanHeader.getRecordCount()-1;
			//Кредитный рейтинг
			addCreditRatingBlock(builder, resoursePath, s);
			builder.newPage();
			int whoInterestsPage = applayForLoanHeader.getRecordCount()-1;
			//Кто интересовался
			addWhoInterestBlock(builder, resoursePath, s);
			builder.newPage();
			//Кто интересовался. Заявления
			addApplyForLoan(builder, resoursePath, s);
			builder.newPage();
			int creditAndCreditCardsPage = applayForLoanHeader.getRecordCount()-1;
			builder.addKeyword("");
			//Кредиты и кредитные карты
			addLoanBlock(builder, resoursePath, s);
			builder.newPage();
			//Кредиты и кредитные карты. Блок с общими данными по конкретному кредиту
			addCurrentLoanBlock(builder, resoursePath, s);
			contents.put("Кредитный рейтинг", creditRatingPage);
			contents.put("Кто интересовался кредитной историей", whoInterestsPage);
			contents.put("Кредиты и кредитные карты", creditAndCreditCardsPage);
		}
		builder.newPage();
		builder.addKeyword("");
		//
		tableBuilder = builder.getTableBuilderInstance(2, getMainFontStr(resoursePath), new ParagraphStyle(10f, 0f, 11f, 0f), 100f);
		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addEmptyValueToCell(GRAY_227_AL_CENTER);

		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);

		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);
		tableBuilder = builder.getTableBuilderInstance(2, getMainFontStr(resoursePath), PDFTableStyles.BASE_PARAGRAPH);
		tableBuilder.addImageToCurrentRow(resoursePath + "\\bki-logo.png", 98, 62, PDFTableStyles.CELL_WITHOUT_BORDER);
		paragraphs = new ArrayList<Chunk>();
		paragraphs.add(builder.textString("Отчет предоставлен Объединненым кредитным бюро\n\n", BLACK_BOLD_9_5));
		paragraphs.add(builder.textString(config.okbAddress + "\n\n", BLACK_8_5));
		paragraphs.add(builder.textString(config.okbPhone, BLACK_14_5));
		tableBuilder.addElementsToCell(paragraphs, 1, 1, AL_LEFT);
		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);

		builder.addEmptyParagraph(PDFBuilder.LONG_PARAGRAPH);
		builder.addEmptyParagraph(PDFBuilder.LONG_PARAGRAPH);
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);
		tableBuilder = builder.getTableBuilderInstance(1, getMainFontStr(resoursePath), new ParagraphStyle(10f, 0f, 5f, 0f));
		tableBuilder.addEmptyValueToCell(1, 4, PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addImageToCurrentRow(resoursePath + "\\sbol-logo.gif", 45, 15, AL_LEFT);
		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);

		BaseFont baseFont = PDFBuilder.getBaseFont(getMainFontStr(resoursePath));
		Font font = new Font(baseFont, 12f);
		return buildContents(builder.build(), contents, font);
	}

	/**
	 * Стоит раздел «Информация по результатам поиска (заявитель 1из 1)»
	 * @param builder PDF билтдер
	 */
	private void addSearchInfoBlock(PDFBuilder builder,String fontPath,EnquiryResponseERIB.Consumers.S.Summary summary) throws PDFBuilderException, BusinessException
	{
		builder.addParagraph("Информация по результатам поиска (Заявитель 1 из 1)", PDFTableStyles.BASE_PARAGRAPH ,BLACK_20);
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);

		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(6, fontPath, new ParagraphStyle(10f, 0f, 11f, 0f),100f);

		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addValueToCell("ВЛАДЕЛЕЦ", BLACK_8, AL_CENTER);
		tableBuilder.addValueToCell("СОВЛАДЕЛЕЦ",BLACK_8, AL_CENTER);
		tableBuilder.addValueToCell("ГАРАНТ",BLACK_8, AL_CENTER);
		tableBuilder.addValueToCell("ПОРУЧИТЕЛЬ",BLACK_8, AL_CENTER);
		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);

		Long owner3 = summary.getCAPSLast3MonthsOwner() != null  ? summary.getCAPSLast3MonthsOwner() : 0L;
		Long joint3 = summary.getCAPSLast3MonthsJoint() != null  ? summary.getCAPSLast3MonthsJoint() : 0L;
		Long guarantor3 = summary.getCAPSLast3MonthsGuarantor() != null  ? summary.getCAPSLast3MonthsGuarantor() : 0L;
		Long referee3 = summary.getCAPSLast3MonthsReferee() != null  ? summary.getCAPSLast3MonthsReferee() : 0L;

		Long owner6 = summary.getCAPSLast6MonthsOwner() != null  ? summary.getCAPSLast6MonthsOwner() : 0L;
		Long joint6 = summary.getCAPSLast6MonthsJoint() != null  ? summary.getCAPSLast6MonthsJoint() : 0L;
		Long guarantor6 = summary.getCAPSLast6MonthsGuarantor() != null  ? summary.getCAPSLast6MonthsGuarantor() : 0L;
		Long referee6 = summary.getCAPSLast6MonthsReferee() != null  ? summary.getCAPSLast6MonthsReferee() : 0L;

		Long owner12 = summary.getCAPSLast12MonthsOwner() != null  ? summary.getCAPSLast12MonthsOwner() : 0L;
		Long joint12 = summary.getCAPSLast12MonthsJoint() != null  ? summary.getCAPSLast12MonthsJoint() : 0L;
		Long guarantor12 = summary.getCAPSLast12MonthsGuarantor() != null  ? summary.getCAPSLast12MonthsGuarantor() : 0L;
		Long referee12 = summary.getCAPSLast12MonthsReferee() != null  ? summary.getCAPSLast12MonthsReferee() : 0L;

		//Количество доступных подписчику записей CAIS
		String CAPSOwnerRecip = summary.getCAPSRecordsOwner() != null  ? summary.getCAPSRecordsOwner().toString() : "0";
		String CAPSJointRecip = summary.getCAPSRecordsJoint() != null  ? summary.getCAPSRecordsJoint().toString() : "0";
		String CAPSGuarantorRecip = summary.getCAPSRecordsGuarantor() != null  ? summary.getCAPSRecordsGuarantor().toString() : "0";
		String CAPSRefereeRecip = summary.getCAPSRecordsReferee() != null  ? summary.getCAPSRecordsReferee().toString() : "0";

		//Количество записей CAIS в Бюро
		String CAPSOwner = String.valueOf(summary.getCAPSRecordsOwnerBeforeFilter());
		String CAPSJoint = summary.getCAPSRecordsJointBeforeFilter() != null  ? summary.getCAPSRecordsJointBeforeFilter().toString() : "0";
		String CAPSGuarantor = summary.getCAPSRecordsGuarantorBeforeFilter() != null  ? summary.getCAPSRecordsGuarantorBeforeFilter().toString() : "0";
		String CAPSReferee = summary.getCAPSRecordsRefereeBeforeFilter() != null  ? summary.getCAPSRecordsRefereeBeforeFilter().toString() : "0";

		tableBuilder = builder.getTableBuilderInstance(6,fontPath, new ParagraphStyle(10f, 0f, 11f, 0f),100f);

		tableBuilder.addValueToCell(" Запросы в бюро (CAPS)",2 , 1, BLACK_BOLD_9_5, AL_LEFT, BLACK_4_7);
		tableBuilder.addValueToCell(CAPSOwnerRecip + "(" + CAPSOwner + ")" , BLACK_BOLD_9_5, AL_CENTER, BLACK_6_7);
		tableBuilder.addValueToCell(CAPSJointRecip + "(" + CAPSJoint + ")", BLACK_BOLD_9_5, AL_CENTER, BLACK_6_7);
		tableBuilder.addValueToCell(CAPSGuarantorRecip + "(" + CAPSGuarantor + ")", BLACK_BOLD_9_5, AL_CENTER, BLACK_6_7);
		tableBuilder.addValueToCell(CAPSRefereeRecip + "(" + CAPSReferee + ")", BLACK_BOLD_9_5, AL_CENTER, BLACK_6_7);

		tableBuilder.addValueToCell(" Последние 3 мес",2 , 1, BLACK_BOLD_8, AL_LEFT, GRAY_4_7);
		tableBuilder.addValueToCell(owner3.toString(), BLACK_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell(joint3.toString(), BLACK_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell(guarantor3.toString(), BLACK_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell(referee3.toString(), BLACK_8, AL_CENTER, GRAY_6_7);

		tableBuilder.addValueToCell(" Последние 6 мес",2 , 1, BLACK_BOLD_8, AL_LEFT, GRAY_4_7);
		tableBuilder.addValueToCell(owner6.toString(), BLACK_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell(joint6.toString(), BLACK_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell(guarantor6.toString(), BLACK_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell(referee6.toString(), BLACK_8, AL_CENTER, GRAY_6_7);

		tableBuilder.addValueToCell(" Последние 12 мес",2 , 1, BLACK_BOLD_8, AL_LEFT);
		tableBuilder.addValueToCell(owner12.toString(), BLACK_8, AL_CENTER);
		tableBuilder.addValueToCell(joint12.toString(), BLACK_8, AL_CENTER);
		tableBuilder.addValueToCell(guarantor12.toString(), BLACK_8, AL_CENTER);
		tableBuilder.addValueToCell(referee12.toString(), BLACK_8, AL_CENTER);

		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);

		tableBuilder = builder.getTableBuilderInstance(6, fontPath, new ParagraphStyle(10f, 0f, 11f, 0f),100f);

		//Количество доступных подписчику записей CAIS
		String CAISOwnerRecip = summary.getCAISRecordsOwnerRecip() != null  ? summary.getCAISRecordsOwnerRecip().toString() : "0";
		String CAISJointRecip = summary.getCAISRecordsJointRecip() != null  ? summary.getCAISRecordsJointRecip().toString() : "0";
		String CAISGuarantorRecip = summary.getCAISRecordsGuarantorRecip() != null  ? summary.getCAISRecordsGuarantorRecip().toString() : "0";
		String CAISRefereeRecip = summary.getCAISRecordsRefereeRecip() != null  ? summary.getCAISRecordsRefereeRecip().toString() : "0";

		//Количество записей CAIS в Бюро
		String CAISOwner = String.valueOf(summary.getCAISRecordsOwner());
		String CAISJoint = summary.getCAISRecordsJoint() != null  ? summary.getCAISRecordsJoint().toString() : "0";
		String CAISGuarantor = summary.getCAISRecordsGuarantor() != null  ? summary.getCAISRecordsGuarantor().toString() : "0";
		String CAISReferee = summary.getCAISRecordsReferee() != null  ? summary.getCAISRecordsReferee().toString() : "0";

		tableBuilder.addValueToCell(" Кредиты и кредитные карты (CAIS)", 2, 1, BLACK_BOLD_9_5, AL_LEFT, BLACK_4_7);
		tableBuilder.addValueToCell(CAISOwnerRecip + "(" + CAISOwner + ")", BLACK_BOLD_9_5, AL_CENTER, BLACK_6_7);
		tableBuilder.addValueToCell(CAISJointRecip + "(" + CAISJoint + ")", BLACK_BOLD_9_5, AL_CENTER, BLACK_6_7);
		tableBuilder.addValueToCell(CAISGuarantorRecip + "(" + CAISGuarantor + ")", BLACK_BOLD_9_5, AL_CENTER, BLACK_6_7);
		tableBuilder.addValueToCell(CAISRefereeRecip + "(" + CAISReferee + ")", BLACK_BOLD_9_5, AL_CENTER, BLACK_6_7);

		tableBuilder.addValueToCell(" Текущий худший платежный статус",2 , 1, BLACK_BOLD_8, AL_LEFT, GRAY_4_7);
		tableBuilder.addValueToCell(CreditBuilderFormatter.getPaymentState(summary.getWorstCurrentPayStatusOwner(), ""), BLACK_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell(CreditBuilderFormatter.getPaymentState(summary.getWorstCurrentPayStatusJoint(), ""), BLACK_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell(CreditBuilderFormatter.getPaymentState(summary.getWorstCurrentPayStatusGuarantor(), ""), BLACK_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell(CreditBuilderFormatter.getPaymentState(summary.getWorstCurrentPayStatusReferee(), ""), BLACK_8, AL_CENTER, GRAY_6_7);

		tableBuilder.addValueToCell(" Исторический худший платежный статус",2 , 1, BLACK_BOLD_8, AL_LEFT);
		tableBuilder.addValueToCell(CreditBuilderFormatter.getPaymentState(summary.getWorstEverPayStatusOwner(), ""), BLACK_8, AL_CENTER);
		tableBuilder.addValueToCell(CreditBuilderFormatter.getPaymentState(summary.getWorstEverPayStatusJoint(), ""), BLACK_8, AL_CENTER);
		tableBuilder.addValueToCell(CreditBuilderFormatter.getPaymentState(summary.getWorstEverPayStatusGuarantor(), ""), BLACK_8, AL_CENTER);
		tableBuilder.addValueToCell(CreditBuilderFormatter.getPaymentState(summary.getWorstEverPayStatusReferee(), ""), BLACK_8, AL_CENTER);

		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);

		tableBuilder = builder.getTableBuilderInstance(12, fontPath, new ParagraphStyle(10f, 0f, 11f, 0f),100f);

		tableBuilder.addValueToCell(" Финансовое обязательство",4 , 1, BLACK_BOLD_9_5, AL_LEFT, GRAY_4_7);
		tableBuilder.addValueToCell(" ТЕКУЩИЙ", 4, 1, BLACK_8, AL_CENTER, BLACK_6_7);
		tableBuilder.addValueToCell(" ПОТЕНЦИАЛЬНЫЙ", 4, 1, BLACK_8, AL_CENTER, BLACK_6_7);

		String str1 = " Непогашенный\n остаток";
		String str2 = " Размер\n ежемесячного взноса";
		tableBuilder.addEmptyValueToCell(4,1,PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addValueToCell(str1, 2, 1, BLACK_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell(str2, 2, 1, BLACK_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell(str1, 2, 1, BLACK_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell(str2, 2, 1, BLACK_8, AL_CENTER, GRAY_6_7);

		//Непогашееннй остаток(Текушщий Владелец)
		String outTotBalanceOwner = summary.getTotalOutstandingBalanceOwner() != null ? summary.getTotalOutstandingBalanceOwner().toString() : "";
		//Размере eжемесячного взноса (Текушщий Владелец)
		String monthTotBalanceOwner = summary.getTotalMonthlyInstalmentsOwner()!= null ? summary.getTotalMonthlyInstalmentsOwner().toString() : "";
		//Непогашееннй остаток(Потенциальный Владелец)
		String outPotBalanceOwner = summary.getPotentialOutstandingBalanceOwner() != null ? summary.getPotentialOutstandingBalanceOwner().toString() : "";
		//Размере eжемесячного взноса (Потенциальный Владелец)
		String monthPotBalanceOwner = summary.getPotentialMonthlyInstalmentsOwner()!= null ? summary.getPotentialMonthlyInstalmentsOwner().toString() : "";

		tableBuilder.addValueToCell(" Владелец",4 , 1, BLACK_BOLD_8, AL_LEFT, GRAY_4_7);
		tableBuilder.addValueToCell(MoneyFunctions.getFormatAmountString(outTotBalanceOwner),2 , 1, BLACK_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell(MoneyFunctions.getFormatAmountString(monthTotBalanceOwner),2 , 1, BLACK_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell(MoneyFunctions.getFormatAmountString(outPotBalanceOwner),2 , 1, BLACK_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell(MoneyFunctions.getFormatAmountString(monthPotBalanceOwner),2 , 1, BLACK_8, AL_CENTER, GRAY_6_7);

		//Непогашееннй остаток(Текушщий Совладелец)
		String outTotBalanceAllButOwner = summary.getTotalOutstandingBalanceAllButOwner() != null ? summary.getTotalOutstandingBalanceAllButOwner().toString() : "";
		//Размере eжемесячного взноса (Текушщий Совладелец)
		String monthTotBalanceAllButOwner = summary.getTotalMonthlyInstalmentsAllButOwner()!= null ? summary.getTotalMonthlyInstalmentsAllButOwner().toString() : "";
		//Непогашееннй остаток(Потенциальный Совладелец)
		String outPotBalanceAllButOwner = summary.getPotentialOutstandingBalanceAllButOwner() != null ? summary.getPotentialOutstandingBalanceAllButOwner().toString() : "";
		//Размере eжемесячного взноса (Потенциальный Совладелец)
		String monthPotBalanceAllButOwner = summary.getPotentialMonthlyInstalmentsAllButOwner()!= null ? summary.getPotentialMonthlyInstalmentsAllButOwner().toString() : "";

		summary.getTotalOutstandingBalanceAllButOwner();
		summary.getTotalMonthlyInstalmentsAllButOwner();
		tableBuilder.addValueToCell("Совладелец, гарант и получатель", 4, 1, BLACK_BOLD_8, AL_LEFT);
		tableBuilder.addValueToCell(MoneyFunctions.getFormatAmountString(outTotBalanceAllButOwner), 2, 1, BLACK_8, AL_CENTER);
		tableBuilder.addValueToCell(MoneyFunctions.getFormatAmountString(monthTotBalanceAllButOwner), 2, 1, BLACK_8, AL_CENTER);
		tableBuilder.addValueToCell(MoneyFunctions.getFormatAmountString(outPotBalanceAllButOwner), 2, 1, BLACK_8, AL_CENTER);
		tableBuilder.addValueToCell(MoneyFunctions.getFormatAmountString(monthPotBalanceAllButOwner), 2, 1, BLACK_8, AL_CENTER);

		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);

		tableBuilder = builder.getTableBuilderInstance(10, fontPath, new ParagraphStyle(10f, 0f, 11f, 0f),100f);
		tableBuilder.addValueToCell(" Право собственности", 4, 1, BLACK_BOLD_9_5, AL_LEFT, BLACK_6_7);
		tableBuilder.addValueToCell(" КОЛИЧЕСТВО ИСПОЛНЯЮЩИХ ПОЛЬЗОВАТЕЛЕЙ", 6, 1, BLACK_9_5, AL_CENTER, BLACK_6_7);

		tableBuilder.addEmptyValueToCell(4,1,PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addValueToCell("1", BLACK_BOLD_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell("2", BLACK_BOLD_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell("3", BLACK_BOLD_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell("4", BLACK_BOLD_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell("5", BLACK_BOLD_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell("Более 5", BLACK_BOLD_8, AL_CENTER, GRAY_6_7);

		//Право собственности (CAPS)
		String CAPSDist1 = summary.getCAPSDistribution1() != null  ? summary.getCAPSDistribution1().toString() : "0";
		String CAPSDist2 = summary.getCAPSDistribution2() != null  ? summary.getCAPSDistribution2().toString() : "0";
		String CAPSDist3 = summary.getCAPSDistribution3() != null  ? summary.getCAPSDistribution3().toString() : "0";
		String CAPSDist4 = summary.getCAPSDistribution4() != null  ? summary.getCAPSDistribution4().toString() : "0";
		String CAPSDist5 = summary.getCAPSDistribution5() != null  ? summary.getCAPSDistribution5().toString() : "0";
		String CAPSDist5Pls = summary.getCAPSDistribution5Plus() != null  ? summary.getCAPSDistribution5Plus().toString() : "0";

		tableBuilder.addValueToCell(" Запросы в бюро CAPS",4 , 1, BLACK_BOLD_8, AL_LEFT, GRAY_4_7);
		tableBuilder.addValueToCell(CAPSDist1, BLACK_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell(CAPSDist2, BLACK_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell(CAPSDist3, BLACK_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell(CAPSDist4, BLACK_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell(CAPSDist5, BLACK_8, AL_CENTER, GRAY_6_7);
		tableBuilder.addValueToCell(CAPSDist5Pls, BLACK_8, AL_CENTER, GRAY_6_7);

		//Право собственности (CIAS)
		String CAISDist1 = summary.getCAISDistribution1() != null  ? summary.getCAISDistribution1().toString() : "0";
		String CAISDist2 = summary.getCAISDistribution2() != null  ? summary.getCAISDistribution2().toString() : "0";
		String CAISDist3 = summary.getCAISDistribution3() != null  ? summary.getCAISDistribution3().toString() : "0";
		String CAISDist4 = summary.getCAISDistribution4() != null  ? summary.getCAISDistribution4().toString() : "0";
		String CAISDist5 = summary.getCAISDistribution5() != null  ? summary.getCAISDistribution5().toString() : "0";
		String CAISDist5Pls = summary.getCAISDistribution5Plus() != null  ? summary.getCAISDistribution5Plus().toString() : "0";

		tableBuilder.addValueToCell(" Кредиты и кредитные карты (СAIS)",4 , 1, BLACK_BOLD_8, AL_LEFT);
		tableBuilder.addValueToCell(CAISDist1, BLACK_8, AL_CENTER);
		tableBuilder.addValueToCell(CAISDist2, BLACK_8, AL_CENTER);
		tableBuilder.addValueToCell(CAISDist3, BLACK_8, AL_CENTER);
		tableBuilder.addValueToCell(CAISDist4, BLACK_8, AL_CENTER);
		tableBuilder.addValueToCell(CAISDist5, BLACK_8, AL_CENTER);
		tableBuilder.addValueToCell(CAISDist5Pls, BLACK_8, AL_CENTER);
		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);
	}

	private void addCreditRatingBlock(PDFBuilder builder, String resPath, EnquiryResponseERIB.Consumers.S s) throws PDFBuilderException, BusinessException
	{
		String fontPath = getMainFontStr(resPath);
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(8, fontPath, new ParagraphStyle(15f, 0f, 11f, 0f),100f);
		tableBuilder.addImageToCurrentRow(resPath + "\\battery.gif", 40, 45, 1, 2, AL_CENTER);
		tableBuilder.addValueToCell("СКОРИНГ БЮРО", 7, 1, BLUE_8, AL_LEFT);

		tableBuilder.addValueToCell("Кредитный рейтинг", 7, 1, BLUE_37, AL_LEFT);
		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);

		tableBuilder.addValueToCell("Показывает информацию, насколько хороша Ваша кредитная\n" +
				"история и требуется ли её улучшение. Если Вы вносите все платежи\n" +
				"по кредитам вовремя и не имеете слишком много открытых\n" +
				"кредитов, то Ваша кредитный рейтинг будет высоким", 7, 1, GRAY_11_5, AL_LEFT);

		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);

		tableBuilder = builder.getTableBuilderInstance(2, fontPath, new ParagraphStyle(10f, 0f, 11f, 0f),100f);
		tableBuilder.addValueToCell("Статистические показатели", 2, 1, BLACK_B_18, UNDRL_BLACK_AL_LEFT);
		EnquiryResponseERIB.Consumers.S.Summary summary = s.getSummary();
		tableBuilder.addValueToCell("Текущий худший платежный статус",  BLACK_BOLD_8, UNDRL_GRAY_AL_LEFT);
		tableBuilder.addValueToCell(CreditBuilderFormatter.getPaymentState(summary.getWorstCurrentPayStatusOwner(), ""), RED_8, UNDRL_GRAY_AL_LEFT);
		tableBuilder.addValueToCell("Исторический худший платежный статус",  BLACK_BOLD_8, UNDRL_GRAY_AL_LEFT);
		tableBuilder.addValueToCell(CreditBuilderFormatter.getPaymentState(summary.getWorstEverPayStatusOwner(), ""), RED_8, UNDRL_GRAY_AL_LEFT);
		String outTotBalanceOwner = summary.getTotalOutstandingBalanceOwner() != null ? summary.getTotalOutstandingBalanceOwner().toString() : "";
		tableBuilder.addValueToCell("Общая сумма задолженности",  BLACK_BOLD_8, UNDRL_GRAY_AL_LEFT);
		tableBuilder.addValueToCell(MoneyFunctions.getFormatAmountString(outTotBalanceOwner),  BLACK_8, UNDRL_GRAY_AL_LEFT);
		String monthTotBalanceOwner = summary.getTotalMonthlyInstalmentsOwner()!= null ? summary.getTotalMonthlyInstalmentsOwner().toString() : "";
		tableBuilder.addValueToCell("Суммарный размер ежемесячного взноса",  BLACK_BOLD_8, UNDRL_GRAY_AL_LEFT);
		tableBuilder.addValueToCell(MoneyFunctions.getFormatAmountString(monthTotBalanceOwner),  BLACK_8, UNDRL_GRAY_AL_LEFT);
		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);

		tableBuilder = builder.getTableBuilderInstance(11, fontPath, new ParagraphStyle(10f, 0f, 11f, 0f),100f);
		tableBuilder.addValueToCell("Скоринг бюро", 11, 1, BLACK_B_18, UNDRL_BLACK_AL_LEFT);
		tableBuilder.addEmptyValueToCell(11, 1, PDFTableStyles.CELL_WITHOUT_BORDER);

		tableBuilder.addValueToCell("Количество баллов", 2, 1, BLACK_BOLD_8, AL_LEFT, GRAY_RIGHT_4_7);
		tableBuilder.addValueToCell("Ниже:560", BLACK_8, AL_LEFT, GRAY_LEFT_4_7);
		tableBuilder.addValueToCell("561:640", BLACK_8, AL_CENTER, GRAY_LINE_7);
		tableBuilder.addValueToCell("641:720", BLACK_8, AL_CENTER, GRAY_RIGHT_4_7);
		tableBuilder.addValueToCell("721:800", BLACK_8, AL_CENTER, GRAY_LEFT_4_7);
		tableBuilder.addValueToCell("801:880", BLACK_8, AL_CENTER, GRAY_LINE_7);
		tableBuilder.addValueToCell("881:960", BLACK_8, AL_CENTER, GRAY_RIGHT_4_7);
		tableBuilder.addValueToCell("961:1040", BLACK_8, AL_CENTER, GRAY_LEFT_4_7);
		tableBuilder.addValueToCell("1041:1120", BLACK_8, AL_CENTER, GRAY_LINE_7);
		tableBuilder.addValueToCell("1121:Выше", BLACK_8, AL_CENTER, GRAY_LEFT_4_7);

		tableBuilder.addValueToCell("Рисковый индикатор", 2, 1, BLACK_BOLD_8, AL_LEFT, GRAY_RIGHT_4_7);
		tableBuilder.addValueToCell("1", BLACK_8, AL_CENTER, GRAY_LEFT_4_7);
		tableBuilder.addValueToCell("1", BLACK_8, AL_CENTER, GRAY_LINE_7);
		tableBuilder.addValueToCell("2", BLACK_8, AL_CENTER, GRAY_RIGHT_4_7);
		tableBuilder.addValueToCell("3", BLACK_8, AL_CENTER, GRAY_LEFT_4_7);
		tableBuilder.addValueToCell("4", BLACK_8, AL_CENTER, GRAY_LINE_7);
		tableBuilder.addValueToCell("4", BLACK_8, AL_CENTER, GRAY_RIGHT_4_7);
		tableBuilder.addValueToCell("5", BLACK_8, AL_CENTER, GRAY_LEFT_4_7);
		tableBuilder.addValueToCell("5", BLACK_8, AL_CENTER, GRAY_LINE_7);
		tableBuilder.addValueToCell("5", BLACK_8, AL_CENTER, GRAY_LINE_7);

		tableBuilder.addValueToCell("Уровень риска", 2, 1, BLACK_BOLD_8, AL_LEFT, GRAY_RIGHT_4_7);
		tableBuilder.addValueToCell("", BLACK_8, AL_CENTER, GRAY_LEFT_4_7);
		tableBuilder.addValueToCell("Высокий", BLACK_BOLD_8, AL_CENTER, GRAY_LINE_7);
		tableBuilder.addValueToCell("", BLACK_8, AL_CENTER, GRAY_RIGHT_4_7);
		tableBuilder.addValueToCell("", BLACK_8, AL_CENTER, GRAY_LEFT_4_7);
		tableBuilder.addValueToCell("Средний", BLACK_BOLD_8, AL_CENTER, GRAY_LINE_7);
		tableBuilder.addValueToCell("", BLACK_8, AL_CENTER, GRAY_RIGHT_4_7);
		tableBuilder.addValueToCell("", BLACK_8, AL_CENTER, GRAY_LEFT_4_7);
		tableBuilder.addValueToCell("Низкий", BLACK_BOLD_8, AL_CENTER, GRAY_LINE_7);
		tableBuilder.addValueToCell("", BLACK_8, AL_CENTER, GRAY_LINE_7);
		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);

		List<EnquiryResponseERIB.Consumers.S.BureauScore> bureauScoreList = s.getBureauScores();
		if (bureauScoreList != null && !bureauScoreList.isEmpty())
		{

			tableBuilder = builder.getTableBuilderInstance(13, fontPath, new ParagraphStyle(10f, 0f, 11f, 0f),100f);

			tableBuilder.addValueToCell("НАИМЕНОВАНИЕ\n СКОРИНГА", 5, 1, GRAY_5_5, UNDRL_BLACK_AL_LEFT);
			tableBuilder.addValueToCell("КОД\nСКОРИНГА", 2, 1, GRAY_5_5, CENTER_BLACK_UNDERLINE);
			tableBuilder.addValueToCell("КОЛИЧЕСТВО\nБАЛЛОВ", 2, 1, GRAY_5_5, CENTER_BLACK_UNDERLINE);
			tableBuilder.addValueToCell("РИСКОВЫЙ\nИНДИКАТОР", 2, 1, GRAY_5_5, CENTER_BLACK_UNDERLINE);
			tableBuilder.addValueToCell("ИНДИКАТОР\nДОСТОВЕРНОСТИ", 2, 1, GRAY_5_5, CENTER_BLACK_UNDERLINE);
			tableBuilder.addEmptyValueToCell(13, 1, PDFTableStyles.CELL_WITHOUT_BORDER);

			Integer num=1;
			for (EnquiryResponseERIB.Consumers.S.BureauScore bureauScore:bureauScoreList)
			{
				Integer scoreCardType = bureauScore.getScoreCardType();
				if (scoreCardType != null)
				{
					if (bureauScoreList.size()!=num)
					{
						String bkiTypeOfScoreCardName = CreditBuilderFormatter.getTypeOfScore(scoreCardType.toString());
						tableBuilder.addValueToCell(bkiTypeOfScoreCardName, 5, 1, BLACK_BOLD_8, UNDRL_BLACK_AL_LEFT);
						tableBuilder.addValueToCell(scoreCardType.toString(), 2, 1, BLACK_8, CENTER_BLACK_UNDERLINE);

						Integer scoreNumber = bureauScore.getScoreNumber();
						String scoreNum = scoreNumber != null ?  scoreNumber.toString() : "0";
						FontStyle scoreNumFontStyle = scoreNumber != null && scoreNumber < 560 ? RED_8 : BLACK_8;
						tableBuilder.addValueToCell(scoreNum, 2, 1, scoreNumFontStyle, CENTER_BLACK_UNDERLINE);

						Integer scoreInterval = bureauScore.getScoreInterval();
						String scoreInt = scoreInterval != null ?  scoreInterval.toString() : "0";
						FontStyle scoreIntFontStyle = scoreInterval != null && (scoreInterval.intValue() == 1 || scoreInterval.intValue() == 2) ? RED_8 : BLACK_8;
						tableBuilder.addValueToCell(scoreInt, 2, 1, scoreIntFontStyle, CENTER_BLACK_UNDERLINE);

						String confidenceFlag = bureauScore.getConfidenceFlag() != null ?  bureauScore.getConfidenceFlag().toString() : "0";
						tableBuilder.addValueToCell(confidenceFlag, 2, 1, BLACK_8, CENTER_BLACK_UNDERLINE);
					}
					else
					{
						String bkiTypeOfScoreCardName = CreditBuilderFormatter.getTypeOfScore(scoreCardType.toString());
						tableBuilder.addValueToCell(bkiTypeOfScoreCardName, 5, 1, BLACK_BOLD_8, WITHOUT_BORDER_AL_LEFT);
						tableBuilder.addValueToCell(scoreCardType.toString(), 2, 1, BLACK_8, WITHOUT_BORDER_AL_CENTER);

						Integer scoreNumber = bureauScore.getScoreNumber();
						String scoreNum = scoreNumber != null ?  scoreNumber.toString() : "0";
						FontStyle scoreNumFontStyle = scoreNumber != null && scoreNumber < 560 ? RED_8 : BLACK_8;
						tableBuilder.addValueToCell(scoreNum, 2, 1, scoreNumFontStyle, WITHOUT_BORDER_AL_CENTER);

						Integer scoreInterval = bureauScore.getScoreInterval();
						String scoreInt = scoreInterval != null ?  scoreInterval.toString() : "0";
						FontStyle scoreIntFontStyle = scoreInterval != null && (scoreInterval.intValue() == 1 || scoreInterval.intValue() == 2) ? RED_8 : BLACK_8;
						tableBuilder.addValueToCell(scoreInt, 2, 1, scoreIntFontStyle, WITHOUT_BORDER_AL_CENTER);

						String confidenceFlag = bureauScore.getConfidenceFlag() != null ?  bureauScore.getConfidenceFlag().toString() : "0";
						tableBuilder.addValueToCell(confidenceFlag, 2, 1, BLACK_8, WITHOUT_BORDER_AL_CENTER);
					}
					num++;
				}
			}
			tableBuilder.addToPage(TableBreakRule.twoLineMinimumInPage);
		}
	}

	private void addWhoInterestBlock(PDFBuilder builder, String resPath, EnquiryResponseERIB.Consumers.S s) throws PDFBuilderException, BusinessException, ParseException
	{
		String fontPath = getMainFontStr(resPath);
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(8, fontPath, new ParagraphStyle(15f, 0f, 11f, 0f),100f);
		tableBuilder.addImageToCurrentRow(resPath + "\\bki-search.gif", 40, 45, 1, 2, AL_CENTER);
		tableBuilder.addValueToCell("ИНФОРМАЦИЯ ПО РЕЗУЛЬТАТАМ ПОИСКА", 7, 1, BLUE_8, AL_LEFT);

		tableBuilder.addValueToCell("Кто интересовался\n\nкредитной историей", 7, 1, BLUE_37, AL_LEFT);
		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);

		tableBuilder.addValueToCell("Кто и когда проверял Вашу кредитную историю. Например, банки в\n" +
				"большинстве случаем делают запросы при рассмотрении заявления на\n" +
				"кредит, чтобы сообщить Вам  свое решение.", 7, 1, GRAY_11_5, AL_LEFT);

		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);

		tableBuilder = builder.getTableBuilderInstance(18, fontPath, new ParagraphStyle(7, 0f, 5f, 0f),103f);
		tableBuilder.addEmptyValueToCell(18, 1, BLUE_AL_LEFT);
		tableBuilder.addValueToCell("   ОСНОВНЫЕ ДАННЫЕ РАЗДЕЛА", 18, 1, BLACK_B_9_5, BLUE_AL_LEFT);
		tableBuilder.addEmptyValueToCell(18,1, BLUE_AL_LEFT);

		EnquiryResponseERIB.Consumers.S.Summary summary = s.getSummary();
		Long CAPSOwnerRecip = summary.getCAPSRecordsOwner() != null  ? summary.getCAPSRecordsOwner() : 0;
		Long CAPSJointRecip = summary.getCAPSRecordsJoint() != null  ? summary.getCAPSRecordsJoint() : 0;
		Long CAPSGuarantorRecip = summary.getCAPSRecordsGuarantor() != null  ? summary.getCAPSRecordsGuarantor() : 0;
		Long CAPSRefereeRecip = summary.getCAPSRecordsReferee() != null  ? summary.getCAPSRecordsReferee() : 0;
		Long summ  = CAPSOwnerRecip + CAPSJointRecip + CAPSGuarantorRecip + CAPSRefereeRecip;

		Long owner3 = summary.getCAPSLast3MonthsOwner() != null  ? summary.getCAPSLast3MonthsOwner() : 0L;
		Long joint3 = summary.getCAPSLast3MonthsJoint() != null  ? summary.getCAPSLast3MonthsJoint() : 0L;
		Long guarantor3 = summary.getCAPSLast3MonthsGuarantor() != null  ? summary.getCAPSLast3MonthsGuarantor() : 0L;
		Long referee3 = summary.getCAPSLast3MonthsReferee() != null  ? summary.getCAPSLast3MonthsReferee() : 0L;
		Long summ3 = owner3 + joint3 + guarantor3 + referee3;

		Long owner6 = summary.getCAPSLast6MonthsOwner() != null  ? summary.getCAPSLast6MonthsOwner() : 0L;
		Long joint6 = summary.getCAPSLast6MonthsJoint() != null  ? summary.getCAPSLast6MonthsJoint() : 0L;
		Long guarantor6 = summary.getCAPSLast6MonthsGuarantor() != null  ? summary.getCAPSLast6MonthsGuarantor() : 0L;
		Long referee6 = summary.getCAPSLast6MonthsReferee() != null  ? summary.getCAPSLast6MonthsReferee() : 0L;
		Long summ6 = owner6 + joint6 + guarantor6 + referee6;

		Long owner12 = summary.getCAPSLast12MonthsOwner() != null  ? summary.getCAPSLast12MonthsOwner() : 0L;
		Long joint12 = summary.getCAPSLast12MonthsJoint() != null  ? summary.getCAPSLast12MonthsJoint() : 0L;
		Long guarantor12 = summary.getCAPSLast12MonthsGuarantor() != null  ? summary.getCAPSLast12MonthsGuarantor() : 0L;
		Long referee12 = summary.getCAPSLast12MonthsReferee() != null  ? summary.getCAPSLast12MonthsReferee() : 0L;
		Long summ12 = owner12 + joint12 + guarantor12 + referee12;

		Queue<Pair<String,FontStyle>> textAndStyle = new LinkedList<Pair<String, FontStyle>>();
		textAndStyle.add(new Pair<String, FontStyle>("    Всего: ", BLACK_9_5));
		textAndStyle.add(new Pair<String, FontStyle>(summ.toString(), BLACK_B_9_5));
		tableBuilder.addValueToCell(textAndStyle, 3, 1, BLUE_AL_LEFT);
		tableBuilder.addValueToCell("Последние 3 месяца:", 4, 1, BLACK_9_5, BLUE_AL_RIGHT);
		tableBuilder.addValueToCell(summ3.toString(), 1, 1,BLACK_B_9_5, BLUE_AL_LEFT);
		tableBuilder.addValueToCell("Последние 6 месяцев:", 4, 1, BLACK_9_5, BLUE_AL_RIGHT);
		tableBuilder.addValueToCell(summ6.toString(), 1, 1,BLACK_B_9_5, BLUE_AL_LEFT);
		tableBuilder.addValueToCell("Последние 12 месяцев:", 4, 1, BLACK_9_5, BLUE_AL_RIGHT);
		tableBuilder.addValueToCell(summ12.toString(), 1, 1, BLACK_B_9_5, BLUE_AL_LEFT);

		tableBuilder.addValueToCell("№", 1, 1,GRAY_5_5, BLUE_AL_CENER, BLACK_LEFT_8_3);
		tableBuilder.addValueToCell("ДАТА", 2, 1, GRAY_5_5, BLUE_AL_LEFT, BLACK_LINE_3);
		tableBuilder.addValueToCell("ИСТОЧНИК ЗАПРОСА", 3, 1, GRAY_5_5, BLUE_AL_LEFT, BLACK_LINE_3);
		tableBuilder.addValueToCell("ПРИЧИНА ЗАПРОСА", 4, 1, GRAY_5_5, BLUE_AL_LEFT, BLACK_LINE_3);
		tableBuilder.addValueToCell("ВИД КРЕДИТА", 4, 1, GRAY_5_5, BLUE_AL_LEFT, BLACK_LINE_3);
		tableBuilder.addValueToCell("РАЗМЕР/ЛИМИТ\nКРЕДИТОВАНИЯ", 2, 1, GRAY_5_5, BLUE_AL_CENER, BLACK_LINE_3);
		tableBuilder.addValueToCell("ВАЛЮТА", 2, 1, GRAY_5_5, BLUE_AL_CENER, BLACK_RIGHT_8_3);

		List<EnquiryResponseERIB.Consumers.S.CAPS> capses = s.getCAPS();
		Collections.sort(capses, Collections.reverseOrder(new Comparator<EnquiryResponseERIB.Consumers.S.CAPS>()
		{
			public int compare(EnquiryResponseERIB.Consumers.S.CAPS o1, EnquiryResponseERIB.Consumers.S.CAPS o2)
			{
				return o1.getEnquiryDate().compareTo(o2.getEnquiryDate());
			}
		}));
		Integer n = 1;
		for(EnquiryResponseERIB.Consumers.S.CAPS caps:capses)
		{
			if (capses.size() != n)
			{   //вариант с подчеркиванием
				tableBuilder.addValueToCell(n.toString(), 1, 1,BLACK_8, BLUE_AL_CENER, GRAY_LEFT_8_3);
				tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(caps.getEnquiryDate(), ""), 2, 1, BLACK_8, BLUE_AL_LEFT, GRAY_LINE_3);
				tableBuilder.addValueToCell(CreditBuilderFormatter.formatBankName(caps.getSubscriberName(), ""), 3, 1, BLACK_8, BLUE_AL_LEFT, GRAY_LINE_3);
				tableBuilder.addValueToCell(CreditBuilderFormatter.getReasonForEnquiry(caps.getReason(), ""), 4, 1, BLACK_8, BLUE_AL_LEFT, GRAY_LINE_3);
				tableBuilder.addValueToCell(CreditBuilderFormatter.formatFinanceType(caps.getFinanceType(), ""), 4, 1, BLACK_8, BLUE_AL_LEFT, GRAY_LINE_3);
				String creditLimit = caps.getCreditLimit();
				String amountOfFinance = caps.getAmountOfFinance();
				if (StringHelper.isNotEmpty(creditLimit))
					tableBuilder.addValueToCell(MoneyFunctions.getFormatAmountString(creditLimit), 2, 1, BLACK_8, BLUE_AL_CENER, GRAY_LINE_3);
				else if (StringHelper.isNotEmpty(amountOfFinance))
					tableBuilder.addValueToCell(MoneyFunctions.getFormatAmountString(amountOfFinance) , 2, 1, BLACK_8, BLUE_AL_CENER, GRAY_LINE_3);
				else
					tableBuilder.addEmptyValueToCell(2, 1, BLUE_AL_LEFT, GRAY_LINE_3);

				String currency = caps.getCurrency();
				if (StringHelper.isNotEmpty(currency))
					tableBuilder.addValueToCell(getEmptyStrIfEmpty(currency), 2, 1, BLACK_8, BLUE_AL_CENER, GRAY_RIGHT_8_3);
				else
					tableBuilder.addEmptyValueToCell(2, 1,BLUE_AL_CENER, GRAY_LINE_3);
			}
			else
			{
				tableBuilder.addValueToCell(n.toString(), 1, 1,BLACK_8, BLUE_AL_CENER);
				tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(caps.getEnquiryDate(), ""), 2, 1, BLACK_8, BLUE_AL_LEFT);
				tableBuilder.addValueToCell(CreditBuilderFormatter.formatBankName(caps.getSubscriberName(), ""), 3, 1, BLACK_8, BLUE_AL_LEFT);
				tableBuilder.addValueToCell(CreditBuilderFormatter.getReasonForEnquiry(caps.getReason(), ""), 4, 1, BLACK_8, BLUE_AL_LEFT);
				tableBuilder.addValueToCell(CreditBuilderFormatter.formatFinanceType(caps.getFinanceType(), ""), 4, 1, BLACK_8, BLUE_AL_LEFT);

				String creditLimit = caps.getCreditLimit();
				String amountOfFinance = caps.getAmountOfFinance();
				if (StringHelper.isNotEmpty(creditLimit))
					tableBuilder.addValueToCell(MoneyFunctions.getFormatAmountString(creditLimit) , 2, 1, BLACK_8, BLUE_AL_CENER);
				else if (StringHelper.isNotEmpty(amountOfFinance))
					tableBuilder.addValueToCell(MoneyFunctions.getFormatAmountString(amountOfFinance) , 2, 1, BLACK_8, BLUE_AL_CENER);
				else
					tableBuilder.addEmptyValueToCell(2, 1, BLUE_AL_LEFT, GRAY_LINE_3);

				String currency = caps.getCurrency();
				if (StringHelper.isNotEmpty(currency))
					tableBuilder.addValueToCell(getEmptyStrIfEmpty(currency), 2, 1, BLACK_8, BLUE_AL_CENER);
				else
					tableBuilder.addEmptyValueToCell(2, 1,BLUE_AL_CENER);
			}
			n++;
		}
		tableBuilder.addEmptyValueToCell(18, 1, BORDER_BOTTOM_BLUE);
		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);
	}

	private void addApplyForLoan(PDFBuilder builder,String resPath,EnquiryResponseERIB.Consumers.S s) throws PDFBuilderException, BusinessException, ParseException
	{
		String fontPath = getMainFontStr(resPath);

		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(4, fontPath, new ParagraphStyle(10, 0, 5, 0),100);
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);
		List<EnquiryResponseERIB.Consumers.S.CAPS> capses = s.getCAPS();
		if (!capses.isEmpty())
		{
			int capsI = 1; //счетчик сaps
			int size = capses.size();
			for(EnquiryResponseERIB.Consumers.S.CAPS caps:capses)
			{
				//передаем информацию о блоке и его номере, для последующего считываения в applayForLoanHeader
				builder.addKeyword(TRIG_KEY + "% Запись "+ capsI + " из " + size + " | Кто интересовался моей историей ");
				tableBuilder = builder.getTableBuilderInstance(4, fontPath, new ParagraphStyle(10, 0f, 3f, 0f), 103f);
				tableBuilder.addValueToCell("        Запись" +  capsI + " из " + size +" по запросу в бюро — Дата запроса " + CreditBuilderFormatter.getDate(caps.getEnquiryDate(), "") , 2, 1, BLACK_7, GRAY_AL_LEFT);
				tableBuilder.addValueToCell("Номер запроса: " + capsI + "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0" , 2, 1, BLACK_7, GRAY_AL_RIGHT);
				tableBuilder.addEmptyValueToCell(2, 1, GRAY_AL_LEFT, GRAY_LEFT_12_3);
				tableBuilder.addEmptyValueToCell(2, 1, GRAY_AL_LEFT, GRAY_RIGHT_12_3);
				tableBuilder.addValueToCell("        ПРИЧИНА ЗАПРОСА", 4, 1, BLACK_B_7, GRAY_AL_LEFT);
				tableBuilder.addValueToCell("   " + CreditBuilderFormatter.getReasonForEnquiry(caps.getReason(), ""), 4, 1, BLACK_B_18, GRAY_AL_LEFT);
				tableBuilder.addEmptyValueToCell(4, 1, GRAY_AL_LEFT);
				tableBuilder.addEmptyValueToCell(2, 1, BORDER_LEFT_GARAY_AL_LEFT);
				tableBuilder.addEmptyValueToCell(2, 1, BORDER_RIGHT_GARAY_AL_LEFT);
				tableBuilder.addValueToCell("        Тип финансирование", 1, 1, BLACK_B_8, BORDER_LEFT_GARAY_AL_LEFT);
				tableBuilder.addValueToCell("   " + CreditBuilderFormatter.getPurposeOfFinance(caps.getPurposeOfFinance(), PDF_EMTY), 3, 1, BLACK_8, BORDER_RIGHT_GARAY_AL_LEFT);
				tableBuilder.addValueToCell("        Размер/лимит кредитования", 1, 1, BLACK_B_8, BORDER_LEFT_GARAY_AL_LEFT);
				String creditLimit = caps.getCreditLimit();
				String amountOfFinance = caps.getAmountOfFinance();
				String currency = caps.getCurrency();
				if (StringHelper.isNotEmpty(creditLimit))
					tableBuilder.addValueToCell(MoneyFunctions.getFormatAmountString(creditLimit) + " " + currency, 3, 1, BLACK_8, BORDER_RIGHT_GARAY_AL_LEFT);
				else if (StringHelper.isNotEmpty(amountOfFinance))
					tableBuilder.addValueToCell(MoneyFunctions.getFormatAmountString(amountOfFinance) + " " + currency, 3, 1, BLACK_8, BORDER_RIGHT_GARAY_AL_LEFT);
				else
					tableBuilder.addValueToCell("—", 3, 1, BLACK_8, BORDER_RIGHT_GARAY_AL_LEFT);
				tableBuilder.addValueToCell("        Срок кредитного договора", 1, 1, BLACK_B_8, BORDER_LEFT_GARAY_AL_LEFT);
				tableBuilder.addValueToCell("   " + CreditBuilderFormatter.formatDuration(caps.getDuration(), caps.getDurationUnits(), PDF_EMTY), 3, 1, BLACK_8, BORDER_RIGHT_GARAY_AL_LEFT);
				tableBuilder.addEmptyValueToCell(4, 1, BORDER_LEFT_BOTTOM_RIGHT_GARAY);
				tableBuilder.addToPage(TableBreakRule.wholeTableInPage);

				tableBuilder = builder.getTableBuilderInstance(4, fontPath, new ParagraphStyle(10, 0f, 5f, 0f),100f);
				tableBuilder.addValueToCell("   Подробности запроса"  , 2, 1, GRAY_12, AL_LEFT, BLACK_LEFT_8_3);
				tableBuilder.addEmptyValueToCell(2, 1, AL_LEFT, BLACK_RIGHT_8_3);

				tableBuilder.addValueToCell("    Поиск по", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
				tableBuilder.addValueToCell(CreditBuilderFormatter.formatBankName(caps.getSubscriberName(), PDF_EMTY)  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
				tableBuilder.addValueToCell("      Дата поиска", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
				tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(caps.getEnquiryDate(), PDF_EMTY) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

				tableBuilder.addValueToCell("    Сформированная ссылка", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
				tableBuilder.addValueToCell(getEmptyStrIfEmpty(caps.getStreamID()), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
				tableBuilder.addValueToCell("      Цель финансирования"  , 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
				tableBuilder.addValueToCell(CreditBuilderFormatter.getPurposeOfFinance(caps.getPurposeOfFinance(), PDF_EMTY), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

				tableBuilder.addValueToCell("    Причина запроса", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
				tableBuilder.addValueToCell(CreditBuilderFormatter.getReasonForEnquiry(caps.getReason(), PDF_EMTY), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
				tableBuilder.addValueToCell("      Дата заявки", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
				tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(caps.getApplicationDate(), PDF_EMTY), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

				tableBuilder.addValueToCell("    Номер заявки", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
				tableBuilder.addValueToCell(getEmptyStrIfEmpty(caps.getApplicationNumber()), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
				tableBuilder.addValueToCell("      Запись об оспаривании", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
				tableBuilder.addValueToCell(CreditBuilderFormatter.getDisputeIndicator(caps.getDisputeIndicator(), PDF_EMTY), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

				tableBuilder.addValueToCell("    Класс счёта", 1, 1, BLACK_B_8, AL_LEFT);
				tableBuilder.addValueToCell(CreditBuilderFormatter.getAccountClass(caps.getAccountClass(), PDF_EMTY), 1, 1, BLACK_8, AL_LEFT);
				tableBuilder.addEmptyValueToCell(2, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
				tableBuilder.addToPage(TableBreakRule.wholeTableInPage);

				int comsumI = 1; //счетчик заявителей
				for (EnquiryResponseERIB.Consumers.S.CAPS.Consumer consumer:caps.getConsumers() )
				{
					tableBuilder = builder.getTableBuilderInstance(4, fontPath, new ParagraphStyle(10, 0f, 5f, 0f),100f);
					tableBuilder.addEmptyValueToCell(4, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
					tableBuilder.addEmptyValueToCell(4, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
					tableBuilder.addValueToCell("   Данные заявителя " + comsumI + " из " + caps.getConsumers().size(), 2, 1, GRAY_12, AL_LEFT, BLACK_LEFT_8_3);
					tableBuilder.addEmptyValueToCell(2, 1, AL_LEFT, BLACK_RIGHT_8_3);

					tableBuilder.addValueToCell("    Тип заявителя", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
					tableBuilder.addValueToCell(CreditBuilderFormatter.getApplicantType(consumer.getApplicantType(), PDF_EMTY)  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
					tableBuilder.addValueToCell("      Обращение", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
					tableBuilder.addValueToCell(CreditBuilderFormatter.getTitle(consumer.getTitle(), PDF_EMTY) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

					tableBuilder.addValueToCell("    Фамилия", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
					tableBuilder.addValueToCell(getSurNameFirstLitera(consumer.getSurname()), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
					tableBuilder.addValueToCell("      Девичья фамилия"  , 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
					tableBuilder.addValueToCell(getEmptyStrIfEmpty(consumer.getAliasName()), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

					tableBuilder.addValueToCell("    Имя", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
					tableBuilder.addValueToCell(getEmptyStrIfEmpty(consumer.getName1()), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
					tableBuilder.addValueToCell("      Место рождения", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
					tableBuilder.addValueToCell(getEmptyStrIfEmpty(consumer.getPlaceOfBirth()), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

					tableBuilder.addValueToCell("    Отчество", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
					tableBuilder.addValueToCell(getEmptyStrIfEmpty(consumer.getName2()), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
					tableBuilder.addValueToCell("      Гражданство", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
					tableBuilder.addValueToCell(CreditBuilderFormatter.getCountry(consumer.getNationality(), PDF_EMTY), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

					tableBuilder.addValueToCell("    Дата рождения", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
					tableBuilder.addValueToCell("••/••/••••", 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
					tableBuilder.addValueToCell("      Согласие", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
					tableBuilder.addValueToCell(CreditBuilderFormatter.getConsentIndicato(consumer.getConsentFlag(), PDF_EMTY), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

					tableBuilder.addValueToCell("    Пол", 1, 1, BLACK_B_8, AL_LEFT);
					tableBuilder.addValueToCell(CreditBuilderFormatter.getSex(consumer.getSex(), PDF_EMTY), 1, 1, BLACK_8, AL_LEFT);
					tableBuilder.addValueToCell("      Дата получения согласия", 1, 1, BLACK_B_8, AL_LEFT);
					tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(consumer.getDateConsentGiven(), PDF_EMTY), 1, 1, BLACK_8, AL_LEFT);
					tableBuilder.addEmptyValueToCell(4, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
					tableBuilder.addToPage(TableBreakRule.wholeTableInPage);

					List<EnquiryResponseERIB.Consumers.S.CAPS.Consumer.Address>  addresses = consumer.getAddresses();
					int addressI = 1; //счетчик адресов
					for (EnquiryResponseERIB.Consumers.S.CAPS.Consumer.Address address: addresses)
					{
						addAddress(address, builder, fontPath, addressI, addresses.size());
						addressI++;
					}
					comsumI++;
				}
				capsI++;
			}
		}
		//удаляем ключевое слово чтоб шапка больше не рисовалась.

	}

	private void addLoanBlock(PDFBuilder builder, String resPath, EnquiryResponseERIB.Consumers.S s) throws PDFBuilderException, BusinessException, ParseException
	{
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);
		String fontPath = getMainFontStr(resPath);
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(8, fontPath, new ParagraphStyle(15, 0f, 11f, 0f),100f);
		tableBuilder.addImageToCurrentRow(resPath + "\\bki-card.gif", 40, 45, 1, 2, AL_CENTER);
		tableBuilder.addValueToCell("ЗАПИСИ ОБ ИЗМЕНЕНИЯХ КРЕДИТНОЙ ИСТОРИИ", 7, 1, BLUE_8, AL_LEFT);

		tableBuilder.addValueToCell("Кредиты и кредитные карты", 7, 1, BLUE_30, AL_LEFT);

		tableBuilder.addEmptyValueToCell(PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addValueToCell("Вся информация по Вашим кредитам как на ладони! По каждому\n" +
				"кредиту показана детальная история платежей с указанием\n" +
				"допущенных просрочек", 7, 1, GRAY_11_5, AL_LEFT);

		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);

		tableBuilder = builder.getTableBuilderInstance(14, fontPath, new ParagraphStyle(7, 0f, 5f, 0f),103f);
		tableBuilder.addEmptyValueToCell(14, 1, BLUE_AL_LEFT);
		tableBuilder.addValueToCell("   ОСНОВНЫЕ ДАННЫЕ РАЗДЕЛА", 14, 1, BLACK_B_9_5, BLUE_AL_LEFT);
		List<EnquiryResponseERIB.Consumers.S.CAIS> caises = s.getCAISES();
		tableBuilder.addValueToCell("    Всего:  ", 1, 1, BLACK_9_5, BLUE_AL_LEFT);
		tableBuilder.addValueToCell(String.valueOf(caises.size()) , 13, 1, BLACK_B_9_5, BLUE_AL_LEFT);
		tableBuilder.addValueToCell("№", 1, 1,GRAY_5_5_102, BLUE_AL_CENER, BLACK_LEFT_8_3);
		tableBuilder.addValueToCell("ИСТОЧНИК ИСТОРИИ", 3, 1, GRAY_5_5_102, BLUE_AL_LEFT, BLACK_LINE_3);
		tableBuilder.addValueToCell("ТЕКУЩИЙ СТАТУС ПЛАТЕЖА", 2, 1, GRAY_5_5_102, BLUE_AL_LEFT, BLACK_LINE_3);
		tableBuilder.addValueToCell("РАЗМЕР/ЛИМИТ\nКРЕДИТОВАНИЯ", 2, 1, GRAY_5_5_102, BLUE_AL_CENER, BLACK_LINE_3);
		tableBuilder.addValueToCell("ОБЩАЯ СУММА\nЗАДОЛЖЕННОСТИ", 2, 1, GRAY_5_5_102, BLUE_AL_CENER, BLACK_LINE_3);
		tableBuilder.addValueToCell("СУММА\nПРОСРОЧКИ", 1, 1, GRAY_5_5_102, BLUE_AL_CENER, BLACK_LINE_3);
		tableBuilder.addValueToCell("ВАЛЮТА", 1, 1, GRAY_5_5_102, BLUE_AL_CENER, BLACK_LINE_3);
		tableBuilder.addValueToCell("ДАТА УЧЁТА", 2, 1, GRAY_5_5_102, BLUE_AL_CENER, BLACK_RIGHT_8_3);


		Integer n = 1;
		for(EnquiryResponseERIB.Consumers.S.CAIS cais:caises)
		{
			if (caises.size() != n)
			{   //вариант с подчеркиванием
				tableBuilder.addValueToCell(n.toString(), 1, 1,BLACK_8, BLUE_AL_CENER, GRAY_LEFT_8_3);
				Queue<Pair<String,FontStyle>> textAndStyle = new LinkedList<Pair<String, FontStyle>>();
				textAndStyle.add(new Pair<String, FontStyle>(CreditBuilderFormatter.getFinanceType(cais.getFinanceType(), PDF_EMTY) + "\n", BLACK_8));
				textAndStyle.add(new Pair<String, FontStyle>(CreditBuilderFormatter.formatBankName(cais.getSubscriberName(), PDF_EMTY), GRAY_8));
				tableBuilder.addValueToCell(textAndStyle, 3, 1, BLUE_AL_LEFT, GRAY_LINE_3);

				tableBuilder.addValueToCell(CreditBuilderFormatter.getPaymentState(cais.getAccountPaymentStatus(), PDF_EMTY), 2, 1, BLACK_8, BLUE_AL_LEFT, GRAY_LINE_3);

				String creditLimit = cais.getCreditLimit();
				String amountOfFinance = cais.getAmountOfFinance();
				if (StringHelper.isNotEmpty(creditLimit))
					tableBuilder.addValueToCell(MoneyFunctions.getFormatAmountString(creditLimit) , 2, 1, BLACK_8, BLUE_AL_CENER, GRAY_LINE_3);
				else if (StringHelper.isNotEmpty(amountOfFinance))
					tableBuilder.addValueToCell(MoneyFunctions.getFormatAmountString(amountOfFinance) , 2, 1, BLACK_8, BLUE_AL_CENER, GRAY_LINE_3);
				else
					tableBuilder.addValueToCell(PDF_EMTY, 2, 1, BLACK_B_8, BLUE_AL_CENER, GRAY_LINE_3);

				tableBuilder.addValueToCell(getEmptyStrIfEmpty(MoneyFunctions.getFormatAmountString(cais.getOutstandingBalance())), 2, 1, BLACK_8, BLUE_AL_CENER, GRAY_LINE_3);
				tableBuilder.addValueToCell(getEmptyStrIfEmpty(MoneyFunctions.getFormatAmountString(cais.getArrearsBalance())),1, 1, BLACK_8, BLUE_AL_CENER, GRAY_LINE_3);
				tableBuilder.addValueToCell(getEmptyStrIfEmpty(cais.getCurrency()),1, 1, BLACK_8, BLUE_AL_CENER, GRAY_LINE_3);
				tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(cais.getMonthOfLastUpdate(), PDF_EMTY),2, 1, BLACK_8, BLUE_AL_CENER, GRAY_RIGHT_8_3);
			}
			else
			{//вариант без подчеркивания
				tableBuilder.addValueToCell(n.toString(), 1, 1,BLACK_8, BLUE_AL_CENER);
				Queue<Pair<String,FontStyle>> textAndStyle = new LinkedList<Pair<String, FontStyle>>();
				textAndStyle.add(new Pair<String, FontStyle>(CreditBuilderFormatter.getFinanceType(cais.getFinanceType(), PDF_EMTY) + "\n", BLACK_8));
				textAndStyle.add(new Pair<String, FontStyle>(CreditBuilderFormatter.formatBankName(cais.getSubscriberName(), PDF_EMTY), GRAY_8));
				tableBuilder.addValueToCell(textAndStyle, 3, 1, BLUE_AL_LEFT);

				tableBuilder.addValueToCell(CreditBuilderFormatter.getPaymentState(cais.getAccountPaymentStatus(), PDF_EMTY), 2, 1, BLACK_8, BLUE_AL_LEFT);

				String creditLimit = cais.getCreditLimit();
				String amountOfFinance = cais.getAmountOfFinance();
				if (StringHelper.isNotEmpty(creditLimit))
					tableBuilder.addValueToCell(MoneyFunctions.getFormatAmountString(creditLimit) , 2, 1, BLACK_8, BLUE_AL_CENER);
				else if (StringHelper.isNotEmpty(amountOfFinance))
					tableBuilder.addValueToCell(MoneyFunctions.getFormatAmountString(amountOfFinance) , 2, 1, BLACK_8, BLUE_AL_CENER);
				else
					tableBuilder.addValueToCell(PDF_EMTY, 2, 1, BLACK_B_8, BLUE_AL_CENER);

				tableBuilder.addValueToCell(getEmptyStrIfEmpty(MoneyFunctions.getFormatAmountString(cais.getOutstandingBalance())), 2, 1, BLACK_8, BLUE_AL_CENER);
				tableBuilder.addValueToCell(getEmptyStrIfEmpty(MoneyFunctions.getFormatAmountString(cais.getArrearsBalance())),1, 1, BLACK_8, BLUE_AL_CENER);
				tableBuilder.addValueToCell(getEmptyStrIfEmpty(cais.getCurrency()),1, 1, BLACK_8, BLUE_AL_CENER);
				tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(cais.getMonthOfLastUpdate(), PDF_EMTY),2, 1, BLACK_8, BLUE_AL_CENER);
			}
			n++;
		}
		tableBuilder.addEmptyValueToCell(14, 1, BORDER_BOTTOM_BLUE);
		tableBuilder.addEmptyValueToCell(14, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addValueToCell("Информация по каждому договору актуальна на соответствующую этому договору дату учёта  ",14, 1, BLACK_9_5, AL_CENTER);
		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);
	}

	private void addCurrentLoanBlock(PDFBuilder builder, String resPath, EnquiryResponseERIB.Consumers.S s) throws PDFBuilderException, BusinessException, ParseException
	{

		String fontPath = getMainFontStr(resPath);
		int n = 1;
		int size = s.getCAISES().size();
		for(EnquiryResponseERIB.Consumers.S.CAIS cais:s.getCAISES())
		{
			builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);
			//передаем информацию о блоке и его номере, для последующего считываения в applayForLoanHeader
			builder.addKeyword(TRIG_KEY + "% Запись " + n + " из " + size + " | Кредиты и кредитные карты ");
			PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(4, fontPath, new ParagraphStyle(10, 0f, 3f, 0f), 103f);
			tableBuilder.addValueToCell("      Запись " + n + " из " + size + " — Дата запроса " + CreditBuilderFormatter.getDate(cais.getOpenDate(), ""), 2, 1, BLACK_8_5, GREEN_AL_LEFT);
			tableBuilder.addValueToCell("Дата открытия: " + CreditBuilderFormatter.getDate(cais.getOpenDate(), "") + "\u00a0\u00a0\u00a0\u00a0\u00a0\u00a0", 2, 1, BLACK_8_5, GREEN_AL_RIGHT);
			tableBuilder.addEmptyValueToCell(2, 1, GREEN_AL_LEFT, GRAY_LEFT_12_3);
			tableBuilder.addEmptyValueToCell(2, 1, GREEN_AL_RIGHT, GRAY_RIGHT_12_3);
			tableBuilder.addEmptyValueToCell(4, 1, GREEN_AL_RIGHT);
			tableBuilder.addValueToCell("      " + CreditBuilderFormatter.formatBankName(cais.getSubscriberName(), PDF_EMTY), 4, 1, BLACK_B_9_5, GREEN_AL_LEFT);
			tableBuilder.addValueToCell("   " + CreditBuilderFormatter.getFinanceType(cais.getFinanceType(), ""), 2, 1, BLACK_B_18, GREEN_AL_LEFT);

			//сумма валюта / время единица вермени.
			String amountAndCurr = "";
			if (StringHelper.isNotEmpty(cais.getCreditLimit()))
				amountAndCurr = MoneyFunctions.getFormatAmountString(cais.getCreditLimit());
			else if (StringHelper.isNotEmpty(cais.getAmountOfFinance()))
				amountAndCurr = MoneyFunctions.getFormatAmountString(cais.getAmountOfFinance());
			amountAndCurr = amountAndCurr + " " + cais.getCurrency();
			String duration = CreditBuilderFormatter.formatDuration(cais.getDuration(), cais.getDurationUnit(), PDF_EMTY);
			Queue<Pair<String,FontStyle>> textAndStyle = new LinkedList<Pair<String, FontStyle>>();
			textAndStyle.add(new Pair<String, FontStyle>(amountAndCurr, BLACK_11));
			textAndStyle.add(new Pair<String, FontStyle>("/", GRAY_18));
			textAndStyle.add(new Pair<String, FontStyle>(duration, BLACK_11));
			tableBuilder.addValueToCell(textAndStyle, 2, 1, GREEN_AL_LEFT);
			tableBuilder.addEmptyValueToCell(4, 1, GREEN_AL_RIGHT);
			tableBuilder.addValueToCell("      Непогашеный остаток", 1, 1, BLACK_9_5, GREEN_AL_LEFT);
			tableBuilder.addValueToCell("      Сумма просрочки", 1, 1, BLACK_9_5, GREEN_AL_LEFT);
			tableBuilder.addValueToCell("      Статус выплат", 2, 1, BLACK_9_5, GREEN_AL_LEFT);
			tableBuilder.addValueToCell("      " + getEmptyStrIfEmpty(MoneyFunctions.getFormatAmountString(cais.getOutstandingBalance())), 1, 1, BLACK_9_5, GREEN_AL_LEFT);
			tableBuilder.addValueToCell("      " + getEmptyStrIfEmpty(MoneyFunctions.getFormatAmountString(cais.getArrearsBalance())), 1, 1, BLACK_9_5, GREEN_AL_LEFT);
			tableBuilder.addValueToCell("      " + CreditBuilderFormatter.getPaymentState(cais.getAccountPaymentStatus(), PDF_EMTY), 2, 1, BLACK_9_5, GREEN_AL_LEFT);
			tableBuilder.addEmptyValueToCell(4, 1, GREEN_AL_RIGHT);
			tableBuilder.addToPage(TableBreakRule.wholeTableInPage);

			tableBuilder = builder.getTableBuilderInstance(6, fontPath, new ParagraphStyle(10, 0f, 5f, 0f),100f);
			tableBuilder.addEmptyValueToCell(6, 2, PDFTableStyles.CELL_WITHOUT_BORDER);
			tableBuilder.addValueToCell("   Подробная информация о счёта", 3, 1, GRAY_12, AL_LEFT, BLACK_LEFT_8_3);
			tableBuilder.addEmptyValueToCell(3, 1, PDFTableStyles.CELL_WITHOUT_BORDER,BLACK_RIGHT_8_3);
			tableBuilder.addEmptyValueToCell(6, 1, PDFTableStyles.CELL_WITHOUT_BORDER);

			tableBuilder.addValueToCell("    Предоставлено", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
			tableBuilder.addValueToCell(CreditBuilderFormatter.formatBankName(cais.getSubscriberName(), PDF_EMTY)   , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
			tableBuilder.addValueToCell("      Просроченная задолженность", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
			tableBuilder.addValueToCell(getEmptyStrIfEmpty(MoneyFunctions.getFormatAmountString(cais.getArrearsBalance())), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

			tableBuilder.addValueToCell("    Дата добавления", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
			tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(cais.getDateAccountAdded(), PDF_EMTY)  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
			tableBuilder.addValueToCell("      Договорная запись исполнения обязательств", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
			tableBuilder.addValueToCell(getEmptyStrIfEmpty(MoneyFunctions.getFormatAmountString(cais.getArrearsBalance())) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

			tableBuilder.addValueToCell("    Дата последнего обновления", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
			tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(cais.getMonthOfLastUpdate(), PDF_EMTY)  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
			tableBuilder.addValueToCell("      Специальный статус", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
			tableBuilder.addValueToCell(CreditBuilderFormatter.getAccountSpecialStatus(cais.getAccountSpecialStatus(), PDF_EMTY) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

			tableBuilder.addValueToCell("    Номер счета", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
			tableBuilder.addValueToCell(getEmptyStrIfEmpty(cais.getConsumerAccountNumber())  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
			tableBuilder.addValueToCell("      Статус кредитной линии", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
			tableBuilder.addValueToCell(CreditBuilderFormatter.getCreditFacilitySatus(cais.getCreditFacilityStatus(), PDF_EMTY) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

			tableBuilder.addValueToCell("    Дата учёта", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
			tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(cais.getMonthOfLastUpdate(), PDF_EMTY)   , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
			tableBuilder.addValueToCell("      Дата последнего платежа", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
			tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(cais.getLastPaymentDate(), PDF_EMTY) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

			tableBuilder.addValueToCell("    Дата открытия", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
			tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(cais.getOpenDate(), PDF_EMTY)  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
			tableBuilder.addValueToCell("      Дата последнего пропущенного платежа", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
			tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(cais.getLastMissedPaymentDate(), PDF_EMTY) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

			tableBuilder.addValueToCell("    Класс счёта", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
			tableBuilder.addValueToCell(CreditBuilderFormatter.getAccountClass(cais.getAccountClass(), PDF_EMTY)  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
			tableBuilder.addValueToCell("      Дата неплатежа (дефолт)", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
			tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(cais.getDefaultDate(), PDF_EMTY) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

			tableBuilder.addValueToCell("    Дата закрытия", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
			tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(cais.getAccountClosedDate(), PDF_EMTY)  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
			tableBuilder.addValueToCell("       Дата судебного рассмотрения", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
			tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(cais.getLitigationDate(), PDF_EMTY)  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

			tableBuilder.addValueToCell("    Причина закрытия", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
			tableBuilder.addValueToCell(CreditBuilderFormatter.formatReasonForClosure(cais.getReasonForClosure(), PDF_EMTY)   , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
			tableBuilder.addValueToCell("     Дата списания", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
			tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(cais.getWriteOffDate(), PDF_EMTY) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

			tableBuilder.addValueToCell("    Запись об оспаривании", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
			tableBuilder.addValueToCell(CreditBuilderFormatter.getDisputeIndicator(cais.getRecordBlockDisputeIndicator(), PDF_EMTY) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
			tableBuilder.addValueToCell("      Худший платежный статус", 2, 1, BLACK_B_8, AL_LEFT);
			tableBuilder.addValueToCell(CreditBuilderFormatter.getPaymentState(cais.getWorstPaymentStatus(), PDF_EMTY) , 1, 1, BLACK_8, AL_LEFT);

			tableBuilder.addValueToCell("    Число владельцев счёта", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
			tableBuilder.addValueToCell(getEmptyStrIfEmpty(cais.getConsumerAccountNumber())  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
			tableBuilder.addEmptyValueToCell(3, 1, PDFTableStyles.CELL_WITHOUT_BORDER);

			tableBuilder.addValueToCell("    Статус выплат", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
			tableBuilder.addValueToCell(CreditBuilderFormatter.getPaymentState(cais.getAccountPaymentStatus(), PDF_EMTY)  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
			tableBuilder.addEmptyValueToCell(3, 1, PDFTableStyles.CELL_WITHOUT_BORDER);

			tableBuilder.addValueToCell("     Непогашеный остаток", 2, 1, BLACK_B_8, AL_LEFT);
			tableBuilder.addValueToCell(getEmptyStrIfEmpty(MoneyFunctions.getFormatAmountString(cais.getOutstandingBalance())) , 1, 1, BLACK_8, AL_LEFT);
			tableBuilder.addEmptyValueToCell(3, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
			tableBuilder.addToPage(TableBreakRule.wholeTableInPage);

			tableBuilder = builder.getTableBuilderInstance(6, fontPath, new ParagraphStyle(10, 0f, 5f, 0f),100f);
			tableBuilder.addEmptyValueToCell(6, 2, PDFTableStyles.CELL_WITHOUT_BORDER);
			tableBuilder.addValueToCell("   Данные о финансировании", 3, 1, GRAY_12, AL_LEFT, BLACK_LEFT_8_3);
			tableBuilder.addEmptyValueToCell(3, 1, PDFTableStyles.CELL_WITHOUT_BORDER,BLACK_RIGHT_8_3);
			tableBuilder.addEmptyValueToCell(6, 1, PDFTableStyles.CELL_WITHOUT_BORDER);

			tableBuilder.addValueToCell("    Тип финансирования ", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
			tableBuilder.addValueToCell(CreditBuilderFormatter.formatFinanceType(cais.getFinanceType(), PDF_EMTY)  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
			tableBuilder.addValueToCell("      Цель финансирования", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
			tableBuilder.addValueToCell(CreditBuilderFormatter.getPurposeOfFinance(cais.getPurposeOfFinance(), PDF_EMTY) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

			tableBuilder.addValueToCell("    Валюта", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
			tableBuilder.addValueToCell(getEmptyStrIfEmpty(cais.getCurrency())  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
			tableBuilder.addValueToCell("      Тип обеспечения", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
			tableBuilder.addValueToCell(CreditBuilderFormatter.formatTypeOfSecurity(cais.getTypeOfSecurity(), PDF_EMTY) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

			tableBuilder.addValueToCell("    Размер кредитования", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
			tableBuilder.addValueToCell(getEmptyStrIfEmpty(MoneyFunctions.getFormatAmountString(cais.getAmountOfFinance())) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
			tableBuilder.addValueToCell("       Застрахованная ссуда", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
			tableBuilder.addValueToCell(CreditBuilderFormatter.getInsuredLoan(cais.getInsuredLoan(), PDF_EMTY)  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

			tableBuilder.addValueToCell("    Лимит кредитования", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
			tableBuilder.addValueToCell(getEmptyStrIfEmpty(MoneyFunctions.getFormatAmountString(cais.getCreditLimit())), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
			tableBuilder.addValueToCell("     Размер страхового покрытия", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
			tableBuilder.addValueToCell(getEmptyStrIfEmpty(MoneyFunctions.getFormatAmountString(cais.getAmountInsuredLoan())), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

			tableBuilder.addValueToCell("    Срок кредитного договора", 2, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
			tableBuilder.addValueToCell(CreditBuilderFormatter.formatDuration(cais.getDuration(), cais.getDurationUnit(), PDF_EMTY), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
			tableBuilder.addValueToCell("      Процентная ставка ", 2, 1, BLACK_B_8, AL_LEFT);
			tableBuilder.addValueToCell(getEmptyStrIfEmpty(cais.getInterestRate()), 1, 1, BLACK_8, AL_LEFT);

			tableBuilder.addValueToCell("     Размер платежа", 2, 1, BLACK_B_8, AL_LEFT);
			tableBuilder.addValueToCell(getEmptyStrIfEmpty(MoneyFunctions.getFormatAmountString(cais.getInstalment())) , 1, 1, BLACK_8, AL_LEFT);
			tableBuilder.addEmptyValueToCell(3, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
			tableBuilder.addToPage(TableBreakRule.wholeTableInPage);

			tableBuilder = builder.getTableBuilderInstance(7, fontPath, new ParagraphStyle(7, 0f, 5f, 0f),100f);
			tableBuilder.addEmptyValueToCell(7, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
			tableBuilder.addEmptyValueToCell(7, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
			tableBuilder.addEmptyValueToCell(7, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
			tableBuilder.addValueToCell("  История счёта", 3, 1, BLACK_12, AL_LEFT, BLACK_LEFT_8_3);
			tableBuilder.addEmptyValueToCell(4, 1, PDFTableStyles.CELL_WITHOUT_BORDER,BLACK_RIGHT_8_3);

			tableBuilder.addValueToCell("      ДАТА В ИСТОРИИ", 1, 1, BLACK_5_5, AL_LEFT, GRAY_LEFT_8_3);
			tableBuilder.addValueToCell("СТАТУС ВЫПЛАТ", 2, 1, BLACK_5_5, AL_CENTER, GRAY_LINE_3);
			tableBuilder.addValueToCell("ОБЩАЯ СУММА\nЗАДОЛЖЕННОСТИ", 1, 1, BLACK_5_5, AL_CENTER, GRAY_LINE_3);
			tableBuilder.addValueToCell("ПРОСРОЧЕНАЯ\nЗАДОЛЖЕННОСТИ", 1, 1, BLACK_5_5, AL_CENTER, GRAY_LINE_3);
			tableBuilder.addValueToCell("РАЗМЕР\nПЛАТЕЖА", 1, 1, BLACK_5_5, AL_CENTER, GRAY_LINE_3);
			tableBuilder.addValueToCell("ЛИМИТ\nКРЕДИТОВАНИЯ", 1, 1, BLACK_5_5, AL_CENTER, GRAY_RIGHT_8_3);

			for(EnquiryResponseERIB.Consumers.S.CAIS.MontlyHistory hist: cais.getMontlyHistories())
			{
				tableBuilder.addValueToCell("      " + CreditBuilderFormatter.getDate(hist.getHistoryDate(), PDF_EMTY), 1, 1, BLACK_5_5, AL_LEFT, GRAY_LEFT_8_3);
				tableBuilder.addValueToCell(CreditBuilderFormatter.getPaymentState(hist.getAccountPaymentHistory(), PDF_EMTY), 2, 1, BLACK_5_5, AL_CENTER, GRAY_LINE_3);
				tableBuilder.addValueToCell(getEmptyStrIfEmpty(MoneyFunctions.getFormatAmountString(hist.getAccountBalance())), 1, 1, BLACK_5_5, AL_CENTER, GRAY_LINE_3);
				tableBuilder.addValueToCell(getEmptyStrIfEmpty(MoneyFunctions.getFormatAmountString(hist.getAccountBalance())), 1, 1, BLACK_5_5, AL_CENTER, GRAY_LINE_3);
				tableBuilder.addValueToCell(getEmptyStrIfEmpty(MoneyFunctions.getFormatAmountString(hist.getInstalment())), 1, 1, BLACK_5_5, AL_CENTER, GRAY_LINE_3);
				tableBuilder.addValueToCell(getEmptyStrIfEmpty(MoneyFunctions.getFormatAmountString(hist.getCreditLimit())), 1, 1, BLACK_5_5, AL_CENTER, GRAY_RIGHT_8_3);
			}
			tableBuilder.addToPage(TableBreakRule.wholeTableInPage);

			tableBuilder = builder.getTableBuilderInstance(4, fontPath, new ParagraphStyle(10, 0f, 5f, 0f),100f);
			tableBuilder.addEmptyValueToCell(4, 2, PDFTableStyles.CELL_WITHOUT_BORDER);
			tableBuilder.addValueToCell("   Данные о клиенте", 2, 1, BLACK_12, AL_LEFT, BLACK_LEFT_8_3);
			tableBuilder.addEmptyValueToCell(2, 1, PDFTableStyles.CELL_WITHOUT_BORDER,BLACK_RIGHT_8_3);
			tableBuilder.addEmptyValueToCell(4, 1, PDFTableStyles.CELL_WITHOUT_BORDER);

			List<EnquiryResponseERIB.Consumers.S.CAIS.Consumer> consumers = cais.getConsumers();
			if (!consumers.isEmpty())
			{
				EnquiryResponseERIB.Consumers.S.CAIS.Consumer consumer = consumers.get(0);

				tableBuilder.addValueToCell("    Тип владельца счёта", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
				tableBuilder.addValueToCell(CreditBuilderFormatter.getApplicantType(consumer.getAccountHolderType(), PDF_EMTY)  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
				tableBuilder.addValueToCell("      Место рождения", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
				tableBuilder.addValueToCell(getEmptyStrIfEmpty(consumer.getPlaceOfBirth()) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

				tableBuilder.addValueToCell("    Обращение", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
				tableBuilder.addValueToCell(CreditBuilderFormatter.getTitle(consumer.getTitle(), PDF_EMTY)  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
				tableBuilder.addValueToCell("      Пол", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
				tableBuilder.addValueToCell(CreditBuilderFormatter.getSex(consumer.getSex(), PDF_EMTY) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

				tableBuilder.addValueToCell("    Имя", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
				tableBuilder.addValueToCell(getEmptyStrIfEmpty(consumer.getName1()) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
				tableBuilder.addValueToCell("       Гражданство", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
				tableBuilder.addValueToCell(CreditBuilderFormatter.getCountry(consumer.getNationality(), PDF_EMTY)  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

				tableBuilder.addValueToCell("    Отчество", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
				tableBuilder.addValueToCell(getEmptyStrIfEmpty(consumer.getName2()) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
				tableBuilder.addValueToCell("     Согласие", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
				tableBuilder.addValueToCell(CreditBuilderFormatter.getConsentIndicato(consumer.getConsentFlag(), PDF_EMTY), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

				tableBuilder.addValueToCell("    Фамилия", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
				tableBuilder.addValueToCell(getSurNameFirstLitera(consumer.getSurname()), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
				tableBuilder.addValueToCell("      Дата получения согласия ", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
				tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(consumer.getDateConsentGiven(), PDF_EMTY), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

				tableBuilder.addValueToCell("    Девичья фамилия", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
				tableBuilder.addValueToCell(getEmptyStrIfEmpty(consumer.getAliasName()), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
				tableBuilder.addValueToCell("      Мобильный телефон", 1, 1, BLACK_B_8, AL_LEFT);
				tableBuilder.addValueToCell(getEmptyStrIfEmpty(consumer.getMobileTelNbr()), 1, 1, BLACK_8, AL_LEFT);

				tableBuilder.addValueToCell("     Дата рождения", 1, 1, BLACK_B_8, AL_LEFT);
				tableBuilder.addValueToCell("••/••/••••" , 1, 1, BLACK_8, AL_LEFT);
				tableBuilder.addEmptyValueToCell(2, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
				tableBuilder.addToPage(TableBreakRule.wholeTableInPage);
			}

			List<EnquiryResponseERIB.Consumers.S.CAPS> caps = s.getCAPS();
			if (!caps.isEmpty())
			{
				List<EnquiryResponseERIB.Consumers.S.CAPS.Consumer> consumerList = caps.get(0).getConsumers();
				if (!consumerList.isEmpty())
				{
					EnquiryResponseERIB.Consumers.S.CAPS.Consumer consumer1 = consumerList.get(0);
					List<EnquiryResponseERIB.Consumers.S.CAPS.Consumer.Address> addressList = consumer1.getAddresses();
					if (!addressList.isEmpty())
					{
						EnquiryResponseERIB.Consumers.S.CAPS.Consumer.Address address = addressList.get(0);
						addAddress(address, builder, fontPath, null, null);
					}

					tableBuilder = builder.getTableBuilderInstance(4, fontPath, new ParagraphStyle(10, 0f, 5f, 0f),100f);
					tableBuilder.addEmptyValueToCell(4, 2, PDFTableStyles.CELL_WITHOUT_BORDER);
					tableBuilder.addEmptyValueToCell(4, 2, PDFTableStyles.CELL_WITHOUT_BORDER);
					tableBuilder.addValueToCell("   Основное удостоверение личности", 2, 1, BLACK_12, AL_LEFT, BLACK_LEFT_8_3);
					tableBuilder.addEmptyValueToCell(2, 1, PDFTableStyles.CELL_WITHOUT_BORDER,BLACK_RIGHT_8_3);
					tableBuilder.addEmptyValueToCell(4, 1, PDFTableStyles.CELL_WITHOUT_BORDER);

					tableBuilder.addValueToCell("    Тип удост.личности", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
					tableBuilder.addValueToCell(CreditBuilderFormatter.formatTypeOfSecurity(consumer1.getPrimaryIDType(), PDF_EMTY) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
					tableBuilder.addValueToCell("       Место выдачи", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
					tableBuilder.addValueToCell(getEmptyStrIfEmpty(consumer1.getPrimaryIDIssuePlace())  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

					tableBuilder.addValueToCell("    Номер удост. личности", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
					tableBuilder.addValueToCell(getEmptyStrIfEmpty(consumer1.getPrimaryID()) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
					tableBuilder.addValueToCell("      Государственный орган", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
					tableBuilder.addValueToCell(getEmptyStrIfEmpty(consumer1.getPrimaryIDAuthority())  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

					tableBuilder.addValueToCell("   Дата выдачи", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
					tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(consumer1.getPrimaryIDIssueDate(), PDF_EMTY) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
					tableBuilder.addValueToCell("      Окончание срока действия", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
					tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(consumer1.getPrimaryIDExpiry(), PDF_EMTY)  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);
					tableBuilder.addToPage(TableBreakRule.wholeTableInPage);

					tableBuilder = builder.getTableBuilderInstance(4, fontPath, new ParagraphStyle(10, 0f, 5f, 0f),100f);
					tableBuilder.addEmptyValueToCell(4, 2, PDFTableStyles.CELL_WITHOUT_BORDER);
					tableBuilder.addEmptyValueToCell(4, 2, PDFTableStyles.CELL_WITHOUT_BORDER);
					tableBuilder.addValueToCell("   Второе удостоверение личности", 2, 1, BLACK_12, AL_LEFT, BLACK_LEFT_8_3);
					tableBuilder.addEmptyValueToCell(2, 1, PDFTableStyles.CELL_WITHOUT_BORDER,BLACK_RIGHT_8_3);
					tableBuilder.addEmptyValueToCell(4, 1, PDFTableStyles.CELL_WITHOUT_BORDER);

					tableBuilder.addValueToCell("    Тип удост.личности", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
					tableBuilder.addValueToCell(CreditBuilderFormatter.formatTypeOfSecurity(consumer1.getSecondaryIDType(), PDF_EMTY) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
					tableBuilder.addValueToCell("       Место выдачи", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
					tableBuilder.addValueToCell(getEmptyStrIfEmpty(consumer1.getSecondaryIDIssuePlace())  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

					tableBuilder.addValueToCell("    Номер удост. личности", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
					tableBuilder.addValueToCell(getEmptyStrIfEmpty(consumer1.getSecondaryID()) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
					tableBuilder.addValueToCell("      Государственный орган", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
					tableBuilder.addValueToCell(getEmptyStrIfEmpty(consumer1.getSecondaryIDAuthority())  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

					tableBuilder.addValueToCell("   Дата выдачи", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
					tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(consumer1.getSecondaryIDIssueDate(), PDF_EMTY) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
					tableBuilder.addValueToCell("      Окончание срока действия", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
					tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(consumer1.getSecondaryIDExpiry(), PDF_EMTY)  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);
					tableBuilder.addToPage(TableBreakRule.wholeTableInPage);

					tableBuilder = builder.getTableBuilderInstance(4, fontPath, new ParagraphStyle(10, 0f, 5f, 0f),100f);
					tableBuilder.addEmptyValueToCell(4, 2, PDFTableStyles.CELL_WITHOUT_BORDER);
					tableBuilder.addEmptyValueToCell(4, 2, PDFTableStyles.CELL_WITHOUT_BORDER);
					tableBuilder.addValueToCell("   Другое удостоверение личности", 2, 1, BLACK_12, AL_LEFT, BLACK_LEFT_8_3);
					tableBuilder.addEmptyValueToCell(2, 1, PDFTableStyles.CELL_WITHOUT_BORDER,BLACK_RIGHT_8_3);
					tableBuilder.addEmptyValueToCell(4, 1, PDFTableStyles.CELL_WITHOUT_BORDER);

					tableBuilder.addValueToCell("    Номер водительского удост.", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
					tableBuilder.addValueToCell(getEmptyStrIfEmpty(consumer1.getDrivingLicenceNbr()) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
					tableBuilder.addValueToCell("      Номер.свид.госуд. пенс.стр.", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
					tableBuilder.addValueToCell(getEmptyStrIfEmpty(consumer1.getPensionNbr())  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

					tableBuilder.addValueToCell("    Номер свид.мед.страхования.", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
					tableBuilder.addValueToCell(getEmptyStrIfEmpty(consumer1.getMedicalNbr()) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
					tableBuilder.addValueToCell("      ЕГРН индивид предпр.", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
					tableBuilder.addValueToCell(getEmptyStrIfEmpty(consumer1.getPrivateEntrepreneurEGRN())  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

					tableBuilder.addValueToCell("    Номер свид..индив.предпр.", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
					tableBuilder.addValueToCell(getEmptyStrIfEmpty(consumer1.getPrivateEntrepreneurNbr())  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
					tableBuilder.addValueToCell("      Предыдущий номер удостов \u00a0\u00a0\u00a0\u00a0\u00a0\u00a0личности", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
					tableBuilder.addValueToCell(getEmptyStrIfEmpty(consumer1.getPrivateEntrepreneurNbr())  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

					tableBuilder.addValueToCell("    Дата выдачи свид.части предпр", 1, 1, BLACK_B_8, AL_LEFT);
					tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(consumer1.getPrivateEntrepreneurNbrIssueDate(), PDF_EMTY), 1, 1, BLACK_8, AL_LEFT);
					tableBuilder.addEmptyValueToCell(2, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
					tableBuilder.addToPage(TableBreakRule.wholeTableInPage);

					tableBuilder = builder.getTableBuilderInstance(4, fontPath, new ParagraphStyle(10, 0f, 5f, 0f),100f);
					tableBuilder.addEmptyValueToCell(4, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
					tableBuilder.addEmptyValueToCell(4, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
					tableBuilder.addValueToCell("   Комментарии", 1, 1, BLACK_12, AL_LEFT, BLACK_LEFT_8_3);
					tableBuilder.addEmptyValueToCell(1, 1, PDFTableStyles.CELL_WITHOUT_BORDER, BLACK_RIGHT_12_3);
					tableBuilder.addEmptyValueToCell(2, 1, PDFTableStyles.CELL_WITHOUT_BORDER);

					tableBuilder.addValueToCell("   Комментарии пользователя", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
					tableBuilder.addValueToCell(getEmptyStrIfEmpty(cais.getSubscriberComments()) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
					tableBuilder.addEmptyValueToCell(2, 1, PDFTableStyles.CELL_WITHOUT_BORDER);

					tableBuilder.addValueToCell("   Комментарии владельца счета", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
					tableBuilder.addValueToCell(getEmptyStrIfEmpty(cais.getAccountHolderComments()) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
					tableBuilder.addEmptyValueToCell(2, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
					tableBuilder.addToPage(TableBreakRule.wholeTableInPage);
				}
			}
			builder.newPage();
			n++;
		}
	}

	private void addMain(PDFBuilder builder, String resoursePath, Person person) throws PDFBuilderException
	{
		builder.setBackgroundColor(new BaseColor(BLUE_175));
		builder.addEmptyParagraph();
		ParagraphStyle titleStyle = new ParagraphStyle(5f, 50f, 0f, 0f);
		ParagraphStyle subStringRihgtStyle = new ParagraphStyle(5f, 50f, 0f, 0f,Alignment.right);
		// Титульный лист
		builder.addParagraph(DateHelper.formatDateWithMonthString(DateHelper.getCurrentDate()),subStringRihgtStyle, WHITE_11);
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);
		builder.addPhrase("\n\n");
		builder.addParagraph("ОТЧЕТ БЮРО", titleStyle, WHITE_42);
		builder.addParagraph("КРЕДИТНЫХ", titleStyle, WHITE_42);
		builder.addParagraph("ИСТОРИЙ", titleStyle, WHITE_42);

		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);
		builder.addPhrase("Для ", WHITE_11);
		String  fio = person.getFirstName() + " "+ StringHelper.getEmptyIfNull(person.getPatrName() + " " + person.getSurName().substring(0,1)+".");
		builder.addPhrase(fio, WHITE_11);
		//Содеражние
		builder.addEmptyParagraph(PDFBuilder.BASE_PARAGRAPH);
		ParagraphStyle contentStyle = new ParagraphStyle(15f, 20f, 50f, 20f,Alignment.center);
		builder.addParagraph("СОДЕРЖАНИЕ", contentStyle, WHITE_9_5);
		builder.addEmptyParagraph(PDFBuilder.LONG_PARAGRAPH);
		builder.addImage(resoursePath + "\\bki-main.gif", 84, 50, Alignment.left);
}

	private void addAddress(EnquiryResponseERIB.Consumers.S.CAPS.Consumer.Address address, PDFBuilder builder, String fontPath, Integer addressI, Integer addressesSize) throws PDFBuilderException, BusinessException, ParseException
	{
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(4, fontPath, new ParagraphStyle(10, 0f, 5f, 0f),100f);
		tableBuilder.addEmptyValueToCell(4, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addEmptyValueToCell(4, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
		if (addressI == null || addressesSize == null)
			tableBuilder.addValueToCell("   Адрес", 2, 1, GRAY_12, AL_LEFT, BLACK_LEFT_8_3);
		else
			tableBuilder.addValueToCell("   Адрес " + addressI + " из " + addressesSize, 2, 1, GRAY_12, AL_LEFT, BLACK_LEFT_8_3);
		tableBuilder.addEmptyValueToCell(2, 1, AL_LEFT, BLACK_RIGHT_8_3);

		tableBuilder.addValueToCell("    Тип адреса", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
		tableBuilder.addValueToCell(CreditBuilderFormatter.getAddressType(address.getAddressType(), PDF_EMTY)  , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
		tableBuilder.addValueToCell("      Район", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
		tableBuilder.addValueToCell(getEmptyStrIfEmpty(address.getLine4()) , 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

		tableBuilder.addValueToCell("    Текущий/предыдущий ", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
		tableBuilder.addValueToCell(CreditBuilderFormatter.getCurrentPreviousAddress(address.getAddressFlag(), PDF_EMTY), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
		tableBuilder.addValueToCell("      Республика/Регион"  , 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
		tableBuilder.addValueToCell(CreditBuilderFormatter.getRegionCode(address.getRegionCode(), PDF_EMTY), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

		tableBuilder.addValueToCell("    Номер дома", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
		tableBuilder.addValueToCell("•••", 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
		tableBuilder.addValueToCell("      Город", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
		tableBuilder.addValueToCell(getEmptyStrIfEmpty(address.getLine3()), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

		tableBuilder.addValueToCell("    Строение", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
		tableBuilder.addValueToCell(getEmptyStrIfEmpty(address.getBuildingNbr()), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
		tableBuilder.addValueToCell("      Страна", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
		tableBuilder.addValueToCell(CreditBuilderFormatter.getCountry(address.getCountry(), PDF_EMTY), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

		tableBuilder.addValueToCell("    Корпус", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
		tableBuilder.addValueToCell(getEmptyStrIfEmpty(address.getBuildingNbr()), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
		tableBuilder.addValueToCell("      Почтовый индекс", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
		tableBuilder.addValueToCell(getEmptyStrIfEmpty(address.getPostcode()), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

		tableBuilder.addValueToCell("    Номер квартиры", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
		tableBuilder.addValueToCell("•••", 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
		tableBuilder.addValueToCell("      Дата начала", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
		tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(address.getStartDate(), PDF_EMTY), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

		tableBuilder.addValueToCell("    Улица/проспект/проезд", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_8_3);
		tableBuilder.addValueToCell("•••", 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_12_3);
		tableBuilder.addValueToCell("      Дата окончания", 1, 1, BLACK_B_8, AL_LEFT, GRAY_LEFT_12_3);
		tableBuilder.addValueToCell(CreditBuilderFormatter.getDate(address.getEndDate(), PDF_EMTY), 1, 1, BLACK_8, AL_LEFT, GRAY_RIGHT_8_3);

		tableBuilder.addValueToCell("    Домашний телефон", 1, 1, BLACK_B_8, AL_LEFT);
		tableBuilder.addValueToCell(CreditBuilderFormatter.getPhone(address.getHomeTelNbr(), PDF_EMTY), 1, 1, BLACK_8, AL_LEFT);
		tableBuilder.addValueToCell("      Лет проживания по адресу", 1, 1, BLACK_B_8, AL_LEFT);
		tableBuilder.addValueToCell(getEmptyStrIfEmpty(address.getTimeAtAddress()), 1, 1, BLACK_8, AL_LEFT);
		tableBuilder.addEmptyValueToCell(4, 1, PDFTableStyles.CELL_WITHOUT_BORDER);
		tableBuilder.addToPage(TableBreakRule.wholeTableInPage);
	}

	private String getMainFontStr(String resPas)
	{
		return resPas + PDFBuilder.FONT_NAME;
	}

	private String getEmptyStrIfEmpty(Object obj)
	{
		if (obj == null || (obj instanceof  String && StringHelper.isEmpty((String)obj)))
			return PDF_EMTY;
		else
			return obj.toString();
	}

	private String getSurNameFirstLitera(String surname)
	{
		if (StringHelper.isEmpty(surname))
			return PDF_EMTY;

		StringBuilder sb = new StringBuilder();
		sb.append(surname.toCharArray()[0]);
		sb.append('.');
		return  sb.toString();
	}

	private EnquiryResponseERIB.Consumers.S getS(String report) throws BusinessException
	{
		CreditResponseParser parser = new CreditResponseParser(false, true);
		try
		{
			EnquiryResponseERIB reportObj = (EnquiryResponseERIB) parser.parse(report);
			//Берем первый ибо будет приходить только 1н
			//Все поля в цепочке обязательные.
			if (reportObj.getConsumers()!=null)
				return reportObj.getConsumers().getSS().get(0);
			else
				return null;
		}
		catch (JAXBException e)
		{
			throw new BusinessException(e);
		}
	}

	private byte[] buildContents(byte[] stream, HashMap<String, Integer> contents, Font mainFont) throws BusinessException
	{
		ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
		PdfReader reader = null;
		PdfStamper stamper = null;
		try
		{
			reader = new PdfReader(stream);
			stamper = new PdfStamper(reader, arrayOutputStream);
			PdfPTable table = new PdfPTable(2);
			table.setTotalWidth(300);
			table.setLockedWidth(true);
			table.setHorizontalAlignment(Element.ALIGN_RIGHT);
			stamper.getAcroFields().addSubstitutionFont(mainFont.getBaseFont());
			table.getDefaultCell().setFixedHeight(30);
			table.getDefaultCell().setBorder(Rectangle.NO_BORDER);

			for (String chapter : contents.keySet())
			{
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
				Phrase pageNum = new Phrase(contents.get(chapter) + " | ", mainFont);
				pageNum.getFont().setColor(BaseColor.WHITE);
				table.addCell(pageNum);
				Phrase phrase = new Phrase(chapter, mainFont);
				phrase.getFont().setSize(10.8f);
				phrase.getFont().setColor(BaseColor.WHITE);
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
				table.addCell(phrase);
			}
			table.writeSelectedRows(0, -1, 190, 402, stamper.getOverContent(1));
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
		finally
		{
			try
			{
				if (stamper!=null)
					stamper.close();
			} catch (Exception ignore){}
			if (reader!=null)
				reader.close();
			try
			{
				arrayOutputStream.close();
			} catch (IOException e)
			{
				throw new BusinessException(e);
			}
		}
		return arrayOutputStream.toByteArray();
	}
}
