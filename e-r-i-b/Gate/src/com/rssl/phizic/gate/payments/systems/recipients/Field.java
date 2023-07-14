package com.rssl.phizic.gate.payments.systems.recipients;

import com.rssl.phizic.common.types.RequisiteType;
import com.rssl.phizic.common.types.BusinessFieldSubType;

import java.util.List;

/**
 * ���������� � ���. ���� �������.
 * @author Gainanov
 * @ created 14.07.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� � ���. ���� �������.
 */
public interface Field extends Comparable
{
	/**
	 * ��� ���� ��� ����������� ������������
	 *
	 * @return ��� ����
	 */
	String getName();

	/**
	 * ��� ����
	 *
	 * @return ��� ����
	 */
	FieldDataType getType();

	/**
	 * �������� �� ���������
	 *
	 * @return �������� �� ��������� ��� null, ���� �� �������
	 */
	String getDefaultValue();

	/**
	 * ���������
	 *
	 * @return
	 */
	List<RequisiteType> getRequisiteTypes();

	/**
	 * ����������� �� ��� ����������
	 *
	 * @return ����������� �� ��� ����������
	 */
	boolean isRequired();

	/**
	 * ���������� �� ������������
	 *
	 * @return ���������� �� ������������
	 */
	boolean isVisible();

	/**
	 * ����� �� �������������.
	 *
	 * @return ����� �� �������������.
	 */
	boolean isEditable();

	/**
	 * ������ �� ���� ������ ����� �������.
	 *
	 * @return ������ �� ���� ������ ����� �������
	 */
	boolean isMainSum();

	/**
	 * ������������� ����
	 *
	 * @return ������������� ����
	 */
	String getExternalId();

	/**
	 * ����������� ���� ��� �������
	 *
	 * @return ����������� ���� ��� �������
	 */
	String getDescription();

	/**
	 * ��������� �� ���������
	 *
	 * @return ��������� �� ���������
	 */
	String getHint();

	/**
	 * ����������� ���������
	 *
	 * @return ����������� ���������
	 */
	String getPopupHint();

	/**
	 * ����������� ����� ����
	 *
	 * @return ���������� ����� ����
	 */
	Long getMinLength();

	/**
	 * ������������ ����� ����
	 *
	 * @return ������������ ����� ����
	 */
	Long getMaxLength();

	/**
	 * ������� �������� �����
	 *
	 * @return ������� �������� �����
	 */
	List<FieldValidationRule> getFieldValidationRules();

	/**
	 * �������� �� ���� ��������(����������������)
	 * @return ��/���
	 */
	boolean isKey();

	/**
	 * ������� ���������� ������� � ������� �������
	 * @return ��/���
	 */
	boolean isSaveInTemplate();

	/**
	 * ������� �������� �������� ������ ��� ��������������
	 * ������������, ��������, ��� ��������� � ��� �������������
	 * @return ��/���
	 */
	boolean isRequiredForConformation();

	/**
	 * ������� ��������� � ���
	 * @return ��/���
	 */
	boolean isRequiredForBill();

	/**
	 * ������� ��������� �������� �� ����� ��������������.
	 * @return ��/���
	 */
	boolean isHideInConfirmation();

	/**
	 * �������� ����.
	 * ������������ ��� �������� �������� � �� �
	 * �������� �������������� �������� �� �� � ��������� ���.
	 * ������ ��� �������� �������� ��������� �������� �������.
	 * @return �������� ����
	 */
	Object getValue();

	/**
	 * ���������� �������� ����
	 * @param value �������� ����
	 */
	void setValue(Object value);

	/**
	 * ����� ������ � ����, ������� ��� ����������� ������������
	 * @return ���� ������, ��� null � ������ ����������. 
	 */
	String getError();

	/**
	 * ���� ����� ������������ � ����� ���� ������ �����,
	 * ��� ������ ���� ����� ������ ��������� ������.
	 *
	 * @return ������
	 */
	String getGroupName();

	/**
	 * ����� ��� ������������ �������� ���������� �������� �� ���������.
	 * @return ����� ���� #***#.
	 */
	String getMask();

	/**
	 * ������ �������� ���� ("�������� �������", "������� �����")
	 * @return ������ ��� ���������
	 */
	public BusinessFieldSubType getBusinessSubType();

	/**
	 * @return ������������� ����������� ������ ��� ��������
	 */
	String getExtendedDescId();
}