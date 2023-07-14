package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.FilterPaymentForm;
import com.rssl.phizic.business.payments.forms.PaymentFormService;
import com.rssl.phizic.business.payments.forms.meta.MetadataBean;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;

import java.util.*;

/**
 * @author khudyakov
 * @ created 11.03.2012
 * @ $Author$
 * @ $Revision$
 */
public class PaymentHistoryHelper
{
	public static final String DELIMITER = ",";
	public static final FilterPaymentForm PFP_FILTER_PAYMENT_FORM = new FilterPaymentForm("PersonalFinanceProfile", "pfp");
	public static final FilterPaymentForm FIR_FILTER_PAYMENT_FORM = new FilterPaymentForm("FundInitiatorRequest", "fir");
	public static final FilterPaymentForm DEBIT_CARD_FILTER_PAYMENT_FORM = new FilterPaymentForm("DebitCardClaim", "sbnkd");
	public static final FilterPaymentForm EXTENDED_LOAN_CLAIM_FORM = new FilterPaymentForm("ExtendedLoanClaim", "extendedLoanClaim");
	public static final FilterPaymentForm LOAN_CARD_CLAIM_FORM = new FilterPaymentForm("LoanCardClaim", "loanCardClaim");

	private static final PaymentFormService paymentFormService = new PaymentFormService();
	private static final List<String> simpleLongOfferForms = new ArrayList<String>();    //��������� ����� � ��������� ����������� ���������.
	static
	{
		simpleLongOfferForms.add(FormConstants.CREATE_MONEY_BOX_FORM);
		simpleLongOfferForms.add(FormConstants.EDIT_MONEY_BOX_FORM);
		simpleLongOfferForms.add(FormConstants.REFUSE_MONEY_BOX_FORM);
		simpleLongOfferForms.add(FormConstants.CLOSE_MONEY_BOX_FORM);
		simpleLongOfferForms.add(FormConstants.RECOVERY_MONEY_BOX_FORM);
		simpleLongOfferForms.add(FormConstants.CREATE_P2P_AUTO_TRANSFER_CLAIM_FORM);
		simpleLongOfferForms.add(FormConstants.EDIT_P2P_AUTO_TRANSFER_CLAIM_FORM);
		simpleLongOfferForms.add(FormConstants.DELAY_P2P_AUTO_TRANSFER_CLAIM_FORM);
		simpleLongOfferForms.add(FormConstants.CLOSE_P2P_AUTO_TRANSFER_CLAIM_FORM);
		simpleLongOfferForms.add(FormConstants.RECOVERY_P2P_AUTO_TRANSFER_CLAIM_FORM);
	}

