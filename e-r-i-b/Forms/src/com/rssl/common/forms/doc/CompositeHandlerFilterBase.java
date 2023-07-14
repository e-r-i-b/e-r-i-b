package com.rssl.common.forms.doc;

import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.state.StateObject;
import com.rssl.common.forms.DocumentException;

import java.util.ArrayList;
import java.util.List;

/**
 * ������� ����������� ����� ��� ���������� ���������
 *
 * @author egorova
 * @ created 05.04.2011
 * @ $Author$
 * @ $Revision$
 */

public abstract class CompositeHandlerFilterBase extends HandlerFilterBase
{
	private final List<HandlerFilterBase> handlerFilters = new ArrayList<HandlerFilterBase>();

	protected void addHandler(HandlerFilterBase handler)
	{
		handlerFilters.add(handler);
	}

	public boolean isEnabled(StateObject stateObject) throws DocumentException, DocumentLogicException
	{
		boolean isEnabled = false;
		if (!handlerFilters.isEmpty())
		{
			for (HandlerFilterBase handlerFilter : handlerFilters)
			{
				handlerFilter.setParameters(getParameters());
				//���� ���� �� ���� �� �������� �������� ������ ��������, �� �������, ��� ���� �������� ��������
				if (isEnabled)
					return isEnabled;
				isEnabled = handlerFilter.isEnabled(stateObject);
			}
		}
		return isEnabled;
	}
}
