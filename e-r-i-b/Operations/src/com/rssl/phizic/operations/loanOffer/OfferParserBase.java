package com.rssl.phizic.operations.loanOffer;

import com.csvreader.CsvReader;
import com.rssl.phizic.utils.StringHelper;
import sun.nio.cs.StandardCharsets;

import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * User: Moshenko
 * Date: 10.06.2011
 * Time: 9:40:15
 * Ѕазовый класс дл€ парсинга предодобренных  предложений
 */
abstract public class OfferParserBase
{
    //ридер дл€ работы чтени€ cvs файлв
    protected CsvReader reader;

    //ожидаемое колл-во столбцов в записи
    protected int columnCount;

    /**
     * инициализируем cvs ридер
     * @param inputStream вход€щий поток
     * @param delimiter разделитель
     * @param columnCount ожидаемое чисто параметров в строке
     */
    public void initialize(InputStream inputStream,char delimiter,int columnCount)
    {

        this.columnCount = columnCount;
        //согласно –ќ используетс€ стрндартна€ кодировка windows (MS1251)
	    StandardCharsets str = new StandardCharsets();
	    Charset charset = str.charsetForName("windows-1251");
        reader = new CsvReader(inputStream,delimiter,charset);
    }

    /**
     * ѕровер€ет количество записей
     * в текущей позиции ридера
     * @return
     */
    public boolean countTest()
    {
        if (columnCount == reader.getColumnCount())
            return true;
        else
            return false;
    }

     /**
     * если есть запись в ридере то возвращает true
     * @return
     */
    public boolean hasNext()
    {
       if (StringHelper.isEmpty(reader.getRawRecord()))
           return false;
       else
           return true;
    }

    /**
     * номер текущей записи ридера
     * @return
     */
    public long getCurrentRecord()
    {
      return reader.getCurrentRecord();
    }

	/**
	 * @return “екуща€ строка.
	 */
	public String getRawRecord()
	{
		return reader.getRawRecord();
	}

}
