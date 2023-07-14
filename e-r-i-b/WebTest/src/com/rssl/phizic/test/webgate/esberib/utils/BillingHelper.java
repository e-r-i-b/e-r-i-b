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
 * Хелпер для формирования тестовых ответов для биллингов с карт
 */
public class BillingHelper extends BaseResponseHelper
{
	/**
	 * служебное поле: обратный счетчик запросов.
	 * используется для тестирования принципа ЦПФЛ.
	 * пока значение счетчика больше 0 в нем всегда возвращаем ошибку.
	 */
	private static final String CPFL_COUNTER_FIELD_NAME = "cpfl-counter";

	// поле в котором содерится информация о том найдены ли поставщики
	private static final String ESB_VARIANT_ROUTE = "esb-variant-route";
	// поле, содержащее список КПП найденных поставщиков по конкретным реквизитам
	private static final String ESB_LIST_KPP = "esb-list-kpp";
	private static final String ESB_RECEIVER_NAME = "esb-receiver-name";

	// значения которые может принимать CPFL_VARIANT_ROUTE
	private static final String NOT_FOUND_PROVIDER = "not-found-provider";
	private static final String FOUND_ONE_PROVIDER = "found-one-provider";
	private static final String FOUND_MANY_PROVIDERS = "found-many-providers";

	private static final int NEW_REQUISITES_COUNT = 3;//количество новых полей добавляемых при 1 запросе подготовки платежа.
	private static long counter = 0;

