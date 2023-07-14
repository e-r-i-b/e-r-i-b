package com.rssl.phizic.web.ermb.migration.list.client;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.migration.list.Segment;
import com.rssl.phizic.business.ermb.migration.list.operations.ListClientMigrationOperation;
import com.rssl.phizic.business.ermb.migration.list.operations.ListSegmentOperation;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.web.actions.SaveFilterParameterAction;
import com.rssl.phizic.web.common.ListFormBase;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForward;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Экшн просмотра списка клиента при миграции
 * @author Puzikov
 * @ created 10.12.13
 * @ $Author$
 * @ $Revision$
 */

public class ListClientsMigrationAction extends SaveFilterParameterAction
{
	protected ListEntitiesOperation createListOperation(ListFormBase form) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListClientMigrationOperation.class);
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListClientsMigrationForm.FILTER_FORM;
	}

	protected void updateFormAdditionalData(ListFormBase form, ListEntitiesOperation operation) throws Exception
	{
		ListClientsMigrationForm frm = (ListClientsMigrationForm) form;

		List<Segment> segments = new ArrayList<Segment>();
		if (PermissionUtil.impliesOperation("ListSegmentOperation", "ErmbMigrationService"))
		{
			ListSegmentOperation segmentOperation = createOperation(ListSegmentOperation.class);
			segments.addAll(segmentOperation.getAllSegments());
		}
		else
			segments.add(Segment.SEGMENT_3_1);

		frm.setSegments(segments);
	}

	@Override
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		if (!frm.isFromStart())
		{
			super.doFilter(filterParams, operation, frm);
		}
		else
		{
			Boolean isVip = (Boolean) filterParams.get("vip");
			if (BooleanUtils.isTrue(isVip))
			{
				super.doFilter(filterParams, operation, frm);
			}
			else
			{
				//noinspection unchecked
				frm.setData(new ArrayList(0));
				frm.setFilters(filterParams);
				updateFormAdditionalData(frm, operation);
			}
		}
	}

	@Override
	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		Boolean isVip = (Boolean) filterParams.get("vip");
		if (BooleanUtils.isTrue(isVip))
		{
			if (PermissionUtil.impliesOperation("ListSegmentOperation", "ErmbMigrationService"))
			{
				String segment = (String) filterParams.get("segment");
				if (StringUtils.isNotEmpty(segment))
					query.setParameter("segment", segment);
				else
					//По умолчанию в списке ВИП включен только сегмент 3_1
					query.setParameter("segment", Segment.SEGMENT_3_1.getValue());
			}
			else
				//По умолчанию в списке ВИП включен только сегмент 3_1
				query.setParameter("segment", Segment.SEGMENT_3_1.getValue());
		}
		else
			//Обычный список конфликтеров - 3_2_3
			query.setParameter("segment", Segment.SEGMENT_3_2_3.getValue());

		query.setParameter("fio", filterParams.get("fio"));
		query.setParameter("phone", filterParams.get("phone"));
		Object series = filterParams.get("docSeries");
		Object number = filterParams.get("docNumber");
		query.setParameter("document", series == null && number == null ? null : StringHelper.getEmptyIfNull(series) + StringHelper.getEmptyIfNull(number));
		query.setParameter("status", filterParams.get("status"));
		query.setParameter("department", filterParams.get("department"));
		query.setParameter("birthday", filterParams.get("birthday"));
	}

	@Override
	protected ActionForward createActionForward(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		UrlBuilder urlBuilder = new UrlBuilder();
		ActionForward forward = getCurrentMapping().findForward(FORWARD_START);
		urlBuilder.setUrl(forward.getPath());

		Boolean isVip = (Boolean) frm.getFilter("vip");
		if (isVip!=null && isVip)
			urlBuilder.addParameter("vip", "true");

		return new ActionForward(urlBuilder.getUrl(), false);
	}
}
