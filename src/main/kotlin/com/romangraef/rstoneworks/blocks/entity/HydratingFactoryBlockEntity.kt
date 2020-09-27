package com.romangraef.rstoneworks.blocks.entity

import com.romangraef.rstoneworks.blocks.entity.util.BaseFactoryEntity
import com.romangraef.rstoneworks.recipe.CrushingRecipeType
import com.romangraef.rstoneworks.recipe.HydratingRecipeType
import com.romangraef.rstoneworks.registry.RBlocks
import net.minecraft.item.ItemStack

class HydratingFactoryBlockEntity : BaseFactoryEntity(RBlocks.Entities.HYDRATING_FACTORY_ENTITY) {
	override fun getOutputForInput(itemStack: ItemStack): ItemStack =
		world?.recipeManager?.listAllOfType(HydratingRecipeType)?.firstOrNull { it.input.test(itemStack) }?.output
			?: ItemStack.EMPTY
}
