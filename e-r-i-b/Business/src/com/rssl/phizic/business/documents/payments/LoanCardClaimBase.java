package com.rssl.phizic.business.documents.payments;

/**
 * Created by IntelliJ IDEA.
 * User: Moshenko
 * Date: 29.07.2011
 * Time: 18:26:08
 */
public abstract class LoanCardClaimBase extends LoanClaimBase
{
		private static final String CREDIT_CARD_ATTRIBUTE_NAME = "credit-card"; // �������� ���� ���������� �����
		private static final String GRACE_PERIOD_DURATION_ATTRIBUTE_NAME = "grace-period-duration"; // //�������� ������ ��
		private static final String GRACE_PERIOD_RATE = "grace-period-interest-rate";  //% ������ � �������� ������
		private static final String FIRST_YEAR_PAY = "first-year-service"; // ����� �� ������� ������������: ������ ���
		private static final String NEXT_YEAR_PAY = "next-year-service";  // ����� �� ����������� ����
		private static final String ADDITIONAL_TERMS = "additionalTerms";  // �������������� �������
		private static final String MIN_LIMIT = "min-limit";				// ����������� �����
		private static final String MAX_LIMIT = "max-limit";				// ������������ �����
		private static final String MAX_LIMIT_INCLUDE = "max-limit-include"; // ����. ����� ������������
		private static final String INTEREST_RATE = "interest-rate";		// �������� ������ �������
		private static final String CREDIT_CARD_OFFICE = "credit-card-office"; // ����� ��������� ��������� �����

		public String getCreditCard()
		{
			return getNullSaveAttributeStringValue(CREDIT_CARD_ATTRIBUTE_NAME);
		}

		public String getGracePeriodDuration()
		{
			return getNullSaveAttributeStringValue(GRACE_PERIOD_DURATION_ATTRIBUTE_NAME);
		}

		public String getGracePeriodRate()
		{
			return getNullSaveAttributeStringValue(GRACE_PERIOD_RATE);
		}

		public String getFirstYearPay()
		{
			return getNullSaveAttributeStringValue(FIRST_YEAR_PAY);
		}

		public String getNextYearPay()
		{
			return getNullSaveAttributeStringValue(NEXT_YEAR_PAY);
		}

		public String getAdditionalTerms()
		{
			return getNullSaveAttributeStringValue(ADDITIONAL_TERMS);
		}

		public String getMinLimit()
		{
			return getNullSaveAttributeStringValue(MIN_LIMIT);
		}

		public String getMaxLimit()
		{
			return getNullSaveAttributeStringValue(MAX_LIMIT);
		}

		public String getMaxLimitInclude()
		{
			return getNullSaveAttributeStringValue(MAX_LIMIT_INCLUDE);
		}

		public String getInterestRate()
		{
			return getNullSaveAttributeStringValue(INTEREST_RATE);
		}

		public String getCreditCardOffice()
		{
			return getNullSaveAttributeStringValue(CREDIT_CARD_OFFICE);
		}
}