	/**
	 * Сформировать ответ на запрос TBP_PR(подготовка биллингового платежа)
	 * @param parameters оригинальный запрос
	 * @return ответ.
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
			status.setStatusDesc("Тестовая заглушка отказывает каждый 30 запрос");
			status.setServerStatusDesc(SERVER_STATUS_DESC);
		}
		else if (random.nextInt(10) == 1)
		{
			status.setStatusCode(-777);
			status.setStatusDesc("Случайно возвращаем ошибку -777");
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

		// вытаскиваем поле, которое указывает какое количество поставщиков найдено по реквизитам
		Requisite_Type esbVariantRouteField = getFieldByNameBS(ESB_VARIANT_ROUTE, oldRecipientRec.getRequisites());
		// если поля нет или оно пустое то формируем информацию для одного поставщика
		String value = esbVariantRouteField == null ?	FOUND_ONE_PROVIDER : esbVariantRouteField.getEnteredData()[0];

		// если не найдено ни одного поставщика
		if(NOT_FOUND_PROVIDER.equals(value))
		{
			newRecipientRec = copyRecipientRec(oldRecipientRec);
			Requisite_Type name = getFieldByNameBS(ESB_RECEIVER_NAME, oldRecipientRec.getRequisites());
			newRecipientRec.setName(name.getEnteredData()[0]);
			name.setIsVisible(false);
			name.setIsEditable(false);
		}
		// если найден один поставщик
		else if(FOUND_ONE_PROVIDER.equals(value))
		{
			List<Requisite_Type> newRequisites = generateRequisites(oldRecipientRec, BooleanUtils.isTrue(request.getIsAutoPayment()));
			newRecipientRec = copyRecipientRec(oldRecipientRec);
			// если совершается автоплатеж, добавляем информацию по доступным автоплатежам
			if(BooleanUtils.isTrue(request.getIsAutoPayment()))
				addAutoPayDetails(newRecipientRec);

			newRecipientRec.setBankInfo(new BankInfo_Type("000","111", "222", null, null));
			newRecipientRec.setNotVisibleBankDetails(random.nextBoolean());
			newRecipientRec.setPhoneToClient(RandomHelper.rand(15, RandomHelper.DIGITS));
			newRecipientRec.setNameOnBill(oldRecipientRec.getName());

			newRecipientRec.setRequisites(newRequisites.toArray(new Requisite_Type[newRequisites.size()]));

			createAutoPayBlock(request.getIsAutoPayment(), newRecipientRec);
		}
		// если найдено несколько поставщиков
		else if(FOUND_MANY_PROVIDERS.equals(value))
		{
			List<Requisite_Type> newRequisites = generateRequisites(oldRecipientRec, false);
			newRecipientRec = createRecipientRec(oldRecipientRec, newRequisites);
			newRecipientRec.setBankInfo(new BankInfo_Type("000","111", "222", null, null));
			// ищем поле списка КПП и устанавлием его как нередактируемое
			Requisite_Type listKpp = getFieldByNameBS(ESB_LIST_KPP, oldRecipientRec.getRequisites());
			listKpp.setIsEditable(false);
			// генерим поставщика и устанавлием ему имя и выбранные КПП (нашли именно того кто нам нужен)
			newRecipientRec.setKPP(listKpp.getEnteredData()[0]);
			newRecipientRec.setName("Поставщик с КПП:" + listKpp.getEnteredData()[0]);
			// теперь знаем точно че за поставщик
			esbVariantRouteField.setEnteredData(new String[]{FOUND_ONE_PROVIDER});

			createAutoPayBlock(request.getIsAutoPayment(), newRecipientRec);
		}

		for(Requisite_Type requisite : newRecipientRec.getRequisites())
		{
			// случайно расставляем ошибки в некоторые поля.
			counter++;
			if (counter % 20 == 5)
				requisite.setError("Какая-то ошибка к полю '" + requisite.getNameVisible() + "'");
		}

		if (status.getStatusCode() == -777 && random.nextBoolean())
		{
			// Рандомно добавляем поле
			List<Requisite_Type> requisites = new ArrayList<Requisite_Type>();
			requisites.addAll(Arrays.asList(newRecipientRec.getRequisites()));
			List<FieldDataType> fieldDataTypes = getFieldDataTypesExceptInternalType();
			requisites.add(createRequisite(fieldDataTypes.get(random.nextInt(fieldDataTypes.size()))));
			newRecipientRec.setRequisites(requisites.toArray(new Requisite_Type[requisites.size()]));
		}

		if (isRequisitesSufficient(oldRecipientRec.getRequisites(), newRecipientRec.getRequisites()))
		{
			//добавляем поля, сигнализирующие о конце прокручивания подготовки платежа.
			responce.setMadeOperationId("MadeOperationId");
			responce.setCommission(new BigDecimal("3.12"));
			responce.setCommissionCur("RUR");
			// для автоплатежа нужне признак withCommision, говорящий о том, будут ли сорвешаться платежи с кмисситей
			if(BooleanUtils.isTrue(request.getIsAutoPayment()))
				responce.setWithCommision(new Random().nextBoolean());
		}

		responce.setRecipientRec(newRecipientRec);
	}

	/**
	 * Проверяем достаточность полей для совершения операции:
	 * 1) состав полей одинаков.
	 * 1) в полях нет ошибок.
	 * @param oldRequisites предыдущие реквизиты
	 * @param newRequisites новые реквизиты.
	 * @return достаточно/недостаточно
	 */
	private boolean isRequisitesSufficient(Requisite_Type[] oldRequisites, Requisite_Type[] newRequisites)
	{
		if (oldRequisites == null && newRequisites == null)
		{
			//оба пусты реквизитов - реквизитов достаточно
			return true;
		}
		if (oldRequisites == null ^ newRequisites == null)
		{
			//ровно один пуст - реквизитов НЕдостаточно
			return false;
		}
		if (oldRequisites.length != newRequisites.length)
		{
			//отличается количество реквизитов
			return false;
		}
		//проверяем наличие ошибок
		for (Requisite_Type requisite : newRequisites)
		{
			if (!StringHelper.isEmpty(requisite.getError()))
			{
				return false;
			}
		}
		//ошибок нет: смотрим не добавились ли новые поля взамен старых.
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
				//какой-то из старых реквизитов отсутсвует -> добавился новый
				return false;
			}
		}
		return true;
	}

	/**
	 * Сформировать ответ на запрос TBP_PAY(проводка биллингового платежа)
	 * @param parameters оригинальный запрос
	 * @return ответ.
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
			status.setStatusDesc("Тестовая заглушка отказывает каждый 10 запрос");
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
	 * Сформировать ответ на запрос TBP_CAN(отмена биллингового платежа)
	 * @param parameters оригинальный запрос
	 * @return ответ.
	 * @throws RemoteException
	 */
	public IFXRs_Type createBillingPayCanRs(IFXRq_Type parameters)
	{
		BillingPayCanRq_Type request = parameters.getBillingPayCanRq();
		BillingPayCanRs_Type responce = new BillingPayCanRs_Type();

		Status_Type status = new Status_Type();
		status.setStatusCode(0);
//		status.setStatusDesc("Тестовая заглушка отказывает каждый 10 запрос");
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
	 * Генерация реквизитов
	 * @param recipient RecipientRec_Type
	 * @param isAutoPayment флажок, указывающий ялвяется ли платеж заявкой на автоподписку
	 * @return список реквизитов
	 */
	private List<Requisite_Type> generateRequisites(RecipientRec_Type recipient, boolean isAutoPayment)
	{
		Random random = new Random();
		Requisite_Type[] oldReqiusites = recipient.getRequisites();
		int requisitesSize = (oldReqiusites == null) ? 0 : oldReqiusites.length;
		//получаем счетчик запросов на подготовку платежа.
		Requisite_Type cpflField = getFieldByNameBS(CPFL_COUNTER_FIELD_NAME, oldReqiusites);
		List<FieldDataType> fieldDataTypes  = getFieldDataTypesExceptInternalType();
		if (cpflField == null)
		{
			//Создаем обязательные поле(я) сумм.
			Requisite_Type[] amountFields = createAmoutFields(!isAutoPayment);
			int ammountFieldsLength = amountFields.length;
			//счетчика запросов не было -> это первый запрос ->генерим новые поля
			List<Requisite_Type> newReqiusites = new ArrayList<Requisite_Type>(requisitesSize + NEW_REQUISITES_COUNT + ammountFieldsLength + 1);//+1 - это поле счетчик ЦПФЛ
			int i = 0;
			//копируем старые поля
			if (oldReqiusites != null)
			{
				for (Requisite_Type requisite : oldReqiusites)
				{
					newReqiusites.add(requisite);
					i++;
				}
			}
			//Рандомом добавляем дополнительный атрибут рискованности ПУ
			boolean isRiskRecipient = random.nextBoolean();
			if (isRiskRecipient == Boolean.TRUE)
				newReqiusites.add(createRiskRecipientRequisite());

			//добавляем новые.
			for (int j = 0; j < NEW_REQUISITES_COUNT; i++, j++)
			{
				//выбираем рандомом тип.
				FieldDataType type = fieldDataTypes.get(random.nextInt(fieldDataTypes.size()));
				newReqiusites.add(createRequisite(type));
			}
			//добавляем поле-счетчик
			newReqiusites.add(createCPFLCounterRequisite());
			newReqiusites.addAll(Arrays.asList(amountFields));
			return newReqiusites;
		}
		//поле со счетчиком ЦПФЛ есть
		for (Requisite_Type requisite : oldReqiusites)
		{
			//случайно меняем значения атрибутов(пересчитали на сервере)
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
						value = "измененное биллингом значение";
						break;
					case money:
						value = "12.31";
						break;
				}
				requisite.setEnteredData(new String[]{value});
			}
		}
		//обрабатываем поле счетчик ЦПФЛ.
		String[] enteredData = cpflField.getEnteredData();
		if (enteredData == null || enteredData.length != 1)
		{
			cpflField.setError("Для поля " + CPFL_COUNTER_FIELD_NAME + "должно быть задано ровно 1 значение");
			return Arrays.asList(oldReqiusites);
		}
		String value = enteredData[0];
		if (StringHelper.isEmpty(value))
		{
			cpflField.setError("Не задано значение для поля " + CPFL_COUNTER_FIELD_NAME);
		}
		try
		{
			int intValue = Integer.parseInt(value);
			if (intValue-- > 0)
			{
				cpflField.setError("Продолжаем крутить платеж, пока не получим 0 в поле " + CPFL_COUNTER_FIELD_NAME);
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
			cpflField.setError("Значение для поля " + CPFL_COUNTER_FIELD_NAME + " должно быть натуральным числом ");
			return Arrays.asList(oldReqiusites);
		}
	}

	/**
	 * создание реквизита рискованности ПУ
	 * @return
	 */
	private Requisite_Type createRiskRecipientRequisite()
	{
		Requisite_Type result = new Requisite_Type();
		result.setNameVisible("Признак рискованности ПУ");
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
	 * создание полей суммы ввода/отображаения суммы
	 * @param isVisible является ли гнавная сумма видимой
	 * @return список полей сумм.
	 */
	private Requisite_Type[] createAmoutFields(boolean isVisible)
	{

		int random = new Random().nextInt(7);
		switch (random)
		{
			//3. Платеж с запросом задолженности (и, быть может быть доп. реквизитов у пользователя),
			//задолженность представлена одной суммой, пользователь МОЖЕТ изменить сумму.
			case 0:
				return new Requisite_Type[]{createSimpleAmountField(true, isVisible)};
			//4. То же, что предыдущий, но пользователь НЕ может изменить сумму. 
			case 1:
				return new Requisite_Type[]{createSimpleAmountField(false, isVisible)};
			//5. Одна общая сумма задолженности с разбивкой по статьям,
			// пользователь не может изменять никакие суммы (только согласиться или не согласиться).
			// Например, единый платеж за ЖКХ с указанием отдельных сумм за свет, газ, воду.
			case 2:
				return createSplitDebtAmountFields(false, isVisible, false);
			//6. Одна общая сумма задолженности с разбивкой по статьям, пользователь
			// может изменить только общую сумму платежа, после чего суммы по статьям будут
			// перерасчитаны системой и представлены пользователю лишь для подтверждения
			// (изменить их пользователь сам не может).
			case 3:
				return createSplitDebtAmountFields(true, isVisible, false);
			//7. Несколько разных сумм задолженностей, пользователь не может менять сумму,
			// но может выбрать одну из них (пример - оплата со страховкой или без страховки)
			case 4:
				return createFewDebtAmountFields(false, isVisible);
			//8. Несколько разных сумм задолженностей, пользователь не может менять сумму,
			// но может выбрать сразу несколько задолженностей для оплаты (например, за несколько месяцев)
			case 5:
				return createFewDebtAmountFields(true, isVisible);
			//9. Несколько разных сумм (статей) задолженностей, по каждой пользователь может ввести
			// свою сумму.
			case 6:
				return createSplitDebtAmountFields(true, isVisible,  true);
			//10. пользователь может сам задавать несколько сумм (не выбор из множества допустимых
			// вариантов, а самостоятельное задание этого множества). Например оплата за несколько
			// месяцев. Перечень определяет пользователь (то есть сам пользователь в СБОЛе может
			// добавлять новые значения). - НЕРЕАЛИЗУЕМО ПО ТЕКУЩИМ ФОРМАТАМ.
			default:
				return null; //не может быть
		}
	}

	/**
	 * Создание простого поля общая сумма
	 * @param editable признак редактируемости суммы
	 * @param visible признак отображения суммы суммы
	 * @return поле сумма.
	 */
	private Requisite_Type createSimpleAmountField(boolean editable, boolean visible)
	{
		Requisite_Type result = new Requisite_Type();
		result.setNameVisible("Общая сумма документа");
		result.setNameBS("extend-amount-" + counter++);
		result.setDescription("Описание поля общей суммы");
		result.setComment("Комент к полю общей суммы");
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
	 * создание полей суммы для случая оплаты 1 задолженности с несколькими вариантами оплаты
	 * @param multiselect возможность выбрать несколько вариантов
	 * @param visibleMainSum признак видимости главной суммы
	 * @return список полей
	 */
	private Requisite_Type[] createFewDebtAmountFields(boolean multiselect, boolean visibleMainSum)
	{
		Requisite_Type requisite = new Requisite_Type();
		requisite.setNameVisible("Оплата за");
		requisite.setNameBS("extend-amount-debt-" + counter++);
		requisite.setDescription("Описание поля Оплата за");
		requisite.setComment("Комент к полю Оплата за");
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
		requisite.setMenu(new String[]{"январь 2011 (2.44 руб.)", "февраль 2011 (32.44 руб.)", "март 2011 (312.44 руб.)"});
		return new Requisite_Type[]{requisite, createSimpleAmountField(false, visibleMainSum)};
	}

	/**
	 * оплата 1 задолженности, но с разбивкой по статьям
	 * @param editableMainSum признак редактируемости главной суммы
	 * @param visibleMainSum признак видимости главной суммы
	 * @param editableItem признак редактируемости статей задолженности
	 * @return список полей
	 */
	private Requisite_Type[] createSplitDebtAmountFields(boolean editableMainSum, boolean visibleMainSum,  boolean editableItem)
	{
		Requisite_Type gasAmountRequisite = new Requisite_Type();
		String nameVisible = "Оплата за газ";
		gasAmountRequisite.setNameVisible(nameVisible);
		gasAmountRequisite.setNameBS("extend-amount-debt-gas-" + counter++);
		gasAmountRequisite.setDescription("Описание поля " + nameVisible);
		gasAmountRequisite.setComment("Комент к полю" + nameVisible);
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
		nameVisible = "Оплата за электричество";
		electricAmountRequisite.setNameVisible(nameVisible);
		electricAmountRequisite.setNameBS("extend-amount-debt-electric-" + counter++);
		electricAmountRequisite.setDescription("Описание поля " + nameVisible);
		electricAmountRequisite.setComment("Комент к полю" + nameVisible);
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
		result.setNameVisible("счетчик итераций");
		result.setNameBS(CPFL_COUNTER_FIELD_NAME);
		result.setDescription("Данное поле необходимо для тестировани принципа ЦПФЛ");
		result.setComment("Значение данного поля автоматически умеьшается на единицу при запросах. при значениях больших 0 выдается ошибка");
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
		result.setError("Продолжаем крутить платеж, пока не получим 0 в поле " + CPFL_COUNTER_FIELD_NAME);
		result.setDefaultValue("3");
		return result;
	}

	/**
	 * Создание реквизита
	 * @param type - тип реквизита
	 * @return
	 */
	private Requisite_Type createRequisite(FieldDataType type)
	{
		if (type == FieldDataType.calendar)
		{
			// шина не работает с типом календарь. это городская фишка
			type = FieldDataType.date;
		}
		if (type == FieldDataType.integer)
		{
			// шина не работает с типом integer.
			type = FieldDataType.number;
		}
		Requisite_Type result = new Requisite_Type();
		result.setNameVisible("поле " + type);
		result.setNameBS("extend-" + type + "-" + counter++);
		result.setDescription("Описание поля " + type);
		result.setComment("Комент к полю " + type);
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
			String[] meny = {"значение 1", "значение 2", "значение 3"};
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
	 * Метод копирует поля все поля ПУ и, в случае отсутствия номера счёта, ИНН или БИК, дозаписывает их
	 * @param recipient ПУ
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
	 * Формирование ответа на запрос поиска информации о биллинговом поставщике услуг(BillingPayInqRq)
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
			// поставщиков не найдено
			case 0:
			{
				newRecipientRec = copyRecipientRec(recipientRec);
				newRecipientRec.setRequisites(new Requisite_Type[]{
						createReceiverName(), // наименование
						createSimpleAmountField(true, true), // cумма
						createGroundField(), // назначение платежа
						createEsbVariantRoute(NOT_FOUND_PROVIDER)});
				newRecipientRec.setNotVisibleBankDetails(random.nextBoolean());
				break;
			}

			// найден один поставщик
			case 1:
			{
				List<Requisite_Type> requisites = generateRequisites(request.getRecipientRec(), false);
				requisites.add(createEsbVariantRoute(FOUND_ONE_PROVIDER));
				newRecipientRec = createRecipientRec(recipientRec, requisites);
				createAutoPayBlock(request.getIsAutoPayment(), newRecipientRec);
				break;
			}

			// найдено несколько поставщиков
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
	 * Создание списка КПП
	 * @return дополнительный реквизит ввиде списка КПП
	 */
	private Requisite_Type createListKPP()
	{
		Random random = new Random();
		int countProviders = random.nextInt(3)+2;
		String[] menu = new String[countProviders];

		for(int i = 0; i < countProviders; i++)
			menu[i] = RandomHelper.rand(9, RandomHelper.DIGITS);

		Requisite_Type requisite =  createRequisite(FieldDataType.list);
		requisite.setNameVisible("КПП");
		requisite.setEnteredData(null);
		requisite.setMenu(menu);
		requisite.setNameBS(ESB_LIST_KPP);
		return requisite;
	}

	/**
	 * Создание Информации по поставщику
	 * @param recipientRec
	 * @param requisites реквизиты поставщика
	 * @return
	 */
	private RecipientRec_Type createRecipientRec(RecipientRec_Type recipientRec, List<Requisite_Type> requisites)
	{
		Random rand = new Random();
		RecipientRec_Type result = new RecipientRec_Type();
		// поля, которые относятся к конкретному поставщику

		String digitAndChars = RandomHelper.DIGITS + RandomHelper.ENGLISH_LETTERS;
		result.setCodeRecipientBS(RandomHelper.rand(rand.nextInt(20), digitAndChars));
		result.setCodeService(RandomHelper.rand(rand.nextInt(50), digitAndChars));
		result.setName("Поставщик №" + counter);
		result.setNameService("Услуга №" + counter);
		result.setNameOnBill("Поставщик №" + counter + "(чек)");
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
	 * Создать признак на возможность регистрации подписки на автоплатеж и, если этот признак true, то создать
	 * параметры подписки.
	 * @param isAutoPayment признак автплатежа
	 * @param result построенная информация о поставщике
	 */
	private void createAutoPayBlock(Boolean isAutoPayment, RecipientRec_Type result)
	{
		Random rand = new Random();
		int randInt = rand.nextInt(10);
		// если признак не пришел или он false или рандомно возвращаем false
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
	 * Формирование ответа BillingPayInqRs на поиск поставщика
	 * @param request запрос BillingPayInqRq на поиск поставщика
	 * @param recipientRec  информация по поставщику
	 * @return сформированный ответ
	 */
	private IFXRs_Type createResponce(BillingPayInqRq_Type request, RecipientRec_Type recipientRec)
	{
		BillingPayInqRs_Type responce = new BillingPayInqRs_Type();
		Status_Type status = new Status_Type();
		counter++;
		if (counter % 40 == 0)
		{
			status.setStatusCode(500);
			status.setStatusDesc("Тестовая заглушка отказывает каждый 40 запрос");
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
	 * Формирование поля, в котором содержится сколько постащиков найдено по данным реквизитам
	 * @param value строкое значение(NOT_FOUND_PROVIDER, FOUND_ONE_PROVIDER или FOUND_MANY_PROVIDERS)
	 * @return Сформированное поле
	 */
	private Requisite_Type createEsbVariantRoute(String value)
	{
		Requisite_Type result = new Requisite_Type();
		result.setNameVisible(ESB_VARIANT_ROUTE);
		result.setNameBS(ESB_VARIANT_ROUTE);
		result.setDescription("Данное поле необходимо для тестировани билингов с карт");
		result.setComment("Поле, в котором содержится сколько постащиков найдено по данным реквизитам");
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
	 * Создание поля "Назначение платежа"
	 * @return
	 */
	private Requisite_Type createGroundField()
	{
		Requisite_Type result = new Requisite_Type();
		result.setNameVisible("Назначение платежа");
		result.setNameBS("ground");
		result.setDescription("Назначение платежа");
		result.setComment("Укажите, с какой целью Вы переводите деньги. Например, благотворительный взнос.");
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
		result.setNameVisible("Наименование получателя");
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
			// если пороовый то нужно добавить пороги
			if(AutopayType_Type._P.equals(type.toString()))
				autopayDetails.setLimit("100;200;500");

			massTypes[i] = type;
			types.remove(m);
		}

		autopayDetails.setAutoPayType(massTypes);
		recipientRec.setAutoPayDetails(autopayDetails);
	}

	/**
	 * Получить список всех типов полей FieldDataType, кроме внутренних типов
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
