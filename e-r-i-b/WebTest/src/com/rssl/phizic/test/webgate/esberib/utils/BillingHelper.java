package com.rssl.phizic.test.webgate.esberib.utils;

import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import org.apache.commons.lang.BooleanUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author krenev
 * @ created 30.11.2010
 * @ $Author$
 * @ $Revision$
 * ������ ��� ������������ �������� ������� ��� ��������� � ����
 */
public class BillingHelper extends BaseResponseHelper
{
	/**
	 * ��������� ����: �������� ������� ��������.
	 * ������������ ��� ������������ �������� ����.
	 * ���� �������� �������� ������ 0 � ��� ������ ���������� ������.
	 */
	private static final String CPFL_COUNTER_FIELD_NAME = "cpfl-counter";

	// ���� � ������� ��������� ���������� � ��� ������� �� ����������
	private static final String ESB_VARIANT_ROUTE = "esb-variant-route";
	// ����, ���������� ������ ��� ��������� ����������� �� ���������� ����������
	private static final String ESB_LIST_KPP = "esb-list-kpp";
	private static final String ESB_RECEIVER_NAME = "esb-receiver-name";

	// �������� ������� ����� ��������� CPFL_VARIANT_ROUTE
	private static final String NOT_FOUND_PROVIDER = "not-found-provider";
	private static final String FOUND_ONE_PROVIDER = "found-one-provider";
	private static final String FOUND_MANY_PROVIDERS = "found-many-providers";

	private static final int NEW_REQUISITES_COUNT = 3;//���������� ����� ����� ����������� ��� 1 ������� ���������� �������.
	private static long counter = 0;

	/**
	 * ������������ ����� �� ������ TBP_PR(���������� ������������ �������)
	 * @param parameters ������������ ������
	 * @return �����.
	 * @throws RemoteException
	 */
	public IFXRs_Type createBillingPayPrepRs(IFXRq_Type parameters) throws RemoteException
	{
		BillingPayPrepRq_Type request = parameters.getBillingPayPrepRq();
		BillingPayPrepRs_Type responce = new BillingPayPrepRs_Type();

		if (BooleanUtils.isTrue(request.getIsTemplate()))
			prepareGenericPayment(request, responce);

		else if (BooleanUtils.isTrue(request.getIsAutoPayment()))
			prepareGenericPayment(request, responce);
		
		else if ("UEC_Payment".equals(request.getRecipientRec().getCodeService()))
			UECPaymentHelper.preparePayment(request, responce);

		else if ("OZON".equals(request.getRecipientRec().getCodeService()))
			EInvoicingESBPaymentHelper.preparePayment(request, responce);

		else prepareGenericPayment(request, responce);

		IFXRs_Type ifxRs = new IFXRs_Type();
		ifxRs.setBillingPayPrepRs(responce);
		return ifxRs;
	}

