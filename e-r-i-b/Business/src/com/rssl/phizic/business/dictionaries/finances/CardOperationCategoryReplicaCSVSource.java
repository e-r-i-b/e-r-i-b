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
 * ��� �������� ����������� ��������� �� CSV-����� � ����
 * ---------------------------------------------
 * ��������� ������ � CSV-�����
 * 1 ������� - ������� ID ���������
 * 2 ������� - ���������������� �������� ���������
 * 3 ������� - ��� �������� (�����(I)/������(O))
 * 4 ������� - �������, ��������������� ����� �������� ����� ���� � ���� ���������
			   ("C"-�������� ��������,"N"-����������� ��������,"B"-��� ���� ��������)
 * 5 ������� - ���� ����������� �� ��������� � ��������� ������������� � ��� ��������
               (1-�����������, 0-�������������)
 * 6 ������� - ������� ��������� �� ���������, ��������� � ������� ���������� �������� � MCCCode-�� ������������� � �����������
               (D-���������� ���������, �� ���������- �� ��������� ���������)
 * ---------------------------------------------
 * @author Gololobov
 * @ created 29.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class CardOperationCategoryReplicaCSVSource  implements CsvReplicaSource, RecordConstructor
{
	//����� ��� ������ � csv-�������
    private CsvReader reader;
	//���� ����������� ���������
	private static final String DEFAUL_FILE_NAME = "category_dictionaries.csv";
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
	 * @return DictionaryRecordBase - ������ ��������� �������� "CardOperationCategory"
	 * @throws IOException
	 */
	public DictionaryRecordBase construct(CsvRecordIterator csvRecordIterator) throws IOException
	{
		try
		{
			CardOperationCategory cardOperationCategory = new CardOperationCategory();
			//������� ID
			cardOperationCategory.setExternalId(csvRecordIterator.getColumByIndex(0));
			//���������������� �������� ���������
			cardOperationCategory.setName(csvRecordIterator.getColumByIndex(1));
			//��� �������� (��������(I)/���������(O))
			cardOperationCategory.setIncome(csvRecordIterator.getColumByIndex(2).compareToIgnoreCase("I") == 0);
			//�������, ��������������� ����� �������� ����� ���� � ���� ���������
			// ("C"-�������� ��������,"N"-����������� ��������,"B"-��� ���� ��������)
			String operationType = csvRecordIterator.getColumByIndex(3); 
			boolean cash = operationType.compareToIgnoreCase("C") == 0 ||
					       operationType.compareToIgnoreCase("B") == 0;
			boolean cashless = operationType.compareToIgnoreCase("N") == 0 ||
					           operationType.compareToIgnoreCase("B") == 0;
			//��������
			cardOperationCategory.setCash(cash);
			//������
			cardOperationCategory.setCashless(cashless);
			//���� 1-����������� ��������� � ��������� ������������� � ��� ��������, 0-�������������
			cardOperationCategory.setIncompatibleOperationsAllowed(csvRecordIterator.getColumByIndex(4).compareTo("1") == 0);

			//�����������: D - ��������� ��-���������, T - ������� ����� ������ �������
			String feature = csvRecordIterator.getColumByIndex(5);
			cardOperationCategory.setIsDefault("D".equalsIgnoreCase(feature));
			cardOperationCategory.setForInternalOperations("T".equalsIgnoreCase(feature));

			return cardOperationCategory;
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
