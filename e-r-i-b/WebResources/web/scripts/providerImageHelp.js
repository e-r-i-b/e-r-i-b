function paymentImageHelpHeaderAction()
{
    paymentImageHelpAction('imageHelpProviderHeader', "скрыть", "показать");
}

function paymentImageHelpFieldAction(fieldId)
{
    paymentImageHelpAction('imageHelpProviderField' + fieldId, "скрыть образец квитанции", "образец квитанции");
}

function paymentImageHelpAction(containerId, hideTitle, showTitle)
{
    var parent = $('#' + containerId);
    var container = parent.find(".imageHelp");
    var title = parent.find(".imageHelpTitle");
    if (container.is(':visible'))
    {
        container.hide();
        title.html(showTitle);
        title.removeClass('opened');
        title.addClass('closed');
    }
    else
    {
        container.show();
        title.html(hideTitle);
        title.removeClass('closed');
        title.addClass('opened');

        var imageHelpSrc = $("#imageHelpSrc").val();
        var imgs = container.find('img');
        //если картинка уже загружена то ничего не делаем
        if (imgs.length == 1 && imageHelpSrc == imgs[0].src)
            return;

        container.html('<img style="width: ' + container.width() +'px" src="' + imageHelpSrc + '"/>');

        imgs = container.find('img')[0];
        $(imgs).load(function(){addResizableControl(imgs);});
    }
}

function addResizableControl(parent)
{
    var width = 0;
    if (typeof parent.naturalWidth == "undefined")
    {
        // IE 6/7/8
        var i = new Image();
        i.src = parent.src;
        width = i.width;
    }
    else
    {
        // HTML5 browsers
        width = parent.naturalWidth;
    }
    //нужно ли добавлять лупу
    if (width <= 470)
        return;
    
    $(parent).after('<div class="resizableControlContainer resizableImage"/>');
    if (isIE(6))
        IEPNGFix.init($(parent).parent().find(".resizableControlContainer")[0]);
}

function openImageWindow()
{
    $("#imageHelpProvider").html('<img id="popupWinImageHelp" src="' + $("#imageHelpSrc").val() + '"/>');
    $("#popupWinImageHelp").load(function(){resizeOpenedImageWindow();});

}

function resizeOpenedImageWindow()
{
    var image = $("#popupWinImageHelp");
    var realImgWidth = image[0].scrollWidth;
    var realImgHeight = image[0].scrollHeight;
    var imgWidth = realImgWidth;

    if (realImgWidth<470)
        imgWidth=470;
    if (realImgWidth>800)
        imgWidth=800;
    var imgHeight = realImgHeight*imgWidth/realImgWidth;
    image.css({"width":imgWidth+"px", "height":imgHeight+"px"});
    $("#imageHelpProvider").css({"width":(imgWidth+20)+"px", "height":(imgHeight+20)+"px"});
    $("#providerImageHelpWin").css({"width":(imgWidth+180)+"px", "height":(imgHeight+50)+"px"});
    $("#providerImageHelpWin .workspace-box").css({"width":(imgWidth+180)+"px", "height":(imgHeight+50)+"px"});
    win.open('providerImageHelp');
}

doOnLoad(function(){
    $(".resizableImage").live("click", function(){openImageWindow();});
    if (isIE(6))
        IEPNGFix.init($(".imageHelpTitleContainer")[0]);
});
