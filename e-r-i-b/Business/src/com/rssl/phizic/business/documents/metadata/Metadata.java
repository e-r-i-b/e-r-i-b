package com.rssl.phizic.business.documents.metadata;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.common.forms.doc.StateMachineInfo;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.xslt.FilteredEntityListSource;
import com.rssl.common.forms.xslt.XmlConverter;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import org.w3c.dom.Element;

import java.io.Serializable;
import java.util.Map;
import javax.xml.transform.Templates;

/**
 * ������������ �����������
 * @author Evgrafov
 * @ created 09.11.2006
 * @ $Author: khudyakov $
 * @ $Revision: 58441 $
 */
public interface Metadata extends Serializable
{
	/**
	 * @return ������� ����������� ���������� (== getForm().getName())
	 */
	String getName();

	/**
	 * @return ����������� �����
	 */
	Map<String, Element> getDictionaries();

	/**
	 * @return ����� ��� �������
	 */
	Form getForm();

	/**
	 *
	 * @return ����� ��� ������
	 */
	Form getFilterForm();

	/**
	 * �������� ������ ��� �������������
	 * @param templateName ��� �������
	 * @return ������ ��� �������������
	 */
	XmlConverter createConverter(String templateName);

	/**
	 * @return ������� ����� ��������
	 * @throws FormException
	 */
	BusinessDocument createDocument() throws FormException;

	/**
	 * @return ����� ������ ���������
	 */
	TemplateDocument createTemplate();

	/**
	 * @return �������� ���������������� ������ ����������
	 */
	FilteredEntityListSource getListSource();

	/**
	 * @return ��������� ��� �����
	 */
	ExtendedMetadataLoader getExtendedMetadataLoader();

	/**
	 * @param templateName ��� �������
	 * @return XSLT ������
	 */
	Templates getTemplates(String templateName);

	/**
	 * @return ����� ������ ������
	 */
	String getListFormName();

	/**
	 * @return ��������� ��������������
	 */
	OperationOptions getEditOptions();

	/**
	 * @return ��������� ������
	 */
	OperationOptions getWithdrawOptions();

	boolean isNeedParent();

	Map<String, Object> getAdditionalAttributes();

	/**
	 * @return ������ ���������
	 */
	StateMachineInfo getStateMachineInfo();

	/**
	 * ���� ���������������� ����������
	 * @param fieldSource �������� ������ ��� ������������ ����������� ����������.
	 * @return ����
	 */
	String getMetadataKey(FieldValuesSource fieldSource) throws FormException;
}
