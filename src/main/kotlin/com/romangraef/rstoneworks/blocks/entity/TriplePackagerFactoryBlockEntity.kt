package com.romangraef.rstoneworks.blocks.entity

import com.romangraef.rstoneworks.blocks.entity.util.BaseFactoryEntity
import com.romangraef.rstoneworks.registry.RBlocks
import net.minecraft.inventory.CraftingInventory
import net.minecraft.item.ItemStack
import net.minecraft.recipe.RecipeType

class TriplePackagerFactoryBlockEntity : BaseFactoryEntity(RBlocks.Entities.TRIPLE_PACKAGER_FACTORY_ENTITY) {
	override fun getOutputForInput(itemStack: ItemStack): ItemStack {
		val recipeManager = world?.recipeManager ?: return ItemStack.EMPTY
		return recipeManager.getFirstMatch(RecipeType.CRAFTING, CraftingInventory(null, 3, 3).also {
			(0..8).forEach { i ->
				it.stacks[i] = itemStack
			}
		}, world).orElse(null)?.output?.copy()?.also{
			it.count = 1
		} ?: ItemStack.EMPTY
	}

	override fun consumeInput() {
		inputStack.decrement(9)
	}

	override fun canWork(): Boolean =
		super.canWork() && inputStack.count >= 9 &&(outputStack.isEmpty || outputStack.count + getOutputForInput(inputStack).count <= outputStack.maxCount)
}
