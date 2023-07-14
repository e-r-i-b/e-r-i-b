<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
    <c:set var="form" value="${RecoverPasswordForm}"/>
        <tiles:insert definition="jSonResponseFail">
            <tiles:put name="errorType" value="redirect"/>
            <tiles:put name="redirect" value="${csa:calculateActionURL(pageContext, form.redirect)}"/>
        </tiles:insert>