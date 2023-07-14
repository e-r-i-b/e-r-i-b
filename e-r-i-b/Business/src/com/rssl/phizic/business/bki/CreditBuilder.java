package com.rssl.phizic.business.bki;

import com.rssl.phizgate.common.credit.bki.dictionary.BkiAddressType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.bki.generated.EnquiryResponseERIB;
import org.apache.commons.collections.CollectionUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Gulov
 * @ created 09.10.14
 * @ $Author$
 * @ $Revision$
 */
class CreditBuilder
{
	CreditProduct build(EnquiryResponseERIB.Consumers.S.CAIS record, int i) throws BusinessException
	{
		CreditProduct product;
		if (StringHelper.isEmpty(record.getCreditLimit()))
			product = new Credit(i);
		else
			product = new Card(i);
		product.setOpenDate(CreditBuilderFormatter.formatDate(record.getOpenDate()));
		product.setAmount(CreditBuilderFormatter.formatAmount(record.getAmountOfFinance(), record.getCurrency()));
		product.setBankName(CreditBuilderFormatter.formatBankName(record.getSubscriberName(), ""));
		product.setName(CreditBuilderFormatter.formatCreditName(record.getFinanceType(), record.getPurposeOfFinance()));
		product.setDuration(CreditBuilderFormatter.formatDuration(record.getDuration(), record.getDurationUnit()));
		product.setBalance(CreditBuilderFormatter.formatAmount(record.getOutstandingBalance(), record.getCurrency()));
		EnquiryResponseERIB.Consumers.S.CAIS.MontlyHistory lastMonthData = getLastMonthData(record.getMontlyHistories());
		String monthRequest;
		Money installment;
		Money arrearsBalance;
		Money creditLimit;
		if (lastMonthData == null)
		{
			monthRequest = "";
			installment = CreditBuilderFormatter.formatAmount(null, "");
			arrearsBalance = CreditBuilderFormatter.formatAmount(null, "");
			creditLimit = CreditBuilderFormatter.formatAmount(null, "");
		}
		else
		{
			monthRequest = CreditBuilderFormatter.getMonthRequestString(lastMonthData.getHistoryDate());
			installment = CreditBuilderFormatter.formatAmount(lastMonthData.getInstalment(), record.getCurrency());
			arrearsBalance = CreditBuilderFormatter.formatAmount(lastMonthData.getArrearsBalance(), record.getCurrency());
			creditLimit = CreditBuilderFormatter.formatAmount(lastMonthData.getCreditLimit(), record.getCurrency());
		}
		product.setMonthRequest(monthRequest);
		product.setInstalment(installment);
		product.setArrears(arrearsBalance);
		if (product instanceof Card)
			((Card) product).setCreditLimit(creditLimit);
		if (StringHelper.isNotEmpty(record.getAccountClosedDate())) // закрытый кредит (карта)
		{
			product.setClosedDate(CreditBuilderFormatter.formatDate(record.getAccountClosedDate()));
			product.setReasonForClosure(CreditBuilderFormatter.formatReasonForClosure(record.getReasonForClosure(), ""));
		}

		return product;
	}

	/**
	 * сформировать запрос кредитной истории из записи
	 * @param record - запись из сервиса
	 * @return
	 * @throws BusinessException
	 */
	CreditHistoryRequest buildCreditHistoryRequest(EnquiryResponseERIB.Consumers.S.CAPS record) throws BusinessException
	{
		CreditHistoryRequest request = new CreditHistoryRequest();
		//Дата запроса
		request.setDateRequest(CreditBuilderFormatter.formatDate(record.getEnquiryDate()));
		//причина запроса в БКИ
		request.setReasonForEnquiry(CreditBuilderFormatter.formatReasonForEnquiry(record.getReason()));
		//наименование кредита
		request.setCreditName(CreditBuilderFormatter.formatCreditName(record.getFinanceType(), record.getPurposeOfFinance()));
		//сумма кредита
		if (StringHelper.isNotEmpty(record.getAmountOfFinance()))
			request.setCreditLimit(CreditBuilderFormatter.formatAmount(record.getAmountOfFinance(), record.getCurrency()));
		else
			request.setCreditLimit(CreditBuilderFormatter.formatAmount(record.getCreditLimit(), record.getCurrency()));
		//Наименование банка
		request.setSubscriberName(CreditBuilderFormatter.formatBankName(record.getSubscriberName(), ""));


		String applicationDate = record.getApplicationDate();

		if (StringHelper.isNotEmpty(applicationDate))
			request.setApplicationDate(CreditReportHelper.getApplicationDate(applicationDate));

		request.setFinanceType(CreditBuilderFormatter.formatFinanceType(record.getFinanceType(), ""));
		if (StringHelper.isNotEmpty(record.getDuration()))
			request.setDurationOfAgreement(CreditBuilderFormatter.formatDuration(Long.parseLong(record.getDuration()), record.getDurationUnits()));

		return request;
	}

