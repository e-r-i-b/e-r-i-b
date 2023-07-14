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
 *  Ридер csv-файла, для загрузки записи справочника "Улицы"
 *  -----------------------------------------------
 *  Формат csv-строки:
 *  1я колонка: NAME - название адресного объекта (Улицы)
 *  2я колонка: SOCR - название типа адресного объекта (Улицы)
 *  3я колонка: Region - номер региона, к которому относится данная улица
 *  4я колонка: Area - код района, к которому относится данная улица
 *  5я колонка: City - код города, к которому относится данная улица
 *  6я колонка: Settlement - код населенного пункта, к которому относится данная улица
 *  7я колонка: Street - код улицы
 *  8я колонка: Type - номер типа адресного объекта (улицы)
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
