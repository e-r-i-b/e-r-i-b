package com.rssl.phizic.web.common.mobile.fund;

import org.apache.struts.action.ActionForm;

/**
 * @author saharnova
 * @ created 11.12.14
 * @ $Author$
 * @ $Revision$
 * ����� ��� �������� ���������� ������������ �������� �������� ������������� � ����������� �������
 */

public class CountNotAnswerIncomingFundRequestForm extends ActionForm
{
	private Integer count;

	public Integer getCount()
	{
		return count;
	}

	public void setCount(Integer count)
	{
		this.count = count;
	}
}
