<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/monitoring/thresholdvalues/edit" onsubmit="return setEmptyAction(event);">
    <c:set var="frm" value="${EditMonitoringThresholdValuesForm}"/>


<tiles:insert definition="departmentsEdit">
	<tiles:put name="submenu" type="string" value="MonitoringThreshold"/>

    <tiles:put name="data" type="string">
        <tiles:insert definition="paymentForm" flush="false">
            <tiles:put name="id" value="editDepartments"/>
		    <tiles:put name="name" value="Настройка параметров мониторинга"/>
		    <tiles:put name="description" value="Используйте данную форму для настройки параметров мониторинга."/>
            <tiles:put name="data" type="string">
             <table>
                <tr>
                    <td>
                        Параметр
                    </td>
                    <td align="right">
                        Порог предупреждения
                    </td>
                    <td align="right">
                        Порог ошибки
                    </td>
                </tr>
                <c:forEach var="listElement" items="${frm.values}">
                    <tr>
                        <td nowrap="true">
                            ${listElement.report.description}
                        </td>
                        <td nowrap="true" align="right">
                            <html:text property="field(${listElement.report}warningThreshold)" size="5" maxlength="6"/>
                        </td>
                        <td nowrap="true" align="right">
                            <html:text property="field(${listElement.report}errorThreshold)" size="5" maxlength="6"/>
                        </td>
                    </tr>
                </c:forEach>
             </table>
            </tiles:put>
            <tiles:put name="buttons">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey"     value="button.save"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="bundle"  value="employeesBundle"/>
                    <tiles:put name="postbackNavigation" value="true"/>
                </tiles:insert>
	       </tiles:put>
	       <tiles:put name="alignTable" value="center"/>
        </tiles:insert>

    </tiles:put>

</tiles:insert>
</html:form>
