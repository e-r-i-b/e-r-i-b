package com.rssl.phizic.gate.confirmation;

import com.rssl.phizic.common.types.ConfirmStrategyType;

import java.util.List;

/**
 * @author akrenev
 * @ created 10.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Информация о подтверждении операций клиентом
 */

@SuppressWarnings({"AssignmentToCollectionOrArrayFieldFromParameter", "ReturnOfCollectionOrArrayField"})
public class ConfirmationInfoImpl implements ConfirmationInfo
{
	private ConfirmStrategyType confirmStrategyType;
	private List<CardConfirmationSource> confirmationSources;
	private boolean pushAllowed;

	/**
	 * конструктор
	 */
	public ConfirmationInfoImpl(){}

	/**
	 * конструктор
	 * @param confirmStrategyType предпочтительный способ подтверждения
	 * @param confirmationSources источники подтверждения (карты)
	 */
	public ConfirmationInfoImpl(ConfirmStrategyType confirmStrategyType, List<CardConfirmationSource> confirmationSources, boolean pushAllowed)
	{
		this.confirmStrategyType = confirmStrategyType;
		this.confirmationSources = confirmationSources;
		this.pushAllowed = pushAllowed;
	}

	/**
	 * задать предпочтительный способ подтверждения
	 * @param confirmStrategyType предпочтительный способ подтверждения
	 */
	public void setConfirmStrategyType(ConfirmStrategyType confirmStrategyType)
	{
		this.confirmStrategyType = confirmStrategyType;
	}

	public ConfirmStrategyType getConfirmStrategyType()
	{
		return confirmStrategyType;
	}

	/**
	 * задать источники подтверждения (карты)
	 * @param confirmationSources источники подтверждения (карты)
	 */
	public void setConfirmationSources(List<CardConfirmationSource> confirmationSources)
	{
		this.confirmationSources = confirmationSources;
	}

	public List<CardConfirmationSource> getConfirmationSources()
	{
		return confirmationSources;
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
