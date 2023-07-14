package com.rssl.phizic.business.documents;

import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.business.dictionaries.departments.DepartmentsRecoding;
import com.rssl.phizic.business.dictionaries.departments.DepartmentsRecordingService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;

/**
 * Заявка на перевыпуск карты.
 *
 * @author bogdanov
 * @ created 22.03.2013
 * @ $Author$
 * @ $Revision$
 */

public class ReIssueCardClaim extends GateExecutableDocument implements com.rssl.phizic.gate.payments.ReIssueCardClaim
{
	public static final String CARD_ID = "card";
	public static final String IS_COMMISSION = "is-commission";
	public static final String REASON_CODE = "reason-code";
	public static final String CARD_NUMBER = "card-number";
	public static final String CARD_TYPE = "card-type-name";

	public static final String DESTINATION_OFFICE_REGION = "destination-office-region";
	public static final String DESTINATION_OFFICE_BRANCH = "destination-office-branch";
	public static final String DESTINATION_OFFICE_OFFICE = "destination-office-office";

	public static final String SOURCE_OFFICE_REGION = "source-office-region";
	public static final String SOURCE_OFFICE_BRANCH = "source-office-branch";
	public static final String SOURCE_OFFICE_OFFICE = "source-office-office";

	public static final String OFFICE_INFO = "office-info";
	public static final String CARD_BLOCK_REASON = "card-block-reason";

	public String getOfficeInfo()
	{
		return getNullSaveAttributeStringValue(OFFICE_INFO);
	}

	public void setOfficeInfo(String officeInfo)
	{
		setNullSaveAttributeStringValue(OFFICE_INFO, officeInfo);
	}

	public String getCardBlockReason()
	{
		return getNullSaveAttributeStringValue(CARD_BLOCK_REASON);
	}

	public void setCardBlockReason(String cardBlockReason)
	{
		setNullSaveAttributeStringValue(CARD_BLOCK_REASON, cardBlockReason);
	}

	public Code getBankInfoCode()
	{
		ExtendedCodeImpl code = new ExtendedCodeImpl();

		code.setRegion(getNullSaveAttributeStringValue(SOURCE_OFFICE_REGION));
		code.setBranch(getNullSaveAttributeStringValue(SOURCE_OFFICE_BRANCH));
		code.setOffice(getNullSaveAttributeStringValue(SOURCE_OFFICE_OFFICE));

		return code;
	}

	public Code getConvertedBankInfoCode() throws GateException
	{
		String region = getNullSaveAttributeStringValue(SOURCE_OFFICE_REGION);
		String branch = getNullSaveAttributeStringValue(SOURCE_OFFICE_BRANCH);
		String office = getNullSaveAttributeStringValue(SOURCE_OFFICE_OFFICE);

		return getConvertedCode(region, branch, office, false);
	}

	public void setBankInfoCode(Code bankInfoCode)
	{
		ExtendedCodeImpl code = new ExtendedCodeImpl(bankInfoCode);

		setNullSaveAttributeStringValue(SOURCE_OFFICE_REGION, code.getRegion());
		setNullSaveAttributeStringValue(SOURCE_OFFICE_BRANCH, code.getBranch());
		setNullSaveAttributeStringValue(SOURCE_OFFICE_OFFICE, code.getOffice());
	}

	public String getCardId()
	{
		return getNullSaveAttributeStringValue(CARD_ID);
	}

	public String getCardType() throws GateException
	{
		return getNullSaveAttributeStringValue(CARD_TYPE);
	}

	public void setCardNumber(String cardNumber)
	{
		setNullSaveAttributeStringValue(CARD_NUMBER, cardNumber);	
	}

	public String getCardNumber()
	{
		return getNullSaveAttributeStringValue(CARD_NUMBER);
	}

	public void setCardId(String cardId)
	{
		setNullSaveAttributeStringValue(CARD_ID, cardId);
	}

	public Class<? extends GateDocument> getType()
	{
		return com.rssl.phizic.gate.payments.ReIssueCardClaim.class;
	}

	public boolean getIsCommission()
	{
		return (Boolean) getNullSaveAttributeValue(IS_COMMISSION);
	}

	public void setIsCommission(boolean isCommission)
	{
		setNullSaveAttributeBooleanValue(IS_COMMISSION, isCommission);
	}

	public Code getDestinationCode()
	{
		ExtendedCodeImpl code = new ExtendedCodeImpl();

		code.setRegion(getNullSaveAttributeStringValue(DESTINATION_OFFICE_REGION));
		code.setBranch(getNullSaveAttributeStringValue(DESTINATION_OFFICE_BRANCH));
		code.setOffice(getNullSaveAttributeStringValue(DESTINATION_OFFICE_OFFICE));

		return code;
	}

	public Code getConvertedDestinationCode() throws GateException
	{
		String region = getNullSaveAttributeStringValue(DESTINATION_OFFICE_REGION);
		String branch = getNullSaveAttributeStringValue(DESTINATION_OFFICE_BRANCH);
		String office = getNullSaveAttributeStringValue(DESTINATION_OFFICE_OFFICE);

		return getConvertedCode(region, branch, office, true);
	}

	private ExtendedCodeImpl getConvertedCode(String region, String branch, String office, boolean isDestination) throws GateException
	{
		DepartmentsRecoding departmentsRecoding = null;
		try
		{
			DepartmentsRecordingService documentService = new DepartmentsRecordingService();
			departmentsRecoding = documentService.getDepartmentsRecodingByEribCodes(region, branch, office);
		}
		catch (Exception e)
		{
			throw new GateException("Ошибка при поиске подразделения в справочнке перекодировки СПООБК2", e);
		}

		if (departmentsRecoding == null)
			throw new GateException("Не удается найти подразделение в справочнке перекодировки СПООБК2");

		ExtendedCodeImpl code = new ExtendedCodeImpl();
		if (isDestination)
		{
			String despatch = departmentsRecoding.getDespatch();

			code.setRegion(despatch.substring(0, 2));
			code.setBranch(despatch.substring(2, 6));
			code.setOffice(despatch.substring(6, despatch.length()));
		}
		else
		{
			code.setRegion(region);
			code.setBranch(branch);
			code.setOffice(office);
		}

		return code;
	}

	public void setDestinationCode(Code destinationCode)
	{
		ExtendedCodeImpl code = new ExtendedCodeImpl(destinationCode);

		setNullSaveAttributeStringValue(DESTINATION_OFFICE_REGION, code.getRegion());
		setNullSaveAttributeStringValue(DESTINATION_OFFICE_BRANCH, code.getBranch());
		setNullSaveAttributeStringValue(DESTINATION_OFFICE_OFFICE, code.getOffice());
	}

	public String getReasonCode()
	{
		return getNullSaveAttributeStringValue(REASON_CODE);
	}

	public void setReasonCode(String reasonCode)
	{
		setNullSaveAttributeStringValue(REASON_CODE, reasonCode);
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.NOT_PAYMENT_OPEATION;
	}
}
