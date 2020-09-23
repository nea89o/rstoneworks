package com.romangraef.rstoneworks.blocks.entity

import com.romangraef.rstoneworks.blocks.entity.util.BaseBlockEntity
import com.romangraef.rstoneworks.registry.RBlocks
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraft.util.Tickable
import net.minecraft.util.math.Direction

class FactoryBlockEntity : BaseBlockEntity(RBlocks.Entities.STONEWORK_FACTORY_ENTITY), Tickable, SidedInventory {
	var fuelTime by dataInt("fuelTime")
	var cobbleCount by dataInt("cobbleCount")
	var workTime by dataInt("workTime")
	var workSpeed by dataInt("workSpeed", 1)

	val workAmount = 100 // 1 Cobble every 5 seconds
	val cobbleCacheSize = 64

	override fun tick() {
		if (fuelTime <= 0) return
		if (cobbleCount >= cobbleCacheSize) return
		val currentWorkSpeed = workSpeed.coerceAtMost(fuelTime)
		workTime += currentWorkSpeed
		fuelTime -= currentWorkSpeed
		if (workTime >= workAmount) {
			cobbleCount++
			workTime -= workAmount
		}
	}

	override fun clear() {
		cobbleCount = 0
	}

	override fun size(): Int = 1

	override fun isEmpty(): Boolean = cobbleCount == 0

	override fun getStack(slot: Int): ItemStack = ItemStack(Items.COBBLESTONE, cobbleCount)

	override fun removeStack(slot: Int, amount: Int): ItemStack {
		val reduce = amount.coerceAtMost(cobbleCount)
		cobbleCount -= reduce
		return ItemStack(Items.COBBLESTONE, reduce)
	}

	override fun removeStack(slot: Int): ItemStack = ItemStack(Items.COBBLESTONE, cobbleCount).also {
		cobbleCount = 0
	}


	override fun setStack(slot: Int, stack: ItemStack?) {}

	override fun canPlayerUse(player: PlayerEntity?): Boolean {
		return false
	}

	override fun getAvailableSlots(side: Direction?): IntArray = if (side == Direction.DOWN)
		intArrayOf(0)
	else
		intArrayOf()

	override fun canInsert(slot: Int, stack: ItemStack?, dir: Direction?): Boolean = false

	override fun canExtract(slot: Int, stack: ItemStack?, dir: Direction?): Boolean =
		stack != null && stack.count <= cobbleCount && stack.item == Items.COBBLESTONE && dir == Direction.DOWN && slot == 0


}