package com.romangraef.rstoneworks.compat.rei

import me.shedaniel.math.Point
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.EntryStack
import me.shedaniel.rei.api.RecipeCategory
import me.shedaniel.rei.api.widgets.Widgets
import me.shedaniel.rei.gui.widget.Widget
import net.minecraft.client.resource.language.I18n
import net.minecraft.item.ItemConvertible
import net.minecraft.util.Identifier

open class BaseRecipeCategory<T : BaseRecipeDisplay<*>>(
	private val _identifier: Identifier,
	private val _logo: ItemConvertible,
) : RecipeCategory<T> {
	override fun getIdentifier(): Identifier = _identifier
	override fun getLogo(): EntryStack = EntryStack.create(_logo)
	override fun getCategoryName(): String = I18n.translate(_logo.asItem().translationKey)
	override fun setupDisplay(recipeDisplay: T, bounds: Rectangle): MutableList<Widget> =
		mutableListOf<Widget>().apply {
			val startPoint = Point(bounds.centerX - 41, bounds.y + 10)
			add(Widgets.createRecipeBase(bounds))
			add(Widgets.createArrow(Point(startPoint.x + 24, startPoint.y + 9)).animationDurationTicks(10.0))
			add(
				Widgets.createSlot(Point(startPoint.x + 1, startPoint.y + 9))
					.entries(recipeDisplay.inputs[0])
					.markInput()
			)
			add(
				Widgets.createSlot(Point(startPoint.x + 61, startPoint.y + 9))
					.entries(recipeDisplay.outputs[0])
					.markOutput()
			)
		}

	override fun getDisplayHeight(): Int = 49

}