	private void prepareGenericPayment(BillingPayPrepRq_Type request, BillingPayPrepRs_Type responce)
	{
		Random random = new Random();

		Status_Type status = new Status_Type();
		counter++;
		if (counter % 30 == 0)
		{
			status.setStatusCode(500);
			status.setStatusDesc("�������� �������� ���������� ������ 30 ������");
			status.setServerStatusDesc(SERVER_STATUS_DESC);
		}
		else if (random.nextInt(10) == 1)
		{
			status.setStatusCode(-777);
			status.setStatusDesc("�������� ���������� ������ -777");
			status.setServerStatusDesc(SERVER_STATUS_DESC);
		}
		else
		{
			status.setStatusCode(0);
		}

		responce.setSystemId(request.getSystemId());
		responce.setOperUID(request.getOperUID());
		responce.setRqTm(PaymentsRequestHelper.generateRqTm());
		responce.setRqUID(PaymentsRequestHelper.generateUUID());
		responce.setStatus(status);

		RecipientRec_Type oldRecipientRec = request.getRecipientRec();
		RecipientRec_Type newRecipientRec = null;

		// ����������� ����, ������� ��������� ����� ���������� ����������� ������� �� ����������
		Requisite_Type esbVariantRouteField = getFieldByNameBS(ESB_VARIANT_ROUTE, oldRecipientRec.getRequisites());
		// ���� ���� ��� ��� ��� ������ �� ��������� ���������� ��� ������ ����������
		String value = esbVariantRouteField == null ?	FOUND_ONE_PROVIDER : esbVariantRouteField.getEnteredData()[0];

		// ���� �� ������� �� ������ ����������
		if(NOT_FOUND_PROVIDER.equals(value))
		{
			newRecipientRec = copyRecipientRec(oldRecipientRec);
			Requisite_Type name = getFieldByNameBS(ESB_RECEIVER_NAME, oldRecipientRec.getRequisites());
			newRecipientRec.setName(name.getEnteredData()[0]);
			name.setIsVisible(false);
			name.setIsEditable(false);
		}
		// ���� ������ ���� ���������
		else if(FOUND_ONE_PROVIDER.equals(value))
		{
			List<Requisite_Type> newRequisites = generateRequisites(oldRecipientRec, BooleanUtils.isTrue(request.getIsAutoPayment()));
			newRecipientRec = copyRecipientRec(oldRecipientRec);
			// ���� ����������� ����������, ��������� ���������� �� ��������� ������������
			if(BooleanUtils.isTrue(request.getIsAutoPayment()))
				addAutoPayDetails(newRecipientRec);

			newRecipientRec.setBankInfo(new BankInfo_Type("000","111", "222", null, null));
			newRecipientRec.setNotVisibleBankDetails(random.nextBoolean());
			newRecipientRec.setPhoneToClient(RandomHelper.rand(15, RandomHelper.DIGITS));
			newRecipientRec.setNameOnBill(oldRecipientRec.getName());

			newRecipientRec.setRequisites(newRequisites.toArray(new Requisite_Type[newRequisites.size()]));

			createAutoPayBlock(request.getIsAutoPayment(), newRecipientRec);
		}
		// ���� ������� ��������� �����������
		else if(FOUND_MANY_PROVIDERS.equals(value))
		{
			List<Requisite_Type> newRequisites = generateRequisites(oldRecipientRec, false);
			newRecipientRec = createRecipientRec(oldRecipientRec, newRequisites);
			newRecipientRec.setBankInfo(new BankInfo_Type("000","111", "222", null, null));
			// ���� ���� ������ ��� � ����������� ��� ��� ���������������
			Requisite_Type listKpp = getFieldByNameBS(ESB_LIST_KPP, oldRecipientRec.getRequisites());
			listKpp.setIsEditable(false);
			// ������� ���������� � ����������� ��� ��� � ��������� ��� (����� ������ ���� ��� ��� �����)
			newRecipientRec.setKPP(listKpp.getEnteredData()[0]);
			newRecipientRec.setName("��������� � ���:" + listKpp.getEnteredData()[0]);
			// ������ ����� ����� �� �� ���������
			esbVariantRouteField.setEnteredData(new String[]{FOUND_ONE_PROVIDER});

			createAutoPayBlock(request.getIsAutoPayment(), newRecipientRec);
		}

		for(Requisite_Type requisite : newRecipientRec.getRequisites())
		{
			// �������� ����������� ������ � ��������� ����.
			counter++;
			if (counter % 20 == 5)
				requisite.setError("�����-�� ������ � ���� '" + requisite.getNameVisible() + "'");
		}

		if (status.getStatusCode() == -777 && random.nextBoolean())
		{
			// �������� ��������� ����
			List<Requisite_Type> requisites = new ArrayList<Requisite_Type>();
			requisites.addAll(Arrays.asList(newRecipientRec.getRequisites()));
			List<FieldDataType> fieldDataTypes = getFieldDataTypesExceptInternalType();
			requisites.add(createRequisite(fieldDataTypes.get(random.nextInt(fieldDataTypes.size()))));
			newRecipientRec.setRequisites(requisites.toArray(new Requisite_Type[requisites.size()]));
		}

		if (isRequisitesSufficient(oldRecipientRec.getRequisites(), newRecipientRec.getRequisites()))
		{
			//��������� ����, ��������������� � ����� ������������� ���������� �������.
			responce.setMadeOperationId("MadeOperationId");
			responce.setCommission(new BigDecimal("3.12"));
			responce.setCommissionCur("RUR");
			// ��� ����������� ����� ������� withCommision, ��������� � ���, ����� �� ����������� ������� � ���������
			if(BooleanUtils.isTrue(request.getIsAutoPayment()))
				responce.setWithCommision(new Random().nextBoolean());
		}

		responce.setRecipientRec(newRecipientRec);
	}

	/**
	 * ��������� ������������� ����� ��� ���������� ��������:
	 * 1) ������ ����� ��������.
	 * 1) � ����� ��� ������.
	 * @param oldRequisites ���������� ���������
	 * @param newRequisites ����� ���������.
	 * @return ����������/������������
	 */
	private boolean isRequisitesSufficient(Requisite_Type[] oldRequisites, Requisite_Type[] newRequisites)
	{
		if (oldRequisites == null && newRequisites == null)
		{
			//��� ����� ���������� - ���������� ����������
			return true;
		}
		if (oldRequisites == null ^ newRequisites == null)
		{
			//����� ���� ���� - ���������� ������������
			return false;
		}
		if (oldRequisites.length != newRequisites.length)
		{
			//���������� ���������� ����������
			return false;
		}
		//��������� ������� ������
		for (Requisite_Type requisite : newRequisites)
		{
			if (!StringHelper.isEmpty(requisite.getError()))
			{
				return false;
			}
		}
		//������ ���: ������� �� ���������� �� ����� ���� ������ ������.
		for (Requisite_Type oldRequisite : oldRequisites)
		{
			boolean requisiteAbsent = true;
			for (Requisite_Type newRequisite : newRequisites)
			{
				if (newRequisite.getNameBS().equals(oldRequisite.getNameBS()))
				{
					requisiteAbsent = false;
				}
			}
			if (requisiteAbsent)
			{
				//�����-�� �� ������ ���������� ���������� -> ��������� �����
				return false;
			}
		}
		return true;
	}

