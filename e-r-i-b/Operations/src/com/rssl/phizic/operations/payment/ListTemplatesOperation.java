package com.rssl.phizic.operations.payment;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.FilterPaymentForm;
import com.rssl.phizic.business.documents.templates.ConfigImpl;
import com.rssl.phizic.business.documents.templates.Constants;
import com.rssl.phizic.business.documents.templates.TemplateDocument;
import com.rssl.phizic.business.documents.templates.service.TemplateDocumentService;
import com.rssl.phizic.business.documents.templates.service.filters.TemplateDocumentFilter;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.common.types.documents.FormType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.documents.GateTemplate;
import com.rssl.phizic.gate.payments.template.TemplateInfo;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/**
 * @author mihaylov
 * @ created 11.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class ListTemplatesOperation extends OperationBase implements ListEntitiesOperation
{
	private List<TemplateDocument> templates = new ArrayList<TemplateDocument>();
	private Map<String, Long> allTemplatesNameMap = new HashMap<String, Long>(); // ������������ ���� ������������ �������� �� ���������������

	public void initialize(CreationType channelType) throws BusinessException, BusinessLogicException
	{
		templates = TemplateDocumentService.getInstance().findByChannel(getPerson().asClient(), channelType);
	}

	public void initialize(CreationType channelType, String chargeOffResource) throws BusinessException, BusinessLogicException
	{
		templates = TemplateDocumentService.getInstance().findByChargeOffResourceInChannel(getPerson().asClient(), channelType, chargeOffResource);
	}

	public void initialize(FormType formType, CreationType channelType) throws BusinessException, BusinessLogicException
	{
		templates = TemplateDocumentService.getInstance().findByFormTypeInChannel(getPerson().asClient(), channelType, formType);
	}

	//������ �������� ������ ��� ������� ���� ������������
	public void initializeForPersonalMenu() throws BusinessException, BusinessLogicException
	{
		templates = TemplateDocumentService.getInstance().findForPersonalMenu(getPerson().asClient());
	}

	public void initialize(TemplateDocumentFilter ... filters) throws BusinessException, BusinessLogicException
	{
		initialize(null, filters);
	}

	public void initialize(Comparator<TemplateDocument> comparator, TemplateDocumentFilter ... filters) throws BusinessException, BusinessLogicException
	{
		templates = TemplateDocumentService.getInstance().getFiltered(getPerson().asClient(), filters);
		if (comparator != null)
		{
			Collections.sort(templates, comparator);
		}
	}

	public void initializeForSmsChannel(Set<String> smsCommandAliases) throws BusinessException, BusinessLogicException
	{
		templates =	TemplateDocumentService.getInstance().findReadyToPaymentInSmsChannel(getPerson().asClient(), smsCommandAliases);
	}

	/**
	 * ������ ��������
	 * @return List ��������
	 */
	public List<TemplateDocument> getEntity()
	{
		return Collections.unmodifiableList(templates);
	}

	/**
	 * ��������� ������������ �������  � ������� �� �����������
	 * @param selectedIds - ������������ �������
	 * @param sortTemplates - ������� �����������
	 * @param namesMap �������� ��������
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void save(String[] selectedIds, Long[] sortTemplates, Map<String, String> namesMap) throws BusinessLogicException, BusinessException
	{
		// ��������� �������� �������� �� ������������
		Set<String> uniqueNames = new HashSet<String>(namesMap.values());
		if (uniqueNames.size() < namesMap.values().size())
			throw new BusinessLogicException("�������� �������� ������ ���� ���������");

		if (allTemplatesNameMap.isEmpty())
			initializeAllTemplatesNameMap();

		int i = sortTemplates.length;
		List<String> ids = Arrays.asList(selectedIds);

		for (Long id : sortTemplates)
		{
			String templateName = namesMap.get(id.toString());

			TemplateDocument template = findTemplate(id);
			TemplateInfo templateInfo = template.getTemplateInfo();

			// ��������� �������� ������� �� ��������� �� �������, ������ ���������� ��� ���������, ������� ������������ ����� ��������� �� ���� �������� �������
			if (!templateInfo.getName().equals(templateName) && allTemplatesNameMap.get(templateName) != null)
				throw new BusinessLogicException(String.format(Constants.DUPLICATE_TEMPLATE_NAME_ERROR_MESSAGE, templateName));

			if (templateInfo.getOrderInd() != i || templateInfo.isVisible() != ids.contains(String.valueOf(template.getId())) || !templateInfo.getName().equals(templateName))
			{
				templateInfo.setVisible(ids.contains(String.valueOf(template.getId())));
				templateInfo.setName(templateName);
				templateInfo.setOrderInd(i);
				TemplateDocumentService.getInstance().addOrUpdate(template);
			}
			i--;
		}
	}

	/**
	 * ��������� ������� ����������� ��������
	 * @param sortTemplates - ������� �����������
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void save(Long[] sortTemplates) throws BusinessLogicException, BusinessException
	{
		int i = sortTemplates.length;

		for (Long id : sortTemplates)
		{
            TemplateDocument template = findTemplate(id);
			TemplateInfo templateInfo = template.getTemplateInfo();

			if (templateInfo.getOrderInd() != i)
			{
				templateInfo.setOrderInd(i);
				TemplateDocumentService.getInstance().addOrUpdate(template);
			}
			i--;
		}
	}

	/**
	 * ���������, ������� �� ������������ ��������� ����������� �������� �������� � ������ ����
	 * @param selectedTemplates ������ ��������������� ��������� �������
	 * @param templateNames ���� � ���������� ��������
	 * @return true, ���� ���� ���������
	 * @throws BusinessException
	 */
	public boolean isChanged(List<String> selectedTemplates, Map<String, String> templateNames) throws BusinessException, BusinessLogicException
	{
		List<String> savedSelectedIds = new ArrayList<String>(templates.size());
		for (TemplateDocument template : templates)
		{
			String newValue = templateNames.get(String.valueOf(template.getId()));
			if (StringHelper.isEmpty(newValue))
			{
				throw new BusinessLogicException("����������, ������� �������� �������.");
			}
			
			if (template.getTemplateInfo().isVisible())
			{
				savedSelectedIds.add(String.valueOf(template.getId()));
			}
		}
		if (!selectedTemplates.equals(savedSelectedIds))
		{
			return true;
		}

		for (TemplateDocument template : templates)
		{
			if (!template.getTemplateInfo().getName().equals(templateNames.get(String.valueOf(template.getId()))))
				return true;
		}
		return false;
	}

	public List<FilterPaymentForm> getFilterPaymentFormsForTemplates() throws BusinessException
	{
		List<FilterPaymentForm> filterPaymentForms = new ArrayList<FilterPaymentForm>();
		filterPaymentForms.add(new FilterPaymentForm(FormType.INTERNAL_TRANSFER.getName(), FormType.INTERNAL_TRANSFER));
		filterPaymentForms.add(new FilterPaymentForm(FormType.IMA_PAYMENT.getName(), FormType.IMA_PAYMENT));
		filterPaymentForms.add(new FilterPaymentForm(FormType.LOAN_PAYMENT.getName(), FormType.LOAN_PAYMENT));
		filterPaymentForms.add(new FilterPaymentForm(FormType.INDIVIDUAL_TRANSFER.getName(), FormType.INDIVIDUAL_TRANSFER));
		filterPaymentForms.add(new FilterPaymentForm(FormType.INDIVIDUAL_TRANSFER_NEW.getName(), FormType.INDIVIDUAL_TRANSFER_NEW));
		filterPaymentForms.add(new FilterPaymentForm(FormType.JURIDICAL_TRANSFER.getName(), FormType.JURIDICAL_TRANSFER));
		filterPaymentForms.add(new FilterPaymentForm(FormType.SECURITIES_TRANSFER_CLAIM.getName(), FormType.SECURITIES_TRANSFER_CLAIM));
		filterPaymentForms.add(new FilterPaymentForm(FormType.CONVERT_CURRENCY_TRANSFER.getName(), FormType.CONVERT_CURRENCY_TRANSFER));
		filterPaymentForms.add(new FilterPaymentForm(FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER.getName(), Arrays.asList(FormType.EXTERNAL_PAYMENT_SYSTEM_TRANSFER, FormType.INTERNAL_PAYMENT_SYSTEM_TRANSFER)));

		return filterPaymentForms;
	}

	/**
	 * @return ���
	 */
	public ActivePerson getPerson()
	{
		return PersonHelper.getContextPerson();
	}

	/**
	 * ���������� ��� ���������� query
	 * @return ������������� �������
	 */
	public Long getPersonId()
	{
		return getPerson().getId();
	}

	protected String getInstanceName()
	{
		ConfigImpl config = ConfigFactory.getConfig(ConfigImpl.class);
		return config.getDbSourceName();
	}

	/**
	 * ����� � ������� � ������� ������
	 * @param templateId - ������������� �������
	 * @return ������
	 * @throws BusinessLogicException
	 */
	private TemplateDocument findTemplate(long templateId) throws BusinessLogicException
	{
		for (TemplateDocument template : templates)
		{
			if (template.getId().equals(templateId))
			{
				return template;
			}
		}
		throw new BusinessLogicException("������ � id=" + templateId + " �� ������.");
	}

	/**
	 * ��������� ��� ���� �������� ����������
	 * @throws BusinessException
	 */
	private void initializeAllTemplatesNameMap() throws BusinessException, BusinessLogicException
	{
		for (GateTemplate template : TemplateDocumentService.getInstance().getAll(getPerson().asClient()))
		{
			allTemplatesNameMap.put(template.getTemplateInfo().getName(), template.getId());
		}
	}
}
