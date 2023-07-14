package com.rssl.phizic.business.documents.templates.source;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.common.types.documents.StateCode;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.utils.counter.SmsChannelTemplateNameStrategy;

/**
 * �������� ������� �� ������ �� ���
 * ��������� ������� � �������������
 *
 * @author Puzikov
 * @ created 07.05.15
 * @ $Author$
 * @ $Revision$
 */

public class MbkNewTemplateSource extends NewTemplateSource
{
	/**
	 * ctor
	 * �������� ������� ������������ �� �������� ���-������
	 * ����� �������� - ���
	 *
	 * @param formName ��� �����
	 * @param initialValuesSource ��������� ������
	 * @param person �������� �������
	 */
	public MbkNewTemplateSource(String formName, FieldValuesSource initialValuesSource, Person person) throws BusinessException, BusinessLogicException
	{
		super(formName, initialValuesSource, CreationType.sms, person, new SmsChannelTemplateNameStrategy());
	}

	@Override
	protected State getInitialState(CreationType channelType) throws BusinessLogicException, BusinessException
	{
		return new State(StateCode.WAIT_CONFIRM_TEMPLATE.name());
	}
}
