<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<html:form action="/private/dictionary/addressbook/phoneMaxCountPayList">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="iphone" flush="false">
        <c:if test="${not empty form.phoneMaxCountPayPairList}">
            <tiles:put name="data">
                <phoneNumbers>
                  <c:forEach items="${form.phoneMaxCountPayPairList}" var="contactInfo">
                    <%--Признак своего телефона--%>
                    <c:set var="isSelfPfone" value="${contactInfo.first}"/>
                    <%--Информация по контакту--%>
                    <c:set var="contact" value="${contactInfo.second}"/>
                    <phone>
                        <id>${contact.id}</id>
                        <fields>
                            <field>
                                <c:set var="phoneNumber" value="${contact.phone}"/>
                                <c:choose>
                                    <c:when test="${isSelfPfone}">
                                        <type>selfPhone</type>
                                        <c:set var="phoneNumber" value="${phiz:getCutPhoneIfOur(contact.phone)}"/>
                                    </c:when>
                                    <c:otherwise>
                                        <type>phone</type>
                                    </c:otherwise>
                                </c:choose>
                                <value><c:out value="${phoneNumber}"/></value>
                            </field>
                            <field>
                                <type>fullName</type>
                                <value><c:out value="${contact.fullName}"/></value>
                            </field>
                            <field>
                                <type>frequencyPay</type>
                                <value>${contact.frequencyPay}</value>
                            </field>
                        </fields>
                    </phone>
                  </c:forEach>
                </phoneNumbers>
            </tiles:put>
        </c:if>
    </tiles:insert>
</html:form>
