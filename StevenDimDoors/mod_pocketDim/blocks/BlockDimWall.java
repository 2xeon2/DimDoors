package StevenDimDoors.mod_pocketDim.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import StevenDimDoors.mod_pocketDim.mod_pocketDim;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class BlockDimWall extends Block
{
	private static final float SUPER_HIGH_HARDNESS = 10000000000000F;
	private Icon[] blockIcon = new Icon[2];
	
	public BlockDimWall(int blockID, int j, Material par2Material) 
	{
		super(blockID, Material.ground);
		setTickRandomly(true);
		this.setCreativeTab(mod_pocketDim.dimDoorsCreativeTab);      
	}
	
	public float getBlockHardness(World par1World, int par2, int par3, int par4)
	{
		if (par1World.getBlockMetadata(par2, par3, par4) == 0)
		{
			return this.blockHardness;
		}
		else
		{
			return SUPER_HIGH_HARDNESS;
		}
	}	
	
	public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon[0] = par1IconRegister.registerIcon(mod_pocketDim.modid + ":" + this.getUnlocalizedName2());
        this.blockIcon[1] = par1IconRegister.registerIcon(mod_pocketDim.modid + ":" + this.getUnlocalizedName2() + "perm");
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public Icon getIcon(int par1, int par2)
	{
		if (par2 == 1)
		{
			return blockIcon[par2];
		}
		else
		{
			return blockIcon[0];
		}
	}
	
	@Override
	public int damageDropped (int metadata) 
	{
		return metadata;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	public void getSubBlocks(int unknown, CreativeTabs tab, List subItems) 
	{
		for (int ix = 0; ix < 2; ix++) 
		{
			subItems.add(new ItemStack(this, 1, ix));
		}
	}
    public void onBlockDestroyedByPlayer(World par1World, int par2, int par3, int par4, int par5) {}
    
    protected boolean canSilkHarvest()
    {
        return true;
    }
    
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }
   
    /**
     * replaces the block clicked with the held block, instead of placing the block on top of it. Shift click to disable. 
     */
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityPlayer, int par6, float par7, float par8, float par9)
    {
        if (entityPlayer.getCurrentEquippedItem() != null)
        {
        	Item playerEquip = entityPlayer.getCurrentEquippedItem().getItem();
        	
        	if (playerEquip instanceof ItemBlock)
        	{
        		Block block = Block.blocksList[playerEquip.itemID];
        		if (!Block.isNormalCube(playerEquip.itemID) || block instanceof BlockContainer || block.blockID == this.blockID)
        		{
        			return false;
        		}
        		if (!world.isRemote)
        		{
            		if (!entityPlayer.capabilities.isCreativeMode)
            		{
            			entityPlayer.getCurrentEquippedItem().stackSize--;
            		}
            		world.setBlock(x, y, z, entityPlayer.getCurrentEquippedItem().itemID, entityPlayer.getCurrentEquippedItem().getItemDamage(), 0);
        		}
        		return true;
        	}
        }
        return false;
    }
}
