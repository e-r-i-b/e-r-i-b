
// тут все commandButton
var commandButtons = new Array();
var defaultButton;
var postbackNavigationButton;

function findCommandButton(commandId)
{
	return commandButtons[commandId];
}

function getCommandButtonDelay()
{
    return 0;
}

function findDefaultCommandButton()
{
	/*if(defaultButton == null)
		{
			alert("Page has no postback navigation button");
			throw "Page has no postback navigation button";
		}*/

	return defaultButton;
}

function setDefaultCommandButon(button)
{
	defaultButton = button;
}

function findNavigationButton()
{
	if (postbackNavigationButton == null)
	{
		alert("Page has no postback navigation button");
		throw "Page has no postback navigation button";
	}

	return postbackNavigationButton;
}

function setPostbackNavigationButton(button)
{
	postbackNavigationButton = button;
}

function createCommandButton(commandId, commandText, setDefault)
{
	var button = new CommandButton(commandId, commandText);
	commandButtons[commandId] = button;
	return button;
}

// ctor
function CommandButton(commandId, commandText, stateObject)
{
	this.id                 = commandId;
	this.commandText        = commandText;
	this.validationFunction = null;
	this.confirmText        = null;
	this.autoRefresh        = false;
	this.stateObject        = stateObject;
	this.firstClick         = true;
}

CommandButton.prototype.setValidationFunction = function(func)
{
	this.validationFunction = func;
}

CommandButton.prototype.setConfirmText = function(text)
{
	this.confirmText = text;
}

CommandButton.prototype.setAutoRefresh = function(boolValue)
{
	this.autoRefresh = boolValue;
}

/**
 * Функция нажатия на кнопку
 * @param forceRedirect
 * @param noPreLoader - если true - не отображаем прелоадер после клика
 */
CommandButton.prototype.click = function(forceRedirect, noPreLoader)
{
    var self = this;
    if (!self.firstClick)
    {
        preventDefault(event);
        return false;
    }
    else
    {
        self.firstClick = false;
    }

    var isSuccess = (function()
    {
        if(self.validationFunction != null)
        {
            if(!self.validationFunction())
                return false;
        }

        if(self.confirmText != null)
        {
            if (!confirm(self.confirmText))
                return false;
        }

        return true;
    })();

	if(!isSuccess)
    {
        this.firstClick = true;
        return false;
    }

    if (this.autoRefresh)
	{
        window.setTimeout("window.location.reload();", 60000);
	}

	if(forceRedirect)
	{
		var frm = frm = document.forms[0];
		frm.elements.namedItem('$$forceRedirect').value = forceRedirect;
	}

    if (!noPreLoader)
    {
        loadNewAction('','');
    }
    else if(isClientApp)
    {
        clientBeforeUnload.showTrigger = false;
    }

	callOperation(null, this.id);
}