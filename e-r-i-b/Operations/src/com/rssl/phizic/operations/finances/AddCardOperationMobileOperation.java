package com.rssl.phizic.operations.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.finances.CardOperation;
import com.rssl.phizic.business.finances.CardOperationExtendedFields;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ips.IPSConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;
import java.util.List;

/**
 * �������� ���������� ��������� �������� �� ����
 * @author Jatsky
 * @ created 31.10.14
 * @ $Author$
 * @ $Revision$
 */

public class AddCardOperationMobileOperation extends AddCardOperationOperation
{
	private CardOperationExtendedFields operationExtendedFields;

	@Override public void initialize() throws BusinessException
	{
		super.initialize();
		operationExtendedFields = new CardOperationExtendedFields();
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		Long parentPushUID = operationExtendedFields.getParentPushUID();
		if (parentPushUID != null)
		{
			//�������� � �������� �� ����������
			operation.setLoadDate(Calendar.getInstance());
			CardOperationExtendedFields parentOperationExtendedFields = cardOperationService.findByPushUID(parentPushUID);
			CardOperation parentOperation = cardOperationService.findById(parentOperationExtendedFields.getCardOperationId());
			parentOperation.setLoadDate(null);
			cardOperationService.addOrUpdate(parentOperation);

			operation.setOwnerId(parentOperation.getOwnerId());
			operation.setCardNumber(parentOperation.getCardNumber());
			if (StringHelper.isEmpty(operation.getDescription()))
				operation.setDescription(parentOperation.getDescription());
			if (operation.getCategory() == null)
				operation.setCategory(parentOperation.getCategory());

			operationExtendedFields.setAuthCode(parentOperationExtendedFields.getAuthCode());
			operationExtendedFields.setOperationTypeWay(parentOperationExtendedFields.getOperationTypeWay());
			operationExtendedFields.setDate(operation.getDate());
		}
		else
		{
			IPSConfig ipsConfig = ConfigFactory.getConfig(IPSConfig.class);
			int linkingDeltaTime = ipsConfig.getMaxDateDiffForLinking();
			Calendar minDate = DateHelper.addSeconds((Calendar) operation.getDate().clone(), -linkingDeltaTime);
			Calendar maxDate = DateHelper.addSeconds((Calendar) operation.getDate().clone(), linkingDeltaTime);
			List<CardOperation> loadedOperations = cardOperationService.getClientLoadedOperations(operation.getOwnerId(), minDate, maxDate);
			if (!loadedOperations.isEmpty())
			{
				for (CardOperation loadedOperation : loadedOperations)
				{
					if (operation.getCardNumber().startsWith(loadedOperation.getCardNumber().substring(0, 4))
							&& operation.getCardNumber().endsWith(loadedOperation.getCardNumber().substring(loadedOperation.getCardNumber().length() - 4))
							&& loadedOperation.getNationalAmount() != null
							&& loadedOperation.getNationalAmount().compareTo(operation.getNationalAmount()) == 0)
					{
						//�������� ���������� � ��������� ����������
						loadedOperation.setDescription(operation.getDescription());
						loadedOperation.setNationalAmount(operation.getNationalAmount());
						loadedOperation.setOperationType(operation.getOperationType());
						loadedOperation.setCash(operation.isCash());
						loadedOperation.setHidden(operation.getHidden());
						loadedOperation.setLoadDate(Calendar.getInstance());
						loadedOperation.setCategory(operation.getCategory());
						cardOperationService.addOrUpdate(loadedOperation);

						CardOperationExtendedFields loadedCardOperationExtendedFields = cardOperationService.getCardOperationExtendedFields(loadedOperation.getId());
						if (loadedCardOperationExtendedFields == null)
							loadedCardOperationExtendedFields = new CardOperationExtendedFields();
						loadedCardOperationExtendedFields.setCardOperationId(loadedOperation.getId());
						loadedCardOperationExtendedFields.setPushUID(operationExtendedFields.getPushUID());
						loadedCardOperationExtendedFields.setParentPushUID(operationExtendedFields.getParentPushUID());
						loadedCardOperationExtendedFields.setAuthCode(operationExtendedFields.getAuthCode());
						loadedCardOperationExtendedFields.setOperationTypeWay(operationExtendedFields.getOperationTypeWay());
						loadedCardOperationExtendedFields.setLoadDateMAPI(operationExtendedFields.getLoadDateMAPI());
						loadedCardOperationExtendedFields.setCategoryChange(operationExtendedFields.isCategoryChange());
						loadedCardOperationExtendedFields.setDate(loadedOperation.getDate());
						cardOperationService.addOrUpdate(loadedCardOperationExtendedFields);
						return;
					}
				}
			}
		}
		cardOperationService.addOrUpdate(operation);
		operationExtendedFields.setCardOperationId(operation.getId());
		operationExtendedFields.setDate(operation.getDate());
		cardOperationService.addOrUpdate(operationExtendedFields);
	}

