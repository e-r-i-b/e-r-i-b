<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="csa" uri="http://rssl.com/tags/csa" %>

<tiles:insert page="templates/finish-reg-template.jsp" flush="false">
    <tiles:put name="form" beanName="RegistrationForm"/>
    <tiles:put name="formId" value="RegForm"/>
    <tiles:put name="action" value="/external/page/registration"/>
</tiles:insert>