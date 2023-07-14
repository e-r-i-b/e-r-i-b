package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.DocumentSignature;
import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.NodeInfoContainer;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.PaymentAbilityERL;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.MessageCollector;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.common.types.documents.State;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.logging.operations.context.PossibleAddingOperationUIDObject;
import com.rssl.phizic.person.DeviceInfoObject;
import com.rssl.phizic.monitoring.fraud.FraudAuditedObject;
import com.rssl.phizic.security.ConfirmableObject;
import org.w3c.dom.Document;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

/**
 * @author Evgrafov
 * @ created 09.11.2006
 * @ $Author: khudyakov $
 * @ $Revision: 82714 $
 */
public interface BusinessDocument<D> extends Serializable, ConfirmableObject, StateObject, PossibleAddingOperationUIDObject, NodeInfoContainer, DeviceInfoObject, FraudAuditedObject
{
	/**
	 * ������ ��������� ������������ ����
	 */
	public static final String EXTENDED_FIELD_SOURCE_PATTERN = "extra-parameters/parameter[@name='%s']/@value";

	/**
	 * @return ID ���������
	 */
	Long getId();

	/**
	 * @return ��� ����� �������
	 */
	String getFormName();

	/**TODO REMOVE
	 * @param formName ��� ����� �������
	 */
	void setFormName(String formName);

	/**
	 * @return ��� �����
	 */
	FormType getFormType();

	/**
	 * @return ��������
	 */
	BusinessDocumentOwner getOwner() throws BusinessException;

	/**
	 * @param owner ��������
	 */
	void setOwner(BusinessDocumentOwner owner) throws BusinessException;

	/**
	 * @return ����� ���������
	 */
	String getDocumentNumber();

	/**
	 * ��������� ������ ���������
	 * @param documentNumber ����� ���������
	 */
	void setDocumentNumber(String documentNumber);

