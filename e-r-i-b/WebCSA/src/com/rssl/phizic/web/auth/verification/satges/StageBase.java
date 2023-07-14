package com.rssl.phizic.web.auth.verification.satges;

import com.rssl.auth.csa.front.operations.auth.OperationInfo;
import com.rssl.common.forms.Form;
import com.rssl.phizic.web.auth.Stage;

/**
 * ������� ����� ������
 *
 * @author akrenev
 * @ created 27.03.2013
 * @ $Author$
 * @ $Revision$
 */

public abstract class StageBase implements Stage
{
	private final String name;
	private final Stage stage;
	private final Form form;

	/**
	 * �����������
	 * @param name ��� ������
	 * @param form ���������� ����� ������
	 */
	protected StageBase(String name, Form form)
	{
		this(name, form, null);
	}

	/**
	 * �����������
	 * @param name ��� ������
	 * @param form ���������� ����� ������
	 * @param stage ��������� ������
	 */
	protected StageBase(String name, Form form, Stage stage)
	{
		this.name = name;
		this.stage = stage;
		this.form = form;
	}

	public String getName()
	{
		return name;
	}

	public Stage next(OperationInfo info)
	{
		return stage;
	}

	public Form getForm(OperationInfo info)
	{
		return form;
	}
}
