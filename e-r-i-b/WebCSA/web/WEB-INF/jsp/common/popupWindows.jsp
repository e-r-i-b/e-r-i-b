<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

 <%-- окон с всплывающей ошибкой --%>
<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="errorForm"/>
    <tiles:put name="data">
        <div class="error-message">
        </div>
        <div class="buttonsArea">
            <div class="clientButton" onclick="win.close('errorForm')">
                <div class="buttonGrey">
                    <div class="left-corner"></div>
                    <div class="text">
                        <span>Закрыть</span>
                    </div>
                    <div class="right-corner"></div>
                    <div class="clear"></div>
                </div>
            </div>
        </div>
    </tiles:put>
</tiles:insert>

<%-- окон с текущей стадией --%>
<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="stageForm"/>
    <tiles:put name="data">
    </tiles:put>
</tiles:insert>