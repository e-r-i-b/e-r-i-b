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
 * Форма для работы с состояниями справочников в блоке и запуска синхронизации
 */

public class DictionaryInformationForm extends ActionFormBase
{
	private List<DictionaryInformationWrapper> data;

	/**
	 * @return информация о состоянии справочников
	 */
	public List<DictionaryInformationWrapper> getData()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return data;
	}

	/**
	 * задать информацию о состоянии справочников
	 * @param data информация о состоянии справочников
	 */
	public void setData(List<DictionaryInformationWrapper> data)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.data = data;
	}
}