	/**
	 * ������������ ����� �� ������ TBP_PAY(�������� ������������ �������)
	 * @param parameters ������������ ������
	 * @return �����.
	 * @throws RemoteException
	 */
	public IFXRs_Type createBillingPayExecRs(IFXRq_Type parameters) throws RemoteException
	{
		BillingPayExecRq_Type request = parameters.getBillingPayExecRq();
		BillingPayExecRs_Type responce = new BillingPayExecRs_Type();

		Status_Type status = new Status_Type();
		counter++;
/*
		if (counter % 10 == 5)
		{
			status.setStatusCode(500);
			status.setStatusDesc("�������� �������� ���������� ������ 10 ������");
		}
		else
*/
		{
			status.setStatusCode(0);
		}
		responce.setOperUID(request.getOperUID());
		responce.setRqTm(PaymentsRequestHelper.generateRqTm());
		responce.setRqUID(PaymentsRequestHelper.generateUUID());
		responce.setStatus(status);
		responce.setAuthorizationCode(counter);
		responce.setAuthorizationDtTm(getRqTm());
		IFXRs_Type ifxRs = new IFXRs_Type();
		ifxRs.setBillingPayExecRs(responce);
		return ifxRs;
	}

	/**
	 * ������������ ����� �� ������ TBP_CAN(������ ������������ �������)
	 * @param parameters ������������ ������
	 * @return �����.
	 * @throws RemoteException
	 */
	public IFXRs_Type createBillingPayCanRs(IFXRq_Type parameters)
	{
		BillingPayCanRq_Type request = parameters.getBillingPayCanRq();
		BillingPayCanRs_Type responce = new BillingPayCanRs_Type();

		Status_Type status = new Status_Type();
		status.setStatusCode(0);
//		status.setStatusDesc("�������� �������� ���������� ������ 10 ������");
		responce.setStatus(status);
		responce.setOperUID(request.getOperUID());
		responce.setRqTm(PaymentsRequestHelper.generateRqTm());
		responce.setRqUID(PaymentsRequestHelper.generateUUID());
		responce.setRequisites(request.getRecipientRec().getRequisites());
		responce.setAuthorizationCode(++counter);
		responce.setAuthorizationDtTm(getRqTm());
		IFXRs_Type ifxRs = new IFXRs_Type();
		ifxRs.setBillingPayCanRs(responce);
		return ifxRs;
	}

	/**
	 * ��������� ����������
	 * @param recipient RecipientRec_Type
	 * @param isAutoPayment ������, ����������� �������� �� ������ ������� �� ������������
	 * @return ������ ����������
	 */
	private List<Requisite_Type> generateRequisites(RecipientRec_Type recipient, boolean isAutoPayment)
	{
		Random random = new Random();
		Requisite_Type[] oldReqiusites = recipient.getRequisites();
		int requisitesSize = (oldReqiusites == null) ? 0 : oldReqiusites.length;
		//�������� ������� �������� �� ���������� �������.
		Requisite_Type cpflField = getFieldByNameBS(CPFL_COUNTER_FIELD_NAME, oldReqiusites);
		List<FieldDataType> fieldDataTypes  = getFieldDataTypesExceptInternalType();
		if (cpflField == null)
		{
			//������� ������������ ����(�) ����.
			Requisite_Type[] amountFields = createAmoutFields(!isAutoPayment);
			int ammountFieldsLength = amountFields.length;
			//�������� �������� �� ���� -> ��� ������ ������ ->������� ����� ����
			List<Requisite_Type> newReqiusites = new ArrayList<Requisite_Type>(requisitesSize + NEW_REQUISITES_COUNT + ammountFieldsLength + 1);//+1 - ��� ���� ������� ����
			int i = 0;
			//�������� ������ ����
			if (oldReqiusites != null)
			{
				for (Requisite_Type requisite : oldReqiusites)
				{
					newReqiusites.add(requisite);
					i++;
				}
			}
			//�������� ��������� �������������� ������� ������������� ��
			boolean isRiskRecipient = random.nextBoolean();
			if (isRiskRecipient == Boolean.TRUE)
				newReqiusites.add(createRiskRecipientRequisite());

			//��������� �����.
			for (int j = 0; j < NEW_REQUISITES_COUNT; i++, j++)
			{
				//�������� �������� ���.
				FieldDataType type = fieldDataTypes.get(random.nextInt(fieldDataTypes.size()));
				newReqiusites.add(createRequisite(type));
			}
			//��������� ����-�������
			newReqiusites.add(createCPFLCounterRequisite());
			newReqiusites.addAll(Arrays.asList(amountFields));
			return newReqiusites;
		}
		//���� �� ��������� ���� ����
		for (Requisite_Type requisite : oldReqiusites)
		{
			//�������� ������ �������� ���������(����������� �� �������)
			Boolean isVisible = requisite.getIsVisible();
			if (random.nextInt() % oldReqiusites.length == 0 && isVisible != null && isVisible)
			{
				FieldDataType type = FieldDataType.valueOf(requisite.getType());
				String value = null;
				switch (type)
				{
					case number:
					case integer:
						value = "13";
						break;
					case date:
					case calendar:
						value = "18.08.2004";
						break;
					case list:
					case set:
						String[] menu = requisite.getMenu();
						value = menu[random.nextInt(menu.length)];
						break;
					case string:
						value = "���������� ��������� ��������";
						break;
					case money:
						value = "12.31";
						break;
				}
				requisite.setEnteredData(new String[]{value});
			}
		}
		//������������ ���� ������� ����.
		String[] enteredData = cpflField.getEnteredData();
		if (enteredData == null || enteredData.length != 1)
		{
			cpflField.setError("��� ���� " + CPFL_COUNTER_FIELD_NAME + "������ ���� ������ ����� 1 ��������");
			return Arrays.asList(oldReqiusites);
		}
		String value = enteredData[0];
		if (StringHelper.isEmpty(value))
		{
			cpflField.setError("�� ������ �������� ��� ���� " + CPFL_COUNTER_FIELD_NAME);
		}
		try
		{
			int intValue = Integer.parseInt(value);
			if (intValue-- > 0)
			{
				cpflField.setError("���������� ������� ������, ���� �� ������� 0 � ���� " + CPFL_COUNTER_FIELD_NAME);
				enteredData[0] = String.valueOf(intValue);
				List<Requisite_Type> newReqiusites = new ArrayList<Requisite_Type>(oldReqiusites.length+1);
				for(Requisite_Type type : oldReqiusites)
					newReqiusites.add(type);
				FieldDataType type = fieldDataTypes.get(random.nextInt(fieldDataTypes.size()));
				newReqiusites.add(createRequisite(type));
				return newReqiusites;
			}
			return Arrays.asList(oldReqiusites);
		}
		catch (NumberFormatException e)
		{
			cpflField.setError("�������� ��� ���� " + CPFL_COUNTER_FIELD_NAME + " ������ ���� ����������� ������ ");
			return Arrays.asList(oldReqiusites);
		}
	}

