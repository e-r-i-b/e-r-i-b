<%--
  Created by IntelliJ IDEA.
  User: Gainanov
  Date: 25.03.2009
  Time: 10:49:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<% pageContext.getRequest().setAttribute("mode", "Calendar");%>
<html:form action="/listcalendar">
<tiles:insert definition="logMain">
<tiles:put name="submenu" type="string" value="ListCalendar"/>
<tiles:put name="pageTitle" type="string">
	<bean:message key="list.title" bundle="calendarBundle"/>
</tiles:put>
<c:set var="form" value="${ListCalendarForm}"/>

<tiles:put name="menu" type="string">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.add"/>
        <tiles:put name="commandHelpKey" value="button.add.help"/>
        <tiles:put name="bundle" value="calendarBundle"/>
        <tiles:put name="image" value=""/>
        <tiles:put name="action" value="/editcalendar.do"/>
        <tiles:put name="viewType" value="blueBorder"/>
    </tiles:insert>
</tiles:put>
<tiles:put name="data" type="string">
	<tiles:insert definition="tableTemplate" flush="false">
		<tiles:put name="id" value="calendarTab"/>
		<tiles:put name="buttons">
             <script type="text/javascript">
                var addUrl = "${phiz:calculateActionURL(pageContext,'/editcalendar')}";
                function doEdit()
                {
                    checkIfOneItem("selectedIds");
                    if (!checkOneSelection("selectedIds", "Выберите один календарь!"))
                        return;
                    var id = getRadioValue(document.getElementsByName("selectedIds"));
                    window.location = addUrl + "?id=" + id;
                }
			</script>
			 <tiles:insert definition="clientButton" flush="false">
				<tiles:put name="commandTextKey" value="button.edit"/>
				<tiles:put name="commandHelpKey" value="button.edit.help"/>
				<tiles:put name="bundle"  value="calendarBundle"/>
                <tiles:put name="onclick" value="doEdit();"/>
			</tiles:insert>
			<tiles:insert definition="commandButton" flush="false">
				<tiles:put name="commandKey" value="button.remove"/>
				<tiles:put name="commandHelpKey" value="button.remove.help"/>
				<tiles:put name="bundle" value="calendarBundle"/>
                <tiles:put name="confirmText" value="Вы действительно хотите удалить выбранные календари?"/>
				<tiles:put name="validationFunction" value="checkSelection('selectedIds', 'Выберите календари для удаления.');"/>
			</tiles:insert>
		</tiles:put>
		<tiles:put name="grid">
            <sl:collection model="list" id="listElement" property="data">
                <sl:collectionParam id="selectName" value="selectedIds"/>
                <sl:collectionParam id="selectType" value="checkbox"/>
                <sl:collectionParam id="selectProperty" value="id"/>

                <sl:collectionItem title="Имя" name="listElement" property="name">
                    <sl:collectionItemParam id="action" value="/editcalendar.do?id=${listElement.id}"/>
                </sl:collectionItem>
            </sl:collection>
		</tiles:put>	   
	    <tiles:put name="emptyMessage" value="Не найдено ни одного календаря!"/>
    </tiles:insert>
</tiles:put>
</tiles:insert>

</html:form>