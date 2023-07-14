package com.rssl.phizic.business.statemachine.forms;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.FormFilter;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.common.types.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * �����.
 * � ��������� ������� stateObject ������ ����� ����������� ��� ������ ������� ����� ��������� ���� ����������� (������ ��������).
 * � ����������� �� ��������� stateObject'� ��� ������ �������� ���� ����������� ����� ������� ����� �����������.
 * �� ��������� ������������ �����, ����������� � ����� client-form, employee-form.
 *
 * @author khudyakov
 * @ created 30.07.14
 * @ $Author$
 * @ $Revision$
 */
public class Form
{
	private String url;                                                     //������ ��������
	private Application application;                                        //����������, � ������� ����� �������������� �������
	private List<FormFilter> filters = new ArrayList<FormFilter>();         //������ �������� ���������� ������ �����


	public Form(String url, Application application, List<FormFilter> filters)
	{
		this.url = url;
		this.application = application;
		this.filters.addAll(filters);
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	public Application getApplication()
	{
		return application;
	}

	public void setApplication(Application application)
	{
		this.application = application;
	}

	public List<FormFilter> getFilters()
	{
		return filters;
	}

	public void setFilters(List<FormFilter> filters)
	{
		this.filters = filters;
	}

	/**
	 * ���������� ���������� �����
	 * @param stateObject ������ ������ ���������
	 * @param application ����������
	 * @return true - �������
	 */
	public boolean isActive(StateObject stateObject, Application application)
	{
		try
		{
			if (application != getApplication())
			{
				return false;
			}

			for (FormFilter filter : getFilters())
			{
				//noinspection unchecked
				if (!filter.isEnabled(stateObject))
				{
					return false;
				}
			}
			return true;
		}
		catch (DocumentException e)
		{
			return false;
		}
		catch (DocumentLogicException e)
		{
			return false;
		}
	}
}
