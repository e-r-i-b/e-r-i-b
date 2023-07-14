<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"     prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"    prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"     prefix="bean" %>
<%@ taglib uri="http://struts.application-servers.com/layout"   prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"               prefix="fmt" %>
<%@ taglib uri="http://rssl.com/tags"                           prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"              prefix="c" %>

<html:form action="/addressBook/reports/syncCountExceed">
    <c:set var="form"       value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="addressBookSyncCountExceedReports">
        <tiles:put name="filter" type="string">
            <tiles:insert definition="filterDateField" flush="false">
                <tiles:put name="label" value="label.form.syncCountExceed.filter.fromDate"/>
                <tiles:put name="bundle" value="addressBookReportsBundle"/>
                <tiles:put name="mandatory" value="true"/>
                <tiles:put name="name" value="fromDate"/>
            </tiles:insert>

            <tiles:insert definition="filterDateField" flush="false">
                <tiles:put name="label" value="label.form.syncCountExceed.filter.toDate"/>
                <tiles:put name="bundle" value="addressBookReportsBundle"/>
                <tiles:put name="mandatory" value="true"/>
                <tiles:put name="name" value="toDate"/>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <tiles:insert definition="tableTemplate" flush="false">
                <tiles:put name="text"><bean:message bundle="addressBookReportsBundle" key="label.form.syncCountExceed.list.title"/></tiles:put>
                <tiles:put name="grid">
                    <sl:collection id="listElement" model="list" property="data" bundle="addressBookReportsBundle" styleClass="standartTable">
                        <sl:collectionItem title="label.form.syncCountExceed.list.login"      property="loginId"/>
                        <sl:collectionItem title="label.form.syncCountExceed.list.name"       property="name"/>
                        <sl:collectionItem title="label.form.syncCountExceed.list.document"   property="document"/>
                        <sl:collectionItem title="label.form.syncCountExceed.list.birthday">
                            <fmt:formatDate value="${listElement.birthDay.time}" pattern="dd.MM.yyyy"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.form.syncCountExceed.list.date">
                            <fmt:formatDate value="${listElement.syncDate.time}" pattern="dd.MM.yyyy HH:mm"/>
                        </sl:collectionItem>
                        <sl:collectionItem title="label.form.syncCountExceed.list.message"    property="message"/>
                    </sl:collection>
                    <tiles:put name="isEmpty" value="${empty form.data}"/>
                    <tiles:put name="emptyMessage">
                        <bean:message bundle="addressBookReportsBundle" key="label.form.syncCountExceed.list.empty"/>
                    </tiles:put>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>