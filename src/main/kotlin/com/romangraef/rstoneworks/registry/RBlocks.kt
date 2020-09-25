package com.romangraef.rstoneworks.registry

import com.romangraef.rstoneworks.RStoneworks
import com.romangraef.rstoneworks.blocks.CobbleFactoryBlock
import com.romangraef.rstoneworks.blocks.CrushingFactoryBlock
import com.romangraef.rstoneworks.blocks.DoublePackagerFactoryBlock
import com.romangraef.rstoneworks.blocks.TriplePackagerFactoryBlock
import com.romangraef.rstoneworks.blocks.entity.CobbleFactoryBlockEntity
import com.romangraef.rstoneworks.blocks.entity.CrushingFactoryBlockEntity
import com.romangraef.rstoneworks.blocks.entity.DoublePackagerFactoryBlockEntity
import com.romangraef.rstoneworks.blocks.entity.TriplePackagerFactoryBlockEntity
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.BlockItem
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

object RBlocks : DelayedRegistry<Block>(RStoneworks.MODID) {
	override fun register(identifier: Identifier, obj: Block) {
		Registry.register(Registry.BLOCK, identifier, obj)
		Registry.register(Registry.ITEM, identifier, BlockItem(obj, RItems.DEFAULT_SETTINGS))
	}

	val DEFAULT_SETTINGS
		get() = FabricBlockSettings
			.of(Material.PISTON)
			.hardness(4F)


	val COBBLE_FACTORY by "cobble_factory" {
		CobbleFactoryBlock(DEFAULT_SETTINGS)
	}
	val TRIPLE_PACKAGER_FACTORY by "triple_packager_factory" {
		TriplePackagerFactoryBlock(DEFAULT_SETTINGS)
	}
	val DOUBLE_PACKAGER_FACTORY by "double_packager_factory" {
		DoublePackagerFactoryBlock(DEFAULT_SETTINGS)
	}

	val CRUSHING_FACTORY by "crushing_factory" {
		CrushingFactoryBlock(DEFAULT_SETTINGS)
	}

	object Entities : DefaultDelayedRegistry<BlockEntityType<*>>(Registry.BLOCK_ENTITY_TYPE, RStoneworks.MODID) {
		val COBBLE_FACTORY_ENTITY by "cobble_factory" {
			BlockEntityType.Builder.create(::CobbleFactoryBlockEntity, COBBLE_FACTORY).build(null)
		}
		val TRIPLE_PACKAGER_FACTORY_ENTITY by "triple_packager_factory" {
			BlockEntityType.Builder.create(::TriplePackagerFactoryBlockEntity, TRIPLE_PACKAGER_FACTORY).build(null)
		}
		val DOUBLE_PACKAGER_FACTORY_ENTITY by "double_packager_factory" {
			BlockEntityType.Builder.create(::DoublePackagerFactoryBlockEntity, DOUBLE_PACKAGER_FACTORY).build(null)
		}
		val CRUSHING_FACTORY_ENTITY by "crushing_factory"{
			BlockEntityType.Builder.create(::CrushingFactoryBlockEntity, CRUSHING_FACTORY).build(null)
		}
	}
}