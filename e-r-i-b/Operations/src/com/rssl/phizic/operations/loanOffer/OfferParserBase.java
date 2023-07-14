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
 * ������� ����� ��� �������� ��������������  �����������
 */
abstract public class OfferParserBase
{
    //����� ��� ������ ������ cvs �����
    protected CsvReader reader;

    //��������� ����-�� �������� � ������
    protected int columnCount;

    /**
     * �������������� cvs �����
     * @param inputStream �������� �����
     * @param delimiter �����������
     * @param columnCount ��������� ����� ���������� � ������
     */
    public void initialize(InputStream inputStream,char delimiter,int columnCount)
    {

        this.columnCount = columnCount;
        //�������� �� ������������ ����������� ��������� windows (MS1251)
	    StandardCharsets str = new StandardCharsets();
	    Charset charset = str.charsetForName("windows-1251");
        reader = new CsvReader(inputStream,delimiter,charset);
    }

    /**
     * ��������� ���������� �������
     * � ������� ������� ������
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
     * ���� ���� ������ � ������ �� ���������� true
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
     * ����� ������� ������ ������
     * @return
     */
    public long getCurrentRecord()
    {
      return reader.getCurrentRecord();
    }

	/**
	 * @return ������� ������.
	 */
	public String getRawRecord()
	{
		return reader.getRawRecord();
	}

}
