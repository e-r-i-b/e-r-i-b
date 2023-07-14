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
 *  –идер csv-файла, дл€ загрузки записи справочника "√ород"
 *  -----------------------------------------------
 *  ‘ормат csv-строки:
 *  1€ колонка: NAME - название адресного объекта (города)
 *  2€ колонка: SOCR - название типа адресного объекта (города)
 *  3€ колонка: Region - номер региона, к которому относитс€ данный город
 *  4€ колонка: Area - код района, к которому относитс€ данный город
 *  5€ колонка: City - код города
 *  6€ колонка: Settlement - код населенного пункта(пустое значение, либо нули)
 *  7€ колонка: Street - код улицы(пустое значение, либо нули)
 *  8€ колонка: Level -  тип справочника (City)
 *  9€ колонка: Type - номер типа адресного объекта (города)
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
