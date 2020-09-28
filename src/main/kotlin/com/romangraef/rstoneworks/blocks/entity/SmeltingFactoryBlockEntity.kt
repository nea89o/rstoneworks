package com.romangraef.rstoneworks.blocks.entity

import com.romangraef.rstoneworks.blocks.entity.util.BaseFactoryEntity
import com.romangraef.rstoneworks.registry.RBlocks
import net.minecraft.item.ItemStack
import net.minecraft.recipe.RecipeType

class SmeltingFactoryBlockEntity : BaseFactoryEntity(RBlocks.Entities.SMELTING_FACTORY_ENTITY) {
	override fun getOutputForInput(itemStack: ItemStack): ItemStack =
		world?.recipeManager?.listAllOfType(RecipeType.SMELTING)?.firstOrNull {
			it.previewInputs[0].test(itemStack)
		}?.output?.copy()?.also {
			if (it.count == 0)
				it.count = 1
		} ?: ItemStack.EMPTY

}