package com.anroid.bottommenu

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

class ListPersonAdapter(val context: Context, val istPerson: List<Person>): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView: View = LayoutInflater.from(context).inflate(R.layout.review_contents, null)

        val textId = rowView.findViewById<TextView>(R.id.textId)
        val imageView = rowView.findViewById<ImageView>(R.id.imageView)
        val textContent = rowView.findViewById<TextView>(R.id.textContent)
        val textgenre = rowView.findViewById<TextView>(R.id.textgenre)
        val btn_del = rowView.findViewById<Button>(R.id.btn_del)
        val btn_rev = rowView.findViewById<Button>(R.id.btn_rev)

        //textId
        imageView.setImageResource(R.drawable.image)         // Person 클래스 이미지 없음
        textContent.text = istPerson[position].title
        textgenre.text = istPerson[position].type

        //이벤트
        rowView.setOnClickListener {
            // edt_title.setText(rowView.textContent.text.toString())
            // edt_type.setText(rowView.textgenre.text.toString())
        }
        btn_del.setOnClickListener {

        }
        btn_rev.setOnClickListener {

        }

        return rowView
    }

    override fun getCount(): Int {
        return istPerson.size
    }

    override fun getItem(position: Int): Any {
        return istPerson[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }
}