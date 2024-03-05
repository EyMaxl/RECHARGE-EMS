import 'package:flutter/material.dart';
import 'package:recharge_ems/models/user.dart';
import 'package:recharge_ems/widgets/recipe_card.dart';

class RecipesScreen extends StatelessWidget {
  final User user;

  RecipesScreen({required this.user});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Recipes'),
      ),
      body: ListView.builder(
        itemCount: user.recipes.length,
        itemBuilder: (context, index) {
          return RecipeCard(recipe: user.recipes[index]);
        },
      ),
    );
  }
}
