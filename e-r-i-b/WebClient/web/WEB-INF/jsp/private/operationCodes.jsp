<%--
  Created by IntelliJ IDEA.
  User: Egorova
  Date: 08.07.2008
  Time: 17:34:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html:form action="/private/operationCodes">

<tiles:insert definition="dictionary">
<tiles:put name="pageTitle" type="string" value="Справочник видов валютных операций"/>
<tiles:put name="menu" type="string">
		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.cancel"/>
			<tiles:put name="commandHelpKey" value="button.cancel"/>
			<tiles:put name="image" value=""/>
			<tiles:put name="bundle" value="dictionaryBundle"/>
			<tiles:put name="onclick" value="javascript:window.close()"/>
		</tiles:insert>
</tiles:put>


<tiles:put name="filter" type="string">
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.code"/>
		<tiles:put name="name" value="code"/>
	</tiles:insert>
	<tiles:insert definition="filterTextField" flush="false">
		<tiles:put name="label" value="label.name"/>
		<tiles:put name="name" value="name"/>
	</tiles:insert>
</tiles:put>

<tiles:put name="data" type="string">
        <tiles:insert definition="tableTemplate" flush="false">
            <tiles:put name="id" value="OperationCodes"/>
            <tiles:put name="image" value="iconMid_dictionary.gif"/>
            <tiles:put name="text" value="Валютные операции"/>
            <tiles:put name="buttons">
            <script type="text/javascript">
                function sendOperationTypeData(event)
                {
                    var ids = document.getElementsByName("selectedIds");
                    preventDefault(event);
                    var id = getRadioValue(ids);
                    for (var i = 0; i < ids.length; i++)
                    {
                        if (ids.item(i).checked)
                        {
                            var r = ids.item(i).parentNode.parentNode;
                            var a = new Array(4);
                            a['operationCode'] = trim(r.cells[1].innerHTML);
                            window.opener.setOperationCodeInfo(a);
                            window.close();
                            return true;
                        }
                    }
                    alert("Выберите операцию.");
                    return false;
                }
            </script>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.choose"/>
                    <tiles:put name="commandHelpKey" value="button.choose"/>
                    <tiles:put name="image" value=""/>
                    <tiles:put name="bundle" value="dictionaryBundle"/>
                    <tiles:put name="onclick" value="javascript:sendOperationTypeData(event)"/>
                </tiles:insert>
            </tiles:put>
            <tiles:put name="grid">
                <sl:collection id="listElement" model="list" property="data">
                    <sl:collectionParam id="selectType" value="radio"/>
                    <sl:collectionParam id="selectName" value="selectedIds"/>
                    <sl:collectionParam id="selectProperty" value="id"/>

                    <sl:collectionParam id="onRowClick" value="selectRow(this,'selectedIds');"/>
                    <sl:collectionParam id="onRowDblClick" value="sendOperationTypeData();"/>

                    <sl:collectionItem title="Код">
                        <fmt:formatNumber value="${listElement.code}" minIntegerDigits="5" pattern="#####"/>
                    </sl:collectionItem>
                    <sl:collectionItem title="Наименование" property="name"/>
                </sl:collection>
            </tiles:put>

            <tiles:put name="isEmpty" value="${empty ShowOperationCodesForm.data}"/>
            <tiles:put name="emptyMessage" value="Не&nbsp;найдено&nbsp;ни&nbsp;одного&nbsp;вида&nbsp;валютных&nbsp;операций,<br>
                    соответствующего&nbsp;заданному&nbsp;фильтру!"/>
			</tiles:insert>

</tiles:put>

</tiles:insert>

</html:form>