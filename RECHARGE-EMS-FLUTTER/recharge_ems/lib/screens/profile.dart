import 'package:flutter/material.dart';
import 'package:recharge_ems/models/user.dart';
import 'package:recharge_ems/models/recipe.dart';

class ProfileScreen extends StatelessWidget {
  final User user;
  final List<Recipe> recipes;

  ProfileScreen({required this.user, required this.recipes});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Profile'),
      ),
      body: Column(
        children: [
          SizedBox(height: 20),
          CircleAvatar(
            radius: 50,
            backgroundImage: NetworkImage(user.profileImage!),
          ),
          SizedBox(height: 10),
          Text(
            user.name,
            style: TextStyle(fontSize: 24),
          ),
          SizedBox(height: 20),
          Text(
            'Recipes:',
            style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
          ),
          Expanded(
            child: ListView.builder(
              itemCount: recipes.length,
              itemBuilder: (context, index) {
                return ListTile(
                  title: Text(recipes[index].title!),
                  subtitle: Text(recipes[index].description),
                );
              },
            ),
          ),
        ],
      ),
    );
  }
}
