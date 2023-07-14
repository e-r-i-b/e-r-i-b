<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<c:set var="id" value="${ListPaymentServicesForm.id}"/>
<c:set var="paymentService" value="${phiz:getPaymentServiceById(id)}"/>
<c:set var="childrens" value="${phiz:getPaymentServiceChildren(paymentService)}"/>

var child;
var childrensArray = new Array();

<logic:iterate id="children" name="childrens">
        child                   = new Object();
        child.id                = '${children.id}';
        child.name              = '${children.name}';
        child.code              = '${children.code}';
        childrensArray[childrensArray.length]   = child;
</logic:iterate>
parent.addRow(${id}, childrensArray);
