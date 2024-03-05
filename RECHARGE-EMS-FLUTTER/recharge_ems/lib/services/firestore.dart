import 'package:cloud_firestore/cloud_firestore.dart';
import 'package:firebase_auth/firebase_auth.dart';
import 'package:flutter/material.dart';
//import 'package:flutter_application_1/model/user.dart';


class FirestoreService {

// get collection of notes 

//final CollectionReference notes = FirebaseFirestore.instance.collection('notes');

Future<void> initializeUser(UserCredential userId) async {
  var userDocRef = FirebaseFirestore.instance.collection('users').doc(userId.user!.uid);

  print("UserdocRef");
  print(userDocRef);


  // Check if the user document already exists
  var docSnapshot = await userDocRef.get();
  if (!docSnapshot.exists) {
    print("docSnapshot doesnt Exists");
    // User document does not exist, so create it
  await userDocRef.set({
    'createdAt': Timestamp.now(),
    'email': userId.user!.email,
    'username': FirebaseAuth.instance.currentUser?.displayName,
  });

    // Optionally initialize subcollections with a placeholder document
    // This step can be skipped if you prefer to add documents to these subcollections organically
    /*await userDocRef.collection('ToDos').add({
      'name': 'Today#1', 
      'Description': 'Do todo nr. 1',
    'createdAt': Timestamp.now(),
    'update': Timestamp.now(),
  });*/
    //await userDocRef.collection('ToDos').add({'placeholder': true});
    //await userDocRef.collection('Notes').add({'placeholder': true});

    /* If you added placeholder documents, you can immediately delete them
    // This step can be skipped if you haven't added placeholder documents
    var toDoPlaceholderDoc = await userDocRef.collection('ToDos').limit(1).get();
    if (toDoPlaceholderDoc.docs.isNotEmpty) {
      await toDoPlaceholderDoc.docs.first.reference.delete();
    }

    var notesPlaceholderDoc = await userDocRef.collection('Notes').limit(1).get();
    if (notesPlaceholderDoc.docs.isNotEmpty) {
      await notesPlaceholderDoc.docs.first.reference.delete();
    }*/
  }
  // If the user document already exists, no further action is needed
}



String getCurrentUser() {
  User? user = FirebaseAuth.instance.currentUser;

  if (user != null) {
    // User is signed in
    // You can access user properties like user.email, user.uid, etc.
    return user.uid;
  } else {
    // No user is signed in
    return ' ';
  }
}

// CREATE: add a new note 
Future<void> addNote(String note, String userId, String List){
  CollectionReference notes = FirebaseFirestore.instance.collection('users').doc(userId).collection('ToDos').doc(List).collection('notes');
  return notes.add({
    'note': note,
    'timestamp': Timestamp.now()
  });
}

// READ: get notes from database

Stream<QuerySnapshot> getNoteStream(String userId, String List){
  CollectionReference notes = FirebaseFirestore.instance.collection('users').doc(userId).collection('ToDos').doc(List).collection('notes');
  final notesStream = notes.orderBy('timestamp', descending: true).snapshots();

  return notesStream;
}

Future<void> addSubcollectionName(String userId, String subcollectionName) async {
  final FirebaseFirestore firestore = FirebaseFirestore.instance;
  final DocumentReference registryDoc = firestore.collection('users').doc(userId).collection('ToDos').doc('registry');
  
  return firestore.runTransaction((transaction) async {
    DocumentSnapshot snapshot = await transaction.get(registryDoc);

    if (!snapshot.exists) {
      transaction.set(registryDoc, {'subcollections': [subcollectionName]});
      print('Registry already exists');
    } else {
      print('Registry doesnt exists');
      List<dynamic> subcollections = snapshot['subcollections'];
      if (!subcollections.contains(subcollectionName)) {
        subcollections.add(subcollectionName);
        transaction.update(registryDoc, {'subcollections': subcollections});
      }
    }
  });
}

Future<bool> collectionExists(String userId, String collectionPath) async {

  final collectionRef = FirebaseFirestore.instance.collection('users').doc(userId).collection('ToDos').doc(collectionPath).collection('notes');
  final querySnapshot = await collectionRef.limit(1).get();

  return querySnapshot.docs.isNotEmpty;
}


 Widget buildCollectionListView(String userId, String subcollectionName) {

  final FirebaseFirestore firestore = FirebaseFirestore.instance;

    return StreamBuilder<QuerySnapshot>(
      stream: firestore.collection('users').doc(userId).collection('Todos').doc('registry').collection(subcollectionName).snapshots(),
      builder: (context, snapshot) {
        if (!snapshot.hasData) return CircularProgressIndicator();

        List<DocumentSnapshot> docs = snapshot.data!.docs;

        return ListView.builder(
          shrinkWrap: true,
          physics: NeverScrollableScrollPhysics(), // since it's inside another scrollable widget
          itemCount: docs.length,
          itemBuilder: (context, index) {
            var data = docs[index].data() as Map<String, dynamic>;
            return ListTile(
              title: Text(data['yourFieldName']), // Replace 'yourFieldName' with the actual field name you want to display
            );
          },
        );
      },
    );
  }




Future<void> moveNote(String userId, String sourceCollection, String targetCollection, String docId) async {
  
  print("moveto startetd!");

  final FirebaseFirestore firestore = FirebaseFirestore.instance;
  
  DocumentReference sourceDoc = firestore.collection('users').doc(userId).collection('ToDos').doc(sourceCollection).collection('notes').doc(docId);
  DocumentReference targetDoc = firestore.collection('users').doc(userId).collection('ToDos').doc(targetCollection).collection('notes').doc(docId);

  return firestore.runTransaction((transaction) async {
    DocumentSnapshot snapshot = await transaction.get(sourceDoc);

    if (!snapshot.exists) {
      throw Exception("Document does not exist!");
    }

    transaction.set(targetDoc, snapshot.data());
    transaction.delete(sourceDoc);
  });
}







// UPDATE: update notes given a doc id

// DELETE: delete notes given a doc id


}