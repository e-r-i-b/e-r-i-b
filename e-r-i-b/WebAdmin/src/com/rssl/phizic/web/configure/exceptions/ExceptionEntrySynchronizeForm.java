package com.rssl.phizic.web.configure.exceptions;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author osminin
 * @ created 27.03.15
 * @ $Author$
 * @ $Revision$
 *
 * ����� ������������� ����������� �������� ������
 */
public class ExceptionEntrySynchronizeForm extends ActionFormBase
{
	private Long[] selectedNodes;
	private String exceptionEntryType;

	/**
	 * @return ��������� ����
	 */
	@SuppressWarnings("ReturnOfCollectionOrArrayField")
	public Long[] getSelectedNodes()
	{
		return selectedNodes;
	}

	/**
	 * @param selectedNodes ��������� ����
	 */
	@SuppressWarnings("AssignmentToCollectionOrArrayFieldFromParameter")
	public void setSelectedNodes(Long[] selectedNodes)
	{
		this.selectedNodes = selectedNodes;
	}

	/**
	 * @return �������� ��� ������, � ������� ��������
	 */
	public String getExceptionEntryType()
	{
		return exceptionEntryType;
	}

	/**
	 * @param exceptionEntryType ��� ������, � ������� ��������
	 */
	public void setExceptionEntryType(String exceptionEntryType)
	{
		this.exceptionEntryType = exceptionEntryType;
	}
}
