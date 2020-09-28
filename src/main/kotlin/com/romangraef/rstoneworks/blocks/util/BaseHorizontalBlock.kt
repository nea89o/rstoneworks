package com.romangraef.rstoneworks.blocks.util

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.HorizontalFacingBlock
import net.minecraft.item.ItemPlacementContext
import net.minecraft.state.StateManager
import net.minecraft.util.math.Direction

open class BaseHorizontalBlock(settings: Settings) : HorizontalFacingBlock(settings) {
	init {
		defaultState = stateManager.defaultState.with(FACING, Direction.NORTH)
	}

	override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
		builder.add(FACING)
	}

	override fun getPlacementState(ctx: ItemPlacementContext): BlockState = defaultState.with(FACING, ctx.playerFacing.opposite)

}