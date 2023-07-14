package com.rssl.phizic.business.dictionaries.loanclaim;

import com.csvreader.CsvReader;
import com.rssl.phizic.gate.loanclaim.dictionary.Street;

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
 *  1� �������: NAME - �������� ��������� ������� (�����)
 *  2� �������: SOCR - �������� ���� ��������� ������� (�����)
 *  3� �������: Region - ����� �������, � �������� ��������� ������ �����
 *  4� �������: Area - ��� ������, � �������� ��������� ������ �����
 *  5� �������: City - ��� ������, � �������� ��������� ������ �����
 *  6� �������: Settlement - ��� ����������� ������, � �������� ��������� ������ �����
 *  7� �������: Street - ��� �����
 *  8� �������: Type - ����� ���� ��������� ������� (�����)
 *  -----------------------------------------------
 */
public class StreetCSVReader extends AbstractAddressDictionaryReader
{
	public StreetCSVReader(CsvReader csvReader, String wordSeparators) throws IOException
	{
		super(csvReader, wordSeparators);
	}

	@Override
	protected Street readRecord() throws IOException
	{
		Street street = new Street();
		String codeRegion = csvReader.get(2);
		String codeArea = csvReader.get(3);
		String codeCity = csvReader.get(4);
		String codeSettlement = csvReader.get(5);
		String codeStreet = csvReader.get(6);
		street.setName(csvReader.get(0));
		street.setRegion(codeRegion);
		street.setArea(new BigInteger(buildCodeDictionary(codeRegion, codeArea, null, null,null)));
		street.setCity(new BigInteger(buildCodeDictionary(codeRegion, codeArea, codeCity, null,null)));
		street.setSettlement(new BigInteger(buildCodeDictionary(codeRegion, codeArea, codeCity, codeSettlement,null)));
		street.setCode(buildCodeDictionary(codeRegion, codeArea, codeCity, codeSettlement, codeStreet));
		street.setTypeOfStreet(csvReader.get(7));

		hasNext = csvReader.readRecord();
		return street;
	}
}
