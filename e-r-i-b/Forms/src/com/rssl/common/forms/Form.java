
package com.rssl.common.forms;

import com.rssl.common.forms.validators.MultiFieldsValidator;

import java.util.List;
import java.io.Serializable;

/**
 * @author Evgrafov
 * @ created 08.12.2005
 * @ $Author: komarov $
 * @ $Revision: 38195 $
 */
public interface Form extends Serializable
{
    /**
     * @return ��� �����
     */
    String getName();

	/**
	 * @return �������� �����
	 */
	String getDescription();

	/**
	 * @return �������� �������
	 */
	String getTemplateName();

	/**
	 * @return ��������� �������� �����
	 */
	String getDetailedDescription();

	/**
	 * @return ������������� ����� ������ �� �����
	 */
	String getConfirmDescription();


	/**
	 * @return ������ ����� �����
	 */
	List<Field> getFields();

	/**
	 * @return ������ ����������� �����
	 */
    List<MultiFieldsValidator> getFormValidators();
}