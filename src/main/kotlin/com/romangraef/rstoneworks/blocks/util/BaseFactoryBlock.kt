package com.romangraef.rstoneworks.blocks.util

import com.romangraef.rstoneworks.blocks.entity.util.BaseFactoryEntity
import com.romangraef.rstoneworks.registry.RItems
import net.fabricmc.fabric.api.registry.FuelRegistry
import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.HorizontalFacingBlock
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.state.StateManager
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.World

abstract class BaseFactoryBlock(settings: Settings) : HorizontalFacingBlock(settings), BlockEntityProvider {
	init {
		defaultState = stateManager.defaultState.with(FACING, Direction.NORTH)
	}

	abstract override fun createBlockEntity(world: BlockView): BaseFactoryEntity

	override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
		builder.add(FACING)
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

	override fun getPlacementState(ctx: ItemPlacementContext): BlockState = defaultState.with(FACING, ctx.playerFacing)


}