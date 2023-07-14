<%@ page contentType="text/html;charset=windows-1251" language="java" %>

<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:insert definition="logMain">
    <tiles:put name="pageTitle" type="string">Сервис</tiles:put>
    <tiles:put name="data" type="string">
        <tiles:insert definition="roundBorderLight" flush="false">
            <tiles:put name="color" value="orange"/>
            <tiles:put name="data">
                Для продолжения работы, выберите нужный пункт в левом меню.
            </tiles:put>
        </tiles:insert>
    </tiles:put>
</tiles:insert>