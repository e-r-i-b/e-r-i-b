package com.rssl.phizic;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * ������� ��� ������ � �������� ���. ������ ����. ��������� ��-�� ������� "������" � "�����" �������,
 *  ����� ��������, ��� ��� ���� � ��� �� ���� �� ����� ���� � ��������� ������������
 *
 * @author egorova
 * @ created 15.11.2010
 * @ $Author$
 * @ $Revision$
 */

public class TBSynonymsDictionary extends Config
{
	private static final String MAIN_TB_NAME = "com.rssl.iccs.tb.main.code.";
	private static final String SYNONYMS_TB_NAME = "com.rssl.iccs.tb.codes.";
	private static final String TB_COUNT = "com.rssl.iccs.tb.count";
	private static final String SEPARATOR = ",";
	
	private static final String[] identicalTB38_99 = {"38", "99"};
	private static final String[] identicalTB44_02 = {"44", "2"};
	private static final Set<String[]> identicalTBSet = new HashSet<String[]>();
	private static final int MAIN_TB_INDEX_OF_IDENTICAL = 1;

	//��� ������������ <�������� ����� ��, ������ ��������� ��>
	private Map<String, List<String>> synonyms = new HashMap<String, List<String>>();

	public TBSynonymsDictionary(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh()
	{
		String tb_count = getProperty(TB_COUNT);
		if (StringHelper.isNotEmpty(tb_count))
		{
			Long tbCount = Long.parseLong(tb_count);

			for (int i = 1; i <= tbCount; i++)
			{
				fillMaps(String.valueOf(i));
			}
		}
		identicalTBSet.add(identicalTB38_99);
		identicalTBSet.add(identicalTB44_02);
	}

	private void fillMaps(String num)
	{
		String mainTb = getProperty(MAIN_TB_NAME + num);
		String[] synonymsArray = StringHelper.getEmptyIfNull(getProperty(SYNONYMS_TB_NAME + num)).split(",");
		synonyms.put(mainTb, Arrays.asList(synonymsArray));
	}

	/**
	 * ������� ����� ��������� ��. null - �� �� ������.
	 * @param synonym - ����� ��, ��� �������� ���������� �������� �������� �����.
	 * ����� ��������� � ��������, ����� ��� �� � �����.
	 * @return �������� ����� ��
	 */
	public String getMainTBBySynonym(String synonym)
	{
		String synonymWithoutZero = StringHelper.removeLeadingZeros(synonym);
		for (String mainTb : synonyms.keySet())
		{
			if (synonymWithoutZero.equals(mainTb))
				return mainTb;
			List<String> tempSynonyms = synonyms.get(mainTb);
			if (tempSynonyms.contains(synonymWithoutZero))
				return mainTb;
		}
		return null;
	}

	/**
	 * ������� ����� ��������� ��. null - �� �� ������.
	 * @param synonym - ����� ��, ��� �������� ���������� �������� �������� �����.
	 * ����� ��������� � ��������, ����� ��� �� � �����.
	 * @return �������� ����� ��
	 */
	public String getMainTBBySynonymAndIdentical(String synonym)
	{
		String mainTB = getMainTBBySynonym(synonym);
		if(StringHelper.isEmpty(mainTB))
			return null;

		if (ArrayUtils.contains(identicalTB38_99, mainTB))
			return identicalTB38_99[MAIN_TB_INDEX_OF_IDENTICAL];

		return mainTB;
	}

	/**
	 * ������� ����� ��������� �� � ��������� � ��� ���������� ����� ����� ","
	 * �.�. 40,8,59 � �.�.
	 * @return
	 */
	public String getTBAndSynonymsWithoutLeadingZero(String tb)
	{
		//������� ���������� ����
		String tbWithoutLeadingZero = StringHelper.removeLeadingZeros(tb);
		String result = tbWithoutLeadingZero;

		//���� ����� ���������� �� (38, 99 � 44, 2) - �������
		for (String[] item : identicalTBSet)
		{
			List<String> list = Arrays.asList(item);
			if (list.contains(tbWithoutLeadingZero))
			{
				result = result.concat(SEPARATOR+StringUtils.join(list,SEPARATOR)) ;
				break;
			}
		}		

		//���� ����� �������� ��
		List<String> synonymsList = synonyms.get(tbWithoutLeadingZero);
		if (CollectionUtils.isNotEmpty(synonymsList))
			return result.concat(SEPARATOR+StringUtils.join(synonymsList,SEPARATOR));

		//���� ����� ��������� ��
		for (String mainTb : synonyms.keySet())
		{
			List<String> tmpSynonymsList = synonyms.get(mainTb);
			if (tmpSynonymsList.contains(tbWithoutLeadingZero))
				return result.concat(SEPARATOR+mainTb)
						.concat(SEPARATOR+StringUtils.join(tmpSynonymsList,SEPARATOR));
		}

		return result;
	}

	/**
	 * ��������� ������ ��, �������� ����������� identicalTBSet.
	 * ���� ����� ��������� �� ��� tb ���������� � identicalTBSet �� ����� �������� ������ �� �������� identicalSet(�������� {99,38})
	 * @param tb - �������.
	 * @return ������ �� �� � ������ �� �� � ������ ����� � ����������� ������ ��� ������� ��������.
	 */
	public List<String> getMainTBWithIdenticalSet(String tb)
	{
		String mainTB = getMainTBBySynonym(tb);
		if(StringHelper.isEmpty(mainTB))
			return null;
		String tbWithoutLeadingZeros = StringHelper.removeLeadingZeros(mainTB);
		List<String> listTB = new ArrayList<String>();
		for(String[] item : identicalTBSet)
		{
			List<String> list = Arrays.asList(item);
			if (list.contains(tbWithoutLeadingZeros))
			{
				for (String region : list)
					listTB.add(StringHelper.addLeadingZeros(region, 2));
				return listTB;
			}
		}
		listTB.add(StringHelper.addLeadingZeros(mainTB, 2));
		return listTB;
	}

	/**
	 * �������� ��� �������� ��� ��������, ������� identicalTB � �������� �������� �� � ����� ��
	 * @param tb ��
	 * @return ��������
	 */
	public Set<String> getTBSynonyms(String tb)
	{
		//��������� ��� �������� � �������� ��
		String mainTB = getMainTBBySynonym(tb);
		if (StringHelper.isEmpty(mainTB))
		{
			throw new IllegalArgumentException("��� �� = " + tb + " �� ������ ������� ��.");
		}

		Set<String> result = new HashSet<String>();

		//��������� ��� ������� ��
		result.add(StringHelper.addLeadingZeros(mainTB, 2));

		//���� ����� ���������� �� (38, 99 � 44, 2)
		List<String> identicalTB = getIdenticalTB(tb);
		if (CollectionUtils.isNotEmpty(identicalTB))
		{
			result.addAll(StringHelper.addLeadingZeros(identicalTB, 2));
		}

		List<String> synonymsTB = StringHelper.addLeadingZeros(synonyms.get(mainTB), 2);
		if (CollectionUtils.isNotEmpty(synonymsTB))
		{
			result.addAll(synonymsTB);
		}

		return result;
	}

	private List<String> getIdenticalTB(String tb)
	{
		String tbWithoutLeadingZero = StringHelper.removeLeadingZeros(tb);
		for (String[] item : identicalTBSet)
		{
			List<String> list = Arrays.asList(item);
			if (list.contains(tbWithoutLeadingZero))
			{
				return list;
			}
		}
		return null;
	}

	/**
	 * �������� �� ������� �����������. ������� ��� 38-99, 44-2.
	 * @param region1 -
	 * @param region2 -
	 * @return true - ���� � identicalTBSet ����� ���� ����������. false - ���� ���. ���� �������� ������� �� ���������� � identicalTBSet, �� true ���� ����������.
	 */
	public static boolean isSameTB(String region1, String region2)
	{
		if(region1.equals(region2))
			return true;

		for (String[] item : identicalTBSet)
		{
			List<String> list = Arrays.asList(item);
			if (list.contains(region1))
				return list.contains(region2);
		}

		return false;
	}
}
