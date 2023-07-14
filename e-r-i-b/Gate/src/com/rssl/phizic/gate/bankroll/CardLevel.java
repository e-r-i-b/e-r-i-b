package com.rssl.phizic.gate.bankroll;

/**
 * @ author: Vagin
 * @ created: 15.08.13
 * @ $Author
 * @ $Revision
 * ��� �����
 */
public enum CardLevel
{
	A1("AMEX Black"),
	A3("AMEX Platinum (��)") ,
	A4("AMEX Platinum"),
	CM("��������-Maestro"),
	CT("MasterCard ������������"),
	MB("MasterCard Business"),
	MG("Gold MasterCard"),
	MP("MasterCard Platinum"),
	MS("MasterCard Standard"),
	MV("MasterCard Virtual"),
	MQ("MasterCard payPass"),
	MR("MasterCard WBE PR"),
	MW("MasterCard WBE"),
	PC("���100"),
	PO("���100"),
	PP("���100"),
	PR("���100"),
	PS("���100"),
	RM("World MasterCard �������"),
	RV("Visa Gold �������"),
	UE("���"),
	VB("Visa Business"),
	VC("Visa Classic"),
	VE("��������-Visa Electron"),
	VG("Visa Gold"),
	VI("Visa Infinite"),
	VP("Visa Platinum"),
	VT("Visa ������������"),
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
	 * ����� ��������� gold
	 * @param cardLevel ��� �����
	 * @return boolean
	 */
	public static boolean isGold(CardLevel cardLevel)
	{
		return cardLevel == CardLevel.MG || cardLevel == CardLevel.VG || cardLevel == CardLevel.RM || cardLevel == CardLevel.RV;
	}

	/**
	 * ����� ��������� american express
	 * @param cardLevel ��� �����
	 * @return boolean
	 */
	public static boolean isAmericanExpress(CardLevel cardLevel)
	{
		return cardLevel == CardLevel.A1 || cardLevel == CardLevel.A3 || cardLevel == CardLevel.A4;
	}

	/**
	 * ����� ��������� classic or standard
	 * @param cardLevel ��� �����
	 * @return boolean
	 */
	public static boolean isClassicOrStandard(CardLevel cardLevel)
	{
		return cardLevel == CardLevel.VC || cardLevel == CardLevel.VQ || cardLevel == CardLevel.MS;
	}

	/**
	 * ����� ��������� premium
	 * @param cardLevel ��� �����
	 * @return boolean
	 */
	public static boolean isPremium(CardLevel cardLevel)
	{
		return cardLevel == CardLevel.VI || cardLevel == CardLevel.MP
				|| cardLevel == CardLevel.VP || cardLevel == CardLevel.VR
				|| isGold(cardLevel);
	}
}