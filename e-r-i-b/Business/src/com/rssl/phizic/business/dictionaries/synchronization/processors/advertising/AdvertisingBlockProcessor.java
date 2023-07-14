package com.rssl.phizic.business.dictionaries.synchronization.processors.advertising;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.advertising.AdvertisingArea;
import com.rssl.phizic.business.advertising.AdvertisingBlock;
import com.rssl.phizic.business.advertising.AdvertisingButton;
import com.rssl.phizic.business.dictionaries.productRequirements.AccTypesRequirement;
import com.rssl.phizic.business.dictionaries.productRequirements.ProductRequirement;
import com.rssl.phizic.business.dictionaries.synchronization.processors.ProcessorBase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author komarov
 * @ created 28.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * Процессор синхронизации баннеров
 */

public class AdvertisingBlockProcessor extends ProcessorBase<AdvertisingBlock>
{
	@Override
	protected Class<AdvertisingBlock> getEntityClass()
	{
		return AdvertisingBlock.class;
	}

	@Override
	protected AdvertisingBlock getNewEntity()
	{
		AdvertisingBlock advertisingBlock = new AdvertisingBlock();
		advertisingBlock.setDepartments(new HashSet<String>());
		advertisingBlock.setReqAccTypes(new HashSet<AccTypesRequirement>());
		advertisingBlock.setRequirements(new HashSet<ProductRequirement>());
		advertisingBlock.setAreas(new ArrayList<AdvertisingArea>());
		advertisingBlock.setButtons(new ArrayList<AdvertisingButton>());
		return advertisingBlock;
	}

	@Override
	protected void update(AdvertisingBlock source, AdvertisingBlock destination) throws BusinessException
	{
	 	destination.setImage(mergeImage(source.getImage(), destination.getImage()));
		destination.setAvailability(source.getAvailability());
		destination.setName(source.getName());
		destination.setOrderIndex(source.getOrderIndex());
		destination.setPeriodFrom(source.getPeriodFrom());
		destination.setPeriodTo(source.getPeriodTo());
		destination.setShowTime(source.getShowTime());
		destination.setState(source.getState());
		destination.setTitle(source.getTitle());
		destination.setText(source.getText());
		destination.setUuid(source.getUuid());

		destination.getDepartments().clear();
		destination.getDepartments().addAll(source.getDepartments());

		destination.getRequirements().clear();
		destination.getRequirements().addAll(getRequirements(source.getRequirements()));

		destination.getAreas().clear();
		destination.getAreas().addAll(getAreas(source.getAreas()));

		destination.getReqAccTypes().clear();
		destination.getReqAccTypes().addAll(getReqAccTypes(source.getReqAccTypes()));

		updateButtons(source.getButtons(), destination.getButtons());
	}

	private List<AdvertisingArea> getAreas(List<AdvertisingArea> source)
	{
		List<AdvertisingArea> result = new ArrayList<AdvertisingArea>();
		for(AdvertisingArea area : source)
			result.add(new AdvertisingArea(area.getAreaName(), area.getOrderIndex()));

		return result;
	}

	private Set<ProductRequirement> getRequirements(Set<ProductRequirement> source)
	{
		Set<ProductRequirement> result = new HashSet<ProductRequirement>();
		for(ProductRequirement requirement : source)
			result.add(new ProductRequirement(requirement.getRequirementType(), requirement.getRequirementState()));

		return result;
	}

	private Set<AccTypesRequirement> getReqAccTypes(Set<AccTypesRequirement> source) throws BusinessException
	{
		Set<AccTypesRequirement> result = new HashSet<AccTypesRequirement>();
		for(AccTypesRequirement requirement : source)
			result.add(new AccTypesRequirement(getLocalVersionByGlobal(requirement.getDepositProduct()), requirement.getRequirementState()));

		return result;
	}

	private void updateButtons(List<AdvertisingButton> source, List<AdvertisingButton> destination) throws BusinessException
	{
		List<AdvertisingButton> result = new ArrayList<AdvertisingButton>();
		for(AdvertisingButton sourceButton : source)
			result.add(getUpdatedButton(sourceButton, getButton(sourceButton.getUuid(), destination)));

		destination.clear();
		destination.addAll(result);
	}

	private AdvertisingButton getUpdatedButton(AdvertisingButton source, AdvertisingButton destination) throws BusinessException
	{
		destination.setImage(mergeImage(source.getImage(), destination.getImage()));
		destination.setShow(source.getShow());
		destination.setOrderIndex(source.getOrderIndex());
		destination.setTitle(source.getTitle());
		destination.setUrl(source.getUrl());
		return destination;
	}

	private AdvertisingButton getButton(String uuid, List<AdvertisingButton> destinationButtons)
	{
		for (AdvertisingButton button : destinationButtons)
		{
			if (uuid.equals(button.getUuid()))
				return button;
		}
		AdvertisingButton block = new AdvertisingButton();
		block.setUuid(uuid);
		return block;
	}
}
