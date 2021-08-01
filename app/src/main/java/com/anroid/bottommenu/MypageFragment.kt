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
    var reviewList: List<Review> = ArrayList<Review>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_mypage, container, false)
        db = DBHelper(getActivity(), "Person", null, 1)

        val context = getActivity()

        mypageList = view.findViewById<ListView>(R.id.mypageList)

        reviewList = db.selectReivew()
        val Adapter = ListPersonAdapter(context!!, reviewList)  //샘플리스트 사용
        mypageList.adapter = Adapter

        return view
    }

}