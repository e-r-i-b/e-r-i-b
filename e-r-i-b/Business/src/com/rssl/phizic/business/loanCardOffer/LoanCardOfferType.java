package com.rssl.phizic.business.loanCardOffer;

/**
 * User: Moshenko
 * Date: 24.05.2011
 * Time: 11:20:16
 * ���� ����������� �� ������������� ��������� �����
 */
public enum LoanCardOfferType
{

		/**
        * ����������� �� �������� ����� ��������� �����.
        */
		newCard("1"),
		/**
        * ����������� �� ���������� ���������� ������
        */
		changeLimit("2"),
	    /**
        * ����������� �� ��������� ���� ����� � ������
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
			throw new IllegalArgumentException("����������� ��� ����������� [" + value + "]");
	}

}
