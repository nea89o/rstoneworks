package com.romangraef.rstoneworks.compat.rei

import java.util.Optional
import me.shedaniel.rei.api.EntryStack
import me.shedaniel.rei.api.RecipeDisplay
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.recipe.Recipe
import net.minecraft.util.Identifier

@Environment(EnvType.CLIENT)
open class BaseRecipeDisplay<T : Recipe<*>>(
	val recipe: T,
	private val _category: Identifier,
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

	override fun getRecipeLocation(): Optional<Identifier> = Optional.ofNullable(recipe.id)
	override fun getRecipeCategory(): Identifier = _category
}