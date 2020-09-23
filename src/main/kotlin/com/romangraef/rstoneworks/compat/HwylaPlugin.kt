package com.romangraef.rstoneworks.compat

import com.romangraef.rstoneworks.blocks.entity.CobbleFactoryBlockEntity
import com.romangraef.rstoneworks.blocks.entity.TriplePackagerFactoryBlockEntity
import com.romangraef.rstoneworks.blocks.entity.util.BaseFactoryEntity
import com.romangraef.rstoneworks.blocks.util.BaseFactoryBlock
import com.romangraef.rstoneworks.registry.RBlocks
import mcp.mobius.waila.api.IComponentProvider
import mcp.mobius.waila.api.IDataAccessor
import mcp.mobius.waila.api.IPluginConfig
import mcp.mobius.waila.api.IRegistrar
import mcp.mobius.waila.api.IServerDataProvider
import mcp.mobius.waila.api.IWailaPlugin
import mcp.mobius.waila.api.RenderableTextComponent
import mcp.mobius.waila.api.TooltipPosition
import net.minecraft.block.entity.BlockEntity
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry
import net.minecraft.world.World

class HwylaPlugin : IWailaPlugin {
	override fun register(iRegistrar: IRegistrar) {
		iRegistrar.registerComponentProvider(
			FactoryOutputProvider,
			TooltipPosition.BODY,
			BaseFactoryEntity::class.java
		)
		iRegistrar.registerBlockDataProvider(FactoryOutputProvider, BaseFactoryEntity::class.java)
	}

	object FactoryOutputProvider : IComponentProvider, IServerDataProvider<BlockEntity> {

		override fun appendBody(tooltip: MutableList<Text>, accessor: IDataAccessor, config: IPluginConfig?) {
			super.appendBody(tooltip, accessor, config)
			val be = (accessor.block as BaseFactoryBlock).createBlockEntity(accessor.world)
			be.fromTag(accessor.blockState, accessor.serverData)
			tooltip.add(TranslatableText("hwyla.rstoneworks.upgrade.speed", be.workSpeed))
			tooltip.add(
				RenderableTextComponent(
					be.inputStack.asRenderableText,
					progressBar(be.workTime, be.workAmount),
					be.outputStack.asRenderableText
				)
			)
		}

		override fun appendServerData(p0: CompoundTag?, p1: ServerPlayerEntity?, p2: World?, p3: BlockEntity?) {
			p3 as BaseFactoryEntity
			p3.toTag(p0!!)
		}
	}
}

val FURNACE_PROGRESS = Identifier("furnace_progress")
val RENDER_ITEM = Identifier("item")
val RENDER_SPACER = Identifier("spacer")
fun progressBar(progress: Int, total: Int) = RenderableTextComponent(FURNACE_PROGRESS, CompoundTag().also {
	it.putInt("progress", progress)
	it.putInt("total", total)
})

val ItemStack.asRenderableText
	get() = if (isEmpty) {
		RenderableTextComponent(RENDER_SPACER, CompoundTag().also { it.putInt("width", 18) })
	} else {
		RenderableTextComponent(RENDER_ITEM, CompoundTag().also {
			it.putString("id", Registry.ITEM.getId(item).toString())
			it.putInt("count", count)
			if (hasTag()) it.putString("nbt", tag.toString())
		})
	}