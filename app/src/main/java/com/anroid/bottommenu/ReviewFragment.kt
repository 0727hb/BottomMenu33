package com.anroid.bottommenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView

class ReviewFragment : Fragment() {
    lateinit var db: DBHelper

    lateinit var edt_alias: TextView
    lateinit var edt_type: TextView

    // 별점은 레이팅 바로 구현하는 게 좋을 것 같다
    lateinit var ratingBar: RatingBar

    lateinit var summary: EditText
    lateinit var edt_title: EditText
    lateinit var content: EditText

    lateinit var btn_add: Button
    lateinit var btn_rev: Button
    lateinit var btn_del: Button

    var istPersons: List<Person> = ArrayList<Person>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_review, container, false)

        db = DBHelper(getActivity(), "Person", null, 1)

        edt_alias = view.findViewById<TextView>(R.id.edt_alias)
        edt_type = view.findViewById<TextView>(R.id.edt_type)

        ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)

        summary = view.findViewById<EditText>(R.id.summary)
        edt_title = view.findViewById<EditText>(R.id.edt_title)
        content = view.findViewById<EditText>(R.id.content)

        btn_add = view.findViewById<Button>(R.id.btn_add)
        btn_del = view.findViewById<Button>(R.id.btn_del)
        btn_rev = view.findViewById<Button>(R.id.btn_rev)

        refreshData()

        //Event
        btn_add.setOnClickListener {
            val person = Person(
                edt_alias.text.toString(),
                edt_type.text.toString(),
                edt_title.text.toString()
            )
            db.addPerson(person)
            refreshData()
            (activity as MainActivity).reviewToMypage()
        }
        btn_rev.setOnClickListener {
            val person = Person(
                edt_alias.text.toString(),
                edt_type.text.toString(),
                edt_title.text.toString()
            )
            db.updatePerson(person)
            refreshData()
        }
        btn_del.setOnClickListener {
            val person = Person(
                edt_alias.text.toString(),
                edt_type.text.toString(),
                edt_title.text.toString()
            )
            db.deletePerson(person)
            refreshData()
        }

        return view
    }

    private fun refreshData(){
        istPersons = db.allPerson
        //val adapter= ListPersonAdapter()
        //list.adapter = adapter
    }

}