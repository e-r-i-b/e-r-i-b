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
 *  Ридер csv-файла, для загрузки записи справочника "Населенные пункты"
 *  -----------------------------------------------
 *  Формат csv-строки:
 *  1я колонка: NAME - название адресного объекта (населенного пункта)
 *  2я колонка: SOCR - название типа адресного объекта (населенного пункта)
 *  3я колонка: Region - номер региона, к которому относится данный населенный пункт
 *  4я колонка: Area - код района, к которому относится данный населеный пункт
 *  5я колонка: City - код города, к которому относится данный населеный пункт
 *  6я колонка: Settlement - код населенного пункта
 *  7я колонка: Street - код улицы(пустое значение, либо нули)
 *  8я колонка: Level -  тип справочника (Settlement)
 *  9я колонка: Type - номер типа адресного объекта (населенного пункта)
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
