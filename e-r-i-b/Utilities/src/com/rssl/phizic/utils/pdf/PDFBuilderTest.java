package com.rssl.phizic.utils.pdf;

import com.rssl.phizic.utils.chart.ChartBuilderTest;
import com.rssl.phizic.utils.chart.DocumentOrientation;
import junit.framework.TestCase;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author akrenev
 * @ created 02.03.2012
 * @ $Author$
 * @ $Revision$
 *
 * тест для проверки PDFBuilder'а
 */
public class PDFBuilderTest extends TestCase
{
	private String imgPath;
	private String logoPath;
	private String fontPath;

	public byte[] testPDFBuilder() throws IOException, PDFBuilderException
	{
//		byte[] data = createPDF();
//		writeToFile(data, "C:\\temp\\PDFBuilderTestResult.pdf");
		return createPDF();
	}

	private byte[] createPDF() throws PDFBuilderException, IOException
	{
		logoPath = "D:\\NEW_IKFL\\v1_18\\WebResources\\web\\images\\sbrf\\";
		imgPath = "C:\\temp\\";
		fontPath = "C:\\Windows\\Fonts\\";
		
		HeaderFooterBuilder headerFooterBuilder = new HeaderFooterBuilder();
		headerFooterBuilder.addImageInstance(logoPath + "logoSBRFNew.png", 220, 60, Alignment.right);
		PDFBuilder builder = PDFBuilder.getInstance(headerFooterBuilder, fontPath + PDFBuilder.FONT_NAME, 12, DocumentOrientation.VERTICAL);

		ParagraphStyle mainTitleParagraph = new ParagraphStyle(15f, 0f, 30f, 25f, Alignment.center);
		ParagraphStyle baseParagraph = new ParagraphStyle(15f, 0f, 10f, 25f);
		ParagraphStyle titleParagraph = new ParagraphStyle(15f, 24f, 4f, 0f);
		ParagraphStyle subTitleParagraph = new ParagraphStyle(15f, 15f, 0f, 0f);
		ParagraphStyle descrParagraph = new ParagraphStyle(15f, 0f, 10f, 0f);

		FontStyle mainTitleFont = new FontStyle(16);
		FontStyle titleFont = new FontStyle(16, FontDecoration.bold);
		FontStyle boldTextFont = new FontStyle(12, FontDecoration.bold);
		FontStyle subTitleFont = new FontStyle(14, FontDecoration.bold);
		FontStyle linkFont = new FontStyle(12, FontDecoration.underline, Color.BLUE);

		builder.addParagraph("Уважаемый(-ая) Иван Петрович!", mainTitleParagraph, mainTitleFont);
	    builder.addParagraph("Благодарим Вас, что Вы выбрали ОАО «Сбербанк России» своим финансовым партнёром.", baseParagraph);
		builder.addParagraph("Финансовый план основывается на предоставленной Вами информации.", descrParagraph);
		builder.addParagraph("Мы надеемся, что выбранные продукты помогут Вам достичь финансовых целей.", descrParagraph);

		builder.addParagraph("Мои цели", titleParagraph, titleFont);
		drawTargetsTable(builder);

		builder.addParagraph("Ваш риск-профиль – Уравновешенный", titleParagraph, titleFont);
		builder.addParagraph("Вы готовы вложить часть средств в рискованные активы для получения более высокого дохода по сравнению с вкладами. Поэтому около половины портфеля рекомендуем Вам разместить в высокодоходные активы.", baseParagraph);

		builder.addParagraph("Рекомендуемое распределение средств", titleParagraph, titleFont);
		builder.addImage(imgPath + "diagram1.jpg", 600, 300, Alignment.center);

		builder.newPage();
		builder.addParagraph("Месячный бюджет", titleParagraph, titleFont);
		builder.addImage(imgPath + "diagram2.jpg", 600, 300, Alignment.center);
		builder.addParagraph("Рекомендуем Вам поддерживать на сберегательном счете сумму 105000 руб., которая в совокупности с лимитом по кредитной карте является надежным резервом на случай непредвиденных обстоятельств. Предлагаем Вам оформить кредитную карту Сбербанка.", descrParagraph);

		builder.newPage();
		builder.addParagraph("Портфель «Стартовый капитал»", titleParagraph, titleFont);
		builder.addParagraph("Ниже указаны выбранные Вами продукты для первоначального распределения свободных средств.", baseParagraph);
		builder.addParagraph("Полная стоимость портфеля «Стартовый капитал»: 2 930 000,00 руб.", baseParagraph, boldTextFont);

		builder.addParagraph("Распределение средств в портфеле", subTitleParagraph, subTitleFont);
		drawResources(builder);
		builder.addImage(imgPath + "diagram3.jpg", 600, 300, Alignment.center);

		builder.addParagraph("Портфель «Ежеквартальные вложения»", titleParagraph, titleFont);
		builder.addParagraph("Ниже указаны выбранные Вами продукты для распределения свободных средств на ежеквартальной основе.", baseParagraph);
		builder.addParagraph("Для оплаты периодических страховых взносов по программам накопительного страхования жизни Вам ежеквартально требуется накапливать 150 000,00 рублей. Эту сумму рекомендуем размещать на Сберегательном счёте.", baseParagraph);
		builder.addParagraph("Полная стоимость портфеля «Ежеквартальные вложения»: 400 000,00 руб.", baseParagraph, boldTextFont);

		builder.addParagraph("Распределение средств в портфеле", subTitleParagraph, subTitleFont);
		drawResources(builder);
		ChartBuilderTest chartBiulder = new ChartBuilderTest();
		BufferedImage image = chartBiulder.generatePieChar();
		builder.addImage(image, 540, 270, Alignment.center);

		builder.addParagraph("Обращаем Ваше внимание, что стоимость инвестиционных продуктов может как увеличиваться, так и уменьшаться. Доход, полученный в прошлом, не гарантирует доходность в будущем.", baseParagraph);
		builder.addParagraph("Сформировать Ваш финансовый портфель и оформить документы Вам поможет персональный менеджер.", baseParagraph);
		builder.addHyperlink("Список отделений с выделенными зонами Сбербанк-Премьер.", "http://www.sberbank.ru/", baseParagraph, linkFont);
		builder.addParagraph(" ", baseParagraph);
		builder.addParagraph("Мы очень ценим наше сотрудничество и сделаем всё возможное, чтобы оно было комфортным и результативным.", baseParagraph);

		builder.addParagraph("С уважением, ОАО «Сбербанк России».", Alignment.right);
		builder.addParagraph("Дата: 09.12.2011.", Alignment.right);

	    return builder.build();
	}