	/**
	 * �������� ��������� ������������� ��
	 * @return
	 */
	private Requisite_Type createRiskRecipientRequisite()
	{
		Requisite_Type result = new Requisite_Type();
		result.setNameVisible("������� ������������� ��");
		result.setNameBS("_isRiskRecipient");
		result.setDescription(result.getNameVisible());
		result.setComment(result.getNameVisible());
		result.setType(FieldDataType.string.name());
		result.setIsRequired(false);
		result.setIsSum(false);
		result.setIsKey(false);
		result.setIsEditable(false);
		result.setIsVisible(false);
		result.setIsForBill(false);
		result.setIncludeInSMS(false);
		result.setSaveInTemplate(false);
		result.setHideInConfirmation(false);
		result.setDefaultValue("True");
		return result;
	}

	/**
	 * �������� ����� ����� �����/������������ �����
	 * @param isVisible �������� �� ������� ����� �������
	 * @return ������ ����� ����.
	 */
	private Requisite_Type[] createAmoutFields(boolean isVisible)
	{

		int random = new Random().nextInt(7);
		switch (random)
		{
			//3. ������ � �������� ������������� (�, ���� ����� ���� ���. ���������� � ������������),
			//������������� ������������ ����� ������, ������������ ����� �������� �����.
			case 0:
				return new Requisite_Type[]{createSimpleAmountField(true, isVisible)};
			//4. �� ��, ��� ����������, �� ������������ �� ����� �������� �����. 
			case 1:
				return new Requisite_Type[]{createSimpleAmountField(false, isVisible)};
			//5. ���� ����� ����� ������������� � ��������� �� �������,
			// ������������ �� ����� �������� ������� ����� (������ ����������� ��� �� �����������).
			// ��������, ������ ������ �� ��� � ��������� ��������� ���� �� ����, ���, ����.
			case 2:
				return createSplitDebtAmountFields(false, isVisible, false);
			//6. ���� ����� ����� ������������� � ��������� �� �������, ������������
			// ����� �������� ������ ����� ����� �������, ����� ���� ����� �� ������� �����
			// ������������� �������� � ������������ ������������ ���� ��� �������������
			// (�������� �� ������������ ��� �� �����).
			case 3:
				return createSplitDebtAmountFields(true, isVisible, false);
			//7. ��������� ������ ���� ��������������, ������������ �� ����� ������ �����,
			// �� ����� ������� ���� �� ��� (������ - ������ �� ���������� ��� ��� ���������)
			case 4:
				return createFewDebtAmountFields(false, isVisible);
			//8. ��������� ������ ���� ��������������, ������������ �� ����� ������ �����,
			// �� ����� ������� ����� ��������� �������������� ��� ������ (��������, �� ��������� �������)
			case 5:
				return createFewDebtAmountFields(true, isVisible);
			//9. ��������� ������ ���� (������) ��������������, �� ������ ������������ ����� ������
			// ���� �����.
			case 6:
				return createSplitDebtAmountFields(true, isVisible,  true);
			//10. ������������ ����� ��� �������� ��������� ���� (�� ����� �� ��������� ����������
			// ���������, � ��������������� ������� ����� ���������). �������� ������ �� ���������
			// �������. �������� ���������� ������������ (�� ���� ��� ������������ � ����� �����
			// ��������� ����� ��������). - ������������ �� ������� ��������.
			default:
				return null; //�� ����� ����
		}
	}

