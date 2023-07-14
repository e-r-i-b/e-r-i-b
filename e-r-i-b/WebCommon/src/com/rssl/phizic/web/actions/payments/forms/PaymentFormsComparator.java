package com.rssl.phizic.web.actions.payments.forms;

import com.rssl.phizic.business.documents.FilterPaymentForm;
import com.rssl.phizic.web.actions.StrutsUtils;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

	/**
	 * Компаратор для сортировки форм в алфавитном порядке.
	 */
	public class PaymentFormsComparator implements Comparator<FilterPaymentForm>
	{
		private static final String NAME_PREFIX = "paymentform.";

		/**
		 * Мап из форм в имя формы.
		 */
		private Map<Object, String> formsNames;

		public PaymentFormsComparator(List<FilterPaymentForm> filterPaymentForms, String bandleName)
		{
			formsNames = new HashMap<Object, String>();
			for (FilterPaymentForm filterPaymentForm : filterPaymentForms)
			{
				String bundleKey = NAME_PREFIX + filterPaymentForm.getName();
				String message = StrutsUtils.getMessage(bundleKey, bandleName);
				formsNames.put(filterPaymentForm, message);
			}
		}

		public int compare(FilterPaymentForm o1, FilterPaymentForm o2)
		{
			String o1Desc = formsNames.get(o1);
			String o2Desc = formsNames.get(o2);
			return o1Desc.compareTo(o2Desc);
		}
	}