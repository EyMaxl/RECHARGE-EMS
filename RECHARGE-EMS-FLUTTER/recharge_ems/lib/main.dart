import 'dart:js';

import 'package:flutter/material.dart';
import 'package:recharge_ems/models/user.dart';
import 'package:recharge_ems/screens/recipes.dart';

import 'src/app.dart';
import 'src/settings/settings_controller.dart';
import 'src/settings/settings_service.dart';
import 'src/screens/recipes.dart'; // Import the recipes.dart screen
import 'package:firebase_core/firebase_core.dart';



Future<void> init() async {
  // Initialize Firebase
  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
);
}



void main() async {
  final settingsController = SettingsController(SettingsService());
  await settingsController.loadSettings();

  

  runApp(MyApp(settingsController: settingsController));

  // Navigate to the recipes.dart screen immediately after startup
  WidgetsBinding.instance.addPostFrameCallback((_) {
    Navigator.push(
      // Use the appropriate navigator context here
      context,
      MaterialPageRoute(builder: (context) => RecipesScreen(user: User())),
    );
  });
  
}
