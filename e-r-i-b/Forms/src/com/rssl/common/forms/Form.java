
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
     * @return Имя формы
     */
    String getName();

	/**
	 * @return Описание формы
	 */
	String getDescription();

	/**
	 * @return Название шаблона
	 */
	String getTemplateName();

	/**
	 * @return Детальное описание формы
	 */
	String getDetailedDescription();

	/**
	 * @return Подтверждение после оплаты по форме
	 */
	String getConfirmDescription();


	/**
	 * @return список полей формы
	 */
	List<Field> getFields();

	/**
	 * @return Список валидаторов вормы
	 */
    List<MultiFieldsValidator> getFormValidators();
}