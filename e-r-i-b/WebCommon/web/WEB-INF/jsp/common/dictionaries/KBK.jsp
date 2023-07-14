<%--
  User: Kosyakova
  Date: 13.02.2007
  Time: 11:38:23
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<c:set var="standalone" value="${empty param['action']}"/>

<c:choose>

	<c:when test="${standalone}">
		<c:set var="layoutDefinition" value="paymentMain"/>
	</c:when>
	<c:otherwise>
		<c:set var="layoutDefinition" value="dictionary"/>
	</c:otherwise>
</c:choose>

<html:form action="/private/dictionary/KBK"  onsubmit="return setAction();">
	<c:set var="form" value="${phiz:currentForm(pageContext)}"/>

	<tiles:insert definition="${layoutDefinition}">
		<tiles:put name="mainmenu" value="Payments"/>
		<tiles:put name="pageTitle" type="string" value="Справочник КБК"/>

		<tiles:put name="menu" type="string">
			<script type="text/javascript">
                function setAction(event)
                 {
                    preventDefault(event);
                     <c:if test="${!standalone}">
                        var frm = document.forms.item(0);
                        frm.action = frm.action + "?action=getKBKInfo";
                     </c:if>
                    return true;
                 }
				function sendKBKData(event)
				{
					var ids = document.getElementsByName("selectedId");
					preventDefault(event);
					for (i = 0; i < ids.length; i++)
					{
						if (ids.item(i).checked)
						{
							var r = ids.item(i).parentNode.parentNode;
							var a = new Array(2);
							a['code'] = trim(getElementTextContent(r.cells[1]));
							window.opener.setKBKInfo(a);
							window.close();
							return true;
						}
					}
					alert("Выберите код КБК.");
					return false;
				}
			</script>

			<c:if test="${not standalone}">
				<tiles:insert definition="clientButton" flush="false">
					<tiles:put name="commandTextKey" value="button.cancel"/>
					<tiles:put name="commandHelpKey" value="button.cancel"/>
					<tiles:put name="image" value=""/>
					<tiles:put name="bundle" value="dictionaryBundle"/>
					<tiles:put name="onclick" value="javascript:window.close()"/>
				</tiles:insert>
			</c:if>
		</tiles:put>

		<tiles:put name="filter" type="string">
			<tiles:insert definition="filterTextField" flush="false">
				<tiles:put name="label" value="label.codeKBK"/>
				<tiles:put name="bundle" value="dictionaryBundle"/>
				<tiles:put name="name" value="code"/>
			</tiles:insert>
		</tiles:put>

		<tiles:put name="data" type="string">
			<tiles:insert definition="tableTemplate" flush="false">
				<tiles:put name="id" value="KBK"/>
				<tiles:put name="image" value="iconMid_dictionary.gif"/>
				<tiles:put name="text" value="КБК"/>
				<tiles:put name="buttons">
					<c:if test="${not standalone}">
						<tiles:insert definition="clientButton" flush="false">
							<tiles:put name="commandTextKey" value="button.choose"/>
							<tiles:put name="commandHelpKey" value="button.choose"/>
							<tiles:put name="image" value="iconSm_select.gif"/>
							<tiles:put name="bundle" value="dictionaryBundle"/>
							<tiles:put name="onclick" value="javascript:sendKBKData(event)"/>
						</tiles:insert>
					</c:if>
				</tiles:put>

				<tiles:put name="grid">
					<sl:collection id="country" property="data" model="list">
						<sl:collectionParam id="selectType" value="radio" condition="${not standalone}"/>
						<sl:collectionParam id="selectProperty" value="id" condition="${not standalone}"/>
						<sl:collectionParam id="selectName" value="selectedId" condition="${not standalone}"/>
						<sl:collectionParam id="onRowClick" value="selectRow(this);" condition="${not standalone}"/>
						<sl:collectionParam id="onRowDblClick" value="sendKBKData();" condition="${not standalone}"/>

						<sl:collectionItem title="Код" property="code"/>
						<sl:collectionItem title="Наименование" property="note"/>
					</sl:collection>
				</tiles:put>

				<tiles:put name="isEmpty" value="${empty form.data}"/>
				<tiles:put name="emptyMessage" value="Не найдено ни одного кода КБК, <br/>соответсвующей заданному фильтру!"/>
			</tiles:insert>
		</tiles:put>

	</tiles:insert>

</html:form>
