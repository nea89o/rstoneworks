package com.romangraef.rstoneworks.registry

import com.romangraef.rstoneworks.RStoneworks
import com.romangraef.rstoneworks.blocks.FactoryBlock
import com.romangraef.rstoneworks.blocks.entity.FactoryBlockEntity
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroup
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object RBlocks : DelayedRegistry<Block>(RStoneworks.MODID) {
	override fun register(identifier: Identifier, obj: Block) {
		Registry.register(Registry.BLOCK, identifier, obj)
		Registry.register(
			Registry.ITEM, identifier, BlockItem(
				obj, Item.Settings()
					.group(ItemGroup.MISC)
			)
		)
	}

	val STONEWORK_FACTORY by "factory" {
		FactoryBlock(
			FabricBlockSettings
				.of(Material.PISTON)
				.hardness(4F)
		)
	}

	object Entities : DefaultDelayedRegistry<BlockEntityType<*>>(Registry.BLOCK_ENTITY_TYPE, RStoneworks.MODID) {
		val STONEWORK_FACTORY_ENTITY by "factory" {
			BlockEntityType.Builder.create<FactoryBlockEntity>(::FactoryBlockEntity, STONEWORK_FACTORY).build(null)
		}
	}
}