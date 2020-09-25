package com.romangraef.rstoneworks.blocks.entity

import com.romangraef.rstoneworks.blocks.entity.util.BaseFactoryEntity
import com.romangraef.rstoneworks.recipe.CrushingRecipeType
import com.romangraef.rstoneworks.registry.RBlocks.Entities.CRUSHING_FACTORY_ENTITY
import net.minecraft.item.ItemStack

class CrushingFactoryBlockEntity : BaseFactoryEntity(CRUSHING_FACTORY_ENTITY) {
	override fun getOutputForInput(itemStack: ItemStack): ItemStack =
		world?.recipeManager?.listAllOfType(CrushingRecipeType)?.firstOrNull { it.input.test(itemStack) }?.output
			?: ItemStack.EMPTY
}