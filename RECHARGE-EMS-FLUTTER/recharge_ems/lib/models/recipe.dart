

import 'package:recharge_ems/models/nutrition.dart';

class Recipe {
  final String id;
  final String creatorId;
  final String name;
  final String imageUrl;
  final String duration;
  final String serving;
  final String difficulty;
  final String description;
  final List<String> ingredients;
  final List<String> steps;
  final Nutrition? nutrition;

  Recipe({
    required this.id,
    required this.creatorId,
    required this.name,
    required this.imageUrl,
    required this.duration,
    required this.serving,
    required this.difficulty,
    required this.description,
    required this.ingredients,
    required this.steps,
    required this.nutrition,
  });

  String? get title => null;
}

