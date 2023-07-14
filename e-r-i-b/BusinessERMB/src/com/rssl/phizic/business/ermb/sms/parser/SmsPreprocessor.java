package com.rssl.phizic.business.ermb.sms.parser;

import com.rssl.phizic.business.ermb.sms.config.SmsConfig;
import com.rssl.phizic.config.ConfigFactory;

import java.util.Collection;

/**
 * @author Erkin
 * @ created 30.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������������ ���-�������� ����
 * 1. ���������, ���� �� � ��������� �������� �����. ���� ����, �������� �� ��������������� ��������
 * 2. �������� ����������� ������� �� ������
 * 3. ��������� �������: ������ ���������� �������� �������� �� ���� ������
 * 4. ������� ������� � ������ � ����� ������
 * 5. �������� ������ ������� �� ����
 */
class SmsPreprocessor
{
	static String preprocess(String text)
	{
		Collection<PreprocessorFilter> filters = ConfigFactory.getConfig(SmsConfig.class).getPreprocessorFilters();
		for(PreprocessorFilter filter : filters)
			text = filter.filter(text);

		StringBuilder sb = new StringBuilder(text.length());
		boolean lastSpace = false;
		for (int i=0; i<text.length(); i++)
		{
			char c = text.charAt(i);
			boolean space = Alphabet.isWhiteSpace(c);

			// �������� ����������� ������� �� ������
			space = space || Alphabet.isUnknownChar(c);

			if (space)
			{
				// ������� ������� � ������ ������
				if (sb.length() == 0)
					continue;

				// ��������� ������ ������ ���������� ������� �� ������ �������
				if (lastSpace)
					continue;

				// �������� (������) ���������� ������� �� ������
				c = ' ';
			}

			lastSpace = space;
			sb.append(c);
		}

		// ������� ������� � ����� ������
		if (lastSpace && sb.length() > 0)
			sb.deleteCharAt(sb.length()-1);

		return sb.toString();
	}

}
