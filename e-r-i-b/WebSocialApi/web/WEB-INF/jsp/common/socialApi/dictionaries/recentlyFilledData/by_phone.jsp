<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>

<tiles:importAttribute/>
<c:if test="${not empty data}">
    <c:forEach items="${data}" var="receiver">
        <receiver>
            <id>${receiver.id}</id>
            <fields>
                <field>
                    <type>phone</type>
                    <value><c:out value="${receiver.phone}"/></value>
                </field>
                <c:if test="${not empty receiver.cardNumber}">
                    <field>
                        <type>ourCard</type>
                        <value><c:out value="${phiz:getCutCardNumber(receiver.cardNumber)}"/></value>
                    </field>
                </c:if>
                <c:if test="${not empty receiver.fio}">
                    <field>
                        <type>surName</type>
                        <value><c:out value="${receiver.fio}"/></value>
                    </field>
                </c:if>
           </fields>
        </receiver>
    </c:forEach>
</c:if>

<c:if test="${not empty phones}">
    <c:forEach items="${data}" var="receiver">
        <receiver>
            <id>1</id>
            <fields>
                <field>
                    <type>phone</type>
                    <value><c:out value="${phiz:getCutPhoneNumber(receiver)}"/></value>
                </field>
           </fields>
        </receiver>
    </c:forEach>
</c:if>