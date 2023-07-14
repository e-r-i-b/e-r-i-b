package com.rssl.phizic.business.loanCardOffer;

/**
 * User: Moshenko
 * Date: 24.05.2011
 * Time: 11:20:16
 * “ипы предложений на предодобреную кредитную карту
 */
public enum LoanCardOfferType
{

		/**
        * ѕредложение на открытие новой кредитной карты.
        */
		newCard("1"),
		/**
        * ѕредложение на увеличение кредитного лимита
        */
		changeLimit("2"),
	    /**
        * ѕредложение по изменению вида карты и лимита
        */
		changeType("3");

		private String value;

		LoanCardOfferType(String value)
		{
			this.value = value;
		}

		public String getValue() { return value; }

		public static LoanCardOfferType valueOf(int value)
		{
			switch (value)
			{
				case 1:
					return newCard;
				case 2:
					return changeLimit;
				case 3:
					return changeType;

			}
			throw new IllegalArgumentException("Ќеизвестный тип предложений [" + value + "]");
	}

}
