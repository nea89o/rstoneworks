package com.romangraef.rstoneworks.blocks

import alexiil.mc.lib.attributes.AttributeList
import alexiil.mc.lib.attributes.AttributeProvider
import alexiil.mc.lib.attributes.item.ItemAttributes
import alexiil.mc.lib.attributes.item.ItemExtractable
import com.romangraef.rstoneworks.blocks.entity.FactoryBlockEntity
import com.romangraef.rstoneworks.registry.RItems
import net.fabricmc.fabric.api.registry.FuelRegistry
import net.minecraft.block.Block
import net.minecraft.block.BlockEntityProvider
import net.minecraft.block.BlockState
import net.minecraft.block.HorizontalFacingBlock
import net.minecraft.block.entity.BlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.state.StateManager
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.world.BlockView
import net.minecraft.world.World

class FactoryBlock(settings: Settings) : HorizontalFacingBlock(settings), BlockEntityProvider, AttributeProvider {
	init {
		defaultState = stateManager.defaultState.with(FACING, Direction.NORTH)
	}

	override fun addAllAttributes(world: World, pos: BlockPos, state: BlockState, to: AttributeList<*>) {
		to.offer(ItemExtractable { filter, maxAmount, simulation ->
			val be = world.getBlockEntity(pos) as FactoryBlockEntity
			val extraction = maxAmount.coerceAtMost(be.cobbleCount)
			val itemStack = ItemStack(Items.COBBLESTONE, extraction)
			if (filter.matches(itemStack)) {
				if (simulation.isAction) {
					be.cobbleCount -= extraction
				}
				return@ItemExtractable itemStack
			}
			return@ItemExtractable ItemStack.EMPTY
		})
	}

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
		val blockEntity = world.getBlockEntity(pos) as FactoryBlockEntity
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
	override fun createBlockEntity(world: BlockView?): BlockEntity =
		FactoryBlockEntity()


}