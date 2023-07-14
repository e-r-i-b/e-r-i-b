<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="csa" uri="http://rssl.com/tags/csa" %>

<tiles:insert definition="registration" flush="false">
    <c:set var="defaultBackURL" value="${csa:calculateActionURL(pageContext, '/index')}"/>
    <tiles:put name="data" type="string">
        <div class="g-main">
            <div class="b-content ugc">
                <h1>
                    <csa:messages  id="error" bundle="commonBundle" field="field" message="error">
                        <bean:write name='error' filter='false'/>
                    </csa:messages>
                </h1>
                <p>Попробуйте повторить попытку позже, или обратитесь в контактный центр Сбербанка </br> за помощью: <b>+7 (495) 500 5550, 8 (800) 555 5550.</b></p>
                <p><a href="${defaultBackURL}">Вернуться на страницу входа</a></p>
            </div>
        </div>
    </tiles:put>
    <tiles:put name="needRegionSelector" value="false"/>
</tiles:insert>