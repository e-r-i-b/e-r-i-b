<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="entityPrefix" value="${param.entityPrefix}"/>
<c:if test="${empty entityPrefix}">
    <c:set var="entityPrefix" value="product.base"/>
</c:if>
<script type="text/javascript">
    function addSegmentCodeType(segmentCodeType)
    {
        var segmentCode = segmentCodeType['code'];
        var segmentDescription = segmentCodeType['name'];
        var line =  '<tr id="targetGroup' + segmentCode + '" class="ListLine0">' +
                        '<td class="listItem">' +
                            '<input type="checkbox" class="checkboxMargin" name="segmentCodeTypes" value="' + segmentCode + '">&nbsp;' +
                        '</td>' +
                        '<td>' +
                            '<input type="hidden" value="' + segmentCode +'" name="fields(targetGroup' + segmentCode + ')">' +
                            '<input type="hidden" value="' + segmentDescription +'" name="fields(descriptionTargetGroup' + segmentCode + ')">' +
                            segmentDescription +
                        '</td>' +
                    '</tr>';
        $("#segmentCodeTypes tbody").append(line);
    }

    function addSegmentCodeTypes(data)
    {
        $(".hidableElement").show();
        var dataSize = data['size'];
        for (var i = 0; i < dataSize; i++)
        {
            var segmentCode = data[i]['code'];
            var segmentDescription = data[i]['name'];
            if ($("#targetGroup" + segmentCode).length == 1)
            {
                return "<bean:message bundle="pfpProductBundle" key="${entityPrefix}.segment.field.button.add.message.exist.prefix"/> " +
                      segmentDescription  +
                      " <bean:message bundle="pfpProductBundle" key="${entityPrefix}.segment.field.button.add.message.exist.suffix"/>";
            }
        }
        for (var j = 0; j < dataSize; j++)
            addSegmentCodeType(data[j]);
    }

    function removeSegmentCodeType(element)
    {
        $(element).parent().parent().remove();
    }

    function removeSegmentCodeTypes()
    {
        checkIfOneItem("segmentCodeTypes");
        if (!checkSelection("segmentCodeTypes", '<bean:message bundle="pfpProductBundle" key="${entityPrefix}.segment.field.button.remove.message.checkSelection"/>'))
            return false;

        if (!confirm('<bean:message bundle="pfpProductBundle" key="${entityPrefix}.segment.field.button.remove.message.confirm"/>'))
            return false;

        $("#segmentCodeTypes :checkbox:checked").each(function(){if (this.name != '') removeSegmentCodeType(this);});
        if ($("[name=segmentCodeTypes]").size() == 0)
            $(".hidableElement").hide();
        return true;
    }

    function checkSegment()
    {
        if ($('[name=segmentCodeTypes]').length == 0)
        {
            alert('<bean:message bundle="pfpProductBundle" key="${entityPrefix}.segment.message.empty.confirm"/>');
            return false;
        }
        return true;
    }
</script>
<c:set var="tableHTML">
    <c:set var="setSegment" value="false"/>
    <logic:iterate id="segment" collection="${form.segmentCodeTypeList}">
        <c:set var="fieldName" value="targetGroup${segment}"/>
        <c:if test="${not empty form.fields[fieldName]}">
            <c:set var="setSegment" value="true"/>
            <tr id="${fieldName}" class="ListLine0">
                <td class="listItem">
                    <input type="checkbox" value="${segment}" name="segmentCodeTypes">
                </td>
                <td>
                    <html:hidden property="fields(${fieldName})"/>
                    <bean:message bundle="pfpProductBundle" key="segment.${segment}"/>
                </td>
             </tr>
        </c:if>
    </logic:iterate>
    <%--Нужно чтобы не отображалось сообщения emptyMessage--%>
    <div></div>
</c:set>
<c:set var="buttonDisplayStyle" value=""/>
<c:if test="${setSegment ne 'true'}">
    <c:set var="buttonDisplayStyle" value=" display:none;"/>
</c:if>

<c:if test="${phiz:impliesOperation('ListSegmentCodeTypesOperation', 'PFPManagment')}">

    <tiles:insert definition="clientButton" flush="false" operation="ListProductTypeParametersOperation">
        <tiles:put name="commandTextKey"    value="${entityPrefix}.segment.field.button.add.name"/>
        <tiles:put name="commandHelpKey"    value="${entityPrefix}.segment.field.button.add.name"/>
        <tiles:put name="bundle"            value="pfpProductBundle"/>
        <tiles:put name="onclick"           value="openSegmentCodeTypeDictionary(addSegmentCodeTypes);"/>
        <tiles:put name="viewType"          value="buttonGrayNew"/>
    </tiles:insert>

    <div class="hidableElement" style="${buttonDisplayStyle}">
        <tiles:insert definition="clientButton" flush="false" operation="ListProductTypeParametersOperation">
            <tiles:put name="commandTextKey"    value="${entityPrefix}.segment.field.button.remove.name"/>
            <tiles:put name="commandHelpKey"    value="${entityPrefix}.segment.field.button.remove.name"/>
            <tiles:put name="bundle"            value="pfpProductBundle"/>
            <tiles:put name="onclick"           value="removeSegmentCodeTypes();"/>
            <tiles:put name="viewType"          value="buttonGrayNew"/>
        </tiles:insert>
    </div>

    <div class=" clear"></div>

    <div class="descriptionBlock">
        <bean:message bundle="pfpProductBundle" key="${entityPrefix}.segment.description"/>
    </div>

</c:if>
<%--Таблица--%>
<div class="smallDynamicGrid hidableElement" style="${buttonDisplayStyle}">
    <tiles:insert definition="tableTemplate" flush="false">
        <tiles:put name="id" value="segmentCodeTypes"/>
        <tiles:put name="head">
            <th width="20px" class="titleTable">
                <input type="checkbox"  onclick="switchSelection(this,'segmentCodeTypes');">
            </th>
            <th class="titleTable">
                <bean:message bundle="pfpProductBundle" key="${entityPrefix}.segment.field.table.columns.name"/>
            </th>
        </tiles:put>
        <tiles:put name="data">
            ${tableHTML}
        </tiles:put>
    </tiles:insert>
</div>
