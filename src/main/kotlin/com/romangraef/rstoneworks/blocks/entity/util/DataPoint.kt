package com.romangraef.rstoneworks.blocks.entity.util

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundTag


abstract class DataPoint<T>(val name: String, var value: T) : ReadWriteProperty<BaseBlockEntity, T> {
	override fun getValue(thisRef: BaseBlockEntity, property: KProperty<*>): T = value

	override fun setValue(thisRef: BaseBlockEntity, property: KProperty<*>, value: T) {
		this.value = value
		thisRef.markDirty()
	}

	abstract fun writeToTag(tag: CompoundTag)
	abstract fun readFromTag(tag: CompoundTag)
}

class IntPoint(name: String, default: Int) : DataPoint<Int>(name, default) {
	override fun writeToTag(tag: CompoundTag) {
		tag.putInt(name, value)
	}

	override fun readFromTag(tag: CompoundTag) {
		value = tag.getInt(name)
	}
}

class StringPoint(name: String, default: String) : DataPoint<String>(name, default) {
	override fun readFromTag(tag: CompoundTag) {
		value = tag.getString(name)
	}

	override fun writeToTag(tag: CompoundTag) {
		tag.putString(name, value)
	}
}

class ItemStackPoint(name: String, default: ItemStack) : DataPoint<ItemStack>(name, default) {
	override fun writeToTag(tag: CompoundTag) {
		tag.put(name, CompoundTag().also { value.toTag(it) })
	}

	override fun readFromTag(tag: CompoundTag) {
		value = ItemStack.fromTag(tag.getCompound(name))
	}
}
