import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
import 'package:recharge_ems/constraints/colors.dart';
import 'package:recharge_ems/screens/recipes.dart';
import '../services/firestore.dart';
import 'package:recharge_ems/models/user.dart' as rechUser; // Import the correct User class
//import 'home_screen.dart'; // Import your HomeScreen

class SignUpScreen extends StatefulWidget {
  @override
  _SignUpScreenState createState() => _SignUpScreenState();
}

class _SignUpScreenState extends State<SignUpScreen> {
  FirestoreService firestoreService = FirestoreService();

  bool _passwordVisible = false;
  // Toggles the password show status
  void _toggle() {
    setState(() {
      _passwordVisible = !_passwordVisible;
    });
  }

  final FirebaseAuth _auth = FirebaseAuth.instance;
  final _usernameController = TextEditingController();
  final _emailController = TextEditingController();
  final _passwordController = TextEditingController();

  Future<void> _signUp() async {
    String username = _usernameController.text;
    String email = _emailController.text;
    String password = _passwordController.text;

    try {
      // Check if username is available
      bool usernameAvailable = await isUsernameAvailable(username);
      if (!usernameAvailable) {
        _showErrorDialog('Username is already taken');
        return;
      }

      // Create user
      UserCredential userCredential =
          await _auth.createUserWithEmailAndPassword(
        email: email,
        password: password,
      );

      // Update user profile with username
      await userCredential.user!.updateDisplayName(username);

      if (userCredential.user?.uid != null)
        firestoreService.initializeUser(userCredential);

      // Navigate to the Home Screen or show a success message
      // If sign-up is successful, show the alert dialog
      showDialog(
        context: context,
        builder: (BuildContext context) {
          return AlertDialog(
            title: Text("Sign Up Successful"),
            content: Text("Successfully signed up, Welcome!"),
            actions: <Widget>[
              TextButton(
                child: Text("OK"),
                onPressed: () {
                  // Close the dialog
                  Navigator.of(context).pop();

                  // Navigate back to the sign-in screen
                  // Assuming you are using a named route, replace 'signInScreenRoute' with your actual route name
                  Navigator.of(context).push(


                  // ...

                  MaterialPageRoute(builder: (context) => RecipesScreen(user: rechUser.User.fromUid(userCredential.user?.uid ?? ''))), // Use the named constructor `fromUid` to create a User object
                );
                      //.pushReplacementNamed('/home');
                      //'/home': (context) => AuthWrapper(),
                },
              ),
            ],
          );
        },
      );
    } on FirebaseAuthException catch (e) {
      _showErrorDialog(e.message ?? 'An error occurred. Please try again.');
    }
  }

  Future<bool> isUsernameAvailable(String username) async {
    // Implement logic to check if the username is available
    // This might involve querying your database
    return true;
  }

  void _showErrorDialog(String message) {
    showDialog(
      context: context,
      builder: (context) => AlertDialog(
        title: Text('Sign Up Failed'),
        content: Text(message),
        actions: <Widget>[
          TextButton(
            child: Text('OK'),
            onPressed: () => Navigator.of(context).pop(),
          ),
        ],
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text('Sign Up')),
      body: Padding(
        padding: EdgeInsets.all(16.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Spacer(
              flex: 2,
            ),
            Icon(
              Icons.manage_accounts,
              size: 150,
              color: tdBlue,
            ),
            Spacer(),
            TextField(
              controller: _usernameController,
              decoration: InputDecoration(labelText: 'Username'),
            ),
            TextField(
              controller: _emailController,
              decoration: InputDecoration(labelText: 'Email'),
            ),
            TextField(
              controller: _passwordController,
              obscureText: !_passwordVisible,
              decoration: InputDecoration(
                labelText: 'Password',
                suffixIcon: IconButton(
                  icon: Icon(
                    // Based on passwordVisible state choose the icon
                    _passwordVisible ? Icons.visibility : Icons.visibility_off,
                    color: Theme.of(context).primaryColorDark,
                  ),
                  onPressed: () {
                    // Update the state i.e. toogle the state of passwordVisible variable
                    setState(() {
                      _passwordVisible = !_passwordVisible;
                    });
                  },
                ),
              ),
            ),
            SizedBox(height: 20),
            ElevatedButton(
              onPressed: _signUp,
              child: Text('Sign Up'),
            ),
            Spacer(
              flex: 3,
            ),
          ],
        ),
      ),
    );
  }
}
