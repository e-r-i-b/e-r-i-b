<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>


<% pageContext.getRequest().setAttribute("mode", "Services");%>
<% pageContext.getRequest().setAttribute("userMode", "LogSys");%>
<html:form action="/erkc/log/common">

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <tiles:insert definition="erkcMain">
        <tiles:put name="pageTitle" type="string">
            <bean:message key="label.comlog.page.name" bundle="logBundle"/>
        </tiles:put>

        <tiles:put name="filter" type="string">
            <tiles:put name="fastSearchFilter" value="true"/>
            <c:set var="colCount" value="2" scope="request"/>

            <input type="hidden" name="filter(sessionId)" value="${form.filters.sessionId}"/>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.client"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="fio"/>
                <tiles:put name="size"   value="50"/>
                <tiles:put name="maxlength"  value="255"/>
                <tiles:put name="isDefault" value="Фамилия Имя Отчество"/>
                <tiles:put name="editable" value="false"/>
                <tiles:put name="needClearing" value="false"/>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.period7"/>
                <tiles:put name="bundle" value="logBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <span style="white-space:nowrap;">
                        с&nbsp;
                        <span style="font-weight:normal;overflow:visible;cursor:default;">
                            <input type="text"
                                    size="10" name="filter(fromDate)" class="dot-date-pick"
                                    value="<bean:write name="form" property="filters.fromDate" format="dd.MM.yyyy"/>"/>
                            <input type="text"
                                    size="8" name="filter(fromTime)" class="time-template"
                                    value="<bean:write name="form" property="filters.fromTime" format="HH:mm:ss"/>"
                                    onkeydown="onTabClick(event,'filter(toDate)');"/>
                        </span>
                            &nbsp;по&nbsp;
                        <span style="font-weight:normal;cursor:default;">
                            <input type="text"
                                    size="10" name="filter(toDate)" class="dot-date-pick"
                                    value="<bean:write name="form" property="filters.toDate" format="dd.MM.yyyy"/>"/>

                            <input type="text"
                                    size="8" name="filter(toTime)" class="time-template"
                                    value="<bean:write name="form" property="filters.toTime" format="HH:mm:ss"/>"/>
                        </span>
                    </span>
                </tiles:put>
            </tiles:insert>


            <%-- row 2 --%>
            <tiles:insert definition="filter2TextField" flush="false">
                <tiles:put name="label" value="label.document"/>
                <tiles:put name="bundle" value="claimsBundle"/>
                <tiles:put name="name"   value="series"/>
                <tiles:put name="size"   value="5"/>
                <tiles:put name="maxlength"  value="16"/>
                <tiles:put name="isDefault" value="Серия"/>
                <tiles:put name="name2"   value="number"/>
                <tiles:put name="size2"   value="10"/>
                <tiles:put name="maxlength2"  value="16"/>
                <tiles:put name="default2" value="Номер"/>
                <tiles:put name="editable" value="false"/>
                <tiles:put name="editable2" value="false"/>
                <tiles:put name="needClearing" value="false"/>
                <tiles:put name="needClearing2" value="false"/>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.application"/>
                <tiles:put name="bundle" value="logBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <%@include file="/WEB-INF/jsp/log/applicationSelect.jsp"%>
                </tiles:put>
            </tiles:insert>

            <%-- row 3 --%>
            <tiles:insert definition="filterDateField" flush="false">
                <tiles:put name="label" value="label.birthDay"/>
                <tiles:put name="bundle" value="personsBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="birthday"/>
                <tiles:put name="editable" value="false"/>
                <tiles:put name="needClearing" value="false"/>
            </tiles:insert>

            <tiles:insert definition="filterEmptytField" flush="false">
            </tiles:insert>

            <%-- row 4 --%>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.loginId"/>
                <tiles:put name="bundle" value="logBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="loginId"/>
                <tiles:put name="isFastSearch" value="true"/>
                <tiles:put name="editable" value="false"/>
                <tiles:put name="needClearing" value="false"/>
            </tiles:insert>

           <tiles:insert definition="filterEntryField" flush="false">
                    <tiles:put name="label" value="label.log.type"/>
                    <tiles:put name="bundle" value="logBundle"/>
                    <tiles:put name="mandatory" value="false"/>
                    <tiles:put name="isFastSearch" value="true"/>
                    <tiles:put name="data">
                        <html:checkbox property="filter(showUserLog)" styleClass="logJournal"/>
                        <bean:message key="label.showLogEntries.user" bundle="logBundle"/>

                    </tiles:put>
            </tiles:insert>

            <%-- row 5 --%>
            <html:hidden  property="filter(departmentId)"/>
            <tiles:insert definition="filterTextField" flush="false">
                 <tiles:put name="label" value="label.department.name"/>
                 <tiles:put name="bundle" value="logBundle"/>
                 <tiles:put name="isFastSearch" value="true"/>
                 <tiles:put name="size" value="50"/>
                 <tiles:put name="name" value="departmentName"/>
                 <tiles:put name="editable" value="false"/>
                 <tiles:put name="needClearing" value="false"/>
             </tiles:insert>

           <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.empty"/>
                <tiles:put name="bundle" value="claimsBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="isFastSearch" value="true"/>
                <tiles:put name="data">
                           <html:checkbox property="filter(showSystemLog)" styleClass="logJournal"/>
                           <bean:message key="label.showLogEntries.system" bundle="logBundle"/>
                           <br/>
                           <html:checkbox property="filter(showMessageLog)" styleClass="logJournal"/>
                           <bean:message key="label.showLogEntries.message" bundle="logBundle"/>
                </tiles:put>
           </tiles:insert>
            <script type="text/javascript">
                $(document).ready(function(){$('input[name="filter(birthday)"]').next().remove();});
                addClearMasks(null,
                    function(event)
                    {
                        clearInputTemplate('filter(fromDate)','__.__.____');
                        clearInputTemplate('filter(toDate)','__.__.____');
                        clearInputTemplate('filter(fromTime)','__:__:__');
                        clearInputTemplate('filter(toTime)','__:__:__');
                    });
            </script>
        </tiles:put>
        <tiles:put name="data" type="string">
            <jsp:include page="/WEB-INF/jsp-sbrf/log/common/commonLogData.jsp" flush="false"/>
        </tiles:put>
    </tiles:insert>
</html:form>
