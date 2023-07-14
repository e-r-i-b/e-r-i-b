<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:insert definition="filterDataPeriod" flush="false">
    <tiles:put name="name" value="Date"/>
    <tiles:put name="title" value="Показать письма"/>
    <tiles:put name="buttonKey" value="button.filter"/>
    <tiles:put name="buttonBundle" value="mailBundle"/>
    <tiles:put name="needErrorValidate" value="false"/>
    <tiles:put name="week" value="false"/>
    <tiles:put name="month" value="false"/>
</tiles:insert>
<tiles:insert definition="filter" flush="false">
    <tiles:put name="hiddenData">
    <script type="text/javascript">
        $(document).ready(function()
        {
            oneClickFilterDateChange ('Date', 'period', false);
        });
    </script>
    <%--Фильтр--%>
    <div class="feedback-filter">
        <fieldset>
            <div class="search-parameter">
                <span class="prompt"><bean:message key="label.search.subject" bundle="mailBundle"/>:</span>
                <input type="text" value="${searchText}" class="customPlaceholder feedback-search-string"
                       name="filter(subject)" maxlength="40" title="введите тему письма"/>
            </div>
            <div class="search-parameter">
                <span class="prompt"><bean:message key="label.search.num" bundle="mailBundle"/>:</span>
                <html:text property="filter(num)" styleClass="documentNumber" size="10" />
            </div>
            <div class="search-parameter">
                <c:if test="${type}">
                    <span class="prompt"><bean:message key="label.search.type" bundle="mailBundle"/>:</span>
                    <html:select property="filter(type)" styleClass="select" style="font-style: italic;">
                        <html:option value=""><bean:message key="label.choice" bundle="mailBundle"/></html:option>
                        <html:option value="CONSULTATION"><bean:message key="mailType.CONSULTATION" bundle="mailBundle"/></html:option>
                        <html:option value="COMPLAINT"><bean:message key="mailType.COMPLAINT" bundle="mailBundle"/></html:option>
                        <html:option value="CLAIM"><bean:message key="mailType.CLAIM" bundle="mailBundle"/></html:option>
                        <html:option value="GRATITUDE"><bean:message key="mailType.GRATITUDE" bundle="mailBundle"/></html:option>
                        <html:option value="IMPROVE"><bean:message key="mailType.IMPROVE" bundle="mailBundle"/></html:option>
                        <html:option value="OTHER"><bean:message key="mailType.OTHER" bundle="mailBundle"/></html:option>
                    </html:select>
                </c:if>
                <c:if test="${state}">
                    <span class="prompt"><bean:message key="label.search.state" bundle="mailBundle"/>:</span>
                    <html:select property="filter(state)" styleClass="select">
                        <html:option value=""><bean:message key="label.All" bundle="mailBundle"/></html:option>
                        <html:option value="READ"><bean:message key="label.statusReceived" bundle="mailBundle"/></html:option>
                        <html:option value="NEW"><bean:message key="label.statusNew" bundle="mailBundle"/></html:option>
                        <html:option value="ANSWER"><bean:message key="label.statusAnswer" bundle="mailBundle"/></html:option>
                        <html:option value="DRAFT"><bean:message key="label.statusDraft" bundle="mailBundle"/></html:option>
                    </html:select>
                </c:if>
                <c:if test="${direction}">
                    <span class="prompt"><bean:message key="label.search.type" bundle="mailBundle"/>:</span>
                    <html:select property="filter(mailType)" styleClass="select">
                        <html:option value=""><bean:message key="label.All" bundle="mailBundle"/></html:option>
                        <html:option value="CLIENT"><bean:message key="directionC" bundle="mailBundle"/></html:option>
                        <html:option value="ADMIN"><bean:message key="directionA" bundle="mailBundle"/></html:option>
                    </html:select>
                </c:if>
            </div>
            <div class="search-parameter">
                <div class="search-parameter-margin">
                    <html:checkbox property="filter(isAttach)" styleClass="check"/>
                    <span><bean:message key="label.attached" bundle="mailBundle"/></span>
                </div>
            </div>
        </fieldset>
        <div class="clear"></div>
    </div>
    </tiles:put>
    <c:set var="curType"><bean:write name="org.apache.struts.taglib.html.BEAN"
                                property="filter(typeDate)"/></c:set>
    <tiles:put name="hideFilterButton" value="true"/>
    <tiles:put name="buttonKey" value="button.filter"/>
    <tiles:put name="buttonBundle" value="mailBundle"/>
    <tiles:put name="showClearButton" value="true"/>
</tiles:insert>