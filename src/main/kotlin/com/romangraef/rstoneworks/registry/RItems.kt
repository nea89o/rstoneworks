package com.romangraef.rstoneworks.registry

import com.romangraef.rstoneworks.RStoneworks
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry

object RItems : DefaultDelayedRegistry<Item>(Registry.ITEM, RStoneworks.MODID) {
	val DEFAULT_SETTINGS get() = Item.Settings().group(RStoneworks.ITEM_GROUP)
	val SPEED_UPGRADE by "speed_upgrade" { Item(DEFAULT_SETTINGS) }
}