	/**
	 * �������� �������� ���� ����� �����
	 * @param editable ������� ��������������� �����
	 * @param visible ������� ����������� ����� �����
	 * @return ���� �����.
	 */
	private Requisite_Type createSimpleAmountField(boolean editable, boolean visible)
	{
		Requisite_Type result = new Requisite_Type();
		result.setNameVisible("����� ����� ���������");
		result.setNameBS("extend-amount-" + counter++);
		result.setDescription("�������� ���� ����� �����");
		result.setComment("������ � ���� ����� �����");
		result.setType(FieldDataType.money.name());
		result.setIsRequired(true);
		result.setIsSum(true);
		result.setIsKey(false);
		result.setIsEditable(editable);
		result.setIsVisible(visible);
		result.setIsForBill(true);
		result.setIncludeInSMS(true);
		result.setSaveInTemplate(false);
		result.setHideInConfirmation(false);
		if (!editable || new Random().nextInt(3) == 2)
		{
			result.setDefaultValue("12.45");
		}
		result.setEnteredData(new String[]{"1223.32"});
		return result;
	}

	/**
	 * �������� ����� ����� ��� ������ ������ 1 ������������� � ����������� ���������� ������
	 * @param multiselect ����������� ������� ��������� ���������
	 * @param visibleMainSum ������� ��������� ������� �����
	 * @return ������ �����
	 */
	private Requisite_Type[] createFewDebtAmountFields(boolean multiselect, boolean visibleMainSum)
	{
		Requisite_Type requisite = new Requisite_Type();
		requisite.setNameVisible("������ ��");
		requisite.setNameBS("extend-amount-debt-" + counter++);
		requisite.setDescription("�������� ���� ������ ��");
		requisite.setComment("������ � ���� ������ ��");
		if (multiselect)
		{
			requisite.setType(FieldDataType.set.name());
		}
		else
		{
			requisite.setType(FieldDataType.list.name());
		}
		requisite.setIsRequired(true);
		requisite.setIsSum(false);
		requisite.setIsKey(false);
		requisite.setIsEditable(true);
		requisite.setIsVisible(true);
		requisite.setIsForBill(true);
		requisite.setIncludeInSMS(true);
		requisite.setSaveInTemplate(false);
		requisite.setHideInConfirmation(false);
		requisite.setMenu(new String[]{"������ 2011 (2.44 ���.)", "������� 2011 (32.44 ���.)", "���� 2011 (312.44 ���.)"});
		return new Requisite_Type[]{requisite, createSimpleAmountField(false, visibleMainSum)};
	}

	/**
	 * ������ 1 �������������, �� � ��������� �� �������
	 * @param editableMainSum ������� ��������������� ������� �����
	 * @param visibleMainSum ������� ��������� ������� �����
	 * @param editableItem ������� ��������������� ������ �������������
	 * @return ������ �����
	 */
	private Requisite_Type[] createSplitDebtAmountFields(boolean editableMainSum, boolean visibleMainSum,  boolean editableItem)
	{
		Requisite_Type gasAmountRequisite = new Requisite_Type();
		String nameVisible = "������ �� ���";
		gasAmountRequisite.setNameVisible(nameVisible);
		gasAmountRequisite.setNameBS("extend-amount-debt-gas-" + counter++);
		gasAmountRequisite.setDescription("�������� ���� " + nameVisible);
		gasAmountRequisite.setComment("������ � ����" + nameVisible);
		gasAmountRequisite.setType(FieldDataType.money.name());
		gasAmountRequisite.setIsRequired(true);
		gasAmountRequisite.setIsSum(false);
		gasAmountRequisite.setIsKey(false);
		gasAmountRequisite.setIsEditable(editableItem);
		gasAmountRequisite.setIsVisible(true);
		gasAmountRequisite.setIsForBill(true);
		gasAmountRequisite.setIncludeInSMS(true);
		gasAmountRequisite.setSaveInTemplate(false);
		gasAmountRequisite.setHideInConfirmation(false);
		gasAmountRequisite.setDefaultValue("12.32");
		gasAmountRequisite.setEnteredData(new String[]{"212.32"});

		Requisite_Type electricAmountRequisite = new Requisite_Type();
		nameVisible = "������ �� �������������";
		electricAmountRequisite.setNameVisible(nameVisible);
		electricAmountRequisite.setNameBS("extend-amount-debt-electric-" + counter++);
		electricAmountRequisite.setDescription("�������� ���� " + nameVisible);
		electricAmountRequisite.setComment("������ � ����" + nameVisible);
		electricAmountRequisite.setType(FieldDataType.money.name());
		electricAmountRequisite.setIsRequired(true);
		electricAmountRequisite.setIsSum(false);
		electricAmountRequisite.setIsKey(false);
		electricAmountRequisite.setIsEditable(editableItem);
		electricAmountRequisite.setIsVisible(true);
		electricAmountRequisite.setIsForBill(true);
		electricAmountRequisite.setIncludeInSMS(true);
		electricAmountRequisite.setSaveInTemplate(false);
		electricAmountRequisite.setHideInConfirmation(false);
		electricAmountRequisite.setDefaultValue("112.30");
		electricAmountRequisite.setEnteredData(new String[]{"112.50"});

		return new Requisite_Type[]{gasAmountRequisite, electricAmountRequisite, createSimpleAmountField(editableMainSum, visibleMainSum)};
	}

