package com.rssl.phizic.gate.bankroll;

/**
 * @ author: Vagin
 * @ created: 15.08.13
 * @ $Author
 * @ $Revision
 * Вид карты
 */
public enum CardLevel
{
	A1("AMEX Black"),
	A3("AMEX Platinum (КФ)") ,
	A4("AMEX Platinum"),
	CM("Сбербанк-Maestro"),
	CT("MasterCard транспортная"),
	MB("MasterCard Business"),
	MG("Gold MasterCard"),
	MP("MasterCard Platinum"),
	MS("MasterCard Standard"),
	MV("MasterCard Virtual"),
	MQ("MasterCard payPass"),
	MR("MasterCard WBE PR"),
	MW("MasterCard WBE"),
	PC("ПРО100"),
	PO("ПРО100"),
	PP("ПРО100"),
	PR("ПРО100"),
	PS("ПРО100"),
	RM("World MasterCard Золотой"),
	RV("Visa Gold Золотой"),
	UE("УЭК"),
	VB("Visa Business"),
	VC("Visa Classic"),
	VE("Сбербанк-Visa Electron"),
	VG("Visa Gold"),
	VI("Visa Infinite"),
	VP("Visa Platinum"),
	VT("Visa транспортная"),
	VV("Visa Virtual"),
	VR("Visa Platinum PR"),
	VQ("Visa Classic payWave");

	private String description;

	CardLevel(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}

	/**
	 * Карта категории gold
	 * @param cardLevel тип карты
	 * @return boolean
	 */
	public static boolean isGold(CardLevel cardLevel)
	{
		return cardLevel == CardLevel.MG || cardLevel == CardLevel.VG || cardLevel == CardLevel.RM || cardLevel == CardLevel.RV;
	}

	/**
	 * Карта категории american express
	 * @param cardLevel тип карты
	 * @return boolean
	 */
	public static boolean isAmericanExpress(CardLevel cardLevel)
	{
		return cardLevel == CardLevel.A1 || cardLevel == CardLevel.A3 || cardLevel == CardLevel.A4;
	}

	/**
	 * Карта категории classic or standard
	 * @param cardLevel тип карты
	 * @return boolean
	 */
	public static boolean isClassicOrStandard(CardLevel cardLevel)
	{
		return cardLevel == CardLevel.VC || cardLevel == CardLevel.VQ || cardLevel == CardLevel.MS;
	}

	/**
	 * Карта категории premium
	 * @param cardLevel тип карты
	 * @return boolean
	 */
	public static boolean isPremium(CardLevel cardLevel)
	{
		return cardLevel == CardLevel.VI || cardLevel == CardLevel.MP
				|| cardLevel == CardLevel.VP || cardLevel == CardLevel.VR
				|| isGold(cardLevel);
	}
}