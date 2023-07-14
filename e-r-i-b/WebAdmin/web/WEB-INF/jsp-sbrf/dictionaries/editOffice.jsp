<%--
  Created by IntelliJ IDEA.
  User: Pakhomova
  Date: 21.08.2008
  Time: 15:46:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/dictionaries/editSBRFOffice" onsubmit="return setEmptyAction(event);">

<c:set var="frm" value="${EditSBRFOfficeForm}"/>
<c:set var="isNew" value="${empty frm.synchKey}"/>
<tiles:insert definition="dictionariesEdit">
	<tiles:put name="submenu" type="string" value="Edit"/>
	<tiles:put name="pageTitle"   type="string" value="Редактирование офиса"/>

	<!--меню-->
	<tiles:put name="menu" type="string">

		<tiles:insert definition="commandButton" flush="false" service="SBRFOfficesManagement">
			<tiles:put name="commandKey"     value="button.save"/>
			<tiles:put name="commandHelpKey" value="button.save.help"/>
			<tiles:put name="bundle"  value="dictionariesBundle"/>
			<tiles:put name="isDefault" value="true"/>
			<tiles:put name="postbackNavigation" value="true"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>

		<tiles:insert definition="clientButton" flush="false">
			<tiles:put name="commandTextKey" value="button.close"/>
			<tiles:put name="commandHelpKey" value="button.close.help"/>
			<tiles:put name="bundle"         value="commonBundle"/>
			<tiles:put name="action"         value="/private/dictionary/offices.do"/>
            <tiles:put name="viewType" value="blueBorder"/>
		</tiles:insert>

	</tiles:put>

	<tiles:put name="data" type="string">

    <tiles:insert definition="tableTemplate" flush="false">
        <tiles:put name="data">
		<tr>
			<td class="Width120 Label">
				Наименование<span class="asterisk">*</span>
			</td>
			<td><html:text property="field(name)" size="100"/></td>
		</tr>
		<tr>
			<td class="Width120 Label">ТБ<span class="asterisk">*</span></td>
			<td>
				<c:choose>
					<c:when test="${isNew}">
						<html:text property="field(region)" size="30" maxlength="4"/>
					</c:when>
					<c:otherwise>
						<html:text property="field(region)" size="30" maxlength="4"  readonly="true"/>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="Width120 Label">ОСБ</td>
			<td>
				<c:choose>
					<c:when test="${isNew}">
						<html:text property="field(branch)" size="30" maxlength="4"/>
					</c:when>
					<c:otherwise>
						<html:text property="field(branch)" size="30" maxlength="4"  readonly="true"/>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="Width120 Label">Филиал</td>
			<td>
				<c:choose>
					<c:when test="${isNew}">
						<html:text property="field(office)" size="30" maxlength="7"/>
					</c:when>
					<c:otherwise>
						<html:text property="field(office)" size="30" maxlength="7"  readonly="true"/>
					</c:otherwise>
				</c:choose>
			</td>
		</tr>
		<tr>
			<td class="Width120 Label">БИК<span class="asterisk">*</span></td>
			<td><html:text property="field(bic)" size="30" maxlength="9"/></td>
		</tr>
		<tr>
			<td class="Width120 Label">Адрес</td>
			<td><html:text property="field(address)" size="100" maxlength="240"/></td>
		</tr>
		<tr>
			<td class="Width120 Label">Телефон</td>
			<td><html:text property="field(telephone)" size="100" maxlength="50"/></td>
		</tr>
		<tr>
			<td class="Width120 Label">Индекс</td>
			<td><html:text property="field(postIndex)" size="100" maxlength="6"/></td>
		</tr>
	    </tiles:put>
    </tiles:insert>

	</tiles:put>

</tiles:insert>

</html:form>