	private Requisite_Type getFieldByNameBS(String nameBS, Requisite_Type[] requisites)
	{
		if (requisites == null)
		{
			return null;
		}
		for (Requisite_Type requisite : requisites)
		{
			if (nameBS.equals(requisite.getNameBS()))
			{
				return requisite;
			}
		}
		return null;
	}

	private Requisite_Type createCPFLCounterRequisite()
	{
		Requisite_Type result = new Requisite_Type();
		result.setNameVisible("������� ��������");
		result.setNameBS(CPFL_COUNTER_FIELD_NAME);
		result.setDescription("������ ���� ���������� ��� ����������� �������� ����");
		result.setComment("�������� ������� ���� ������������� ���������� �� ������� ��� ��������. ��� ��������� ������� 0 �������� ������");
		result.setType(FieldDataType.integer.name());
		result.setNumberPrecision(BigInteger.ZERO);
		result.setIsRequired(true);
		result.setIsSum(false);
		result.setIsKey(false);
		result.setIsEditable(true);
		result.setIsVisible(true);
		result.setIsForBill(false);
		result.setIncludeInSMS(false);
		result.setSaveInTemplate(false);
		result.setHideInConfirmation(false);
		result.setError("���������� ������� ������, ���� �� ������� 0 � ���� " + CPFL_COUNTER_FIELD_NAME);
		result.setDefaultValue("3");
		return result;
	}

	/**
	 * �������� ���������
	 * @param type - ��� ���������
	 * @return
	 */
	private Requisite_Type createRequisite(FieldDataType type)
	{
		if (type == FieldDataType.calendar)
		{
			// ���� �� �������� � ����� ���������. ��� ��������� �����
			type = FieldDataType.date;
		}
		if (type == FieldDataType.integer)
		{
			// ���� �� �������� � ����� integer.
			type = FieldDataType.number;
		}
		Requisite_Type result = new Requisite_Type();
		result.setNameVisible("���� " + type);
		result.setNameBS("extend-" + type + "-" + counter++);
		result.setDescription("�������� ���� " + type);
		result.setComment("������ � ���� " + type);
		result.setType(type.name());
		if (type == FieldDataType.number)
		{
			result.setNumberPrecision(BigInteger.valueOf(4));
		}
		result.setIsRequired(true);
		result.setIsSum(false);
		result.setIsKey(false);
		result.setIsEditable(true);
		result.setIsVisible(true);
		result.setIsForBill(true);
		result.setIncludeInSMS(true);
		result.setSaveInTemplate(true);
		result.setHideInConfirmation(false);
		result.setRequisiteTypes(createRandomRequisiteTypes());
		if (type == FieldDataType.set || type == FieldDataType.list)
		{
			String[] meny = {"�������� 1", "�������� 2", "�������� 3"};
			result.setMenu(meny);
			result.setEnteredData(new String[]{meny[new Random().nextInt(meny.length)]});
		}
		else if (type == FieldDataType.date)
		{
			result.setDefaultValue("13.12.2010");
			result.setEnteredData(new String[]{"13.12.2010"});
		}
		else
		{
			result.setDefaultValue("134");
		}
		return result;
	}

	/**
	 * ����� �������� ���� ��� ���� �� �, � ������ ���������� ������ �����, ��� ��� ���, ������������ ��
	 * @param recipient ��
	 * @return
	 */
	static RecipientRec_Type copyRecipientRec(RecipientRec_Type recipient)
	{
		RecipientRec_Type result = new RecipientRec_Type();
		BeanHelper.copyPropertiesFull(result, recipient);
		if (result.getAcctId() == null || result.getAcctId().equals(""))
		{
			result.setAcctId("12345678901234567890");
		}
		if (result.getTaxId() == null || result.getTaxId().equals(""))
		{
			result.setTaxId("123456789012");
		}
		if (result.getBIC() == null || result.getBIC().equals("")){
			result.setBIC("044525401");
		}
		return result;
	}

