package com.romangraef.rstoneworks.blocks

import com.romangraef.rstoneworks.blocks.entity.SmeltingFactoryBlockEntity
import com.romangraef.rstoneworks.blocks.entity.util.BaseFactoryEntity
import com.romangraef.rstoneworks.blocks.util.BaseFactoryBlock
import net.minecraft.world.BlockView

class SmeltingFactoryBlock(settings: Settings) : BaseFactoryBlock(settings){
	override fun createBlockEntity(world: BlockView): BaseFactoryEntity =
		SmeltingFactoryBlockEntity()

}