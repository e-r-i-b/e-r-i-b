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
 * ���� ��� �������� PDFBuilder'�
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

		builder.addParagraph("���������(-��) ���� ��������!", mainTitleParagraph, mainTitleFont);
	    builder.addParagraph("���������� ���, ��� �� ������� ��� ��������� ������ ����� ���������� ��������.", baseParagraph);
		builder.addParagraph("���������� ���� ������������ �� ��������������� ���� ����������.", descrParagraph);
		builder.addParagraph("�� ��������, ��� ��������� �������� ������� ��� ������� ���������� �����.", descrParagraph);

		builder.addParagraph("��� ����", titleParagraph, titleFont);
		drawTargetsTable(builder);

		builder.addParagraph("��� ����-������� � ��������������", titleParagraph, titleFont);
		builder.addParagraph("�� ������ ������� ����� ������� � ����������� ������ ��� ��������� ����� �������� ������ �� ��������� � ��������. ������� ����� �������� �������� ����������� ��� ���������� � �������������� ������.", baseParagraph);

		builder.addParagraph("������������� ������������� �������", titleParagraph, titleFont);
		builder.addImage(imgPath + "diagram1.jpg", 600, 300, Alignment.center);

		builder.newPage();
		builder.addParagraph("�������� ������", titleParagraph, titleFont);
		builder.addImage(imgPath + "diagram2.jpg", 600, 300, Alignment.center);
		builder.addParagraph("����������� ��� ������������ �� �������������� ����� ����� 105000 ���., ������� � ������������ � ������� �� ��������� ����� �������� �������� �������� �� ������ �������������� �������������. ���������� ��� �������� ��������� ����� ���������.", descrParagraph);

		builder.newPage();
		builder.addParagraph("�������� ���������� �������", titleParagraph, titleFont);
		builder.addParagraph("���� ������� ��������� ���� �������� ��� ��������������� ������������� ��������� �������.", baseParagraph);
		builder.addParagraph("������ ��������� �������� ���������� �������: 2 930 000,00 ���.", baseParagraph, boldTextFont);

		builder.addParagraph("������������� ������� � ��������", subTitleParagraph, subTitleFont);
		drawResources(builder);
		builder.addImage(imgPath + "diagram3.jpg", 600, 300, Alignment.center);

		builder.addParagraph("�������� ��������������� ���������", titleParagraph, titleFont);
		builder.addParagraph("���� ������� ��������� ���� �������� ��� ������������� ��������� ������� �� �������������� ������.", baseParagraph);
		builder.addParagraph("��� ������ ������������� ��������� ������� �� ���������� �������������� ����������� ����� ��� ������������� ��������� ����������� 150 000,00 ������. ��� ����� ����������� ��������� �� �������������� �����.", baseParagraph);
		builder.addParagraph("������ ��������� �������� ��������������� ���������: 400 000,00 ���.", baseParagraph, boldTextFont);

		builder.addParagraph("������������� ������� � ��������", subTitleParagraph, subTitleFont);
		drawResources(builder);
		ChartBuilderTest chartBiulder = new ChartBuilderTest();
		BufferedImage image = chartBiulder.generatePieChar();
		builder.addImage(image, 540, 270, Alignment.center);

		builder.addParagraph("�������� ���� ��������, ��� ��������� �������������� ��������� ����� ��� �������������, ��� � �����������. �����, ���������� � �������, �� ����������� ���������� � �������.", baseParagraph);
		builder.addParagraph("������������ ��� ���������� �������� � �������� ��������� ��� ������� ������������ ��������.", baseParagraph);
		builder.addHyperlink("������ ��������� � ����������� ������ ��������-�������.", "http://www.sberbank.ru/", baseParagraph, linkFont);
		builder.addParagraph(" ", baseParagraph);
		builder.addParagraph("�� ����� ����� ���� �������������� � ������� �� ���������, ����� ��� ���� ���������� � ��������������.", baseParagraph);

		builder.addParagraph("� ���������, ��� ��������� ������.", Alignment.right);
		builder.addParagraph("����: 09.12.2011.", Alignment.right);

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

		tableBuilder.addValueToCell("����", tbHeaderFont, cellStyle);
		tableBuilder.addValueToCell("���� ������������", tbHeaderFont, cellStyle);
		tableBuilder.addValueToCell("�������� �������", tbHeaderFont, cellStyle);
		tableBuilder.addValueToCell("�����", tbHeaderFont, cellStyle);

		tableBuilder.addValueToCell("������� ������������", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("10.10.2012", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("�������", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("4000000,00 ���", tbTextFont, cellStyle);

		tableBuilder.addValueToCell("������� ����������", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("05.10.2012", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("����������", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("2000,00 ���", tbTextFont, cellStyle);

		/*�������� ��� ����������� ������� CHG041239: ���������� ���������� � �������� � ������ ��� �� ����� ��������.*/
		/*���������� �������� ��������� ���������*/
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
		builder.addParagraph("����������� ��������� ������� ���������� ������ ����", resTitleParagraph, boldTextFont);
		PDFTableBuilder tableBuilder = builder.getTableBuilderInstance(3, fontPath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);

		tableBuilder.setWidths(360, widths);

		tableBuilder.addImageToCurrentRow(iconPath + "icon_pmnt_loans.png", 1, 2, cellImgStyle);
		tableBuilder.addValueToCell("����� ���������� ������", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("12 000,00 ���", tbTextFont, cellStyle);

		tableBuilder.addValueToCell("����������� ����� �Allianz Indexx�", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("100 000,00 ���", tbTextFont, cellStyle);

		/*�������� ��� ����������� ������� CHG041239: ���������� ���������� � �������� � ������ ��� �� ����� ��������.*/
		/*���������� �������� ��������� ���������*/
		/*builder.addTable(tableBuilder);*/
//////////////////////////////////////

////////////////////////////////////
		builder.addParagraph("����������� �������������� ������� ���������� ������", resTitleParagraph, boldTextFont);
		tableBuilder = builder.getTableBuilderInstance(3, fontPath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);

		tableBuilder.setWidths(360, widths);

		tableBuilder.addImageToCurrentRow(iconPath + "icon_pmnt_loans.png", 1, 2, cellImgStyle);
		tableBuilder.addValueToCell("����������� �����  ���������� ������", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("100 000,00 ���", tbTextFont, cellStyle);

		tableBuilder.addValueToCell("��� �Allianz Indexx�", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("20 000 000,00 ���", tbTextFont, cellStyle);

		/*�������� ��� ����������� ������� CHG041239: ���������� ���������� � �������� � ������ ��� �� ����� ��������.*/
		/*���������� �������� ��������� ���������*/
		/*builder.addTable(tableBuilder);*/
//////////////////////////////////////

////////////////////////////////////
		builder.addParagraph("������ �������������� ����", resTitleParagraph, boldTextFont);
		tableBuilder = builder.getTableBuilderInstance(3, fontPath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);

		tableBuilder.setWidths(360, widths);

		tableBuilder.addImageToCurrentRow(iconPath + "icon_depositariy.png", 64, 64, cellImgStyle);
		tableBuilder.addValueToCell("��� �Allianz Indexx�", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("100 000,00 ���", tbTextFont, cellStyle);

		/*�������� ��� ����������� ������� CHG041239: ���������� ���������� � �������� � ������ ��� �� ����� ��������.*/
		/*���������� �������� ��������� ���������*/
		/*builder.addTable(tableBuilder);*/
//////////////////////////////////////

////////////////////////////////////
		builder.addParagraph("������������ ������������� ����", resTitleParagraph, boldTextFont);
		tableBuilder = builder.getTableBuilderInstance(3, fontPath + PDFBuilder.FONT_NAME, PDFTableStyles.BASE_PARAGRAPH);

		tableBuilder.setWidths(360, widths);

		tableBuilder.addImageToCurrentRow(iconPath + "icon_depositariy.png", 64, 64, cellImgStyle);
		tableBuilder.addValueToCell("��� ������� AUR�", tbTextFont, cellStyle);
		tableBuilder.addValueToCell("300 000,00 ���", tbTextFont, cellStyle);

		/*�������� ��� ����������� ������� CHG041239: ���������� ���������� � �������� � ������ ��� �� ����� ��������.*/
		/*���������� �������� ��������� ���������*/
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
