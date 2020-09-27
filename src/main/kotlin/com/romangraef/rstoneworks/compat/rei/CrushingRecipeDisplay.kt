package com.romangraef.rstoneworks.compat.rei

import com.romangraef.rstoneworks.recipe.CrushingRecipe
import java.util.Optional
import me.shedaniel.rei.api.EntryStack
import me.shedaniel.rei.api.RecipeDisplay
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.util.Identifier

@Environment(EnvType.CLIENT)
class CrushingRecipeDisplay(
	val recipe: CrushingRecipe
) : RecipeDisplay {
	val inputs: MutableList<MutableList<EntryStack>> = EntryStack.ofIngredients(recipe.previewInputs)
	val outputs: MutableList<MutableList<EntryStack>> =
		mutableListOf(EntryStack.ofItemStacks(listOf(recipe.output)))

	override fun getInputEntries(): MutableList<MutableList<EntryStack>> =
		inputs

	override fun getResultingEntries(): MutableList<MutableList<EntryStack>> =
		outputs

	override fun getRequiredEntries(): MutableList<MutableList<EntryStack>> =
		inputs

	override fun getRecipeLocation(): Optional<Identifier> = Optional.ofNullable(recipe.identifier)

	override fun getRecipeCategory(): Identifier = REIPlugin.CRUSHING_RECIPE_CATEGORY

}