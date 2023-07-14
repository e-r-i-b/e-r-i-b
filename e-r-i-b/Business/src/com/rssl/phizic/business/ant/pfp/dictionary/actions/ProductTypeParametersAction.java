package com.rssl.phizic.business.ant.pfp.dictionary.actions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.products.types.*;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

/**
 * Экшен загрузки разделов ПФП
 * @author koptyaev
 * @ created 24.07.2013
 * @ $Author$
 * @ $Revision$
 */
public class ProductTypeParametersAction  extends DictionaryRecordsActionBase<ProductTypeParameters>
{
	List<SegmentCodeType> getTargetGroup(Element element) throws BusinessException
	{
		try
		{
			Element tGroup = XmlHelper.selectSingleNode(element,"targetGroups");
			final List<SegmentCodeType> targetGroup = new ArrayList<SegmentCodeType>();
			if (tGroup != null)
			{
				XmlHelper.foreach(tGroup, "targetGroup", new ForeachElementAction()
				{
					public void execute(Element element) throws BusinessLogicException
					{
						targetGroup.add(SegmentCodeType.valueOf(XmlHelper.getElementText(element)));
					}
				});
			}
			return targetGroup;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	public void execute(Element element) throws Exception
	{
		String key  = XmlHelper.getSimpleElementValue(element, "key");
		String name = XmlHelper.getSimpleElementValue(element, "name");
		DictionaryProductType type = DictionaryProductType.valueOf(XmlHelper.getSimpleElementValue(element, "type"));
		boolean use = getBooleanValue(XmlHelper.getSimpleElementValue(element, "use"));

		String description = XmlHelper.getSimpleElementValue(element, "description");
		boolean useOnDiagram = getBooleanValue(XmlHelper.getSimpleElementValue(element, "useOnDiagram"));
		boolean useOnTable = getBooleanValue(XmlHelper.getSimpleElementValue(element, "useOnTable"));
		DiagramParameters diagramParameters = null;

		if(useOnDiagram)
		{
			//сборка diagramParameters
			diagramParameters = new DiagramParameters();
			Element elDiagramParameters = XmlHelper.selectSingleNode(element, "diagramParameters");
			diagramParameters.setUseZero(getBooleanValue(XmlHelper.getSimpleElementValue(elDiagramParameters, "useZero")));

			Element elementAxisX = XmlHelper.selectSingleNode(elDiagramParameters, "axisX");
			DiagramAxis axisX = new DiagramAxis();
			axisX.setName(XmlHelper.getSimpleElementValue(elementAxisX,"name"));
			axisX.setUseSteps(getBooleanValue(XmlHelper.getSimpleElementValue(elementAxisX, "useSteps")));
			final List<DiagramStep> diagramStepsX = new ArrayList<DiagramStep>();
			try
			{
				XmlHelper.foreach(elementAxisX, "step", new ForeachElementAction()
				{
					public void execute(Element element)
					{
						DiagramStep diagramStep = new DiagramStep();
						diagramStep.setFrom(Long.valueOf(XmlHelper.getSimpleElementValue(element,"from")));
						diagramStep.setTo(Long.valueOf(XmlHelper.getSimpleElementValue(element,"to")));
						diagramStep.setName(XmlHelper.getSimpleElementValue(element,"name"));
						diagramStepsX.add(diagramStep);
					}
				});
			}
			catch (Exception e)
			{
				throw new BusinessException(e);
			}
			axisX.setSteps(diagramStepsX);

			Element elementAxisY = XmlHelper.selectSingleNode(elDiagramParameters, "axisY");
			DiagramAxis axisY = new DiagramAxis();
			axisY.setName(XmlHelper.getSimpleElementValue(elementAxisY,"name"));
			axisY.setUseSteps(getBooleanValue(XmlHelper.getSimpleElementValue(elementAxisY, "useSteps")));
			final List<DiagramStep> diagramStepsY = new ArrayList<DiagramStep>();
			try
			{
				XmlHelper.foreach(elementAxisY, "step", new ForeachElementAction()
				{
					public void execute(Element element)
					{
						DiagramStep diagramStep = new DiagramStep();
						diagramStep.setFrom(Long.valueOf(XmlHelper.getSimpleElementValue(element,"from")));
						diagramStep.setTo(Long.valueOf(XmlHelper.getSimpleElementValue(element,"to")));
						diagramStep.setName(XmlHelper.getSimpleElementValue(element,"name"));
						diagramStepsY.add(diagramStep);
					}
				});
			}
			catch (Exception e)
			{
				throw new BusinessException(e);
			}
			axisY.setSteps(diagramStepsY);

			diagramParameters.setAxisX(axisX);
			diagramParameters.setAxisY(axisY);
		}

		TableParameters tableParameters = null;
		if(useOnTable)
		{
			//сборка tableParameters
			Element elementTableParameters = XmlHelper.selectSingleNode(element, "tableParameters");
			tableParameters = new TableParameters();
			final List<TableColumn> columns = new ArrayList<TableColumn>();
			try
			{
				XmlHelper.foreach(elementTableParameters, "column", new ForeachElementAction()
				{
					public void execute(Element element)
					{
						TableColumn tableColumn = new TableColumn();
						tableColumn.setValue(XmlHelper.getSimpleElementValue(element,"value"));
						tableColumn.setOrderIndex(Long.valueOf(XmlHelper.getSimpleElementValue(element,"orderIndex")));
						columns.add(tableColumn);
					}
				});
			}
			catch (Exception e)
			{
				throw new BusinessException(e);
			}
			tableParameters.setColumns(columns);
		}

		//сборка link
		Element linkElement = XmlHelper.selectSingleNode(element, "link");
		ProductTypeLink productTypeLink = new ProductTypeLink();
		productTypeLink.setHint(XmlHelper.getSimpleElementValue(linkElement, "hint"));
		productTypeLink.setName(XmlHelper.getSimpleElementValue(linkElement, "name"));

		String imageUrl= XmlHelper.getSimpleElementValue(element, "imageUrl");

		ProductTypeParameters productTypeParameters = new ProductTypeParameters();
		productTypeParameters.setName(name);
		productTypeParameters.setType(type);
		productTypeParameters.setUse(use);
		productTypeParameters.setTargetGroup(getTargetGroup(element));
		productTypeParameters.setDescription(description);
		productTypeParameters.setUseOnDiagram(useOnDiagram);
		productTypeParameters.setUseOnTable(useOnTable);
		productTypeParameters.setDiagramParameters(diagramParameters);
		productTypeParameters.setTableParameters(tableParameters);
		productTypeParameters.setLink(productTypeLink);
		addRecord(key, productTypeParameters, getImageValue(imageUrl));
	}
}
