package com.rssl.phizic.gate.payments.longoffer;

/**
 * Канал создания подписки(IB - интернет банк, VSP - ВСП, US - устройство самообслуживания)
 * @author niculichev
 * @ created 24.01.2012
 * @ $Author$
 * @ $Revision$
 */
public enum ChannelType
{
	/*
	  Интернет банк
	 */
	IB,

	/*
	ВСП
	 */
	VSP,

	/*
	Устройство вамообслуживания
	 */
	US,

	SMSSender;
}
