<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="bean" uri="http://jakarta.apache.org/struts/tags-bean" %>

<tiles:importAttribute name="mainmenu" ignore="true"/>
<c:set var="mode" scope="request" value="${mainmenu}"/>
<tiles:importAttribute name="enabledLink" ignore="true"/>
<c:set var="enabledLink" scope="request" value="${enabledLink}"/>
<div id="menu">
    <script type="text/javascript">
        function activeParentItem()
        {
            $('#other').addClass('active activeOther');
        }
    </script>
    <c:set var="width" value="305"/>
    <table border="0" cellspacing="0" cellpadding="0" class="mainMenu">
        <tr>
            <tiles:insert definition="mainMenuInset">
                <tiles:put name="activity" value="${mode == 'Index'}"/>
                <tiles:put name="action" value="/guest/index"/>
                <tiles:put name="text" value="Главная"/>
                <tiles:put name="insetWidth" value="${width}"/>
                <tiles:put name="positionItem" value="firstPosition"/>
                <tiles:put name="enabledLink" value="${enabledLink}"/>
            </tiles:insert>

            <tiles:insert definition="mainMenuInset">
                <tiles:put name="activity" value="${mode == 'Abilities'}"/>
                <tiles:put name="action" value="/guest/promo"/>
                <tiles:put name="text" value="Возможности Сбербанк Онлайн"/>
                <tiles:put name="insetWidth" value="${width}"/>
                <tiles:put name="enabledLink" value="${enabledLink}"/>
            </tiles:insert>

            <tiles:insert definition="mainMenuInset">
                <tiles:put name="fake" value="${true}"/>
                <tiles:put name="positionItem" value="lastPosition"/>
            </tiles:insert>
        </tr>
    </table>
</div>

<script type="text/javascript">


    $(document).ready(function(){

        $('#other').live("mouseenter",
                function(){
                    $('#dropDownMenu').css('display','block');
                });

        $('#other').live("mouseleave",
                function(){
                    $('#dropDownMenu').css('display','none');
                });

    });

</script>