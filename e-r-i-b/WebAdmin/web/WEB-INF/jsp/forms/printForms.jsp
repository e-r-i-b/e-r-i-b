<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/forms/editPrintForm"  onsubmit="return setEmptyAction(event);">
	<tiles:insert definition="logMain">
		<tiles:put name="submenu" type="string" value="Forms"/>
		<tiles:put type="string" name="messagesBundle" value="formsBundle"/>
        <tiles:put name="pageTitle" type="string">
			Шаблоны форм платежей
		</tiles:put>

		<!--меню-->
		<tiles:put name="menu" type="string">
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey"     value="button.save"/>
				<tiles:put name="commandHelpKey" value="button.save"/>
				<tiles:put name="bundle"  value="formsBundle"/>
				<tiles:put name="image"   value=""/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
			<tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey"     value="button.list"/>
				<tiles:put name="commandHelpKey" value="button.list"/>
				<tiles:put name="bundle"  value="formsBundle"/>
				<tiles:put name="image"   value=""/>
				<tiles:put name="action"  value="/forms/payment-forms.do"/>
                <tiles:put name="viewType" value="blueBorder"/>
			</tiles:insert>
		</tiles:put>

		<!--данные-->
		<tiles:put name="data" type="string">
			<c:set var="form" value="${EditPrintFormForm}"/>
			<c:set var="idPrefix"   value="prefix"/>
			<table cellpadding="0" cellspacing="0" width="100%">
				<tr><td class="listPayment">
					&nbsp;Форма платежа:&nbsp;
					<bean:write name="form" property="formName"/>
				</td></tr>
				<tr><td>&nbsp;</td></tr>
				<c:if test="${not empty form.packages}">
					<tr><td>&nbsp;<b>Пакеты шаблонов</b></td></tr>
					<tr><td>
                    <table id="packagesTable" cellspacing="0" cellpadding="0" width="100%" class="userTab">
		            <!-- заголовок списка -->
			         <tr class="titleTable">
						<td width="20px">
							 <html:checkbox property="selectAllPackages" style="border:none" onclick="switchSelection('selectAllPackages','selectedPackages')"/>
					    </td>
						<td width="300px">Наименование</td>
						<td>Описание</td>
					 </tr>
					<!-- строки списка -->
	                 <c:set var="lineNumber" value="0"/>
                     <c:forEach var="packagesList" items="${form.packages}">
	                    <c:set var="lineNumber" value="${lineNumber+1}"/>
	                    <c:set var="packageId" value="${idPrefix}_${packagesList.id}"/>
						<tr class="listLine${lineNumber % 2}">
                            <td align=center class="ListItem">
								<html:multibox property="selectedPackages" style="border:none">
									<bean:write name="packagesList" property="id"/>
								</html:multibox>
							</td>
							<td class="ListItem">
								<a href="javascript:hideOrShow('${packageId}')" title="Показать/скрыть операции">
									&nbsp;<c:out value="${packagesList.name}"/>
								</a>
							</td>
							<td class="ListItem"><bean:write name="packagesList" property="description"/>&nbsp;</td>
						</tr>
	                    <tbody id=${packageId}>
				            <c:forEach var="template" items="${packagesList.templates}" varStatus="lineInfo">
								<tr class="listLine${lineNumber % 2}">
									<td class="listItem" colspan="2" valign="top" width="329px">
										<nobr>&nbsp;<bean:write name="template" property="name"/>&nbsp;</nobr>
									</td>
									<td class="listItem" nowrap="true">
										<bean:write name="template" property="description"/>&nbsp;
									</td>
								</tr>
							</c:forEach>
		                </tbody>
		            </c:forEach>
		            </table>
					</td></tr>
	            </c:if>
				<c:if test="${not empty form.templates}">
					<tr><td>&nbsp;</td></tr>
					<tr><td>&nbsp;<b>Шаблоны документов</b></td></tr>
					<tr><td>
                    <table cellspacing="0" cellpadding="0" width="100%" class="userTab">
		            <!-- заголовок списка -->
			         <tr class="titleTable">
						<td width="20px">
							 <html:checkbox property="selectAllTemplates" style="border:none" onclick="switchSelection('selectAllTemplates','selectedTemplates')"/>
					    </td>
						<td width="300px">Наименование</td>
						<td>Описание</td>
					 </tr>
					<!-- строки списка -->
	                 <c:set var="lineNumber" value="0"/>
                     <c:forEach var="templatesList" items="${form.templates}">
	                    <c:set var="lineNumber" value="${lineNumber+1}"/>
						<tr class="listLine${lineNumber % 2}">
                            <td align=center class="ListItem">
								<html:multibox property="selectedTemplates" style="border:none">
									<bean:write name="templatesList" property="id"/>
								</html:multibox>
							</td>
							<td class="ListItem">
								&nbsp;
									<bean:write name="templatesList" property="name"/>
							</td>
							<td class="ListItem" colspan="2"><bean:write name="templatesList" property="description"/>&nbsp;</td>
						</tr>
		            </c:forEach>
		            </table>
					</td></tr>
	            </c:if>
			</table>
			<script type="text/javascript">
				hideOrShowTableBodies('packagesTable', true, "${idPrefix}_.+");
			</script>
		</tiles:put>
	</tiles:insert>
</html:form>