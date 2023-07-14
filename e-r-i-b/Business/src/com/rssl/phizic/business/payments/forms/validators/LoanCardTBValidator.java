package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.TBSynonymsDictionary;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/** Валидатор для проверки того, что карта и кредит открыты в одном тербанке (определяем по sbrf.PhizIC.properties) + костыль для 99 ТБ (BUG032254)
 * @author akrenev
 * @ created 15.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class LoanCardTBValidator extends MultiFieldsValidatorBase
{
	public static final String FIELD_LOAN_TB = "loanTB";
	public static final String FIELD_CARD_TB = "cardTB";

	private static final Map<String, String> synonyms = new HashMap<String, String>();

	static
	{
		//заполняем специфичной информацией
		synonyms.put("38", "99");
		synonyms.put("99", "99");
	}

	private boolean isSynonyms(String loanTB, String cardTB)
	{
		String mainTB4Loan = synonyms.get(loanTB);
		String mainTB4card = synonyms.get(cardTB);

		if (mainTB4Loan == null || mainTB4card == null)
		    return false;

		return mainTB4Loan.equals(mainTB4card);
	}

	public boolean validate(Map values) throws TemporalDocumentException
	{
		String loanTB = (String) retrieveFieldValue(FIELD_LOAN_TB, values);
		String cardTB = (String) retrieveFieldValue(FIELD_CARD_TB, values);

		//логика позаимствована из CompareValidator
		if (loanTB == null || cardTB == null)
		    return true;

		loanTB = StringHelper.removeLeadingZeros(loanTB);
		cardTB = StringHelper.removeLeadingZeros(cardTB);

		if (loanTB.equals(cardTB))
			return true;

		String mainTB4Loan = ConfigFactory.getConfig(TBSynonymsDictionary.class).getMainTBBySynonym(loanTB);
		String mainTB4card = ConfigFactory.getConfig(TBSynonymsDictionary.class).getMainTBBySynonym(cardTB);

		if (mainTB4Loan.equals(mainTB4card))
			return true;

		return isSynonyms(loanTB, cardTB);
	}
}
