package com.rssl.auth.csa.back.integration.erib.types;

import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.util.List;

/**
 * @author akrenev
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 *
 *  информация о подтверждении
 */

@SuppressWarnings({"ReturnOfCollectionOrArrayField", "AssignmentToCollectionOrArrayFieldFromParameter"})
public class ConfirmationInformation
{
	private ConfirmStrategyType confirmStrategyType;
	private List<CardInformation> confirmationSources;
	private boolean pushAllowed;

	/**
	 * @return предпочтительный способ подтверждения
	 */
	public ConfirmStrategyType getConfirmStrategyType()
	{
		return confirmStrategyType;
	}

	/**
	 * задать предпочтительный способ подтверждения
	 * @param confirmStrategyType предпочтительный способ подтверждения
	 */
	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{
		this.confirmStrategyType = confirmStrategyType;
	}

	/**
	 * задать предпочтительный способ подтверждения (по строке)
	 * @param confirmStrategyType предпочтительный способ подтверждения
	 */
	public void setConfirmStrategyType(String confirmStrategyType)
	{
		this.confirmStrategyType = ConfirmStrategyType.valueOf(confirmStrategyType);
	}

	/**
	 * @return источники подтверждений
	 */
	public List<CardInformation> getConfirmationSources()
	{
		return confirmationSources;
	}

	/**
	 * задать источники подтверждений
	 * @param confirmationSources источники подтверждений
	 */
	public void setConfirmationSources(List<CardInformation> confirmationSources)
	{
		this.confirmationSources = confirmationSources;
	}

	/**
	 * @return доступно ли подтверждение по push
	 */
	public boolean isPushAllowed()
	{
		return pushAllowed;
	}

	/**
	 * задать доступно ли подтверждение по push
	 * @param pushAllowed доступно ли подтверждение по push
	 */
	public void setPushAllowed(boolean pushAllowed)
	{
		this.pushAllowed = pushAllowed;
	}
}
