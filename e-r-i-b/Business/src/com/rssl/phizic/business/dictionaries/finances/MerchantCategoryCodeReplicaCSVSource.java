package com.rssl.phizic.business.dictionaries.finances;

import com.csvreader.CsvReader;
import com.rssl.phizic.business.locale.MultiLocaleQueryHelper;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.BeanQuery;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.dictionaries.CsvReplicaSource;
import com.rssl.phizic.gate.dictionaries.DictionaryRecordBase;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import sun.nio.cs.StandardCharsets;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * ��� �������� ����������� MCC-����� �� CSV-����� � ����
 * ---------------------------------------------
 * ��������� ������ � CSV-�����
 * 1 ������� - MCC ���
 * 2 ������� - ������� ID ���������
 * ---------------------------------------------
 * @author Gololobov
 * @ created 02.08.2011
 * @ $Author$
 * @ $Revision$
 */

public class MerchantCategoryCodeReplicaCSVSource implements CsvReplicaSource, RecordConstructor
{
	//����� ��� ������ � csv-�������
    private CsvReader reader;
	//��������� �������� ����������� ����� � owner is null
	private Map operationCategoriesWithOwnerNull = null;
	//���� ����������� ���������
	private static final String DEFAUL_FILE_NAME = "mcccode_dictionaries.csv";
	//�����������
	private static final char DELIMITER = ';';

	public void initialize(GateFactory factory) throws GateException
	{
		//Key - CardOperationCategory.externalId, Value - CardOperationCategory
		this.operationCategoriesWithOwnerNull = loadPublicCategories();
	}

	/**
	 *
	 * @param reader - CSV-�����
	 */
	public void setReader(CsvReader reader)
	{
		this.reader = reader;
	}

	private CsvReader getDefaultReader() throws GateException
	{
		InputStream readerStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(DEFAUL_FILE_NAME);
		if (readerStream == null)
		{
			throw new GateException("�� ��������� ���� �������: "+DEFAUL_FILE_NAME);
		}

		return new CsvReader(readerStream,DELIMITER,new StandardCharsets().charsetForName("windows-1251"));
	}

	/**
	 * ����� ���������� �������� �� ������ �� CSV-�����
	 * @return Iterator - ���������� �������� �� ������ �� CSV-�����
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public Iterator iterator() throws GateException, GateLogicException
	{
		if (reader == null)
		{
			reader = getDefaultReader();
		}

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
			MerchantCategoryCode merchantCategoryCode = new MerchantCategoryCode();
			//MCC ���
			merchantCategoryCode.setCode(Long.parseLong(csvRecordIterator.getColumByIndex(0)));
			//����� ��������� �� externalId ��������� �� ����������� ���������
			CardOperationCategory operationCategory1 = (CardOperationCategory)this.operationCategoriesWithOwnerNull.get(csvRecordIterator.getColumByIndex(1));
			CardOperationCategory operationCategory2 = (CardOperationCategory)this.operationCategoriesWithOwnerNull.get(csvRecordIterator.getColumByIndex(2));
			if (operationCategory1 != null)
				merchantCategoryCode.addOperationCategory(operationCategory1);
			if (operationCategory2 != null)
				merchantCategoryCode.addOperationCategory(operationCategory2);
			return merchantCategoryCode;
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

	/**
	 * �������� ���� ��������� �������� ������� ���� ������ ��� ������ "_Load3_Dictionaries"
	 * @throws GateException
	 */
	private Map<String,CardOperationCategory> loadPublicCategories() throws GateException
	{
		List<CardOperationCategory> list = null;
		try
		{

			BeanQuery query = MultiLocaleQueryHelper.getQuery("com.rssl.phizic.business.dictionaries.finances.getOperationCategoriesWithOwnerNull");
			list =  query.executeListInternal();

		}
		catch (Exception e)
		{
			throw new GateException(e);
		}

		//Map � ����������� �������� key- externalId ��������, value- ���� ���������
		Map<String,CardOperationCategory> categoriesWithOwnerNull = new HashMap<String,CardOperationCategory>();
		if (!CollectionUtils.isEmpty(list))
		{
			for (CardOperationCategory cardOperationCategory : list)
			{
				categoriesWithOwnerNull.put(cardOperationCategory.getExternalId(), cardOperationCategory);
			}
		}
		
		return categoriesWithOwnerNull;
	}
}
