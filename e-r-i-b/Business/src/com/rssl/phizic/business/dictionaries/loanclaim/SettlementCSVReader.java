package com.rssl.phizic.business.dictionaries.loanclaim;

import com.csvreader.CsvReader;
import com.rssl.phizic.gate.loanclaim.dictionary.Settlement;

import java.io.IOException;
import java.math.BigInteger;

/**
 * @author Nady
 * @ created 25.05.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 *  ����� csv-�����, ��� �������� ������ ����������� "���������� ������"
 *  -----------------------------------------------
 *  ������ csv-������:
 *  1� �������: NAME - �������� ��������� ������� (����������� ������)
 *  2� �������: SOCR - �������� ���� ��������� ������� (����������� ������)
 *  3� �������: Region - ����� �������, � �������� ��������� ������ ���������� �����
 *  4� �������: Area - ��� ������, � �������� ��������� ������ ��������� �����
 *  5� �������: City - ��� ������, � �������� ��������� ������ ��������� �����
 *  6� �������: Settlement - ��� ����������� ������
 *  7� �������: Street - ��� �����(������ ��������, ���� ����)
 *  8� �������: Level -  ��� ����������� (Settlement)
 *  9� �������: Type - ����� ���� ��������� ������� (����������� ������)
 *  -----------------------------------------------
 */
public class SettlementCSVReader extends AbstractAddressDictionaryReader
{
	public SettlementCSVReader(CsvReader csvReader, String searchWordSeparators) throws IOException
	{
		super(csvReader, searchWordSeparators);
	}

	@Override
	protected Settlement readRecord() throws IOException
	{
		Settlement settlement = new Settlement();
		String codeRegion = csvReader.get(2);
		String codeArea = csvReader.get(3);
		String codeCity = csvReader.get(4);
		String codeSettlement = csvReader.get(5);
		settlement.setName(csvReader.get(0));
		settlement.setRegion(codeRegion);
		settlement.setArea(new BigInteger(buildCodeDictionary(codeRegion, codeArea, null, null,null)));
		settlement.setCity(new BigInteger(buildCodeDictionary(codeRegion, codeArea, codeCity, null,null)));
		settlement.setCode(buildCodeDictionary(codeRegion, codeArea, codeCity, codeSettlement, null));
		settlement.setTypeOfLocality(csvReader.get(8));

		hasNext = csvReader.readRecord();
		return settlement;
	}
}
