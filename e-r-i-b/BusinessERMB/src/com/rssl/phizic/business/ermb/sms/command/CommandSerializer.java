package com.rssl.phizic.business.ermb.sms.command;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.rssl.phizic.utils.json.BasicGsonSingleton;

/**
 * Сериализатор смс-команды
 * @author Rtischeva
 * @created 12.09.13
 * @ $Author$
 * @ $Revision$
 */
public class CommandSerializer
{
	private static final JsonParser jsonParser = new JsonParser();

	public String serializeCommand(Command command)
	{
		Gson gson = BasicGsonSingleton.getGson();
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("className", command.getClass().getName());
		JsonElement commandElement = gson.toJsonTree(command);
		jsonObject.add("commandBody", commandElement);
		return jsonObject.toString();
	}

	public Command deserializeCommand(String serializedCommand)
	{
		JsonElement jsonElement = jsonParser.parse(serializedCommand);
		JsonObject commandJson = jsonElement.getAsJsonObject();
		String className = commandJson.get("className").getAsString();
		JsonElement commandBody = commandJson.getAsJsonObject("commandBody");

		Gson gson = BasicGsonSingleton.getGson();
		try
		{
			return (Command) gson.fromJson(commandBody, Class.forName(className));
		}
		catch (ClassNotFoundException e)
		{
			throw new RuntimeException(e);
		}
	}
}
