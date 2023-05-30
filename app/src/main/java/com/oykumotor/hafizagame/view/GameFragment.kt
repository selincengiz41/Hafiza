package com.oykumotor.hafizagame.view

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.oykumotor.hafizagame.R
import com.oykumotor.hafizagame.databinding.FragmentGameBinding
import com.oykumotor.hafizagame.databinding.FragmentLoginBinding
import com.oykumotor.hafizagame.view.adapter.CardAdapter
import com.oykumotor.hafizagame.view.model.Card
import com.oykumotor.hafizagame.view.model.User

class GameFragment : Fragment() , CardAdapter.Listener {
    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private val cardAdapter = CardAdapter(arrayListOf(),this@GameFragment)
    private lateinit var db : FirebaseFirestore
    private lateinit var auth : FirebaseAuth
    var cardGryffindorList: ArrayList<Card> = ArrayList()
    var cardSlytherinList: ArrayList<Card> = ArrayList()
    var cardRavenclawList: ArrayList<Card> = ArrayList()
    var cardHufflepuffList: ArrayList<Card> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.visibility=View.GONE
        binding.textViewScore.visibility=View.GONE
        binding.score.visibility=View.GONE
        binding.time.visibility=View.GONE

        binding.button.setOnClickListener {


            binding.button.visibility=View.GONE
            binding.textViewScore.visibility=View.VISIBLE
            binding.score.visibility=View.VISIBLE
            binding.time.visibility=View.VISIBLE
            binding.recyclerView.visibility=View.VISIBLE
            binding.recyclerView.layoutManager= GridLayoutManager(context,4)
            binding.recyclerView.adapter=cardAdapter

            getGryffindorCardData()
           /* cardGryffindorList.addAll(cardAdapter.cardList)
            cardAdapter.cardList.clear()
            cardAdapter.notifyDataSetChanged()*/

          /*  cardHufflepuffList.addAll(cardAdapter.cardList)
            cardAdapter.cardList.clear()
            cardAdapter.notifyDataSetChanged()*/
            getRavenClawCardData()
            /*cardRavenclawList.addAll(cardAdapter.cardList)
            cardAdapter.cardList.clear()
            cardAdapter.notifyDataSetChanged()*/
            getSlytherinCardData()
           /* cardSlytherinList.addAll(cardAdapter.cardList)
            cardAdapter.cardList.clear()
            cardAdapter.notifyDataSetChanged()*/
            getHufflepuffCardData()

            val timer =object :CountDownTimer(45000,1000){
                override fun onTick(p0: Long) {
                binding.time.text= (p0/1000).toString()
                }

                override fun onFinish() {
                    val builder = AlertDialog.Builder(context)
                    builder.setTitle("Game over")
                    builder.setMessage("Time is out. Score ${binding.score.text}")
//builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))

                    builder.setPositiveButton(android.R.string.ok) { dialog, which ->
                        Toast.makeText(context,
                            android.R.string.ok, Toast.LENGTH_LONG).show()
                    }

                   /* builder.setNegativeButton(android.R.string.no) { dialog, which ->
                        Toast.makeText(context,
                            android.R.string.no, Toast.LENGTH_SHORT).show()
                    }

                    builder.setNeutralButton("Maybe") { dialog, which ->
                        Toast.makeText(context,
                            "Maybe", Toast.LENGTH_SHORT).show()
                    }*/
                    builder.show()

                }

            }
            timer.start()



        }
    }

    fun getGryffindorCardData(){
        val number=11
        val randomIndex= Math.floor(Math.random()*number)
        db =Firebase.firestore

        db.collection("GryffindorCards").orderBy("House",
            Query.Direction.DESCENDING).limit(2).addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Toast.makeText(
                    context,
                    exception.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            } else {

                if (snapshot != null) {
                    if (!snapshot.isEmpty) {



                        val documents = snapshot.documents
                        for (document in documents) {
                            val house = document.get("House") as String
                            val name = document.get("Name") as String
                            val point = document.get("Point") as String

                            val card = Card(house, name,point,false)
                            println(card.Name+" "+card.House+" "+card.Point)
                            cardAdapter.cardList.add(card)
                            cardAdapter.cardList.add(card)
                            cardAdapter.notifyDataSetChanged()


                        }


                    }
                }


            }
        }
    }

  fun getSlytherinCardData(){
        db =Firebase.firestore

        db.collection("SlytherinCards").orderBy("House",
            Query.Direction.DESCENDING).limit(2).addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Toast.makeText(
                    context,
                    exception.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            } else {

                if (snapshot != null) {
                    if (!snapshot.isEmpty) {

                     //   cardList.clear()

                        val documents = snapshot.documents
                        for (document in documents) {
                            val name = document.get("Name") as String
                            val house = document.get("House") as String
                            val point = document.get("Point") as String



                            val card = Card(house, name,point,false)
                            cardAdapter.cardList.add(card)
                            cardAdapter.cardList.add(card)
                            cardAdapter.notifyDataSetChanged()
                        }


                    }
                }


            }
        }
    }
    fun getRavenClawCardData(){
        db =Firebase.firestore

        db.collection("RavenclawCards").orderBy("House",
            Query.Direction.DESCENDING).limit(2).addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Toast.makeText(
                    context,
                    exception.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            } else {

                if (snapshot != null) {
                    if (!snapshot.isEmpty) {



                        val documents = snapshot.documents
                        for (document in documents) {
                            val name = document.get("Name") as String
                            val house = document.get("House") as String
                            val point = document.get("Point") as String



                            val card = Card(house, name,point,false)
                            cardAdapter.cardList.add(card)
                            cardAdapter.cardList.add(card)
                            cardAdapter.notifyDataSetChanged()
                        }


                    }
                }


            }
        }
    }
    fun getHufflepuffCardData(){
        db =Firebase.firestore

        db.collection("HufflePuffCards").orderBy("House",
            Query.Direction.DESCENDING).limit(2).addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                Toast.makeText(
                    context,
                    exception.localizedMessage,
                    Toast.LENGTH_LONG
                ).show()
            } else {

                if (snapshot != null) {
                    if (!snapshot.isEmpty) {



                        val documents = snapshot.documents
                        for (document in documents) {
                            val name = document.get("Name") as String
                            val house = document.get("House") as String
                            val point = document.get("Point") as String


                            val card = Card(house, name,point,false)
                            cardAdapter.cardList.add(card)
                            cardAdapter.cardList.add(card)
                            cardAdapter.cardList.shuffle()
                            cardAdapter.notifyDataSetChanged()
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

    override fun onScoreChanged(score: Int, islem: Boolean) {


       var current= binding.score.text.toString().toInt()
        var sum =0
        if(islem){
             sum =current+score
        }
        else{
             sum =current-score
        }


        binding.score.text=sum.toString()
    }


}