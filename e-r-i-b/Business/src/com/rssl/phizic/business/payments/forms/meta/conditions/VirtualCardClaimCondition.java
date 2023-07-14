package com.rssl.phizic.business.payments.forms.meta.conditions;

import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.cardProduct.CardProduct;
import com.rssl.phizic.business.cardProduct.CardProductService;
import com.rssl.phizic.business.cardProduct.CardProductType;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.payments.VirtualCardClaim;
import com.rssl.phizic.business.ext.sbrf.dictionaries.CASNSICardProduct;
import com.rssl.phizic.business.statemachine.StateObjectCondition;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.utils.DateHelper;

/**
 * @author Mescheryakova
 * @ created 13.10.2011
 * @ $Author$
 * @ $Revision$
 *
 * �������� ������������ ��������� � ������ ����������� �����
 */

public class VirtualCardClaimCondition implements StateObjectCondition
{
	private static final CardProductService cardProductService = new CardProductService();

	public boolean accepted(StateObject stateObject, StateMachineEvent stateMachineEvent) throws BusinessException, BusinessLogicException
	{
		if (!(stateObject instanceof VirtualCardClaim))
			return false;

		boolean isCheckResult = checkVirtualCardClaim((VirtualCardClaim) stateObject);
		if (isCheckResult)
			((BusinessDocumentBase) stateObject).setRefusingReason("������� �� �������� ����������");  // ��������� ��� ������� ������ ������ ����������
		
		return  isCheckResult;
	}

	private boolean checkVirtualCardClaim(VirtualCardClaim document) throws BusinessException
	{
		// �������� id ���������� �������� �� ���������
		Long cardProductId = document.getCardProductId();
		if (cardProductId == null)
			return false;

		// ���� ��������� ������� � ��� � ��, ���������, ��� �� �������� ��� ��������� � ���� - ����. ����� � �������� ����� �� � ���� �� ����������
		CardProduct cardProduct = cardProductService.findLocaledById(cardProductId);
		if (cardProduct == null)
			return true;

		boolean isOnline = cardProduct.getOnline();    // ������� �������� �������
		boolean isVirtualType = (cardProduct.getType() == CardProductType.VIRTUAL);  // ��� ����������� �����
		boolean isNameEquals =  cardProduct.getName().equals(document.getNameCardProduct()); // ��� �������� � �� � �� ����� ����������
		boolean isExpirationDate = (DateHelper.diff(cardProduct.getStopOpenDate(), DateHelper.getCurrentDate()) < 0); // ���� �������� ���� ���������� �������� ���������
		boolean isKindsProductExists = cardProduct.getKindOfProducts().isEmpty(); // � �������� ��� ����� ����

		if (!isOnline || !isVirtualType	|| !isNameEquals || isExpirationDate || isKindsProductExists)
			return true;

		boolean isKindWrong = true;
		for (CASNSICardProduct casnsiCardProduct : cardProduct.getKindOfProducts())
		{
			boolean isKindIdEquals = casnsiCardProduct.getProductId().equals(document.getKindCardProduct()); // ���� ��������, ��������� �� �����, ����� ���� �� ���������� ��������
			boolean isSubKindIdEqals = casnsiCardProduct.getProductSubId().equals(document.getSubKindCardProduct());  // ������� � ����� � �� �� �. �. �����
			Currency currency = casnsiCardProduct.getCurrency();
			boolean isCurrencyCorrect = false;
			if (currency != null) {
				boolean isCurrencyNumberCorrect = currency.getNumber().equals(document.getCurrencyCodeCardProduct());
				boolean isCurrensyCodeCorrect = currency.getCode().equals(document.getCurrencyNameCardProduct());
				isCurrencyCorrect = isCurrencyNumberCorrect || isCurrensyCodeCorrect;
			}
			boolean isExpirationDateSubKind =  (DateHelper.diff(casnsiCardProduct.getStopOpenDeposit(), DateHelper.getCurrentDate()) > 0);   // ���� �������� ���� ���������� �������� ��� �� ���������

			// ����� ������ ��������� ��� � �� ��������, �������
			if (isKindIdEquals	&&  isSubKindIdEqals && isCurrencyCorrect && isExpirationDateSubKind)
			{
				isKindWrong = false;
				break;
			}
		}
		return isKindWrong;
	}
}
