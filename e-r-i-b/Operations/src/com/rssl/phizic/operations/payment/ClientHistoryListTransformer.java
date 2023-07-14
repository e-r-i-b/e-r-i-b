package com.rssl.phizic.operations.payment;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.FormConstants;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.documents.BusinessDocumentBase;
import com.rssl.phizic.business.documents.payments.*;
import com.rssl.phizic.business.extendedattributes.ExtendedAttribute;
import com.rssl.phizic.common.types.exceptions.SystemException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Rtischeva
 * @ created 20.03.15
 * @ $Author$
 * @ $Revision$
 */
public class ClientHistoryListTransformer extends HistoryListTransformer<Object[], PaymentOperationHistoryEntity>
{
	private final BusinessDocumentOwner documentOwner;

	public ClientHistoryListTransformer(BusinessDocumentOwner documentOwner)
	{
		this.documentOwner = documentOwner;
	}

	public List<Object[]> transform(List<PaymentOperationHistoryEntity> input) throws SystemException
	{
		List<PaymentDocumentEntity> documentEntities = new ArrayList<PaymentDocumentEntity>();
		for (PaymentOperationHistoryEntity entity : input)
		{
			if (entity != null)
			{
			    if (entity.getDocument() != null)
    				documentEntities.add(entity.getDocument());
			}
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

	private List<Object[]> buildResultList(List<PaymentOperationHistoryEntity> input, Map<Long, String> formNamesMap, Map<Long, List<ExtendedAttribute>> extendedAttributesMap, Map<Long, Department> departmentsMap) throws SystemException
	{
		List<Object[]> output = new ArrayList<Object[]>(input.size());
		for (int i = 0; i < input.size(); i ++)
		{
			Object[] element = new Object[10];

			PaymentOperationHistoryEntity entity = input.get(i);

			if (entity != null)
			{
				PaymentDocumentEntity documentEntity = entity.getDocument();

				if (documentEntity != null)
				{
					try
					{
						BusinessDocumentBase businessDocument = transformDocumentEntity(documentEntity, documentOwner);

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

						element[9] = businessDocument;
					}
					catch (DocumentException e)
					{
						throw new SystemException(e);
					}
				}
				if (entity.getPfpId() != null)
				{
					element[0] = entity.getPfpId();
					element[2] = entity.getPfpState();
					element[1] = entity.getPfpDate();
					element[3] = entity.getPfpEmployee();
				}
				if (entity.getFirId() != null)
				{
					element[4] = entity.getFirId();
					element[5] = entity.getFirSum();
					element[6] = entity.getFirCard();
					element[7] = entity.getFirDate();
				}
				if (entity.getClaimId() != null)
				{
					element[8] = entity.getClaimId();
					element[1] = entity.getPfpDate();
					element[2] = entity.getPfpState();
					element[3] = entity.getPfpEmployee();
				}

				output.add(element);
			}
		}
		return output;
	}

}
