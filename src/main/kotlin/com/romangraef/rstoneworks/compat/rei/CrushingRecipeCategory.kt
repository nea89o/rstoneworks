package com.romangraef.rstoneworks.compat.rei

import com.romangraef.rstoneworks.compat.rei.REIPlugin.Companion.CRUSHING_RECIPE_CATEGORY
import com.romangraef.rstoneworks.registry.RBlocks
import me.shedaniel.math.Point
import me.shedaniel.math.Rectangle
import me.shedaniel.rei.api.EntryStack
import me.shedaniel.rei.api.RecipeCategory
import me.shedaniel.rei.api.widgets.Widgets
import me.shedaniel.rei.gui.widget.Widget
import net.minecraft.client.resource.language.I18n
import net.minecraft.util.Identifier

object CrushingRecipeCategory : RecipeCategory<CrushingRecipeDisplay> {
	override fun getIdentifier(): Identifier = CRUSHING_RECIPE_CATEGORY

	override fun getLogo(): EntryStack = EntryStack.create(RBlocks.CRUSHING_FACTORY)

	override fun getCategoryName(): String = I18n.translate("category.rstoneworks.crushing.name")

	override fun setupDisplay(recipeDisplay: CrushingRecipeDisplay, bounds: Rectangle): MutableList<Widget> =
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