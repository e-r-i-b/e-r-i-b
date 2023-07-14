<%--
  Created by IntelliJ IDEA.
  User: kligina
  Date: 06.05.2010
  Time: 16:34:26
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/mail/list">
    <tiles:insert definition="mailMain">
        <tiles:put name="submenu" type="string" value="MailList"/>
        <tiles:put name="pageTitle" type="string">
            <bean:message key="list.title" bundle="mailBundle"/>
        </tiles:put>

        <tiles:put name="menu" type="string">
            <tiles:insert definition="commandButton" flush="false" operation="EditMailOperation">
                <tiles:put name="commandKey" value="button.add"/>
                <tiles:put name="commandHelpKey" value="button.add.help"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="viewType" value="blueBorder"/>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="filter" type="string">

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.statusNew"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="data">
                    <html:checkbox property="filter(showNew)"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.statusReceived"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="data">
                    <html:checkbox property="filter(showReceived)"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.statusDraft"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="data">
                    <html:checkbox property="filter(showDraft)"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.statusAnswer"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="data">
                    <html:checkbox property="filter(showAnswer)"/>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.num"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="name" value="num"/>
	        </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.subject"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="name" value="subject"/>
                <tiles:put name="maxlength" value="40"/>
            </tiles:insert>

             <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.mailType"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="data">
                    <html:select property="filter(type)" styleClass="select">
                        <html:option value=""><bean:message key="label.All" bundle="mailBundle"/></html:option>
                        <html:option value="CONSULTATION"><bean:message key="mailType.CONSULTATION" bundle="mailBundle"/></html:option>
                        <html:option value="COMPLAINT"><bean:message key="mailType.COMPLAINT" bundle="mailBundle"/></html:option>
                        <html:option value="CLAIM"><bean:message key="mailType.CLAIM" bundle="mailBundle"/></html:option>
                        <html:option value="GRATITUDE"><bean:message key="mailType.GRATITUDE" bundle="mailBundle"/></html:option>
                        <html:option value="IMPROVE"><bean:message key="mailType.IMPROVE" bundle="mailBundle"/></html:option>
                        <html:option value="OTHER"><bean:message key="mailType.OTHER" bundle="mailBundle"/></html:option>
			        </html:select>
		        </tiles:put>
	        </tiles:insert>
            <tiles:insert definition="filterDataSpan" flush="false">
                <tiles:put name="label" value="label.date"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="Date"/>
                <tiles:put name="template" value="DATE_TEMPLATE"/>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.attached"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="data">
                    <html:select property="filter(isAttach)" styleClass="select">
                        <html:option value=""><bean:message key="label.All" bundle="mailBundle"/></html:option>
                        <html:option value="not null"><bean:message key="label.yes" bundle="mailBundle"/></html:option>
                        <html:option value="null"><bean:message key="label.no" bundle="mailBundle"/></html:option>
			        </html:select>
		        </tiles:put>
	        </tiles:insert>
            <%-- ФИО Клиента --%>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.fio"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="fio"/>
                <tiles:put name="maxlength" value="140"/>
                <tiles:put name="size" value="40"/>
	        </tiles:insert>
            <%-- ФИО Сотрудника --%>
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.fioEmpl"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="fioEmpl"/>
                <tiles:put name="maxlength" value="140"/>
                <tiles:put name="size" value="40"/>
	        </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.login"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="employeeLogin"/>
                <tiles:put name="maxlength" value="50"/>
                <tiles:put name="size"      value="22"/>
	        </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="response_method"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="data">
                    <html:select property="filter(response_method)" styleClass="select">
                        <html:option value=""><bean:message key="label.All" bundle="mailBundle"/></html:option>
                        <html:option value="BY_PHONE"><bean:message key="method.by_phone" bundle="mailBundle"/></html:option>
                        <html:option value="IN_WRITING"><bean:message key="method.in_writing" bundle="mailBundle"/></html:option>
			        </html:select>
		        </tiles:put>
	        </tiles:insert>
            <tiles:insert definition="filterEntryField" flush="false" >
                <tiles:put name="label" value="label.mail.theme"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="data">
                    <html:select property="filters(theme)" styleClass="select">
                        <html:option value=""><bean:message key="label.All" bundle="mailBundle"/></html:option>
                        <c:forEach items="${phiz:getAllMailSubjects()}" var="theme">
                            <html:option value="${theme.id}">${theme.description}</html:option>
                        </c:forEach>
			        </html:select>
		        </tiles:put>
	        </tiles:insert>
            <tiles:insert definition="filterEntryField" flush="false">
                   <tiles:put name="label" value="label.person.TB"/>
                   <tiles:put name="bundle" value="mailBundle"/>
                   <tiles:put name="data">
                       <html:select property="filters(user_TB)" styleClass="select">
                           <html:option value=""><bean:message key="label.All" bundle="mailBundle"/></html:option>
                           <c:forEach items="${phiz:getAllowedTB()}" var="department">
                               <html:option value="${department.region}">${department.name}</html:option>
                           </c:forEach>
                   </html:select>
                </tiles:put>
            </tiles:insert>
            <tiles:insert definition="filterEntryField" flush="false">
                   <tiles:put name="label" value="label.employee.area"/>
                   <tiles:put name="bundle" value="mailBundle"/>
                   <tiles:put name="data">
                       <html:select property="filters(area_uuid)" styleClass="select">
                           <html:option value=""><bean:message key="label.All" bundle="mailBundle"/></html:option>
                           <c:forEach items="${phiz:getAllowedArea()}" var="area">
                               <html:option value="${area.uuid}">${area.name}</html:option>
                           </c:forEach>
                   </html:select>
                </tiles:put>
            </tiles:insert>
        </tiles:put>

        <tiles:put name="data" type="string">
            <jsp:include page="/WEB-INF/jsp-sbrf/mail/listData.jsp"/>
        </tiles:put>
    </tiles:insert>
 </html:form>