package com.rssl.phizic.web.dictionaries.pfp.products.card;

import com.rssl.common.forms.Form;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.products.card.Card;
import com.rssl.phizic.business.dictionaries.pfp.products.card.CardDiagramParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.card.CardProgrammType;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.dictionaries.pfp.products.card.EditCardOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.image.ImageEditActionBase;
import org.apache.struts.action.ActionMessages;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author akrenev
 * @ created 08.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Экшен редактирования карты (ПФП)
 */

public class EditCardAction extends ImageEditActionBase
{
	protected EditEntityOperation createEditOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		EditCardOperation operation = createOperation("EditPFPCardOperation");
		operation.initialize(frm.getId());
		return operation;
	}

	protected Form getEditForm(EditFormBase frm)
	{
		return EditCardForm.EDIT_FORM;
	}

	protected void updateEntity(Object entity, Map<String, Object> data) throws Exception
	{
		Card card = (Card) entity;
		card.setName((String) data.get(EditCardForm.NAME_FIELD_NAME));
		card.setProgrammType((CardProgrammType) data.get(EditCardForm.PROGRAMM_TYPE_FIELD_NAME));
		card.setInputs((BigDecimal) data.get(EditCardForm.INPUTS_FIELD_NAME));
		card.setBonus((BigDecimal) data.get(EditCardForm.BONUS_FIELD_NAME));
		card.setClause((String) data.get(EditCardForm.CLAUSE_FIELD_NAME));
		card.setDescription((String) data.get(EditCardForm.DESCRIPTION_FIELD_NAME));
		card.setRecommendation((String) data.get(EditCardForm.RECOMMENDATION_FIELD_NAME));
		card.setShowAsDefault((Boolean) data.get(EditCardForm.SHOW_AS_DEFAULT_FIELD_NAME));
		CardDiagramParameters diagramParameters = card.getDiagramParameters();
		diagramParameters.setUseImage((Boolean) data.get(EditCardForm.DIAGRAM_USE_IMAGE_FIELD_NAME));
		diagramParameters.setColor((String) data.get(EditCardForm.DIAGRAM_COLOR_FIELD_NAME));
		diagramParameters.setUseNet((Boolean) data.get(EditCardForm.DIAGRAM_USE_NET_FIELD_NAME));
	}

	protected void updateForm(EditFormBase frm, Object entity) throws Exception
	{
		Card card = (Card) entity;
		frm.setField(EditCardForm.NAME_FIELD_NAME,           card.getName());
		frm.setField(EditCardForm.PROGRAMM_TYPE_FIELD_NAME,  card.getProgrammType());
		frm.setField(EditCardForm.INPUTS_FIELD_NAME,         card.getInputs());
		frm.setField(EditCardForm.BONUS_FIELD_NAME,          card.getBonus());
		frm.setField(EditCardForm.CLAUSE_FIELD_NAME,         card.getClause());
		frm.setField(EditCardForm.DESCRIPTION_FIELD_NAME,    card.getDescription());
		frm.setField(EditCardForm.SHOW_AS_DEFAULT_FIELD_NAME,card.isShowAsDefault());
		frm.setField(EditCardForm.RECOMMENDATION_FIELD_NAME, card.getRecommendation());
		CardDiagramParameters diagramParameters = card.getDiagramParameters();
		frm.setField(EditCardForm.DIAGRAM_USE_IMAGE_FIELD_NAME,     diagramParameters.isUseImage());
		frm.setField(EditCardForm.DIAGRAM_COLOR_FIELD_NAME,         diagramParameters.getColor());
		frm.setField(EditCardForm.DIAGRAM_USE_NET_FIELD_NAME,       diagramParameters.isUseNet());
	}


	@Override
	protected ActionMessages validateAdditionalFormData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		return validateImageFormData(frm, operation);
	}

	@Override
	protected void updateFormAdditionalData(EditFormBase frm, EditEntityOperation operation) throws Exception
	{
		updateFormImageData(frm, operation);
	}

	@Override
	protected void updateOperationAdditionalData(EditEntityOperation operation, EditFormBase form, Map<String, Object> data) throws Exception
	{
		updateOperationImageData(operation, form, data);
	}
}
