package com.rssl.phizic.business.documents.templates;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.documents.GateTemplate;
import com.rssl.phizic.gate.payments.owner.EmployeeInfo;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.security.ConfirmableObject;

import java.util.Calendar;
import java.util.Map;

/**
 * ��������� ������� ������� (������ ����������)
 *
 * @author khudyakov
 * @ created 30.01.14
 * @ $Author$
 * @ $Revision$
 */
public interface TemplateDocument extends ConfirmableObject, StateObject, GateTemplate, FraudAuditedObject
{
	/**
	 * ������������������� ������ ��������������� �������
	 */
	void initialize() throws BusinessException, BusinessLogicException;

	/**
	 * ������������������� ������ ��������������� �������
	 * @param document ������ ��������
	 */
	void initialize(BusinessDocument document) throws BusinessException;

	/**
	 * @return ���������� �� ���������� �������
	 * �����: ������������ ��� ����������� ������� ����, ������������� 1 ���
	 */
	ActivityInfo getActivityInfo();

	/**
	 * ���������� ���������� �� ���������� �������
	 * @param activityInfo ���������� �� ���������� �������
	 */
	void setActivityInfo(ActivityInfo activityInfo);

	/**
	 * @return ������ (����-��������)
	 * @throws BusinessException
	 */
	public Map<String, String> getFormData() throws BusinessException;

	/**
	 * �������� ������
	 * @param source ������ ��������� ������
	 * @throws BusinessException
	 */
	void setFormData(FieldValuesSource source) throws BusinessException, BusinessLogicException;

	/**
	 * @param metadata ����������
	 * @return �������� ������� �����������
	 */
	String generateDefaultName(Metadata metadata) throws BusinessException;

	/**
	 * �������� ������� (������)
	 * @return �������
	 */
	Person getOwner() throws BusinessException;

	/**
	 * ���������� ����� �������� �������
	 * @param channel ����� �������� �������
	 */
	void setClientCreationChannel(CreationType channel);

	/**
	 * ���������� ����� �������������(������ internet)
	 * @param channel - �����
	 */
	void setClientOperationChannel(CreationType channel);

	/**
	 * ���������� ����� �������������(������ internet)
	 * @param channel - �����
	 */
	void setAdditionalOperationChannel(CreationType channel);

	/**
	 * ���������� ���� ��������������� ������������� �������
	 * @param date ���� ��������������� �������������
	 */
	void setAdditionalOperationDate(Calendar date);

	/**
	 * ���������� � ������ ���������� � ��������� ����������.
	 * @param employeeInfo
	 */
	void setCreatedEmployeeInfo(EmployeeInfo employeeInfo);

	/**
	 * ���������� � ������ ���������� � ������������� ����������.
	 * @param employeeInfo - ���� ����������
	 */
	void setConfirmedEmployeeInfo(EmployeeInfo employeeInfo);

	/**
	 * ���������� ���������� ���� ����������
	 * @param receiverTransitBank ���������� ���� ����������
	 */
	void setReceiverTransitBank(ResidentBank receiverTransitBank);

	/**
	 * @return ��� ����� ��������
	 */
	ResourceType getChargeOffResourceType();

	/**
	 * @return ���� ������� ��������
	 * @throws BusinessException
	 */
	PaymentAbilityERL getChargeOffResourceLink() throws BusinessException;

	/**
	 * @return ��� ����� ����������
	 */
	ResourceType getDestinationResourceType();

	/**
	 * @return ���� ������� ����������
	 * (��� ������� �������� �����������)
	 *
	 * @throws BusinessException
	 */
	PaymentAbilityERL getDestinationResourceLink() throws BusinessException;

	/**
	 * @return ����� �������
	 */
	Money getExactAmount();

	/**
	 * @return ��� ����������
	 */
	String getReceiverType();

	/**
	 * @return ������ ���������� ������� �������
	 */
	String getReceiverSubType();

	/**
	 * ���������� ���� ����������� �� ������ ���
	 * @param value ����
	 */
	void setRestrictReceiverInfoByPhone(boolean value);

	/**
	 * @return ��������� ����������� �� ������ ��� � ������ ��������
	 */
	boolean hasRestrictReceiverInfoByPhone();

	/**
	 * �������� ���������� � ���������� ������� �� �����
	 * @param card �����
	 */
	void fillReceiverInfoByCardOwner(Card card);

	/**
	 * ���������� ��� ����������
	 * @param value ��� ����������
	 */
	void setReceiverFirstName(String value);

	/**
	 * ���������� ������� ����������
	 * @param value ������� ����������
	 */
	void setReceiverSurName(String value);

	/**
	 * ���������� �������� ����������
	 * @param value ��������
	 */
	void setReceiverPatrName(String value);

	/**
	 * ���������� ������� ������������� ����������
	 * @param id �������������
	 */
	void setReceiverInternalId(Long id);

	/**
	 * @return ������������� ����� �������
	 */
	String getLoanLinkId();

	/**
	 * @return �������� ����
	 */
	String getTarifPlanCodeType() throws DocumentException;
}
