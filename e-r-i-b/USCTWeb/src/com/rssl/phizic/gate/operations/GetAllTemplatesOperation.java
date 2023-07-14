package com.rssl.phizic.gate.operations;

import com.rssl.phizic.gate.clients.GUID;
import com.rssl.phizic.gate.owners.client.ClientHelper;
import com.rssl.phizic.gate.owners.person.ProfileNotFoundException;
import com.rssl.phizic.gate.templates.impl.TemplateDocument;
import com.rssl.phizic.gate.templates.impl.TemplateOrderIndexComparator;
import com.rssl.phizic.gate.templates.services.CorrelationHelper;
import com.rssl.phizic.gate.templates.services.TemplateDocumentService;
import com.rssl.phizic.gate.templates.services.generated.GateTemplate;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.rssl.phizic.logging.Constants.LOG_MODULE_CORE;

/**
 * �������� ������ ���� �������� �������
 *
 * @author khudyakov
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */
public class GetAllTemplatesOperation implements FindEntityOperation, CorrelationOperation
{
	private static final Log log = PhizICLogFactory.getLog(LOG_MODULE_CORE);

	private List<TemplateDocument> result = new ArrayList<TemplateDocument>();

	public GetAllTemplatesOperation(List guids) throws Exception
	{
		if (CollectionUtils.isEmpty(guids))
		{
			log.error("�������� ��������� ������ ��������: ������������� ������ �������� �� ������ ������� ��������.");
			throw new ProfileNotFoundException();
		}

		try
		{
			List<GUID> profiles = ClientHelper.getUniqueClientHistory(CorrelationHelper.toGateProfiles(guids));
			for (GUID profile : profiles)
			{
				//����������� �� ���� ������� ��������� �������� �������
				List<TemplateDocument> templates = TemplateDocumentService.getInstance().getAll(profile);
				if (CollectionUtils.isEmpty(templates))
				{
					continue;
				}

				result.addAll(templates);
			}

			if (CollectionUtils.isNotEmpty(result))
			{
				Collections.sort(result, new TemplateOrderIndexComparator());
			}
		}
		catch (Exception e)
		{
			log.error("�������� ��������� ������ ��������: ������ ��������� ������ ��������.", e);
			return;
		}
	}

	public List<TemplateDocument> getEntity()
	{
		return Collections.unmodifiableList(result);
	}

	public List toGeneratedEntity() throws Exception
	{
		if (CollectionUtils.isEmpty(result))
		{
			return Collections.emptyList();
		}

		List<GateTemplate> generated = new ArrayList<GateTemplate>();
		for (TemplateDocument template : getEntity())
		{
			try
			{
				generated.add(CorrelationHelper.toGenerated(template));
			}
			catch (Exception e)
			{
				log.error("�������� ��������� ������ ��������: ������ ���������� �������, id = " + template.getId() + ", ������ �� �������� � ������������ ������.", e);
			}
		}
		return generated;
	}
}
