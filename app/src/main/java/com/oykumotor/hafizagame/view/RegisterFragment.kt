package com.oykumotor.hafizagame.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.oykumotor.hafizagame.R
import com.oykumotor.hafizagame.databinding.FragmentLoginBinding
import com.oykumotor.hafizagame.databinding.FragmentRegisterBinding
import com.oykumotor.hafizagame.view.model.User

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!
    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
// ...


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        db = Firebase.firestore

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvLoginHere.setOnClickListener {
            val action = RegisterFragmentDirections.actionRegisterFragmentToLoginFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.btnRegister.setOnClickListener {



            val email=  binding.etRegEmail.text.toString()
            val password = binding.etRegPass.text.toString()

            val user = hashMapOf<String,Any>()
            user.put("email",email)
            user.put("password",password)



            db.collection( "Users").add(user).addOnCompleteListener{task ->

                if (task.isComplete && task.isSuccessful) {
                    //back
                    Toast.makeText(context,"Kayıt yapıldı",Toast.LENGTH_LONG).show()


                }

            }.addOnFailureListener{exception ->
                Toast.makeText(context,exception.localizedMessage,Toast.LENGTH_LONG).show()
            }





        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}