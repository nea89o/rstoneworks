package com.romangraef.rstoneworks.compat.rei

import com.romangraef.rstoneworks.recipe.CrushingRecipe
import com.romangraef.rstoneworks.registry.RBlocks
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

@Environment(EnvType.CLIENT)
class CrushingRecipeDisplay(recipe: CrushingRecipe) :
	BaseRecipeDisplay<CrushingRecipe>(recipe, REIPlugin.CRUSHING_RECIPE_CATEGORY)

object CrushingRecipeCategory :
	BaseRecipeCategory<CrushingRecipeDisplay>(REIPlugin.CRUSHING_RECIPE_CATEGORY, RBlocks.CRUSHING_FACTORY)