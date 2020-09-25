package com.romangraef.rstoneworks.blocks.entity.util

import net.fabricmc.fabric.api.registry.FuelRegistry
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.SidedInventory
import net.minecraft.item.ItemStack
import net.minecraft.util.Tickable
import net.minecraft.util.math.Direction

abstract class BaseFactoryEntity(type: BlockEntityType<*>) : BaseBlockEntity(type), SidedInventory, Tickable {

	/*
		SidedInventory Implementation:
			Slot 0: Top, Input
			Slot 1: Bottom, Output
			Slot 2: Sides, Coal
	*/
	fun isValidInput(itemStack: ItemStack): Boolean {
		println("Stack: $itemStack")
		return !getOutputForInput(itemStack).isEmpty
	}
	abstract fun getOutputForInput(itemStack: ItemStack): ItemStack
	val workAmount = 100

	override fun tick() {
		if (!canWork()) return
		val currentOutput = getOutputForInput(inputStack)
		if (currentOutput.isEmpty) {
			workTime = 0
			return
		}
		if (!currentOutput.isItemEqual(outputStack) && !outputStack.isEmpty) {
			workTime = 0
			return
		}
		val currentWorkSpeed = workSpeed.coerceAtMost(fuelTime)
		workTime += currentWorkSpeed
		fuelTime -= currentWorkSpeed
		if (workTime >= workAmount) {
			if (outputStack.isEmpty) outputStack = currentOutput
			else outputStack.increment(currentOutput.count)
			consumeInput()
			workTime -= workAmount
		}
		markDirty()
	}

	open fun consumeInput() {
		if (!inputStack.isEmpty) inputStack.decrement(1)
	}

	open fun canWork(): Boolean = fuelTime >= 0 && outputStack.isEmpty || outputStack.maxCount > outputStack.count

	val INPUT_SLOT = 0
	val OUTPUT_SLOT = 1
	val FUEL_SLOT = 2
	var outputStack by dataItemStack("outputSlot")
	var inputStack by dataItemStack("inputSlot")
	var fuelTime by dataInt("fuelTime")
	var workTime by dataInt("workTime")
	var workSpeed by dataInt("workSpeed", 1)

	override fun clear() {
		outputStack = ItemStack.EMPTY
		inputStack = ItemStack.EMPTY
	}

	override fun size(): Int = 3

	override fun isEmpty(): Boolean = outputStack.isEmpty && inputStack.isEmpty

	override fun getStack(slot: Int): ItemStack = when (slot) {
		INPUT_SLOT -> inputStack
		OUTPUT_SLOT -> outputStack
		else -> ItemStack.EMPTY
	}

	override fun removeStack(slot: Int, amount: Int): ItemStack = when (slot) {
		OUTPUT_SLOT -> outputStack.copy().also {
			val reduce = amount.coerceAtMost(outputStack.count)
			it.count = reduce
			outputStack.decrement(reduce)
		}
		else -> ItemStack.EMPTY
	}

	override fun removeStack(slot: Int): ItemStack = when (slot) {
		OUTPUT_SLOT -> outputStack.also {
			outputStack = ItemStack.EMPTY
		}
		else -> ItemStack.EMPTY
	}


	override fun setStack(slot: Int, stack: ItemStack?) {
		stack ?: return
		when (slot) {
			INPUT_SLOT -> inputStack = stack
			FUEL_SLOT -> fuelTime += (FuelRegistry.INSTANCE[stack.item] ?: 0)*stack.count
		}
	}

	override fun canPlayerUse(player: PlayerEntity?): Boolean {
		return false
	}

	override fun getAvailableSlots(side: Direction?): IntArray = when (side) {
		Direction.DOWN -> intArrayOf(OUTPUT_SLOT)
		Direction.UP -> intArrayOf(INPUT_SLOT)
		else -> intArrayOf(FUEL_SLOT)
	}

	override fun canInsert(slot: Int, stack: ItemStack?, dir: Direction?): Boolean =
		stack != null && slot in getAvailableSlots(dir) && when (slot) {
			INPUT_SLOT -> if (inputStack.isEmpty) isValidInput(stack) else inputStack.isItemEqual(stack)
			FUEL_SLOT -> if (fuelTime > 0) false else FuelRegistry.INSTANCE[stack.item] ?: 0 > 0
			else -> false
		}

	override fun canExtract(slot: Int, stack: ItemStack?, dir: Direction?): Boolean =
		stack != null && slot in getAvailableSlots(dir) && when (slot) {
			OUTPUT_SLOT -> !outputStack.isEmpty && outputStack.isItemEqual(stack) && outputStack.count <= stack.count
			else -> false
		}


}