<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@taglib uri="http://struts.application-servers.com/layout" prefix="sl"%>

<html:form action="/bankcells/cells">
	<tiles:insert definition="bankcellsMain">
		<tiles:put name="submenu" type="string" value="CellTypes"/>
		<tiles:put name="pageTitle" type="string" value="Размеры сейфов"/>

		<tiles:put name="menu" type="string">
		</tiles:put>

<%-- данные --%>
		<tiles:put name="data" type="string">
		<tiles:insert definition="tableTemplate" flush="false">
			<tiles:put name="id" value="systemsNews"/>
            <tiles:put name="buttons">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.edit"/>
                    <tiles:put name="commandHelpKey" value="button.edit.help"/>
                    <tiles:put name="bundle" value="bankcellsBundle"/>
                    <tiles:put name="onclick" value="editCell()"/>
                </tiles:insert>

                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.checkpresence"/>
                    <tiles:put name="commandHelpKey" value="button.checkpresence.help"/>
                    <tiles:put name="bundle" value="bankcellsBundle"/>
                    <tiles:put name="validationFunction">
                        function()
                        {
                            checkIfOneItem("selectedIds");
                            return checkOneSelection('selectedIds', 'Выберите один тип ячейки');
                        }
                    </tiles:put>
                </tiles:insert>

                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"     value="button.remove"/>
                    <tiles:put name="commandHelpKey" value="button.remove.help"/>
                    <tiles:put name="bundle" value="bankcellsBundle"/>
                </tiles:insert>
            </tiles:put>
			<tiles:put name="grid">
				<sl:collection
					model="list"
					property="data"
					id="cell"
					bundle="bankcellsBundle"
					emptyKey="label.type.empty"
					selectName="selectedIds"
					selectProperty="id">

				<sl:collectionItem width="150px"
					title="label.cellType"
					action="/bankcells/cellTerms.do?id=${cell.id}">
				   ${cell.description}
				</sl:collectionItem>

				<sl:collectionItem title="label.termsOfLease">
					<c:forEach var="termOfLeaseElement" items="${cell.termsOfLease}" varStatus="lineInfo">
						<c:if test="${lineInfo.count != 1}">,</c:if>
						<bean:write name="termOfLeaseElement" property="description"/>
					</c:forEach>
				</sl:collectionItem>
				</sl:collection>			  
			</tiles:put>
            <tiles:put name="emptyMessage" value="Не найдено ни одного размера сейфа!"/>
		</tiles:insert>     
			
		<tiles:insert definition="tableTemplate" flush="false">
			<tiles:put name="id" value="systemsNews"/>
			<tiles:put name="text" value="Добавление размера сейфа"/>
            <tiles:put name="buttons">
                <tiles:insert definition="commandButton" flush="false">
					<tiles:put name="commandKey" value="button.add"/>
					<tiles:put name="commandHelpKey" value="button.add.help"/>
					<tiles:put name="bundle" value="bankcellsBundle"/>
				</tiles:insert>
            </tiles:put>
			<tiles:put name="data">
				<tr>
					<td align="right">
						&nbsp;Описание&nbsp;
					</td>
					<td align="center">
						&nbsp;
						<html:text property="newCellTypeDescription" size="60"
						           maxlength="120" styleClass="contactInput"/>
						&nbsp;
					</td>					
				</tr>
				</tiles:put>
		</tiles:insert>

		</tiles:put>
	</tiles:insert>
	<script type="text/javascript">
		var addUrl = "${phiz:calculateActionURL(pageContext,'/bankcells/cellTerms')}";

		function editCell()
		{
            checkIfOneItem("selectedIds");
			if(!checkSelection("selectedIds", "Выберите ячейку"))
				return;

			var id = getRadioValue(document.getElementsByName("selectedIds"))
			window.location = addUrl + "?id=" + id;
		}
	</script>

</html:form>
