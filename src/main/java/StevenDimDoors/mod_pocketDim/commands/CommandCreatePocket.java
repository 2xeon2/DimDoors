package StevenDimDoors.mod_pocketDim.commands;

import net.minecraft.entity.player.EntityPlayer;
import StevenDimDoors.mod_pocketDim.helpers.DungeonHelper;

public class CommandCreatePocket extends DDCommandBase
{
	private static CommandCreatePocket instance = null;
	
	private CommandCreatePocket()
	{
		super("dd-create", "");
	}
	
	public static CommandCreatePocket instance()
	{
		if (instance == null)
			instance = new CommandCreatePocket();
		
		return instance;
	}

	@Override
	protected DDCommandResult processCommand(EntityPlayer sender, String[] command)
	{
		if (command.length > 0)
		{
			return DDCommandResult.TOO_MANY_ARGUMENTS;
		}
		
		//Place a door leading to a pocket dimension where the player is standing.
		//The pocket dimension will serve as a room for the player to build a dungeon.
		int x = (int) sender.posX;
		int y = (int) sender.posY;
		int z = (int) sender.posZ;
		DungeonHelper.instance().createCustomDungeonDoor(sender.worldObj, x, y, z);
		
		//Notify the player
		sendChat(sender, "Created a door to a pocket dimension. Please build your dungeon there.");
		
		return DDCommandResult.SUCCESS;
	}
}