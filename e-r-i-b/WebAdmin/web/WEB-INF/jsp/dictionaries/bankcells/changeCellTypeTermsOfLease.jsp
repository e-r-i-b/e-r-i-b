<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/bankcells/cellTerms">
	<tiles:insert definition="bankcellsMain">
		<tiles:put name="submenu" type="string" value="CellTypeTermsOfLease"/>
		<tiles:put name="pageTitle" type="string" value="Сейфовые ячейки / Сроки аренды"/>

		<tiles:put name="menu" type="string">

			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.list"/>
				<tiles:put name="commandHelpKey" value="button.list.help"/>
				<tiles:put name="bundle" value="bankcellsBundle"/>
				<tiles:put name="image" value=""/>
				<tiles:put name="action" value="/bankcells/cells.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</tiles:put>

		<%-- данные --%>
		<tiles:put name="data" type="string">
			<tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="changeCellTypeTermsOfLease"/>
                <tiles:put name="name" value="Сейфовые ячейки / Сроки аренды "/>
                <tiles:put name="description" value="Используйте данную форму для редактирования сроков аренды сейфовой ячейки."/>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle" value="bankcellsBundle"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="data">
					<tr class="pmntInfAreaTitle">
						<td colspan="2">Размер сейфа</td>
					</tr>
					<tr>
						<td class="Width120 LabelAll">
							Описание
						</td>
					    <td align="center">						   
					    	<html:text property="field(сellTypeDescription)" size="60"
							           maxlength="120" styleClass="contactInput"/>
							&nbsp;
						</td>
					</tr>
					<tr class="pmntInfAreaTitle">
						<td height="20px" colspan="2">
                            <c:set var="form" value="${ChangeCellTypeTermsOfLeaseForm}"/>
				            <input type="hidden" name="id" value="${form.id}"/>
							<bean:message key="label.termsOfLease" bundle="bankcellsBundle"/>
						</td>
                	</tr>
					<c:forEach var="termOfLease" items="${form.termsOfLease}"
					           varStatus="lineInfo">
					<tr>
					    <td class="LabelAll">
							<html:multibox property="selectedIds"
						                   style="border:none">
						               		<bean:write name="termOfLease" property="id"/>
							</html:multibox>
                        </td>
                        <td>
							<bean:write name="termOfLease" property="description"/>&nbsp;
						</td>
					</tr>
					</c:forEach>
				</tiles:put>
                <tiles:put name="alignTable" value="center"/>
            </tiles:insert>
		</tiles:put>
	</tiles:insert>
</html:form>
