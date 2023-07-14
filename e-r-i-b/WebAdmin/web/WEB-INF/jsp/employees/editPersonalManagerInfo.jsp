<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>


<html:form action="/employees/manager"  onsubmit="return setEmptyAction(event);">
    <c:set var="form" scope="request" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="employee" scope="request" value="${form.employee}"/>
    <c:set var="isSelfEditing" value="${not phiz:impliesService('PersonalManagerInformationManagement')}"/>

    <tiles:insert definition="employeesEdit">
        <tiles:put name="submenu" type="string" value="PersonalManager"/>
        <c:if test="${isSelfEditing}">
            <tiles:put name="leftMenu" value=""/>
            <tiles:put name="additionalInfoBlock" value=""/>
        </c:if>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">               
                <tiles:put name="name">
                    <c:choose>
                        <c:when test="${isSelfEditing}"><bean:message key="edit.self.manager.title" bundle="employeesBundle"/></c:when>
                        <c:otherwise><bean:message key="edit.manager.title" bundle="employeesBundle"/></c:otherwise>
                    </c:choose>
                </tiles:put>
                <tiles:put name="description"><bean:message key="edit.manager.description" bundle="employeesBundle"/></tiles:put>
                <tiles:put name="data">
                    <c:if test="${isSelfEditing}">
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title"><bean:message key="label.manager.fio" bundle="employeesBundle"/></tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <span>${employee.surName}&nbsp;${employee.firstName}&nbsp;${employee.patrName}&nbsp;</span>
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                        </tiles:insert>
                    </c:if>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title"><bean:message key="label.managerId" bundle="employeesBundle"/></tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text  property="fields(managerId)" maxlength="14" readonly="${isSelfEditing}"/>
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                    </tiles:insert>

                    <c:if test="${isSelfEditing}">
                        <c:set var="department" value="${phiz:getCurrentDepartment()}"/>
                        <c:set var="osb" value="${phiz:getOSB(department)}"/>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title"><bean:message key="label.manager.osb" bundle="employeesBundle"/></tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <c:choose>
                                    <c:when test="${not empty osb}">
                                        ${osb.name}
                                    </c:when>
                                    <c:otherwise>
                                        —
                                    </c:otherwise>
                                </c:choose>
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                        </tiles:insert>
                        <tiles:insert definition="simpleFormRow" flush="false">
                            <tiles:put name="title"><bean:message key="label.manager.vsp" bundle="employeesBundle"/></tiles:put>
                            <tiles:put name="isNecessary" value="true"/>
                            <tiles:put name="data">
                                <c:choose>
                                    <c:when test="${phiz:isVSPOffice(department)}">
                                        ${department.name}
                                    </c:when>
                                    <c:otherwise>
                                        —
                                    </c:otherwise>
                                </c:choose>
                            </tiles:put>
                            <tiles:put name="needMargin" value="true"/>
                        </tiles:insert>
                    </c:if>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title"><bean:message key="label.channel.name" bundle="employeesBundle"/></tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:select property="fields(managerChannel)" styleId="channelSelect" styleClass="select">
                                <c:forEach items="${form.channels}" var="channel">
                                    <c:set var="channelName"><c:out value="${channel.name}"/></c:set>
                                    <html:option value="${channelName}">${channelName}</html:option>
                                </c:forEach>
                            </html:select>
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title"><bean:message key="label.manager.phone" bundle="employeesBundle"/></tiles:put>
                        <tiles:put name="isNecessary" value="true"/>
                        <tiles:put name="data">
                            <html:text  property="fields(emplPhone)" maxlength="20"/>
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title"><bean:message key="label.manager.eMail" bundle="employeesBundle"/></tiles:put>
                        <tiles:put name="isNecessary" value="${isSelfEditing}"/>
                        <tiles:put name="data">
                            <html:text  property="fields(emplEMail)" maxlength="36"/>
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title"><bean:message key="label.lead.eMail" bundle="employeesBundle"/></tiles:put>
                        <tiles:put name="isNecessary" value="${isSelfEditing}"/>
                        <tiles:put name="data">
                            <html:text  property="fields(emplLeadEMail)" maxlength="36"/>
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                    </tiles:insert>

                </tiles:put>
                <tiles:put name="buttons">
                    
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle"  value="pfpConfigureBundle"/>
                        <tiles:put name="onclick" value="javascript:resetForm(event)"/>
                    </tiles:insert>

                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"  value="commonBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="alignTable" value="center"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>