<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute name="mainmenu" ignore="true"/>

<c:set var="mode" scope="request" value="${mainmenu}"/>

<phiz:bookmark action="/private/accounts" moduleId="Info">����� � �����</phiz:bookmark>
<phiz:bookmark action="/private/payments" moduleId="Payments">��������</phiz:bookmark>
<phiz:bookmark action="/private/deposits" moduleId="Deposits">������</phiz:bookmark>
<phiz:bookmark action="/private/loans/products/list" moduleId="Loans">�������</phiz:bookmark>
<phiz:bookmark action="/private/claims" moduleId="ClientClaims">������</phiz:bookmark>
<phiz:bookmark action="/private/service"  moduleId="ServicesClient">������</phiz:bookmark>
<phiz:bookmark action="/private/mail/list" serviceId="ClientMailManagement">�����</phiz:bookmark>

