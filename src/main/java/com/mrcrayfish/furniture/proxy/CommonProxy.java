/**
 * MrCrayfish's Furniture Mod
 * Copyright (C) 2016  MrCrayfish (http://www.mrcrayfish.com/)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.mrcrayfish.furniture.proxy;

import com.google.common.collect.ImmutableList;
import com.mrcrayfish.furniture.Reference;
import com.mrcrayfish.furniture.handler.CraftingHandler;
import com.mrcrayfish.furniture.handler.SyncEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.List;

public class CommonProxy implements ProxyInterface
{
	public World getClientWorld()
	{
		return null;
	}

	public EntityPlayer getClientPlayer()
	{
		return null;
	}

	@Override
	public boolean isSinglePlayer()
	{
		return false;
	}

	@Override
	public boolean isDedicatedServer()
	{
		return true;
	}

	@Override
	public void preInit()
	{
		MinecraftForge.EVENT_BUS.register(new CraftingHandler());
		MinecraftForge.EVENT_BUS.register(new SyncEvent());
		MinecraftForge.EVENT_BUS.register(this);
	}

	private static final List<String> IGNORE_SOUNDS;

	static
	{
		ImmutableList.Builder<String> builder = ImmutableList.builder();
		builder.add("channel_news");
		builder.add("channel_sam_tabor");
		builder.add("channel_heman");
		builder.add("channel_switch");
		builder.add("channel_cooking");
		IGNORE_SOUNDS = builder.build();
	}

	@SubscribeEvent
	public void onMissingMap(RegistryEvent.MissingMappings<SoundEvent> event)
	{
		for(RegistryEvent.MissingMappings.Mapping<SoundEvent> missing : event.getMappings())
		{
			if(missing.key.getResourceDomain().equals(Reference.MOD_ID) && IGNORE_SOUNDS.contains(missing.key.getResourcePath().toString()))
			{
				missing.ignore();
			}
		}
	}
}
