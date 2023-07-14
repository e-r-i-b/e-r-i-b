package com.rssl.phizic.test.webgate.esberib.utils;

import com.rssl.phizgate.common.payments.BillingPaymentHelper;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.gate.payments.systems.recipients.FieldDataType;
import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/**
 * @author Erkin
 * @ created 25.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ’елпер дл€ обработки запросов по технологии ”Ё 
 */
class UECPaymentHelper
{
	private static final Pattern UEC_REQUISITE_NAME_PATTERN = Pattern.compile("UECAttr\\d+");
	private static final Pattern RANDOM_FILED_PATTERN = Pattern.compile("RandomField\\d+");

	private static final BigDecimal COMMISSION = new BigDecimal("9.11");
	private static final String RANDOM_SUM = "RandomSum";
	private static final BigDecimal MAGIC_NUM = BigDecimal.valueOf(500);

	///////////////////////////////////////////////////////////////////////////

	static void preparePayment(BillingPayPrepRq_Type request, BillingPayPrepRs_Type responce)
	{
		responce.setRqUID(PaymentsRequestHelper.generateUUID());
		responce.setRqTm(PaymentsRequestHelper.generateRqTm());
		responce.setOperUID(request.getOperUID());
		responce.setSystemId(request.getSystemId());

		responce.setStatus(new Status_Type(0, null, null, null));

		boolean isCommision = testSum(responce,request.getRecipientRec().getRequisites());

		responce.setRecipientRec(preparePymentRecipientRec(request.getRecipientRec(),isCommision));
	}

	private static boolean testSum(BillingPayPrepRs_Type responce,Requisite_Type[] requesites)
	{
		String randSumStr = "";
		for (Requisite_Type requesite : requesites)
		{

			String reqName = requesite.getNameBS();
			String[] reqData = requesite.getEnteredData();
			if (reqName.equals(RANDOM_SUM))
				randSumStr = reqData[0];
		}

		BigDecimal randSum = StringHelper.isEmpty(randSumStr) ? null : new BigDecimal(randSumStr);
		if (randSum != null)
		{
			int randSumCompare = randSum.compareTo(MAGIC_NUM);
			if ((randSumCompare == 1) || (randSumCompare == 0))
			{
				setCommision(responce);
				return true;
			}
		}
		return false;
	}

	private static void setCommision(BillingPayPrepRs_Type responce)
	{

		responce.setMadeOperationId("MadeOperationId");
		responce.setCommission(COMMISSION);
		responce.setCommissionCur("RUR");
	}

	private static RecipientRec_Type preparePymentRecipientRec(RecipientRec_Type oldRecipientRec,boolean isCommision)
	{
		List<Requisite_Type> requisites = generateRequisites(oldRecipientRec,isCommision);
		Requisite_Type recipientAccount = removeRequisite(requisites, "Payee.PersonalAcc");
		Requisite_Type recipientName = removeRequisite(requisites, "Payee.Name");
		Requisite_Type recipientBIC = removeRequisite(requisites, "Payee.Bank.BIC");
		Requisite_Type recipientINN = removeRequisite(requisites, "Payee.INN");

		RecipientRec_Type newRecipientRec = BillingHelper.copyRecipientRec(oldRecipientRec);

		newRecipientRec.setBankInfo(new BankInfo_Type("000","111", "222", null, null));
		newRecipientRec.setNotVisibleBankDetails(false);
		newRecipientRec.setPhoneToClient(RandomHelper.rand(15, RandomHelper.DIGITS));
		newRecipientRec.setNameOnBill(oldRecipientRec.getName());
		if (recipientAccount != null)
			newRecipientRec.setAcctId(getRequisiteFirstDateItem(recipientAccount));
		if (recipientName != null) {
			newRecipientRec.setName(getRequisiteFirstDateItem(recipientName));
			newRecipientRec.setNameOnBill(getRequisiteFirstDateItem(recipientName));
		}
		if (recipientBIC != null)
			newRecipientRec.setBIC(getRequisiteFirstDateItem(recipientBIC));
		if (recipientINN != null)
			newRecipientRec.setTaxId(getRequisiteFirstDateItem(recipientINN));

		newRecipientRec.setRequisites(requisites.toArray(new Requisite_Type[requisites.size()]));
		return newRecipientRec;
	}

