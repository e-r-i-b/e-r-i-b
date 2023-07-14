<%--
  Created by IntelliJ IDEA.
  User: kligina
  Date: 06.05.2010
  Time: 16:35:25
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

<html:form action="/erkc/mail/sentList">
    <tiles:insert definition="erkcMain">

        <tiles:put name="pageTitle" type="string">
            <bean:message key="list.title" bundle="mailBundle"/>.&nbsp;<bean:message key="sentList.title" bundle="mailBundle"/>
        </tiles:put>

        <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

        <tiles:put name="filter" type="string">
            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.surName"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="surName"/>
                <tiles:put name="maxlength" value="42"/>
                <tiles:put name="editable" value="false"/>
                <tiles:put name="needClearing" value="false"/>
	        </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.firstName"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="firstName"/>
                <tiles:put name="maxlength" value="42"/>
                <tiles:put name="editable" value="false"/>
                <tiles:put name="needClearing" value="false"/>
            </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.patrName"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="patrName"/>
                <tiles:put name="maxlength" value="42"/>
                <tiles:put name="editable" value="false"/>
                <tiles:put name="needClearing" value="false"/>
            </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.surNameEmpl"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="surNameEmpl"/>
                <tiles:put name="maxlength" value="42"/>
	        </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.firstNameEmpl"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="firstNameEmpl"/>
                <tiles:put name="maxlength" value="42"/>
            </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.patrNameEmpl"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="patrNameEmpl"/>
                <tiles:put name="maxlength" value="42"/>
            </tiles:insert>

             <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.login"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="login"/>
                <tiles:put name="maxlength" value="22"/>
                <tiles:put name="size"      value="25"/>
	        </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.subject"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="name" value="subject"/>
                <tiles:put name="maxlength" value="40"/>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.recipientType"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="data">
                    <html:select property="filter(recipientType)" styleClass="select">
                        <html:option value=""><bean:message key="label.All" bundle="mailBundle"/></html:option>
                        <html:option value="P"><bean:message key="client" bundle="mailBundle"/></html:option>
                        <html:option value="G"><bean:message key="label.group" bundle="mailBundle"/></html:option>
                    </html:select>
                </tiles:put>
            </tiles:insert>

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.groupName"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="mandatory" value="false"/>
                <tiles:put name="name" value="groupName"/>
                <tiles:put name="maxlength" value="128"/>
            </tiles:insert>

            <tiles:insert definition="filterDataSpan" flush="false">
                <tiles:put name="label" value="label.date"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="name" value="Date"/>
                <tiles:put name="template" value="DATE_TEMPLATE"/>
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

            <tiles:insert definition="filterTextField" flush="false">
                <tiles:put name="label" value="label.num"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="name" value="num"/>
            </tiles:insert>

            <tiles:insert definition="filterEntryField" flush="false">
                  <tiles:put name="label" value="label.person.TB"/>
                  <tiles:put name="bundle" value="mailBundle"/>
                  <tiles:put name="data">
                      <html:select property="filters(user_TB)" styleClass="select">
                          <html:option value=""><bean:message key="label.All" bundle="mailBundle"/></html:option>
                          <c:forEach items="${phiz:getAllowedTB()}" var="department">
                              <html:option value="${department.id}">${department.name}</html:option>
                          </c:forEach>
                      </html:select>
                 </tiles:put>
            </tiles:insert>
            <tiles:insert definition="filterEntryField" flush="false">
                <tiles:put name="label" value="label.employee.area"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="data">
                    <html:select property="filters(area_id)" styleClass="select">
                        <html:option value=""><bean:message key="label.All" bundle="mailBundle"/></html:option>
                        <c:forEach items="${phiz:getAllowedArea()}" var="area">
                            <html:option value="${area.id}">${area.name}</html:option>
                        </c:forEach>
                    </html:select>
                </tiles:put>
            </tiles:insert>

        </tiles:put>

        <tiles:put name="data" type="string">
            <jsp:include page="/WEB-INF/jsp-sbrf/mail/sentListData.jsp"/>
        </tiles:put>
    </tiles:insert>
 </html:form>