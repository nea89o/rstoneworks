package com.romangraef.rstoneworks.blocks

import com.romangraef.rstoneworks.blocks.entity.CrushingFactoryBlockEntity
import com.romangraef.rstoneworks.blocks.entity.util.BaseFactoryEntity
import com.romangraef.rstoneworks.blocks.util.BaseFactoryBlock
import net.minecraft.world.BlockView

class CrushingFactoryBlock(settings: Settings) : BaseFactoryBlock(settings) {
	override fun createBlockEntity(world: BlockView): BaseFactoryEntity = CrushingFactoryBlockEntity()

}