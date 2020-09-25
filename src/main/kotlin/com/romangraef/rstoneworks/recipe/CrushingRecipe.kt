package com.romangraef.rstoneworks.recipe

import com.google.gson.JsonObject
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.recipe.Ingredient
import net.minecraft.recipe.Recipe
import net.minecraft.recipe.RecipeSerializer
import net.minecraft.recipe.RecipeType
import net.minecraft.recipe.ShapedRecipe
import net.minecraft.util.Identifier
import net.minecraft.world.World

class CrushingRecipe(
	val identifier: Identifier,
	val input: Ingredient,
	private val _output: ItemStack,
	) : Recipe<Inventory> {

	override fun matches(inv: Inventory, world: World): Boolean =
		inv.size() >= 1 && input.test(inv.getStack(0))

	/** Please do it yourself */
	override fun craft(inv: Inventory): ItemStack = ItemStack.EMPTY
	override fun fits(width: Int, height: Int): Boolean = width >= 1 && height >= 1

	override fun getId(): Identifier = identifier
	override fun getOutput(): ItemStack = _output.copy()
	override fun getSerializer(): RecipeSerializer<*> = CrushingRecipeSerializer

	override fun getType(): RecipeType<*> = CrushingRecipeType
}

object CrushingRecipeType : RecipeType<CrushingRecipe>
object CrushingRecipeSerializer : RecipeSerializer<CrushingRecipe> {
	override fun read(id: Identifier, json: JsonObject): CrushingRecipe = CrushingRecipe(
		id, Ingredient.fromJson(json.getAsJsonObject("input")),
		ShapedRecipe.getItemStack(json.getAsJsonObject("output")).also {
			if (it.count == 0) it.count = 1
		}
	)

	override fun read(id: Identifier, buf: PacketByteBuf): CrushingRecipe = CrushingRecipe(
		id,
		Ingredient.fromPacket(buf),
		buf.readItemStack()
	)

	override fun write(buf: PacketByteBuf, recipe: CrushingRecipe) {
		recipe.input.write(buf)
		buf.writeItemStack(recipe.output)
	}
}