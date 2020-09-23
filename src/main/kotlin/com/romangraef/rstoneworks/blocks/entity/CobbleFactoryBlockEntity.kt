package com.romangraef.rstoneworks.blocks.entity

import com.romangraef.rstoneworks.blocks.entity.util.BaseFactoryEntity
import com.romangraef.rstoneworks.registry.RBlocks
import net.minecraft.item.ItemStack
import net.minecraft.item.Items

class CobbleFactoryBlockEntity : BaseFactoryEntity(RBlocks.Entities.COBBLE_FACTORY_ENTITY) {
	override fun getOutputForInput(itemStack: ItemStack): ItemStack =
		if (itemStack.isEmpty) ItemStack(Items.COBBLESTONE)
		else ItemStack.EMPTY
}