	private static List<Requisite_Type> generateRequisites(RecipientRec_Type oldRecipientRec,boolean isCommision)
	{
		Requisite_Type[] oldRequisites = oldRecipientRec.getRequisites();
		List<Requisite_Type> newRequisites = new ArrayList<Requisite_Type>();

		String randSum = "0";
		for (Requisite_Type oldRequisite : oldRequisites)
		{
			String recName = oldRequisite.getNameBS();
			if (recName.equals(RANDOM_SUM))
				randSum = oldRequisite.getEnteredData()[0];
			else if (RANDOM_FILED_PATTERN.matcher(recName).matches())
			{
				newRequisites.add(oldRequisite);
			}

			Requisite_Type newRequisite = generateRequisite(oldRequisite);
			if (newRequisite != null)
				newRequisites.add(newRequisite);
		}

		if (!isCommision)
		{

			Random rand = new Random();
			int j = rand.nextInt(5);
			for (int i = 0; j > i ; i++)
			{
				Requisite_Type test = createStringRequisite("RandomField"+i, "data");
				test.setIsEditable(true);
				newRequisites.add(test);
			}
		}

		Requisite_Type addSum = createAmountRequisite("RandomSum", new BigDecimal(randSum), "¬ведите " + MAGIC_NUM + " или больше");
		newRequisites.add(addSum);

		return newRequisites;
	}

	private static Requisite_Type generateRequisite(Requisite_Type oldRequisite)
	{
		Pair<String, String> nameAndValue = getUECRequisiteNameAndValue(oldRequisite);
		if (nameAndValue == null)
			return null;
		String name = nameAndValue.getFirst();
		String value = nameAndValue.getSecond();

		if (name.equals("Sum"))
			return createMainAmountRequisite(value);

		return createStringRequisite(name, value);
	}

	private static Pair<String, String> getUECRequisiteNameAndValue(Requisite_Type requisite)
	{
		if (!UEC_REQUISITE_NAME_PATTERN.matcher(requisite.getNameBS()).matches())
			return null;

		String dateItem = getRequisiteFirstDateItem(requisite);
		if (StringHelper.isEmpty(dateItem))
			return null;

		String nameValueRec[] = StringUtils.split(dateItem, '=');
		if (nameValueRec.length < 2)
			return null;

		String name = nameValueRec[0];
		String value = StringUtils.join(nameValueRec, '=', 1, nameValueRec.length);
		return new Pair<String, String>(name, value);
	}

	private static Requisite_Type createMainAmountRequisite(String amountCopeckAsString)
	{
		BigDecimal amount = new BigDecimal(amountCopeckAsString);
		amount = amount.divide(new BigDecimal(100));
		amount = amount.add(COMMISSION);

		Requisite_Type requisite = new Requisite_Type();
		requisite.setNameVisible("—умма");
		requisite.setNameBS(BillingPaymentHelper.DEFAULT_AMOUNT_FIELD_ID);
		requisite.setType(FieldDataType.money.name());
		requisite.setIsRequired(true);
		requisite.setIsSum(true);
		requisite.setIsKey(false);
		requisite.setIsEditable(false);
		requisite.setIsVisible(true);
		requisite.setIsForBill(true);
		requisite.setIncludeInSMS(true);
		requisite.setSaveInTemplate(false);
		requisite.setHideInConfirmation(false);
		requisite.setEnteredData(new String[]{amount.toString()});
		return requisite;
	}

	private static Requisite_Type createAmountRequisite(String fieldName, BigDecimal amount, String comment)
	{
		Requisite_Type requisite = new Requisite_Type();
		requisite.setNameVisible(fieldName);
		requisite.setNameBS(fieldName);
		requisite.setComment(comment);
		requisite.setType(FieldDataType.money.name());
		requisite.setIsRequired(true);
		requisite.setIsSum(false);
		requisite.setIsKey(false);
		requisite.setIsEditable(true);
		requisite.setIsVisible(true);
		requisite.setIsForBill(true);
		requisite.setIncludeInSMS(true);
		requisite.setSaveInTemplate(false);
		requisite.setHideInConfirmation(false);
		requisite.setEnteredData(new String[]{amount.toString()});
		return requisite;
	}

	private static Requisite_Type createStringRequisite(String name, String value)
	{
		Requisite_Type requisite = new Requisite_Type();
		requisite.setNameVisible(name);
		requisite.setNameBS(name);
		requisite.setType(FieldDataType.string.name());
		requisite.setIsVisible(true);
		requisite.setEnteredData(new String[]{value});
		return requisite;
	}

	private static String getRequisiteFirstDateItem(Requisite_Type requisite)
	{
		String[] enteredData = requisite.getEnteredData();
		if (ArrayUtils.isEmpty(enteredData))
			return null;
		return enteredData[0];
	}

	private static Requisite_Type removeRequisite(List<Requisite_Type> requisites, String name)
	{
		Iterator<Requisite_Type> it = requisites.iterator();
		while (it.hasNext())
		{
			Requisite_Type requisite = it.next();
			if (name.equals(requisite.getNameBS()))
			{
				it.remove();
				return requisite;
			}
		}
		return null;
	}
}
