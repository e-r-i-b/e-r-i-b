package com.rssl.phizic.business.dictionaries.finances;

import com.csvreader.CsvReader;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.CsvReplicaSource;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import sun.nio.cs.StandardCharsets;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * ��� �������� ����������� ����� �������� �� CSV-����� � ����
 * ---------------------------------------------
 * ��������� ������ � CSV-�����
 * 1 ������� - ���/��� ��������
 * 2 ������� - ��� ��������: �������� (C) / ����������� (N)
 * ---------------------------------------------
 * @author Gololobov
 * @ created 02.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class CardOperationTypeReplicaCSVSource implements CsvReplicaSource, RecordConstructor
{
	//����� ��� ������ � csv-�������
    private CsvReader reader;
	//���� ����������� ���������
	private static final String DEFAUL_FILE_NAME = "opertype_dictionaries.csv";
	//�����������
	private static final char DELIMITER = ';';

	public void initialize(GateFactory factory) throws GateException
	{
		InputStream readerStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(DEFAUL_FILE_NAME);
		if (readerStream == null)
			throw new GateException("�� ��������� ���� �������: "+DEFAUL_FILE_NAME);
		this.reader = new CsvReader(readerStream,DELIMITER,new StandardCharsets().charsetForName("windows-1251"));
	}

	/**
	 * ����� ���������� �������� �� ������ �� CSV-�����
	 * @return Iterator - ���������� �������� �� ������ �� CSV-�����
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public Iterator iterator() throws GateException, GateLogicException
	{
		try
		{
			return new CsvRecordIterator(this, reader);
		}
		catch (IOException e)
		{
			throw new RuntimeException("������ �����/������. ���� ������� ��� ������ ������� �������.",e);
		}
	}

	/**
	 * ����� ������� ����� ������ � ��������� ��� ������� �� CSV-�����
	 * @param csvRecordIterator - �������� �� CSV-������
	 * @return DictionaryRecordBase - ������ ��� �������� "CardOperationType"
	 * @throws IOException
	 */
	public DictionaryRecordBase construct(CsvRecordIterator csvRecordIterator) throws IOException
	{
		try
		{
			CardOperationType cardOperationType = new CardOperationType();
			//���/��� ��������
			cardOperationType.setCode(Long.parseLong(csvRecordIterator.getColumByIndex(0)));
			//��� ��������: �������� (C) / ����������� (N)
			cardOperationType.setCash(csvRecordIterator.getColumByIndex(1).compareToIgnoreCase("C") == 0);
			return cardOperationType;
		}
		catch (NumberFormatException e)
		{
			throw new RuntimeException("�������� ������ ������. � ������ " + csvRecordIterator.getCurentRecordNum(), e);
		}
	}

	/**
	 * ����������� CSV-������
	 */
	public void close()
	{
		if (this.reader != null)
			this.reader.close();
	}

	public void setReader(CsvReader reader) throws GateException
	{
		this.reader = reader;
	}
}
