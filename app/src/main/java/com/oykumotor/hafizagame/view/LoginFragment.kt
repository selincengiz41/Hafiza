package com.oykumotor.hafizagame.view

import android.content.ContentValues.TAG
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import com.google.firebase.firestore.*
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.oykumotor.hafizagame.R
import com.oykumotor.hafizagame.databinding.FragmentLoginBinding
import com.oykumotor.hafizagame.view.model.User


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private lateinit var auth : FirebaseAuth
    private val binding get() = _binding!!
    private lateinit var db :FirebaseFirestore

    private var isLogin :Boolean =false
   // var userList = arrayListOf<User>()
    var userList: ArrayList<User> = ArrayList()
// ...


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        var mediaPlayer = MediaPlayer.create(context, R.raw.music)
        mediaPlayer.start() // no need to call prepare(); create() does that for you



    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvRegisterHere.setOnClickListener {
            val action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.btnLogin.setOnClickListener {

            val email =binding.etLoginEmail.text.toString()
            val password =binding.etLoginPass.text.toString()


            getUserData()

        for( i in userList){
          if(email.equals(i.email)&& password.equals(i.password)){
              Toast.makeText(context,"Giriş yapıldı",Toast.LENGTH_SHORT).show()

              val action =LoginFragmentDirections.actionLoginFragmentToGameFragment()
              Navigation.findNavController(it).navigate(action)
              isLogin=true
          }
        }

            if(!isLogin){
                Toast.makeText(context,"Giriş başarısız",Toast.LENGTH_LONG).show()
            }
        }}


    fun getUserData(){
        db =Firebase.firestore

        db.collection("Users").orderBy("email",
            Query.Direction.DESCENDING).addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Toast.makeText(
                    context,
                    exception.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            } else {

                if (snapshot != null) {
                    if (!snapshot.isEmpty) {

                        userList.clear()

                        val documents = snapshot.documents
                        for (document in documents) {
                            val email = document.get("email") as String
                            val password = document.get("password") as String



                            val user = User(email, password)
                            userList.add(user)
                        }


                    }
                }


            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }





}