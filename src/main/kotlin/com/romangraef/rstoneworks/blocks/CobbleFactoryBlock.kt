package com.romangraef.rstoneworks.blocks

import com.romangraef.rstoneworks.blocks.entity.CobbleFactoryBlockEntity
import com.romangraef.rstoneworks.blocks.entity.util.BaseFactoryEntity
import com.romangraef.rstoneworks.blocks.util.BaseFactoryBlock
import net.minecraft.block.entity.BlockEntity
import net.minecraft.world.BlockView

class CobbleFactoryBlock(settings: Settings) : BaseFactoryBlock(settings) {
	override fun createBlockEntity(world: BlockView): BaseFactoryEntity = CobbleFactoryBlockEntity()
}