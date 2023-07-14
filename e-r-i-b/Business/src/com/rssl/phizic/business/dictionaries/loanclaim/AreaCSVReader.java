package com.rssl.phizic.business.dictionaries.loanclaim;

import com.csvreader.CsvReader;
import com.rssl.phizic.gate.loanclaim.dictionary.Area;

import java.io.IOException;

/**
 * @author Nady
 * @ created 25.05.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 *  ����� csv-�����, ��� �������� ������ ����������� "������"
 *  -----------------------------------------------
 *  ������ csv-������:
 *  1� �������: NAME - �������� ��������� �������� (������)
 *  2� �������: SOCR - �������� ���� ��������� ������� (������)
 *  3� �������: Region - ����� �������, � �������� ��������� ������ �����
 *  4� �������: Area - ��� ������
 *  5� �������: City - ��� ������(������ ��������, ���� ����)
 *  6� �������: Settlement - ��� ����������� ������(������ ��������, ���� ����)
 *  7� �������: Street - ��� �����(������ ��������, ���� ����)
 *  8� �������: Level -  ��� ����������� (Area)
 *  9� �������: Type - ����� ���� ��������� ������� (������)
 *  -----------------------------------------------
 */
public class AreaCSVReader extends AbstractAddressDictionaryReader
{

	public AreaCSVReader(CsvReader csvReader, String searchWordSeparators) throws IOException
	{
		super(csvReader, searchWordSeparators);
	}

	/**
	 * ��������� ������ csv-�����
	 * @return  ������ ����������� "������"
	 */
	protected Area readRecord() throws IOException
	{
		Area area = new Area();
		String codeRegion = csvReader.get(2);
		String codeArea = csvReader.get(3);
		area.setName(csvReader.get(0));
		area.setRegion(codeRegion);
		area.setCode(buildCodeDictionary(codeRegion, codeArea, null, null, null));
		area.setTypeOfArea(csvReader.get(8));

		hasNext = csvReader.readRecord();
		return area;
	}
}
