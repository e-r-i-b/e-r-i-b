<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>

<%--
    Компонент для прикрепления файла.

    file           - имя поля в которое загружаем файл.
    isFileAttached - прикрепляли ли мы до этого другой файл.
    fileName       - имя файла который был прикреплён.
    form           - форма куда файл сохраняется.
--%>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>

<tiles:importAttribute/>
 <style type="text/css">
        #FileField
        {
            position: absolute;
            margin-left:152px;
            filter: alpha(opacity: 0);
            opacity: 0;
            z-index:1;
            cursor: pointer;
            cursor: hand;
        }

        .blocker {
            background-color: #000000;<%--/*Иначе в ie6 div прокликивается*/--%>
            filter: alpha(opacity: 0);<%--/*Иначе в ie6 div прокликивается*/--%>
            opacity: 0;               <%--/*Иначе в ie6 div прокликивается*/--%>
            height: 25px;
            position: absolute;
            width: 310px;
            z-index: 2;
        }

        #FileName
        {
            height: 15px;
            font-size: 8pt;
            width: 295px;
            z-index: 2;
        }
        #rollBackButton
        {
            display : inline;
        }
        #cancelButton
        {
            display:inline;
        }
        #attachField
        {
            width:490px;
        }


    </style>
    <script type="text/javascript">
        $(document).ready(function(){
            // не использовать hover. Происходит "залипание" подсветки кнопки Обзор
            $('#attachField #FileField').live("mouseenter",
                function(){
                    $(this).parent().find('#showHover .buttonGrey').addClass('buttonGreyHover');
                });

            $('#attachField #FileField').live("mouseleave",
                function(){
                    $(this).parent().find('#showHover .buttonGrey').removeClass('buttonGreyHover');
                });
        });
    </script>
    <input id="setNewFile" name="field(setNewFile)" value="true" type="hidden"/>
    <div id="attachField">
        <html:file property="${file}" styleId="FileField"/>
        <div class="blocker"></div>
        <input type="text" id="FileName" name="field(fileName)" readonly="true"/>

        <div id="showHover" style="display:inline;">
            <tiles:insert name="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.browse"/>
                <tiles:put name="commandHelpKey" value="button.browse"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="onclick" value=";"/>
            </tiles:insert>
        </div>

       <c:set var="funcName" value="cancel()"/>
       <c:if test="${isFileAttached}">
           <c:set var="funcName" value="rollBack()"/>
       </c:if>
       <div id="rollBackButton">
           <tiles:insert name="clientButton"  flush="false">
               <tiles:put name="commandTextKey" value="button.cancel"/>
               <tiles:put name="commandHelpKey" value="button.cancel"/>
               <tiles:put name="viewType" value="buttonGrey"/>
               <tiles:put name="bundle" value="mailBundle"/>
               <tiles:put name="onclick" value="${funcName};"/>
           </tiles:insert>
       </div>
       <div id="cancelButton">
           <tiles:insert name="clientButton"  flush="false">
               <tiles:put name="commandTextKey" value="button.cancel"/>
               <tiles:put name="commandHelpKey" value="button.cancel"/>
               <tiles:put name="viewType" value="buttonGrey"/>
               <tiles:put name="bundle" value="mailBundle"/>
               <tiles:put name="onclick" value="cancel();"/>
           </tiles:insert>
       </div>
    </div>
    <c:if test="${isFileAttached}">
        <div id="oldFileField">
            <html:link onclick="clientBeforeUnload.showTrigger=false; new CommandButton('button.upload').click();" href="#">
                <c:out value="${fileName}"/>
            </html:link>
            <tiles:insert name="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.substitute"/>
                <tiles:put name="commandHelpKey" value="button.substitute"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="onclick" value="newAttachFile();"/>
            </tiles:insert>
            <tiles:insert name="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.remove"/>
                <tiles:put name="commandHelpKey" value="button.remove"/>
                <tiles:put name="bundle" value="mailBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="onclick" value="removeAttachFile();"/>
            </tiles:insert>
        </div>
    </c:if>

<script type="text/javascript">

       <c:if test="${isFileAttached}"> doOnLoad(function() {rollBack()}); </c:if>

        var fileInput = document.getElementById('FileField');
        var fileName = document.getElementById('FileName');
        var cancelButton = document.getElementById('cancelButton');
        document.getElementById("cancelButton").style.display = "none";

        WindowOnLoad();
        function WindowOnLoad()
        {
            fileInput.value = '';
            fileInput.onchange = HandleChanges;
             if(window.navigator.userAgent.indexOf("iPhone") != -1 || window.navigator.userAgent.indexOf("iPad") != -1)
            {
               $("#setNewFile").val('false');
               $('#attachRow').hide();
            }

        };

        function HandleChanges()
        {
            file = fileInput.value;
            reWin = /.*\\(.*)/;
            var fileTitle = file.replace(reWin, "$1"); //выдираем название файла
            reUnix = /.*\/(.*)/;

            fileTitle = fileTitle.replace(reUnix, "$1"); //выдираем название файла

            var RegExExt =/.*\.(.*)/;
            var ext = fileTitle.replace(RegExExt, "$1");//и его расширение

            fileName.value = fileTitle;
        };

        function newAttachFile()
        {
            document.getElementById("attachField").style.display = "";
            document.getElementById("oldFileField").style.display = "none";
            $("#setNewFile").val('true');
        }

        function removeAttachFile()
        {
            newAttachFile();
            document.getElementById("rollBackButton").style.display = "none";
            document.getElementById("cancelButton").style.display = "";
        }

        function rollBack()
        {
            document.getElementById("attachField").style.display = "none";
            document.getElementById("oldFileField").style.display = "";
            $("#setNewFile").val('false');
            cancel();
        }

        function cancel()
        {
            var elem = document.getElementById('FileField');
            $(elem).remove();
            $('div .blocker').before('<html:file property="${file}" styleId="FileField"/>');            
            fileInput = document.getElementById('FileField');
            fileName = document.getElementById('FileName');
            fileName.value ='';
            WindowOnLoad();
        }
    </script>

