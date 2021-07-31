package com.anroid.bottommenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ListView

class MypageFragment : Fragment() {

    lateinit var db: DBHelper

    lateinit var mypageList: ListView
    var istPersons: List<Person> = ArrayList<Person>()

    //리스트 샘플
    var dogList = arrayListOf<Person>(
        Person("Chow Chow", "Male", "4"),
        Person("Breed Pomeranian", "Female", "1"),
        Person("Golden Retriver", "Female", "3"),
        Person("Yorkshire Terrier", "Male", "5"),
        Person("Pug", "Male", "4"),
        Person("Alaskan Malamute", "Male", "7"),
        Person("Shih Tzu", "Female", "5")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mypage, container, false)
        db = DBHelper(getActivity(), "Person", null, 1)

        val context = getActivity()

        mypageList = view.findViewById<ListView>(R.id.mypageList)


        istPersons = db.allPerson
        val Adapter = ListPersonAdapter(context!!, dogList)  //샘플리스트 사용
        mypageList.adapter = Adapter

        return view
    }

}