	CreditContract buildCreditContract(EnquiryResponseERIB.Consumers.S.CAIS record) throws BusinessException
	{
		CreditContract creditContract = new CreditContract();
		creditContract.setBankName(CreditBuilderFormatter.formatBankName(record.getSubscriberName(), ""));
		creditContract.setFinanceType(CreditBuilderFormatter.formatFinanceType(record.getFinanceType(), ""));
		creditContract.setPurposeOfFinance(CreditBuilderFormatter.formatPurposeOfFinance(record.getPurposeOfFinance()));
		creditContract.setTypeOfSecurity(CreditBuilderFormatter.formatTypeOfSecurity(record.getTypeOfSecurity(), ""));
		creditContract.setDuration(CreditBuilderFormatter.formatDuration(record.getDuration(), record.getDurationUnit()));
		creditContract.setReasonForClosure(CreditBuilderFormatter.formatReasonForClosure(record.getReasonForClosure(), ""));

		creditContract.setComment(record.getSubscriberComments());
		creditContract.setAmount(CreditBuilderFormatter.formatAmount(record.getAmountOfFinance(), record.getCurrency()));
		creditContract.setCreditLimit(CreditBuilderFormatter.formatAmount(record.getCreditLimit(), record.getCurrency()));
		EnquiryResponseERIB.Consumers.S.CAIS.MontlyHistory lastMonthData = getLastMonthData(record.getMontlyHistories());
		Money installment;
		Money arrearsBalance;
		if (lastMonthData == null)
		{
			installment = CreditBuilderFormatter.formatAmount(null, "");
			arrearsBalance = CreditBuilderFormatter.formatAmount(null, "");
		}
		else
		{
			installment = CreditBuilderFormatter.formatAmount(lastMonthData.getInstalment(), record.getCurrency());
			arrearsBalance = CreditBuilderFormatter.formatAmount(lastMonthData.getArrearsBalance(), record.getCurrency());
		}
		creditContract.setInstalment(installment);
		creditContract.setBalance(CreditBuilderFormatter.formatAmount(record.getOutstandingBalance(), record.getCurrency()));
		creditContract.setArrearsBalance(arrearsBalance);
		creditContract.setOpenDate(CreditBuilderFormatter.formatDate(record.getOpenDate()));
		creditContract.setLastPaymentDate(CreditBuilderFormatter.formatDate(record.getLastPaymentDate()));
		creditContract.setLastMissedPaymentDate(CreditBuilderFormatter.formatDate(record.getLastMissedPaymentDate()));
		creditContract.setFulfilmentDate(CreditBuilderFormatter.formatDate(record.getFulfilmentDueDate()));
		creditContract.setClosedDate(CreditBuilderFormatter.formatDate(record.getAccountClosedDate()));
		creditContract.setLitigationDate(CreditBuilderFormatter.formatDate(record.getLitigationDate()));
		creditContract.setWriteOffDate(CreditBuilderFormatter.formatDate(record.getWriteOffDate()));
		creditContract.setSubscriberName(creditContract.getBankName());

		EnquiryResponseERIB.Consumers.S.CAIS.Consumer consumer = record.getConsumers().get(0);

		creditContract.setApplicantType(CreditBuilderFormatter.getApplicantType(consumer.getAccountHolderType(), ""));

		creditContract.setFirstName(consumer.getName1());
		creditContract.setSurname(consumer.getSurname());
		creditContract.setPatronymic(consumer.getName2());

		creditContract.setBirthday(CreditBuilderFormatter.formatDate(consumer.getDateOfBirth()));
		creditContract.setBirthPlace(consumer.getPlaceOfBirth());

		creditContract.setPersonDocumentType(CreditBuilderFormatter.formatDocumentType(consumer.getPrimaryIDType()));
		creditContract.setPersonDocumentNumber(consumer.getPrimaryID());
		creditContract.setInn(consumer.getSecondaryID());

		BkiAddressType registrationAddress = CreditBuilderFormatter.getRegistrationAddress();
		BkiAddressType residenceAddress = CreditBuilderFormatter.getResidenceAddress();

		for (EnquiryResponseERIB.Consumers.S.CAIS.Consumer.Address address : consumer.getAddresses())
		{
			if (registrationAddress != null && registrationAddress.getCode().equals(address.getType()))
				creditContract.setRegistrationAddress(CreditBuilderFormatter.formatAddress(address));
			else if (residenceAddress != null &&  residenceAddress.getCode().equals(address.getType()))
				creditContract.setResidentialAddress(CreditBuilderFormatter.formatAddress(address));
		}

		creditContract.setMobilePhoneNumber(CreditBuilderFormatter.formatPhoneNumber(consumer.getMobileTelNbr()));
		creditContract.setPensionNumber(consumer.getPensionNbr());
		creditContract.setEntrepreneurNumber(consumer.getPrivateEntrepreneurNbr());

		return creditContract;
	}

	private static class HistoryComparator implements Comparator<EnquiryResponseERIB.Consumers.S.CAIS.MontlyHistory>
	{
		public int compare(EnquiryResponseERIB.Consumers.S.CAIS.MontlyHistory o1, EnquiryResponseERIB.Consumers.S.CAIS.MontlyHistory o2)
		{
			return o1.getHistoryDate().compareTo(o2.getHistoryDate());
		}
	}

	private EnquiryResponseERIB.Consumers.S.CAIS.MontlyHistory getLastMonthData(List<EnquiryResponseERIB.Consumers.S.CAIS.MontlyHistory> history)
	{
		if (CollectionUtils.isEmpty(history))
			return null;
		if (history.size() == 1)
			return history.get(0);
		return Collections.max(history, new HistoryComparator());
	}
}
