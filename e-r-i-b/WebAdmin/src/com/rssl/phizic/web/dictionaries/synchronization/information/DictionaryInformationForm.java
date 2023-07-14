package com.rssl.phizic.web.dictionaries.synchronization.information;

import com.rssl.phizic.operations.dictionaries.synchronization.information.DictionaryInformationWrapper;
import com.rssl.phizic.web.actions.ActionFormBase;

import java.util.List;

/**
 * @author akrenev
 * @ created 04.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * ����� ��� ������ � ����������� ������������ � ����� � ������� �������������
 */

public class DictionaryInformationForm extends ActionFormBase
{
	private List<DictionaryInformationWrapper> data;

	/**
	 * @return ���������� � ��������� ������������
	 */
	public List<DictionaryInformationWrapper> getData()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return data;
	}

	/**
	 * ������ ���������� � ��������� ������������
	 * @param data ���������� � ��������� ������������
	 */
	public void setData(List<DictionaryInformationWrapper> data)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.data = data;
	}
}
