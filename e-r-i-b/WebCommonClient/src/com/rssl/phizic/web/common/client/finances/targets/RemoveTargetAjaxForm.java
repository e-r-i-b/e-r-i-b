package com.rssl.phizic.web.common.client.finances.targets;

import org.apache.struts.action.ActionForm;
import com.rssl.phizic.operations.finances.targets.RemoveTargetState;

/**
 * @author mihaylov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * ����� �������� ����
 */
public class RemoveTargetAjaxForm extends ActionForm
{
	private Long id;
	private Long accountLinkId;
	private Long claimId;
	private String claimStateCode;
	private RemoveTargetState removeState;

	/**
	 * @return ������������� ����
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ ������������� ����
	 * @param id ������������� ����
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ������������� ����� �����, ������������ � ����
	 */
	public Long getAccountLinkId()
	{
		return accountLinkId;
	}

	/**
	 * ������ ������������� ����� �����, ������������ � ����
	 * @param accountLinkId ������������� ����� �����, ������������ � ����
	 */
	public void setAccountLinkId(Long accountLinkId)
	{
		this.accountLinkId = accountLinkId;
	}

	/**
	 * @return ������������� ������ �� �������� ������, ������������ � ����
	 */
	public Long getClaimId()
	{
		return claimId;
	}

	/**
	 * ������ ������������� ������ �� �������� ������, ������������ � ����
	 * @param claimId ������������� ������ �� �������� ������, ������������ � ����
	 */
	public void setClaimId(Long claimId)
	{
		this.claimId = claimId;
	}

	/**
	 * @return ��� ������� ������ �� �������� ������, ������������ � ����
	 */
	public String getClaimStateCode()
	{
		return claimStateCode;
	}

	/**
	 * ������ ��� ������� ������ �� �������� ������, ������������ � ����
	 * @param claimStateCode ��� ������� ������ �� �������� ������, ������������ � ����
	 */
	public void setClaimStateCode(String claimStateCode)
	{
		this.claimStateCode = claimStateCode;
	}

	/**
	 * @return ��������� �������� ����
	 */
	public RemoveTargetState getRemoveState()
	{
		return removeState;
	}

	/**
	 * ������ ��������� �������� ����
	 * @param removeState ��������� �������� ����
	 */
	public void setRemoveState(RemoveTargetState removeState)
	{
		this.removeState = removeState;
	}
}