	/**
	 * ������������ ������ �� ������ ������ ���������� � ����������� ���������� �����(BillingPayInqRq)
	 * @param parameters
	 * @return
	 * @throws RemoteException
	 */
	public IFXRs_Type createBillingPayInqRs(IFXRq_Type parameters) throws RemoteException
	{
		BillingPayInqRq_Type request = parameters.getBillingPayInqRq();
		RecipientRec_Type recipientRec = request.getRecipientRec();
		RecipientRec_Type newRecipientRec = null;

		Random random = new Random();
		switch(random.nextInt(3))
		{
			// ����������� �� �������
			case 0:
			{
				newRecipientRec = copyRecipientRec(recipientRec);
				newRecipientRec.setRequisites(new Requisite_Type[]{
						createReceiverName(), // ������������
						createSimpleAmountField(true, true), // c����
						createGroundField(), // ���������� �������
						createEsbVariantRoute(NOT_FOUND_PROVIDER)});
				newRecipientRec.setNotVisibleBankDetails(random.nextBoolean());
				break;
			}

			// ������ ���� ���������
			case 1:
			{
				List<Requisite_Type> requisites = generateRequisites(request.getRecipientRec(), false);
				requisites.add(createEsbVariantRoute(FOUND_ONE_PROVIDER));
				newRecipientRec = createRecipientRec(recipientRec, requisites);
				createAutoPayBlock(request.getIsAutoPayment(), newRecipientRec);
				break;
			}

			// ������� ��������� �����������
			case 2:
			{
				newRecipientRec = copyRecipientRec(recipientRec);
				newRecipientRec.setRequisites(new Requisite_Type[]{createListKPP(), createEsbVariantRoute(FOUND_MANY_PROVIDERS)});
				newRecipientRec.setNotVisibleBankDetails(random.nextBoolean());
				newRecipientRec.setIsAutoPayAccessible(true);
			}
		}
		newRecipientRec.setBankInfo(new BankInfo_Type("000","111", "222", null, null));
		return createResponce(request, newRecipientRec);
	}

	/**
	 * �������� ������ ���
	 * @return �������������� �������� ����� ������ ���
	 */
	private Requisite_Type createListKPP()
	{
		Random random = new Random();
		int countProviders = random.nextInt(3)+2;
		String[] menu = new String[countProviders];

		for(int i = 0; i < countProviders; i++)
			menu[i] = RandomHelper.rand(9, RandomHelper.DIGITS);

		Requisite_Type requisite =  createRequisite(FieldDataType.list);
		requisite.setNameVisible("���");
		requisite.setEnteredData(null);
		requisite.setMenu(menu);
		requisite.setNameBS(ESB_LIST_KPP);
		return requisite;
	}

	/**
	 * �������� ���������� �� ����������
	 * @param recipientRec
	 * @param requisites ��������� ����������
	 * @return
	 */
	private RecipientRec_Type createRecipientRec(RecipientRec_Type recipientRec, List<Requisite_Type> requisites)
	{
		Random rand = new Random();
		RecipientRec_Type result = new RecipientRec_Type();
		// ����, ������� ��������� � ����������� ����������

		String digitAndChars = RandomHelper.DIGITS + RandomHelper.ENGLISH_LETTERS;
		result.setCodeRecipientBS(RandomHelper.rand(rand.nextInt(20), digitAndChars));
		result.setCodeService(RandomHelper.rand(rand.nextInt(50), digitAndChars));
		result.setName("��������� �" + counter);
		result.setNameService("������ �" + counter);
		result.setNameOnBill("��������� �" + counter + "(���)");
		result.setPhoneToClient(RandomHelper.rand(15, RandomHelper.DIGITS));
		result.setCorrAccount(RandomHelper.rand(20, RandomHelper.DIGITS));
		result.setKPP(RandomHelper.rand(9, RandomHelper.DIGITS));

		result.setNotVisibleBankDetails(rand.nextBoolean());
		result.setTaxId(recipientRec.getTaxId());
		result.setBIC(recipientRec.getBIC());
		result.setAcctId(recipientRec.getAcctId());

		result.setRequisites(requisites.toArray(new Requisite_Type[requisites.size()]));

		return result;
	}

	/**
	 * ������� ������� �� ����������� ����������� �������� �� ���������� �, ���� ���� ������� true, �� �������
	 * ��������� ��������.
	 * @param isAutoPayment ������� ����������
	 * @param result ����������� ���������� � ����������
	 */
	private void createAutoPayBlock(Boolean isAutoPayment, RecipientRec_Type result)
	{
		Random rand = new Random();
		int randInt = rand.nextInt(10);
		// ���� ������� �� ������ ��� �� false ��� �������� ���������� false
		if (isAutoPayment == null || !isAutoPayment || randInt == 0)
		{
			result.setIsAutoPayAccessible(false);
		}
		else
		{
			addAutoPayDetails(result);
			result.setIsAutoPayAccessible(true);
		}
	}

