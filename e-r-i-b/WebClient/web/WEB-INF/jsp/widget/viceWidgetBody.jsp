<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<tiles:importAttribute/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<tiles:insert page="/WEB-INF/jsp/common/layout/widgetBody.jsp" flush="false">
    <tiles:put name="borderColor" value="whiteTop"/>
    <tiles:put name="sizeable" value="true"/>
    <tiles:put name="editable" value="false"/>
    <tiles:put name="viewPanel">
        <div class="ajaxLoader">
            <img src="${imagePath}/ajaxLoader.gif" alt="Загрузка..." title="Загрузка..." class="loader"/>
        </div>
        <div class="notAvaliableWidget" style="display:none;">
            <div class="notAvaliableTitle">
                Виджет временно недоступен
            </div>
            <div class="notAvaliableText">Пожалуйста, попробуйте ещё раз позже</div>
            <div align="center" class="buttons">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="commandTextKey" value="button.update"/>
                    <tiles:put name="commandHelpKey" value="button.update"/>
                    <tiles:put name="onclick" value=";"/>
                </tiles:insert>
            </div>
        </div>

        <div class="forbiddenWidget" style="display:none;">
            <div class="forbiddenTitle">
                Виджет отключен
            </div>
            <div align="center" class="buttons">
                <span button="roll">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="commandTextKey" value="button.roll"/>
                        <tiles:put name="commandHelpKey" value="button.roll"/>
                        <tiles:put name="onclick" value=";"/>
                    </tiles:insert>
                </span>
                <span button="close">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="bundle" value="commonBundle"/>
                        <tiles:put name="commandTextKey" value="button.remove"/>
                        <tiles:put name="commandHelpKey" value="button.remove"/>
                        <tiles:put name="onclick" value=";"/>
                    </tiles:insert>
                </span>
            </div>
        </div>
    </tiles:put>
</tiles:insert>