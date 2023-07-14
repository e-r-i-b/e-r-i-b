package com.rssl.phizic.operations.payment;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.payments.ClientBusinessDocumentOwner;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.documents.payments.LoanCardClaim;
import com.rssl.phizic.business.documents.payments.PaymentDocumentEntity;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.common.types.exceptions.SystemException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Rtischeva
 * @ created 06.04.15
 * @ $Author$
 * @ $Revision$
 */
public class EmployeePaymentListTransformer extends HistoryListTransformer<Object[], Object[]>
{
	public List<Object[]> transform(List<Object[]> input) throws SystemException
	{
		List<PaymentDocumentEntity> documentEntities = new ArrayList<PaymentDocumentEntity>();
		for (Object[] inputElement : input)
		{
			PaymentDocumentEntity documentEntity = (PaymentDocumentEntity) inputElement[0];
			documentEntities.add(documentEntity);
		}

		// 1. Строим списки идентификаторов документов и департаментов. Параллельно вычисляем макс. и мин. даты создания кредитных заявок
		collectIds(documentEntities);

		// 2. Строим мапу всех расширенных полей для документов
		Map<Long, List<ExtendedAttribute>> extendedAttributesMap = buildExtendedAttributesMap();

		// 3. Строим мапу всех описаний форм и складываем их в мапу (ключ - ИД формы, значение - описание формы)
		Map<Long, String> formNamesMap = buildFormNamesMap();

		// 4. Строим мапу департаментов
		Map<Long, Department> departmentsMap = buildDepartmentsMap();

		// 5. Создаем результирующий список
		return buildResultList(input, formNamesMap, extendedAttributesMap, departmentsMap);
	}

	private List<Object[]> buildResultList(List<Object[]> input, Map<Long, String> formNamesMap, Map<Long, List<ExtendedAttribute>> extendedAttributesMap, Map<Long, Department> departmentsMap) throws SystemException
	{
		List<Object[]> output = new ArrayList<Object[]>(input.size());

		for (Object[] inputElement : input)
		{
			Object[] element = new Object[2];
			PaymentDocumentEntity documentEntity = (PaymentDocumentEntity) inputElement[0];
			ActivePerson person  = (ActivePerson) inputElement[1];
			try
			{
				BusinessDocumentBase businessDocument = transformDocumentEntity(documentEntity, new ClientBusinessDocumentOwner(person));

				if (businessDocument instanceof ExtendedLoanClaim)
				{
					businessDocument.setFormName(FormConstants.EXTENDED_LOAN_CLAIM);
				}
				else if (businessDocument instanceof LoanCardClaim)
				{
					businessDocument.setFormName(FormConstants.LOAN_CARD_CLAIM);
				}
				else
				{
					businessDocument.setFormName(formNamesMap.get(documentEntity.getFormId()));
				}

				List<ExtendedAttribute> extendedFields = extendedAttributesMap.get(businessDocument.getId());
				if (extendedFields != null)
					businessDocument.addAttributes(extendedFields);

				if (documentEntity.getDepartmentId() != null)
					businessDocument.setDepartment(departmentsMap.get(documentEntity.getDepartmentId()));

				element[0] = businessDocument;
				element[1] = person;
			}
			catch (DocumentException e)
			{
				throw new SystemException(e);
			}
			output.add(element);
		}
		return output;
	}
}
