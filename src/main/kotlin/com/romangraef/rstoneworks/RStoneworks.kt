package com.romangraef.rstoneworks

import com.romangraef.rstoneworks.recipe.CrushingRecipeSerializer
import com.romangraef.rstoneworks.recipe.CrushingRecipeType
import com.romangraef.rstoneworks.recipe.HydratingRecipeSerializer
import com.romangraef.rstoneworks.recipe.HydratingRecipeType
import com.romangraef.rstoneworks.registry.RBlocks
import com.romangraef.rstoneworks.registry.RItems
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

// For support join https://discord.gg/v6v4pMv
object RStoneworks {
	val MODID = "rstoneworks"
	val ITEM_GROUP = FabricItemGroupBuilder.build(identifier("general")) { ItemStack(RBlocks.COBBLE_FACTORY) }
	fun identifier(path: String) = Identifier(MODID, path)
}

@Suppress("unused")
fun init() {
	Registry.register(Registry.RECIPE_SERIALIZER, RStoneworks.identifier("crushing"), CrushingRecipeSerializer)
	Registry.register(Registry.RECIPE_TYPE, RStoneworks.identifier("crushing"), CrushingRecipeType)

	Registry.register(Registry.RECIPE_SERIALIZER, RStoneworks.identifier("hydrating"), HydratingRecipeSerializer)
	Registry.register(Registry.RECIPE_TYPE, RStoneworks.identifier("hydrating"), HydratingRecipeType)

	RItems.registerAll()
	RBlocks.registerAll()
	RBlocks.Entities.registerAll()
}

