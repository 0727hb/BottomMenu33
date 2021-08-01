package com.anroid.bottommenu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class ReviewFragment : Fragment() {
    lateinit var db: DBHelper

    lateinit var edt_alias: TextView

    lateinit var ratingBar: RatingBar

    lateinit var summary: EditText
    lateinit var edt_title: EditText
    lateinit var review: EditText

    lateinit var btn_add: Button
    lateinit var btn_rev: Button
    lateinit var btn_del: Button

    lateinit var btn_good: CheckBox
    lateinit var btn_hate: CheckBox

    var istPersons: List<Person> = ArrayList<Person>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_review, container, false)

        db = DBHelper(getActivity(), "Person", null, 1)

        edt_alias = view.findViewById<TextView>(R.id.edt_alias)

        ratingBar = view.findViewById<RatingBar>(R.id.ratingBar)

        summary = view.findViewById<EditText>(R.id.summary)
        edt_title = view.findViewById<EditText>(R.id.edt_title)
        review = view.findViewById<EditText>(R.id.review)

        btn_add = view.findViewById<Button>(R.id.btn_add)
        btn_del = view.findViewById<Button>(R.id.btn_del)
        btn_rev = view.findViewById<Button>(R.id.btn_rev)

        btn_good = view.findViewById<CheckBox>(R.id.btn_good)
        btn_hate = view.findViewById<CheckBox>(R.id.btn_hate)

        refreshData()

        //Event
        btn_add.setOnClickListener {
            var alias = "HELLOouo"
            var title = edt_title.text.toString()
            var review = review.text.toString()

            db.addReview(alias, title, review)

            (activity as MainActivity).reviewToMypage()
        }
        btn_rev.setOnClickListener {

            refreshData()
        }
        btn_del.setOnClickListener {

            refreshData()
        }

        btn_good.setOnClickListener{
            btn_hate.isChecked = false
        }
        btn_hate.setOnClickListener {
            btn_good.isChecked = false
        }

        return view
    }

    private fun refreshData(){
        istPersons = db.allPerson
        //val adapter= ListPersonAdapter()
        //list.adapter = adapter
    }
}