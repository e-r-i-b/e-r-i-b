package com.rssl.common.forms.validators;


import java.util.ArrayList;
import java.util.List;

/**
 * @author tisov
 * @ created 24.07.15
 * @ $Author$
 * @ $Revision$
 * ����������� ���������, ��������������� ���������� �������� ��� ������������� ��������� �����
 */
public abstract class CompositeLogicValidatorBase extends MultiFieldsValidatorBase
{
	protected List<MultiFieldsValidatorBase> validatorsChain = new ArrayList<MultiFieldsValidatorBase>();

	/**
	 * ���������� ����������
	 * @param validator
	 */
	public void addValidator(MultiFieldsValidatorBase validator)
	{
		this.validatorsChain.add(validator);
	}
}
