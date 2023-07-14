package com.rssl.phizic.business.ips;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.finances.CardOperationCategory;
import com.rssl.phizic.business.dictionaries.finances.MerchantCategoryCode;
import com.rssl.phizic.business.finances.CardOperation;
import com.rssl.phizic.business.finances.CardOperationExtendedFields;
import com.rssl.phizic.business.finances.CardOperationService;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ips.IPSConfig;
import com.rssl.phizic.gate.ips.IPSCardOperation;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author lepihina
 * @ created 18.06.14
 * $Author$
 * $Revision$
 * ��������������� �����, ������� �������� ��� �������� �������� �� ��� ����������:
 * 1. ���� �� ��� ����� �������� � ��
 * 2. ���� �� ������ �������� (�������� ����� ������ �������)
 * 3. ���� ��� �������� ��������������� �������� (�������� �� BUSSINES_DOCUNENTS)
 *
 * �� �� ����������� �������� �������:
 * 1. ���� �������� �� ����� ���������, �� � ������ ����������� ����� ����� ��������
 * 2. ���� � ������ ���� ��������, ���� ������� ������ ����������� ����������� ��� ���� ��������, �� ����� �������� ��������� �� ������
 */
public class CardOperationResolver
{
	private static final String DUPLICATE_OPERATIONS_ERROR = "������ ��� ��������, �������� �� �������� ������������� ��� ��������� � ���������" +
			" ��������� ����� ������ �������, ������� ��������� ������ ��������.\n��������: ";
	private static final String DUPLICATE_OPERATION_ERROR = "������ ��� ��������, �������� �� �������� ������������� ��� ��������� � ���������" +
			" ��������� ����� ������ �������, ���� � �� �� �������� �������� ������ ��� ���������� ��������.\n�������� ��� ������� ������ ������: ";

	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);
	private static final CardOperationService cardOperationService = new CardOperationService();

	private List<CardOperation> list;
	private Long loginId;
	private Calendar minListDate;
	private Calendar maxListDate;
	private int monthsDelta;

	private int internalOperationsDeltaTime;
	private int linkingDeltaTime;
	private int maxDeltaTime;
	private List<Long> cardToCardMCCCodes;
	private int packNumber;
	private boolean allLoadedOperationsAreInList;

	/**
	 * @param loginId ������������� ������ �������
	 */
	public CardOperationResolver(Long loginId)
	{
		IPSConfig ipsConfig = ConfigFactory.getConfig(IPSConfig.class);
		internalOperationsDeltaTime = ipsConfig.getInternalOperationsMaxDiffTime().intValue();
		linkingDeltaTime = ipsConfig.getMaxDateDiffForLinking();
		maxDeltaTime = Math.max(internalOperationsDeltaTime, linkingDeltaTime);
		cardToCardMCCCodes = ipsConfig.getCardToCardMCCCodesList();
		monthsDelta = ipsConfig.getLoadingPackSizeInMonths();
		this.loginId = loginId;
		this.list = new ArrayList<CardOperation>();
	}

	/**
	 * @param firstOperationDate ����������� ���� ��������
	 * @param lastOperationDate ������������ ���� ��������
	 */
	public void initialize(Calendar firstOperationDate, Calendar lastOperationDate) throws BusinessException
	{
		setDatePeriod(firstOperationDate, lastOperationDate);
		addToOperationsList();
	}

	private void addToOperationsList() throws BusinessException
	{
		if (allLoadedOperationsAreInList)
			return;
		
		try
		{
			Calendar fromDate = (Calendar)minListDate.clone();
			fromDate = DateHelper.addMonths(fromDate, packNumber * monthsDelta);
			Calendar toDate = (Calendar)minListDate.clone();

			List<CardOperation> newOperations = new ArrayList<CardOperation>();
			while(newOperations.size() == 0 && (packNumber == 0 || toDate.before(maxListDate)))
			{
				toDate = DateHelper.addMonths(toDate, (packNumber + 1) * monthsDelta);
				newOperations = cardOperationService.getClientLoadedOperations(loginId, fromDate, toDate);
				packNumber++;
			}

			list.addAll(newOperations);
			allLoadedOperationsAreInList = !toDate.before(maxListDate);
		}
		catch (BusinessException e)
		{
			log.error("��������� ������ ��� ��������� ������ �������� �� ������� ��� �������� �������� �� ���.");
			throw e;
		}
	}

	private void setDatePeriod(Calendar firstOperationDate, Calendar lastOperationDate)
	{
		Calendar minDate = (Calendar)firstOperationDate.clone();
		Calendar maxDate = (Calendar)lastOperationDate.clone();
		minDate = DateHelper.addSeconds(minDate, -maxDeltaTime);
		maxDate = DateHelper.addSeconds(maxDate, maxDeltaTime);

		this.minListDate = minDate;
		this.maxListDate = maxDate;
	}

	private int incIndex(int index) throws BusinessException
	{
		int newIndex = index + 1;
		if (newIndex == list.size() && !allLoadedOperationsAreInList)
		{
			addToOperationsList();
		}

		return newIndex;
	}

	private void removeOlderOperations(IPSCardOperation operation) throws BusinessException
	{
		Calendar minDate = (Calendar)operation.getOperationDate().clone();
		minDate = DateHelper.addSeconds(minDate, -maxDeltaTime);

		while (list.size() != 0 && list.get(0).getDate().before(minDate))
		{
			list.remove(0);
			if (list.size() == 0 && !allLoadedOperationsAreInList)
			{
				addToOperationsList();
			}
		}
	}

	/**
	 * ��������� ���� �� �������� ����� �����������
	 * @param ipsOperation - ����������� ��������
	 * @return true - ��� ���� ����� �������� � ������
	 */
	public boolean hasEqualOperation(IPSCardOperation ipsOperation) throws BusinessException
	{
		removeOlderOperations(ipsOperation);
		if (list.size() == 0)
			return false;

		int equalOperationIndex = 0;
		while (equalOperationIndex < list.size() && !list.get(equalOperationIndex).getDate().after(ipsOperation.getOperationDate()))
		{
			if (ipsOperation.getDocumentNumber().equals(list.get(equalOperationIndex).getExternalId()))
			{
				return true;
			}

			equalOperationIndex = incIndex(equalOperationIndex);
		}

		return false;
	}

	/**
	 * ���� ������ �������� �� ��������
	 * 1) ���������� �� ����� �id ���������� � ������ �������� (� ��������������� ������)
	 * 2) ����������� �� ���� ������ ���������� �������� � �������� �������������� ���������
	 *
	 * @param ipsOperation ��������, ��� ������� ������ ����
	 * @param operationCardMoney ����� �������� � ������ �����
	 * @param mcc mcc ��������
	 * @param internalIncomeCategory �������� ��������� "������� ����� ������ �������"
	 * @param internalOutcomeCategory ��������� ��������� "������� ����� ������ �������"
	 * @return ������ ��������
	 */
	public CardOperation findInternalOperation(IPSCardOperation ipsOperation, Money operationCardMoney, MerchantCategoryCode mcc, CardOperationCategory internalIncomeCategory, CardOperationCategory internalOutcomeCategory) throws BusinessException
	{
		if (list.size() == 0)
			return null;

		List<CardOperation> suitableInternalOperations = new ArrayList<CardOperation>();
		Calendar minDate = (Calendar)ipsOperation.getOperationDate().clone();
		minDate = DateHelper.addSeconds(minDate, -internalOperationsDeltaTime);
		Calendar maxDate = (Calendar)ipsOperation.getOperationDate().clone();
		maxDate = DateHelper.addSeconds(maxDate, internalOperationsDeltaTime);

		int internalOperationIndex = 0;
		while (internalOperationIndex < list.size() && list.get(internalOperationIndex).getDate().before(minDate))
		{
			internalOperationIndex = incIndex(internalOperationIndex);
		}

		String deviceNumber = CardOperationHelper.getDeviceNumber(ipsOperation);
		while (internalOperationIndex < list.size() && !list.get(internalOperationIndex).getDate().after(maxDate))
		{
			CardOperation cardOperation = list.get(internalOperationIndex);
			if (isSuitableInternalOperation(cardOperation, deviceNumber, operationCardMoney, ipsOperation.getOperationCard().getNumber()))
			{
				suitableInternalOperations.add(cardOperation);
			}

			internalOperationIndex = incIndex(internalOperationIndex);
		}

		if (CollectionUtils.isEmpty(suitableInternalOperations))
			return null;

		if (suitableInternalOperations.size() > 1)
		{
			log.error(getManyInternalOperationsToOneError(ipsOperation, operationCardMoney.getDecimal(), mcc.getCode(), suitableInternalOperations));
			return null;
		}

		CardOperation internalOperation = suitableInternalOperations.get(0);
		// ���� ������� ������ ��������, �� ��� ��� � ��������� "�������� ����� ����� �������", �� ����� ��������� � �������.
		if (internalOperation.getCategory().equals(internalIncomeCategory) || internalOperation.getCategory().equals(internalOutcomeCategory))
		{
			log.error(getOneInternalOperationToManyError(ipsOperation, operationCardMoney.getDecimal(), mcc.getCode(), internalOperation));
			return null;
		}

		return internalOperation;
	}

	/**
	 * ���� ��������������� �������� �������� �� ��������
	 * 1) ���� �������� �������� � �� �������
	 * 2) ����� �������� ���������
	 * 3) ��������� �����
	 *
	 * @param ipsOperation ��������, ��� ������� ������ ����
	 * @param operationNationalMoney ����� �������� � ������������ ������
	 * @return ��������������� ��������
	 */
	public CardOperation findPreloadedOperation(IPSCardOperation ipsOperation, Money operationNationalMoney) throws BusinessException
	{
		if (list.size() == 0)
			return null;

		List<CardOperation> suitablePreloadedOperations = new ArrayList<CardOperation>();
		Calendar minDate = (Calendar)ipsOperation.getOperationDate().clone();
		minDate = DateHelper.addSeconds(minDate, -linkingDeltaTime);
		Calendar maxDate = (Calendar)ipsOperation.getOperationDate().clone();
		maxDate = DateHelper.addSeconds(maxDate, linkingDeltaTime);

		int preloadedOperationIndex = 0;
		while (preloadedOperationIndex < list.size() && list.get(preloadedOperationIndex).getDate().before(minDate))
		{
			preloadedOperationIndex = incIndex(preloadedOperationIndex);
		}

		while (preloadedOperationIndex < list.size() && !list.get(preloadedOperationIndex).getDate().after(maxDate))
		{
			CardOperation cardOperation = list.get(preloadedOperationIndex);
			if (isSuitablePreloadedOperation(cardOperation, operationNationalMoney, ipsOperation))
			{
				suitablePreloadedOperations.add(cardOperation);
			}
			preloadedOperationIndex = incIndex(preloadedOperationIndex);
		}

		if (CollectionUtils.isEmpty(suitablePreloadedOperations))
		{
			log.error("�������� � �����������: loginId=" + loginId + ", ����� �����=" + ipsOperation.getOperationCard().getNumber() + ", ����� ��������="
					+ operationNationalMoney.getDecimal() + ", ���� ��������=" + ipsOperation.getOperationDate().getTime().toString() + "  ����� �� �������");
			return null;
		}

		if (suitablePreloadedOperations.size() > 1)
		{
			log.error("�������� � �����������: loginId=" + loginId + ", ����� �����=" + ipsOperation.getOperationCard().getNumber() + ", ����� ��������="
					+ operationNationalMoney.getDecimal() + ", ���� ��������=" + ipsOperation.getOperationDate().getTime().toString() + "  ������� � �����������");
			return null;
		}

		return suitablePreloadedOperations.get(0);
	}

	private boolean isSuitableInternalOperation(CardOperation operation, String deviceNumber, Money operationCardMoney, String cardNumber)
	{
		if (!StringUtils.equals(operation.getDeviceNumber(), deviceNumber))
			return false;

		if (operation.getCardAmount() == null || operationCardMoney.getDecimal().negate().compareTo(operation.getCardAmount()) != 0)
			return false;

		if (!cardToCardMCCCodes.contains(operation.getMccCode()))
			return false;

		if (StringUtils.equals(operation.getCardNumber(), cardNumber))
			return false;

		return true;
	}

	private boolean isSuitablePreloadedOperation(CardOperation operation, Money operationNationalMoney, IPSCardOperation ipsCardOperation) throws BusinessException
	{
		CardOperationExtendedFields cardOperationExtendedFields = null;
		if (operation.getLoadDate() != null)
		{
			if (operation.getExternalId() != null)
				return false;
			cardOperationExtendedFields = cardOperationService.getCardOperationExtendedFields(operation.getId());
			if (cardOperationExtendedFields == null)
				return false;
		}

		if (operation.getNationalAmount() == null || operation.getNationalAmount().compareTo(operationNationalMoney.getDecimal()) != 0)
			return false;

		if (cardOperationExtendedFields == null)
			cardOperationExtendedFields = cardOperationService.getCardOperationExtendedFields(operation.getId());

		if (cardOperationExtendedFields != null)
		{
			if (cardOperationExtendedFields.getParentPushUID() != null)
				return false;

			if (cardOperationExtendedFields.getPushUID() != null)
			{
				if (!StringHelper.isEmpty(cardOperationExtendedFields.getAuthCode()) && !StringHelper.equals(cardOperationExtendedFields.getAuthCode(), ipsCardOperation.getAuthCode()))
					return false;
				if (!(operation.getCardNumber().startsWith(ipsCardOperation.getOperationCard().getNumber().substring(0, 4)) && operation.getCardNumber().endsWith(ipsCardOperation.getOperationCard().getNumber().substring(ipsCardOperation.getOperationCard().getNumber().length() - 4))))
					return false;
			}
		}
		if (!StringUtils.equals(operation.getCardNumber(), ipsCardOperation.getOperationCard().getNumber()))
			return false;

		return true;
	}

	private String getManyInternalOperationsToOneError(IPSCardOperation ipsOperation, BigDecimal cardAmount, Long mccCode, List<CardOperation> internalOperations)
	{
		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append(DUPLICATE_OPERATIONS_ERROR).append("CardOperation{")
				.append("externalId='").append(ipsOperation.getDocumentNumber()).append('\'')
				.append(", date=").append(DateHelper.toISO8601DateFormat(ipsOperation.getOperationDate()))
				.append(", cardNumber='").append(MaskUtil.getCutCardNumberForLog(ipsOperation.getOperationCard().getNumber())).append('\'')
				.append(", description='").append(CardOperationHelper.getOperationDescription(ipsOperation)).append('\'')
				.append(", cardAmount=").append(cardAmount)
				.append(", deviceNumber=").append(CardOperationHelper.getDeviceNumber(ipsOperation))
				.append(", ownerLoginId=").append(loginId)
				.append(", mccCode=").append((mccCode != null ? mccCode : "null"))
				.append('}');
		errorMessage.append(".\n������ ��������: ");

		for(CardOperation operation : internalOperations)
		{
			errorMessage.append(operation.toString()).append(";\n");
		}
		return errorMessage.toString();
	}

	private String getOneInternalOperationToManyError(IPSCardOperation ipsOperation, BigDecimal cardAmount, Long mccCode, CardOperation internalOperation)
	{
		StringBuilder errorMessage = new StringBuilder();
		errorMessage.append(DUPLICATE_OPERATION_ERROR).append("CardOperation{")
				.append("externalId='").append(ipsOperation.getDocumentNumber()).append('\'')
				.append(", date=").append(DateHelper.toISO8601DateFormat(ipsOperation.getOperationDate()))
				.append(", cardNumber='").append(MaskUtil.getCutCardNumberForLog(ipsOperation.getOperationCard().getNumber())).append('\'')
				.append(", description='").append(CardOperationHelper.getOperationDescription(ipsOperation)).append('\'')
				.append(", cardAmount=").append(cardAmount)
				.append(", deviceNumber=").append(CardOperationHelper.getDeviceNumber(ipsOperation))
				.append(", ownerLoginId=").append(loginId)
				.append(", mccCode=").append((mccCode != null ? mccCode : "null"))
				.append('}');
		errorMessage.append(".\n��������, ������� ������� ��� ������: ");
		errorMessage.append(internalOperation.toString());
		return errorMessage.toString();
	}
}
