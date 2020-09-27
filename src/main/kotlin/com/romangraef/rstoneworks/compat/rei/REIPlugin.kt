package com.romangraef.rstoneworks.compat.rei

import com.romangraef.rstoneworks.RStoneworks
import com.romangraef.rstoneworks.recipe.CrushingRecipe
import com.romangraef.rstoneworks.registry.RBlocks
import me.shedaniel.rei.api.EntryStack
import me.shedaniel.rei.api.RecipeHelper
import me.shedaniel.rei.api.plugins.REIPluginV0
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.util.Identifier


@Environment(EnvType.CLIENT)
class REIPlugin : REIPluginV0 {
	companion object {
		val CRUSHING_RECIPE_CATEGORY = RStoneworks.identifier("crushing")
	}

	override fun getPluginIdentifier(): Identifier = RStoneworks.identifier("rei_default")

	override fun registerPluginCategories(recipeHelper: RecipeHelper) {
		super.registerPluginCategories(recipeHelper)
		recipeHelper.registerCategory(CrushingRecipeCategory)
	}

	override fun registerRecipeDisplays(recipeHelper: RecipeHelper) {
		recipeHelper.registerRecipes(CRUSHING_RECIPE_CATEGORY, CrushingRecipe::class.java, ::CrushingRecipeDisplay)
	}

	override fun registerOthers(recipeHelper: RecipeHelper) {
		recipeHelper.registerWorkingStations(CRUSHING_RECIPE_CATEGORY, EntryStack.create(RBlocks.CRUSHING_FACTORY))
		recipeHelper.removeAutoCraftButton(CRUSHING_RECIPE_CATEGORY)
	}
}
