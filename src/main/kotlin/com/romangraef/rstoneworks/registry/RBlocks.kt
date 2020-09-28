package com.romangraef.rstoneworks.registry

import com.romangraef.rstoneworks.RStoneworks
import com.romangraef.rstoneworks.blocks.CobbleFactoryBlock
import com.romangraef.rstoneworks.blocks.CrushingFactoryBlock
import com.romangraef.rstoneworks.blocks.DoublePackagerFactoryBlock
import com.romangraef.rstoneworks.blocks.HydratingFactoryBlock
import com.romangraef.rstoneworks.blocks.SmeltingFactoryBlock
import com.romangraef.rstoneworks.blocks.TriplePackagerFactoryBlock
import com.romangraef.rstoneworks.blocks.entity.CobbleFactoryBlockEntity
import com.romangraef.rstoneworks.blocks.entity.CrushingFactoryBlockEntity
import com.romangraef.rstoneworks.blocks.entity.DoublePackagerFactoryBlockEntity
import com.romangraef.rstoneworks.blocks.entity.HydratingFactoryBlockEntity
import com.romangraef.rstoneworks.blocks.entity.SmeltingFactoryBlockEntity
import com.romangraef.rstoneworks.blocks.entity.TriplePackagerFactoryBlockEntity
import com.romangraef.rstoneworks.blocks.util.BaseHorizontalBlock
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Material
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.BlockItem
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Identifier
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import net.minecraft.world.World
import net.minecraft.world.explosion.Explosion

object RBlocks : DelayedRegistry<Block>(RStoneworks.MODID) {
	override fun register(identifier: Identifier, obj: Block) {
		Registry.register(Registry.BLOCK, identifier, obj)
		Registry.register(Registry.ITEM, identifier, BlockItem(obj, RItems.DEFAULT_SETTINGS))
	}

	val DEFAULT_SETTINGS
		get() = FabricBlockSettings
			.of(Material.PISTON)
			.hardness(4F)


	val BASE_FACTORY by "base_factory" {
		BaseHorizontalBlock(DEFAULT_SETTINGS)
	}
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
	val HYDRATING_FACTORY by "hydrating_factory" {
		HydratingFactoryBlock(DEFAULT_SETTINGS)
	}
	val SMELTING_FACTORY by "smelting_factory" {
		SmeltingFactoryBlock(DEFAULT_SETTINGS)
	}
	val TUOVI_BLOCK by "tuovi_block" {
		object : Block(DEFAULT_SETTINGS.resistance(0F).hardness(1F)) {
			override fun onUse(
				state: BlockState,
				world: World,
				pos: BlockPos,
				player: PlayerEntity,
				hand: Hand,
				hit: BlockHitResult
			): ActionResult {
				world.createExplosion(
					player,
					pos.x.toDouble(),
					pos.y.toDouble(),
					pos.z.toDouble(),
					1F,
					false,
					Explosion.DestructionType.NONE
				)
				return ActionResult.SUCCESS
			}
		}
	}

	object Entities : DefaultDelayedRegistry<BlockEntityType<*>>(Registry.BLOCK_ENTITY_TYPE, RStoneworks.MODID) {
		private fun <T : BlockEntity> createBEBuilder(supplier: () -> T, vararg blocks: Block): BlockEntityType<T> =
			BlockEntityType.Builder.create(supplier, *blocks).build(null)

		val COBBLE_FACTORY_ENTITY by "cobble_factory" {
			createBEBuilder(::CobbleFactoryBlockEntity, COBBLE_FACTORY)
		}
		val TRIPLE_PACKAGER_FACTORY_ENTITY by "triple_packager_factory" {
			createBEBuilder(::TriplePackagerFactoryBlockEntity, TRIPLE_PACKAGER_FACTORY)
		}
		val DOUBLE_PACKAGER_FACTORY_ENTITY by "double_packager_factory" {
			createBEBuilder(::DoublePackagerFactoryBlockEntity, DOUBLE_PACKAGER_FACTORY)
		}
		val CRUSHING_FACTORY_ENTITY by "crushing_factory"{
			createBEBuilder(::CrushingFactoryBlockEntity, CRUSHING_FACTORY)
		}
		val HYDRATING_FACTORY_ENTITY by "hydrating_factory" {
			createBEBuilder(::HydratingFactoryBlockEntity, HYDRATING_FACTORY)
		}
		val SMELTING_FACTORY_ENTITY by "smelting_factory"{
			createBEBuilder(::SmeltingFactoryBlockEntity, SMELTING_FACTORY)
		}
	}
}