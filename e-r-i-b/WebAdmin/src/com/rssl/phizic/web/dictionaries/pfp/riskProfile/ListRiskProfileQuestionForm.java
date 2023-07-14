package com.rssl.phizic.web.dictionaries.pfp.riskProfile;

import com.rssl.phizic.business.dictionaries.pfp.riskProfile.Question;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 08.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * ����� ������ ��������
 */

public class ListRiskProfileQuestionForm extends ListFormBase
{
	private Map<String, List<Question>> questionsMap = new HashMap<String, List<Question>>();

	/**
	 * �������� ������ �������� ��� ��������
	 * @param segment �������
	 * @param questions �������
	 */
	public void addQuestions(String segment, List<Question> questions)
	{
		questionsMap.put(segment, questions);	
	}

	/**
	 * @return �������, �������� �� ���������
	 */
	public Map<String, List<Question>> getQuestionsMap()
	{
		return Collections.unmodifiableMap(questionsMap);
	}
}
