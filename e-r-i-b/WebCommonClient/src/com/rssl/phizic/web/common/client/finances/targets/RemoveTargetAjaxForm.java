package com.rssl.phizic.web.common.client.finances.targets;

import org.apache.struts.action.ActionForm;
import com.rssl.phizic.operations.finances.targets.RemoveTargetState;

/**
 * @author mihaylov
 * @ created 30.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Форма удаления цели
 */
public class RemoveTargetAjaxForm extends ActionForm
{
	private Long id;
	private Long accountLinkId;
	private Long claimId;
	private String claimStateCode;
	private RemoveTargetState removeState;

	/**
	 * @return идентификаотр цели
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификаотр цели
	 * @param id идентификаотр цели
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return идентификатор линка счета, привязанного к цели
	 */
	public Long getAccountLinkId()
	{
		return accountLinkId;
	}

	/**
	 * задать идентификатор линка счета, привязанного к цели
	 * @param accountLinkId идентификатор линка счета, привязанного к цели
	 */
	public void setAccountLinkId(Long accountLinkId)
	{
		this.accountLinkId = accountLinkId;
	}

	/**
	 * @return идентификатор заявки на закрытие вклада, привязанного к цели
	 */
	public Long getClaimId()
	{
		return claimId;
	}

	/**
	 * задать идентификатор заявки на закрытие вклада, привязанного к цели
	 * @param claimId идентификатор заявки на закрытие вклада, привязанного к цели
	 */
	public void setClaimId(Long claimId)
	{
		this.claimId = claimId;
	}

	/**
	 * @return код статуса заявки на закрытие вклада, привязанного к цели
	 */
	public String getClaimStateCode()
	{
		return claimStateCode;
	}

	/**
	 * задать код статуса заявки на закрытие вклада, привязанного к цели
	 * @param claimStateCode код статуса заявки на закрытие вклада, привязанного к цели
	 */
	public void setClaimStateCode(String claimStateCode)
	{
		this.claimStateCode = claimStateCode;
	}

	/**
	 * @return результат удаления цели
	 */
	public RemoveTargetState getRemoveState()
	{
		return removeState;
	}

	/**
	 * задать результат удаления цели
	 * @param removeState результат удаления цели
	 */
	public void setRemoveState(RemoveTargetState removeState)
	{
		this.removeState = removeState;
	}
}
