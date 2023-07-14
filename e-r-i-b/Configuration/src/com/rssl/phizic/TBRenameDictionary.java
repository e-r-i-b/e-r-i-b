package com.rssl.phizic;

import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * ������� ������������ ������� ������ �� � ������ ������ ��
 * ����� ��� ������� ����� �� ���������, �� ������ �������� �� ����� (������: ��������� ����) 
 * User: Gainanov
 * @ $Author$
 * @ $Revision$
 */
public class TBRenameDictionary extends Config
{
	private static final String PROP_NAME = "com.rssl.iccs.tb.rename.codes";


	//��� ������������ <������ ����� ��, ����� ����� ��>
	private Map<String, String> synonyms = new HashMap<String, String>();

	public TBRenameDictionary(PropertyReader reader)
	{
		super(reader);
	}

	protected void doRefresh() throws ConfigurationException
	{
		String props = getProperty(PROP_NAME);
		String[] synonymsArray =  props.split(",");
		for (String synonym : synonymsArray)
		{
			StringTokenizer st = new StringTokenizer(synonym, "-");
			synonyms.put(st.nextToken(), st.nextToken());
		}
	}

	/**
	 * ������� ����� ������ ��. null - ������������ �� �������.
	 * @param synonym - ������ ����� ��, ��� �������� ���������� �������� ����� �����.
	 * @return ����� ����� ��
	 */
	public String getNewTbBySynonym(String synonym)
	{
		return synonyms.get(synonym);
	}

	/**
	 * ������� ����� ������� ��. null - ������������ �� �������.
	 * @param synonym - ����� ����� TB, ��� ������ ���������� �������� ������ �����.
	 * @return ������ ����� ��.
	 */
	public String getOldTbBySynonym(String synonym)
	{
		for (String tbCode : getAllSynonyms().keySet())
		{
			//����������� ��� ��������
			String renameTbCode = getNewTbBySynonym(tbCode);
			String tbCodeWithoutLeadingZeros = StringHelper.removeLeadingZeros(renameTbCode);

			//������������ ���� �������� ��� ���������� �����.
			if (tbCodeWithoutLeadingZeros.startsWith(StringHelper.removeLeadingZeros(synonym)))
				return tbCode;
		}
		return null;
	}

	public Map<String, String> getAllSynonyms()
	{
		return synonyms;
	}
}