	/**
	 * ������������ ������ BillingPayInqRs �� ����� ����������
	 * @param request ������ BillingPayInqRq �� ����� ����������
	 * @param recipientRec  ���������� �� ����������
	 * @return �������������� �����
	 */
	private IFXRs_Type createResponce(BillingPayInqRq_Type request, RecipientRec_Type recipientRec)
	{
		BillingPayInqRs_Type responce = new BillingPayInqRs_Type();
		Status_Type status = new Status_Type();
		counter++;
		if (counter % 40 == 0)
		{
			status.setStatusCode(500);
			status.setStatusDesc("�������� �������� ���������� ������ 40 ������");
			status.setServerStatusDesc(SERVER_STATUS_DESC);
		}
		else
		{
			status.setStatusCode(0);
		}

		responce.setOperUID(request.getOperUID());
		responce.setRqTm(PaymentsRequestHelper.generateRqTm());
		responce.setRqUID(PaymentsRequestHelper.generateUUID());
		responce.setStatus(status);
		responce.setSystemId("new_system_id");

		responce.setRecipientRec(recipientRec);

		IFXRs_Type ifxRs =  new IFXRs_Type();
		ifxRs.setBillingPayInqRs(responce);
		return ifxRs;
	}

	/**
	 * ������������ ����, � ������� ���������� ������� ���������� ������� �� ������ ����������
	 * @param value ������� ��������(NOT_FOUND_PROVIDER, FOUND_ONE_PROVIDER ��� FOUND_MANY_PROVIDERS)
	 * @return �������������� ����
	 */
	private Requisite_Type createEsbVariantRoute(String value)
	{
		Requisite_Type result = new Requisite_Type();
		result.setNameVisible(ESB_VARIANT_ROUTE);
		result.setNameBS(ESB_VARIANT_ROUTE);
		result.setDescription("������ ���� ���������� ��� ����������� �������� � ����");
		result.setComment("����, � ������� ���������� ������� ���������� ������� �� ������ ����������");
		result.setType(FieldDataType.string.name());
		result.setIsRequired(false);
		result.setIsSum(false);
		result.setIsKey(false);
		result.setIsEditable(false);
		result.setIsVisible(false);
		result.setIsForBill(false);
		result.setIncludeInSMS(false);
		result.setSaveInTemplate(false);
		result.setHideInConfirmation(false);
		result.setDefaultValue(value);
		return result;
	}

	/**
	 * �������� ���� "���������� �������"
	 * @return
	 */
	private Requisite_Type createGroundField()
	{
		Requisite_Type result = new Requisite_Type();
		result.setNameVisible("���������� �������");
		result.setNameBS("ground");
		result.setDescription("���������� �������");
		result.setComment("�������, � ����� ����� �� ���������� ������. ��������, ����������������� �����.");
		result.setType(FieldDataType.string.name());
		result.setAttributeLength(new AttributeLength_Type(BigInteger.valueOf(100), BigInteger.ZERO));
		result.setIsRequired(true);
		result.setIsSum(false);
		result.setIsKey(false);
		result.setIsEditable(true);
		result.setIsVisible(true);
		result.setIsForBill(false);
		result.setIncludeInSMS(false);
		result.setSaveInTemplate(false);
		result.setHideInConfirmation(false);
		return result;
	}

	private Requisite_Type createReceiverName()
	{
		Requisite_Type result = createRequisite(FieldDataType.string);
		result.setNameVisible("������������ ����������");
		result.setDefaultValue(null);
		result.setNameBS(ESB_RECEIVER_NAME);
		return result;
	}

	private void addAutoPayDetails(RecipientRec_Type recipientRec)
	{
		AutopayDetails_Type autopayDetails = new AutopayDetails_Type();

		List<String> types = new ArrayList<String>();
		types.add("R");
		types.add("P");
		types.add("A");

		int n = new Random().nextInt(types.size());
		AutopayType_Type[] massTypes = new AutopayType_Type[n + 1];

		for(int i = 0; i < n + 1; i++)
		{
			int m = new Random().nextInt(types.size());
			AutopayType_Type type = AutopayType_Type.fromString(types.get(m));
			// ���� �������� �� ����� �������� ������
			if(AutopayType_Type._P.equals(type.toString()))
				autopayDetails.setLimit("100;200;500");

			massTypes[i] = type;
			types.remove(m);
		}

		autopayDetails.setAutoPayType(massTypes);
		recipientRec.setAutoPayDetails(autopayDetails);
	}

	/**
	 * �������� ������ ���� ����� ����� FieldDataType, ����� ���������� �����
	 * @return
	 */
	private List<FieldDataType> getFieldDataTypesExceptInternalType()
	{
		List<FieldDataType> fieldDataTypes  = new ArrayList<FieldDataType>();
		for (FieldDataType fieldDataType : FieldDataType.values())
		{
			if(fieldDataType != FieldDataType.link
					&& fieldDataType != FieldDataType.choice
					&& fieldDataType != FieldDataType.graphicset
					&& fieldDataType != FieldDataType.email)
				fieldDataTypes.add(fieldDataType);
		}
		return fieldDataTypes;
	}
}
