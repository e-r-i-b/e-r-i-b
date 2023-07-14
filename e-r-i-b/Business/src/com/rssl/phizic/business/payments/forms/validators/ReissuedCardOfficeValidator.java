package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.ReissuedCardDepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.resources.external.CardLink;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * ¬алидатор дл€ проверки возможности перевыпуска карты в выбранном офисе дл€ заданной карты.
 * ≈сли офис заданной карты совпадает с выбранным, то в нем можно перевыпустить эту карты, иначе офис должен поддерживать перевыпуск.
 *
 * @author bogdanov
 * @ created 25.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class ReissuedCardOfficeValidator extends MultiFieldsValidatorBase
{
	private static final ReissuedCardDepartmentService reissuedCardService = new ReissuedCardDepartmentService();

	private static final String OFFICE_REGION = "officeRegion";
	private static final String OFFICE_BRANCH = "officeBranch";
	private static final String OFFICE_OFFICE = "officeOffice";
	private static final String CARD_ID = "cardId";

	/**
	 * “ип карт, перевыпуск которых строго прив€зан к подразделению, выпустившему карту.
	 */
	private static final Set<String> canReissueOnlyCardOffice = new HashSet<String>();

	static
	{
		canReissueOnlyCardOffice.add("21");
		canReissueOnlyCardOffice.add("22");
	}

	public boolean validate(Map values) throws TemporalDocumentException
	{
		try
		{
			CardLink link = (CardLink) retrieveFieldValue(CARD_ID, values);

			ExtendedCodeImpl distOfficeCode = new ExtendedCodeImpl(
					(String) retrieveFieldValue(OFFICE_REGION, values),
					(String) retrieveFieldValue(OFFICE_BRANCH, values),
					(String) retrieveFieldValue(OFFICE_OFFICE, values)
			);
			ExtendedCodeImpl officeCode = new ExtendedCodeImpl(link.getGflTB(), link.getGflOSB(), link.getGflVSP());
			if (distOfficeCode.equals(officeCode))
				return true;

			if (link.getCard().isVirtual())
				return false;

			String uniCardType = link.getCard().getUNICardType();
			if (canReissueOnlyCardOffice.contains(uniCardType))
				return false;

			return reissuedCardService.allowedDepartmentCreditCard(distOfficeCode.getRegion(), distOfficeCode.getBranch(), distOfficeCode.getOffice());
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}
	}
}
