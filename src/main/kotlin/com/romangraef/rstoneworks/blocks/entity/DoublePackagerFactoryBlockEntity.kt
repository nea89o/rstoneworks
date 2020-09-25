package com.romangraef.rstoneworks.blocks.entity

import com.romangraef.rstoneworks.blocks.entity.util.BaseFactoryEntity
import com.romangraef.rstoneworks.registry.RBlocks
import net.minecraft.inventory.CraftingInventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.RecipeType

class DoublePackagerFactoryBlockEntity : BaseFactoryEntity(RBlocks.Entities.DOUBLE_PACKAGER_FACTORY_ENTITY) {
	override fun getOutputForInput(itemStack: ItemStack): ItemStack {
		val recipeManager = world?.recipeManager ?: return ItemStack.EMPTY
		return recipeManager.getFirstMatch(RecipeType.CRAFTING, CraftingInventory(null, 2, 2).also {
			(0..3).forEach { i ->
				it.stacks[i] = itemStack
			}
		}, world).orElse(null)?.output?.copy()?.also {
			if (it.count == 0)
				it.count = 1
		} ?: ItemStack.EMPTY
	}

	override fun canWork(): Boolean =
		super.canWork() && inputStack.count >= 4 && (outputStack.isEmpty || outputStack.count + getOutputForInput(
			inputStack
		).count <= outputStack.maxCount)

	override fun consumeInput() {
		inputStack.decrement(4)
	}
}