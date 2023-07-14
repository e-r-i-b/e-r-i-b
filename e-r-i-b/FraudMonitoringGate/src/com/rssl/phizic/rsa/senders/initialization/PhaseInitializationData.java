package com.rssl.phizic.rsa.senders.initialization;

import com.rssl.phizic.rsa.InteractionType;
import com.rssl.phizic.rsa.PhaseType;

/**
 * Инициализирующие данные взаимодействия с ВС ФМ
 *
 * @author khudyakov
 * @ created 22.05.15
 * @ $Author$
 * @ $Revision$
 */
public class PhaseInitializationData implements InitializationData
{
	private InteractionType interactionType = InteractionType.SYNC;
	private PhaseType phaseType = PhaseType.CONTINUOUS_INTERACTION;
	private boolean IMSI;

	public PhaseInitializationData(InteractionType interactionType, PhaseType phaseType)
	{
		this.interactionType = interactionType;
		this.phaseType = phaseType;
	}

	public PhaseInitializationData(InteractionType interactionType, PhaseType phaseType, boolean IMSI)
	{
		this.interactionType = interactionType;
		this.phaseType = phaseType;
		this.IMSI = IMSI;
	}

	/**
	 * @return тип взаимодействия
	 */
	public InteractionType getInteractionType()
	{
		return interactionType;
	}

	/**
	 * @return фаза взаимодействия
	 */
	public PhaseType getPhaseType()
	{
		return phaseType;
	}

	/**
	 * @return true - сработала проверка имси
	 */
	public boolean isIMSI()
	{
		return IMSI;
	}
}
