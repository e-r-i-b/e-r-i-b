package com.rssl.phizic.web.dictionaries.pfp.riskProfile;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.configure.SegmentHelper;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.ProductType;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfile;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfileUtil;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.riskProfile.EditRiskProfileOperation;
import com.rssl.phizic.web.common.EditActionBase;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 08.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * Ёкшен редактировани€
 */

public class EditRiskProfileAction extends EditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditRiskProfileOperation operation = createOperation(EditRiskProfileOperation.class);
		operation.initialize(frm.getId());
		((EditRiskProfileForm)frm).setProductTypeSegmentDependence(operation.getProductTypeSegmentDependence());
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return ((EditRiskProfileForm)frm).createEditForm();
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		RiskProfile profile = (RiskProfile) entity;
		profile.setName((String) data.get("name"));
		profile.setSegment((SegmentCodeType) data.get("segment"));
		profile.setDescription((String) data.get("description"));
		profile.setMinWeight((Long) data.get("minWeight"));
		profile.setMaxWeight((Long) data.get("maxWeight"));
		Map<ProductType, Long> weights = new HashMap<ProductType, Long>();
		for (ProductType productType : RiskProfileUtil.getProductTypes())
		{
			Long weight = (Long) data.get("productType" + productType);
			if (weight != null)
				weights.put(productType, weight);
		}
		profile.updateProductsWeights(weights);
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		RiskProfile profile = (RiskProfile) entity;
		frm.setField("name", profile.getName());
		frm.setField("segment", profile.getSegment());
		frm.setField("description", profile.getDescription());
		frm.setField("minWeight", profile.getMinWeight());
		frm.setField("maxWeight", profile.getMaxWeight());
		for (Map.Entry<ProductType, Long> entry: profile.getProductsWeights().entrySet())
		{
			frm.setField("productType" + entry.getKey(), entry.getValue());
		}
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		super.updateFormAdditionalData(frm, operation);
		frm.setField("segmentList", SegmentHelper.getSegmentList());
	}
}
