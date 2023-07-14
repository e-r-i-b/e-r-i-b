<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles"%>

<c:set var="form" value="${AuthenticationFormBase}"/>
<script type="text/javascript">
    <%-- список соответсвия символа и кода. --%>
    var sym = new Array();
    <c:forEach items="${csa:captchaSymbols()}" var="ch" varStatus="chInfo"><%
        %>sym["${ch}"]=${chInfo.count - 1};<%
    %></c:forEach>

    <%-- Конвертация кода, чтобы не было проблем с кодировкой. --%>
    function convertCode(field)
    {
        var code = $(field).val().toLowerCase();
        var convertedCode = "";
        for (var i = 0; i < code.length; i++)
            convertedCode += code.charCodeAt(i) + '_';

        $(field).closest(".captcha-block").find("#captchaCode").val(convertedCode);
    }

    var authForm;
    <c:set var="showForm" value="${empty form.form ? 'login-form' : form.form}"/>
    $(document).ready(function(){
        authForm = new AuthForm(${form.fields.needTuringTest == null || form.fields.needTuringTest});
        authForm.showForm('<c:out value="${showForm}"/>', true);
    });
</script>
<tiles:useAttribute name="formItems"/>
<c:forEach var="formItem" items="${formItems}">
    <jsp:include page="${formItem}"/>
</c:forEach>
