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
 *  Ридер csv-файла, для загрузки записи справочника "Районы"
 *  -----------------------------------------------
 *  Формат csv-строки:
 *  1я колонка: NAME - название адресного объектаа (района)
 *  2я колонка: SOCR - название типа адресного объекта (района)
 *  3я колонка: Region - номер региона, к которому относится данный район
 *  4я колонка: Area - код района
 *  5я колонка: City - код города(пустое значение, либо нули)
 *  6я колонка: Settlement - код населенного пункта(пустое значение, либо нули)
 *  7я колонка: Street - код улицы(пустое значение, либо нули)
 *  8я колонка: Level -  тип справочника (Area)
 *  9я колонка: Type - номер типа адресного объекта (района)
 *  -----------------------------------------------
 */
public class AreaCSVReader extends AbstractAddressDictionaryReader
{

	public AreaCSVReader(CsvReader csvReader, String searchWordSeparators) throws IOException
	{
		super(csvReader, searchWordSeparators);
	}

	/**
	 * Прочитать строку csv-файла
	 * @return  запись справочника "Районы"
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
