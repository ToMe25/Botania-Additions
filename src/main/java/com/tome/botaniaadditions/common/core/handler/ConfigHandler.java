/**
 * This class was created by <ToMe25>. It's distributed as
 * part of the Botania Additions Mod. Get the Source Code in github:
 * https://github.com/ToMe25/Botania-Additions
 *
 * Botania Additions is Open Source and distributed under the
 * MIT License: https://github.com/ToMe25/Botania-Additions/blob/master/LICENSE
 *
 * File Created @ [Jan 28, 2020, 9:59 (GMT)]
 */
package com.tome.botaniaadditions.common.core.handler;

import java.io.File;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.tome.botaniaadditions.BotaniaAdditions;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

public class ConfigHandler {

	private static CommentedFileConfig cfg;
	private static final ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
	private static final String CATEGORY_GENERAL = "general";

	public static BooleanValue enableTerraHarvester;
	public static BooleanValue enableTimelessIvy;

	static {
		loadConfig();
	}

	public static void loadConfig() {
		cfg = CommentedFileConfig
				.builder(new File(FMLPaths.CONFIGDIR.get().toString(), BotaniaAdditions.MODID + "-common.toml")).sync()
				.autosave().build();
		cfg.load();
		initConfig();
		ForgeConfigSpec spec = builder.build();
		ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, spec, cfg.getFile().getName());
		spec.setConfig(cfg);
	}

	private static void initConfig() {
		builder.comment("The general configuration for this mod").push(CATEGORY_GENERAL).pop();
		enableTerraHarvester = getBoolean("enableTerraHarvester", CATEGORY_GENERAL, true,
				"If this is set to true the Terra Harvester will be added to the game.");
		enableTimelessIvy = getBoolean("enableTimelessIvy", CATEGORY_GENERAL, true,
				"If this is set to true the Timeless Ivy will be added to the game.");
	}

	public static BooleanValue getBoolean(String name, String category, boolean defaultValue, String comment) {
		String path = category + "." + name;
		return builder.comment(comment, "Default: " + defaultValue).define(path, defaultValue);
	}

}