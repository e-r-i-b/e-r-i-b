package com.rssl.phizic.web.configure.exceptions;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author osminin
 * @ created 27.03.15
 * @ $Author$
 * @ $Revision$
 *
 * Форма синхронизации справочника маппинга ошибок
 */
public class ExceptionEntrySynchronizeForm extends ActionFormBase
{
	private Long[] selectedNodes;
	private String exceptionEntryType;

	/**
	 * @return выбранные ноды
	 */
	@SuppressWarnings("ReturnOfCollectionOrArrayField")
	public Long[] getSelectedNodes()
	{
		return selectedNodes;
	}

	/**
	 * @param selectedNodes выбранные ноды
	 */
	@SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter")
	public void setSelectedNodes(Long[] selectedNodes)
	{
		this.selectedNodes = selectedNodes;
	}

	/**
	 * @return получить тип ошибки, с которым работаем
	 */
	public String getExceptionEntryType()
	{
		return exceptionEntryType;
	}

	/**
	 * @param exceptionEntryType тип ошибки, с которым работаем
	 */
	public void setExceptionEntryType(String exceptionEntryType)
	{
		this.exceptionEntryType = exceptionEntryType;
	}
}
