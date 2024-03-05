
import 'package:recharge_ems/models/recipe.dart';

class User {
  final String uid;
  final String email;
  final String name;
  String? phone = "NaN";
  String? address;
  //final String role;
  final String photoUrl;
  final List<Recipe> recipes = [];

  User({required this.uid, required this.email, required this.name, this.phone, this.address, this.photoUrl = 'https://example.com/default-image.png'});

  String? get profileImage => null;
}