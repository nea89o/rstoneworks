package com.romangraef.rstoneworks.blocks.util

import com.romangraef.rstoneworks.blocks.entity.util.BaseFactoryEntity
import com.romangraef.rstoneworks.registry.RItems
import net.fabricmc.fabric.api.registry.FuelRegistry
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.ItemScatterer
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.BlockView
import net.minecraft.world.World

abstract class BaseFactoryBlock(settings: Settings) : BaseHorizontalBlock(settings), BlockEntityProvider {


	abstract override fun createBlockEntity(world: BlockView): BaseFactoryEntity


	override fun onStateReplaced(
		state: BlockState,
		world: World,
		pos: BlockPos?,
		newState: BlockState,
		moved: Boolean
	) {
		if (!state.isOf(newState.block)) {
			val blockEntity = world.getBlockEntity(pos) as BaseFactoryEntity
			ItemScatterer.spawn(world, pos, blockEntity as Inventory?)
			super.onStateReplaced(state, world, pos, newState, moved)
		}
	}

	override fun onUse(
		state: BlockState,
		world: World,
		pos: BlockPos,
		player: PlayerEntity,
		hand: Hand,
		hit: BlockHitResult
	): ActionResult {
		val itemStack = player.getStackInHand(hand) ?: ItemStack.EMPTY
		val blockEntity = world.getBlockEntity(pos) as BaseFactoryEntity
		if (itemStack.isEmpty && hand == Hand.MAIN_HAND && player.isSneaky) {
			player.giveItemStack(blockEntity.outputStack)
			blockEntity.outputStack = ItemStack.EMPTY
			return ActionResult.SUCCESS
		}
		if (itemStack.item == RItems.SPEED_UPGRADE) {
			if (blockEntity.workSpeed < 4) {
				blockEntity.workSpeed++
				if (!player.isCreative)
					itemStack.decrement(1)
				return ActionResult.SUCCESS
			}
		}
		val fuelValue = FuelRegistry.INSTANCE[itemStack.item] ?: 0
		if (fuelValue > 0) {
			if (blockEntity.fuelTime == 0) {
				blockEntity.fuelTime = fuelValue
				blockEntity.markDirty()
				if (!player.isCreative)
					itemStack.decrement(1)
				return ActionResult.SUCCESS
			}
		}
		return super.onUse(state, world, pos, player, hand, hit)
	}


}