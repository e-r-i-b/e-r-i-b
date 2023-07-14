<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>


<html:form action="/ermb/migration/migration">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:importAttribute/>

    <tiles:insert definition="migrationMain">
        <tiles:put name="submenu" type="string" value="Migration"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="migration.title" bundle="migrationBundle"/>
        </tiles:put>

        <tiles:put name="data" type="string">
            <script type="text/javascript">
                function validateSelection()
                {
                    return checkSelection('selectedData', 'Выберите сегмент для миграции');
                }
            </script>
            <tiles:insert definition="gridTableTemplate" flush="false">
                <tiles:put name="id" value="segments"/>
                <tiles:put name="data">
                    <c:forEach var="segment" items="${form.data}">
                        <tr>
                            <th width="20px">
                                <input type="checkbox" name="selectedData" value="${segment.value}"/>
                            </th>
                            <th>
                                <c:out value="${segment.value}"/>
                            </th>
                        </tr>
                    </c:forEach>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="commandButton" flush="false">
                <tiles:put name="commandKey" value="button.migration"/>
                <tiles:put name="commandHelpKey" value="button.migration.help"/>
                <tiles:put name="bundle" value="migrationBundle"/>
                <tiles:put name="validationFunction" value="validateSelection()"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
