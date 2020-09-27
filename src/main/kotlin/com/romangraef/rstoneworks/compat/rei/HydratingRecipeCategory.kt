package com.romangraef.rstoneworks.compat.rei

import com.romangraef.rstoneworks.recipe.HydratingRecipe
import com.romangraef.rstoneworks.registry.RBlocks
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment

@Environment(EnvType.CLIENT)
class HydratingRecipeDisplay(recipe: HydratingRecipe) :
	BaseRecipeDisplay<HydratingRecipe>(recipe, REIPlugin.HYDRATING_RECIPE_CATEGORY)

object HydratingRecipeCategory :
	BaseRecipeCategory<HydratingRecipeDisplay>(REIPlugin.HYDRATING_RECIPE_CATEGORY, RBlocks.HYDRATING_FACTORY)
