<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"     prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"     prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"              prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"    prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags"                           prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout"   prefix="sl" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt"               prefix="fmt" %>

<html:form action="/addressBook/reports/requestsCount">
    <c:set var="form"       value="${phiz:currentForm(pageContext)}"/>
    <c:set var="fromStart"  value="${form.fromStart}"/>
    <tiles:insert definition="addressBookRequestsCountReports">
        <tiles:put name="filter" type="string">
            <c:set var="colCount" value="2" scope="request"/>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="mandatory"     value="false"/>
                <tiles:put name="label"         value="label.form.requestsCount.filter.empty"/>
                <tiles:put name="bundle"        value="addressBookReportsBundle"/>
                <tiles:put name="name"          value="count"/>
                <tiles:put name="data">
                    <span style="white-space:nowrap;">
                        <bean:message bundle="addressBookReportsBundle" key="label.form.requestsCount.filter.count"/>
                        <html:text property="filter(count)" size="10" maxlength="10"/>
                    </span>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label"     value="label.form.requestsCount.filter.empty"/>
                <tiles:put name="bundle"    value="addressBookReportsBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <span style="white-space:nowrap;">
                        <bean:message bundle="addressBookReportsBundle" key="label.form.requestsCount.filter.from"/>
                        <span style="font-weight:normal;overflow:visible;cursor:default;">
                            <input type="text"
                                   size="8" name="filter(fromDate)" class="dot-date-pick"
                                   maxsize="10"
                                   value="<bean:write name="form" property="filters.fromDate" format="dd.MM.yyyy"/>"/>
                        </span>
                        <bean:message bundle="addressBookReportsBundle" key="label.form.requestsCount.filter.to"/>
                        <span style="font-weight:normal;cursor:default;">
                            <input type="text"
                                   size="8" name="filter(toDate)" class="dot-date-pick"
                                   maxsize="10"
                                   value="<bean:write name="form" property="filters.toDate" format="dd.MM.yyyy"/>"/>
                        </span>
                    </span>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
        <tiles:put name="data" type="string">
            <c:choose>
                <c:when test="${fromStart}">
                    <script type="text/javascript">
                        doOnLoad(switchFilter, this);
                    </script>
                </c:when>
                <c:otherwise>
                    <tiles:insert definition="tableTemplate" flush="false">
                        <tiles:put name="text"><bean:message bundle="addressBookReportsBundle" key="label.form.requestsCount.list.title"/></tiles:put>
                        <tiles:put name="grid">
                            <sl:collection id="listElement" model="list" property="data" bundle="addressBookReportsBundle" styleClass="standartTable">
                                <sl:collectionItem title="label.form.requestsCount.list.login"      property="login"/>
                                <sl:collectionItem title="label.form.requestsCount.list.name"       property="name"/>
                                <sl:collectionItem title="label.form.requestsCount.list.document"   property="document"/>
                                <sl:collectionItem title="label.form.requestsCount.list.birthday">
                                    <fmt:formatDate value="${listElement.birthday.time}" pattern="dd.MM.yyyy"/>
                                </sl:collectionItem>
                                <sl:collectionItem title="label.form.requestsCount.list.count"      property="count"/>
                            </sl:collection>
                            <tiles:put name="isEmpty" value="${empty form.data}"/>
                            <tiles:put name="emptyMessage">
                                <bean:message bundle="addressBookReportsBundle" key="label.form.requestsCount.list.empty"/>
                            </tiles:put>
                        </tiles:put>
                    </tiles:insert>
                </c:otherwise>
            </c:choose>
        </tiles:put>
    </tiles:insert>
</html:form>
