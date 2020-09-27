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
import net.minecraft.util.collection.DefaultedList
import net.minecraft.world.World

class HydratingRecipe(
	val identifier: Identifier,
	val input: Ingredient,
	private val _output: ItemStack,
) : Recipe<Inventory> {
	override fun matches(inv: Inventory, world: World): Boolean = inv.size() > 1 && input.test(inv.getStack(0))

	/** Do it yourself */
	override fun craft(inv: Inventory?): ItemStack = ItemStack.EMPTY
	override fun fits(width: Int, height: Int): Boolean = width >= 1 && height >= 1

	override fun getId(): Identifier = identifier
	override fun getOutput(): ItemStack = _output.copy()
	override fun getSerializer(): RecipeSerializer<*> = HydratingRecipeSerializer
	override fun getPreviewInputs(): DefaultedList<Ingredient> = DefaultedList.of<Ingredient>().also {
		it.add(input)
	}

	override fun getType(): RecipeType<*> = HydratingRecipeType
}

object HydratingRecipeType : RecipeType<HydratingRecipe>
object HydratingRecipeSerializer : RecipeSerializer<HydratingRecipe> {
	override fun read(id: Identifier, json: JsonObject): HydratingRecipe = HydratingRecipe(
		id, Ingredient.fromJson(json.getAsJsonObject("input")),
		ShapedRecipe.getItemStack(json.getAsJsonObject("output")).also {
			if (it.count == 0) it.count = 1
		}
	)

	override fun read(id: Identifier, buf: PacketByteBuf): HydratingRecipe = HydratingRecipe(
		id,
		Ingredient.fromPacket(buf),
		buf.readItemStack()
	)

	override fun write(buf: PacketByteBuf, recipe: HydratingRecipe) {
		recipe.input.write(buf)
		buf.writeItemStack(recipe.output)
	}
}