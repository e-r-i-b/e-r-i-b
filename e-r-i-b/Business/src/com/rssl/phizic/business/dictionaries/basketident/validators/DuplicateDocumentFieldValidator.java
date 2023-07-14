package com.rssl.phizic.business.dictionaries.basketident.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.common.types.basket.fieldFormula.Constants;
import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author osminin
 * @ created 31.07.15
 * @ $Author$
 * @ $Revision$
 *
 * ���������, ����������� ���������� ���������� ��������� ����� ��������� � ����� ������� ��� �������� ������ �� � ��������� �������.
 * ������� ���������� - ���������� ���������.
 * ������ �������� ���������� �� ���������.
 */
public class DuplicateDocumentFieldValidator extends DuplicateFieldValidatorBase
{
	private int documentFieldsSize;

	/**
	 * ctor
	 * @param fieldsIds �������������� ���������� �����
	 * @param newFieldsIds �������������� ����������� �����
	 * @param documentFieldsSize ���������� ���������� ���������
	 */
	public DuplicateDocumentFieldValidator(String fieldsIds, String newFieldsIds, int documentFieldsSize)
	{
		super(fieldsIds, newFieldsIds);
		this.documentFieldsSize = documentFieldsSize;
	}

	public boolean validate(Map values) throws TemporalDocumentException
	{
		return validateFormulas(values, getFieldIdsArr(), StringUtils.EMPTY) &&
				validateFormulas(values, getNewFieldIdsArr(), Constants.NEW_FIELD_PREFIX);
	}

	private boolean validateFormulas(Map values, String[] ids, String prefix)
	{
		for (String id : ids)
		{
			//�������� �� ������ ������� � ��������� ������� ���������� ����� ��������� ���������� ��������� �������
			if (!validateFormula(values, id, prefix))
			{
				return false;
			}
		}
		return true;
	}

	private boolean validateFormula(Map values, String id, String prefix)
	{
		Set<String> documentFieldValues = new HashSet<String>(documentFieldsSize);
		int emptyFieldsSize = 0;

		for (int i = 0; i < documentFieldsSize; i++)
		{
			//�������� �������� ������� ���������� ��������� ���������
			String documentFieldValue = (String) values.get(Constants.getIdentTypeFieldName(id, i, prefix));

			if (documentFieldValue.equals(StringUtils.EMPTY))
			{
				//������ �������� ����������� �� ���������
				emptyFieldsSize++;
			}
			else
			{
				//�������� ���������� � ���
				documentFieldValues.add(documentFieldValue);
			}
		}

		//���� ���������� ���, �� ���������� ������ �������� � ������ ���� ������ ��������������� ���������� ���������� ���������
		return documentFieldValues.size() + emptyFieldsSize == documentFieldsSize;
	}
}
