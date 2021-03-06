package com.mrcrayfish.furniture.gui;

import com.mrcrayfish.furniture.gui.containers.ContainerCrate;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageSealCrate;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class GuiCrate extends GuiContainer
{
	private static final ResourceLocation GUI = new ResourceLocation("cfm:textures/gui/crate.png");
	
	private int x, y, z;
	private GuiButton btnSeal;
	
	public GuiCrate(IInventory playerInventory, IInventory crateInventory, int x, int y, int z)
	{
		super(new ContainerCrate(playerInventory, crateInventory));
		this.xSize = 176;
		this.ySize = 193;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	@Override
	public void initGui()
	{
		super.initGui();
		int posX = width / 2;
		int posY = height / 2;
		btnSeal = new GuiButton(0, posX + 46, posY - 62, 50, 20, "Seal");
		this.buttonList.add(btnSeal);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks)
	{
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI);
		int posX = (width - xSize) / 2;
		int posY = (height - ySize) / 2;
		this.drawTexturedModalRect(posX, posY, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException
	{
		if(!button.enabled) return;
		
		if(button.id == btnSeal.id)
		{
			PacketHandler.INSTANCE.sendToServer(new MessageSealCrate(x, y, z));
			
			this.mc.displayGuiScreen((GuiScreen) null);

	        if (this.mc.currentScreen == null)
	        {
	            this.mc.setIngameFocus();
	        }
		}
	}
}