	/**
	 * ������������� ������������� ���������� ��������� ��� ��������
	 * @param pushUID - ������������� ���������� ���������
	 * @throws BusinessException
	 */
	public void setPushUID(Long pushUID) throws BusinessException
	{
		if (pushUID == null)
			return;
		CardOperationExtendedFields operationExtField = cardOperationService.findByPushUID(pushUID);
		if (operationExtField != null)
			throw new BusinessException("�������� � pushUID = " + pushUID + " ��� ���� � ����");
		this.operationExtendedFields.setPushUID(pushUID);
	}

	/**
	 * ������������� ����� ����� ��� ��������
	 * @param cardNumber - ����� �����
	 * @throws BusinessException
	 */
	public void setCardNumber(String cardNumber) throws BusinessException, BusinessLogicException
	{
		for (CardLink cardLink : PersonContext.getPersonDataProvider().getPersonData().getCards())
		{
			if (cardLink.getNumber().startsWith(cardNumber.substring(0, 4)) && cardLink.getNumber().endsWith(cardNumber.substring(cardNumber.length() - 4)))
			{
				operation.setCardNumber(cardLink.getNumber());
                break;
			}
		}
		if (StringHelper.isEmpty(operation.getCardNumber()))
		{
			throw new BusinessException("�� ������� ����� � ������� " + cardNumber);
		}
	}

	/**
	 * ������������� �� ������ ������� ��� ��������
	 * @param loginId - �� ������ �������
	 * @throws BusinessException
	 */
	public void setLoginId(Long loginId) throws BusinessException, BusinessLogicException
	{
		if (!loginId.equals(login.getId()))
			throw new BusinessException("�� ������ ������ � �� " + loginId);
		operation.setOwnerId(login.getId());
	}

	/**
	 * ������������� ������������� ���������� ��������� ��� �������� ��������
	 * @param parentPushUID - ������������� ���������� ��������� ��� �������� ��������
	 * @throws BusinessException
	 */
	public void setParentPushUID(Long parentPushUID) throws BusinessException, BusinessLogicException
	{
		operationExtendedFields.setParentPushUID(parentPushUID);
	}

	/**
	 * ������������� ��� ����������� ��� ��������
	 * @param authCode - ��� �����������
	 * @throws BusinessException
	 */
	public void setAuthCode(String authCode) throws BusinessException, BusinessLogicException
	{
		operationExtendedFields.setAuthCode(authCode);
	}

	/**
	 * ������������� ��������� ��� �������� ��� ��������
	 * @param operationTypeWay - ��������� ��� ��������
	 * @throws BusinessException
	 */
	public void setOperationTypeWay(String operationTypeWay) throws BusinessException, BusinessLogicException
	{
		operationExtendedFields.setOperationTypeWay(operationTypeWay);
	}

	/**
	 * ������������� ������� ��������� �������� � ��� � ������� � �������� ��� ��� ��������
	 * @param loadDateMAPI - ������� ��������� �������� � ��� � ������� � �������� ���
	 * @throws BusinessException
	 */
	public void setLoadDateMAPI(Calendar loadDateMAPI) throws BusinessException, BusinessLogicException
	{
		operationExtendedFields.setLoadDateMAPI(loadDateMAPI);
	}

	/**
	 * ������������� ������� ����, ��� ������ ����� ��������� ��� ��������
	 * @param categoryChange - ������� ����, ��� ������ ����� ���������
	 * @throws BusinessException
	 */
	public void setCategoryChange(boolean categoryChange) throws BusinessException, BusinessLogicException
	{
		operationExtendedFields.setCategoryChange(categoryChange);
	}
}
