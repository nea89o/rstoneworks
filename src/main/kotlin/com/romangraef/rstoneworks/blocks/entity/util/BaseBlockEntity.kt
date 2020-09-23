package com.romangraef.rstoneworks.blocks.entity.util

import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag


open class BaseBlockEntity(type: BlockEntityType<*>) : BlockEntity(type) {
	val points = mutableListOf<DataPoint<*>>()
	override fun toTag(tag: CompoundTag): CompoundTag {
		points.forEach { it.writeToTag(tag) }
		return super.toTag(tag)
	}

	fun <T> register(point: DataPoint<T>) = point.also { points.add(point) }
	fun dataInt(name: String, default: Int = 0) = register(IntPoint(name, default))
	fun dataString(name: String, default: String) = register(StringPoint(name, default))
	fun dataItemStack(name: String, default: ItemStack = ItemStack.EMPTY) = register(ItemStackPoint(name, default))
	override fun fromTag(state: BlockState, tag: CompoundTag) {
		points.forEach { it.readFromTag(tag) }
		super.fromTag(state, tag)
	}
}
