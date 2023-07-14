<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://rssl.com/tags/mobile" prefix="mobile" %>
<%@ page contentType="text/xml;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<tiles:insert definition="iphone" flush="false">
    <tiles:put name="data"> 
        <c:set var="person" value="${phiz:getPersonInfo()}"/>
        <loginCompleted>${not empty person}</loginCompleted>
        <tiles:insert definition="personType" flush="false">
            <tiles:put name="name" value="person" />
            <tiles:put name="person" beanName="person"/>
        </tiles:insert>
    </tiles:put>
    <tiles:put name="messagesBundle" value="securityBundle"/>
</tiles:insert>