	private void drawTargetsTable(PDFBuilder builder) throws PDFBuilderException
	{
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(4, fontPath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);
		FontStyle tbHeaderFont = new FontStyle(12, FontDecoration.normal, Color.GRAY);
		FontStyle tbTextFont = new FontStyle(12);

		CellStyle cellStyle = new CellStyle();
		cellStyle.setBorderWidth(0);
		cellStyle.setBorderWidthBottom(0.2f);
		cellStyle.setBorderColor(Color.GRAY);

		tableBuilder.addValueToCell("Цель", tbHeaderFont, cellStyle);
		tableBuilder.addValueToCell("Дата приобретения", tbHeaderFont, cellStyle);
		tableBuilder.addValueToCell("Источник средств", tbHeaderFont, cellStyle);
		tableBuilder.addValueToCell("Сумма", tbHeaderFont, cellStyle);

		tableBuilder.addValueToCell("Покупка недвижимости", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("10.10.2012", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("Ипотека", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("4000000,00 руб", tbTextFont, cellStyle);

		tableBuilder.addValueToCell("Покупка автомобиля", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("05.10.2012", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("Автокредит", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("2000,00 руб", tbTextFont, cellStyle);

		/*Отключил для исправления запроса CHG041239: Отображать информацию о продукте в отчете пдф на одной странице.*/
		/*Необходимо добавить заголовок параграфа*/
		/*builder.addTable(tableBuilder);*/
	}

	private void drawResources(PDFBuilder builder) throws PDFBuilderException
	{
		FontStyle tbTextFont = new FontStyle(12);
		FontStyle boldTextFont = new FontStyle(12, FontDecoration.bold);
		ParagraphStyle resTitleParagraph = new ParagraphStyle(15f, 20f, 0f, 0f);
		ParagraphStyle baseParagraph = new ParagraphStyle(15f, 0f, 10f, 25f);

		CellStyle cellStyle = new CellStyle();
		cellStyle.setBorderWidth(0);
		cellStyle.setBorderWidthBottom(0.2f);
		cellStyle.setBorderColor(Color.GRAY);
		cellStyle.setMinimumHeight(32f);

		CellStyle cellImgStyle = new CellStyle();
		cellImgStyle.setBorderWidth(0);
		cellImgStyle.setBorderWidthBottom(0.2f);
		cellImgStyle.setBorderColor(Color.GRAY);
		cellImgStyle.setMinimumHeight(66f);


		String iconPath = "D:\\NEW_IKFL\\v1_18\\WebResources\\web\\skins\\sbrf\\images\\pfp\\";

		int[] widths = {70, 200, 90};

////////////////////////////////////
		builder.addParagraph("Комплексный страховой продукт «Страховой индекс плюс»", resTitleParagraph, boldTextFont);
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(3, fontPath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);

		tableBuilder.setWidths(360, widths);

		tableBuilder.addImageToCurrentRow(iconPath + "icon_pmnt_loans.png", 1, 2, cellImgStyle);
		tableBuilder.addValueToCell("Вклад «Страховой индекс»", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("12 000,00 руб", tbTextFont, cellStyle);

		tableBuilder.addValueToCell("Страхование жизни «Allianz Indexx»", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("100 000,00 руб", tbTextFont, cellStyle);

		/*Отключил для исправления запроса CHG041239: Отображать информацию о продукте в отчете пдф на одной странице.*/
		/*Необходимо добавить заголовок параграфа*/
		/*builder.addTable(tableBuilder);*/
//////////////////////////////////////

////////////////////////////////////
		builder.addParagraph("Комплексный инвестиционный продукт «Страховой индекс»", resTitleParagraph, boldTextFont);
		tableBuilder = builder.getTableBuilderInstance(3, fontPath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);

		tableBuilder.setWidths(360, widths);

		tableBuilder.addImageToCurrentRow(iconPath + "icon_pmnt_loans.png", 1, 2, cellImgStyle);
		tableBuilder.addValueToCell("Страхование жизни  «Страховой индекс»", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("100 000,00 руб", tbTextFont, cellStyle);

		tableBuilder.addValueToCell("ПИФ «Allianz Indexx»", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("20 000 000,00 руб", tbTextFont, cellStyle);

		/*Отключил для исправления запроса CHG041239: Отображать информацию о продукте в отчете пдф на одной странице.*/
		/*Необходимо добавить заголовок параграфа*/
		/*builder.addTable(tableBuilder);*/
//////////////////////////////////////

////////////////////////////////////
		builder.addParagraph("Паевый инвестиционный фонд", resTitleParagraph, boldTextFont);
		tableBuilder = builder.getTableBuilderInstance(3, fontPath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);

		tableBuilder.setWidths(360, widths);

		tableBuilder.addImageToCurrentRow(iconPath + "icon_depositariy.png", 64, 64, cellImgStyle);
		tableBuilder.addValueToCell("ПИФ «Allianz Indexx»", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("100 000,00 руб", tbTextFont, cellStyle);

		/*Отключил для исправления запроса CHG041239: Отображать информацию о продукте в отчете пдф на одной странице.*/
		/*Необходимо добавить заголовок параграфа*/
		/*builder.addTable(tableBuilder);*/
//////////////////////////////////////

////////////////////////////////////
		builder.addParagraph("Обезличенный Металлический счет", resTitleParagraph, boldTextFont);
		tableBuilder = builder.getTableBuilderInstance(3, fontPath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);

		tableBuilder.setWidths(360, widths);

		tableBuilder.addImageToCurrentRow(iconPath + "icon_depositariy.png", 64, 64, cellImgStyle);
		tableBuilder.addValueToCell("ОМС «Золото AUR»", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("300 000,00 руб", tbTextFont, cellStyle);

		/*Отключил для исправления запроса CHG041239: Отображать информацию о продукте в отчете пдф на одной странице.*/
		/*Необходимо добавить заголовок параграфа*/
		/*builder.addTable(tableBuilder);*/
//////////////////////////////////////

		builder.addParagraph(" ");
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
