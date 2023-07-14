<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
<c:if test="${phiz:impliesOperation('UploadAvatarOperation', '')}">
<div class="uploadAvatarContainer">
<html:form action="/private/async/userprofile/uploadAvatar" enctype="multipart/form-data">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="person" value="${phiz:getPersonInfo()}"/>
    <c:set var="state" value="${form.state}"/>
    <html:hidden property="state" styleId="formState" value="${form.state}"/>
    <html:hidden property="error" styleId="formError" value="${form.error}"/>
    <html:hidden property="fileName" styleId="formFileName" value="${form.fileName}"/>
    <html:hidden property="state" styleId="formState" value="${form.state}"/>
    <!-- unknownnError -->

    <%-- Заголовок --%>
    <div class="loadAvatarHeader">
        <div class="float">
            <tiles:insert definition="userImage" flush="false">
                <tiles:put name="imagePath" value="${phiz:avatarPath('SOURCE', null)}"/>
                <tiles:put name="selector" value="SOURCE"/>
                <tiles:put name="imgStyle" value="avatarPreview"/>
            </tiles:insert>
        </div>
        <div class="float loadAvatarInfo">
            <c:choose>
                <c:when test="${state != 'save'}">
                    <div><h1>Загрузите фото вашего профиля с жесткого диска</h1></div>
                    Размер фотографии не должен превышать
                    <c:choose>
                        <c:when test="${form.maxFileSize >= 1024}"><c:out value="${form.maxFileSize / 1024}MB, "/></c:when>
                        <c:otherwise><c:out value="${form.maxFileSize}KB, "/></c:otherwise>
                    </c:choose>можно использовать файлы в форматах: <c:out value="${form.fileTypes}"/>.
                    <c:if test="${form.hasAvatar}">
                        <c:choose>
                            <c:when test="${not empty form.error && state == 'load'}">
                                <div class="delete"></div><div class="delete errorText"><c:out value="${form.error}"/></div>
                            </c:when>
                            <c:otherwise>
                                <a onclick="reloadWindow('delete');" class="blackButton"><div class="delete"></div>Удалить фотографию</a>
                            </c:otherwise>
                        </c:choose>

                        <div class="clear"></div>
                    </c:if>
                </c:when>
                <c:otherwise>
                    <div><h1>Выберите самую удачную область фотографии для кадрирования</h1></div>
                    Перетащите белый квадрат для кадрирования, выбрав нужный внешний вид фотографии. Чтобы внести изменения, нажмите кнопку «Сохранить&nbsp;фото».
                </c:otherwise>
            </c:choose>
        </div>
        <div class="clear"></div>
    </div>

    <%-- Основная часть --%>
    <div class="loadAvatarBody">
        <script type="text/javascript">
            function setError(error, fileName)
            {
                $("#errorInfo").show();
                $("#errorInfo .loadImageText").get(0).innerHTML = error;
                $("#errorInfo .errorText").get(0).innerHTML = fileName;
            }
        </script>
        <div class="float loadImageWindow" id="errorInfo">
            <div class="loadImageText">error</div>
            <div class="loadImageTextFile errorText">file name</div>
            <img src="${globalUrl}/commonSkin/images/profile/errorImage.png" class="avatarPreload errorImage"/>
            <div class="loadAvatarLoadButton errorImage">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.loadOtherImage"/>
                    <tiles:put name="commandHelpKey" value="button.loadOtherImage.help"/>
                    <tiles:put name="viewType" value="buttonGray"/>
                    <tiles:put name="bundle" value="userprofileBundle"/>
                    <tiles:put name="onclick" value="reloadWindow('loadOpen')"/>
                </tiles:insert>
            </div>
        </div>
        <script type="text/javascript">
            function reloadWindow(state)
            {
                ajaxQuery(
                    state == 'save' ? ("state="+state+"&x="+$("#x").val()+"&y="+$("#y").val()+"&width="+$("#width").val()+"&height="+$("#height").val()) : "state="+state,
                    "${phiz:calculateActionURL(pageContext, "/private/async/userprofile/uploadAvatar")}", function(data) {
                        if (data.indexOf("reloadProfilePage") == 0)
                        {
                            win.close("addAvatar");
                        }
                        else if (data.indexOf('unknownnError') < 0)
                        {
                            setError("Загрузка файла временно недоступна. Повторите попытку позже.", "");
                            $('.loadImageWindowSave').hide();
                            $('.loadAvatarFoolter .buttonsArea').hide();
                        }
                        else
                        {
                            $("#addAvatar").get(0).innerHTML = data;
                            evalAjaxJS(data);
                        }
                });
            }
        </script>
    <c:choose>
        <c:when test="${empty state || state == 'load' || state == 'loadOpen'}"><%-- Загрузка --%>
            <div class="float loadImageWindow" id="win1">
                <img src="${globalUrl}/commonSkin/images/profile/TEMP.jpg" alt="загрузка аватара" class="avatarPreload"/>

                <input type="file" name="avatarFile" class="avatarFile" value="${form.avatarFile}" onchange="changeFileInput();" accept="${form.mimeTypes}"/>
                <script type="text/javascript">
                    if (!testExtendedFileUpload())
                    {
                        var ieUploader = new IEUploader(buildParams(""));
                    }

                    function buildParams(file)
                    {
                        return {
                            file:       file,
                            state:      $("#formState").val(),
                            url:        '${phiz:calculateActionURL(pageContext, "/private/async/userprofile/uploadAvatar")}',
                            fieldName:  'avatarFile',
                            acceptFile: '${form.mimeTypes}',
                            name: 'ieUploader',
                            onupload:   function (f) {onUpload(f);},
                            onprogress: function(percents) {},
                            oncomplete: function(done, data) {
                                if (done)
                                {
                                    $("#addAvatar").get(0).innerHTML = data;
                                    evalAjaxJS(data);
                                }
                                else
                                {
                                    setError("Загрузка файла временно недоступна. Повторите попытку позже.", "");
                                    $('.loadImageWindow').hide();
                                    $('.loadAvatarFoolter .buttonsArea').hide();
                                }
                            }
                        };
                    }
                </script>
                <div class="loadAvatarLoadButton">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.upload"/>
                        <tiles:put name="commandHelpKey" value="button.upload.help"/>
                        <tiles:put name="bundle" value="userprofileBundle"/>
                        <tiles:put name="onclick" value="showFileChoiser();"/>
                    </tiles:insert>
                </div>
            </div>

            <div class="float loadImageWindow" id="win2">
                <%-- Отправка фотографии и провека --%>
                <div class="loadImageText">Загрузка изображения...</div>
                <div class="loadImageTextFile">file name</div>
                <img src="${globalUrl}/commonSkin/images/ajax-loader64.gif" class="loadImagePreloader"/>
            </div>

            <script type="text/javascript">
                function showFileChoiser() {
                    if (testExtendedFileUpload())
                        $("input.avatarFile").get(0).click();
                    else
                        ieUploader.getUploader().find("input.avatarFile").get(0).click();
                }

                function changeFileInput() {
                    $.each($("input.avatarFile").get(0).files, uploadFile);
                }

                function onUpload(file)
                {
                    $("#win1").hide();
                    $("#win2").show();
                    var text = $("#win2 .loadImageTextFile");
                    text.get(0).innerHTML = !testExtendedFileUpload() ? file : file.name;
                }

                function uploadFile(i, file) {
                    onUpload(file);
                    $("#formState").val("upload");
                    if (file.size/1024 > ${form.maxFileSize})
                    {
                        $("#win2").hide();
                        setError("Размер файла превышает допустимый", file.name);
                        return;
                    }
                    if ("${form.mimeTypes}".indexOf(file.type) < 0)
                    {
                        $("#win2").hide();
                        setError("Недопустимый тип файла", file.name);
                        return;
                    }
                    $("#formFileName").val(file.name);
                    new uploaderObject(buildParams(file));
                }

                try
                {
                <c:if test="${state == 'loadOpen'}">
                    if (testExtendedFileUpload())
                        showFileChoiser();
                </c:if>

                    $("#win2").hide();
                    $("#errorInfo").hide();
                }
                catch (err){}
            </script>
        </c:when>
        <c:when test="${state == 'error'}"><%-- Ошибка загрузки --%>
            <script type="text/javascript">
                try
                {
                    setError("${form.error}", "${form.fileName}");
                }
                catch(ignore) {}
            </script>
        </c:when>
        <c:when test="${state == 'save'}"><%-- Кадрирование и сохранение --%>
            <html:hidden property="x" styleId="x" value="${form.x}"/>
            <html:hidden property="y" styleId="y" value="${form.y}"/>
            <html:hidden property="width" styleId="width" value="${form.width}"/>
            <html:hidden property="height" styleId="height" value="${form.height}"/>

            <div class="loadImageWindowSave">
                <div class="avatarPresave" style="left: ${(351 - form.imageWidth)/2}px; top: ${(354 - form.imageHeight)/2}px;">
                    <tiles:insert definition="userImage" flush="false">
                        <tiles:put name="imagePath" value="${phiz:avatarPath('TEMP', null)}"/>
                        <tiles:put name="selector" value="TEMP"/>
                        <tiles:put name="temp" value="true"/>
                        <tiles:put name="imgStyle" value="avatarPresave"/>
                        <tiles:put name="imgId" value="cropbox"/>
                        <tiles:put name="style" value="width: ${form.imageWidth}px; height: ${form.imageHeight}px;"/>
                    </tiles:insert>
                </div>
            </div>

            <script type="text/javascript">
                try {
                $(function(){
                      $('#cropbox').Jcrop({
                          setSelect: [ ${form.x}, ${form.y}, ${form.width}, ${form.height} ],
                          aspectRatio: 1,
                          onChange: updateCoords,
                          onSelect: updateCoords,
                          minSize: [ 20, 20 ] ,
                          bgColor: 'white'
                      });
                    $("#errorInfo").hide();
                  });
                }
                catch (ignore){}

                function updateCoords(c) {
                    try {
                        $('#x').val(c.x);
                        $('#y').val(c.y);
                        $('#width').val(c.w);
                        $('#height').val(c.h);
                    }
                    catch (ignore){}
                };
            </script>
        </c:when>
        <c:otherwise>
            <%-- непонятное состояние --%>
        </c:otherwise>
    </c:choose>
    </div>
    <div class="clear"></div>

    <%-- Нижняя часть --%>
    <div class="loadAvatarFoolter">
        <c:choose>
            <c:when test="${state != 'save'}">
                <a onclick="win.open('addAvatarRules', null, 'addAvatar');" class="profileLink size14">Правила размещения фотографий</a>
            </c:when>
            <c:otherwise>
                <div class="buttonsArea">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="viewType" value="buttonGray"/>
                        <tiles:put name="bundle" value="userprofileBundle"/>
                        <tiles:put name="onclick" value="win.close('addAvatar');"/>
                    </tiles:insert>

                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.setAvatar"/>
                        <tiles:put name="commandHelpKey" value="button.setAvatar.help"/>
                        <tiles:put name="bundle" value="userprofileBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="onclick" value="reloadWindow('save');"/>
                    </tiles:insert>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</html:form>
</div>
</c:if>