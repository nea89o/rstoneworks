package com.romangraef.rstoneworks

import com.romangraef.rstoneworks.registry.RBlocks
import com.romangraef.rstoneworks.registry.RItems
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier

// For support join https://discord.gg/v6v4pMv
object RStoneworks {
	val MODID = "rstoneworks"
	val ITEM_GROUP = FabricItemGroupBuilder.build(identifier("general")) { ItemStack(RBlocks.COBBLE_FACTORY) }
	fun identifier(path: String) = Identifier(MODID, path)
}

@Suppress("unused")
fun init() {
	RItems.registerAll()
	RBlocks.registerAll()
	RBlocks.Entities.registerAll()
}

