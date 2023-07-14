<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/monitoring/view" onsubmit="return setEmptyAction(event);">
    <c:set var="frm" value="${ViewMonitoringReportsForm}"/>

<tiles:insert definition="reports">
	<tiles:put name="submenu" type="string" value="Monitoring"/>

    <tiles:put name="data" type="string">
        <tiles:insert definition="paymentForm" flush="false">            
		    <tiles:put name="name" value="ѕросмотр данных мониторинга"/>
            <tiles:put name="data" type="string">
            <tiles:insert definition="simpleFormRow" flush="false">
                <tiles:put name="title">
                    ѕодразделение :
                </tiles:put>
                <tiles:put name="data">
                    <c:out value="${phiz:getDepartmentById(frm.departmentId).name}"/>
                    <input  type="button" class="buttWhite smButt" onclick="openAllowedTBDictionary(setDepartmentInfo);" value="..."/>
                </tiles:put>
            </tiles:insert>

            <c:forEach var="listElement" items="${frm.thresholdValueses}">
                <c:set var="color" value=""/>
                <c:set var="reportValue" value="${frm.monitoringReportValues[listElement.report]}"/>
                <c:if test="${listElement.warningThreshold != 0 and reportValue > listElement.warningThreshold}">
                    <c:set var="color" value="yellow"/>
                </c:if>
                <c:if test="${listElement.errorThreshold != 0 and reportValue > listElement.errorThreshold}">
                    <c:set var="color" value="red"/>
                </c:if>
                <tiles:insert definition="simpleFormRow" flush="false">
                    <tiles:put name="data">
                        <div style="background-color: ${color};">
                            <c:choose>
                                <c:when test="${(listElement.report eq 'DISPATCH_DOCUMENT_COUNT' or listElement.report eq 'DELAYED_DOCUMENT_COUNT') and reportValue > 0}">
                                    <phiz:link action="/monitoring/documents" serviceId="Monitoring">
                                        <phiz:param name="report" value="${listElement.report}"/>
                                        <phiz:param name="departmentId" value="${frm.departmentId}"/>
                                        ${listElement.report.description}
                                    </phiz:link>
                                </c:when>
                                <c:otherwise>
                                    ${listElement.report.description}
                                </c:otherwise>
                            </c:choose>
                            <div style="float: right;">${reportValue}</div>
                        </div>
                    </tiles:put>
                </tiles:insert>
            </c:forEach>

            </tiles:put>
            <tiles:put name="buttons">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey"     value="button.update"/>
                    <tiles:put name="commandHelpKey" value="button.update"/>
                    <tiles:put name="bundle"  value="commonBundle"/>
                    <tiles:put name="action" value="/monitoring/view.do?departmentId=${frm.departmentId}"/>
                </tiles:insert>
	       </tiles:put>
	       <tiles:put name="alignTable" value="center"/>
        </tiles:insert>

    </tiles:put>

</tiles:insert>
    <script type="text/javascript">
        
        function openAllowedTBDictionary(callback)
        {
            window.setDepartmentInfo = callback;
            win = window.open(document.webRoot+'/dictionaries/allowedTerbanks.do?type=oneSelection',
                           'Departments', "resizable=1,menubar=0,toolbar=0,scrollbars=1,width=" + (getClientWidth - 100) + "px,height=" + (getClientHeight - 100) + "px, left=50px, top=50px");
        }

        function setDepartmentInfo(resource)
        {
            window.location = document.webRoot + '/monitoring/view.do?departmentId=' + resource['id'];
        }

        function autoRefresh()
        {
            //обновл€ем страницу каждые 10 минут
            window.setTimeout("window.location.reload();", 10 * 60 * 1000);
        }
        doOnLoad(autoRefresh);

    </script>
</html:form>
