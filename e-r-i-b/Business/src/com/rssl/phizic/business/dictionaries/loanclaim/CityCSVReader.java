package com.rssl.phizic.business.dictionaries.loanclaim;

import com.csvreader.CsvReader;
import com.rssl.phizic.gate.loanclaim.dictionary.City;

import java.io.IOException;
import java.math.BigInteger;

/**
 * @author Nady
 * @ created 25.05.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 *  ����� csv-�����, ��� �������� ������ ����������� "�����"
 *  -----------------------------------------------
 *  ������ csv-������:
 *  1� �������: NAME - �������� ��������� ������� (������)
 *  2� �������: SOCR - �������� ���� ��������� ������� (������)
 *  3� �������: Region - ����� �������, � �������� ��������� ������ �����
 *  4� �������: Area - ��� ������, � �������� ��������� ������ �����
 *  5� �������: City - ��� ������
 *  6� �������: Settlement - ��� ����������� ������(������ ��������, ���� ����)
 *  7� �������: Street - ��� �����(������ ��������, ���� ����)
 *  8� �������: Level -  ��� ����������� (City)
 *  9� �������: Type - ����� ���� ��������� ������� (������)
 *  -----------------------------------------------
 */
public class CityCSVReader extends AbstractAddressDictionaryReader
{

	public CityCSVReader(CsvReader csvReader, String searchWordSeparators) throws IOException
	{
		super(csvReader, searchWordSeparators);
	}

	@Override
	protected City readRecord() throws IOException
	{
		City city = new City();
		String codeRegion = csvReader.get(2);
		String codeArea = csvReader.get(3);
		String codeCity = csvReader.get(4);
		city.setName(csvReader.get(0));
		city.setRegion(codeRegion);
		city.setArea(new BigInteger(buildCodeDictionary(codeRegion, codeArea, null, null, null)));
		city.setCode(buildCodeDictionary(codeRegion, codeArea, codeCity, null, null));
		city.setTypeOfCity(csvReader.get(8));

		hasNext = csvReader.readRecord();
		return city;
	}
}
