package com.rssl.phizic.business.dictionaries.loanclaim;

/**
 * @author Nady
 * @ created 25.05.2014
 * @ $Author$
 * @ $Revision$
 */

import com.csvreader.CsvReader;
import com.rssl.phizic.gate.loanclaim.dictionary.MultiWordDictionaryEntry;
import com.rssl.phizic.utils.StringHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * ������� ����� ��� ������� ������������ ��������� ����������
 */
public abstract class AbstractAddressDictionaryReader
{
	private static final String DEFAULT_CODE = "0000";
	private static final int MIN_POSTFIX_LENGTH = 3;    //@see liveSearchInput.js#minChars

	protected final CsvReader csvReader;
	protected boolean hasNext;
	private final String wordSeparators;

	protected AbstractAddressDictionaryReader(CsvReader csvReader, String wordSeparators) throws IOException
	{
		this.csvReader = csvReader;
		this.csvReader.readHeaders();
		this.hasNext = csvReader.readRecord();
		this.wordSeparators = wordSeparators;
	}

	/**
	 * ������� �� ��������� ������ � �����
	 * @return
	 */
	public boolean hasNext()
	{
		return hasNext;
	}

	/**
	 * ����� ��� ��������� ���� �����������
	 * @param region - ��� �������
	 * @param area   - ��� ������
	 * @param city   - ��� ������
	 * @param settlement - ��� ����������� ������
	 * @param street     - ��� �����
	 * @return - ��� ����������� � ����� �������
	 */
	protected String buildCodeDictionary(String region, String area, String city, String settlement, String street)
	{
		return  (region != null ? StringHelper.addLeadingZeros(region, 4): DEFAULT_CODE) +
				(area!=null ? StringHelper.addLeadingZeros(area, 4) : DEFAULT_CODE) +
				(city!=null ? StringHelper.addLeadingZeros(city, 4) : DEFAULT_CODE) +
				(settlement!=null ? StringHelper.addLeadingZeros(settlement, 4) : DEFAULT_CODE)+
				(street!=null ? StringHelper.addLeadingZeros(street, 4) : DEFAULT_CODE);
	}

	/**
	 * ������ ������ �� ����������� � �������� �� ��� ��������� ��������� ��� ��
	 * @see MultiWordDictionaryEntry#searchPostfix
	 * @return ������ �����������, �������� �� ��������� ��� ������
	 */
	public List<MultiWordDictionaryEntry> readRecordWithPostfixes() throws IOException
	{
		MultiWordDictionaryEntry record = readRecord();

		List<String> postfixes = StringHelper.getPostfixes(record.getName(), wordSeparators, MIN_POSTFIX_LENGTH);
		List<MultiWordDictionaryEntry> result = new ArrayList<MultiWordDictionaryEntry>(postfixes.size() + 1);
		
		//���� �����
		record.setSearchPostfix(record.getName().toLowerCase());
		result.add(record);
		//��� ���������
		for (String postfix : postfixes)
		{
			MultiWordDictionaryEntry entry = (MultiWordDictionaryEntry) record.clone();
			entry.setSearchPostfix(postfix.toLowerCase());
			result.add(entry);
		}

		return result;
	}

	/**
	 * ��������� ������
	 * @return �������� �����������
	 */
	protected abstract MultiWordDictionaryEntry readRecord() throws IOException;
}