	/**
	 * ��������� ������ FilterPaymentForm (�������������� ����:��������� �����)
	 * @return ������ FilterPaymentForm
	 */
	public static List<FilterPaymentForm> getFilterPaymentForms() throws BusinessException
	{
		List<MetadataBean> beans = paymentFormService.getAllFormsLight();
		if (CollectionUtils.isEmpty(beans))
			return Collections.emptyList();

		Map<String, String> formsMap = new HashMap<String, String>();
		for (MetadataBean bean : beans)
		{
			formsMap.put(bean.getName(), String.valueOf(bean.getId()));
		}

		List<FilterPaymentForm> filterPaymentForms = new ArrayList<FilterPaymentForm>();

		filterPaymentForms.add(PFP_FILTER_PAYMENT_FORM);
		filterPaymentForms.add(FIR_FILTER_PAYMENT_FORM);
		filterPaymentForms.add(DEBIT_CARD_FILTER_PAYMENT_FORM);
		filterPaymentForms.add(EXTENDED_LOAN_CLAIM_FORM);
		formsMap.remove(FormConstants.EXTENDED_LOAN_CLAIM);
		filterPaymentForms.add(LOAN_CARD_CLAIM_FORM);
		formsMap.remove(FormConstants.LOAN_CARD_CLAIM);

		//�������� ids ��� ���� �������� ������������
		String createAutoPaymentIds = createFormIds(formsMap, FormConstants.CREATE_AUTOPAYMENT_FORM, FormConstants.SERVICE_PAYMENT_FORM, FormConstants.INTERNAL_PAYMENT_FORM, FormConstants.RUR_PAYMENT_FORM, FormConstants.JUR_PAYMENT_FORM, FormConstants.LOAN_PAYMENT_FORM);
		if (StringHelper.isNotEmpty(createAutoPaymentIds))
		{
			filterPaymentForms.add(new FilterPaymentForm(FormConstants.CREATE_AUTOPAYMENT_DEFAULT_FORM, createAutoPaymentIds, true));
			//������� �� ������ ����� ������������
			formsMap.remove(FormConstants.CREATE_AUTOPAYMENT_FORM);
		}

		//�������� ids ��� ���� �������������� ������������
		String editAutoPaymentIds = createFormIds(formsMap, FormConstants.EDIT_AUTOPAYMENT_FORM, FormConstants.EDIT_AUTOSUBSCRIPTION_PAYMENT);
		if (StringHelper.isNotEmpty(editAutoPaymentIds))
		{
			filterPaymentForms.add(new FilterPaymentForm(FormConstants.EDIT_AUTOPAYMENT_DEFAULT_FORM, editAutoPaymentIds, true));
			//������� �� ������ ����� ������������
			formsMap.remove(FormConstants.EDIT_AUTOSUBSCRIPTION_PAYMENT);
			formsMap.remove(FormConstants.EDIT_AUTOPAYMENT_FORM);
		}

		//�������� ids ��� ���� �������� ������������
		String refuseAutoPaymentIds = createFormIds(formsMap, FormConstants.REFUSE_AUTOPAYMENT_FORM, FormConstants.REFUSE_AUTOSUBSCRIPTION_PAYMENT_FORM, FormConstants.REFUSE_LONGOFFER_FORM);
		if (StringHelper.isNotEmpty(refuseAutoPaymentIds))
		{
			filterPaymentForms.add(new FilterPaymentForm(FormConstants.REFUSE_AUTOPAYMENT_DEFAULT_FORM, refuseAutoPaymentIds, true));
			//������� �� ������ ����� ������������
			formsMap.remove(FormConstants.REFUSE_AUTOSUBSCRIPTION_PAYMENT_FORM);
			formsMap.remove(FormConstants.REFUSE_AUTOPAYMENT_FORM);
			formsMap.remove(FormConstants.REFUSE_LONGOFFER_FORM);
		}

		//�������� ids ��� ���� ������������� ������������
		String recoveryAutoPaymentIds = createFormIds(formsMap, FormConstants.RECOVERY_AUTOSUBSCRIPTION_PAYMENT_FORM);
		if (StringHelper.isNotEmpty(recoveryAutoPaymentIds))
		{
			filterPaymentForms.add(new FilterPaymentForm(FormConstants.RECOVERY_AUTOSUBSCRIPTION_PAYMENT_FORM, recoveryAutoPaymentIds, true));
			//������� �� ������ ����� ������������
			formsMap.remove(FormConstants.RECOVERY_AUTOSUBSCRIPTION_PAYMENT_FORM);
		}

		//�������� ids ��� ���� ������������ ������������
		String delayAutoPaymentIds = createFormIds(formsMap, FormConstants.DELAY_AUTOSUBSCRIPTION_PAYMENT_FORM);
		if (StringHelper.isNotEmpty(delayAutoPaymentIds))
		{
			filterPaymentForms.add(new FilterPaymentForm(FormConstants.DELAY_AUTOSUBSCRIPTION_PAYMENT_FORM, delayAutoPaymentIds, true));
			//������� �� ������ ����� ������������
			formsMap.remove(FormConstants.DELAY_AUTOSUBSCRIPTION_PAYMENT_FORM);
		}

		//�������� ids ��� ���� ������ �� �������������� ��������� �����
		String preapprovedLoanCardClaimIds = createFormIds(formsMap, FormConstants.LOAN_CARD_OFFER_FORM, FormConstants.PREAPPROVED_LOAN_CARD_CLAIM, FormConstants.LOAN_CARD_CLAIM);
		if (StringHelper.isNotEmpty(preapprovedLoanCardClaimIds))
		{
			filterPaymentForms.add(new FilterPaymentForm(FormConstants.LOAN_CARD_CLAIM,             preapprovedLoanCardClaimIds, false));
			filterPaymentForms.add(new FilterPaymentForm(FormConstants.PREAPPROVED_LOAN_CARD_CLAIM, preapprovedLoanCardClaimIds, false));
			//������� �� ������ ����� ������ �� �������������� ��������� �����
			formsMap.remove(FormConstants.LOAN_CARD_CLAIM);
			formsMap.remove(FormConstants.LOAN_CARD_OFFER_FORM);
			formsMap.remove(FormConstants.PREAPPROVED_LOAN_CARD_CLAIM);
		}

		//����� ��������� �����
		if (MapUtils.isNotEmpty(formsMap))
		{
			for (Map.Entry<String, String> entry : formsMap.entrySet())
			{
				filterPaymentForms.add(new FilterPaymentForm(entry.getKey(), entry.getValue(), simpleLongOfferForms.contains(entry.getKey())));
			}
		}

		return filterPaymentForms;
	}

	private static String createFormIds(Map<String, String> formsMap, String ... forms)
	{
		if (ArrayUtils.isEmpty(forms))
			throw new IllegalArgumentException("�������� forms ������ ���� ��������.");

		List<String> ids = new ArrayList<String>();
		for (String form : forms)
		{
			if (formsMap.get(form) != null)
			{
				ids.add(formsMap.get(form));
			}
		}

		return createFormIds(ids);
	}

	private static String createFormIds(List<String> ids)
	{
		if (CollectionUtils.isEmpty(ids))
			return "";

		int i = 1;
		StringBuffer buffer = new StringBuffer();

		for (String id : ids)
		{
			buffer.append(id);
			if (i < ids.size())
				buffer.append(DELIMITER);

			i++;
		}

		return buffer.toString();
	}
}
