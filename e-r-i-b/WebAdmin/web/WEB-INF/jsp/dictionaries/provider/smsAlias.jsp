<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>


<html:form action="/dictionaries/provider/sms/alias" onsubmit="return setAction();">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:importAttribute/>
    <tiles:insert definition="empty">
        <tiles:put name="data" type="string">

            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="name">
                    <bean:message bundle="providerBundle" key="label.sms.alias.fields"/>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey"     value="button.save"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="bundle"  value="commonBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="action" value="/dictionaries/provider/sms/alias.do"/>
                    </tiles:insert>
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey"     value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle"  value="commonBundle"/>
                        <tiles:put name="action" value="/dictionaries/provider/sms/alias.do?id=${form.id}"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="data" type="string">
                    <input type="hidden" name="id" value="${form.id}"/>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.sms.alias.provider" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <c:out value="${form.alias.serviceProvider.name}"/>
                        </tiles:put>
                    </tiles:insert>

                    <tiles:insert definition="simpleFormRow" flush="false">
                        <tiles:put name="title">
                            <bean:message key="label.sms.alias" bundle="providerBundle"/>
                        </tiles:put>
                        <tiles:put name="needMargin" value="true"/>
                        <tiles:put name="data">
                            <c:out value="${form.alias.name}"/>
                        </tiles:put>
                    </tiles:insert>
                    <tiles:insert definition="simpleTableTemplate" flush="false">
                        <tiles:put name="grid">
                            <sl:collection id="field"
                                           property="alias.smsAliaseFields"
                                           model="list"
                                           bundle="providerBundle"
                                           indexId="index">

                                <c:set var="fd" value="${field.field}"/>
                                <sl:collectionItem title="label.sms.alias.fields" value="${fd.name}"/>

                                <sl:collectionItem title="label.sms.alias.editable">
                                    <c:choose>
                                        <c:when test="${field.editable}">
                                            <input type="checkbox" value="${field.id}" checked="true" name="aliasFieldEditable"/>
                                        </c:when>
                                        <c:otherwise>
                                            <input type="checkbox" value="${field.id}" name="aliasFieldEditable"/>
                                        </c:otherwise>
                                    </c:choose>
                                </sl:collectionItem>

                                <sl:collectionItem title="label.sms.alias.value">
                                    <c:choose>
                                        <c:when test="${fd.type eq 'set' or fd.type eq 'list'}">
                                            <select name="field(${fd.externalId})">
                                                <c:forEach items="${fd.listValues}" var="option">
                                                    <c:choose>
                                                        <c:when test="${option eq field.value}">
                                                            <option value="${option}" selected>${option}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="${option}">${option}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>
                                        </c:when>
                                        <c:otherwise>
                                            <input value="${field.value}" name="field(${fd.externalId})"/>
                                        </c:otherwise>
                                    </c:choose>
                                </sl:collectionItem>
                            </sl:collection>
                        </tiles:put>
                        <tiles:put name="isEmpty" value="${empty form.alias.smsAliaseFields}"/>
                        <tiles:put name="emptyMessage"
                                   value="У поставщика услуг нет полей"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>
