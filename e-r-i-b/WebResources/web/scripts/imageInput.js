var DISC_IMAGE_KIND     = 'DISC';
var EXTERNAL_IMAGE_KIND = 'EXTERNAL';

var jsImageObjectList = new Array();

function getImageObject(imageId)
{
    return jsImageObjectList[imageId];
}

function addImage(image)
{
    jsImageObjectList[image.id] = image;
}

function jsImageObject(id, imageSourceKind, maxFileInputSize)
{
    this.id = id;
    this.kind = imageSourceKind;

    var fileInputHtml = '<input type="file" name="images(imageDiscSource' + id + ')" size="' + maxFileInputSize + '" id="imageFileField' + id + '" onchange="onChangeFileInput(' + "'" + id + "'" + ');"/>';

    var setNewFileField = $('#setNewFileField' + id);
    var discImageSelectorArea = $('#discImageSelectorArea' + id);
    var notAttachedDiscImageSelectorArea = $('#notAttachedDiscImageSelectorArea' + id);
    var cancelButton = $('#cancelButton' + id);
    var attachedDiscImageSelectorArea = $('#attachedDiscImageSelectorArea' + id);
    var externalImageSelectorArea = $('#externalImageSelectorArea' + id);
    var externalImageField = $('#imageUrlField' + id);

    var replaseFileInput = function()
    {
        $('#imageFileField' + id).remove();
        cancelButton.before(fileInputHtml);
    };

    this.setDiscKind = function()
    {
        this.kind = DISC_IMAGE_KIND;
        externalImageSelectorArea.hide();
        discImageSelectorArea.show();
    };

    this.setExternalKind = function()
    {
        this.kind = EXTERNAL_IMAGE_KIND;
        discImageSelectorArea.hide();
        setNewFileField.val("true");
        externalImageSelectorArea.show();
    };

    this.changeFileInput = function()
    {
        setNewFileField.val("true");
        cancelButton.show();
    };
    
    this.clearFileInput = function()
    {
        setNewFileField.val("true");
        replaseFileInput();
        cancelButton.hide();
    };

    this.removeAttach = function()
    {
        replaseFileInput();
        notAttachedDiscImageSelectorArea.show();
        attachedDiscImageSelectorArea.hide();
        setNewFileField.val('true');
    };

    this.clear = function()
    {
        replaseFileInput();
        setNewFileField.val('true');
        externalImageField.val('');
    };

    this.rollBackAttach = function()
    {
        replaseFileInput();
        cancelButton.hide();
        notAttachedDiscImageSelectorArea.hide();
        attachedDiscImageSelectorArea.show();
        setNewFileField.val('false');
    };

    this.isEmpty = function()
    {
        if (this.kind == DISC_IMAGE_KIND)
            return $('#imageFileField' + id).val() == '' && (attachedDiscImageSelectorArea.length == 0 || attachedDiscImageSelectorArea.is(':not(:visible)'));

        if (this.kind == EXTERNAL_IMAGE_KIND)
            return externalImageSelectorArea.find('input[type=text]').val() == '';

        return true;
    };
}

function changeImageSource(imageId, newKind)
{
    var image = getImageObject(imageId);
    if (image.kind == newKind)
        return;

    if (newKind == DISC_IMAGE_KIND)
        return image.setDiscKind();

    if (newKind == EXTERNAL_IMAGE_KIND)
        return image.setExternalKind();
}

function onChangeFileInput(imageId)
{
    var image = getImageObject(imageId);
    image.changeFileInput();
}

function clearFileInput(imageId)
{
    var image = getImageObject(imageId);
    image.clearFileInput();
}

function newAttachFileInput(imageId)
{
    var image = getImageObject(imageId);
    image.removeAttach();
}

function rollBackFileInput(imageId)
{
    var image = getImageObject(imageId);
    image.rollBackAttach();
}