	/**
	 * ������������� ���������
	 * @param document XML �������������
	 * @param messageCollector ��������� ������, ����� ���� null
	 */
	void initialize(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException;

	/**
	 * ������������� ���������
	 * @param template ������
	 */
	void initialize(TemplateDocument template) throws DocumentException;

	/**
	 * �������� ����� ���������
	 * @return deep copy
	 */
	BusinessDocument createCopy() throws DocumentException, DocumentLogicException;

	/**
	 * ������� ����� � �������� ���������� ������
	 * @param instanceClass ����� ����� ��� null ���� ����� ���������� ������
	 * @return �����
	 * @throws DocumentException
	 * @throws DocumentLogicException
	 */
	public BusinessDocument createCopy(Class<? extends BusinessDocument> instanceClass) throws DocumentException, DocumentLogicException;

	/**
	 * �������������� ��������� � XML �������������
	 * @return XML �������������
	 */
	Document convertToDom() throws DocumentException;

	/**
	 * �������������� (����������) ��������� �� XML �������������
	 * @param document XML ������������� �������
	 * @param messageCollector ��������� ������, ����� ���� null
	 * @throws DocumentLogicException ������ ��� ������ ������. ������ �����
	 */
	void readFromDom(Document document, MessageCollector messageCollector) throws DocumentException, DocumentLogicException;

	/**
	 * @return �������� ��� ���������
	 */
	Class <? extends GateDocument> getType();

	/**
	 * ���������� ��� �������� ���������
	 * @param type - ��� ���������
	 */
	void setCreationType(CreationType type);

	/**
	 * @return ��� �������� ���������
	 */
	CreationType getCreationType();

	/**
	 * @return ����� ������������� ��������
	 */
	public CreationType getClientOperationChannel();

	/**
	 * ���������� ����� ������������� ��������
	 * @param clientOperationChannel ����� ������������� ��������
	 */
	public void setClientOperationChannel(CreationType clientOperationChannel);

	/**
	 * @return ������ ���������
	 */
	State getState();

	/**
	 * @param state ������ ���������
	 */
	void setState(State state);

	/**
	 * @return ��� ���������
	 */
	DocumentSignature getSignature();

	/**
	 * @param signature ��� ���������
	 */
	void setSignature(DocumentSignature signature);

	/**
	 * @return �������������, �������� ����������� ��������
	 */
	D getDepartment() throws BusinessException;

	/**
	 * ��������� �������������
	 * @param department �������������, �������� ����������� ��������
	 */
	void setDepartment(D department);

	/**
	 * ������� ��� ���������� �����
	 * @return ��� ���������� �����
	 */
	String getMbOperCode();

	/**
	 * ���������� ��� ���������� �����
	 * @param mbOperCode - ��� ���������� �����
	 */
	void setMbOperCode(String mbOperCode);

	//TODO � ������ ������ ������!
	/**TODO rename creationDate
	 * @return ���� �������� ���������
	 */
	Calendar getDateCreated();

	/**
	 * @param dateCreated ���� �������� ���������
	 */
	void setDateCreated(Calendar dateCreated);

	/**TODO rename creationDate
	 * @return ���� ���������(����������� �������������)
	 */
	Calendar getDocumentDate();

	/**
	 * @param documentDate ���� ���������(����������� �������������)
	 */
	void setDocumentDate(Calendar documentDate);

	/**
	 * @return ���� ���������� ��������, ������� ����������� � ������ ������������� ���������.
	 */
	Calendar getOperationDate();

	/**
	 * @return ������� ������ ���������.
	 */
	String getRefusingReason();

	/**
	 * ��������� ���� ���������� ��������.
	 * @param operationDate ���� ���������� ��������.
	 */
	void setOperationDate(Calendar operationDate);

	/**
	 * ���� ������ ��������� ������, �.�. ����� ���-���� ������ � ���������� ���� ������.
	 * ��������, ���� �������� ��������� ��� ����. ���, �� �������� �� ����� �� ���������.
	 * @return ���� ������
	 */
	public Calendar getAdmissionDate();

	/**
	 * ���������� ���� ������ ��������� ������.
	 * @param admissionDate ���� ������
	 */
	public void setAdmissionDate(Calendar admissionDate);

	/**
	 * @return ���� ���������� ���������
	 */
	Calendar getExecutionDate();

	/**
	 * ��������� ���� ���������� ���������
	 * @param executionDate ���� ���������� ���������
	 */
	void setExecutionDate(Calendar executionDate);

	/**
	 * ���������� ������� ��������� ������ � �����
	 * @param archive true - ��������� � �����
	 */
	void setArchive(boolean archive);

	/**
	 * @return true - ������ �������� � �����
	 */
	boolean isArchive();

	/**
	 * ��������� ������� ������ ���������
	 * @param refusingReason ������� ������ ���������
	 */
	void setRefusingReason(String refusingReason);

	/**
	 * ���������� �������� ������ ���������
	 * @param stateMachineName �������� ������ ���������
	 */
	void setStateMachineName(String stateMachineName);

	/**
	 * ���������� ���������� ��������� ������, ��������� ��� ���������� �������
	 * @return ���������� ������
	 */
	public Long getCountError();

	/**
	 * ������������� ����� �������� ���������� ��������� ������ ��� ���������� �������
	 * @param countError - ���-�� ������
	 */
	public  void setCountError(Long countError);

	/**
	 * ����������� 1 � ���������� ������
	 */
	public  void  incrementCountError();

	/**
	 * @return ��� �������� ��������� (���� ��������� �������������/�� �������)
	 */
	public CreationSourceType getCreationSourceType();

	/**
	 * ������������� ��� �������� ���������
	 * @param creationSourceType - ��� �������� ���������
	 */
	public void setCreationSourceType(CreationSourceType creationSourceType);

	/**
	 * ������������� ��� ������� � ������� ��������� ��������
	 * @param name ��� �������
	 */
	void setSystemName(String name);

	/**
	 * ��� ���������, �� ���. ��� ����������� ��������
	 * @return
	 */
	public ConfirmStrategyType getConfirmStrategyType();

	/**
	 * @return �����-���.
	 */
	String getPromoCode();

	/**
	 * @return Id ������ ���������� ��������
	 */
	Long getCreatedEmployeeLoginId();

	/**
	 * ���������� id ������ ���������� ��������
	 * @param loginId id ������
	 */
	void setCreatedEmployeeLoginId(Long loginId);

	/**
	 * @return Id ������ �������������� ��������
	 */
	Long getConfirmedEmployeeLoginId();

	/**
	 * ���������� id ������ �������������� ��������
	 * @param loginId id ������
	 */
	void setConfirmedEmployeeLoginId(Long loginId);

	/**
	 * @return
	 */
	Long getAdditionalConfirmedEmployeeLoginId();

	/**
	 *
	 * @param additionalConfirmedEmployeeLoginId
	 */
	void setAdditionalConfirmedEmployeeLoginId(Long additionalConfirmedEmployeeLoginId);

	/**
	 * Id ������, � ������� ����������� ��������
	 * @return sessionId
	 */
	String getSessionId();

	/**
	 * ������������� id ������, � ������� ����������� ��������
	 * @param sessionId  - id ������
	 */
	public void setSessionId(String sessionId);

	/**
	 * @return  ����� ��, � �������� ����� ������
	 */
	public String getCodeATM();

	/**
	 * @param codeATM - ����� ��, � �������� ����� ������
	 */
	public void setCodeATM(String codeATM);

	/**
	 * ��� ������ �� ��������� � �������: ������� �� ������� �����/����� ���� ���� ����� �����
	 * @return ��� ������ �� ��������� � �������
	 */
	TypeOfPayment getTypeOfPayment();

	/**
	 * @return  ������� ��� �������� ��������� � ������ "��������� �������������� �������������"
	 */
	String getReasonForAdditionalConfirm();

	/**
	 * @param reasonForAdditionalConfirn - ������� ��� �������� ��������� � ������ "��������� �������������� �������������"
	 */
	void setReasonForAdditionalConfirm(String reasonForAdditionalConfirn);

	/**
	 * @return ��� ������, ��� ������� ��� ������ ������
	 */
	LoginType getLoginType();

	/**
	 * ���������� ��� ������, ��� ������� ��� ������ ������
	 * @param loginType ��� ������, ��� ������� ��� ������ ������
	 */
	void setLoginType(LoginType loginType);

	/**
	 * @return ������ ���. ������������� �������
	 */
	CreationType getAdditionalOperationChannel();

	/**
	 * @param additionalOperationChannel ������ ���. ������������� �������
	 */
	void setAdditionalOperationChannel(CreationType additionalOperationChannel);

	/**
	 * @return ���� ���. ������������� �������
	 */
	Calendar getAdditionalOperationDate();

	/**
	 * @param date ���� ���. ������������� �������
	 */
	void setAdditionalOperationDate(Calendar date);

	/**
	 * ����, ������������ ���������� ����������� �������� �������� ������� ������� �� ��
	 * @return true - ��������
	 */
	boolean getCheckStatusCountResult();

	/**
	 * ���������� ����, ������������ ���������� ����������� �������� �������� ������� ������� �� ��
	 * @param result - ����
	 */
	void setCheckStatusCountResult(boolean result);

	/**
	 * @return �������� ���� ����������, ��� �������� � offline
	 */
	Calendar getOfflineDelayedDate();

	/**
	 * �������� �� ������ �������� ���������� �� �������
	 * @return true - �������� ���������� �� �������
	 */
	boolean isByTemplate();

	/**
	 * �������� �� ������ �������� ���������� �� �������
	 * @return true - �������� ���������� �� �������
	 */
	boolean isByMobileTemplate();

	/**
	 * �������� �� ������ �������� ������
	 * @return true - �������� ������
	 */
	boolean isCopy();

	/**
	 * @return ���� ��������
	 */
	PaymentAbilityERL getChargeOffResourceLink() throws DocumentException;

	/**
	 * @return ���� ��������
	 */
	ExternalResourceLink getDestinationResourceLink() throws DocumentException;

	/**
	 * ���������� ��� ������� � ����
	 * @param ermbPaymentType - ��� ������� � ����
	 */
	void setErmbPaymentType(String ermbPaymentType);

	/**
	 * ���������� ��� ������� � ����
	 * @return ��� ������� � ����
	 */
	String getErmbPaymentType();

	/**
	 * ���������� �������� �������, �� �������� ������ ��������
	 * @param templateName - �������� �������
	 */
	void setTemplateName(String templateName);

	/**
	 * ���������� �������� �������, �� �������� ������ ��������
	 * @return �������� �������
	 */
	String getTemplateName();

	/**
	 * ������������� ���-����� ����������
	 * @param receiverSmsAlias  ���-����� ����������
	 */
	void setReceiverSmsAlias(String receiverSmsAlias);

	/**
	 * ���������� ���-����� ����������
	 * @return ���-����� ����������
	 */
	String getReceiverSmsAlias();

	/**
	 * ���������� ������������� ���-������� � ����
	 * @param ermbSmsRequestId
	 */
	void setErmbSmsRequestId(String ermbSmsRequestId);

	/**
	 * ���������� ������������� ���-������� � ����
	 * @return ������������� ���-������� � ����
	 */
	String getErmbSmsRequestId();

	/**
	 * ���������� ����� ������������ ��������
	 * @param rechargePhoneNumber - ����� ������������ ��������
	 */
	void setRechargePhoneNumber(String rechargePhoneNumber);

	/**
	 * ������� ����� ������������ ��������
	 * @return - ����� ������������ ��������
	 */
	String getRechargePhoneNumber();

	/**
	 * ���������� �������� ���������� ���������
	 * @param mobileOperatorName - �������� ���������� ���������
	 */
	void setMobileOperatorName(String mobileOperatorName);

	/**
	 * ������� �������� ���������� ���������
	 * @return -  �������� ���������� ���������
	 */
	String getMobileOperatorName();

	/**
	 * @return ����� �������
	 */
	Money getExactAmount();

	/**
	 * @param name - ��� ����� ��������� �� ���������� �������/��������
	 */
	void setEventMessageKey(String name);

	/**
	 * @return ��� ����� ��������� �� ���������� �������/��������
	 */
	String getEventMessageKey();

	/**
	 * @return ������� �������� �������
	 */
	String getDefaultTemplateName() throws BusinessException, BusinessLogicException;

	/**
	 * ��������� �������������� ������� �� ������ �������� ������ ������
	 * @return ������������� ������� ��� null, ���� ������ ������ �� �� ������ �������
	 */
	Long getTemplateId();

	/**
	 * ������� �������������� �������, �� ������ �������� ������ ������
	 * @param templateId - ������������� �������
	 */
	void setTemplateId(Long templateId);

	/**
	 * @return true - ������ �� ������������� ��������������� �������
	 */
	boolean isPaymentByConfirmTemplate() throws DocumentException;

	/**
	 * ��������� �������� (��������) ���������� ������� � ������� �� �������
	 * @param template ������
	 * @return true - ��������� ���
	 */
	boolean equalsKeyEssentials(TemplateDocument template) throws BusinessException;

	/**
	 * @return ���� ������������� ���������� ������� �������
	 */
	boolean isNeedSaveTemplate();

	/**
	 * ����������/�������� ���� ������������� ���������� ������� �������
	 * @param needSaveTemplate ������������� ���������� ������� �������
	 */
	void setNeedSaveTemplate(boolean needSaveTemplate);

	/**
	 * @return true - ���������/���������� ���������/������������
	 */
	boolean isLongOffer();

	/**
	 * @return �������� ����
	 */
	String getTarifPlanCodeType() throws DocumentException;

	/**
	 * @return true - ������ ������ ����������� ������ �����������
	 */
	boolean isMarkReminder();

	/**
	 * ���������� ������� ������ �����������
	 * @param reminder true - ����������� ������ �����������
	 */
	void setMarkReminder(boolean reminder);

	/**
	 * ������� ��������� ������������
	 * @return true - ������������, false - ���
	 */
	boolean isUpgradable();

	/**
	 * ������ ��������� IMSI ��� ������������� ���������
	 * @return
	 */
	boolean isAlwaysIMSICheck();

	/**
	 * ��������� �������-��������� � ����� �������
	 * @return ������-���������
	 */
	DocumentNotice getNotice();

	/**
	 * ������� ������-���������
	 */
	void removeNotice();

	/**
	 * ���������� ������-��������� �� ����������� �������
	 * @param notice ������-���������
	 */
	void setNotice(DocumentNotice notice);

	/**
	 * �������� �������� �� ������ � ������ ���������� � ������� ����������
	 * @return true - ��������
	 */
	boolean checkApplicationRestriction();

	/**
	 * ������� "�������� ������������ ������� ��������"
	 * @return true - ������������
	 */
	boolean supportStatusHistory();

	/**
	 * ���������� ������� ����� �������� ��������� (������ ��� ����������, ������� ������������ ������� ��������)
	 * ��� ����������, ������� �� ������������ ������� ��������, ������������ ������ ������
	 * @return
	 */
	List<BusinessDocumentStatusEntry> getStatusHistory();

	/**
	 * �������� �� �������� ��������������� ������������� ������� ������� ����-�����������?
	 * @return true - ���� ��, false - ���� ���, ��� ���� �������� ������������� �� �������.
	 */
	boolean isFraudReasonForAdditionalConfirm();

	/**
	 * �������� �����, ������������ �������� ������������ (����)
	 * @return �����
	 */
	String getSecurityOfficerText();

	/**
	 * �������� � �������� ����� ������� ������������
	 * @param text
	 */
	void setSecurityOfficerText(String text);
}
