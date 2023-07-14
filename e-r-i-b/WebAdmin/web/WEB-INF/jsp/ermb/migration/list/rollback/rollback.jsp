<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>


<html:form action="/ermb/migration/rollback">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:importAttribute/>

    <tiles:insert definition="migrationMain">
        <tiles:put name="submenu" type="string" value="Rollback"/>
        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message key="migration.rollback.title" bundle="migrationBundle"/>
                </tiles:put>

                <tiles:put name="data">
                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.rollback.date" bundle="migrationBundle"/>
                        </tiles:put>
                        <tiles:put name="data">
                            <input type="text"
                                   size="10" name="field(date)" class="dot-date-pick"
                                   value="<bean:write name="form" property="field(date)" format="dd.MM.yyyy"/>"/>
                            <input type="text"
                                   size="8" name="field(time)" class="time-template"
                                   value="<bean:write name="form" property="field(time)" format="HH:mm:ss"/>"/>
                        </tiles:put>
                    </tiles:insert>
                </tiles:put>

                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.rollback"/>
                        <tiles:put name="commandHelpKey" value="button.rollback.help"/>
                        <tiles:put name="bundle" value="migrationBundle"/>
                    </tiles:insert>
                </tiles:put>

            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
